package com.justinmtech.guilds.persistence.file;

import com.justinmtech.guilds.persistence.ManageData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final FileManager fileManager;

    public PlayerListener(ManageData data) {
        this.fileManager = (FileManager) data;
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e) {
        if (!getFileManager().getCache().playerExists(e.getPlayer().getUniqueId())) {
            getFileManager().getCache().addPlayer(e.getPlayer().getUniqueId(), null, null);
        }
    }

    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent e) {
        getFileManager().getCache().removePlayer(e.getPlayer().getUniqueId());
    }

    public FileManager getFileManager() {
        return fileManager;
    }
}
