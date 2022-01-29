package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;

public class CreateGuild extends SubCommand {

    public CreateGuild(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    public void execute() {
        Player player = (Player) getSender();
        String name = getArgs()[1];
        if (getPlugin().getData().getGuild(name) == null) {
            if (getPlugin().getData().getGuild(player.getUniqueId()) == null) {
                if (Guilds.getEcon().has(Bukkit.getOfflinePlayer(player.getUniqueId()), getPlugin().getConfig().getDouble("settings.guild-cost"))) {
                    Guilds.getEcon().withdrawPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), getPlugin().getConfig().getDouble("settings.guild-cost"));
                    getPlugin().getData().createGuild(player.getUniqueId(), name);
                    Message.send(getPlugin(), player, "create-guild");
                } else {
                    Message.sendPlaceholder(getPlugin(), getSender(), "guild-create-insufficient-funds", String.valueOf(getPlugin().getConfig().getDouble("settings.guild-cost")));
                }
            } else {
                Message.send(getPlugin(), getSender(), "already-in-guild");
            }
        } else {
            Message.send(getPlugin(), getSender(), "guild-already-exists");
        }
    }
}
