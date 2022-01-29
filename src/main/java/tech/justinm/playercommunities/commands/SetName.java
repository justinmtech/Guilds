package tech.justinm.playercommunities.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.SubCommand;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.core.Role;
import tech.justinm.playercommunities.util.Message;

public class SetName extends SubCommand {
    public SetName(PlayerCommunities plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        if (getSender() instanceof Player) {
            Player player = (Player) getSender();
            Community community = getPlugin().getData().getCommunity(player.getUniqueId());
            if (community == null) {
                Message.send(getPlugin(), getSender(), "not-in-community");
            } else if (community.getMembers().get(player.getUniqueId()) != Role.LEADER) {
                Message.send(getPlugin(), getSender(), "must-be-owner");
            } else if (getPlugin().getData().getCommunity(getArgs()[1]) != null) {
                Message.send(getPlugin(), getSender(), "community-already-exists");
            } else {
              community.setName(getArgs()[1]);
              Message.sendPlaceholder(getPlugin(), getSender(), "community-set-name", getArgs()[1]);
            }
        } else {
            Message.send(getPlugin(), getSender(), "not-console-command");
        }
    }
}
