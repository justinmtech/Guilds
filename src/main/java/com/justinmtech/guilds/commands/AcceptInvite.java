package com.justinmtech.guilds.commands;

import com.justinmtech.guilds.core.GPlayer;
import com.justinmtech.guilds.core.Role;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.SubCommand;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.util.Message;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("UnusedReturnValue")
public class AcceptInvite extends SubCommand {

    public AcceptInvite(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    //TODO Test
    private boolean execute() {
        Player player2 = (Player) getSender();
        String guildName = getArgs()[1];
        boolean noGuild = getPlugin().getDb().getGuild(player2.getUniqueId()).isEmpty();
        boolean invited = getPlugin().getDb().hasInvite(player2.getUniqueId(), guildName);
        if (!invited) {
            Message.sendPlaceholder(getPlugin(), getSender(), "no-invite", guildName);
            return false;
        }
        Optional<Guild> guild = getPlugin().getDb().getGuild(guildName);
        boolean guildExists = guild.isPresent();

        if (guildExists && noGuild) {
            getPlugin().getDb().savePlayer(new GPlayer(player2.getUniqueId(), guildName, Role.MEMBER));
            Message.sendPlaceholder(getPlugin(), getSender(), "invite-accepted", guild.get().getName());
            getPlugin().getDb().deleteInvite(player2.getUniqueId(), guildName);
            if (Bukkit.getPlayer(guild.get().getOwner()) != null) {
                Message.sendPlaceholder(getPlugin(), Objects.requireNonNull(Bukkit.getPlayer(guild.get().getOwner())), "player-joined-guild", player2.getName());
            }
        } else {
            if (!guildExists) player2.sendMessage(guildName + " does not exist anymore!");
            else Message.send(getPlugin(), getSender(), "already-in-guild");
        }
        return true;
    }
}
