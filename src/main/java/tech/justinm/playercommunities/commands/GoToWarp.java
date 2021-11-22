package tech.justinm.playercommunities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.base.Community;
import tech.justinm.playercommunities.base.Warp;

public class GoToWarp implements CommandExecutor {
    private PlayerCommunities plugin;

    public GoToWarp(PlayerCommunities plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("pcwarp")) {
            Player player = (Player) sender;
            String warpName = args[0];

            Community community = plugin.getCommunities().stream().filter(c -> c.getMembers().contains(player)).findFirst().orElseThrow(null);
            if (community.getMembers().contains(player) && community.getWarps().stream().anyMatch(w -> w.getName().equalsIgnoreCase(warpName))) {
                Warp warp = community.getWarps().stream().filter(w -> w.getName().equalsIgnoreCase(warpName)).findFirst().orElseThrow(null);
                player.teleport(warp.getLocation());
                player.sendMessage("You were teleported to the warp!");
            } else {
                player.sendMessage("Error!");
            }
        }


        return false;
    }
}
