package net.resolutemc.raidablebases.Hook;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import net.resolutemc.raidablebases.RaidableBases;
import net.resolutemc.raidablebases.Utils.PlayerLocationUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Optional;

public class MythicMobsHook {

    private final PlayerLocationUtils playerLocationUtils = new PlayerLocationUtils();

    // TODO: Make config option for setting which mobs to spawn
    private final MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob("raid_defender01").orElse(null);

    public MythicMob getMob() {
        return mob;
    }

    // TODO: Loop over mobs in config file for each raid and spawn them in
    public void spawnMob(Player player) {
        new BukkitRunnable() {
            int mobAmount = 4;
            @Override
            public void run() {
                if (mobAmount == 0) this.cancel();
                mobAmount --;
                getMob().spawn(BukkitAdapter.adapt(playerLocationUtils.randomLocation(player)), 1);
            }
        }.runTaskTimer(RaidableBases.getInstance(), 0, 20L);
    }

    public void removeMob(Player player) {
        double x = player.getLocation().getX() + 4;
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ() + 4;

        for (Entity entity : player.getNearbyEntities(x, y, z)) {
            Optional<ActiveMob> optionalActiveMob = MythicBukkit.inst().getMobManager().getActiveMob(entity.getUniqueId());
            optionalActiveMob.ifPresent(activeMob -> {
                if (activeMob.getType().getInternalName().equals(getMob().getInternalName())) {
                    entity.remove();
                }
            });
        }


    }





}
