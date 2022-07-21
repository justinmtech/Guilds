package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.core.Guild;

import java.util.Optional;
import java.util.UUID;

public class LeaveGuild extends SubCommand {
    public LeaveGuild(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private boolean execute() {
        if (getSender() instanceof Player) {
            Player player = (Player) getSender();
            Optional<Guild> guild = getPlugin().getDb().getGuild(player.getUniqueId());

            if (guild.isEmpty()) {
                Message.send(getPlugin(), getSender(), "not-in-guild");
                return false;
            }

            if (guild.get().isOwner(player.getUniqueId())) {
                Message.send(getPlugin(), getSender(), "leave-guild-owner");
                return false;
            }
            guild.get().removeMember(player.getUniqueId());
            Message.sendPlaceholder(getPlugin(), getSender(), "leave-guild", guild.get().getName());
            return true;
        }
        return false;
    }
}
