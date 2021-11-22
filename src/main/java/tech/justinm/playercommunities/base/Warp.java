package tech.justinm.playercommunities.base;

import org.bukkit.Location;

public class Warp {
    private String name;
    private String description;
    private Location location;

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
}
