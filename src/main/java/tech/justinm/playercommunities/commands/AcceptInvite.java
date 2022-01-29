package tech.justinm.playercommunities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.SubCommand;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.util.Message;

public class AcceptInvite extends SubCommand {

    public AcceptInvite(PlayerCommunities plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private boolean execute() {
        Player player2 = (Player) getSender();
        String communityName = getArgs()[1];
        boolean noCommunity = getPlugin().getData().getCommunity(player2.getUniqueId()) == null;
        boolean invited = getPlugin().getData().getInvite(player2.getUniqueId()).equalsIgnoreCase(communityName);
        Community community = getPlugin().getData().getCommunity(communityName);
        boolean communityExists = getPlugin().getData().getCommunity(communityName) != null;

        if (invited && communityExists && noCommunity) {
            getPlugin().getData().addMember(player2.getUniqueId(), communityName);
            Message.sendPlaceholder(getPlugin(), getSender(), "invite-accepted", community.getName());
            if (Bukkit.getPlayer(community.getOwner()) != null) {
                Message.sendPlaceholder(getPlugin(), Bukkit.getPlayer(community.getOwner()), "player-joined-community", player2.getName());
            }
        } else {
            if (!communityExists) player2.sendMessage(communityName + " does not exist anymore!");
            else if (!invited) Message.sendPlaceholder(getPlugin(), getSender(), "no-invite", communityName);
            else Message.send(getPlugin(), getSender(), "already-in-community");
        }
        return true;
    }
}
