package tech.justinm.playercommunities;

import org.bukkit.plugin.java.JavaPlugin;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.core.Invite;
import tech.justinm.playercommunities.commands.*;

import java.util.ArrayList;
import java.util.List;

public final class PlayerCommunities extends JavaPlugin {
    private List<Community> communities;
    private List<Invite> invites;

    @Override
    public void onEnable() {
        communities = new ArrayList<>();
        invites = new ArrayList<>();

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
        communities.clear();
        invites.clear();
        System.out.println("PlayerCommunities disabled!");
    }

    public List<Community> getCommunities() {
        return communities;
    }

    public List<Invite> getInvites() {
        return invites;
    }
}
