package tech.justinm.playercommunities.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.SubCommand;
import tech.justinm.playercommunities.core.Community;

import java.util.Collections;
import java.util.List;

public class ListCommunities extends SubCommand {

    public ListCommunities(PlayerCommunities plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        Player player = (Player) getSender();
        List<Community> communityList = getPlugin().getData().getAllCommunities();
        Collections.sort(communityList);
        player.sendMessage(communityList.toString());
    }
}
