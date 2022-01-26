package tech.justinm.playercommunities.persistence;

import org.bukkit.entity.Player;
import tech.justinm.playercommunities.core.Community;
import java.util.List;
import java.util.UUID;

public interface ManageData {
    void setup();
    Community getCommunity(UUID playerUuid);
    Community getCommunity(String name);
    List<Community> getAllCommunities();
    void createCommunity(UUID owner, String name);
    void saveAllCommunities(List<Community> communities);
    void loadAllCommunities();
    void deleteCommunity(String communityName);
    String getInvite(UUID receiver);
    void deleteInvite(UUID receiver);
    void createInvite(UUID receiver, String communityName);

}
