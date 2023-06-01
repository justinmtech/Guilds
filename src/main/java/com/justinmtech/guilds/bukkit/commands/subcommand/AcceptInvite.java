package com.justinmtech.guilds.bukkit.commands.subcommand;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.bukkit.commands.SubCommand;
import com.justinmtech.guilds.core.GPlayerImp;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.core.Role;
import com.justinmtech.guilds.bukkit.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("UnusedReturnValue")
public class AcceptInvite extends SubCommand {

    public AcceptInvite(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private boolean execute() {
        Player player2 = (Player) getSender();
        String guildName = getArgs()[1];
        boolean noGuild = getPlugin().getData().getGuild(player2.getUniqueId()).isEmpty();
        boolean invited = getPlugin().getData().hasInvite(player2.getUniqueId(), guildName);
        if (!invited) {
            Message.sendPlaceholder(getPlugin(), getSender(), "no-invite", guildName);
            return false;
        }
        Optional<Guild> guild = getPlugin().getData().getGuild(guildName);
        boolean guildExists = guild.isPresent();

        if (guildExists && noGuild) {
            getPlugin().getData().savePlayer(new GPlayerImp(player2.getUniqueId(), guildName, Role.MEMBER));
            guild.get().addMember(player2.getUniqueId());
            getPlugin().getData().saveGuild(guild.get());
            Message.sendPlaceholder(getPlugin(), getSender(), "invite-accepted", guild.get().getName());
            getPlugin().getData().deleteInvite(player2.getUniqueId(), guildName);
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
