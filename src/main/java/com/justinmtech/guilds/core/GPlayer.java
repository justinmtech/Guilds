package com.justinmtech.guilds.core;

import java.util.UUID;

public class GPlayer {
    private final UUID uuid;
    private String guildId;
    private Role role;

    public GPlayer(UUID uuid, String guildId, Role role) {
        this.uuid = uuid;
        this.guildId = guildId;
        this.role = role;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
