package com.justinmtech.guilds;

import org.bukkit.command.CommandSender;

public class SubCommand {
    private final Guilds plugin;
    private final CommandSender sender;
    private final String[] args;

    public SubCommand(Guilds plugin, CommandSender sender, String[] args) {
        this.plugin = plugin;
        this.sender = sender;
        this.args = args;
    }

    public Guilds getPlugin() {
        return plugin;
    }

    public CommandSender getSender() {
        return sender;
    }

    public String[] getArgs() {
        return args;
    }
}
