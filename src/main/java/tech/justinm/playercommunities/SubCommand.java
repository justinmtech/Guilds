package tech.justinm.playercommunities;

import org.bukkit.command.CommandSender;

public class SubCommand {
    private final PlayerCommunities plugin;
    private final CommandSender sender;
    private final String[] args;

    public SubCommand(PlayerCommunities plugin, CommandSender sender, String[] args) {
        this.plugin = plugin;
        this.sender = sender;
        this.args = args;
    }

    public PlayerCommunities getPlugin() {
        return plugin;
    }

    public CommandSender getSender() {
        return sender;
    }

    public String[] getArgs() {
        return args;
    }
}
