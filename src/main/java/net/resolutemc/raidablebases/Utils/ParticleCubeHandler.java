package net.resolutemc.raidablebases.Utils;

import net.resolutemc.raidablebases.RaidableBases;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;


import java.util.*;

public class ParticleCubeHandler {

    // I take no credit for this particle system.
    // This comes entirely from Esophose of Rosewood Development
    // Check out her spigot page here > https://www.spigotmc.org/resources/authors/esophose.34168/

    private final Map<UUID, ParticleCube> cubes = new HashMap<>();
    private final BukkitTask cubeTask;

    public ParticleCubeHandler(RaidableBases raidableBases) {
        this.cubeTask = Bukkit.getScheduler().runTaskTimer(raidableBases, this::drawCubes, 0, 1);
    }

    private void drawCubes() {
        for (ParticleCube cube : this.cubes.values()) {
            List<Location> cubePoints = this.getHollowCube(cube.corner1(), cube.corner2(), 0.25D);
            for (Location location : cubePoints) {
                location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, new Particle.DustOptions(Color.BLUE, 1));
            }
        }
    }


    public void addCube(Player player, Location corner1, Location corner2) {
        this.cubes.put(player.getUniqueId(), new ParticleCube(corner1, corner2));
    }

    public void removeCube(Player player) {
        this.cubes.remove(player.getUniqueId());
    }

    public void removeCubes() {
        this.cubes.clear();
    }

    public void disable() {
        this.cubes.clear();
        this.cubeTask.cancel();
    }

    private List<Location> getHollowCube(Location corner1, Location corner2, double particleDistance) {
        List<Location> result = new ArrayList<>();
        World world = corner1.getWorld();
        double minX = Math.min(corner1.getX(), corner2.getX());
        double minY = Math.min(corner1.getY(), corner2.getY());
        double minZ = Math.min(corner1.getZ(), corner2.getZ());
        double maxX = Math.max(corner1.getX(), corner2.getX());
        double maxY = Math.max(corner1.getY(), corner2.getY());
        double maxZ = Math.max(corner1.getZ(), corner2.getZ());

        for (double x = minX; x <= maxX; x += particleDistance) {
            result.add(new Location(world, x, minY, minZ));
            result.add(new Location(world, x, maxY, minZ));
            result.add(new Location(world, x, minY, maxZ));
            result.add(new Location(world, x, maxY, maxZ));
        }

        for (double y = minY; y <= maxY; y += particleDistance) {
            result.add(new Location(world, minX, y, minZ));
            result.add(new Location(world, maxX, y, minZ));
            result.add(new Location(world, minX, y, maxZ));
            result.add(new Location(world, maxX, y, maxZ));
        }

        for (double z = minZ; z <= maxZ; z += particleDistance) {
            result.add(new Location(world, minX, minY, z));
            result.add(new Location(world, maxX, minY, z));
            result.add(new Location(world, minX, maxY, z));
            result.add(new Location(world, maxX, maxY, z));
        }

        return result;
    }

    private record ParticleCube(Location corner1, Location corner2) { }

}
