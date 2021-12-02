package tech.justinm.playercommunities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.core.Invite;

public class ProcessInvite implements CommandExecutor {
    private PlayerCommunities plugin;
    private Invite invite;
    private Community community;
    private Player player2;
    private String communityName;

    public ProcessInvite(PlayerCommunities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("pcaccept")) {
            if (sender instanceof Player) {
                player2 = (Player) sender;
                communityName = args[0];

                if (inviteExists() && communityExists()) {
                    community.getMembers().add(player2);
                    player2.sendMessage("You joined " + community.getName() + "!");
                    invite.getSender().sendMessage(player2.getName() + " joined your community!");
                } else {
                    player2.sendMessage("That community does not exist anymore!");
                }
                return true;
                }
            }
        return false;
    }

    private boolean inviteExists() {
        try {
            invite = plugin.getInvites().stream().filter(i -> i.getReceiver().equals(player2) &&
                    i.getCommunity().getName().equalsIgnoreCase(communityName)).findAny().orElseThrow(NullPointerException::new);
            return true;
        } catch (NullPointerException e) {
            player2.sendMessage("No invite found from that community!");
            return false;
        }
    }

    private boolean communityExists() {
        try {
            community = invite.getCommunity();
            return plugin.getCommunities().stream().anyMatch(c -> c.equals(community));
        } catch (NullPointerException e) {
            player2.sendMessage(communityName + " not found!");
            return false;
        }
    }
}
