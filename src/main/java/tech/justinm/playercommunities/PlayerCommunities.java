package tech.justinm.playercommunities;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import tech.justinm.playercommunities.commands.*;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.core.Invite;
import tech.justinm.playercommunities.core.Warp;
import tech.justinm.playercommunities.persistence.FileManager;
import tech.justinm.playercommunities.persistence.ManageData;

import java.io.FileNotFoundException;
import java.io.IOException;

public final class PlayerCommunities extends JavaPlugin {
    private ManageData data;

    @Override
    public void onEnable() {
        data = new FileManager(this);
        data.setup();
        try {
            data.loadAllCommunities();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.getCommand("pccreate").setExecutor(new CreateCommunity(this));
        this.getCommand("pclist").setExecutor(new ListCommunities(this));
        this.getCommand("pcdelete").setExecutor(new DeleteCommunity(this));
        this.getCommand("pcsetwarp").setExecutor(new SetWarp(this));
        this.getCommand("pcwarp").setExecutor(new GoToWarp(this));
        this.getCommand("pcsetdesc").setExecutor(new SetDescription(this));
        this.getCommand("pcinfo").setExecutor(new GetCommunityInfo(this));
        this.getCommand("pcinvite").setExecutor(new InvitePlayer(this));
        this.getCommand("pcaccept").setExecutor(new ProcessInvite(this));

        System.out.println("PlayerCommunities enabled!");
    }

    @Override
    public void onDisable() {
        try {
            data.saveAllCommunities(data.getAllCommunities());
        } catch (IOException e) {
            e.printStackTrace();
        }
        data.getAllCommunities().clear();
        data.getAllInvites().clear();
        System.out.println("PlayerCommunities disabled!");
    }

    public ManageData getData() {
        return data;
    }

    public void setData(ManageData data) {
        this.data = data;
    }
}
