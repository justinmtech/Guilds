package tech.justinm.playercommunities;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import tech.justinm.playercommunities.commands.*;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.core.Invite;
import tech.justinm.playercommunities.core.Warp;
import tech.justinm.playercommunities.persistence.FlatfileDataHandler;
import tech.justinm.playercommunities.persistence.ManageData;

public final class PlayerCommunities extends JavaPlugin {
    private ManageData data;

    @Override
    public void onEnable() {
        data = new FlatfileDataHandler(this);

        this.getCommand("pccreate").setExecutor(new CreateCommunity(this));
        this.getCommand("pclist").setExecutor(new ListCommunities(this));
        this.getCommand("pcdelete").setExecutor(new DeleteCommunity(this));
        this.getCommand("pcsetwarp").setExecutor(new SetWarp(this));
        this.getCommand("pcwarp").setExecutor(new GoToWarp(this));
        this.getCommand("pcsetdesc").setExecutor(new SetDescription(this));
        this.getCommand("pcinfo").setExecutor(new GetCommunityInfo(this));
        this.getCommand("pcinvite").setExecutor(new InvitePlayer(this));
        this.getCommand("pcaccept").setExecutor(new ProcessInvite(this));

        ConfigurationSerialization.registerClass(Community.class);
        ConfigurationSerialization.registerClass(Invite.class);
        ConfigurationSerialization.registerClass(Warp.class);

        System.out.println("PlayerCommunities enabled!");
    }

    @Override
    public void onDisable() {
        data.getALlCommunities().clear();
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
