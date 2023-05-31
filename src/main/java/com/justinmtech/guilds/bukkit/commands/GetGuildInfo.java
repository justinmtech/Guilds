package com.justinmtech.guilds.bukkit.commands;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.bukkit.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class GetGuildInfo extends SubCommand {

    public GetGuildInfo(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        if (getArgs().length == 1) {
            String guildName = getArgs()[0];
            Optional<Guild> guild = getPlugin().getData().getGuild(guildName);
            if (guild.isEmpty() || guild.get().getMembers().size() == 0)
                Message.sendPlaceholder(getPlugin(), getSender(), "guild-not-found", getArgs()[0]);
            else Message.sendGuildInfo(getPlugin(), getSender(), "messages.guild-info", guild.get());
        } else if (getArgs().length == 0 && getSender() instanceof Player) {
            Optional<Guild> guild = getPlugin().getData().getGuild(((Player) getSender()).getUniqueId());
            if (guild.isEmpty()) {
                Message.sendHelp(getPlugin(), getSender(), "help", "guild");
            } else {
                Message.sendGuildInfo(getPlugin(), getSender(), "messages.guild-info", guild.get());
            }
        } else if (getArgs().length == 1) {
            Player player = (Player) getSender();
            Optional<Guild> guild = getPlugin().getData().getGuild(player.getUniqueId());
            guild.ifPresent(value -> Message.sendGuildInfo(getPlugin(), getSender(), "messages.guild-info", value));
        }
    }
}
