package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

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

    //TODO Transaction confirmations
    private void execute() {
        if (getSender() instanceof Player) {
            Player player = (Player) getSender();
            Optional<Guild> guild = getPlugin().getData().getGuild(player.getUniqueId());
            if (guild.isEmpty()) {
                Message.send(getPlugin(), getSender(), "not-in-guild");
                return;
            }
            double upgradeCost = getPlugin().getConfig().getDouble("settings.upgrade-costs.level-" + guild.orElseThrow().getLevel() + 1);

            if (!guild.get().isOwner(player.getUniqueId())) {
                Message.send(getPlugin(), getSender(), "must-be-owner");
            } else if (guild.get().getLevel() == guild.get().getMaxLevel()) {
                Message.send(getPlugin(), getSender(), "already-max-level");
            } else if (Guilds.getEcon().has(Bukkit.getOfflinePlayer(player.getUniqueId()), upgradeCost)) {
                Map<String, String> placeholders = new HashMap<>();
                placeholders.put("%cost%", String.valueOf(upgradeCost));
                placeholders.put("%command%", label);
                Message.sendPlaceholders(getPlugin(), getSender(), "upgrade-confirm", placeholders);
                getPlugin().getCache().addTransactionConfirmation(player.getUniqueId(), upgradeCost);
                BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                scheduler.scheduleSyncDelayedTask(getPlugin(), () -> getPlugin().getCache().removeTransactionConfirmation(player.getUniqueId()), 200);
            } else {
                Message.send(getPlugin(), getSender(), "not-console-command");
            }
        }
    }
}
