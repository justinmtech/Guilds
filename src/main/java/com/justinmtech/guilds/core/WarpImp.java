package com.justinmtech.guilds.core;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Objects;

public class WarpImp implements Warp {
    private final String id;
    private final String world;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public WarpImp(String id, String world, double x, double y, double z, float yaw, float pitch) {
        this.id = id;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public WarpImp(String id, Location loc) {
        this.id = id;
        this.world = Objects.requireNonNull(loc.getWorld()).getName();
        this.x = loc.getX();
        this.y = loc.getY();
        this.z = loc.getZ();
        this.yaw = loc.getYaw();
        this.pitch = loc.getPitch();
    }

    @Override
    public String getWorld() {
        return world;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getZ() {
        return z;
    }

    @Override
    public float getYaw() {
        return yaw;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Location toLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }
}
