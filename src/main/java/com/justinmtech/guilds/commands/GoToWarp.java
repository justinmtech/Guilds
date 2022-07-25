package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.core.Warp;
import com.justinmtech.guilds.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;

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
            player.teleport(warp.get().toLocation());
            Message.sendPlaceholder(getPlugin(), getSender(), "warp", warpName);
        } else {
            Message.sendPlaceholder(getPlugin(), getSender(), "warp-error", warpName);
        }
    }
}
