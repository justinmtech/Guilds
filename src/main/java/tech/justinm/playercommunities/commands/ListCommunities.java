package tech.justinm.playercommunities.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.SubCommand;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.util.Message;

import java.util.Collections;
import java.util.List;

public class ListCommunities extends SubCommand {

    public ListCommunities(PlayerCommunities plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private boolean execute() {
        List<Community> communityList = getPlugin().getData().getAllCommunities();
        Collections.sort(communityList);
        if (communityList.size() == 0) {
            Message.send(getPlugin(), getSender(), "no-communities");
            return false;
        }
        Message.send(getPlugin(), getSender(), "community-list-header");
        int listSize = Math.min(communityList.size(), 10);
        for (int i = 0; i < listSize; i++) {
            Community community = communityList.get(i);
            String[] placeholders = {String.valueOf(i + 1), community.getName(), String.valueOf(community.getMembers().size())};
            Message.sendCommunityInfo(getPlugin(), getSender(), "community-list-line", placeholders);
        }
        return true;
    }
}
