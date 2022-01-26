package tech.justinm.playercommunities;

import org.bukkit.plugin.java.JavaPlugin;
import tech.justinm.playercommunities.commands.*;
import tech.justinm.playercommunities.persistence.FileManager;
import tech.justinm.playercommunities.persistence.ManageData;

public final class PlayerCommunities extends JavaPlugin {
    private ManageData data;

    @Override
    public void onEnable() {
        data = new FileManager(this);
        data.setup();
        data.loadAllCommunities();

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
        data.saveAllCommunities(data.getAllCommunities());
        data.getAllCommunities().clear();

        System.out.println("PlayerCommunities disabled!");
    }

    public ManageData getData() {
        return data;
    }

    public void setData(ManageData data) {
        this.data = data;
    }
}
