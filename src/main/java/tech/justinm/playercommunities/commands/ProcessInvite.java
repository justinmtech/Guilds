package tech.justinm.playercommunities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.SubCommand;
import tech.justinm.playercommunities.core.Community;

public class ProcessInvite extends SubCommand {

    public ProcessInvite(PlayerCommunities plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        Player player2 = (Player) getSender();
        String communityName = getArgs()[1];
        boolean invited = getPlugin().getData().getInvite(player2.getUniqueId()).equalsIgnoreCase(communityName);
        Community community = getPlugin().getData().getCommunity(communityName);
        boolean communityExists = getPlugin().getData().getCommunity(communityName) != null;

        if (invited && communityExists) {
            community.getMembers().add(player2.getUniqueId());
            player2.sendMessage("You joined " + community.getName() + "!");
            if (Bukkit.getPlayer(community.getOwner()) != null) Bukkit.getPlayer(community.getOwner()).sendMessage(player2.getName() + " joined your community!");
        } else {
            if (communityName == null) player2.sendMessage("Please use the correct syntax! /pcaccept <community>");
            if (!invited) player2.sendMessage("You do not have a pending invite from " + communityName + ".");
            if (!communityExists) player2.sendMessage(communityName + " does not exist anymore!");
        }
    }
}
