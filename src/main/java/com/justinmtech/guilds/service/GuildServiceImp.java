package com.justinmtech.guilds.service;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.bukkit.util.Message;
import com.justinmtech.guilds.core.*;
import com.justinmtech.guilds.persistence.GuildsRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class GuildServiceImp implements GuildsRepository, GuildService {
    private GuildsRepository guildsRepository;
    private Guilds plugin;

    public GuildServiceImp(Guilds plugin) {
        this.plugin = plugin;
        this.guildsRepository = plugin.getGuildsRepository();
    }

    public void leaveGuild(Guild guild, GPlayer player) {
        guild.removeMember(player.getUuid());
        player.setGuildId(null);
        savePlayer(player);
        saveGuild(guild);
    }

    @Override
    public boolean saveGuild(Guild guild) {
        return guildsRepository.saveGuild(guild);
    }

    @Override
    public boolean savePlayer(GPlayer player) {
        return guildsRepository.savePlayer(player);
    }

    @Override
    public boolean saveInvite(UUID playerId, String guildId) {
        return guildsRepository.saveInvite(playerId, guildId);
    }

    @Override
    public boolean saveWarp(String id, String world, double x, double y, double z, float yaw, float pitch, String guildId) {
        return guildsRepository.saveWarp(id, world, x, y, z, yaw, pitch, guildId);
    }

    @Override
    public boolean deletePlayer(String id) {
        return guildsRepository.deletePlayer(id);
    }

    @Override
    public boolean deleteGuild(String id) {
        return guildsRepository.deleteGuild(id);
    }

    @Override
    public boolean deleteInvite(UUID playerId, String guildId) {
        return guildsRepository.deleteInvite(playerId, guildId);
    }

    @Override
    public boolean deleteWarp(String guildId, String warpId) {
        return guildsRepository.deleteWarp(guildId, warpId);
    }

    @Override
    public Optional<Guild> getGuild(String id) {
        return guildsRepository.getGuild(id);
    }

    @Override
    public Optional<Guild> getGuild(UUID uuid) {
        return guildsRepository.getGuild(uuid);
    }

    @Override
    public Optional<GPlayer> getPlayer(UUID id) {
        return guildsRepository.getPlayer(id);
    }

    @Override
    public Optional<Warp> getWarp(UUID id, String warpId) {
        return guildsRepository.getWarp(id, warpId);
    }

    @Override
    public boolean hasInvite(UUID uuid, String guildId) {
        return guildsRepository.hasInvite(uuid, guildId);
    }

    @Override
    public List<Guild> getAllGuilds() {
        return guildsRepository.getAllGuilds();
    }

    @Override
    public boolean saveAllData() {
        return guildsRepository.saveAllData();
    }

    @Override
    public boolean loadAllData() {
        return guildsRepository.loadAllData();
    }

    @Override
    public boolean acceptInvite(UUID receiverId, String guildName) {
        boolean noGuild = getGuild(receiverId).isEmpty();
        boolean invited = hasInvite(receiverId, guildName);
        Player receiver = Bukkit.getPlayer(receiverId);
        if (receiver == null) return false;
        if (!invited) {
            Message.sendPlaceholder(plugin, receiver, "no-invite", guildName);
            return false;
        }
        Optional<Guild> guild = getGuild(guildName);
        boolean guildExists = guild.isPresent();

        if (guildExists && noGuild) {
            savePlayer(new GPlayerImp(receiverId, guildName, Role.MEMBER));
            guild.get().addMember(receiverId);
            saveGuild(guild.get());
            Message.sendPlaceholder(plugin, receiver, "invite-accepted", guild.get().getName());
            plugin.getGuildsRepository().deleteInvite(receiverId, guildName);
            if (Bukkit.getPlayer(guild.get().getLeader()) != null) {
                Message.sendPlaceholder(plugin, Objects.requireNonNull(Bukkit.getPlayer(guild.get().getLeader())), "player-joined-guild", receiver.getName());
            }
        } else {
            if (!guildExists) receiver.sendMessage(guildName + " does not exist anymore!");
            else Message.send(plugin, receiver, "already-in-guild");
        }
        return true;
    }

    @Override
    public boolean confirmation(UUID playerId) {
        Player player = Bukkit.getPlayer(playerId);
        if (!plugin.getCache().hasPendingTransaction(player.getUniqueId())) return false;
        double amount = plugin.getCache().getPendingTransactionAmount(player.getUniqueId());
        Optional<Guild> guild = getGuild(player.getUniqueId());
        if (guild.isEmpty()) {
            Message.send(plugin, player, "no-confirmation");
            return false;
        }
        guild.get().setLevel(guild.get().getLevel() + 1);
        plugin.getGuildsRepository().saveGuild(guild.get());
        Guilds.getEcon().withdrawPlayer(player, amount);
        plugin.getCache().removeTransactionConfirmation(player.getUniqueId());
        Message.sendPlaceholder(plugin, player, "guild-level-up", String.valueOf(guild.get().getLevel()));
        return true;
    }

    @Override
    public boolean createGuild(UUID playerId, String guildName) {
        return false;
    }

    @Override
    public boolean denyInvite(UUID playerId, String guildName) {
        return false;
    }

    @Override
    public boolean disbandGuild(UUID playerId, String guildName) {
        return false;
    }

    @Override
    public void getGuildInfo(UUID playerId, String guildName) {

    }

    @Override
    public boolean sendToWarp(UUID playerId, String warpName) {
        return false;
    }

    @Override
    public boolean invitePlayer(UUID playerId, String playerName) {
        return false;
    }

    @Override
    public boolean kickPlayer(UUID playerId, String playerName) {
        return false;
    }

    @Override
    public boolean leaveGuild(UUID playerId, String guildName) {
        return false;
    }

    @Override
    public void listGuilds() {

    }

    @Override
    public boolean promotePlayer(UUID playerId, String playerName) {
        return false;
    }

    @Override
    public boolean demotePlayer(UUID playerId, String playerName) {
        return false;
    }

    @Override
    public boolean setDescription(UUID playerId, String description) {
        return false;
    }

    @Override
    public boolean setWarp(UUID playerId, String warpName) {
        return false;
    }

    @Override
    public boolean upgradeGuild(UUID playerId) {
        return false;
    }
}
