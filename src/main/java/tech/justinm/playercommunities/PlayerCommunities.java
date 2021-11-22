package tech.justinm.playercommunities;

import org.bukkit.plugin.java.JavaPlugin;
import tech.justinm.playercommunities.base.Community;
import tech.justinm.playercommunities.commands.*;

import java.util.ArrayList;
import java.util.List;

public final class PlayerCommunities extends JavaPlugin {
    private List<Community> communities;

    @Override
    public void onEnable() {
        // Plugin startup logic

        //Get community data from persistent data storage
        communities = new ArrayList<>();
        System.out.println("PlayerCommunities enabled!");
        this.getCommand("pccreate").setExecutor(new CreateCommunity(this));
        this.getCommand("pclist").setExecutor(new ListCommunities(this));
        this.getCommand("pcdelete").setExecutor(new DeleteCommunity(this));
        this.getCommand("pcsetwarp").setExecutor(new SetCommunityWarp(this));
        this.getCommand("pcwarp").setExecutor(new GoToWarp(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("PlayerCommunities disabled!");
    }

    public List<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(List<Community> communities) {
        this.communities = communities;
    }
}
