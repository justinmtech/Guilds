package com.justinmtech.guilds;

import com.justinmtech.guilds.persistence.*;
import com.justinmtech.guilds.persistence.database.DatabaseCache;
import com.justinmtech.guilds.persistence.database.Database;
import com.justinmtech.guilds.persistence.file.FileManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;

//TODO Revamp all commands
//TODO Tie both file and db under one interface
//TODO Choose which implementation of the interface is used on startup
public final class Guilds extends JavaPlugin {
    private ManageDataNew data;
    private static Economy econ = null;
    private DatabaseCache cache;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        if (Objects.requireNonNull(getConfig().getString("storage-type")).equalsIgnoreCase("db")) {
            setupDatabase();
        } else {
            setupFilesystem();
        }

        Objects.requireNonNull(this.getCommand("guilds")).setExecutor(new CommandHandler(this));

        if (!setupEconomy() ) {
            getLogger().log(Level.SEVERE, "Economy not setup!");
            getServer().getPluginManager().disablePlugin(this);
        }

        cache = new DatabaseCache();
        getLogger().log(Level.INFO, "Plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Plugin disabled!");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    private boolean setupDatabase() {
        try {
            data = new Database(
                    getConfig().getString("db.host"),
                    getConfig().getInt("db.port"),
                    getConfig().getString("db.username"),
                    getConfig().getString("db.password"),
                    getConfig().getString("db.table"),
                    getConfig().getString("db.database"));
            return true;
        } catch (SQLException e) {
            getLogger().log(Level.SEVERE, e.getMessage());
            getLogger().log(Level.SEVERE, "Plugin shutting down due to database error. Check your database settings.");
            getPluginLoader().disablePlugin(this);
        }
        return false;
    }

    private boolean setupFilesystem() {
        try {
            data = new FileManager(this);
            return true;
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "File system could not be initialized..");
            getPluginLoader().disablePlugin(this);
        }
        return false;
    }

    public static Economy getEcon() {
        return econ;
    }

    public ManageDataNew getData() {
        return data;
    }

    public DatabaseCache getCache() {
        return cache;
    }
}
