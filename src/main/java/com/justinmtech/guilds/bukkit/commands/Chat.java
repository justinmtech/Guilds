package com.justinmtech.guilds.bukkit.commands;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.bukkit.util.Message;
import com.justinmtech.guilds.core.Guild;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Optional;

public class Chat extends SubCommand {

    public Chat(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        Player player = (Player) getSender();
        try {
            Optional<Guild> guildFetched = getPlugin().getData().getGuild(player.getUniqueId());
            if (guildFetched.isEmpty()) {
                Message.send(getPlugin(), getSender(), "not-in-guild");
                return;
            }

            String message = getMessageFromArgs(getArgs());
            if (message.isEmpty() || message.isBlank()) {
                Message.send(getPlugin(), getSender(), "message-empty");
                return;
            }
            Guild guild = guildFetched.get();
            guild.sendChat(getMessageFormat(player.getName(), message));

        } catch (NullPointerException e) {
            e.printStackTrace();
            Message.send(getPlugin(), getSender(), "generic-error");
        }
    }

    public static String getMessageFromArgs(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            if (!arg.equalsIgnoreCase("chat") && !arg.equalsIgnoreCase("c")) {
                sb.append(arg).append(" ");
            }
        }
        return sb.toString();
    }

    private String getMessageFormat(String playerName, String message) {
        FileConfiguration config = getPlugin().getConfig();
        String format = ChatColor.translateAlternateColorCodes('&', config.getString("chat.format", "§a[GC] &f%player%&7: &a%message%"));
        format = format.replace("%player%", playerName);
        format = format.replace("%message%", message);
        return format;
    }
}
