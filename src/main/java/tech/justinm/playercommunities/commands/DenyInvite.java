package tech.justinm.playercommunities.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.SubCommand;
import tech.justinm.playercommunities.util.Message;

public class DenyInvite extends SubCommand {
    public DenyInvite(PlayerCommunities plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        if (getSender() instanceof Player) {
            Player player = (Player) getSender();
            String name = getArgs()[1];

            try {
                if (getPlugin().getData().getInvite(player.getUniqueId(), name) == null) {
                    Message.sendPlaceholder(getPlugin(), getSender(), "no-invite", name);
                } else {
                    getPlugin().getData().deleteInvite(player.getUniqueId(), name);
                    Message.sendPlaceholder(getPlugin(), getSender(), "invite-denied", name);
                }
            } catch (NullPointerException e) {
                Message.sendPlaceholder(getPlugin(), getSender(), "no-invite", name);
            }
        } else {
            Message.send(getPlugin(), getSender(), "not-console-command");
        }
    }
}
