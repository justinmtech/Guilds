package com.justinmtech.guilds.bukkit.gui;

import com.justinmtech.guilds.core.GPlayer;
import org.bukkit.inventory.Inventory;

public interface GuildGui {

    Inventory getMainMenu(GPlayer gPlayer);
    Inventory getHelpMenu(GPlayer gPlayer);
    Inventory getInvitesMenu(GPlayer gPlayer);
    Inventory getGuildsMenu(GPlayer gPlayer);
    Inventory getGuildMenu(GPlayer gPlayer);
}
