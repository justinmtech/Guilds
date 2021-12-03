package tech.justinm.playercommunities.persistence;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.core.Invite;

import java.io.File;
import java.util.List;

public class FlatfileDataHandler implements ManageData {
    private final PlayerCommunities plugin;
    private List<Community> communities;
    private List<Invite> invites;

    public FlatfileDataHandler(PlayerCommunities plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean setup() {
        try {
            File file = new File("plugins//PlayerCommunities//data");
            if (!file.exists()) {
            return file.mkdir();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Community getCommunity(Player player) {
        return communities.stream().filter(c -> c.getMembers().contains(player)).findAny().orElseThrow(NullPointerException::new);
    }

    @Override
    public Community getCommunity(Player player, String name) {
        return communities.stream().filter(c -> c.getMembers().contains(player)
                && c.getName().equalsIgnoreCase(name)).findAny().orElseThrow(NullPointerException::new);
    }

    @Override
    public List<Community> getALlCommunities() {
        return communities;
    }

    @Override
    public boolean saveCommunity(Community community) {
        try {
            File file = new File("plugins//PlayerCommunities//data//" + community.getName());
                if (!file.exists()) {
                    file.createNewFile();
                }
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("name", community.getName());
            config.set("description", community.getDescription());
            config.set("members", community.getMembers());
            config.set("warps", community.getWarps());
            config.save(file);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveAllCommunities(List<Community> communities) {
        try {
            for (Community community : communities) {
                saveCommunity(community);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean loadCommunity(Player player) {
        return false;
    }

    @Override
    public boolean loadAllCommunities() {
        return false;
    }

    @Override
    public boolean deleteCommunity(Community community) {
        return false;
    }

    @Override
    public Invite getInvite(Player receiver) {
        return null;
    }

    @Override
    public List<Invite> getAllInvites() {
        return null;
    }

    @Override
    public boolean deleteInvite(Invite invite) {
        return false;
    }
}
