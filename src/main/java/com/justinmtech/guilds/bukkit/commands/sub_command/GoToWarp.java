package com.justinmtech.guilds.bukkit.commands.sub_command;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.bukkit.commands.SubCommand;
import com.justinmtech.guilds.core.Warp;
import com.justinmtech.guilds.bukkit.util.Message;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Optional;

public class GoToWarp extends SubCommand {

    public GoToWarp(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        Player player = (Player) getSender();
        String warpName = getArgs()[1];
        Optional<Warp> warp = getPlugin().getData().getWarp(player.getUniqueId(), warpName);
        if (warp.isPresent()) {
            teleportWarmup(player, warp.get());
        } else {
            Message.sendPlaceholder(getPlugin(), getSender(), "warp-error", warpName);
        }
    }

    private void teleportWarmup(Player player, Warp warp) {
        Location loc = player.getLocation();
        new BukkitRunnable() {
            int secondsLeft = 5;
            @Override
            public void run() {
                if (loc.getX() != player.getLocation().getX() ||
                        loc.getZ() != player.getLocation().getZ()) {
                    player.sendMessage("§cTeleportation cancelled due to movement!");
                    cancel();
                    return;
                }
                if (secondsLeft == 0) {
                    player.teleport(warp.toLocation());
                    Message.sendPlaceholder(getPlugin(), getSender(), "warp", warp.getId());
                    cancel();
                    return;
                }
                player.sendMessage("§6Teleporting in §e" + secondsLeft + " seconds§6...");
                secondsLeft--;
            }
        }.runTaskTimer(getPlugin(), 0L, 20L);
    }
}
