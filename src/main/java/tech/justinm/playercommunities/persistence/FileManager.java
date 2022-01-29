package tech.justinm.playercommunities.persistence;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.core.Role;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FileManager implements ManageData {
    private final PlayerCommunities plugin;
    private final List<Community> communityList;
    private final Map<String, Community> communitiesByName;
    private final Map<UUID, String> communitiesByUuid;
    private final Map<UUID, List<String>> invites;

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
        File config = new File(plugin.getDataFolder(), "config.yml");
        if (!config.exists()) {
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
        List<Object> members = (List<Object>) community.get("members");
        List<Object> warps = (List<Object>) community.get("warps");

        Community communityObject = new Community(name, UUID.fromString(ownerId), description, members, warps);
        this.communityList.add(communityObject);
        this.communitiesByName.put(name, communityObject);
        members.forEach(member -> _addMember((JSONObject) member, name));
    }

    private void _addMember(JSONObject member, String name) {
        String id = (String) member.get("player");
        this.communitiesByUuid.put(UUID.fromString(id), name);
    }

    @Override
    public void deleteCommunity(String communityName) {;
        communityList.removeIf(c -> c.getName().equals(communityName));
        communitiesByUuid.remove(communitiesByName.get(communityName).getOwner());
        communitiesByName.remove(communityName);
    }

    @Override
    public void addMember(UUID member, String communityName) {
        getCommunity(communityName).getMembers().put(member, Role.MEMBER);
        communitiesByUuid.put(member, communityName);
    }

    @Override
    public void removeMember(UUID member) {
        getCommunity(member).getMembers().remove(member);
        communitiesByUuid.remove(member);
    }

    @Override
    public String getInvite(UUID receiver, String name) {
        return invites.get(receiver).stream().filter(i -> i.equalsIgnoreCase(name)).findFirst().orElseThrow(NullPointerException::new);
    }

    @Override
    public void createInvite(UUID receiver, String communityName) {
        List<String> inviteList;
        try {
            inviteList = getInvites(receiver);
            inviteList.add(communityName);
        } catch (Exception e) {
            inviteList = new ArrayList<>();
            inviteList.add(communityName);
        }
        try {
        invites.put(receiver, inviteList);
        } catch (Exception e) {
            inviteList = new ArrayList<>();
            inviteList.add(communityName);
        }
    }

    @Override
    public void clearCache() {
        communityList.clear();
        communitiesByName.clear();
        communitiesByUuid.clear();
        invites.clear();
    }

    @Override
    public void deleteInvite(UUID receiver, String name) {
        invites.get(receiver).removeIf(i -> i.equalsIgnoreCase(name));
    }

    @Override
    public List<String> getInvites(UUID receiver) {
        return invites.get(receiver);
    }
}
