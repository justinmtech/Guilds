package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;

import java.util.NoSuchElementException;

public class SetDescription extends SubCommand {

    public SetDescription(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        Player player = (Player) getSender();
        try {
            StringBuilder desc = new StringBuilder();
            for (String arg : getArgs()) desc.append(arg).append(" ");
            try {
                getPlugin().getData().getGuild(player.getUniqueId()).orElseThrow().setDescription(desc.toString().trim());
            } catch (NoSuchElementException ignored) {
                Message.send(getPlugin(), getSender(), "generic-error");
            }
            Message.sendPlaceholder(getPlugin(), getSender(), "set-description", desc.toString());
        } catch (NullPointerException e) {
            e.printStackTrace();
            Message.send(getPlugin(), getSender(), "generic-error");
        }
    }
}
