package tech.justinm.playercommunities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.core.Warp;

//TODO error handling

public class SetWarp implements CommandExecutor {
    private PlayerCommunities plugin;

    public SetWarp(PlayerCommunities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (label.equalsIgnoreCase("pcsetwarp")) {
                if (sender instanceof Player) {
                Player player = (Player) sender;
                try {
                    String warpName = args[0];
                    Community community = plugin.getData().getCommunity(player.getUniqueId());
                    if (community.isOwner(player.getUniqueId())) {
                        community.getWarps().put(warpName, player.getLocation());
                        player.sendMessage("You set a warp called " + warpName + " at your location!");
                    } else {
                        player.sendMessage("Something went wrong!");
                    }
                    return true;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    player.sendMessage("Something went wrong!");
                }
            }
        }
            return false;
    }
}
