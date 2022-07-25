package com.justinmtech.guilds.core;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

//TODO Add support for players with no guild or role
public class GPlayer {
    private final UUID uuid;
    private String guildId;
    private Role role;
    private final Set<String> invites;

    public GPlayer(UUID uuid, String guildId, Role role) {
        this.uuid = uuid;
        this.guildId = guildId;
        this.role = role;
        //TODO Add constructor with invites?
        this.invites = new HashSet<>();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String newGuildId) {
        this.guildId = newGuildId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean hasInvite(String guildId) {
        return invites.contains(guildId);
    }

    public void addInvite(String guildId) {
        invites.add(guildId);
    }

    public void removeInvite(String guildId) {
        invites.remove(guildId);
    }
}
