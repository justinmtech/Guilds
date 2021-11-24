package tech.justinm.playercommunities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.core.Invite;

import java.util.List;

public class ProcessInvite implements CommandExecutor {
    private PlayerCommunities plugin;

    public ProcessInvite(PlayerCommunities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("pcaccept")) {
            if (sender instanceof Player) {
                Player player2 = (Player) sender;
                List<Invite> invites = plugin.getInvites();

                if (invites.contains(player2)) {
                    Invite invite = invites.stream().filter(i -> i.getReceiver().equals(player2)).findAny().orElseThrow(NullPointerException::new);
                    plugin.getCommunities()
                            .stream().filter(c -> c.getName().equals(invite.getCommunity()))
                            .findAny().orElseThrow(NullPointerException::new).getMembers().add(player2);
                    player2.sendMessage("You joined the " + invite.getCommunity().getName() + " community!");
                    invite.getSender().sendMessage(player2.getName() + " joined your community!");
                }
            }
        }
        return false;
    }
}
