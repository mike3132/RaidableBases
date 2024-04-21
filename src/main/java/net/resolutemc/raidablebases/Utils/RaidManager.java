package net.resolutemc.raidablebases.Utils;

import net.resolutemc.raidablebases.PreRaid.PreRaidBossBar;
import net.resolutemc.raidablebases.PreRaid.PreRaidTitle;
import net.resolutemc.raidablebases.RaidableBases;
import net.resolutemc.raidablebases.Schematics.SchematicLoader;
import net.resolutemc.raidablebases.Schematics.SchematicSaver;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RaidManager {

    private final RaidableBases raidableBases;
    private final SchematicLoader schematicLoader = new SchematicLoader();
    private final SchematicSaver schematicSaver = new SchematicSaver();
    private final PlayerLocationUtils locationUtils = new PlayerLocationUtils();
    private final PreRaidTitle preRaidTitle = new PreRaidTitle();

    public RaidManager(RaidableBases raidableBases) {
        this.raidableBases = raidableBases;
    }


    // Logic for starting a specific raid
    public void startSpecificRaid(Player player, String string) {
        PreRaidBossBar preRaidBossBar = new PreRaidBossBar();
        Location pos1 = locationUtils.pos1(player);
        Location pos2 = locationUtils.pos2(player);

        raidableBases.getPreRaidPlayers().add(player.getUniqueId());
        preRaidTitle.sendTitle(player);
        schematicSaver.blockCache(player);
        preRaidBossBar.addBar(player);

        //TODO: Make messages config for this
        player.sendMessage("Saving current blocks placeholder");

        new BukkitRunnable() {
            @Override
            public void run() {
                raidableBases.getPreRaidPlayers().remove(player.getUniqueId());
                raidableBases.getParticleCubeHandler().addCube(player, pos1, pos2);
                schematicLoader.loadSpecificSchematic(player, string);

                //TODO: Make messages config for this
                player.sendMessage("Schematic loaded placeholder");
            }
        }.runTaskLater(RaidableBases.getInstance(), 200L);

    }

    // Logic for starting a random raid
    public void starRandomRaid(Player player, String string) {
        Location pos1 = locationUtils.pos1(player);
        Location pos2 = locationUtils.pos2(player);

        this.raidableBases.getPreRaidPlayers().add(player.getUniqueId());
        raidableBases.getPreRaidPlayers().add(player.getUniqueId());
        //preRaidBossBar.addBar(player);
        preRaidTitle.sendTitle(player);
        schematicSaver.blockCache(player);

        //TODO: Make messages config for this
        player.sendMessage("Saving current blocks placeholder");

        new BukkitRunnable() {
            @Override
            public void run() {
                raidableBases.getPreRaidPlayers().remove(player.getUniqueId());
                raidableBases.getParticleCubeHandler().addCube(player, pos1, pos2);
                schematicLoader.loadRandomSchematic(player, string);

                //TODO: Make messages config for this
                player.sendMessage("Schematic loaded placeholder");
            }
        }.runTaskLater(RaidableBases.getInstance(), 200L);
    }

    public void resetArea(Player player) {
        player.sendMessage("Please wait 10 seconds while we reset the area");

        //TODO: Teleport logic for sending player back to where they started the raid from
        // so that the area gets reset correctly

        raidableBases.getParticleCubeHandler().removeCube(player);
        schematicLoader.callBlockCache(player);
        //TODO: Make messages config for this
        player.sendMessage("Schematic loaded placeholder");

        //new BukkitRunnable() {
        //    @Override
        //    public void run() {
        //        raidableBases.getParticleCubeHandler().removeCube(player);
        //        schematicLoader.callBlockCache(player);
        //        //TODO: Make messages config for this
        //        player.sendMessage("Schematic loaded placeholder");
        //    }
        //}.runTaskLater(RaidableBases.getInstance(), 200L);
    }


}
