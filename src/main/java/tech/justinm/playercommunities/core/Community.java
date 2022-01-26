package tech.justinm.playercommunities.core;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class Community implements Comparable<Community> {
    private String name;
    private UUID owner;
    private String description;
    private List<UUID> members;
    private Map<String, Location> warps;

    public Community(UUID owner, String name) {
        this.owner = owner;
        this.name = name;
        this.members = new LinkedList<>();
        members.add(owner);
        this.warps = new HashMap<>();
    }

    public Community(String name, UUID ownerId, String description, List<UUID> memberUuids, Object warps) {
        this.members = new LinkedList<>();
        this.warps = new HashMap<>();
        this.name = name;
        this.owner = ownerId;
        this.description = description;
        this.members = memberUuids;
        ((JSONArray) warps).forEach(warp -> _addWarps((JSONObject) warp));
    }

    private void _addWarps(JSONObject warp) {
            String name = (String) warp.get("name");
            String world = (String) warp.get("world");
            double x = (Double) warp.get("x");
            double y = (Double) warp.get("y");
            double z = (Double) warp.get("z");
            double yaw = (Double) warp.get("yaw");
            double pitch = (Double) warp.get("pitch");
            this.warps.put(name, new Location(Bukkit.getWorld(world), x, y, z, (float) yaw, (float) pitch));
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

    public List<UUID> getMembers() {
        return members;
    }

    public boolean isOwner(UUID playerUuid) {
        return playerUuid.equals(owner);
    }

    public boolean containsMember(Player player) {
        return members.contains(player);
    }

    public boolean containsWarp(String name) {
        return warps.get(name) != null;
    }

    public void setMembers(List<UUID> members) {
        this.members = members;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public Map<String, Location> getWarps() {
        return warps;
    }

    public void setWarps(Map<String, Location> warps) {
        this.warps = warps;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("owner", owner);
        json.put("description", description);
        json.put("members", members);
        JSONArray warpArray = new JSONArray();
        for (String key : warps.keySet()) {
            Location location = warps.get(key);
            String world = location.getWorld().getName();
            double x = location.getX();
            double y = location.getY();
            double z = location.getZ();
            double yaw = location.getYaw();
            double pitch = location.getPitch();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", key);
            jsonObject.put("world", world);
            jsonObject.put("x", x);
            jsonObject.put("y", y);
            jsonObject.put("z", z);
            jsonObject.put("yaw", yaw);
            jsonObject.put("pitch", pitch);
            warpArray.add(jsonObject);
        }
        json.put("warps", warpArray);
        return json.toString();
    }

    @Override
    public int compareTo(Community o) {
        if (members.size() > o.getMembers().size()) return 1;
        return -1;
    }
}
