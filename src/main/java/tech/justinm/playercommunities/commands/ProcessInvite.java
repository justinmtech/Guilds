package tech.justinm.playercommunities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.core.Community;

public class ProcessInvite implements CommandExecutor {
    private final PlayerCommunities plugin;

    public ProcessInvite(PlayerCommunities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("pcaccept")) {
            if (sender instanceof Player && args.length == 1) {
                Player player2 = (Player) sender;
                String communityName = args[0];
                boolean invited = plugin.getInvites().stream().anyMatch(i -> i.getReceiver().equals(player2) &&
                        i.getCommunity().getName().equalsIgnoreCase(communityName));
                boolean communityExists = plugin.getCommunities().stream().anyMatch(c -> c.getName().equalsIgnoreCase(communityName));

                if (invited && communityExists) {
                    Community community = plugin.getCommunities().stream()
                            .filter(c -> c.getName().equalsIgnoreCase(communityName)).findAny().orElseThrow(NullPointerException::new);
                    community.getMembers().add(player2);
                    player2.sendMessage("You joined " + community.getName() + "!");
                    community.getOwner().sendMessage(player2.getName() + " joined your community!");
                } else {
                    if (communityName == null) player2.sendMessage("Please use the correct syntax! /pcaccept <community>");
                    if (!invited) player2.sendMessage("You do not have a pending invite from " + communityName + ".");
                    if (!communityExists) player2.sendMessage(communityName + " does not exist anymore!");
                }
                return true;
                }
            }
        return false;
    }
}
