package com.kolapsag.top;

import com.kolapsag.top.utils.ChatUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class StatManager {

    private final Map<UUID, Integer> blocksBroken = new HashMap<>();
    private final Map<UUID, Integer> playersKilled = new HashMap<>();
    private final Map<UUID, Integer> deaths = new HashMap<>();
    private final Inventory topka = Bukkit.createInventory(null, 27, Component.text("                Topka", ChatUtil.hex("3dbf94")));

    public void addBrokenBlock(OfflinePlayer player, int num) { blocksBroken.put(player.getUniqueId(), num); }
    public void addKill(OfflinePlayer player, int num) { playersKilled.put(player.getUniqueId(), num); }
    public void addDeath(OfflinePlayer player, int num) { deaths.put(player.getUniqueId(), num); }
    public Map<UUID, Integer> getBlocksBroken() { return Collections.unmodifiableMap(blocksBroken); }
    public Map<UUID, Integer> getPlayersKilled() { return Collections.unmodifiableMap(playersKilled); }
    public Map<UUID, Integer> getDeaths() { return Collections.unmodifiableMap(deaths); }
    public int getPlayerBrokenBlocks(OfflinePlayer player) { return blocksBroken.get(player.getUniqueId()); }
    public int getPlayerKills(OfflinePlayer player) { return playersKilled.get(player.getUniqueId()); }
    public int getPlayerDeaths(OfflinePlayer player) { return deaths.get(player.getUniqueId()); }
    public boolean isInBroken(OfflinePlayer player) { return blocksBroken.containsKey(player.getUniqueId()); }
    public boolean isInKilled(OfflinePlayer player) { return playersKilled.containsKey(player.getUniqueId()); }
    public boolean isInDeath(OfflinePlayer player) { return deaths.containsKey(player.getUniqueId()); }
    public Inventory getGui() { return topka; }

    public void saveTop(FileConfiguration conf) {
        for (Map.Entry<UUID, Integer> entry : blocksBroken.entrySet()) { conf.set("BlocksBroken." + entry.getKey(), entry.getValue()); }
        for (Map.Entry<UUID, Integer> entry : playersKilled.entrySet()) { conf.set("PlayersKilled." + entry.getKey(), entry.getValue()); }
        for (Map.Entry<UUID, Integer> entry : deaths.entrySet()) { conf.set("Deaths." + entry.getKey(), entry.getValue()); }
    }
    @SuppressWarnings("ConstantConditions")
    public void retrieveTop(FileConfiguration conf) {

        if (conf.getConfigurationSection("BlocksBroken") != null) {
            for (String str : conf.getConfigurationSection("BlocksBroken").getKeys((false))) blocksBroken.put(UUID.fromString(str), conf.getInt("BlocksBroken." + str));
            conf.set("BlocksBroken", null);
        }
        if (conf.getConfigurationSection("PlayersKilled") != null) {
            for (String str : conf.getConfigurationSection("PlayersKilled").getKeys((false))) playersKilled.put(UUID.fromString(str), conf.getInt("PlayersKilled." + str));
            conf.set("PlayersKilled", null);
        }
        if (conf.getConfigurationSection("Deaths") != null) {
            for (String str : conf.getConfigurationSection("Deaths").getKeys((false))) deaths.put(UUID.fromString(str), conf.getInt("Deaths." + str));
            conf.set("Deaths", null);
        }
    }
}
