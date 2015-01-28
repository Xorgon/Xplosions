package me.xorgon.xplosions;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main Plugin class.
 */
public class XplosionsPlugin extends JavaPlugin{

    private static XplosionsPlugin instance;

    @Override
    public void onEnable(){
        instance = this;
        Bukkit.getPluginManager().registerEvents(new XPListeners(this), this);
    }

    @Override
    public void onDisable(){

    }
}
