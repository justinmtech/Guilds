package com.justinmtech.guilds.bukkit.commands.subcommand;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.bukkit.commands.SubCommand;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.bukkit.util.Message;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Optional;

public class SetWarp extends SubCommand {

    public SetWarp(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        Player player = (Player) getSender();
        try {
            String warpName = getArgs()[1];
            Optional<Guild> guild = getPlugin().getData().getGuild(player.getUniqueId());
            if (guild.isEmpty()) {
                Message.send(getPlugin(), getSender(), "must-be-owner");
                return;
            }
            if (guild.get().isOwner(player.getUniqueId())) {
                Location loc = player.getLocation();
                getPlugin().getData().saveWarp(warpName,
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
