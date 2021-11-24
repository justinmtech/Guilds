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

                try {
                    Player player = (Player) sender;
                    Player player2 = Bukkit.getPlayer(args[0]);
                    Community community = plugin.getCommunities().stream().filter(c -> c.isOwner(player)).findFirst().orElseThrow(null);

                    if (plugin.getCommunities().stream().anyMatch(c -> c.isOwner(player))) {
                        if (!plugin.getCommunities().stream().anyMatch(c -> c.containsMember(player2))) {
                            plugin.getInvites().add(new Invite(player, player2, community));
                            player.sendMessage("You sent " + player2.getName() + " an invite!");
                            player2.sendMessage("You received a community invite from " + player + "! Type /pcaccept <player> to accept.");
                        //plugin.getCommunities().stream().filter(c -> c.isOwner(player)).findAny().orElseThrow(NullPointerException::new).getMembers().add(player2);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }



        return false;
    }
}
