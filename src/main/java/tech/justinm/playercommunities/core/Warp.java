package tech.justinm.playercommunities.core;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class Warp implements ConfigurationSerializable {
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

    public Warp(Map<String, Object> serializedMap) {
        name = (String) serializedMap.get("name");
        description = (String) serializedMap.get("description");
        location = (Location) serializedMap.get("location");
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
    public Map<String, Object> serialize() {
        HashMap<String, Object> serializableMap = new HashMap<>();

        serializableMap.put("name", name);
        serializableMap.put("description", description);
        serializableMap.put("location", location);

        return serializableMap;
    }
}
