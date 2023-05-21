package com.justinmtech.guilds.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WarpTest {
    private static Warp warp;

    @BeforeAll
    static void setup() {
        warp = new Warp("best_warp", "world", 50, 100, -50, 100, 500);
    }

    @Test
    @Order(1)
    public void getWorld() {
        Assertions.assertEquals("world", warp.getWorld());
    }

    @Test
    @Order(2)
    public void getX() {
        Assertions.assertEquals(50, warp.getX());
    }

    @Test
    @Order(3)
    public void getY() {
        Assertions.assertEquals(100, warp.getY());
    }

    @Test
    @Order(4)
    public void getZ() {
        Assertions.assertEquals(-50, warp.getZ());
    }

    @Test
    @Order(5)
    public void getYaw() {
        Assertions.assertEquals(100, warp.getYaw());
    }

    @Test
    @Order(6)
    public void getPitch() {
        Assertions.assertEquals(500, warp.getPitch());
    }

    @Test
    @Order(7)
    public void getId() {
        Assertions.assertEquals("best_warp", warp.getId());
    }
}