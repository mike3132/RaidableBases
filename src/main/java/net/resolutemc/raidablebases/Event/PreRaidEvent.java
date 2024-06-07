package net.resolutemc.raidablebases.Event;

import net.resolutemc.raidablebases.RaidableBases;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PreRaidEvent implements Listener {

    RaidableBases raidableBases;


    public PreRaidEvent(RaidableBases raidableBases) {
        this.raidableBases = raidableBases;
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent pme) {
        Player player = pme.getPlayer();

        if (raidableBases.getPreRaidPlayers().contains(player.getUniqueId())) {
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

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent bpe) {
        Player player = bpe.getPlayer();

        if (raidableBases.getPreRaidPlayers().contains(player.getUniqueId())) {
            bpe.setCancelled(true);
            // TODO: Make messages config for this
            player.sendMessage("You cannot place blocks while a raid is being setup");
        }
    }

    @EventHandler
    public void onBlockPlace(BlockBreakEvent bpe) {
        Player player = bpe.getPlayer();

        if (raidableBases.getPreRaidPlayers().contains(player.getUniqueId())) {
            bpe.setCancelled(true);
            // TODO: Make messages config for this
            player.sendMessage("You cannot break blocks while a raid is being setup");
        }
    }


}
