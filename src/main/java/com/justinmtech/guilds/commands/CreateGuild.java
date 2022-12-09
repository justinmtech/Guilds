package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.core.GPlayer;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.core.Role;
import com.justinmtech.guilds.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;

import java.util.Optional;

public class CreateGuild extends SubCommand {

    public CreateGuild(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    public void execute() {
        Player player = (Player) getSender();
        String name = getArgs()[1];
        Optional<Guild> guild = getPlugin().getData().getGuild(name);
        Optional<GPlayer> gPlayer = getPlugin().getData().getPlayer(player.getUniqueId());
        if (guild.isEmpty()) {
            if (gPlayer.isEmpty()) {
                if (Guilds.getEcon().has(Bukkit.getOfflinePlayer(player.getUniqueId()), getPlugin().getConfig().getDouble("settings.guild-cost"))) {
                    Guilds.getEcon().withdrawPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), getPlugin().getConfig().getDouble("settings.guild-cost"));
                    getPlugin().getData().saveGuild(new Guild(player.getUniqueId(), name));
                    getPlugin().getData().savePlayer(new GPlayer(player.getUniqueId(), name, Role.LEADER));
                    Message.send(getPlugin(), player, "create-guild");
                } else {
                    Message.sendPlaceholder(getPlugin(), getSender(), "guild-create-insufficient-funds",
                            String.valueOf(getPlugin().getConfig().getDouble("settings.guild-cost")));
                }
            } else {
                Message.send(getPlugin(), getSender(), "already-in-guild");
            }
        } else {
            Message.send(getPlugin(), getSender(), "guild-already-exists");
        }
    }
}
