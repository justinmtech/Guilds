package com.justinmtech.guilds.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

public class WarpTest {
    private static Warp warp;

    @BeforeAll
    static void setup() {
        warp = new WarpImp("best_warp", "world", 50, 100, -50, 100, 500);
    }

    @Test
    public void getWorld() {
        Assertions.assertEquals("world", warp.getWorld());
    }

    @Test
    public void getX() {
        Assertions.assertEquals(50, warp.getX());
    }

    @Test
    public void getY() {
        Assertions.assertEquals(100, warp.getY());
    }

    @Test
    public void getZ() {
        Assertions.assertEquals(-50, warp.getZ());
    }

    @Test
    public void getYaw() {
        Assertions.assertEquals(100, warp.getYaw());
    }

    @Test
    public void getPitch() {
        Assertions.assertEquals(500, warp.getPitch());
    }

    @Test
    public void getId() {
        Assertions.assertEquals("best_warp", warp.getId());
    }
}