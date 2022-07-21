package com.justinmtech.guilds.persistence;

import com.justinmtech.guilds.core.GPlayer;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.core.Warp;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//TODO Make new interface
public interface ManageDataNew {

    boolean saveGuild(Guild guild);
    boolean savePlayer(GPlayer player);
    boolean saveInvite(UUID playerId, String guildId);
    boolean saveWarp(String id, String world, double x, double y, double z, float yaw, float pitch, String guildId);
    boolean deletePlayer(String id);
    boolean deleteGuild(String id);
    boolean deleteInvite(UUID playerId, String guildId);
    boolean deleteWarp(String guildId, String warpId);
    Optional<Guild> getGuild(String id);
    Optional<Guild> getGuild(UUID uuid);
    Optional<GPlayer> getPlayer(UUID id);
    Optional<Warp> getWarp(UUID id, String warpId);
    boolean hasInvite(UUID uuid, String guildId);
    List<Guild> getAllGuilds();
}
