package com.justinmtech.guilds.bukkit.commands.sub_command;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.bukkit.commands.SubCommand;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.core.Role;
import com.justinmtech.guilds.bukkit.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class InvitePlayer extends SubCommand {

    public InvitePlayer(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    //TODO Test
    @SuppressWarnings("UnusedReturnValue")
    private boolean execute() {
        Player player = (Player) getSender();
        Player player2;
        player2 = Bukkit.getPlayer(getArgs()[1]);
        Optional<Guild> guild = getPlugin().getData().getGuild(player.getUniqueId());

        if (guild.isEmpty()) {
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

        if (guild.get().getMembers().get(player.getUniqueId()) != Role.LEADER && guild.get().getMembers().get(player.getUniqueId()) != Role.MOD) {
            Message.send(getPlugin(), getSender(), "must-be-mod");
            return false;
        }

        if (guild.get().getMembers().containsKey(player2.getUniqueId())) {
            Message.sendPlaceholder(getPlugin(), getSender(), "player-already-in-guild", player2.getName());
            return false;
        }

        getPlugin().getData().saveInvite(player2.getUniqueId(), guild.get().getName());
        Message.sendPlaceholder(getPlugin(), getSender(), "invite-send", player2.getName());
        Message.sendPlaceholder(getPlugin(), player2, "invite-receive", guild.get().getName());
        return true;
    }
}
