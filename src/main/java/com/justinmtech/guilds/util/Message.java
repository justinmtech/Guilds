package com.justinmtech.guilds.util;

import com.justinmtech.guilds.core.Guild;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Message {
    public static void send(Guilds plugin, CommandSender sender, String messagePath) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("messages." + messagePath))));
    }

    public static void sendPlaceholder(Guilds plugin, CommandSender sender, String messagePath, String placeholder) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages." + messagePath).replace("%placeholder%", placeholder)));
    }

    public static void sendPlaceholders(Guilds plugin, CommandSender sender, String messagePath, Map<String, String> placeholders) {
        String output = plugin.getConfig().getString("messages." + messagePath);
        for (String placeholder : placeholders.keySet()) {
            output = Objects.requireNonNull(output).replace(placeholder, placeholders.get(placeholder));
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', output));
    }

    public static void sendPlaceholderPath(Guilds plugin, CommandSender sender, String messagePath, String placeholder) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString(messagePath)).replace("%placeholder%", placeholder)));
    }

    public static void sendPlaceholder(Guilds plugin, Player sender, String messagePath, String placeholder) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages." + messagePath).replace("%placeholder%", placeholder)));
    }

    public static void sendHelp(Guilds plugin, CommandSender sender, String messagePath, String placeholder) {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection(messagePath);
        for (String key : section.getKeys(false)) {
            if (key.equals("header")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString(messagePath + "." + key))));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(messagePath + "." + key).replace("%placeholder%", placeholder)));
            }
        }
    }

    public static void sendGuildList(Guilds plugin, CommandSender sender, String messagePath, String[] placeholder) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                Objects.requireNonNull(plugin.getConfig().getString("messages." + messagePath))
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
                    sendPlaceholderPath(plugin, sender, messagePath + "." + key, Bukkit.getOfflinePlayer(guild.getOwner()).getName());
                    break;
                case "description" :
                    sendPlaceholderPath(plugin, sender, messagePath + "." + key, guild.getDescription());
                    break;
                case "level" :
                    sendPlaceholderPath(plugin, sender, messagePath + "." + key, guild.getLevel() + "/" + guild.getMaxLevel());
                    break;
                case "size" :
                    sendPlaceholderPath(plugin, sender, messagePath + "." + key, guild.getMembers().size() + "/" + guild.getMaxMembers());
                    break;
                case "online" :
                    int online = 0;
                    for (UUID member : guild.getMembers().keySet()) {
                        if (Bukkit.getPlayer(member) != null) online++;
                    }
                    sendPlaceholderPath(plugin, sender, messagePath + "." + key, online + "/"  + guild.getMembers().size());
                    break;
                case "members" :
                    StringBuilder string = new StringBuilder();
                    string.append(plugin.getConfig().getString(messagePath + "." + key)).append(" ");
                    for (UUID member : guild.getMembers().keySet()) {
                        string.append(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(section.getString("member-color")))).append(Bukkit.getOfflinePlayer(member).getName()).append(" ");
                    }
                    sendRaw(plugin, sender, string.toString());
                    break;
                case "bottom" :
                    send(plugin, sender, "guild-info." + key);
                    break;
            }
        }
    }

    public static void sendRaw(Guilds plugin, CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
