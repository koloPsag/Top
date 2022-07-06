package com.kolapsag.top.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private final ItemStack item;

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
    }

    public void enchant(@NotNull Enchantment enchantment, int level, boolean safe) {
        if (!safe) { item.addUnsafeEnchantment(enchantment, level); return; }
        item.addEnchantment(enchantment, level);
    }

    public ItemBuilder displayName(Component displayName) {
        item.editMeta(meta -> meta.displayName(displayName));
        return this;
    }

    public ItemBuilder lore(Component... lore) {
        item.editMeta(meta -> {
            List<Component> oldLore = meta.lore();
            if(oldLore == null) oldLore = new ArrayList<>();

            oldLore.addAll(List.of(lore));
            meta.lore(oldLore);
        });
        return this;
    }

    public void glow() {
        item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
    }

    public ItemStack build() {
        return item;
    }
}