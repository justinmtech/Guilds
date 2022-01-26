package tech.justinm.playercommunities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.core.Community;

public class GoToWarp implements CommandExecutor {
    private final PlayerCommunities plugin;

    public GoToWarp(PlayerCommunities plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (label.equalsIgnoreCase("pc") && args.length == 2 &&
                args[0].equalsIgnoreCase("warp")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    String warpName = args[0];

                    Community community = plugin.getData().getCommunity(player.getUniqueId());
                    if (community.getWarps().containsKey(warpName)) {
                        player.teleport(community.getWarps().get(warpName));
                        player.sendMessage("You were teleported to " + warpName + "!");
                        return true;
                    } else {
                        player.sendMessage("Error!");
                        return false;
                    }
                }
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
