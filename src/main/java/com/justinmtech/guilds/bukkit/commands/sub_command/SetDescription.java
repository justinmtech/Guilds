package com.justinmtech.guilds.bukkit.commands.sub_command;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.bukkit.commands.SubCommand;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.bukkit.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SetDescription extends SubCommand {

    public SetDescription(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        Player player = (Player) getSender();
        Optional<Guild> guild = getPlugin().getData().getGuild(player.getUniqueId());
        if (guild.isEmpty()) {
            Message.send(getPlugin(), getSender(), "generic-error");
            return;
        }
            try {
                StringBuilder desc = new StringBuilder();
                for (String arg : getArgs()) {
                    if (arg.equals("setdesc")) continue;
                    desc.append(arg).append(" ");
                }
                guild.get().setDescription(desc.toString().trim());
                Message.sendPlaceholder(getPlugin(), getSender(), "set-description", desc.toString());
            } catch (NullPointerException e) {
                e.printStackTrace();
                Message.send(getPlugin(), getSender(), "generic-error");
            }
        }
}
