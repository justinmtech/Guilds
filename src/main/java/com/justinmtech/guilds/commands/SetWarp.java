package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.core.Warp;
import com.justinmtech.guilds.util.Message;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.core.Guild;

import java.util.Objects;
import java.util.Optional;

public class SetWarp extends SubCommand {

    public SetWarp(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    //TODO Warp saving/getting
    private void execute() {
        Player player = (Player) getSender();
        try {
            String warpName = getArgs()[1];
            Optional<Guild> guild = getPlugin().getDb().getGuild(player.getUniqueId());
            if (guild.isEmpty()) {
                Message.send(getPlugin(), getSender(), "must-be-owner");
                return;
            }
            if (guild.get().isOwner(player.getUniqueId())) {
                Location loc = player.getLocation();
                getPlugin().getDb().saveWarp(warpName,
                        Objects.requireNonNull(loc.getWorld()).getName(),
                        loc.getX(), loc.getY(), loc.getZ(),
                        loc.getYaw(), loc.getPitch(),
                        guild.get().getName());
                Message.sendPlaceholder(getPlugin(), getSender(), "set-warp", warpName);
            } else {
                Message.send(getPlugin(), getSender(), "must-be-owner");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Message.send(getPlugin(), getSender(), "generic-error");
        }
    }
}
