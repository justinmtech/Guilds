package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;

public class CreateGuild extends SubCommand {

    public CreateGuild(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    public void execute() {
        Player player = (Player) getSender();
        String name = getArgs()[1];
        if (getPlugin().getData().getGuild(name) == null) {
            if (getPlugin().getData().getGuild(player.getUniqueId()) == null) {
                getPlugin().getData().createGuild(player.getUniqueId(), name);
                Message.send(getPlugin(), player, "create-guild");
            } else {
                Message.send(getPlugin(), getSender(), "already-in-guild");
            }
        } else {
            Message.send(getPlugin(), getSender(), "guild-already-exists");
        }
    }
}
