package com.justinmtech.guilds.util;

import com.justinmtech.guilds.core.Guild;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;

import java.util.UUID;

public class Message {
    public static void send(Guilds plugin, CommandSender sender, String messagePath) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages." + messagePath)));
    }

    public static void sendPlaceholder(Guilds plugin, CommandSender sender, String messagePath, String placeholder) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages." + messagePath).replace("%placeholder%", placeholder)));
    }

    public static void sendPlaceholderPath(Guilds plugin, CommandSender sender, String messagePath, String placeholder) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(messagePath).replace("%placeholder%", placeholder)));
    }

    public static void sendPlaceholder(Guilds plugin, Player sender, String messagePath, String placeholder) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages." + messagePath).replace("%placeholder%", placeholder)));
    }

    public static void sendHelp(Guilds plugin, CommandSender sender, String messagePath, String placeholder) {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection(messagePath);
        for (String key : section.getKeys(false)) {
            if (key.equals("header")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(messagePath + "." + key)));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(messagePath + "." + key).replace("%placeholder%", placeholder)));
            }
        }
    }

    public static void sendGuildList(Guilds plugin, CommandSender sender, String messagePath, String[] placeholder) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages." + messagePath)
                        .replace("%number%", placeholder[0])
                        .replace("%guildName%", placeholder[1])
                        .replace("%members%", placeholder[2])));
    }

    public static void sendGuildInfo(Guilds plugin, CommandSender sender, String messagePath, Guild guild) {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection(messagePath);
        for (String key : section.getKeys(false)) {
            switch (key) {
                case "header" :
                    sendPlaceholderPath(plugin, sender, messagePath + "." + key, guild.getName());
                    break;
                case "leader" :
                    sendPlaceholderPath(plugin, sender, messagePath + "." + key, guild.getOwner().toString());
                    break;
                case "description" :
                    sendPlaceholderPath(plugin, sender, messagePath + "." + key, guild.getDescription());
                    break;
                case "level" :
                    sendPlaceholderPath(plugin, sender, messagePath + "." + key, String.valueOf(guild.getLevel()));
                    break;
                case "size" :
                    sendPlaceholderPath(plugin, sender, messagePath + "." + key, String.valueOf(guild.getMembers().size()));
                    break;
                case "members" :
                    send(plugin, sender, messagePath + "." + key);
                    break;
                case "member-color" :
                    for (UUID member : guild.getMembers().keySet()) {
                        StringBuilder string = new StringBuilder();
                        string.append(ChatColor.translateAlternateColorCodes('&', section.getString("member-color")) +
                                Bukkit.getOfflinePlayer(member).getName() + " ");
                    }
                    send(plugin, sender, messagePath + "." + key);
            }
        }
    }

    public static void sendRaw(Guilds plugin, CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
