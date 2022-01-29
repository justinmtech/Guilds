package tech.justinm.playercommunities.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.SubCommand;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.util.Message;

public class LeaveCommunity extends SubCommand {
    public LeaveCommunity(PlayerCommunities plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private boolean execute() {
        if (getSender() instanceof Player) {
            Player player = (Player) getSender();
            Community community = getPlugin().getData().getCommunity(player.getUniqueId());

            if (community == null) {
                Message.send(getPlugin(), getSender(), "not-in-community");
                return false;
            }

            if (community.isOwner(player.getUniqueId())) {
                Message.send(getPlugin(), getSender(), "leave-community-owner");
                return false;
            }

            getPlugin().getData().removeMember(player.getUniqueId());
            Message.sendPlaceholder(getPlugin(), getSender(), "leave-community", community.getName());
            return true;
        }
        return false;
    }
}
