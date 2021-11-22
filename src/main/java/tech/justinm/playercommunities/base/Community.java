package tech.justinm.playercommunities.base;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Community {
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
        this.members = new ArrayList<>();
        this.warps = new ArrayList<>();
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
        return name;
    }
}
