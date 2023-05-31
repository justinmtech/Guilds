package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.core.*;
import com.justinmtech.guilds.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
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
            if (gPlayer.isEmpty() || !gPlayer.get().hasGuild()) {
                if (Guilds.getEcon().has(Bukkit.getOfflinePlayer(player.getUniqueId()), getPlugin().getConfig().getDouble("settings.guild-cost"))) {
                    Guilds.getEcon().withdrawPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), getPlugin().getConfig().getDouble("settings.guild-cost"));
                    createGuild(player, name);
                } else {
                    String cost = String.valueOf(getPlugin().getConfig().getDouble("settings.guild-cost"));
                    double amount = Double.parseDouble(cost);
                    DecimalFormat formatter = new DecimalFormat("#,###");
                    Message.sendPlaceholder(getPlugin(), getSender(), "guild-create-insufficient-funds",
                            formatter.format(amount));
                }
            } else {
                Message.send(getPlugin(), getSender(), "already-in-guild");
            }
        } else {
            Message.send(getPlugin(), getSender(), "guild-already-exists");
        }
    }

    private void createGuild(Player player, String name) {
        getPlugin().getData().saveGuild(new GuildImp(player.getUniqueId(), name));
        getPlugin().getData().savePlayer(new GPlayerImp(player.getUniqueId(), name, Role.LEADER));
        Message.send(getPlugin(), player, "create-guild");
    }
}
