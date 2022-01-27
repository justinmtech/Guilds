package tech.justinm.playercommunities.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.SubCommand;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.util.Message;

public class GetCommunityInfo extends SubCommand {

    public GetCommunityInfo(PlayerCommunities plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        Player player = (Player) getSender();
        try {
            String communityName = getArgs()[0];
            Community community = getPlugin().getData().getCommunity(communityName);
            player.sendMessage(community.toString());
        } catch (NullPointerException e) {
            e.printStackTrace();
            Message.sendPlaceholder(getPlugin(), getSender(), "community-not-found", getArgs()[0]);
            }
    }
}
