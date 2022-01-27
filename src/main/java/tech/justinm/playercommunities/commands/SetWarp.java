package tech.justinm.playercommunities.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.SubCommand;
import tech.justinm.playercommunities.core.Community;

public class SetWarp extends SubCommand {

    public SetWarp(PlayerCommunities plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        Player player = (Player) getSender();
        try {
            String warpName = getArgs()[1];
            Community community = getPlugin().getData().getCommunity(player.getUniqueId());
            if (community.isOwner(player.getUniqueId())) {
                community.getWarps().put(warpName, player.getLocation());
                player.sendMessage("You set a warp called " + warpName + " at your location!");
            } else {
                player.sendMessage("Something went wrong!");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            player.sendMessage("Something went wrong!");
        }
    }
}
