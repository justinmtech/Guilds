package com.justinmtech.guilds.bukkit.gui;

import com.justinmtech.aqua.item.ItemFactory;
import com.justinmtech.guilds.core.GPlayer;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.core.Role;
import com.justinmtech.guilds.persistence.GuildsRepository;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class GuildGuiImp implements GuildGui {
    private final GuildsRepository guildsRepository;
    private final FileConfiguration config;
    private final static int MAX_INV_SIZE = 54;

    public GuildGuiImp(GuildsRepository guildsRepository, FileConfiguration config) {
        this.guildsRepository = guildsRepository;
        this.config = config;
    }

    @Override
    public Inventory getMainMenu(GPlayer gPlayer) {
        Inventory menu = Bukkit.createInventory(null, MAX_INV_SIZE / 2, MAIN_MENU_TITLE);
        menu.setItem(3, getHelpMenuItem());
        menu.setItem(4, getCreateGuildItem());
        menu.setItem(5, getGuildsItem());
        menu.setItem(13, getInvitesItem());
        if (gPlayer.hasGuild()) {
            Optional<Guild> guild = guildsRepository.getGuild(gPlayer.getGuildId());
            guild.ifPresent(value -> menu.setItem(0, getGuildListItem(value, true)));
            menu.setItem(12, getWarpsItem());
            menu.setItem(14, getLeaveGuildItem());
        } else if (gPlayer.hasGuild() && gPlayer.getRole().ordinal() > 1) {
            menu.setItem(22, getKickMemberItem());
        } else if (gPlayer.hasGuild() && gPlayer.getRole().ordinal() > 2) {
            menu.setItem(20, getPromoteMemberItem());
            menu.setItem(21, getDemoteMemberItem());
        } else if (gPlayer.hasGuild() && gPlayer.getRole() == Role.LEADER) {
            menu.setItem(23, getDisbandGuildItem());
        }
        return menu;
    }

    @Override
    public Inventory getHelpMenu(GPlayer gPlayer) {
        Inventory menu = Bukkit.createInventory(null, MAX_INV_SIZE / 2, HELP_MENU_TITLE);
        menu.addItem(ItemFactory.build(Material.PAPER, "§6Help", "§7View help menu"));
        int guildCost = config.getInt("settings.guild-cost");
        menu.addItem(ItemFactory.build(Material.PAPER, "§6Create Guild", "§7Use /guild create <name> to create a guild", "§A guild costs $" + guildCost + " to make!"));
        menu.addItem(ItemFactory.build(Material.PAPER, "§6Top Guilds", "§7Use /guild top to view the top guilds"));
        menu.addItem(ItemFactory.build(Material.PAPER, "§6Accepting Invites", "§7Use /guild accept <guild> to accept an invite"));
        menu.addItem(ItemFactory.build(Material.PAPER, "§6Denying Invites", "§7Use /guild deny <guild> to deny an invite (optional)"));
        if (gPlayer.hasGuild()) {
            menu.addItem(ItemFactory.build(Material.PAPER, "§6Warps", "§7Use /guild warps to view your guilds warps"));
            menu.addItem(ItemFactory.build(Material.PAPER, "§6Leaving Guilds", "§7Use /guild leave to leave your guild"));
        } else if (gPlayer.hasGuild() && gPlayer.getRole().ordinal() > 1) {
            menu.addItem(ItemFactory.build(Material.PAPER, "§6Kicking Members", "§7Use /guild kick <player> to kick a member from your guild"));
            menu.addItem(ItemFactory.build(Material.PAPER, "§6Invite Members", "§7Use /guild invite <player> to invite a player to your guild"));
        } else if (gPlayer.hasGuild() && gPlayer.getRole().ordinal() > 2) {
            menu.addItem(ItemFactory.build(Material.PAPER, "§6Promoting Members", "§7Use /guild promote <player> to promote a member in your guild"));
            menu.addItem(ItemFactory.build(Material.PAPER, "§6Demoting Members", "§7Use /guild demote <player> to demote a member in your guild"));
            menu.addItem(ItemFactory.build(Material.PAPER, "§6Setting Guild Description", "§7Use /guild setdesc <description> to set your guilds description"));
        } else if (gPlayer.hasGuild() && gPlayer.getRole() == Role.LEADER) {
            menu.addItem(ItemFactory.build(Material.PAPER, "§6Disbanding Guild", "§7Use /guild disband to permanently delete your guild"));
        }
        return menu;
    }

    public ItemStack getCreateGuildButton(int guildCost) {
        return ItemFactory.build(Material.PAPER, "§6Create Guild", "§7Use /guild create <name> to create a guild", "§A guild costs $" + guildCost + " to make!");
    }

    @Override
    public Inventory getGuildMenu(Guild guild, int page) {
        Inventory menu = Bukkit.createInventory(null, MAX_INV_SIZE, GUILD_MENU + ": " + guild.getName());
        ItemStack guildItem = getGuildListItem(guild, true);
        for (int i = 0; i < 9; i++) {
            menu.setItem(i, getFillerItem());
        }
        menu.setItem(4, guildItem);
        int memberIndex = (page - 1) * 36;
        List<UUID> members = new ArrayList<>(guild.getMembers().keySet());
        for (int i = memberIndex; i < members.size(); i++) {
            Optional<GPlayer> gPlayerMember = guildsRepository.getPlayer(members.get(i));
            if (gPlayerMember.isEmpty()) continue;
            ItemStack memberHead = buildPlayerHeadFromGPlayer(gPlayerMember.get());
            if (memberHead == null) continue;
            menu.addItem(memberHead);
        }
        int pages = (int) Math.ceil(guild.getMembers().size() / 36.0);
        setFooter(MAX_INV_SIZE, menu, page, pages);
        return menu;
    }

    public ItemStack getFillerItem() {
        return ItemFactory.build(Material.GRAY_STAINED_GLASS_PANE, " ");
    }

    private ItemStack buildPlayerHeadFromGPlayer(GPlayer player) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUuid());
        if (!offlinePlayer.hasPlayedBefore()) return null;
        String playerName = offlinePlayer.getName();
        ItemStack playerHeadItem = ItemFactory.build(Material.PLAYER_HEAD, "§6" + playerName, "§7Role: " + player.getRole().name());
        SkullMeta im = (SkullMeta) playerHeadItem.getItemMeta();
        if (im == null) return playerHeadItem;
        im.setOwningPlayer(offlinePlayer);
        playerHeadItem.setItemMeta(im);
        return playerHeadItem;
    }

    @Override
    public Inventory getInvitesMenu(GPlayer gPlayer) {
        Inventory menu = Bukkit.createInventory(null, MAX_INV_SIZE / 2, INVITES_MENU_TITLE);
        List<String> invites = gPlayer.getInvites().stream().toList();
        for (int i = 0; i < invites.size(); i++) {
            menu.setItem(i, buildInviteItem(invites.get(i)));
        }
        if (gPlayer.hasGuild() && gPlayer.getRole().ordinal() > 1) {
            menu.addItem(ItemFactory.build(Material.GREEN_WOOL, "§aInvite Player"));
        }
        return menu;
    }

    private ItemStack buildInviteItem(String guildId) {
        return ItemFactory.build(Material.PAPER, "§6Invite from " + guildId, "§aLeft click to accept invite", "§cRight click to deny invite");
    }

    @Override
    public Inventory getTopGuildsMenu(GPlayer gPlayer, int page) {
        Inventory menu = Bukkit.createInventory(null, MAX_INV_SIZE, GUILD_TOP_MENU_TITLE);
        List<Guild> guild = guildsRepository.getAllGuilds();
        Collections.sort(guild);
        int pages = (int) Math.ceil(guild.size() / 45.0);

        int guildIndex = (page - 1) * 45;
        for (int i = guildIndex; i < 45; i++) {
            try {
                Guild guildFetched = guild.get(guildIndex++);
                menu.addItem(getGuildListItem(guildFetched, guildFetched.containsMember(gPlayer.getUuid())));
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        setFooter(MAX_INV_SIZE, menu, page, pages);

        return menu;
    }

    private void setFooter(int size, Inventory menu, int page, int pages) {
        if (page > 1) menu.setItem(size - 6, getPreviousPageItem());
        menu.setItem(size - 5, getCurrentPage(page, pages));
        if (pages > 1 && page < pages) menu.setItem(size - 3, getNextPageItem());
        menu.setItem(size - 9, getReturnMenuItem());
        menu.setItem(size - 1, getCloseMenuItem());
    }

    public ItemStack getReturnMenuItem() {
        return ItemFactory.build(Material.BARRIER, "§cReturn", "§7Return to the previous menu");
    }

    public ItemStack getCloseMenuItem() {
        return ItemFactory.build(Material.BARRIER, "§cClose", "§7Close the menu");
    }

    public ItemStack getPreviousPageItem() {
        return ItemFactory.build(Material.PAPER, "§cPrevious page");
    }

    public ItemStack getCurrentPage(int page, int pages) {
        return ItemFactory.build(Material.PAPER, "§aCurrent Page: " + page + "/" + pages);
    }

    public ItemStack getNextPageItem() {
        return ItemFactory.build(Material.PAPER, "§aNext page");
    }

    public ItemStack getGuildListItem(Guild guild, boolean own) {
        return ItemFactory.build(Material.PAPER, "§6" + guild.getName(),
                "§7Level: " + guild.getLevel(),
                "§7Members: " + guild.getMembers().size(),
                "§7Rating: " + guild.getRating(),
                "§7Leader: " + guild.getLeader(),
                "",
                "§7View this guild",
                own ? "§cYou are in this guild." : "");
    }

    public ItemStack getHelpMenuItem() {
        return ItemFactory.build(Material.YELLOW_WOOL, "§6Help", "§7View help menu");
    }

    public ItemStack getCreateGuildItem() {
        return ItemFactory.build(Material.LIME_WOOL, "§dCreate Guild", "§7Create a new guild");
    }

    public ItemStack getGuildsItem() {
        return ItemFactory.build(Material.BLUE_WOOL, "§eGuilds", "§7View all guilds");
    }

    public ItemStack getInvitesItem() {
        return ItemFactory.build(Material.PURPLE_WOOL, "§bInvites", "§7View your invites");
    }

    public ItemStack getMembersItem() {
        return ItemFactory.build(Material.GREEN_WOOL, "§aMembers", "§7View your guild members");
    }

    public ItemStack getLeaveGuildItem() {
        return ItemFactory.build(Material.RED_WOOL, "§cLeave Guild", "§7Leave your guild");
    }

    public ItemStack getKickMemberItem() {
        return ItemFactory.build(Material.RED_WOOL, "§cKick Member", "§7Kick a member from your guild");
    }

    public ItemStack getPromoteMemberItem() {
        return ItemFactory.build(Material.YELLOW_WOOL, "§ePromote Member", "§7Promote a member in your guild");
    }

    public ItemStack getDemoteMemberItem() {
        return ItemFactory.build(Material.YELLOW_WOOL, "§cDemote Member", "§7Demote a member in your guild");
    }

    public ItemStack getDisbandGuildItem() {
        return ItemFactory.build(Material.RED_WOOL, "§cDisband Guild", "§7Disband your guild");
    }

    public ItemStack getGuildItem() {
        return ItemFactory.build(Material.BLUE_WOOL, "§6Guild", "§7View your guild");
    }

    public ItemStack getWarpsItem() {
        return ItemFactory.build(Material.BLUE_WOOL, "§6Warps", "§7View your guild warps");
    }

    public ItemStack getSetDescriptionItem() {
        return ItemFactory.build(Material.BLUE_WOOL, "§6Set Description", "§7Set your guild description");
    }



}
