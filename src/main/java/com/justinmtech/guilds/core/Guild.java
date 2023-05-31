package com.justinmtech.guilds.core;

import java.util.Map;
import java.util.UUID;

public interface Guild extends Comparable<Guild> {

    String getName();
    void setName(String name);

    UUID getOwner();
    void setOwner(UUID owner);

    String getDescription();
    void setDescription(String description);

    Map<UUID, Role> getMembers();
    void setMembers(Map<UUID, Role> members);
    void addMember(UUID uuid);
    void removeMember(UUID uuid);
    Map<UUID, Role> getOnlineMembers();
    boolean containsMember(UUID playerUuid);
    int getMaxMembers();

    boolean isOwner(UUID playerUuid);
    boolean isMod(UUID playerUuid);
    boolean isColeader(UUID playerUuid);

    Map<String, Warp> getWarps();
    boolean containsWarp(String warpName);
    void setWarps(Map<String, Warp> warps);
    void addWarp(Warp warp);
    void removeWarp(String warpName);
    void updateWarp(Warp warp);
    int getMaxWarps();

    int getMaxLevel();
    int getLevel();
    void setLevel(int level);
}
