package com.justinmtech.guilds.persistence.database;

import com.justinmtech.guilds.core.GPlayerImp;
import com.justinmtech.guilds.core.GuildImp;
import com.justinmtech.guilds.core.Role;
import com.justinmtech.guilds.core.Warp;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseTest {
    private static Database d;
    private static UUID uuid;

    @BeforeAll
    static void init() throws SQLException {
        d = new Database("localhost", 3306, "root", "password", "db_test", "server");
        uuid = UUID.randomUUID();
    }

    @Test
    void testSQL() throws SQLException {
        assertNotNull(d.connect());
        d.createAllTables();
        GuildImp guild = new GuildImp(uuid, "Warriors");
        assertTrue(d.saveGuild(guild));
        d.getGuild(guild.getName());
        assertTrue(d.savePlayer(new GPlayerImp(uuid, "Warriors", Role.LEADER)));
        assertTrue(d.saveWarp("CoolWarp", "world", 100, 50, 100, 0, 0, "Warriors"));
        Optional<Warp> warp = d.getWarp(uuid, "CoolWarp");
        Optional<Warp> warp2 = d.getWarp(uuid, "noexist");
        assertTrue(warp.isPresent());
        assertTrue(warp2.isEmpty());
        assertTrue(d.saveInvite(uuid, "Warriors"));
        assertTrue(d.deleteInvite(uuid, "Warriors"));
        assertTrue(d.deleteWarp("CoolWarp"));
        assertTrue(d.deletePlayer(uuid.toString()));
        assertTrue(d.deleteGuild("Warriors"));
    }

    @AfterAll
    static void stop() throws SQLException {
        d.dropAllTables();
    }
}