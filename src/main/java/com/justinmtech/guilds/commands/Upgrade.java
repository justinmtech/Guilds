package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class Upgrade extends SubCommand {
    private final String label;
    public Upgrade(Guilds plugin, CommandSender sender, String[] args, String label) {
        super(plugin, sender, args);
        this.label = label;
        execute();
    }

    private void execute() {
        //check if player
        if (getSender() instanceof Player) {
            Player player = (Player) getSender();
            //check if in guild
            Guild guild = getPlugin().getData().getGuild(player.getUniqueId());
            double upgradeCost = getPlugin().getConfig().getDouble("settings.upgrade-costs.level-" + guild.getLevel() + 1);
            if (guild == null) {
                Message.send(getPlugin(), getSender(), "not-in-guild");
            } else if (!guild.isOwner(player.getUniqueId())) {
                Message.send(getPlugin(), getSender(), "must-be-owner");
            } else if (guild.getLevel() == guild.getMaxLevel()) {
                Message.send(getPlugin(), getSender(), "already-max-level");
            } else if (Guilds.getEcon().has(Bukkit.getOfflinePlayer(player.getUniqueId()), upgradeCost)) {
                String[] placeholders = {label, String.valueOf(upgradeCost)};
                Message.sendPlaceholders(getPlugin(), getSender(), "upgrade-confirm", placeholders);
                getPlugin().getData().getTransactionConfirmations().put(player.getUniqueId(), upgradeCost);
                BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                scheduler.scheduleSyncDelayedTask(getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        getPlugin().getData().getTransactionConfirmations().remove(player.getUniqueId());
                    }
                }, 200);
            } else {
                Message.send(getPlugin(), getSender(), "not-console-command");
            }


        }
    }
}
