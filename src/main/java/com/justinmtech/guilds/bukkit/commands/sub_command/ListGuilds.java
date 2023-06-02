package com.justinmtech.guilds.bukkit.commands.sub_command;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.bukkit.commands.SubCommand;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.bukkit.util.Message;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ListGuilds extends SubCommand {

    public ListGuilds(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    @SuppressWarnings("UnusedReturnValue")
    private boolean execute() {
        List<Guild> guildList = getPlugin().getGuildsRepository().getAllGuilds();
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
