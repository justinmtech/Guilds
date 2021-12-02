package tech.justinm.playercommunities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.core.Invite;

public class InvitePlayer implements CommandExecutor {
    private PlayerCommunities plugin;
    private Community community;
    private Player player;
    private Player player2;
    private String[] args;

    public InvitePlayer(PlayerCommunities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("pcinvite")) {
            if (sender instanceof Player) {
                player = (Player) sender;
                this.args = args;

                if (invitedPlayerExists() && isCommunityOwner() && playerNotInCommunity()) {
                    plugin.getInvites().add(new Invite(player, player2, community));
                    player.sendMessage("You sent " + player2.getName() + " an invite!");
                    player2.sendMessage("You received a community invite from " + player + "! Type /pcaccept <player> to accept.");
                }
                return true;
            }
        }
        return false;
    }

    private boolean isCommunityOwner() {
        try {
            community = plugin.getCommunities().stream().filter(c -> c.isOwner(player)).findAny().orElseThrow(NullPointerException::new);
            return true;
        } catch (NullPointerException e) {
            player2.sendMessage("You must be the owner of a community to invite a player!");
            return false;
        }
    }

    private boolean playerNotInCommunity() {
        if (!community.getMembers().contains(player2)) {
            return true;
        } else {
            player.sendMessage("That player is already in your community!");
            return false;
        }
    }

    private boolean invitedPlayerExists() {
        try {
            player2 = Bukkit.getPlayer(args[0]);
            return true;
        } catch (NullPointerException e) {
            player.sendMessage("That player does not exist!");
            return false;
        }
    }
}
