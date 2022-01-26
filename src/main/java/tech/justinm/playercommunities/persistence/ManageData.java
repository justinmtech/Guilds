package tech.justinm.playercommunities.persistence;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import tech.justinm.playercommunities.core.Community;
import tech.justinm.playercommunities.core.Invite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ManageData {
    void setup();
    Community getCommunity(UUID playerUuid);
    Community getCommunity(String name);
    List<Community> getAllCommunities();
    void createCommunity(UUID owner, String name);
    boolean saveCommunity(Community community);
    boolean saveAllCommunities(List<Community> communities) throws IOException;
    boolean loadCommunity(Player player);
    boolean loadAllCommunities() throws FileNotFoundException;
    boolean deleteCommunity(Community community);
    Invite getInvite(Player receiver);
    List<Invite> getAllInvites();
    boolean deleteInvite(Invite invite);

}
