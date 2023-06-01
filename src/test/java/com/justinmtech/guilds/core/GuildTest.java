package com.justinmtech.guilds.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GuildTest {
    private static Guild defaultGuild;
    private static UUID owner;
    private static UUID testPlayer;
    private static UUID testMod;
    private static UUID testCoLeader;
    private static UUID testMember;




    @BeforeEach
    void init() {
        defaultGuild = new GuildImp(owner, "GuildName");
        testPlayer = UUID.fromString("d8d5a923-7b20-43d8-883b-1150148d6955");
        owner = UUID.fromString("2adce835-6539-4ce9-9585-50f03822d9d6");
    }

    @Test
    void getName() {
        assertEquals("GuildName", defaultGuild.getName());
    }

    @Test
    void setName() {
        defaultGuild.setName("NewName");
        assertEquals("NewName", defaultGuild.getName());
    }

    @Test
    void getDescription() {
        assertEquals("A new guild!", defaultGuild.getDescription());
    }

    @Test
    void setDescription() {
        defaultGuild.setDescription("New desc");
        assertEquals("New desc", defaultGuild.getDescription());
    }

    @Test
    void getMembers() {
        assertNotNull(defaultGuild.getMembers());
        assertEquals(1, defaultGuild.getMembers().size());
    }

    @Test
    void isOwner() {
        assertTrue(defaultGuild.isOwner(owner));
    }

    @Test
    void containsMember() {
        assertTrue(defaultGuild.containsMember(owner));
        assertFalse(defaultGuild.containsMember(UUID.fromString("d8d5a923-7b20-43d8-883b-1150148d6955")));
    }

    @Test
    void containsWarp() {
        assertFalse(defaultGuild.containsWarp("test"));
    }

    @Test
    void getOwner() {
        assertEquals(owner, defaultGuild.getLeader());
    }

    @Test
    void setOwner() {
        defaultGuild.setLeader(testPlayer);
        assertFalse(defaultGuild.isOwner(owner));
        assertEquals(testPlayer, defaultGuild.getLeader());
        assertTrue(defaultGuild.isOwner(testPlayer));
    }

    @Test
    void getWarps() {
        assertNotNull(defaultGuild.getWarps());
    }

    @Test
    void getLevel() {
        assertEquals(1, defaultGuild.getLevel());
    }

    @Test
    void setLevel() {
        defaultGuild.setLevel(2);
        assertEquals(2, defaultGuild.getLevel());
        defaultGuild.setLevel(0);
        assertEquals(1, defaultGuild.getLevel());
        defaultGuild.setLevel(10);
        assertEquals(10, defaultGuild.getLevel());
        defaultGuild.setLevel(11);
        assertEquals(10, defaultGuild.getLevel());
        defaultGuild.setLevel(-5);
        assertEquals(1, defaultGuild.getLevel());
        defaultGuild.setLevel(100);
        assertEquals(10, defaultGuild.getLevel());
    }

    @Test
    void getMaxWarps() {
        defaultGuild.setLevel(1);
        assertEquals(0, defaultGuild.getMaxWarps());
        defaultGuild.setLevel(2);
        assertEquals(1, defaultGuild.getMaxWarps());
        defaultGuild.setLevel(6);
        assertEquals(3, defaultGuild.getMaxWarps());
        defaultGuild.setLevel(10);
        assertEquals(5, defaultGuild.getMaxWarps());
        defaultGuild.setLevel(20);
        assertEquals(5, defaultGuild.getMaxWarps());
    }

    @Test
    void getMaxMembers() {
        defaultGuild.setLevel(1);
        assertEquals(5, defaultGuild.getMaxMembers());
        defaultGuild.setLevel(3);
        assertEquals(15, defaultGuild.getMaxMembers());
        defaultGuild.setLevel(9);
        assertEquals(45, defaultGuild.getMaxMembers());

    }

    @Test
    void getMaxLevel() {
        assertEquals(10, defaultGuild.getMaxLevel());
    }

    @Test
    void isMod() {
    }

    @Test
    void isColeader() {
    }

/*    @Test
    void testString() {
        System.out.println(defaultGuild.toString());
    }*/
}