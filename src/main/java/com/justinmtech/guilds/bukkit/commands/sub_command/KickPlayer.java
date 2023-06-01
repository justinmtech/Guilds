package com.justinmtech.guilds.bukkit.commands.sub_command;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.bukkit.commands.SubCommand;
import com.justinmtech.guilds.core.GPlayer;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.bukkit.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

//TODO Ensure mods cannot kick co-leaders
public class KickPlayer extends SubCommand {
    public KickPlayer(Guilds plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        execute();
    }

    private boolean execute() {
        if (getSender() instanceof Player) {
            Player player = (Player) getSender();

            //ensure is player
            Optional<GPlayer> gPlayer = getPlugin().getData().getPlayer(player.getUniqueId());
            if (gPlayer.isEmpty()) {
                Message.send(getPlugin(), getSender(), "not-in-guild");
                return false;
            }
            //ensure player is in a guild
            Optional<Guild> guild = getPlugin().getData().getGuild(player.getUniqueId());
            if (guild.isEmpty()) {
                Message.send(getPlugin(), getSender(), "not-in-guild");
                return false;
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(getArgs()[1]);
            //ensure the target player is in the same guild
            if (!guild.get().getMembers().containsKey(target.getUniqueId())) {
                Message.sendPlaceholder(getPlugin(), getSender(), "player-not-in-guild", target.getName());
                return false;
            }

            Optional<GPlayer> targetPlayer = getPlugin().getData().getPlayer(target.getUniqueId());


            //ensure player is at least a mod
            if (gPlayer.get().getRole().ordinal() == 0) {
                Message.send(getPlugin(), getSender(), "must-be-mod");
                return false;
            }

            //ensure player is not kicking themselves
            if (target.getUniqueId() == player.getUniqueId()) {
                Message.send(getPlugin(), getSender(), "self-kick-error");
                return false;
            }

            //ensure player is not kicking the leader
            if (target.getUniqueId() == guild.get().getLeader()) {
                Message.send(getPlugin(), getSender(), "kick-leader-error");
                return false;
            }

            //ensure player can kick the target
            if (gPlayer.get().getRole().ordinal() < targetPlayer.get().getRole().ordinal()) {
                Message.sendPlaceholder(getPlugin(), getSender(), "kick-error", target.getName());
                return false;
            }

            //kick the player
            guild.get().getMembers().remove(target.getUniqueId());
            getPlugin().getData().saveGuild(guild.get());
            targetPlayer.get().setGuildId(null);
            getPlugin().getData().savePlayer(targetPlayer.get());
            guild.get().getOnlineMembers().forEach((uuid, p) -> {
                Player bukkitPlayer = Bukkit.getPlayer(uuid);
                if (bukkitPlayer != null) {
                    if (!bukkitPlayer.getUniqueId().equals(targetPlayer.get().getUuid())) {
                        Message.sendPlaceholder(getPlugin(), bukkitPlayer, "kick-success", target.getName());
                    }
                }
            });
            Player targetPlayerOnline = Bukkit.getPlayer(target.getUniqueId());
            if (targetPlayerOnline != null) Message.sendPlaceholder(getPlugin(), targetPlayerOnline, "kicked-from-guild", guild.get().getName());
            return true;
        } else {
            Message.send(getPlugin(), getSender(), "not-console-command");
        }
        return false;
    }
}
