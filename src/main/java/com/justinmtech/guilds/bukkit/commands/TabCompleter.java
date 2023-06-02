package com.justinmtech.guilds.bukkit.commands;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.core.Role;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    private final Guilds plugin;
    public final static List<String> BASE_COMMANDS = List.of("help", "create", "list", "accept", "deny");
    public final static List<String> HAS_GUILD_COMMANDS = List.of("help", "warp", "leave", "list", "accept", "deny");
    public final static List<String> GUILD_LEADER_COMMANDS = List.of("help", "invite", "kick", "upgrade", "promote", "demote", "setwarp", "setdesc", "list", "disband");

    public TabCompleter(Guilds plugin) {
        this.plugin = plugin;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Optional<Guild> guild = getPlugin().getGuildsRepository().getGuild(player.getUniqueId());
            if (command.getName().equalsIgnoreCase("guilds")) {
                if (args.length == 1) {
                    if (guild.isEmpty()) {
                        return getRecommendationsThatStartWith(args[0], BASE_COMMANDS);
                    } else {
                        if (guild.get().isOwner(player.getUniqueId())) {
                            return getRecommendationsThatStartWith(args[0], GUILD_LEADER_COMMANDS);
                        } else {
                            return getRecommendationsThatStartWith(args[0], HAS_GUILD_COMMANDS);
                        }
                    }
                }
                if (args.length == 2) {
                    if (guild.isPresent() && args[0].equalsIgnoreCase("warp")) {
                        Set<String> warps = guild.get().getWarps().keySet();
                        return new ArrayList<>(warps);
                    }
                    if (guild.isPresent() && args[0].equalsIgnoreCase("invite")) {
                        List<String> players = new ArrayList<>();
                        Bukkit.getOnlinePlayers().forEach(p -> {
                            if (!guild.get().getMembers().containsKey(p.getUniqueId())) {
                                players.add(p.getName());
                            }
                        });
                        return players;
                    }
                    if (guild.isPresent() && (args[0].equalsIgnoreCase("promote"))) {
                        List<String> players = new ArrayList<>();
                        for (UUID member : guild.get().getMembers().keySet()) {
                            if (guild.get().getMembers().containsKey(member)) {
                                if (!guild.get().isOwner(member)) {
                                    String name = Bukkit.getOfflinePlayer(member).getName();
                                    if (name != null && !name.isEmpty()) players.add(name);
                                }
                            }
                        }
                        return players;
                    }
                    if (guild.isPresent() && (args[0].equalsIgnoreCase("demote"))) {
                        List<String> players = new ArrayList<>();
                        for (UUID member : guild.get().getMembers().keySet()) {
                                Role role = guild.get().getMembers().get(member);
                                if (role != Role.MEMBER) {
                                    String name = Bukkit.getOfflinePlayer(member).getName();
                                    if (name != null && !name.isEmpty()) players.add(Bukkit.getOfflinePlayer(member).getName());
                                }
                        }
                        return players;
                    }
                    if (guild.isPresent() && (args[0].equalsIgnoreCase("kick"))) {
                        List<String> players = new ArrayList<>();
                        for (UUID member : guild.get().getMembers().keySet()) {
                            if (guild.get().getMembers().containsKey(member)) {
                                if (!guild.get().isOwner(member)) {
                                    String name = Bukkit.getOfflinePlayer(member).getName();
                                    if (name != null && !name.isEmpty()) players.add(name);
                                }
                            }
                        }
                        return players;
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    public static List<String> getRecommendationsThatStartWith(String arg, List<String> subCommands) {
        if (arg == null) return subCommands;
        if (arg.isEmpty()) return subCommands;
        List<String> recommendations = new ArrayList<>();
        for (String command : subCommands) {
            if (command.startsWith(arg)) {
                recommendations.add(command);
            }
        }
        return recommendations;
    }

    public Guilds getPlugin() {
        return plugin;
    }
}
