package com.justinmtech.guilds;

import com.justinmtech.guilds.persistence.FileManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.justinmtech.guilds.persistence.ManageData;

public final class Guilds extends JavaPlugin {
    private ManageData data;

    @Override
    public void onEnable() {
        data = new FileManager(this);
        data.setup();
        data.loadAllGuilds();

        this.getCommand("guilds").setExecutor(new CommandHandler(this));

        System.out.println("Guilds enabled!");
    }

    @Override
    public void onDisable() {
        data.saveAllGuilds();
        data.clearCache();

        System.out.println("Guilds disabled!");
    }

    public ManageData getData() {
        return data;
    }

    public void setData(ManageData data) {
        this.data = data;
    }
}
