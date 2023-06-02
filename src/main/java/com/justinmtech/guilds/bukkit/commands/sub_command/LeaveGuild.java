package com.justinmtech.guilds.bukkit.commands.sub_command;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.bukkit.commands.SubCommand;
import com.justinmtech.guilds.core.GPlayer;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.bukkit.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class LeaveGuild extends SubCommand {
    public LeaveGuild(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    @SuppressWarnings("UnusedReturnValue")
    private boolean execute() {
        if (getSender() instanceof Player) {
            Player player = (Player) getSender();
            Optional<GPlayer> gPlayer = getPlugin().getGuildsRepository().getPlayer(player.getUniqueId());
            Optional<Guild> guild = getPlugin().getGuildsRepository().getGuild(player.getUniqueId());

            if (guild.isEmpty() || gPlayer.isEmpty()) {
                Message.send(getPlugin(), getSender(), "not-in-guild");
                return false;
            }

            if (guild.get().isOwner(player.getUniqueId())) {
                Message.send(getPlugin(), getSender(), "leave-guild-owner");
                return false;
            }
            getPlugin().getGuildsService().leaveGuild(guild.get(), gPlayer.get());
            Message.sendPlaceholder(getPlugin(), getSender(), "leave-guild", guild.get().getName());
            return true;
        }
        return false;
    }
}
