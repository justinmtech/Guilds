package tech.justinm.playercommunities.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.SubCommand;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.util.Message;

public class DisbandCommunity extends SubCommand {

    public DisbandCommunity(PlayerCommunities plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    public boolean execute() {
        Player player = (Player) getSender();
        try {
            Community community = getPlugin().getData().getCommunity(player.getUniqueId());
            if (community.getOwner().equals(player.getUniqueId())) {
                getPlugin().getData().deleteCommunity(community.getName());
                Message.send(getPlugin(), getSender(), "disband-community");
            } else {
                Message.send(getPlugin(), getSender(), "must-be-owner");
            }
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }
}
