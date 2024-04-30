package net.resolutemc.raidablebases.Utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Random;

public class PlayerLocationUtils {

    public Location pos1(Player player) {
        Location playerLocation = player.getLocation();
        double x = playerLocation.getBlockX() + 0.5;
        double y = playerLocation.getBlockY() + 0.5;
        double z = playerLocation.getBlockZ() + 0.5;

        return new Location(playerLocation.getWorld(), x +32, y +15, z +32);
    }

    public Location pos2(Player player) {
        Location playerLocation = player.getLocation();
        double x = playerLocation.getBlockX() + 0.5;
        double y = playerLocation.getBlockY() + 0.5;
        double z = playerLocation.getBlockZ() + 0.5;

        return new Location(playerLocation.getWorld(), x -32, y - 16, z -32);
    }

    public Location randomLocation(Player player) {
        Random random = new Random();
        Location playerLocation = player.getLocation();
        int x = player.getLocation().getBlockX() + random.nextInt(-32, 32);
        int y = playerLocation.getBlockY();
        int z = player.getLocation().getBlockZ() + random.nextInt(-32, 32);

        return new Location(playerLocation.getWorld(), x, y, z);
    }




}
