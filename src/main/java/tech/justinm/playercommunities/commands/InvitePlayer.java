package tech.justinm.playercommunities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.core.Community;

public class InvitePlayer implements CommandExecutor {
    private final PlayerCommunities plugin;

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
                Community community = plugin.getData().getCommunity(player.getUniqueId());
                boolean senderOwnsCommunity = community.isOwner(player.getUniqueId());
                boolean receiverNotInCommunity = !community.getMembers().contains(player2.getUniqueId());

                if (player2 != null && senderOwnsCommunity && receiverNotInCommunity) {
                    plugin.getData().createInvite(player2.getUniqueId(), community.getName());
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
