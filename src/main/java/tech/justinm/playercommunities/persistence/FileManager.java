package tech.justinm.playercommunities.persistence;

import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.core.Community;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FileManager implements ManageData {
    private final PlayerCommunities plugin;
    private final List<Community> communities;
    private final Map<UUID, String> invites;

    public FileManager(PlayerCommunities plugin) {
        this.plugin = plugin;
        this.communities = new LinkedList<>();
        this.invites = new HashMap<>();
    }


    @Override
    public void setup() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
            plugin.saveDefaultConfig();
        }
    }

    @Override
    public Community getCommunity(String name) {
        return communities.stream().filter(c -> c.getName().equalsIgnoreCase(name)).findAny().orElseThrow(NullPointerException::new);
    }

    @Override
    public Community getCommunity(UUID playerUuid) {
        return communities.stream().filter(c -> c.getMembers().contains(playerUuid)).findAny().orElseThrow(NullPointerException::new);
    }

    @Override
    public List<Community> getAllCommunities() {
        return communities;
    }

    @Override
    public void createCommunity(UUID owner, String name) {
        this.communities.add(new Community(owner, name));
    }

    @Override
    public boolean saveCommunity(Community community) {
        return false;
    }

    @Override
    public boolean saveAllCommunities(List<Community> communities) {
        FileWriter file = null;

        JSONArray communitiesArray = new JSONArray();
        for (Community community : communities) {
            JSONObject communityObject = new JSONObject();
            communityObject.put("community", community);
            communitiesArray.add(communityObject);
        }

        try {
            file = new FileWriter(plugin.getDataFolder().getAbsolutePath() + "//communities.json");
            file.write(communitiesArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean loadCommunity(Player player) {
        return false;
    }

    @Override
    public boolean loadAllCommunities() {
        try {
            FileReader reader = new FileReader(plugin.getDataFolder().getAbsolutePath() + "//communities.json");
            Object object = new JSONParser().parse(reader);
            ((JSONArray) object).forEach(community -> _addCommunity((JSONObject) community));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void _addCommunity(JSONObject communities) {
        JSONObject community = (JSONObject) communities.get("community");

        String name = (String) community.get("name");
        UUID ownerId = (UUID) community.get("owner");
        String description = (String) community.get("description");
        List<UUID> members = (List<UUID>) community.get("members");
        List<Object> warps = (List<Object>) community.get("warps");

        Community communityObject2 = new Community(name, ownerId, description, members, warps);
        this.communities.add(communityObject2);
    }

    @Override
    public boolean deleteCommunity(String communityName) {
        return communities.remove(communityName);
    }

    @Override
    public String getInvite(UUID receiver) {
        return invites.get(receiver);
    }

    @Override
    public void createInvite(UUID receiver, String communityName) {
        invites.put(receiver, communityName);
    }

    @Override
    public void deleteInvite(UUID receiver) {
        invites.remove(receiver);
    }
}
