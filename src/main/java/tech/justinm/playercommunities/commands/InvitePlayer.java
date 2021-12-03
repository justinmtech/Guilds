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

    public InvitePlayer(PlayerCommunities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("pcinvite")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Player player2;
                player2 = Bukkit.getPlayer(args[0]);
                boolean senderOwnsCommunity = plugin.getCommunities().stream().anyMatch(c -> c.getOwner().equals(player));
                boolean receiverNotInCommunity = plugin.getCommunities().stream().anyMatch(c -> c.getMembers().contains(player2));

                if (player2 != null && senderOwnsCommunity && receiverNotInCommunity) {
                    Community community = plugin.getCommunities().stream().filter(c -> c.getOwner().equals(player)).findFirst().orElseThrow(NullPointerException::new);
                    plugin.getInvites().add(new Invite(player, player2, community));
                    player.sendMessage("You sent " + player2.getName() + " an invite!");
                    player2.sendMessage("You received a community invite from " + player + "! Type /pcaccept <player> to accept.");
                } else {
                    if (player2 == null) player.sendMessage("Player not found!");
                    if (!senderOwnsCommunity) player.sendMessage("You must be a community owner to invite players!");
                    if (!receiverNotInCommunity) player.sendMessage("That player is already in your community!");
                }
                return true;
            }
        }
        return false;
    }
}
