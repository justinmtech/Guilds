package tech.justinm.playercommunities.persistence;

import org.bukkit.entity.Player;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.core.Invite;

import java.util.List;

public interface ManageData {
    boolean setup();
    Community getCommunity(Player player);
    Community getCommunity(Player player, String name);
    List<Community> getALlCommunities();
    boolean saveCommunity(Community community);
    boolean saveAllCommunities(List<Community> communities);
    boolean loadCommunity(Player player);
    boolean loadAllCommunities();
    boolean deleteCommunity(Community community);
    Invite getInvite(Player receiver);
    List<Invite> getAllInvites();
    boolean deleteInvite(Invite invite);

}
