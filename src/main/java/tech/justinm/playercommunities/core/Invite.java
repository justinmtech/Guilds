package tech.justinm.playercommunities.core;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Invite implements ConfigurationSerializable {
    private Player sender;
    private Player receiver;
    private Community community;

    public Invite(Player sender, Player receiver, Community community) {
        this.sender = sender;
        this.receiver = receiver;
        this.community = community;
    }

    public Invite(Map<String, Object> serializedMap) {
        sender = (Player) serializedMap.get("sender");
        receiver = (Player) serializedMap.get("receiver");
        community = (Community) serializedMap.get("community");
    }


    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public Player getSender() {
        return sender;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public void setReceiver(Player receiver) {
        this.receiver = receiver;
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> serializableMap = new HashMap<>();

        serializableMap.put("sender", sender);
        serializableMap.put("receiver", receiver);
        serializableMap.put("community", community);

        return serializableMap;
    }
}
