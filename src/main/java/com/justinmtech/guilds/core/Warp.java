package com.justinmtech.guilds.core;

import org.bukkit.Location;

public interface Warp {
    String getId();
    String getWorld();
    double getX();
    double getY();
    double getZ();
    float getYaw();
    float getPitch();
    boolean isPublic();
    void setPublic(boolean isPublic);
    Location toLocation();
}
