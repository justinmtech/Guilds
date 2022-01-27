package tech.justinm.playercommunities.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.SubCommand;
import tech.justinm.playercommunities.core.Community;

public class DeleteCommunity extends SubCommand {

    public DeleteCommunity(PlayerCommunities plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    public void execute() {
        Player player = (Player) getSender();
        String communityName = getArgs()[1];
        Community community = getPlugin().getData().getCommunity(communityName);
        if (community.getOwner().equals(player.getUniqueId())) {
            getPlugin().getData().deleteCommunity(communityName);
            player.sendMessage("You deleted your community!");
        } else {
            player.sendMessage("You must be the owner of a community to delete it!");
        }
    }
}
