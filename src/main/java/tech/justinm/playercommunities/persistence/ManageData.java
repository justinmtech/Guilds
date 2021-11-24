package tech.justinm.playercommunities.persistence;

import tech.justinm.playercommunities.core.Community;

import java.util.List;

public interface ManageData {

    Community getCommunity(String name);

    List<Community> getALlCommunities();

    void createCommunity(Community community);

    void updateCommunity(Community community);

    void deleteCommunity(Community community);

    void saveAllCommunities();
}
