package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.core.Guild;

public class GetGuildInfo extends SubCommand {

    public GetGuildInfo(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        Guild guild = null;
        try {
            String guildName = getArgs()[0];
            guild = getPlugin().getData().getGuild(guildName);
        } catch (NullPointerException e) {
            Message.sendPlaceholder(getPlugin(), getSender(), "guild-not-found", getArgs()[0]);
        }
        Message.sendGuildInfo(getPlugin(), getSender(), "messages.guild-info", guild);

    }
}
