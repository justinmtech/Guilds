package com.justinmtech.guilds.persistence.file;

import com.justinmtech.guilds.core.GPlayer;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.core.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FileCache {
    private final Map<UUID, GPlayer> guildPlayers;
    private final Map<String, Guild> guilds;
    private final Map<UUID, Double> transactionConfirmations;

    public FileCache() {
        guildPlayers = new HashMap<>();
        guilds = new HashMap<>();
        transactionConfirmations = new HashMap<>();
    }

    public void addPlayer(UUID uuid, String guildId, Role role) {
        guildPlayers.put(uuid, new GPlayer(uuid, guildId, role));
    }

    public Optional<GPlayer> getPlayer(UUID uuid) {
        if (guildPlayers.containsKey(uuid)) {
            return Optional.of(guildPlayers.get(uuid));
        } else {
            return Optional.empty();
        }
    }

    public void removePlayer(UUID uuid) {
        guildPlayers.remove(uuid);
    }

    public boolean playerExists(UUID uuid) {
        return guildPlayers.containsKey(uuid);
    }

    public boolean guildExists(String guildId) {
        return guilds.containsKey(guildId);
    }

    public void addGuild(Guild guild) {
        guilds.put(guild.getName(), guild);
    }

    public void removeGuildAndItsPlayers(String id) {
        Optional<Guild> guild = getGuild(id);
        if (guild.isPresent()) {
            Map<UUID, Role> members = guild.get().getMembers();
            for (UUID uuid : members.keySet()) {
                removePlayer(uuid);
            }
        }
        guilds.remove(id);
    }

    public Optional<Guild> getGuild(String id) {
        if (guilds.containsKey(id)) {
            return Optional.of(guilds.get(id));
        } else {
            return Optional.empty();
        }
    }

    public Map<String, Guild> getGuilds() {
        return guilds;
    }

    public Map<UUID, GPlayer> getGuildPlayers() {
        return guildPlayers;
    }

    public void addTransactionConfirmation(UUID uuid, double value) {
        transactionConfirmations.put(uuid, value);
    }

    public double getTransactionAmount(UUID uuid) {return transactionConfirmations.get(uuid);}

    public boolean hasTransactionConfirmation(UUID uuid) {
        return transactionConfirmations.containsKey(uuid);
    }
}
