package tech.justinm.playercommunities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.core.Community;

public class GetCommunityInfo implements CommandExecutor {
    private PlayerCommunities plugin;

    public GetCommunityInfo(PlayerCommunities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("pcinfo")) {
            Player player = (Player) sender;

            try {
                String communityName = args[0];
                Community community = plugin.getCommunities().stream().filter(c -> c.getName().equalsIgnoreCase(communityName)).findAny().orElseThrow(NullPointerException::new);
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
