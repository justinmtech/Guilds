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

    private void execute() {
        Player player = (Player) getSender();
        Player player2;
        player2 = Bukkit.getPlayer(getArgs()[1]);
        Community community = getPlugin().getData().getCommunity(player.getUniqueId());
        boolean senderOwnsCommunity = community.isOwner(player.getUniqueId());
        boolean receiverNotInCommunity = !community.getMembers().contains(player2.getUniqueId());

        if (player2 != null && senderOwnsCommunity && receiverNotInCommunity) {
            getPlugin().getData().createInvite(player2.getUniqueId(), community.getName());
            Message.sendPlaceholder(getPlugin(), getSender(), "invite-send", player2.getName());
            Message.sendPlaceholder(getPlugin(), player2, "invite-receive", getSender().getName());
        } else {
            if (player2 == null) Message.sendPlaceholder(getPlugin(), getSender(), "player-not-found", player2.getName());
            if (!senderOwnsCommunity) Message.send(getPlugin(), getSender(), "must-be-owner");
            if (!receiverNotInCommunity) Message.sendPlaceholder(getPlugin(), getSender(), "player-already-in-community", player2.getName());
        }
    }
}
