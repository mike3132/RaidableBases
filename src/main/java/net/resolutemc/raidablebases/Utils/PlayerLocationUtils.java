package net.resolutemc.raidablebases.Utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Random;

public class PlayerLocationUtils {

    public Location pos1(Player player) {
        Location playerLocation = player.getLocation();
        double x = playerLocation.getX();
        double y = playerLocation.getY();
        double z = playerLocation.getZ();

        return new Location(playerLocation.getWorld(), x +4, y +8, z +4);
    }

    public Location pos2(Player player) {
        Location playerLocation = player.getLocation();
        double x = playerLocation.getX();
        double y = playerLocation.getY();
        double z = playerLocation.getZ();

        return new Location(playerLocation.getWorld(), x -4, y, z -4);
    }

    public Location randomLocation(Player player) {
        Random random = new Random();
        Location playerLocation = player.getLocation();
        int x = player.getLocation().getBlockX() + random.nextInt(-4, 4);
        int y = playerLocation.getBlockY();
        int z = player.getLocation().getBlockZ() + random.nextInt(-4, 4);

        return new Location(playerLocation.getWorld(), x, y, z);
    }




}
