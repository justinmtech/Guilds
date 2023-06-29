package com.justinmtech.guilds.bukkit.commands;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.bukkit.util.Message;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.core.GuildImp;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SetTag extends SubCommand {

    public SetTag(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        Player player = (Player) getSender();
        try {
            String tagName = getArgs()[1];
            if (tagName.length() > GuildImp.MAX_TAG_LENGTH) {
                Message.sendPlaceholder(getPlugin(), getSender(), "tag-too-long", String.valueOf(GuildImp.MAX_TAG_LENGTH));
                return;
            }

            Optional<Guild> guild = getPlugin().getData().getGuild(player.getUniqueId());
            if (guild.isEmpty()) {
                Message.send(getPlugin(), getSender(), "must-be-owner");
                return;
            }
            if (guild.get().isOwner(player.getUniqueId())) {
                Message.sendPlaceholder(getPlugin(), getSender(), "set-tag", tagName);
                guild.get().setTag(tagName);
                getPlugin().getData().saveGuild(guild.get());
            } else {
                Message.send(getPlugin(), getSender(), "must-be-owner");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Message.send(getPlugin(), getSender(), "generic-error");
        }
    }
}
