package tech.justinm.playercommunities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.base.Community;
import tech.justinm.playercommunities.base.Warp;

public class SetCommunityWarp implements CommandExecutor {
    private PlayerCommunities plugin;

    public SetCommunityWarp(PlayerCommunities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("pcsetwarp")) {
            if (sender instanceof Player) {
            Player player = (Player) sender;

                String warpName = args[0];
                Player owner = plugin.getCommunities().stream().filter(c -> c.getName().equals(warpName)).findFirst().orElseThrow(null).getOwner();
                if (player.equals(owner)) {
                    plugin.getCommunities()
                            .stream().filter(c -> c.getOwner().equals(owner))
                            .findFirst().orElseThrow(null)
                            .getWarps().add(new Warp(warpName, player.getLocation()));
                    player.sendMessage("You set a warp called " + warpName + " at your location!");
                } else {
                    player.sendMessage("Something went wrong!");
                }
            }
        }
        return false;
    }
}
