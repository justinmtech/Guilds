package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.core.Guild;

public class DisbandGuild extends SubCommand {

    public DisbandGuild(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    public boolean execute() {
        Player player = (Player) getSender();
        try {
            Guild guild = getPlugin().getData().getGuild(player.getUniqueId());
            if (guild.getOwner().equals(player.getUniqueId())) {
                getPlugin().getData().deleteGuild(guild.getName());
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
