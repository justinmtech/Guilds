package com.justinmtech.guilds.persistence;

import com.justinmtech.guilds.core.Guild;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ManageData {
    void setup();
    Guild getGuild(UUID playerUuid);
    Guild getGuild(String name);
    List<Guild> getAllGuilds();
    void createGuild(UUID owner, String name);
    void saveAllGuilds();
    void loadAllGuilds();
    void deleteGuild(String communityName);
    void addMember(UUID member, String communityName);
    void removeMember(UUID member);
    String getInvite(UUID receiver, String name);
    void deleteInvite(UUID receiver, String name);
    List<String> getInvites(UUID receiver);
    void createInvite(UUID receiver, String communityName);
    void clearCache();
    Map<UUID, Double> getTransactionConfirmations();
}
