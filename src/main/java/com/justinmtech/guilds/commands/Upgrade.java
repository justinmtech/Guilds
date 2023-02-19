package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Upgrade extends SubCommand {
    private final String label;
    public Upgrade(Guilds plugin, CommandSender sender, String[] args, String label) {
        super(plugin, sender, args);
        this.label = label;
        execute();
    }

    private void execute() {
        if (getSender() instanceof Player) {
            Player player = (Player) getSender();
            Optional<Guild> guild = getPlugin().getData().getGuild(player.getUniqueId());
            if (guild.isEmpty()) {
                Message.send(getPlugin(), getSender(), "not-in-guild");
                return;
            }
            int level = guild.get().getLevel();
            int upgradeCost = getPlugin().getConfig().getInt("settings.upgrade-costs.level-" + level);

            int amount = Integer.parseInt(String.valueOf(upgradeCost));
            DecimalFormat formatter = new DecimalFormat("#,###");

            if (!guild.get().isOwner(player.getUniqueId())) {
                Message.send(getPlugin(), getSender(), "must-be-owner");
            } else if (guild.get().getLevel() == guild.get().getMaxLevel()) {
                Message.send(getPlugin(), getSender(), "already-max-level");
            } else if (Guilds.getEcon().has(Bukkit.getOfflinePlayer(player.getUniqueId()), upgradeCost)) {
                String message = ChatColor.translateAlternateColorCodes('&', getPlugin().getConfig().getString("messages.upgrade-confirm"))
                        .replace("%command%", label)
                        .replace("%cost%", formatter.format(amount));
                player.sendMessage(message);
                getPlugin().getCache().addTransactionConfirmation(player.getUniqueId(), upgradeCost);
                BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                scheduler.scheduleSyncDelayedTask(getPlugin(), () -> getPlugin().getCache().removeTransactionConfirmation(player.getUniqueId()), 200);
            } else {
                Message.sendPlaceholder(getPlugin(), getSender(), "upgrade-insufficient-funds", formatter.format(amount));
            }
        } else {
            Message.send(getPlugin(), getSender(), "not-console-command");
        }
    }
}
