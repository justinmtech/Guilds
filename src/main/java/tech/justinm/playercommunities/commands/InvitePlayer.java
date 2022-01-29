package tech.justinm.playercommunities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.SubCommand;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.util.Message;

public class InvitePlayer extends SubCommand {

    public InvitePlayer(PlayerCommunities plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private boolean execute() {
        Player player = (Player) getSender();
        Player player2;
        player2 = Bukkit.getPlayer(getArgs()[1]);
        Community community = getPlugin().getData().getCommunity(player.getUniqueId());

        if (community == null) {
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

        if (!community.isOwner(player.getUniqueId())) {
            Message.send(getPlugin(), getSender(), "must-be-owner");
            return false;
        }

        if (community.getMembers().contains(player2.getUniqueId())) {
            Message.sendPlaceholder(getPlugin(), getSender(), "player-already-in-community", player2.getName());
            return false;
        }

        getPlugin().getData().createInvite(player2.getUniqueId(), community.getName());
        Message.sendPlaceholder(getPlugin(), getSender(), "invite-send", player2.getName());
        Message.sendPlaceholder(getPlugin(), player2, "invite-receive", community.getName());
        return true;
    }
}
