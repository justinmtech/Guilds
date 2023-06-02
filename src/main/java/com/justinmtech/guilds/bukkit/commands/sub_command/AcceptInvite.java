package com.justinmtech.guilds.bukkit.commands.sub_command;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.bukkit.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("UnusedReturnValue")
public class AcceptInvite extends SubCommand {

    public AcceptInvite(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private boolean execute() {
        Player player = (Player) getSender();
        return getPlugin().getGuildsService().acceptInvite(player.getUniqueId(), getArgs()[1]);
    }
}
