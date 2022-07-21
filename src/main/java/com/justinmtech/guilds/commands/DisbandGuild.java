package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.core.Guild;

import java.util.Optional;

public class DisbandGuild extends SubCommand {

    public DisbandGuild(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    public boolean execute() {
        Player player = (Player) getSender();
        try {
            Optional<Guild> guild = getPlugin().getDb().getGuild(player.getUniqueId());
            if (guild.isEmpty()) {
                Message.send(getPlugin(), getSender(), "must-be-owner");
                return true;
            }

            if (guild.get().getOwner().equals(player.getUniqueId())) {
                getPlugin().getDb().deleteGuild(guild.get().getName());
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
