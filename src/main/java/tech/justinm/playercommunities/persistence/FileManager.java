package tech.justinm.playercommunities.persistence;

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
    private final List<Community> communityList;
    private final Map<String, Community> communitiesByName;
    private final Map<UUID, String> communitiesByUuid;
    private final Map<UUID, String> invites;

    public FileManager(PlayerCommunities plugin) {
        this.plugin = plugin;
        this.invites = new HashMap<>();
        this.communitiesByName = new HashMap<>();
        this.communitiesByUuid = new HashMap<>();
        this.communityList = new ArrayList<>();
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
        return communitiesByName.get(name);
    }

    @Override
    public Community getCommunity(UUID playerUuid) {
        return communitiesByName.get(communitiesByUuid.get(playerUuid));
    }

    @Override
    public List<Community> getAllCommunities() {
        return communityList;
    }

    @Override
    public void createCommunity(UUID owner, String name) {
        Community community = new Community(owner, name);
        this.communityList.add(community);
        this.communitiesByUuid.put(owner, name);
        this.communitiesByName.put(name, community);
    }


    @Override
    public void saveAllCommunities() {
        FileWriter file = null;

        JSONArray communitiesArray = new JSONArray();
        for (Community community : communityList) {
            JSONObject communityObject = new JSONObject();
            communityObject.put("community", community);
            communitiesArray.add(communityObject);
        }

        try {
            file = new FileWriter(plugin.getDataFolder().getAbsolutePath() + "//communities.json");
            file.write(communitiesArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void loadAllCommunities() {
        try {
            FileReader reader = new FileReader(plugin.getDataFolder().getAbsolutePath() + "//communities.json");
            Object object = new JSONParser().parse(reader);
            ((JSONArray) object).forEach(community -> _addCommunity((JSONObject) community));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void _addCommunity(JSONObject communities) {
        JSONObject community = (JSONObject) communities.get("community");

        String name = (String) community.get("name");
        String ownerId = (String) community.get("owner");
        String description = (String) community.get("description");
        List<String> members = (List<String>) community.get("members");
        List<Object> warps = (List<Object>) community.get("warps");

        List<UUID> memberUuids = new ArrayList<>();
        for (String member : members) {
            memberUuids.add(UUID.fromString(member));
        }
        Community communityObject = new Community(name, UUID.fromString(ownerId), description, memberUuids, warps);
        this.communityList.add(communityObject);
        this.communitiesByName.put(name, communityObject);
        for (UUID member : memberUuids) {
        this.communitiesByUuid.put(member, name);
        }
    }

    @Override
    public void deleteCommunity(String communityName) {;
        communityList.removeIf(c -> c.getName().equals(communityName));
        communitiesByUuid.remove(communitiesByName.get(communityName).getOwner());
        communitiesByName.remove(communityName);
    }

    @Override
    public void addMember(UUID member, String communityName) {
        getCommunity(communityName).getMembers().add(member);
        communitiesByUuid.put(member, communityName);
    }

    @Override
    public void removeMember(UUID member) {
        getCommunity(member).getMembers().remove(member);
        communitiesByUuid.remove(member);
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
    public void clearCache() {
        communityList.clear();
        communitiesByName.clear();
        communitiesByUuid.clear();
        invites.clear();
    }

    @Override
    public void deleteInvite(UUID receiver) {
        invites.remove(receiver);
    }
}
