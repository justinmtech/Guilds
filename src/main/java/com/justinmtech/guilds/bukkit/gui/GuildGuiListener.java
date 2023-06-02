package com.justinmtech.guilds.bukkit.gui;

import com.justinmtech.guilds.core.GPlayer;
import com.justinmtech.guilds.persistence.GuildsRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

//TODO Quality problem spotted: The logic for the commands are done in the commands, but they need to be part of a service.
public class GuildGuiListener implements Listener {
    private GuildGuiImp gui;
    private GuildsRepository guildsRepository;

    public GuildGuiListener(GuildGui gui, GuildsRepository guildsRepository) {
        this.gui = (GuildGuiImp) gui;
        this.guildsRepository = guildsRepository;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        String title = e.getView().getTitle();
        Player player = (Player) e.getWhoClicked();
        if (title.equals(GuildGui.MAIN_MENU_TITLE)) {
            mainMenuClick(e, player);
        } else if (title.equals(GuildGui.GUILD_MENU)) {
            guildMenuClick(e, title, player);
        } else if (title.equals(GuildGui.HELP_MENU_TITLE)) {
            helpMenuClick(e, title, player);
        } else if (title.equals(GuildGui.GUILD_TOP_MENU_TITLE)) {
            guildTopMenuClick(e, title, player);
        }
    }

    private void mainMenuClick(InventoryClickEvent e, Player player) {
        ItemStack clickedItem = e.getCurrentItem();
        Optional<GPlayer> gPlayer = guildsRepository.getPlayer(player.getUniqueId());
        if (gPlayer.isEmpty()) return;
        if (clickedItem == null) return;
        if (clickedItem.isSimilar(gui.getHelpMenuItem())) {
            player.openInventory(gui.getHelpMenu(gPlayer.get()));
            //TODO May produce bug
        } else if (clickedItem.isSimilar(gui.getCreateGuildButton(0))) {
            //TODO Imp
            player.sendMessage("§aEnter the name of your guild in chat:");
        } else if (clickedItem.isSimilar(gui.getGuildsItem())) {
            player.openInventory(gui.getTopGuildsMenu(gPlayer.get(), 1));
        } else if (clickedItem.isSimilar(gui.getInvitesItem())) {
            player.openInventory(gui.getInvitesMenu(gPlayer.get()));
        } else if (clickedItem.isSimilar(gui.getWarpsItem())) {
            //TODO Add warps menu
            player.sendMessage("Warps menu clicked");
            //player.openInventory(gui.get)
        } else if (clickedItem.isSimilar(gui.getLeaveGuildItem())) {
            //gPlayer.get().l
        }
    }

    private void guildMenuClick(InventoryClickEvent e, String title, Player player) {

    }

    private void helpMenuClick(InventoryClickEvent e, String title, Player player) {

    }

    private void guildTopMenuClick(InventoryClickEvent e, String title, Player player) {

    }
}
