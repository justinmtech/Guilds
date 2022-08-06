package com.justinmtech.guilds.persistence.database;

import com.justinmtech.guilds.core.GPlayer;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.core.Role;
import com.justinmtech.guilds.core.Warp;
import com.justinmtech.guilds.persistence.ManageData;

import java.sql.*;
import java.util.*;

@SuppressWarnings("UnusedReturnValue")
//TODO /guild nonGuildName returns error
public class Database implements ManageData {
    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String playerTable;
    private final String guildTable;
    private final String warpTable;
    private final String inviteTable;
    private final String database;

    public Database(String host, int port, String username, String password, String table, String database) throws SQLException {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.playerTable = table + "_players";
        this.guildTable = table + "_guilds";
        this.warpTable = table + "_warps";
        this.inviteTable = table + "_invites";
        this.database = database;
        connect();
        createAllTables();
    }

    public Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException("Could not connect");
        }
    }

    public boolean createAllTables() throws SQLException {
        createGuildTable();
        createPlayerTable();
        createWarpsTable();
        createInvitesTable();
        return true;
    }

    private boolean createGuildTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + guildTable +
                " (id VARCHAR(64) NOT NULL, " +
                "description VARCHAR(128) NOT NULL, " +
                "level INT NOT NULL DEFAULT 1, " +
                "PRIMARY KEY (id))";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Could not create guild table");
        }
    }

    private boolean createPlayerTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + playerTable + " (id VARCHAR(64) PRIMARY KEY, " +
                "role VARCHAR(12) NOT NULL, " +
                "guild_id VARCHAR(64) NOT NULL, INDEX guild_ind (guild_id), FOREIGN KEY (guild_id) REFERENCES " + guildTable + "(id) ON DELETE CASCADE ON UPDATE CASCADE)";
                //"PRIMARY KEY (id))";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Could not create player table");
        }
    }

    @Override
    public boolean saveGuild(Guild guild) {
        try {
            if (rowExists(guildTable, guild.getName())) {
                return updateGuild(guild);
            } else {
                return insertGuild(guild);
            }
        } catch (SQLException e) {
            return false;
        }
    }

    private boolean updateGuild(Guild guild) throws SQLException {
        String sql = "UPDATE " + guildTable + " SET id = ?, description = ?, level = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, guild.getName());
            stat.setString(2, guild.getDescription());
            stat.setInt(3, guild.getLevel());
            stat.setString(4, guild.getName());
            stat.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Could not update guild: " + guild.getName());
        }
    }

    private boolean rowExists(String table, String id) {
        String sql = "SELECT EXISTS (SELECT * FROM " + table + " WHERE id = ?)";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, id);
            stat.execute();
            ResultSet rs = stat.getResultSet();
            if (rs.next()) return rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean rowExists(String table, String playerId, String guildId) {
        String sql = "SELECT EXISTS (SELECT * FROM " + table + " WHERE player_id = ? AND guild_id = ?)";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, playerId);
            stat.setString(2, guildId);
            stat.execute();
            ResultSet rs = stat.getResultSet();
            if (rs.next()) return rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean insertGuild(Guild guild) {
        String sql = "INSERT INTO " + guildTable + " VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, guild.getName());
            stat.setString(2, guild.getDescription());
            stat.setInt(3, guild.getLevel());
            stat.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteGuild(String id) {
        String sql = "DELETE FROM " + guildTable + " WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, id);
            stat.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean savePlayer(GPlayer player) {
        try {
            if (rowExists(playerTable, player.getGuildId())) {
                return updatePlayer(player.getUuid(), player.getRole().toString(), player.getGuildId());
            } else {
                return insertPlayer(player.getUuid(), player.getRole().toString(), player.getGuildId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean insertPlayer(UUID id, String role, String guildId) throws SQLException {
        String sql = "INSERT INTO " + playerTable + " VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, id.toString());
            stat.setString(2, role);
            stat.setString(3, guildId);
            stat.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Could not add player to the database");
        }
    }

    private boolean updatePlayer(UUID id, String role, String guildId) throws SQLException {
        String sql = "UPDATE " + playerTable + "SET role = ?, guild_id = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, role);
            stat.setString(2, guildId);
            stat.setString(3, id.toString());
            stat.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Could not add player to the database");
        }
    }

    @Override
    public boolean deletePlayer(String id) {
        String sql = "DELETE FROM " + playerTable + " WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, id);
            stat.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean saveWarp(String id, String world, double x, double y, double z, float yaw, float pitch, String guildId) {
        if (rowExists(warpTable, id)) {
            return updateWarp(id, world, x, y, z, yaw, pitch, guildId);
        } else {
            return insertWarp(id, world, x, y, z, yaw, pitch, guildId);
        }
    }

    private boolean insertWarp(String id, String world, double x, double y, double z, float yaw, float pitch, String guildId) {
        String sql = "INSERT INTO " + warpTable + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect();PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, id);
            stat.setString(2, world);
            stat.setDouble(3, x);
            stat.setDouble(4, y);
            stat.setDouble(5, z);
            stat.setFloat(6, yaw);
            stat.setFloat(7, pitch);
            stat.setString(8, guildId);
            stat.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Warp> getWarp(UUID uuid, String warpId) {
        Optional<Guild> guild = getGuild(uuid);
        if (guild.isEmpty()) return Optional.empty();
        String guildId = guild.get().getName();
        String sql = "SELECT id, world, x, y, z, yaw, pitch FROM " + warpTable + " WHERE id = ? AND guild_id = ?";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, warpId);
            stat.setString(2, guildId);
            stat.execute();
            ResultSet rs = stat.getResultSet();
            if (rs.next()) {
                String id = rs.getString(1);
                String world = rs.getString(2);
                double x = rs.getDouble(3);
                double y = rs.getDouble(4);
                double z = rs.getDouble(5);
                float yaw = rs.getFloat(6);
                float pitch = rs.getFloat(7);
                return Optional.of(new Warp(id, world, x, y, z, yaw, pitch));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private boolean updateWarp(String id, String world, double x, double y, double z, float yaw, float pitch, String guildId) {
        String sql = "UPDATE " + warpTable + " SET world = ?, x = ?, y = ?, z = ?, yaw = ?, pitch = ? WHERE guild_id = ? AND id = ?";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, world);
            stat.setDouble(2, x);
            stat.setDouble(3, y);
            stat.setDouble(4, z);
            stat.setFloat(5, yaw);
            stat.setFloat(6, pitch);
            stat.setString(7, guildId);
            stat.setString(8, id);
            stat.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<Guild> getGuild(String id) {
        Guild guild = new Guild(id);
        Optional<Guild> guildData = loadGuildData(id);
        if (guildData.isPresent()) {
            guild.setLevel(guildData.get().getLevel());
            guild.setDescription(guildData.get().getDescription());
        } else {
            return Optional.empty();
        }
        Optional<Map<UUID, Role>> members = loadGuildMembers(id);
        if (members.isPresent()) {
            guild.setMembers(members.get());
            for (UUID uuid : members.get().keySet()) {
                if (members.get().get(uuid).equals(Role.LEADER)) {
                    guild.setOwner(uuid);
                }
            }
        } else {
            return Optional.empty();
        }
        Optional<Map<String, Warp>> warps = loadGuildWarps(id);
        warps.ifPresent(guild::setWarps);
        return Optional.of(guild);
    }

    @Override
    public Optional<Guild> getGuild(UUID uuid) {
        String guildId;
        Optional<GPlayer> player = getPlayer(uuid);
        if (player.isPresent()) {
            guildId = player.get().getGuildId();
            return getGuild(guildId);
        }
        return Optional.empty();
    }

    @Override
    public Optional<GPlayer> getPlayer(UUID id) {
        String sql = "SELECT role, guild_id FROM " + playerTable + " WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stat = connect().prepareStatement(sql)) {
            stat.setString(1, id.toString());
            stat.execute();
            ResultSet rs = stat.getResultSet();
            if (rs.next()) {
                String role = rs.getString(1);
                String guildId = rs.getString(2);
                return Optional.of(new GPlayer(id, guildId, Role.valueOf(role)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @SuppressWarnings("unused")
    @Override
    public List<Guild> getAllGuilds() {
        Set<String> ids = getGuildIds();
        List<Guild> guilds = new ArrayList<>();
        for (String id : ids) {
            Optional<Guild> guild = getGuild(id);
            guild.ifPresent(guilds::add);
        }
        return guilds;
    }

    @SuppressWarnings("unused")
    @Override
    public boolean saveAllData() {
        return false;
    }

    @SuppressWarnings("unused")
    @Override
    public boolean loadAllData() {
        return false;
    }

    @SuppressWarnings("unused")
    @Override
    public boolean hasInvite(UUID playerId, String guildId) {
        String sql = "SELECT EXISTS(SELECT * FROM " + inviteTable + " WHERE player_id = ? AND guild_id = ?";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, playerId.toString());
            stat.setString(2, guildId);
            stat.execute();
            ResultSet rs = stat.getResultSet();
            if (rs.next()) return rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Set<String> getGuildIds() {
        String sql = "SELECT id FROM " + guildTable;
        Set<String> ids = new HashSet<>();
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.execute();
            ResultSet rs = stat.getResultSet();
            while (rs.next()) {
                ids.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
        return ids;
    }


    private Optional<Guild> loadGuildData(String id) {
        Guild guild = new Guild(id);
        String sql = "SELECT description, level FROM " + guildTable + " WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, id);
            stat.execute();
            ResultSet rs = stat.getResultSet();
            if (rs.next()) {
                String desc = rs.getString(1);
                int level = rs.getInt(2);
                guild.setDescription(desc);
                guild.setLevel(level);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(guild);
    }

    private Optional<Map<UUID, Role>> loadGuildMembers(String id) {
        String sql = "SELECT * FROM " + playerTable + " WHERE guild_id = ?";
        Map<UUID, Role> members = new HashMap<>();
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, id);
            stat.execute();
            ResultSet rs = stat.getResultSet();
            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString(1));
                Role role = Role.valueOf(rs.getString(2));
                members.put(uuid, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        if (members.size() == 0) return Optional.empty();
        return Optional.of(members);
    }

    private Optional<Map<String, Warp>> loadGuildWarps(String id) {
        String sql = "SELECT id, world, x, y, z, yaw, pitch FROM " + warpTable + " WHERE guild_id = ?";
        Map<String, Warp> warps = new HashMap<>();
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, id);
            stat.execute();
            ResultSet rs = stat.getResultSet();
            while (rs.next()) {
                String warpId = rs.getString(1);
                String world = rs.getString(2);
                double x = rs.getDouble(3);
                double y = rs.getDouble(4);
                double z = rs.getDouble(5);
                float yaw = rs.getFloat(6);
                float pitch = rs.getFloat(7);
                warps.put(warpId, new Warp(warpId, world, x, y, z, yaw, pitch));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(warps);
    }

    public boolean deleteWarp(String id) throws SQLException {
        String sql = "DELETE FROM " + warpTable + " WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, id);
            stat.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Could not delete guild from database");
        }
    }

    private void createWarpsTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + warpTable + "(id VARCHAR(64) PRIMARY KEY, " +
                "world VARCHAR(64) NOT NULL, x DOUBLE NOT NULL, y DOUBLE NOT NULL, z DOUBLE NOT NULL, " +
                "yaw FLOAT NOT NULL, pitch FLOAT NOT NULL, " +
                "guild_id VARCHAR(64) NOT NULL, INDEX guild_ind (guild_id), FOREIGN KEY (guild_id) REFERENCES " + guildTable + "(id) ON DELETE CASCADE ON UPDATE CASCADE)";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Could not create warp table");
        }
    }

    private void createInvitesTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + inviteTable +
                "(player_id VARCHAR(64) NOT NULL, INDEX player_ind (player_id), FOREIGN KEY (player_id) REFERENCES " + playerTable + "(id) ON DELETE CASCADE, " +
                "guild_id VARCHAR(64) NOT NULL UNIQUE, INDEX guild_ind (guild_id), FOREIGN KEY (guild_id) REFERENCES " + guildTable + "(id) ON DELETE CASCADE ON UPDATE CASCADE)";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Could not create invites table");
        }
    }

    @Override
    public boolean saveInvite(UUID playerId, String guildId) {
        if (rowExists(inviteTable, playerId.toString(), guildId)) {
            return updateInvite(playerId.toString(), guildId);
        } else {
            return insertInvite(playerId.toString(), guildId);
        }
    }

    private boolean updateInvite(String playerId, String guildId) {
        String sql = "UPDATE " + inviteTable + " WHERE player_id = ? AND guild_id = ?";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, playerId);
            stat.setString(2, guildId);
            stat.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean insertInvite(String playerId, String guildId) {
        String sql = "INSERT INTO " + inviteTable + " VALUES (?, ?)";
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, playerId);
            stat.setString(2, guildId);
            stat.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteInvite(UUID playerId, String guildId) {
        String sql = "DELETE FROM " + inviteTable + " WHERE player_id = ? AND guild_id = ?";
        try (Connection conn = connect(); PreparedStatement stat = connect().prepareStatement(sql)) {
            stat.setString(1, playerId.toString());
            stat.setString(2, guildId);
            stat.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings("unused")
    @Override
    public boolean deleteWarp(String guildId, String warpId) {
        return false;
    }

    private boolean dropTable(String name) throws SQLException {
        String sql = "DROP TABLE " + name;
        try (Connection conn = connect(); PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Could not drop table: " + name);
        }
    }

    public boolean dropAllTables() throws SQLException {
        dropTable(inviteTable);
        dropTable(playerTable);
        dropTable(warpTable);
        dropTable(guildTable);
        return true;
    }

}
