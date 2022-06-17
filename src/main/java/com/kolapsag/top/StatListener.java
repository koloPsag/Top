package com.kolapsag.top;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public record StatListener(Top main) implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        final Player p = e.getPlayer();

        if (!statManager().isInBroken(p)) { statManager().addBrokenBlock(p, 1); }
        statManager().addBrokenBlock(p, statManager().getPlayerBrokenBlocks(p) + 1);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        final Player p = e.getPlayer();

        if (!statManager().isInDeath(p)) { statManager().addDeath(p, 1); } else { statManager().addDeath(p, statManager().getPlayerDeaths(p) + 1); }

        if (p.getKiller() == null) return;

        final OfflinePlayer player = p.getKiller();

        if (!statManager().isInKilled(player)) { statManager().addKill(player, 1); } else { statManager().addKill(player, statManager().getPlayerKills(p) + 1); }

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (!e.getClickedInventory().equals(statManager().getGui())) return;

        e.setCancelled(true);
    }
    private StatManager statManager() { return main.getStatManager(); }
}
