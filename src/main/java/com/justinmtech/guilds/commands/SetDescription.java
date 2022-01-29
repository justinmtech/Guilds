package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;

public class SetDescription extends SubCommand {

    public SetDescription(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        Player player = (Player) getSender();
        try {
            StringBuilder desc = new StringBuilder();
            for (String arg : getArgs()) {
                desc.append(arg).append(" ");
            }
            getPlugin().getData().getGuild(player.getUniqueId()).setDescription(desc.toString().trim());
            Message.sendPlaceholder(getPlugin(), getSender(), "set-description", desc.toString());
        } catch (NullPointerException e) {
            e.printStackTrace();
            Message.send(getPlugin(), getSender(), "generic-error");
        }
    }
}
