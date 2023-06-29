package com.justinmtech.guilds.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class GuildImp implements Guild {
    private String name;
    private UUID owner;
    private String description;
    private Map<UUID, Role> members;
    private Map<String, Warp> warps;
    private int level;
    private String tag;

    public static int MAX_TAG_LENGTH = 5;
    public static int MAX_NAME_LENGTH = 16;

    public GuildImp(UUID owner, String name) {
        this.owner = owner;
        setName(name);
        this.members = new HashMap<>();
        members.put(owner, Role.LEADER);
        this.warps = new HashMap<>();
        this.description = "A new guild!";
        this.level = 1;
        this.tag = name;
    }

    public GuildImp(String name) {
        this.owner = null;
        setName(name);
        this.members = new HashMap<>();
        //members.put(null, Role.LEADER);
        this.warps = new HashMap<>();
        this.description = "A new guild!";
        this.level = 1;
        this.tag = name;
    }

    public GuildImp(String name, String tag, UUID ownerId, String description, List<Object> memberObjects, Object warps, int level) {
        this.members = new HashMap<>();
        this.warps = new HashMap<>();
        setName(name);
        this.owner = ownerId;
        this.description = description;
        memberObjects.forEach(member -> _addMember((JSONObject) member));
        ((JSONArray) warps).forEach(warp -> _addWarps((JSONObject) warp));
        this.level = level;
        this.tag = tag != null ? tag : name;
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
            this.warps.put(name, new WarpImp(name, world, x, y, z, (float) yaw, (float) pitch));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void setTag(String tag) {
        if (tag.length() > MAX_TAG_LENGTH) {
            this.tag  = tag.substring(0, MAX_TAG_LENGTH);
        } else {
            this.tag = tag;
        }
    }

    @Override
    public void setName(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            this.name = name.substring(0, MAX_NAME_LENGTH);
        } else {
            this.name = name;
        }
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Map<UUID, Role> getMembers() {
        return members;
    }

    @Override
    public Map<UUID, Role> getOnlineMembers() {
        Map<UUID, Role> onlineMembers = new HashMap<>();
        for (UUID uuid : members.keySet()) {
            if (Bukkit.getPlayer(uuid) != null) {
                onlineMembers.put(uuid, members.get(uuid));
            }
        }
        return onlineMembers;
    }

    @Override
    public boolean isOwner(UUID playerUuid) {
        return playerUuid.equals(owner);
    }

    @Override
    public boolean isMod(UUID playerUuid) {
        if (members.containsKey(playerUuid)) {
            return members.get(playerUuid).equals(Role.MOD);
        } else {
            return false;
        }
    }

    @Override
    public boolean isColeader(UUID playerUuid) {
        if (members.containsKey(playerUuid)) {
            return members.get(playerUuid).equals(Role.COLEADER);
        } else {
            return false;
        }
    }

    @Override
    public boolean containsMember(UUID playerUuid) {
        return members.containsKey(playerUuid);
    }

    @Override
    public boolean containsWarp(String name) {
        return warps.get(name) != null;
    }

    @Override
    public UUID getOwner() {
        return owner;
    }

    @Override
    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    @Override
    public Map<String, Warp> getWarps() {
        return warps;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        if (level >= 1 && level <= 10) {
            this.level = level;
        } else {
            if (level < 1) this.level = 1;
            if (level > 10) this.level = 10;
        }
    }

    @Override
    public void sendChat(String message) {
        for (UUID uuid : members.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                player.sendMessage(message);
            }
        }
    }

    @Override
    public int getMaxWarps() {
        return level;
    }

    @Override
    public int getMaxMembers() {
        return level * 3;
    }

    @Override
    public int getMaxLevel() {
        return 10;
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
        if (tag != null) json.put("tag", tag);
        return json.toString();
    }

    @Override
    public int compareTo(Guild o) {
        if (members.size() > o.getMembers().size()) return 1;
        return -1;
    }

    @Override
    public void setMembers(Map<UUID, Role> members) {
        this.members = members;
    }

    @Override
    public void setWarps(Map<String, Warp> warps) {
        this.warps = warps;
    }

    @Override
    public void addMember(UUID uuid) {
        members.put(uuid, Role.MEMBER);
    }

    @Override
    public void removeMember(UUID uuid) {
        members.remove(uuid);
    }

    @Override
    public void addWarp(Warp warp) {warps.put(warp.getId(), warp);}

    @Override
    public void removeWarp(String id) {warps.remove(id);}

    @Override
    public void updateWarp(Warp warp) {warps.replace(warp.getId(), warp);}
}
