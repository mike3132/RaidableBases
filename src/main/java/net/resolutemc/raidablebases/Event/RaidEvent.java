package net.resolutemc.raidablebases.Event;

import net.resolutemc.raidablebases.RaidableBases;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class RaidEvent implements Listener {


    @EventHandler
    public void onPlayerClick(PlayerInteractEvent pie) {
        Player player = pie.getPlayer();
        Block block = pie.getClickedBlock();

        if (!RaidableBases.getInstance().getRaidingPlayers().containsKey(player.getUniqueId())) return;
        // TODO: Make a config option for all blocks to blacklist players opening during raids
        if (!block.getType().equals(Material.CHEST) && !block.getType().equals(Material.TRAPPED_CHEST)) return;
        if (pie.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        pie.setCancelled(true);
        // TODO: Make message config for this
        player.sendMessage("You cannot open containers in raids, you have to blow them up");
    }


}
