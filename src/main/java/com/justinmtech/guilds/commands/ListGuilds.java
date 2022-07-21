package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.util.Message;
import org.bukkit.command.CommandSender;
import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.core.Guild;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ListGuilds extends SubCommand {

    public ListGuilds(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private boolean execute() {
        List<Guild> guildList = getPlugin().getDb().getAllGuilds();
        Collections.sort(guildList);
        if (guildList.size() == 0) {
            Message.send(getPlugin(), getSender(), "no-guilds");
            return false;
        }
        Message.send(getPlugin(), getSender(), "guild-list-header");
        int listSize = Math.min(guildList.size(), 10);
        for (int i = 0; i < listSize; i++) {
            Guild guild = guildList.get(i);
            String[] placeholders = {String.valueOf(i + 1), guild.getName(), String.valueOf(guild.getMembers().size())};
            Message.sendGuildList(getPlugin(), getSender(), "guild-list-line", placeholders);
        }
        return true;
    }
}
