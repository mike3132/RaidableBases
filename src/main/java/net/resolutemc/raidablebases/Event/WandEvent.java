package net.resolutemc.raidablebases.Event;


import net.resolutemc.raidablebases.RaidableBases;
import net.resolutemc.raidablebases.Utils.RegionUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

public class WandEvent implements Listener {

    private final NamespacedKey key = new NamespacedKey(RaidableBases.getInstance(), "Wand-Item-Key");

    private final RaidableBases raidableBases;
    public WandEvent(RaidableBases raidableBases) {
        this.raidableBases = raidableBases;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent pie) {
        Player player = pie.getPlayer();
        Block block = pie.getClickedBlock();
        RegionUtils region = raidableBases.getRegionCache().computeIfAbsent(player.getUniqueId(), x -> new RegionUtils());

        if (!player.getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_AXE)) return;
        if (!player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) return;

        if (pie.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            region.setPos1(block.getLocation());
            player.sendMessage("Pos 1 set");
            return;
        }
        if (pie.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            region.setPos2(block.getLocation());
            player.sendMessage("Pos 2 set placeholder");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent bbe) {
        Player player = bbe.getPlayer();

        if (!player.getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_AXE)) return;
        if (!player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) return;
        bbe.setCancelled(true);
    }


}
