package com.justinmtech.guilds.persistence.file;

import com.justinmtech.guilds.core.GPlayer;
import com.justinmtech.guilds.core.Role;
import com.justinmtech.guilds.core.Warp;
import com.justinmtech.guilds.persistence.ManageDataNew;
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
public class FileManager implements ManageDataNew {
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
        return Optional.of(getCache().getGuild(name));
    }

    @Override
    public Optional<Guild> getGuild(UUID playerUuid) {
        String guildId = getCache().getPlayer(playerUuid).getGuildId();
        return Optional.of(getCache().getGuild(guildId));
    }

    @Override
    public Optional<GPlayer> getPlayer(UUID id) {
        GPlayer player = getCache().getPlayer(id);
        if (player == null) {
            return Optional.empty();
        } else {
            return Optional.of(player);
        }
    }

    @Override
    public Optional<Warp> getWarp(UUID id, String warpId) {
        String guildId = getCache().getPlayer(id).getGuildId();
        if (guildId == null) {
            return Optional.empty();
        } else {
            return Optional.of(getCache().getGuild(guildId).getWarps().get(warpId));
        }
    }

    @Override
    public boolean hasInvite(UUID uuid, String guildId) {
        return getCache().getPlayer(uuid).hasInvite(guildId);
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
        getCache().getGuilds().replace(guild.getName(), guild);
        return true;
    }

    @Override
    public boolean savePlayer(GPlayer player) {
        getCache().getPlayers().replace(player.getUuid(), new GPlayer(player.getUuid(), player.getGuildId(), player.getRole()));
        return true;
    }

    //TODO Implement saving
    public void saveAllGuilds() {
        FileWriter file = null;
        Set<String> guildIds = getCache().getGuilds().keySet();
        ArrayList<Guild> guildList = new ArrayList<>();
        for (String guild : guildIds) {
            guildList.add(getCache().getGuild(guild));
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
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO Implement loading
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
        JSONObject guild = (JSONObject) guilds.getOrDefault("guild", null);
        String name = (String) guild.getOrDefault("name", null);
        String ownerId = (String) guild.getOrDefault("owner", null);
        String description = (String) guild.getOrDefault("description", "No description set!");
        ArrayList<Object> members = (ArrayList<Object>) guild.getOrDefault("members", new ArrayList<>());
        ArrayList<Object> warps = (ArrayList<Object>) guild.getOrDefault("warps", new ArrayList<>());
        long level = (long) guild.getOrDefault("level", 1);

        Guild guildObject = new Guild(name, UUID.fromString(ownerId), description, members, warps, (int) level);
        getCache().addGuild(guildObject);
    }

    @Override
    public boolean deleteGuild(String guildName) {
        getCache().removeGuild(guildName);
        return true;
    }

    @Override
    public boolean saveInvite(UUID receiver, String guildName) {
        getCache().getPlayer(receiver).addInvite(guildName);
        return true;
    }

    @Override
    public boolean saveWarp(String id, String world, double x, double y, double z, float yaw, float pitch, String guildId) {
        Guild guild = getCache().getGuild(guildId);
        if (guild == null) return false;
        if (guild.getWarps().containsKey(id)) {
            getCache().getGuild(guildId).getWarps().replace(id, new Warp(id, world, x, y, z, yaw, pitch));
        } else {
            getCache().getGuild(guildId).getWarps().put(id, new Warp(id, world, x, y, z, yaw, pitch));
        }
        return true;
    }

    @Override
    public boolean deletePlayer(String id) {
        getCache().removePlayer(UUID.fromString(id));
        return true;
    }

    @Override
    public boolean deleteInvite(UUID receiver, String name) {
        getCache().getPlayer(receiver).removeInvite(name);
        return true;
    }

    @Override
    public boolean deleteWarp(String guildId, String warpId) {
        getCache().getGuild(guildId).removeWarp(warpId);
        return true;
    }
}




