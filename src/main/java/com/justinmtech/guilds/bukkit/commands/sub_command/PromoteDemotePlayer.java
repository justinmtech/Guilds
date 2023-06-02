package com.justinmtech.guilds.bukkit.commands.sub_command;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.bukkit.commands.SubCommand;
import com.justinmtech.guilds.core.GPlayer;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.core.Role;
import com.justinmtech.guilds.bukkit.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PromoteDemotePlayer extends SubCommand {

    public PromoteDemotePlayer(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    //TODO Cannot demote players
    //guild promote/demote <player>
    private boolean execute() {
        if (getSender() instanceof Player) {
            Player player = (Player) getSender();
            Optional<Guild> guild = getPlugin().getGuildsRepository().getGuild(player.getUniqueId());
            if (guild.isEmpty()) {
                Message.send(getPlugin(), getSender(), "must-be-mod");
                return false;
            }
            String targetName = getArgs()[1];
            if (targetName.isEmpty()) {
                Message.send(getPlugin(), getSender(), "player-not-found");
                return false;
            }
            OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
            if (!guild.get().getMembers().containsKey(target.getUniqueId())) {
                Message.send(getPlugin(), getSender(), "player-not-in-guild");
                return false;
            }
            Optional<GPlayer> targetGplayer = getPlugin().getGuildsRepository().getPlayer(target.getUniqueId());

            if (targetGplayer.isEmpty()) {
                Message.send(getPlugin(), getSender(), "player-not-found");
                return false;
            }
            Optional<GPlayer> gplayer = getPlugin().getGuildsRepository().getPlayer(player.getUniqueId());
            if (gplayer.isEmpty()) {
                Message.send(getPlugin(), getSender(), "must-be-mod");
                return false;
            }
            boolean promoting = getArgs()[0].equalsIgnoreCase("promote");
            if (targetGplayer.get().getRole() == Role.LEADER) {
                Message.send(getPlugin(), getSender(), promoting ? "cannot-promote" : "cannot-demote");
                return false;
            }
            Role newRole = promoting ? targetGplayer.get().promote() : targetGplayer.get().demote();
            Map<UUID, Role> onlineMembers = guild.get().getOnlineMembers();
            if (newRole != null) {
                guild.get().getMembers().put(target.getUniqueId(), newRole);
                getPlugin().getGuildsRepository().savePlayer(targetGplayer.get());
                getPlugin().getGuildsRepository().saveGuild(guild.get());
                for (UUID uuid : onlineMembers.keySet()) {
                    Player onlineMember = Bukkit.getPlayer(uuid);
                    if (onlineMember != null) {
                        Map<String, String> placeholders = new HashMap<>();
                        placeholders.put("%player%", target.getName());
                        placeholders.put("%role%", targetGplayer.get().getRole().toString().replace("COLEADER", "CO-LEADER"));
                        Message.sendPlaceholders(getPlugin(), onlineMember, promoting ? "player-promoted" : "player-demoted", placeholders);
                    }
                }
            } else {
                for (UUID uuid : onlineMembers.keySet()) {
                    Player onlineMember = Bukkit.getPlayer(uuid);
                    if (onlineMember != null) {
                        Message.sendPlaceholder(getPlugin(), onlineMember, promoting ? "cannot-promote" : "cannot-demote", target.getName());
                    }
                }
            }
        } else {
            Message.send(getPlugin(), getSender(), "not-console-command");
        }
        return true;
    }
}
