package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class Confirmation extends SubCommand {

    public Confirmation(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private void execute() {
        if (getSender() instanceof ConsoleCommandSender) return;
        Player player = (Player) getSender();
        if (!getPlugin().getCache().hasPendingTransaction(player.getUniqueId())) return;
        double amount = getPlugin().getCache().getPendingTransactionAmount(player.getUniqueId());
        Optional<Guild> guild = getPlugin().getData().getGuild(player.getUniqueId());
        if (guild.isEmpty()) {
            Message.send(getPlugin(), getSender(), "no-confirmation");
            return;
        }
        guild.get().setLevel(guild.get().getLevel() + 1);
        getPlugin().getData().saveGuild(guild.get());
        Guilds.getEcon().withdrawPlayer(player, amount);
        getPlugin().getCache().removeTransactionConfirmation(player.getUniqueId());
        Message.sendPlaceholder(getPlugin(), getSender(), "guild-level-up", String.valueOf(guild.get().getLevel()));
    }
}
