package me.xorgon.xplosions;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main Plugin class.
 */
public class XplosionsPlugin extends JavaPlugin{

    private static XplosionsPlugin instance;
    private XPManager manager;

    @Override
    public void onEnable(){
        instance = this;
        manager = new XPManager(this);
        manager.load();
        Bukkit.getPluginManager().registerEvents(new XPListeners(this, manager), this);
    }

    @Override
    public void onDisable(){
        manager.save();
    }

    public static XplosionsPlugin getPlugin() {
        return instance;
    }
}
