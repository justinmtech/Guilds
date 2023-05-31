package com.justinmtech.guilds.core;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GPlayerImp implements GPlayer {
    private final UUID uuid;
    private String guildId;
    private Role role;
    private final Set<String> invites;

    public GPlayerImp(UUID uuid, String guildId, Role role) {
        this.uuid = uuid;
        this.guildId = guildId;
        this.role = role;
        this.invites = new HashSet<>();
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String getGuildId() {
        return guildId;
    }

    @SuppressWarnings("unused")
    @Override
    public void setGuildId(String newGuildId) {
        this.guildId = newGuildId;
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public Role setRole(Role role) {
        this.role = role;
        return role;
    }

    @Override
    public Role promote() {
        if (role == Role.MEMBER) {
            return setRole(Role.MOD);
        } else if (role == Role.MOD) {
            return setRole(Role.COLEADER);
        }
        return null;
    }

    @Override
    public Role demote() {
        if (role == Role.MEMBER) {
            return null;
        }
        if (role == Role.MOD) {
            return setRole(Role.MEMBER);
        } else if (role == Role.COLEADER) {
            return setRole(Role.MOD);
        }
        return null;
    }

    @Override
    public boolean hasInvite(String guildId) {
        return invites.contains(guildId);
    }

    @Override
    public void addInvite(String guildId) {
        invites.add(guildId);
    }

    @Override
    public void removeInvite(String guildId) {
        invites.remove(guildId);
    }

    @Override
    public boolean hasGuild() {
        return this.guildId != null && !this.guildId.isBlank() && !this.guildId.isEmpty();
    }
}
