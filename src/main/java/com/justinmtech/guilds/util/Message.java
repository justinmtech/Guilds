package com.justinmtech.guilds.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;

public class Message {
    public static void send(Guilds plugin, CommandSender sender, String messagePath) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages." + messagePath)));
    }

    public static void sendPlaceholder(Guilds plugin, CommandSender sender, String messagePath, String placeholder) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages." + messagePath).replace("%placeholder%", placeholder)));
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

    public static void sendGuildInfo(Guilds plugin, CommandSender sender, String messagePath, String[] placeholder) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages." + messagePath)
                        .replace("%number%", placeholder[0])
                        .replace("%guildName%", placeholder[1])
                        .replace("%members%", placeholder[2])));
    }

    public static void sendRaw(Guilds plugin, CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
