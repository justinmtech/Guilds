package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.util.Message;
import org.bukkit.command.CommandSender;
import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.core.Guild;

import java.util.Optional;

public class GetGuildInfo extends SubCommand {

    public GetGuildInfo(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        String guildName = getArgs()[0];
        Optional<Guild> guild = getPlugin().getData().getGuild(guildName);
        if (guild.isEmpty()) {
            Message.sendPlaceholder(getPlugin(), getSender(), "guild-not-found", getArgs()[0]);
        } else Message.sendGuildInfo(getPlugin(), getSender(), "messages.guild-info", guild.get());

    }
}
