package com.kolapsag.top;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Top extends JavaPlugin {

    private StatManager statManager;

    @Override
    public void onEnable() {
        getCommand("topka").setExecutor(new TopkaCommand(this));
        Bukkit.getPluginManager().registerEvents(new StatListener(this), this);

        statManager = new StatManager(this);
        statManager.retrieveTop(getConfig());
        saveConfig();
    }
    @Override
    public void onDisable() {
        statManager.saveTop(getConfig());
        saveConfig();
    }
    public StatManager getStatManager() { return statManager; }
}