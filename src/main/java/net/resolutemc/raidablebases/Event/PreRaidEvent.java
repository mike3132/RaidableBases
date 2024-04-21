package net.resolutemc.raidablebases.Event;

import net.resolutemc.raidablebases.Chat.ColorTranslate;
import net.resolutemc.raidablebases.RaidableBases;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
            pme.setCancelled(true);
        }

    }


}
