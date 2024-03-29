package com.justinmtech.guilds.persistence.file;

import com.justinmtech.guilds.core.GPlayer;
import com.justinmtech.guilds.core.GuildImp;
import com.justinmtech.guilds.core.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FileCacheTest {

    private FileCache cache;

    @BeforeEach
    public void setup() {
        cache = new FileCache();
    }

    @Test
    void addPlayer() {
        assertEquals(0, cache.getGuildPlayers().size());
        cache.addPlayer(UUID.randomUUID(), "Test", Role.LEADER);
        assertEquals(1, cache.getGuildPlayers().size());
    }

    @Test
    void getPlayer() {
        UUID uuid = UUID.randomUUID();
        cache.addPlayer(uuid, "Test", Role.LEADER);
        Optional<GPlayer> gPlayer = cache.getPlayer(uuid);
        assertTrue(gPlayer.isPresent());
    }

    @Test
    void removePlayer() {
        UUID uuid = UUID.randomUUID();
        cache.addPlayer(uuid, "Test", Role.LEADER);
        assertEquals(1, cache.getGuildPlayers().size());
        cache.removePlayer(uuid);
        assertEquals(0, cache.getGuildPlayers().size());
    }

    @Test
    void playerExists() {
        UUID uuid = UUID.randomUUID();
        cache.addPlayer(uuid, "Test", Role.LEADER);
        assertTrue(cache.playerExists(uuid));
        assertFalse(cache.playerExists(UUID.randomUUID()));
    }

    @Test
    void guildExists() {
        GuildImp guild = new GuildImp(UUID.randomUUID(), "Test");
        cache.addGuild(guild);
        assertEquals(1, cache.getGuilds().size());
        assertTrue(cache.guildExists(guild.getName()));
        assertFalse(cache.guildExists("random"));
    }

    @Test
    void addGuild() {
        GuildImp guild = new GuildImp(UUID.randomUUID(), "Test");
        assertEquals(0, cache.getGuilds().size());
        cache.addGuild(guild);
        assertEquals(1, cache.getGuilds().size());
    }

    @Test
    void removeGuild() {
        GuildImp guild = new GuildImp(UUID.randomUUID(), "Test");
        cache.addGuild(guild);
        assertEquals(1, cache.getGuilds().size());
        cache.removeGuildAndItsPlayers(guild.getName());
        assertEquals(0, cache.getGuilds().size());
    }

    @Test
    void getGuild() {
        GuildImp guild = new GuildImp(UUID.randomUUID(), "Test");
        cache.addGuild(guild);
        assertNotNull(cache.getGuild(guild.getName()));
    }

    @Test
    void getGuilds() {
        assertNotNull(cache.getGuilds());
    }

    @Test
    void getPlayers() {
        assertNotNull(cache.getGuildPlayers());
    }

    @Test
    void addTransactionConfirmation() {
        UUID uuid = UUID.randomUUID();
        double value = 1.0;
        cache.addTransactionConfirmation(uuid, value);
        assertTrue(cache.hasTransactionConfirmation(uuid));
        assertEquals(1.0, cache.getTransactionAmount(uuid));
    }

    @Test
    void hasTransactionConfirmation() {
        UUID uuid = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        cache.addTransactionConfirmation(uuid, 2.0);
        assertTrue(cache.hasTransactionConfirmation(uuid));
        assertFalse(cache.hasTransactionConfirmation(uuid2));
    }
}