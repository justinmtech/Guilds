package com.justinmtech.guilds.core;

import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface Guild extends Comparable<Guild> {

    String getName();
    void setName(String name);

    void setTag(String tag);
    String getTag();

    void setSymbol(ItemStack itemStack);
    ItemStack getSymbol();

    List<ItemStack> getVault();
    void setVault(List<ItemStack> vault);
    void addVaultItem(ItemStack itemStack);
    void removeVaultItem(ItemStack itemStack);

    BigDecimal getBankBalance();
    void setBankBalance(BigDecimal balance);
    void addBankBalance(BigDecimal balance);
    void removeBankBalance(BigDecimal balance);

    int getRating();

    UUID getLeader();
    void setLeader(UUID owner);

    String getDescription();
    void setDescription(String description);

    Map<UUID, Role> getMembers();
    void setMembers(Map<UUID, Role> members);
    void addMember(UUID uuid);
    void removeMember(UUID uuid);
    Map<UUID, Role> getOnlineMembers();
    boolean containsMember(UUID playerUuid);
    int getMaxMembers();

    boolean isOwner(UUID playerUuid);
    boolean isMod(UUID playerUuid);
    boolean isColeader(UUID playerUuid);

    Map<String, Warp> getWarps();
    boolean containsWarp(String warpName);
    void setWarps(Map<String, Warp> warps);
    void addWarp(Warp warp);
    void removeWarp(String warpName);
    void updateWarp(Warp warp);
    int getMaxWarps();

    int getMaxLevel();
    int getLevel();
    void setLevel(int level);
}
