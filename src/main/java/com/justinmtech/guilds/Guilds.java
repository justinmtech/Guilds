package com.justinmtech.guilds;

import com.justinmtech.guilds.persistence.*;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

//TODO Revamp all commands
public final class Guilds extends JavaPlugin {
    private ManageData data;
    private ManageDataNew db;
    private static Economy econ = null;
    private Cache cache;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        if (Objects.requireNonNull(getConfig().getString("storage-type")).equalsIgnoreCase("db")) {
            if (!setupDatabase()) {
                getLogger().log(Level.SEVERE, "Plugin is not set to db storage-type");
                getPluginLoader().disablePlugin(this);
            }
        }

        Objects.requireNonNull(this.getCommand("guilds")).setExecutor(new CommandHandler(this));

        if (!setupEconomy() ) {
            getLogger().log(Level.SEVERE, "Economy not setup!");
            //getServer().getPluginManager().disablePlugin(this);
        }

        cache = new Cache();
        getLogger().log(Level.INFO, "Plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Plugin disabled!");
    }

    public ManageData getData() {
        return data;
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
            db = new Database(
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

    public static Economy getEcon() {
        return econ;
    }

    public ManageDataNew getDb() {
        return db;
    }

    public Cache getCache() {
        return cache;
    }
}
