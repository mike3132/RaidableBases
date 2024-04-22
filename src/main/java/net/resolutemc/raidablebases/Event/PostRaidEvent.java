package net.resolutemc.raidablebases.Event;

import net.resolutemc.raidablebases.RaidableBases;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PostRaidEvent implements Listener {

    RaidableBases raidableBases;

    public PostRaidEvent(RaidableBases raidableBases) {
        this.raidableBases = raidableBases;
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent pme) {
        Player player = pme.getPlayer();

        if (raidableBases.getPostRaidPlayers().contains(player.getUniqueId())) {
            Location current = pme.getFrom();
            Location moveTo = pme.getTo();
            double currentX = current.getX();
            double currentZ = current.getZ();
            double moveX = moveTo.getX();
            double moveZ = moveTo.getZ();

            if (moveX != currentX || moveZ != currentZ) {
                pme.setCancelled(true);
            }
        }
    }

}
