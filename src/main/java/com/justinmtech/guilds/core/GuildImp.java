package com.justinmtech.guilds.core;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class GuildImp implements Guild {
    private String name;
    //TODO Add to serialization
    private String tag;
    private UUID owner;
    private String description;
    private Map<UUID, Role> members;
    private Map<String, Warp> warps;
    private int level;
    //TODO Add to serialization
    private ItemStack symbol;
    //TODO Add to serialization
    private BigDecimal bankBalance;
    //TODO Add to serialization
    private List<ItemStack> vault;

    private static final int MAX_LEVEL = 10;
    private static final double MEMBER_WEIGHT = 10.0;
    private static final double BALANCE_WEIGHT = 0.001;

    public GuildImp(UUID owner, String name) {
        this.owner = owner;
        this.name = name;
        this.members = new HashMap<>();
        members.put(owner, Role.LEADER);
        this.warps = new HashMap<>();
        this.description = "A new guild!";
        this.level = 1;
    }

    public GuildImp(String name) {
        this.owner = null;
        this.name = name;
        this.members = new HashMap<>();
        //members.put(null, Role.LEADER);
        this.warps = new HashMap<>();
        this.description = "A new guild!";
        this.level = 1;
    }

    public GuildImp(String name, UUID ownerId, String description, List<Object> memberObjects, Object warps, int level, BigDecimal bankBalance, List<ItemStack> vault) {
        this.members = new HashMap<>();
        this.warps = new HashMap<>();
        this.name = name;
        this.owner = ownerId;
        this.description = description;
        memberObjects.forEach(member -> _addMember((JSONObject) member));
        ((JSONArray) warps).forEach(warp -> _addWarps((JSONObject) warp));
        this.level = level;
        this.bankBalance = bankBalance;
        this.vault = vault;
    }

    private void _addMember(JSONObject member) {
        String id = (String) member.get("player");
        String role = (String) member.get("role");
        this.members.put(UUID.fromString(id), Role.valueOf(role));
    }

    private void _addWarps(JSONObject warp) {
            String name = (String) warp.get("name");
            String world = (String) warp.get("world");
            double x = (Double) warp.get("x");
            double y = (Double) warp.get("y");
            double z = (Double) warp.get("z");
            double yaw = (Double) warp.get("yaw");
            double pitch = (Double) warp.get("pitch");
            boolean isPublic = (Boolean) warp.get("isPublic");
            this.warps.put(name, new WarpImp(name, world, x, y, z, (float) yaw, (float) pitch, isPublic));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Map<UUID, Role> getMembers() {
        return members;
    }

    @Override
    public Map<UUID, Role> getOnlineMembers() {
        Map<UUID, Role> onlineMembers = new HashMap<>();
        for (UUID uuid : members.keySet()) {
            if (Bukkit.getPlayer(uuid) != null) {
                onlineMembers.put(uuid, members.get(uuid));
            }
        }
        return onlineMembers;
    }

    @Override
    public boolean isOwner(UUID playerUuid) {
        return playerUuid.equals(owner);
    }

    @Override
    public boolean isMod(UUID playerUuid) {
        if (members.containsKey(playerUuid)) {
            return members.get(playerUuid).equals(Role.MOD);
        } else {
            return false;
        }
    }

    @Override
    public boolean isColeader(UUID playerUuid) {
        if (members.containsKey(playerUuid)) {
            return members.get(playerUuid).equals(Role.COLEADER);
        } else {
            return false;
        }
    }

    @Override
    public boolean containsMember(UUID playerUuid) {
        return members.containsKey(playerUuid);
    }

    @Override
    public boolean containsWarp(String name) {
        return warps.get(name) != null;
    }

    @Override
    public UUID getLeader() {
        return owner;
    }

    @Override
    public void setLeader(UUID owner) {
        this.owner = owner;
    }

    @Override
    public Map<String, Warp> getWarps() {
        return warps;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        if (level >= 1 && level <= MAX_LEVEL) {
            this.level = level;
        } else {
            if (level < 1) this.level = 1;
            if (level > MAX_LEVEL) this.level = MAX_LEVEL;
        }
    }

    @Override
    public int getMaxWarps() {
        return (int) Math.floor(level / 2.0);
    }

    @Override
    public int getMaxMembers() {
        return level * 5;
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        if (owner != null) json.put("owner", owner.toString());
        json.put("description", description);
        JSONArray jsonArray = new JSONArray();
        if (members != null) {
            for (UUID member : members.keySet()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("player", member.toString());
                jsonObject.put("role", members.get(member).toString());
                jsonArray.add(jsonObject);
            }
            json.put("members", jsonArray);
        }
        JSONArray warpArray = new JSONArray();
        for (String key : warps.keySet()) {
            Warp location = warps.get(key);
            String world = location.getWorld();
            double x = location.getX();
            double y = location.getY();
            double z = location.getZ();
            double yaw = location.getYaw();
            double pitch = location.getPitch();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", key);
            jsonObject.put("world", world);
            jsonObject.put("x", x);
            jsonObject.put("y", y);
            jsonObject.put("z", z);
            jsonObject.put("yaw", yaw);
            jsonObject.put("pitch", pitch);
            warpArray.add(jsonObject);
        }
        json.put("warps", warpArray);
        json.put("level", level);
        json.put("tag", tag);
        json.put("symbol", symbol);
        json.put("bankBalance", bankBalance);
        json.put("vault", vault);
        return json.toString();
    }

    @Override
    public int compareTo(Guild o) {
        if (this.getRating() > o.getRating()) return 1;
        return -1;
    }

    @Override
    public void setMembers(Map<UUID, Role> members) {
        this.members = members;
    }

    @Override
    public void setWarps(Map<String, Warp> warps) {
        this.warps = warps;
    }

    @Override
    public void addMember(UUID uuid) {
        members.put(uuid, Role.MEMBER);
    }

    @Override
    public void removeMember(UUID uuid) {
        members.remove(uuid);
    }

    @Override
    public void addWarp(Warp warp) {warps.put(warp.getId(), warp);}

    @Override
    public void removeWarp(String id) {warps.remove(id);}

    @Override
    public void updateWarp(Warp warp) {warps.replace(warp.getId(), warp);}

    @Override
    public void setTag(String tag) {
        if (tag.length() > 5) tag = tag.substring(0, 5);
        this.tag = tag;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void setSymbol(ItemStack itemStack) {
        this.symbol = itemStack;
    }

    @Override
    public ItemStack getSymbol() {
        return symbol != null ? symbol : new ItemStack(Material.CHEST);
    }

    @Override
    public List<ItemStack> getVault() {
        return vault;
    }

    @Override
    public void setVault(List<ItemStack> vault) {
        this.vault = vault;
    }

    @Override
    public void addVaultItem(ItemStack itemStack) {
        vault.add(itemStack);
    }

    @Override
    public void removeVaultItem(ItemStack itemStack) {
        vault.remove(itemStack);
    }

    @Override
    public BigDecimal getBankBalance() {
        return bankBalance;
    }

    @Override
    public void setBankBalance(BigDecimal balance) {
        this.bankBalance = balance;
    }

    @Override
    public void addBankBalance(BigDecimal balance) {
        BigDecimal newBalance = getBankBalance().add(balance);
        setBankBalance(newBalance);
    }

    @Override
    public void removeBankBalance(BigDecimal balance) {
        BigDecimal newBalance = getBankBalance().subtract(balance);
        setBankBalance(newBalance);
    }

    @Override
    public int getRating() {
        int members = getMembers().size();
        int balance = getBankBalance().intValue();
        int memberRating = (int) (members * MEMBER_WEIGHT);
        int balanceRating = (int) (balance * BALANCE_WEIGHT);
        return memberRating + balanceRating;
    }
}
