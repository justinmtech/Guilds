package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.core.Guild;

public class GoToWarp extends SubCommand {

    public GoToWarp(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        try {
            Player player = (Player) getSender();
            String warpName = getArgs()[1];

            Guild guild = getPlugin().getData().getGuild(player.getUniqueId());
            if (guild.getWarps().containsKey(warpName)) {
                player.teleport(guild.getWarps().get(warpName));
                Message.sendPlaceholder(getPlugin(), getSender(), "warp", warpName);
            } else {
                Message.sendPlaceholder(getPlugin(), getSender(), "warp-error", warpName);
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }
}
