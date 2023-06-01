package com.justinmtech.guilds.bukkit.gui;

import com.justinmtech.aqua.item.ItemFactory;
import com.justinmtech.guilds.core.GPlayer;
import com.justinmtech.guilds.core.Guild;
import com.justinmtech.guilds.core.Role;
import com.justinmtech.guilds.persistence.GuildsRepository;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class GuildGuiImp implements GuildGui {
    private final GuildsRepository guildsRepository;
    private final FileConfiguration config;

    public GuildGuiImp(GuildsRepository guildsRepository, FileConfiguration config) {
        this.guildsRepository = guildsRepository;
        this.config = config;
    }

    @Override
    public Inventory getMainMenu(GPlayer gPlayer) {
        Inventory menu = Bukkit.createInventory(null, 27, "Guilds");
        menu.setItem(3, getHelpMenuItem());
        menu.setItem(4, getCreateGuildItem());
        menu.setItem(5, getGuildsItem());
        menu.setItem(13, getInvitesItem());
        if (gPlayer.hasGuild()) {
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
        Inventory menu = Bukkit.createInventory(null, 27, "Guilds Help");
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

    @Override
    public Inventory getGuildMenu(Guild guild, GPlayer gPlayer) {
        Inventory menu = Bukkit.createInventory(null, 54, guild.getName() + " Guild");
        //Members
        //Level
        //Rating
        return menu;
    }

    @Override
    public Inventory getInvitesMenu(GPlayer gPlayer) {
        Inventory menu = Bukkit.createInventory(null, 27, "Guild Invites");
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
    public Inventory getGuildsMenu(GPlayer gPlayer, int page) {
        Inventory menu = Bukkit.createInventory(null, 54, "Guilds");
        List<Guild> guild = guildsRepository.getAllGuilds();
        Collections.sort(guild);
        int pages = (int) Math.ceil(guild.size() / 45.0);

        int guildIndex = (page - 1) * 45;
        for (int i = guildIndex; i < 45; i++) {
            try {
                Guild guildFetched = guild.get(guildIndex++);
                menu.addItem(getGuildListItem(guildFetched));
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        if (page > 1) menu.setItem(48, ItemFactory.build(Material.PAPER, "§cPrevious page"));
        menu.setItem(49, ItemFactory.build(Material.PAPER, "§aCurrent Page: " + page));
        if (pages > 1 && page < pages) menu.setItem(50, ItemFactory.build(Material.PAPER, "§aNext page"));

        return menu;
    }

    private ItemStack getGuildListItem(Guild guild) {
        return ItemFactory.build(Material.PAPER, "§6" + guild.getName(),
                "§7Level: " + guild.getLevel(),
                "§7Members: " + guild.getMembers().size(),
                "§7Rating: " + guild.getRating(),
                "§7Leader: " + guild.getLeader());
    }

    private ItemStack getHelpMenuItem() {
        return ItemFactory.build(Material.YELLOW_WOOL, "§6Help", "§7View help menu");
    }

    private ItemStack getCreateGuildItem() {
        return ItemFactory.build(Material.LIME_WOOL, "§dCreate Guild", "§7Create a new guild");
    }

    private ItemStack getGuildsItem() {
        return ItemFactory.build(Material.BLUE_WOOL, "§eGuilds", "§7View all guilds");
    }

    private ItemStack getInvitesItem() {
        return ItemFactory.build(Material.PURPLE_WOOL, "§bInvites", "§7View your invites");
    }

    private ItemStack getMembersItem() {
        return ItemFactory.build(Material.GREEN_WOOL, "§aMembers", "§7View your guild members");
    }

    private ItemStack getLeaveGuildItem() {
        return ItemFactory.build(Material.RED_WOOL, "§cLeave Guild", "§7Leave your guild");
    }

    private ItemStack getKickMemberItem() {
        return ItemFactory.build(Material.RED_WOOL, "§cKick Member", "§7Kick a member from your guild");
    }

    private ItemStack getPromoteMemberItem() {
        return ItemFactory.build(Material.YELLOW_WOOL, "§ePromote Member", "§7Promote a member in your guild");
    }

    private ItemStack getDemoteMemberItem() {
        return ItemFactory.build(Material.YELLOW_WOOL, "§cDemote Member", "§7Demote a member in your guild");
    }

    private ItemStack getDisbandGuildItem() {
        return ItemFactory.build(Material.RED_WOOL, "§cDisband Guild", "§7Disband your guild");
    }

    private ItemStack getGuildItem() {
        return ItemFactory.build(Material.BLUE_WOOL, "§6Guild", "§7View your guild");
    }

    private ItemStack getWarpsItem() {
        return ItemFactory.build(Material.BLUE_WOOL, "§6Warps", "§7View your guild warps");
    }

    private ItemStack getSetDescriptionItem() {
        return ItemFactory.build(Material.BLUE_WOOL, "§6Set Description", "§7Set your guild description");
    }

}
