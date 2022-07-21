package com.justinmtech.guilds.core;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Guild implements Comparable<Guild> {
    private String name;
    private UUID owner;
    private String description;
    private Map<UUID, Role> members;
    private Map<String, Warp> warps;
    private int level;

    public Guild(UUID owner, String name) {
        this.owner = owner;
        this.name = name;
        this.members = new HashMap<>();
        members.put(owner, Role.LEADER);
        this.warps = new HashMap<>();
        this.description = "A new guild!";
        this.level = 1;
    }

    public Guild(String name) {
        this.owner = null;
        this.name = name;
        this.members = new HashMap<>();
        //members.put(null, Role.LEADER);
        this.warps = new HashMap<>();
        this.description = "A new guild!";
        this.level = 1;
    }

    public Guild(String name, UUID ownerId, String description, List<Object> memberObjects, Object warps, int level) {
        this.members = new HashMap<>();
        this.warps = new HashMap<>();
        this.name = name;
        this.owner = ownerId;
        this.description = description;
        memberObjects.forEach(member -> _addMember((JSONObject) member));
        ((JSONArray) warps).forEach(warp -> _addWarps((JSONObject) warp));
        this.level = level;
    }

    private void _addMember(JSONObject member) {
        String id = (String) member.get("player");
        String role = (String) member.get("role");
        this.members.put(UUID.fromString(id), Role.valueOf(role));
    }

    private void _addWarps(JSONObject warp) {
            String name = (String) warp.get("name");
            String world = (String) warp.get("world");
            double x = (Double) warp.get("x");
            double y = (Double) warp.get("y");
            double z = (Double) warp.get("z");
            double yaw = (Double) warp.get("yaw");
            double pitch = (Double) warp.get("pitch");
            this.warps.put(name, new Warp(name, world, x, y, z, (float) yaw, (float) pitch));
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

    public Map<UUID, Role> getMembers() {
        return members;
    }

    public boolean isOwner(UUID playerUuid) {
        return playerUuid.equals(owner);
    }

    public boolean containsMember(UUID playerUuid) {
        return members.containsKey(playerUuid);
    }

    public boolean containsWarp(String name) {
        return warps.get(name) != null;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public Map<String, Warp> getWarps() {
        return warps;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level >= 1 && level <= 10) {
            this.level = level;
        } else {
            if (level < 1) this.level = 1;
            if (level > 10) this.level = 10;
        }
    }

    public int getMaxWarps() {
        return level;
    }

    public int getMaxMembers() {
        return level * 3;
    }

    public int getMaxLevel() {
        int MAX_LEVEL = 10;
        return MAX_LEVEL;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        if (owner != null) json.put("owner", owner.toString());
        json.put("description", description);
        JSONArray jsonArray = new JSONArray();
        if (members != null) {
            for (UUID member : members.keySet()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("player", member.toString());
                jsonObject.put("role", members.get(member).toString());
                jsonArray.add(jsonObject);
            }
            json.put("members", jsonArray);
        }
        JSONArray warpArray = new JSONArray();
        for (String key : warps.keySet()) {
            Warp location = warps.get(key);
            String world = location.getWorld();
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
        json.put("level", level);
        return json.toString();
    }

    @Override
    public int compareTo(Guild o) {
        if (members.size() > o.getMembers().size()) return 1;
        return -1;
    }

    public void setMembers(Map<UUID, Role> members) {
        this.members = members;
    }

    public void setWarps(Map<String, Warp> warps) {
        this.warps = warps;
    }

    //TODO test
    public void addMember(UUID uuid) {
        members.put(uuid, Role.MEMBER);
    }

    //TODO test
    public void removeMember(UUID uuid) {
        members.remove(uuid);
    }

    public void addWarp(Warp warp) {warps.put(warp.getId(), warp);}

    public void removeWarp(String id) {warps.remove(id);}

    public void updateWarp(Warp warp) {warps.replace(warp.getId(), warp);}
}
