package com.justinmtech.guilds.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.util.Message;

public class AcceptInvite extends SubCommand {

    public AcceptInvite(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private boolean execute() {
        Player player2 = (Player) getSender();
        String guildName = getArgs()[1];
        boolean noGuild = getPlugin().getData().getGuild(player2.getUniqueId()) == null;
        try {
            boolean invited = getPlugin().getData().getInvite(player2.getUniqueId(), guildName) != null;
        } catch (NullPointerException e) {
            Message.sendPlaceholder(getPlugin(), getSender(), "no-invite", guildName);
            return false;
        }
        Guild guild = getPlugin().getData().getGuild(guildName);
        boolean guildExists = getPlugin().getData().getGuild(guildName) != null;

        if (guildExists && noGuild) {
            getPlugin().getData().addMember(player2.getUniqueId(), guildName);
            Message.sendPlaceholder(getPlugin(), getSender(), "invite-accepted", guild.getName());
            getPlugin().getData().deleteInvite(player2.getUniqueId(), guildName);
            if (Bukkit.getPlayer(guild.getOwner()) != null) {
                Message.sendPlaceholder(getPlugin(), Bukkit.getPlayer(guild.getOwner()), "player-joined-guild", player2.getName());
            }
        } else {
            if (!guildExists) player2.sendMessage(guildName + " does not exist anymore!");
            else Message.send(getPlugin(), getSender(), "already-in-guild");
        }
        return true;
    }
}
