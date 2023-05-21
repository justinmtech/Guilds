package com.justinmtech.guilds.persistence.file;

import com.justinmtech.guilds.Guilds;
import com.justinmtech.guilds.core.GPlayer;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.core.Role;
import com.justinmtech.guilds.core.Warp;
import com.justinmtech.guilds.persistence.ManageData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("unchecked")
public class FileManager implements ManageData {
    private final Guilds plugin;
    private final FileCache cache;

    public FileManager(Guilds plugin) {
        this.plugin = plugin;
        this.cache = new FileCache();
    }

    public FileCache getCache() {
        return cache;
    }

    @Override
    public Optional<Guild> getGuild(String name) {
        return getCache().getGuild(name);
    }

    @Override
    public Optional<Guild> getGuild(UUID playerUuid) {
        if (getCache().getPlayer(playerUuid).isEmpty()) return Optional.empty();
        if (!getCache().getPlayer(playerUuid).get().hasGuild()) return Optional.empty();
        String guildId = getCache().getPlayer(playerUuid).get().getGuildId();
        return getCache().getGuild(guildId);
    }

    @Override
    public Optional<GPlayer> getPlayer(UUID id) {
        return getCache().getPlayer(id);
    }

    @Override
    public Optional<Warp> getWarp(UUID id, String warpId) {
        if (getCache().getPlayer(id).isEmpty()) return Optional.empty();
        String guildId = getCache().getPlayer(id).get().getGuildId();
        if (guildId == null) {
            return Optional.empty();
        } else {
            if (getCache().getGuild(guildId).isEmpty()) return Optional.empty();
            Warp warp = getCache().getGuild(guildId).get().getWarps().get(warpId);
            return warp != null ? Optional.of(warp) : Optional.empty();
        }
    }

    @Override
    public boolean hasInvite(UUID uuid, String guildId) {
        if (getCache().getPlayer(uuid).isEmpty()) return false;
        return getCache().getPlayer(uuid).get().hasInvite(guildId);
    }

    @Override
    public List<Guild> getAllGuilds() {
        List<Guild> guildList = new ArrayList<>();
        Map<String, Guild> guilds = getCache().getGuilds();
        for (String id : guilds.keySet()) {
            guildList.add(guilds.get(id));
        }
        return guildList;
    }

    @Override
    public boolean saveAllData() {
        saveAllGuilds();
        return true;
    }

    @Override
    public boolean loadAllData() {
        loadAllGuilds();
        return true;
    }

    @Override
    public boolean saveGuild(Guild guild) {
        if (getCache().getGuilds().containsKey(guild.getName())) {
            getCache().getGuilds().replace(guild.getName(), guild);
        } else {
            getCache().addGuild(guild);
        }
        return true;
    }

    @Override
    public boolean savePlayer(GPlayer player) {
        if (getCache().playerExists(player.getUuid())) {
            getCache().getGuildPlayers().replace(player.getUuid(), player);
        } else {
            getCache().addPlayer(player.getUuid(), player.getGuildId(), player.getRole());
        }
        return true;
    }

    public void saveAllGuilds() {
        FileWriter file = null;
        Set<String> guildIds = getCache().getGuilds().keySet();
        ArrayList<Guild> guildList = new ArrayList<>();
        for (String guild : guildIds) {
            if (getCache().getGuild(guild).isEmpty()) continue;
            guildList.add(getCache().getGuild(guild).get());
        }

        JSONArray guildsArray = new JSONArray();
        for (Guild guild : guildList) {
            JSONObject guildJson = new JSONObject();
            guildJson.put("guild", guild);
            guildsArray.add(guildJson);
        }

        try {
            file = new FileWriter(plugin.getDataFolder().getAbsolutePath() + "//guilds.json");
            file.write(guildsArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(file).flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadAllGuilds() {
        File file = new File(plugin.getDataFolder().getAbsolutePath() + "//guilds.json");
        saveAllGuilds();
        try {
            if (!file.exists()) file.createNewFile();
            FileReader reader = new FileReader(plugin.getDataFolder().getAbsolutePath() + "//guilds.json");
            Object object = new JSONParser().parse(reader);
            ((JSONArray) object).forEach(guild -> _addGuild((JSONObject) guild));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void _addGuild(JSONObject guilds) {
        JSONObject guild = (JSONObject) guilds.getOrDefault("guild", null);
        String name = (String) guild.getOrDefault("name", null);
        String ownerId = (String) guild.getOrDefault("owner", null);
        String description = (String) guild.getOrDefault("description", "No description set!");
        ArrayList<Object> members = (ArrayList<Object>) guild.getOrDefault("members", new ArrayList<>());
        ArrayList<Object> warps = (ArrayList<Object>) guild.getOrDefault("warps", new ArrayList<>());
        long level = (long) guild.getOrDefault("level", 1);

        Guild guildObject = new Guild(name, UUID.fromString(ownerId), description, members, warps, (int) level);
        _addMembers(members, name);

        getCache().addGuild(guildObject);
    }

    private void _addMembers(ArrayList<Object> memberObjects, String name) {
        memberObjects.forEach(member -> _addMember((JSONObject) member, name));
    }

    private void _addMember(JSONObject member, String name) {
        String id = (String) member.get("player");
        String role = (String) member.get("role");
        getCache().addPlayer(UUID.fromString(id), name, Role.valueOf(role));
    }

    @Override
    public boolean deleteGuild(String guildName) {
        getCache().removeGuildAndItsPlayers(guildName);
        return true;
    }

    @Override
    public boolean saveWarp(String id, String world, double x, double y, double z, float yaw, float pitch, String guildId) {
        Optional<Guild> guild = getCache().getGuild(guildId);
        if (guild.isEmpty()) return false;
        if (guild.get().getWarps().containsKey(id)) {
            guild.get().getWarps().replace(id, new Warp(id, world, x, y, z, yaw, pitch));
        } else {
            guild.get().getWarps().put(id, new Warp(id, world, x, y, z, yaw, pitch));
        }
        return true;
    }

    @Override
    public boolean deletePlayer(String id) {
        getCache().removePlayer(UUID.fromString(id));
        return true;
    }

    @Override
    public boolean saveInvite(UUID receiver, String guildName) {
        if (getCache().getPlayer(receiver).isEmpty()) return false;
        getCache().getPlayer(receiver).get().addInvite(guildName);
        return true;
    }

    @Override
    public boolean deleteInvite(UUID receiver, String name) {
        if (getCache().getPlayer(receiver).isEmpty()) return false;
        getCache().getPlayer(receiver).get().removeInvite(name);
        return true;
    }

    @Override
    public boolean deleteWarp(String guildId, String warpId) {
        if (getCache().getGuild(guildId).isEmpty()) return false;
        getCache().getGuild(guildId).get().removeWarp(warpId);
        return true;
    }
}




