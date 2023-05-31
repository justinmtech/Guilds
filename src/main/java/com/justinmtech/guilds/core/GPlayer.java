package com.justinmtech.guilds.core;

import java.util.UUID;

public interface GPlayer {

    UUID getUuid();
    String getGuildId();
    void setGuildId(String newGuildId);
    Role getRole();
    Role setRole(Role role);
    Role promote();
    Role demote();
    boolean hasInvite(String guildId);
    void addInvite(String guildId);
    void removeInvite(String guildId);
    boolean hasGuild();
}
