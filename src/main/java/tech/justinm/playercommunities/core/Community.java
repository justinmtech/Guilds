package tech.justinm.playercommunities.core;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Community implements Comparable<Community>, ConfigurationSerializable {
    private String name;
    private Player owner;
    private String description;
    private List<Player> members;
    private List<Warp> warps;

    public Community(String name, String description, List<Player> members, List<Warp> warps) {
        this.name = name;
        this.description = description;
        this.members = new ArrayList<>();
        this.members = members;
        this.warps = new ArrayList<>();
        this.warps = warps;
    }

    public Community(String name, String description) {
        this.name = name;
        this.description = description;
        this.members = new ArrayList<>();
        this.warps = new ArrayList<>();
    }

    public Community(String name, Player owner) {
        this.name = name;
        this.owner = owner;
        this.description = "Default description :(";
        this.members = new ArrayList<>();
        this.warps = new ArrayList<>();
    }

    public Community(Map<String, Object> serializedMap) {
        name = (String) serializedMap.get("name");
        owner = (Player) serializedMap.get("owner");
        description = (String) serializedMap.get("description");
        members = (List<Player>) serializedMap.get("members");
        warps = (List<Warp>) serializedMap.get("warps");
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

    public List<Player> getMembers() {
        return members;
    }

    public boolean isOwner(Player player) {
        if (player.equals(owner)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean containsMember(Player player) {
        if (members.contains(player)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean containsWarp(String name) {
        if (warps.stream().anyMatch(w -> w.getName().equalsIgnoreCase(name))) {
            return true;
        }
        return false;
    }

    public void setMembers(List<Player> members) {
        this.members = members;
    }

    public List<Warp> getWarps() {
        return warps;
    }

    public void setWarps(List<Warp> warps) {
        this.warps = warps;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Community: " + name + ", Members: " + members.size() + ", Description: [" + description + "], " + "Warps: " + warps.size();
    }

    @Override
    public int compareTo(Community o) {
        if (this.members.size() > o.getMembers().size()) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String,Object> mapSerializer = new HashMap<>();

        mapSerializer.put("name", name);
        mapSerializer.put("owner", owner);
        mapSerializer.put("description", description);
        mapSerializer.put("members", members);
        mapSerializer.put("warps", warps);
        return mapSerializer;
    }
}
