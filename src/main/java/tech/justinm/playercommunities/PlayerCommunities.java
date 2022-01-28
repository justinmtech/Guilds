package tech.justinm.playercommunities;

import org.bukkit.plugin.java.JavaPlugin;
import tech.justinm.playercommunities.persistence.FileManager;
import tech.justinm.playercommunities.persistence.ManageData;

public final class PlayerCommunities extends JavaPlugin {
    private ManageData data;

    @Override
    public void onEnable() {
        data = new FileManager(this);
        data.setup();
        data.loadAllCommunities();

        this.getCommand("playercommunities").setExecutor(new CommandHandler(this));

        System.out.println("PlayerCommunities enabled!");
    }

    @Override
    public void onDisable() {
        data.saveAllCommunities();
        data.clearCache();

        System.out.println("PlayerCommunities disabled!");
    }

    public ManageData getData() {
        return data;
    }

    public void setData(ManageData data) {
        this.data = data;
    }
}
