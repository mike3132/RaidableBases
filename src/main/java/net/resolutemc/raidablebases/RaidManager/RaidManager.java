package net.resolutemc.raidablebases.RaidManager;

import net.resolutemc.raidablebases.Hook.MythicMobsHook;
import net.resolutemc.raidablebases.RaidableBases;
import net.resolutemc.raidablebases.Schematics.SchematicLoader;
import net.resolutemc.raidablebases.Schematics.SchematicRemover;
import net.resolutemc.raidablebases.Schematics.SchematicSaver;
import net.resolutemc.raidablebases.Utils.PlayerLocationUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RaidManager {

    private final RaidableBases raidableBases;
    private final SchematicLoader schematicLoader = new SchematicLoader();
    private final SchematicSaver schematicSaver = new SchematicSaver();
    private final PlayerLocationUtils locationUtils = new PlayerLocationUtils();
    private final PreRaidTitle preRaidTitle = new PreRaidTitle();
    private final PostRaidTitle postRaidTitle = new PostRaidTitle();

    public RaidManager(RaidableBases raidableBases) {
        this.raidableBases = raidableBases;
    }

    // Starts a specific raid based on the command arg
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
                raidableBases.getRaidingPlayers().put(player.getUniqueId(), player.getLocation());
                spawnMob(player);

                //TODO: Make messages config for this
                player.sendMessage("Schematic loaded placeholder");
            }
        }.runTaskLater(RaidableBases.getInstance(), 200L);
    }

    // Starts a random raid
    public void starRandomRaid(Player player, String string) {
        PreRaidBossBar preRaidBossBar = new PreRaidBossBar();
        Location pos1 = locationUtils.pos1(player);
        Location pos2 = locationUtils.pos2(player);

        this.raidableBases.getPreRaidPlayers().add(player.getUniqueId());
        raidableBases.getPreRaidPlayers().add(player.getUniqueId());
        preRaidBossBar.addBar(player);
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
                raidableBases.getRaidingPlayers().put(player.getUniqueId(), player.getLocation());

                //TODO: Make messages config for this
                player.sendMessage("Schematic loaded placeholder");
            }
        }.runTaskLater(RaidableBases.getInstance(), 200L);
    }

    // Stops a raid, teleports the player to where the raid started and resets the area around a player
    public void raidEnd(Player player) {
        SchematicRemover schematicRemover = new SchematicRemover();
        PostRaidBossBar postRaidBossBar = new PostRaidBossBar();
        player.teleport(raidableBases.getRaidingPlayers().get(player.getUniqueId()));
        raidableBases.getRaidingPlayers().remove(player.getUniqueId());
        raidableBases.getPostRaidPlayers().add(player.getUniqueId());
        postRaidBossBar.addBar(player);
        postRaidTitle.sendTitle(player);
        removeMob(player);

        new BukkitRunnable() {
            @Override
            public void run() {
                schematicLoader.callBlockCache(player);
                raidableBases.getParticleCubeHandler().removeCube(player);
                raidableBases.getPostRaidPlayers().remove(player.getUniqueId());
                schematicRemover.removeSchematic(player);

                //TODO: Make messages config for this
                player.sendMessage("Schematic loaded placeholder");
            }
        }.runTaskLater(RaidableBases.getInstance(), 200L);
    }

    public void raidForceStop(Player player) {
        SchematicRemover schematicRemover = new SchematicRemover();
        if (!raidableBases.getRaidingPlayers().containsKey(player.getUniqueId())) return;
        player.teleport(raidableBases.getRaidingPlayers().get(player.getUniqueId()));

        schematicLoader.callBlockCache(player);
        raidableBases.getParticleCubeHandler().removeCube(player);
        schematicRemover.removeSchematic(player);
        raidableBases.getRaidingPlayers().remove(player.getUniqueId());
    }

    // TODO: Fix this
    private void spawnMob(Player player) {
        MythicMobsHook mobsHook = new MythicMobsHook();
        mobsHook.spawnMob(player);
    }

    private void removeMob(Player player) {
        MythicMobsHook mobsHook = new MythicMobsHook();
        mobsHook.removeMob(player);
    }


}
