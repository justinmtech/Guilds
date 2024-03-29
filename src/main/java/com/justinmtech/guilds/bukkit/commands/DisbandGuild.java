package com.justinmtech.guilds.bukkit.commands;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.bukkit.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class DisbandGuild extends SubCommand {

    public DisbandGuild(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean execute() {
        Player player = (Player) getSender();
        try {
            Optional<Guild> guild = getPlugin().getData().getGuild(player.getUniqueId());
            if (guild.isEmpty()) {
                Message.send(getPlugin(), getSender(), "must-be-owner");
                return true;
            }

            if (guild.get().getOwner().equals(player.getUniqueId())) {
                getPlugin().getData().deleteGuild(guild.get().getName());
                Message.send(getPlugin(), getSender(), "disband-guild");
            } else {
                Message.send(getPlugin(), getSender(), "must-be-owner");
            }
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }
}
