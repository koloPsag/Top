package com.kolapsag.top;

import com.kolapsag.top.utils.ChatUtil;
import com.kolapsag.top.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public record TopkaCommand(Top main) implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof final Player p)) { Bukkit.getLogger().warning("Nie dla konsolki."); return true; }

        final Inventory topka = statManager().getGui();

        final ItemBuilder blocksBroken = new ItemBuilder(Material.GOLD_BLOCK).displayName(Component.text("WYKOPANE BLOKI", ChatUtil.hex("3e41b0"), TextDecoration.BOLD));
        final ItemBuilder kills = new ItemBuilder(Material.DIAMOND_SWORD).displayName(Component.text("ZABICI GRACZE", ChatUtil.hex("b51663"), TextDecoration.BOLD));
        final ItemBuilder death = new ItemBuilder(Material.GOLDEN_APPLE).displayName(Component.text("ŚMIERCI", ChatUtil.hex("adb30e"), TextDecoration.BOLD));
        final ItemStack glass = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayName(Component.text(" ")).build();

        final AtomicInteger blockTop = new AtomicInteger(), killTop = new AtomicInteger(), deathTop = new AtomicInteger();

        entryMap(statManager().getBlocksBroken())
                .forEach(broken -> blocksBroken.lore(Component.text(blockTop.incrementAndGet() + "." + " " + Bukkit.getOfflinePlayer(broken.getKey()).getName() + ": " + broken.getValue())));

        entryMap(statManager().getPlayersKilled())
                .forEach(killed -> kills.lore(Component.text(killTop.incrementAndGet() + "." + " " + Bukkit.getOfflinePlayer(killed.getKey()).getName() + ": " + killed.getValue())));

        entryMap(statManager().getDeaths())
                .forEach(deaths -> death.lore(Component.text(deathTop.incrementAndGet() + "." + " " + Bukkit.getOfflinePlayer(deaths.getKey()).getName() + ": " + deaths.getValue())));

        if (statManager().getBlocksBroken().isEmpty()) { blocksBroken.lore(Component.text("Brak statystyk.")).enchant(Enchantment.PROTECTION_ENVIRONMENTAL).hideEnchants(); }
        if (statManager().getPlayersKilled().isEmpty()) { kills.lore(Component.text("Brak statystyk.")).enchant(Enchantment.PROTECTION_ENVIRONMENTAL).hideEnchants(); }
        if (statManager().getDeaths().isEmpty()) { death.lore(Component.text("Brak statystyk.")).enchant(Enchantment.PROTECTION_ENVIRONMENTAL).hideEnchants()   ; }

        for (int i = 0; i < 27; i++) topka.setItem(i, glass);

        topka.setItem(12, blocksBroken.build());
        topka.setItem(13, kills.build());
        topka.setItem(14, death.build());
        p.openInventory(topka);
        return false;
    }
    private StatManager statManager() { return main.getStatManager(); }
    private Stream<Map.Entry<UUID, Integer>> entryMap(Map<UUID, Integer> map) { return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(3); }
}
