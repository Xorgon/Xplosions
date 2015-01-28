package me.xorgon.xplosions;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Plugin listeners class.
 */
public class XPListeners implements Listener{
    private XplosionsPlugin plugin;

    public XPListeners(XplosionsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event){
        Entity bomb = event.getEntity();
        Location location = bomb.getLocation();
        World world = location.getWorld();
        List<Block> blocks = event.blockList();

        for (Block block : blocks) {
            FallingBlock fBlock = world.spawnFallingBlock(location, block.getType(), block.getData());
            fBlock.setDropItem(false);

            Vector bLoc = block.getLocation().toVector();
            Vector loc = location.toVector();
            bLoc.subtract(loc);
            Double bLocMag = bLoc.length();

            Double x = bLoc.getX()/bLocMag;
            Double y = bLoc.getY()/bLocMag;
            Double z = bLoc.getZ()/bLocMag;

            Vector velocity = new Vector(x,y,z).multiply(1/bLocMag);

            fBlock.setVelocity(velocity);
        }
    }

    @EventHandler
    public void fBlockLand(BlockPhysicsEvent event){
        //Bukkit.broadcastMessage("1");
    }

}
