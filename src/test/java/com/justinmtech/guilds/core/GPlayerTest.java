package com.justinmtech.guilds.core;

import org.junit.jupiter.api.*;

import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GPlayerTest {
    private static GPlayer player;
    private static UUID uuid;

    @BeforeAll
    static void setup() {
        uuid = UUID.randomUUID();
        player = new GPlayerImp(uuid, "bears", Role.MEMBER);
    }

    @Test
    @Order(1)
    public void testGetUuid() {
        Assertions.assertEquals(uuid, player.getUuid());
    }

    @Test
    @Order(2)
    public void testGetGuildId() {
        Assertions.assertEquals("bears", player.getGuildId());
    }

    @Test
    @Order(3)
    public void testSetGuildId() {
        player.setGuildId("boars");
        Assertions.assertEquals("boars", player.getGuildId());
    }

    @Test
    @Order(4)
    public void testGetRole() {
        Assertions.assertEquals(Role.MEMBER, player.getRole());
    }

    @Test
    @Order(5)
    public void testSetRole() {
        player.setRole(Role.LEADER);
        Assertions.assertEquals(Role.LEADER, player.getRole());
    }

    @Test
    @Order(6)
    public void testHasInvite() {
        Assertions.assertFalse(player.hasInvite("otherguild"));
    }

    @Test
    @Order(7)
    public void testAddInvite() {
        player.addInvite("thebros");
        Assertions.assertTrue(player.hasInvite("thebros"));
    }

    @Test
    @Order(8)
    public void testRemoveInvite() {
        player.removeInvite("thebros");
        Assertions.assertFalse(player.hasInvite("thebros"));
    }

    @Test
    @Order(9)
    public void testPromote() {
        player.setRole(Role.MEMBER);
        Assertions.assertEquals(Role.MOD, player.promote());
        Assertions.assertEquals(Role.COLEADER, player.promote());
        Assertions.assertNull(player.promote());
    }
}