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

    public ItemBuilder enchant(Enchantment enchantment) {
        return this.enchant(enchantment, 1, false);
    }

    public ItemBuilder enchant(@NotNull Enchantment enchantment, int level, boolean safe) {
        if(safe) {
            item.addEnchantment(enchantment, level);
        }else {
            item.addUnsafeEnchantment(enchantment, level);
        }
        return this;
    }

    public void hideEnchants() {
        item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
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

    public ItemStack build() {
        return item;
    }

}
