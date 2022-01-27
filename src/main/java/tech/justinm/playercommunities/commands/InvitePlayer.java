package tech.justinm.playercommunities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.SubCommand;
import tech.justinm.playercommunities.core.Community;

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
            player.sendMessage("You sent " + player2.getName() + " an invite!");
            player2.sendMessage("You received a community invite from " + player + "! Type /pcaccept <player> to accept.");
        } else {
            if (player2 == null) player.sendMessage("Player not found!");
            if (!senderOwnsCommunity) player.sendMessage("You must be a community owner to invite players!");
            if (!receiverNotInCommunity) player.sendMessage("That player is already in your community!");
        }
    }
}
