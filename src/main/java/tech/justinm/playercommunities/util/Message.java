package tech.justinm.playercommunities.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import tech.justinm.playercommunities.PlayerCommunities;

public class Message {
    public static void send(PlayerCommunities plugin, CommandSender sender, String messagePath) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages." + messagePath)));
    }

    public static void sendPlaceholder(PlayerCommunities plugin, CommandSender sender, String messagePath, String placeholder) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages." + messagePath).replace("%placeholder%", placeholder)));
    }

    public static void sendRaw(PlayerCommunities plugin, CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
