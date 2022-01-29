package tech.justinm.playercommunities.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;

public class Message {
    public static void send(PlayerCommunities plugin, CommandSender sender, String messagePath) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages." + messagePath)));
    }

    public static void sendPlaceholder(PlayerCommunities plugin, CommandSender sender, String messagePath, String placeholder) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages." + messagePath).replace("%placeholder%", placeholder)));
    }

    public static void sendPlaceholder(PlayerCommunities plugin, Player sender, String messagePath, String placeholder) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages." + messagePath).replace("%placeholder%", placeholder)));
    }

    public static void sendHelp(PlayerCommunities plugin, CommandSender sender, String messagePath, String placeholder) {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection(messagePath);
        for (String key : section.getKeys(false)) {
            if (key.equals("header")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(messagePath + "." + key)));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(messagePath + "." + key).replace("%placeholder%", placeholder)));
            }
        }
    }

    public static void sendCommunityInfo(PlayerCommunities plugin, CommandSender sender, String messagePath, String[] placeholder) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages." + messagePath)
                        .replace("%number%", placeholder[0])
                        .replace("%communityName%", placeholder[1])
                        .replace("%members%", placeholder[2])));
    }

    public static void sendRaw(PlayerCommunities plugin, CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
