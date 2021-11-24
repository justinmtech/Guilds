package tech.justinm.playercommunities.persistence;

import tech.justinm.playercommunities.PlayerCommunities;
import tech.justinm.playercommunities.core.Community;

import java.util.List;

public class FlatfileDataHandler implements ManageData {
    private final PlayerCommunities plugin;
    private Community community;
    private List<Community> communities;

    public FlatfileDataHandler(PlayerCommunities plugin, Community community, List<Community> communities) {
        this.plugin = plugin;
        this.community = community;
        this.communities = communities;
    }

    public FlatfileDataHandler(PlayerCommunities plugin, Community community) {
        this.plugin = plugin;
        this.community = community;
    }

    public FlatfileDataHandler(PlayerCommunities plugin, List<Community> communities) {
        this.plugin = plugin;
        this.communities = communities;
    }


    @Override
    public Community getCommunity(String name) {
        //Get community by name from data storage file
        return null;
    }

    @Override
    public List<Community> getALlCommunities() {
        //Return a list of communities from the data storage file
        return null;
    }

    @Override
    public void createCommunity(Community community) {
        //Create a new community in the data storage file IF it does not already exist
        try {
            if (!plugin.getDataFolder().exists()) {
                if (plugin.getDataFolder().createNewFile()) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCommunity(Community community) {

    }

    @Override
    public void deleteCommunity(Community community) {

    }

    @Override
    public void saveAllCommunities() {

    }
}
