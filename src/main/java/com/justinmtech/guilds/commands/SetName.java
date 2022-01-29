package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.core.Role;
import com.justinmtech.guilds.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.core.Guild;

public class SetName extends SubCommand {
    public SetName(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        if (getSender() instanceof Player) {
            Player player = (Player) getSender();
            Guild guild = getPlugin().getData().getGuild(player.getUniqueId());
            if (guild == null) {
                Message.send(getPlugin(), getSender(), "not-in-guild");
            } else if (guild.getMembers().get(player.getUniqueId()) != Role.LEADER) {
                Message.send(getPlugin(), getSender(), "must-be-owner");
            } else if (getPlugin().getData().getGuild(getArgs()[1]) != null) {
                Message.send(getPlugin(), getSender(), "guild-already-exists");
            } else {
              guild.setName(getArgs()[1]);
              Message.sendPlaceholder(getPlugin(), getSender(), "guild-set-name", getArgs()[1]);
            }
        } else {
            Message.send(getPlugin(), getSender(), "not-console-command");
        }
    }
}
