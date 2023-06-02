package com.justinmtech.guilds.service;

import java.util.UUID;

public interface GuildService {
    boolean acceptInvite(UUID playerId, String guildName);
    boolean confirmation(UUID playerId);
    boolean createGuild(UUID playerId, String guildName);
    boolean denyInvite(UUID playerId, String guildName);
    boolean disbandGuild(UUID playerId, String guildName);
    void getGuildInfo(UUID playerId, String guildName);
    boolean sendToWarp(UUID playerId, String warpName);
    boolean invitePlayer(UUID playerId, String playerName);
    boolean kickPlayer(UUID playerId, String playerName);
    boolean leaveGuild(UUID playerId, String guildName);
    void listGuilds();
    boolean promotePlayer(UUID playerId, String playerName);
    boolean demotePlayer(UUID playerId, String playerName);
    boolean setDescription(UUID playerId, String description);
    boolean setWarp(UUID playerId, String warpName);
    boolean upgradeGuild(UUID playerId);
}
