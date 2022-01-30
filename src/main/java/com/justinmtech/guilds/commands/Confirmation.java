package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Confirmation extends SubCommand {

    public Confirmation(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        if (getSender() instanceof Player) {
            Player player = (Player) getSender();
            if (getPlugin().getData().getTransactionConfirmations().get(player.getUniqueId()) != null) {
                Guild guild = getPlugin().getData().getGuild(player.getUniqueId());
                guild.setLevel(guild.getLevel() + 1);
                Message.sendPlaceholder(getPlugin(), getSender(), "guild-level-up", String.valueOf(guild.getLevel()));
            } else {
                Message.send(getPlugin(), getSender(), "no-confirmation");
            }
        }
    }
}
