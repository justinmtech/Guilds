package com.justinmtech.guilds.bukkit.commands.sub_command;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.bukkit.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Confirmation extends SubCommand {

    public Confirmation(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        if (getSender() instanceof ConsoleCommandSender) return;
        Player player = (Player) getSender();
        getPlugin().getGuildsService().confirmation(player.getUniqueId());
    }
}
