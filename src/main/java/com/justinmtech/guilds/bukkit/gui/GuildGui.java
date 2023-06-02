package com.justinmtech.guilds.bukkit.gui;

import com.justinmtech.guilds.core.GPlayer;
import com.justinmtech.guilds.core.Guild;
import org.bukkit.inventory.Inventory;

public interface GuildGui {
    String GUILD_MENU = "Guild";
    String MAIN_MENU_TITLE = GUILD_MENU + "s";
    String HELP_MENU_TITLE = MAIN_MENU_TITLE + " Help";
    String INVITES_MENU_TITLE = GUILD_MENU + " Invites";
    String GUILD_TOP_MENU_TITLE = " Top" + MAIN_MENU_TITLE;

    Inventory getMainMenu(GPlayer gPlayer);
    Inventory getHelpMenu(GPlayer gPlayer);
    Inventory getInvitesMenu(GPlayer gPlayer);
    Inventory getTopGuildsMenu(GPlayer gPlayer, int page);
    Inventory getGuildMenu(Guild guild, int page);
}
