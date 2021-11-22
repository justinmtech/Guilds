package tech.justinm.playercommunities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.base.Community;

import java.util.Collections;
import java.util.List;

public class ListCommunities implements CommandExecutor {
    private final PlayerCommunities plugin;

    public ListCommunities(PlayerCommunities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("pclist")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                List<Community> communityList = plugin.getCommunities();
                Collections.sort(communityList);
                player.sendMessage(communityList.toString());
                return true;
            }
        }

        return false;
    }
}
