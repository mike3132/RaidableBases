package net.resolutemc.raidablebases.Event;

import net.resolutemc.raidablebases.RaidableBases;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SetupEvent implements Listener {

    RaidableBases raidableBases;

    public SetupEvent(RaidableBases raidableBases) {
        this.raidableBases = raidableBases;
    }

    @EventHandler
    public void onItemSwap(PlayerSwapHandItemsEvent is) {
        Player player = is.getPlayer();
        if (!raidableBases.getSetupModePlayers().contains(player.getUniqueId())) return;

        Block targetBlock = player.getTargetBlockExact(4);
        if (targetBlock == null) return;
        is.setCancelled(true);
        chestCopy(player, targetBlock);
        player.sendMessage("Copied your inventory to chest");
    }

    private void chestCopy(Player player, Block block) {
        if (!block.getType().equals(Material.CHEST)) return;
        if (player.getInventory().isEmpty()) {
            player.sendMessage("You have nothing in your inventory");
            return;
        }
        Chest chest = (Chest) block.getState();

        if (chest.getBlockInventory().firstEmpty() == -1) {
            player.sendMessage("That chest is full");
            return;
        }
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null) continue;
            chest.getBlockInventory().addItem(itemStack);
        }
    }


}
