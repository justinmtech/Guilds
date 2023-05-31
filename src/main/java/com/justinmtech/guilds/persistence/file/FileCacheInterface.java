package com.justinmtech.guilds.persistence.file;

import com.justinmtech.guilds.core.GPlayer;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.core.Role;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface FileCacheInterface {
    void addPlayer(UUID uuid, String guildId, Role role);
    Optional<GPlayer> getPlayer(UUID uuid);
    void removePlayer(UUID uuid);
    boolean playerExists(UUID uuid);
    boolean guildExists(String guildId);
    void addGuild(Guild guild);
    void removeGuildAndItsPlayers(String id);
    Optional<Guild> getGuild(String id);
    Map<String, Guild> getGuilds();
    Map<UUID, GPlayer> getGuildPlayers();
    void addTransactionConfirmation(UUID uuid, double amount);
    double getTransactionAmount(UUID uuid);
    boolean hasTransactionConfirmation(UUID uuid);
}
