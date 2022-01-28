package tech.justinm.playercommunities.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.SubCommand;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.util.Message;

public class GoToWarp extends SubCommand {

    public GoToWarp(PlayerCommunities plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        try {
            Player player = (Player) getSender();
            String warpName = getArgs()[1];

            Community community = getPlugin().getData().getCommunity(player.getUniqueId());
            if (community.getWarps().containsKey(warpName)) {
                player.teleport(community.getWarps().get(warpName));
                Message.sendPlaceholder(getPlugin(), getSender(), "warp", warpName);
            } else {
                Message.sendPlaceholder(getPlugin(), getSender(), "warp-error", warpName);
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }
}
