package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.core.Role;
import com.justinmtech.guilds.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.core.Guild;

public class InvitePlayer extends SubCommand {

    public InvitePlayer(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private boolean execute() {
        Player player = (Player) getSender();
        Player player2;
        player2 = Bukkit.getPlayer(getArgs()[1]);
        Guild guild = getPlugin().getData().getGuild(player.getUniqueId());

        if (guild == null) {
            Message.send(getPlugin(), getSender(), "must-be-owner");
            return false;
        }

        if (player2 == null) {
            Message.sendPlaceholder(getPlugin(), getSender(), "player-not-found", getArgs()[1]);
            return false;
        }

        if (player.getName().equalsIgnoreCase(getArgs()[1])) {
            Message.send(getPlugin(), getSender(), "self-invite-error");
            return false;
        }

        if (guild.getMembers().get(player.getUniqueId()) != Role.LEADER) {
            Message.send(getPlugin(), getSender(), "must-be-owner");
            return false;
        }

        if (guild.getMembers().get(player2.getUniqueId()) != null) {
            Message.sendPlaceholder(getPlugin(), getSender(), "player-already-in-guild", player2.getName());
            return false;
        }

        getPlugin().getData().createInvite(player2.getUniqueId(), guild.getName());
        Message.sendPlaceholder(getPlugin(), getSender(), "invite-send", player2.getName());
        Message.sendPlaceholder(getPlugin(), player2, "invite-receive", guild.getName());
        return true;
    }
}
