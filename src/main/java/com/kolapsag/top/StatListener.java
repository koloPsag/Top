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

        if (statManager().isNotInMap(statManager().getBlocksBroken(), p)) { statManager().addBrokenBlock(p, 1); return; }
        statManager().addBrokenBlock(p, statManager().getBlocksBroken(p) + 1);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        final Player p = e.getPlayer();

        if (statManager().isNotInMap(statManager().getDeaths(), p)) { statManager().addDeath(p, 1); } else { statManager().addDeath(p, statManager().getDeaths(p) + 1); }

        if (p.getKiller() == null) return;

        final OfflinePlayer killer = p.getKiller();

        if (statManager().isNotInMap(statManager().getPlayersKilled(), killer)) { statManager().addKill(killer, 1); return; }
        statManager().addKill(killer, statManager().getPlayersKilled(killer) + 1);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (!e.getClickedInventory().equals(statManager().getGui())) return;

        e.setCancelled(true);
    }
    private StatManager statManager() { return main.getStatManager(); }
}