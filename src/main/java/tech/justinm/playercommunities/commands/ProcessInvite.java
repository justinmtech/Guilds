package tech.justinm.playercommunities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.SubCommand;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.util.Message;

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
            Message.sendPlaceholder(getPlugin(), getSender(), "invite-accepted", community.getName());
            if (Bukkit.getPlayer(community.getOwner()) != null) Message.sendPlaceholder(getPlugin(), Bukkit.getPlayer(community.getOwner()), "player-joined-community", getSender().getName());
        } else {
            if (!communityExists) player2.sendMessage(communityName + " does not exist anymore!");
            else Message.sendPlaceholder(getPlugin(), getSender(), "no-invite", communityName);
        }
    }
}
