package tech.justinm.playercommunities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.core.Community;

public class GetCommunityInfo implements CommandExecutor {
    private final PlayerCommunities plugin;

    public GetCommunityInfo(PlayerCommunities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("pc") &&
                args.length == 2 && args[0].equalsIgnoreCase("info")) {
            Player player = (Player) sender;

            try {
                String communityName = args[1];
                Community community = plugin.getData().getCommunity(communityName);
                player.sendMessage(community.toString());
                return true;

            } catch (NullPointerException e) {
                e.printStackTrace();
                player.sendMessage("An error occurred!");
            }
        }


        return false;
    }
}
