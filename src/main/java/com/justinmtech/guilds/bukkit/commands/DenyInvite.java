package com.justinmtech.guilds.bukkit.commands;

import com.justinmtech.guilds.bukkit.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;

public class DenyInvite extends SubCommand {
    public DenyInvite(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        if (getSender() instanceof Player) {
            Player player = (Player) getSender();
            String name = getArgs()[1];

            try {
                if (!getPlugin().getData().hasInvite(player.getUniqueId(), name)) {
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
