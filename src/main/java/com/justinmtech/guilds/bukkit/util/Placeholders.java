package com.justinmtech.guilds.bukkit.util;

import com.justinmtech.guilds.core.GPlayer;
import com.justinmtech.guilds.persistence.GuildsRepository;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Placeholders extends PlaceholderExpansion {
    private final GuildsRepository data;

    public Placeholders(GuildsRepository data) {
        this.data = data;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "guilds";
    }

    @Override
    public @NotNull String getAuthor() {
        return "justinmtech";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        Optional<GPlayer> gPlayer = getData().getPlayer(player.getUniqueId());
        if (gPlayer.isEmpty()) {
            return "None";
        } else if (params.equalsIgnoreCase("guild")) {
            String guildId = gPlayer.get().getGuildId();
            if (guildId != null) return guildId;
        }
        return "None";
    }

    public GuildsRepository getData() {
        return data;
    }
}
