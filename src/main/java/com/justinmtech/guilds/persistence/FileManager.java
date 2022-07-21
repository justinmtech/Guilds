package com.justinmtech.guilds.persistence;

import com.justinmtech.guilds.core.Role;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.core.Guild;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

//TODO Update file manager, separate caching to Cache
public class FileManager implements ManageData {
    private final Guilds plugin;
    private final List<Guild> guildList;
    private final Map<String, Guild> guildsByName;
    private final Map<UUID, String> guildsByUuid;
    private final Map<UUID, List<String>> invites;
    private final Map<UUID, Double> transactionConfirmations;

    public FileManager(Guilds plugin) {
        this.plugin = plugin;
        this.invites = new HashMap<>();
        this.guildsByName = new HashMap<>();
        this.guildsByUuid = new HashMap<>();
        this.guildList = new ArrayList<>();
        this.transactionConfirmations = new HashMap<>();
    }

    @Override
    public Map<UUID, Double> getTransactionConfirmations() {
        return transactionConfirmations;
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
    public Guild getGuild(String name) {
        return guildsByName.get(name);
    }

    @Override
    public Guild getGuild(UUID playerUuid) {
        return guildsByName.get(guildsByUuid.get(playerUuid));
    }

    @Override
    public List<Guild> getAllGuilds() {
        return guildList;
    }

    @Override
    public void createGuild(UUID owner, String name) {
        Guild guild = new Guild(owner, name);
        this.guildList.add(guild);
        this.guildsByUuid.put(owner, name);
        this.guildsByName.put(name, guild);
    }

    @Override
    public void saveAllGuilds() {
        FileWriter file = null;

        JSONArray guildsArray = new JSONArray();
        for (Guild guild : guildList) {
            JSONObject guildObject = new JSONObject();
            guildObject.put("guild", guild);
            guildsArray.add(guildObject);
        }

        try {
            file = new FileWriter(plugin.getDataFolder().getAbsolutePath() + "//guilds.json");
            file.write(guildsArray.toJSONString());
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
    public void loadAllGuilds() {
        try {
            FileReader reader = new FileReader(plugin.getDataFolder().getAbsolutePath() + "//guilds.json");
            Object object = new JSONParser().parse(reader);
            ((JSONArray) object).forEach(guild -> _addGuild((JSONObject) guild));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void _addGuild(JSONObject guilds) {
        JSONObject guild = (JSONObject) guilds.get("guild");

        String name = (String) guild.get("name");
        String ownerId = (String) guild.get("owner");
        String description = (String) guild.getOrDefault("description", "No description set!");
        List<Object> members = (List<Object>) guild.getOrDefault("members", new ArrayList<>());
        List<Object> warps = (List<Object>) guild.getOrDefault("warps", new ArrayList<>());
        long level = (long) guild.getOrDefault("level", 1);

        Guild guildObject = new Guild(name, UUID.fromString(ownerId), description, members, warps, (int) level);
        this.guildList.add(guildObject);
        this.guildsByName.put(name, guildObject);
        members.forEach(member -> _addMember((JSONObject) member, name));
    }

    private void _addMember(JSONObject member, String name) {
        String id = (String) member.get("player");
        this.guildsByUuid.put(UUID.fromString(id), name);
    }

    @Override
    public void deleteGuild(String guildName) {
        guildList.removeIf(c -> c.getName().equals(guildName));
        guildsByUuid.remove(guildsByName.get(guildName).getOwner());
        guildsByName.remove(guildName);
    }

    @Override
    public void addMember(UUID member, String guildName) {
        getGuild(guildName).getMembers().put(member, Role.MEMBER);
        guildsByUuid.put(member, guildName);
    }

    @Override
    public void removeMember(UUID member) {
        getGuild(member).getMembers().remove(member);
        guildsByUuid.remove(member);
    }

    @Override
    public String getInvite(UUID receiver, String name) {
        return invites.get(receiver).stream().filter(i -> i.equalsIgnoreCase(name)).findFirst().orElseThrow(NullPointerException::new);
    }

    @Override
    public void createInvite(UUID receiver, String guildName) {
        List<String> inviteList;
        try {
            inviteList = getInvites(receiver);
            inviteList.add(guildName);
        } catch (Exception e) {
            inviteList = new ArrayList<>();
            inviteList.add(guildName);
        }
        try {
        invites.put(receiver, inviteList);
        } catch (Exception e) {
            inviteList = new ArrayList<>();
            inviteList.add(guildName);
        }
    }

    @Override
    public void clearCache() {
        guildList.clear();
        guildsByName.clear();
        guildsByUuid.clear();
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




