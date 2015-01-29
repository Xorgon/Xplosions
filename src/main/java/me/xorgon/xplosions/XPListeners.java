package me.xorgon.xplosions;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

/**
 * Plugin listeners class.
 */
public class XPListeners implements Listener{
    private XplosionsPlugin plugin;
    private XPManager manager;
    Double f;
    Double d;

    public XPListeners(XplosionsPlugin plugin, XPManager manager) {
        this.plugin = plugin;
        this.manager = manager;
        f = manager.getExplosionFactor();
        d = manager.getDestructionRatio();
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event){
        Entity bomb = event.getEntity();
        Location location = bomb.getLocation();
        World world = location.getWorld();
        List<Block> blocks = event.blockList();


        for (Block block : blocks) {
            FallingBlock fBlock = world.spawnFallingBlock(location, block.getType(), block.getData());
            fBlock.setDropItem(true);

            Vector bLoc = block.getLocation().toVector();
            Vector loc = location.toVector();
            bLoc.subtract(loc);
            Double bLocMag = bLoc.length();

            Double x = bLoc.getX()/bLocMag;
            Double y = bLoc.getY()/bLocMag;
            Double z = bLoc.getZ()/bLocMag;

            Vector velocity = new Vector(x,y,z).multiply(f * 1/Math.pow(bLocMag,2));

            fBlock.setVelocity(velocity);
        }
    }

    @EventHandler
    public void fBlockLand(EntityChangeBlockEvent event){
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.FALLING_BLOCK) {
            Random rand = new Random();
            Double destroy = rand.nextDouble();
            if (destroy >= d){
                event.getBlock().breakNaturally();
            }

            //Removed due to feasibility issues in current game versions.
            /*
            Location loc = entity.getLocation();
            World world = entity.getWorld();
            FallingBlock fBlock = (FallingBlock) entity;
            Vector vel = entity.getLocation().getDirection();
            Double velMag = vel.length();
            Vector distance = new Vector(vel.getX() / velMag, vel.getY() / velMag, vel.getZ() / velMag).multiply(2);
            world.getBlockAt(loc).setType(Material.SANDSTONE);
            Bukkit.broadcastMessage(ChatColor.BLUE + vel.toString());

            Double x = loc.getBlockX() + distance.getX();
            Double y = loc.getBlockY() + distance.getY();
            Double z = loc.getBlockZ() + distance.getZ();

            Location blockLoc = new Location(world, x, y, z);

            Block hit = world.getBlockAt(blockLoc.getBlockX(), blockLoc.getBlockY(), blockLoc.getBlockZ());

            float hitHardness = BundledBlockData.getInstance().getMaterialById(hit.getTypeId()).getHardness();
            float blockHardness = BundledBlockData.getInstance().getMaterialById(fBlock.getBlockId()).getHardness();

            Bukkit.broadcastMessage(ChatColor.GREEN + String.valueOf(hitHardness) + ChatColor.RED + String.valueOf(blockHardness));
            if (blockHardness > hitHardness){
                hit.setType(Material.CLAY);
                event.setCancelled(true);
            }*/

        }
    }
}
