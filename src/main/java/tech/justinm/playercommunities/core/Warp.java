package tech.justinm.playercommunities.core;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.json.simple.JSONObject;

import java.util.Map;

public class Warp {
    private String name;
    private String description;
    private Location location;

    public Warp(String name, String description, String world, double x, double y, double z, float yaw, float pitch) {
        this.name = name;
        this.description = description;
        location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public Warp(String name, String description, Location location) {
        this.name = name;
        this.description = description;
        this.location = location;
    }

    public Warp(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("description", description);
        json.put("x", location.getX());
        json.put("y", location.getY());
        json.put("z", location.getZ());
        json.put("pitch", location.getPitch());
        json.put("yaw", location.getYaw());
        json.put("world", location.getWorld().getName());


        return json.toString();
    }
}
