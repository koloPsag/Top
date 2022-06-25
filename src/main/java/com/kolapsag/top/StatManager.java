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

    private final Map<UUID, Integer> blocksBroken = new HashMap<>(),
    playersKilled = new HashMap<>(),
    deaths = new HashMap<>();

    private final Inventory topka = Bukkit.createInventory(null, 27, Component.text("                Topka", ChatUtil.hex("3dbf94")));

    public void addBrokenBlock(OfflinePlayer player, int num) { blocksBroken.put(player.getUniqueId(), num); }
    public void addKill(OfflinePlayer player, int num) { playersKilled.put(player.getUniqueId(), num); }
    public void addDeath(OfflinePlayer player, int num) { deaths.put(player.getUniqueId(), num); }
    public Map<UUID, Integer> getBlocksBroken() { return Collections.unmodifiableMap(blocksBroken); }
    public Map<UUID, Integer> getPlayersKilled() { return Collections.unmodifiableMap(playersKilled); }
    public Map<UUID, Integer> getDeaths() { return Collections.unmodifiableMap(deaths); }
    public int getBlocksBroken(OfflinePlayer player) { return blocksBroken.get(player.getUniqueId()); }
    public int getPlayersKilled(OfflinePlayer player) { return playersKilled.get(player.getUniqueId()); }
    public int getDeaths(OfflinePlayer player) { return deaths.get(player.getUniqueId()); }
    public boolean isNotInMap(Map<UUID, Integer> map, OfflinePlayer player) { return !map.containsKey(player.getUniqueId()); }
    public Inventory getGui() { return topka; }

    public void saveTop(FileConfiguration conf) {
        blocksBroken.forEach((key, value) -> conf.set("BlocksBroken." + key, value));
        playersKilled.forEach((key, value) -> conf.set("PlayersKilled." + key, value));
        deaths.forEach((key, value) -> conf.set("Deaths." + key, value));
    }
    public void retrieveTop(FileConfiguration conf) {

        final Map<String, Map<UUID, Integer>> pathMap = new HashMap<>();
        pathMap.put("BlocksBroken", blocksBroken); pathMap.put("PlayersKilled", playersKilled); pathMap.put("Deaths", deaths);

        pathMap.forEach((path, map) -> {
            if (conf.getConfigurationSection(path) != null) {
                for (String value : conf.getConfigurationSection(path).getKeys(false)) map.put(UUID.fromString(value), conf.getInt(path + "." + value));
                conf.set(path, null);
            }
        });

    }
}
