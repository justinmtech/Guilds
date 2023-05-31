package com.justinmtech.guilds;

import com.justinmtech.guilds.bukkit.commands.CommandHandler;
import com.justinmtech.guilds.bukkit.commands.TabCompleter;
import com.justinmtech.guilds.persistence.*;
import com.justinmtech.guilds.service.TransactionCacheImp;
import com.justinmtech.guilds.persistence.database.Database;
import com.justinmtech.guilds.persistence.file.FileManager;
import com.justinmtech.guilds.persistence.file.PlayerListener;
import com.justinmtech.guilds.bukkit.util.Placeholders;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;

public final class Guilds extends JavaPlugin {
    private GuildsRepository data;
    private static Economy econ = null;
    private TransactionCacheImp cache;

    public Guilds() {
        super();
    }

    protected Guilds(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        boolean dbEnabled;
        if (Objects.requireNonNull(getConfig().getString("storage-type", "file")).equalsIgnoreCase("db")) {
            setupDatabase();
            dbEnabled = true;
        } else {
            setupFilesystem();
            dbEnabled = false;
        }

        if (!dbEnabled) {
            data.loadAllData();
            getServer().getPluginManager().registerEvents(new PlayerListener(data), this);
            autoSaveTask();
        }

        Objects.requireNonNull(this.getCommand("guilds")).setExecutor(new CommandHandler(this));
        Objects.requireNonNull(getCommand("guilds")).setTabCompleter(new TabCompleter(this));

        if (!setupEconomy()) {
            getLogger().log(Level.SEVERE, "Economy not setup!");
            getServer().getPluginManager().disablePlugin(this);
        }

        cache = new TransactionCacheImp();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders(getData()).register();
        }
        getLogger().log(Level.INFO, "Plugin enabled!");
    }

    @Override
    public void onDisable() {
        data.saveAllData();
        getLogger().log(Level.INFO, "Plugin disabled!");
    }

    private void autoSaveTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                data.saveAllData();
                getLogger().log(Level.INFO, "Auto-saved data.");
            }
        }.runTaskTimer(this, 6000L, 6000L);
    }

    private boolean setupEconomy() {
        //if (testing) return true;
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

    @SuppressWarnings("UnusedReturnValue")
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

    @SuppressWarnings("UnusedReturnValue")
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

    public GuildsRepository getData() {
        return data;
    }

    public TransactionCacheImp getCache() {
        return cache;
    }
}
