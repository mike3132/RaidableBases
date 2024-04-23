package net.resolutemc.raidablebases.Event;

import net.resolutemc.raidablebases.RaidableBases;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

public class TntEvent implements Listener {

    RaidableBases raidableBases;

    public TntEvent(RaidableBases raidableBases) {
        this.raidableBases = raidableBases;
    }


    @EventHandler
    public void onTntEvent(EntityExplodeEvent ee) {
        Entity entity = ee.getEntity();
        TNTPrimed primed = (TNTPrimed) entity;

        if (!(primed.getSource() instanceof Player)) return;
        Player player = (Player) primed.getSource();
        if (!raidableBases.getRaidingPlayers().containsKey(player.getUniqueId())) return;

        for (Block block : ee.blockList()) {
            if (block.getType().equals(Material.AIR)) return;
            Location source = ee.getEntity().getLocation();
            FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation(), block.getBlockData());

            double distance = fallingBlock.getLocation().distance(source);
            Vector velocity = fallingBlock.getLocation().clone().subtract(source).toVector().normalize().divide(
                    new Vector(distance, distance, distance)).multiply(1.5);

            if (velocity.getY() < 0) {
                velocity.multiply(new Vector(1, -1.5, 1));
            } else {
                velocity.multiply(new Vector(1, 1.5, 1));
            }

            if (velocity.getY() > 2) {
                velocity.setY(2);
            }

            velocity.add(Vector.getRandom().multiply(0.05));
            fallingBlock.setVelocity(velocity);
            fallingBlock.setGlowing(true);
            fallingBlock.setCancelDrop(true);
        }
    }
}
