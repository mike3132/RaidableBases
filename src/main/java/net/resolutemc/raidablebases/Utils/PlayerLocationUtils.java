package net.resolutemc.raidablebases.Utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerLocationUtils {

    public Location pos1(Player player) {
        Location playerLocation = player.getLocation();
        int x = playerLocation.getBlockX();
        int y = playerLocation.getBlockY();
        int z = playerLocation.getBlockZ();

        return new Location(playerLocation.getWorld(), x+4, y+8, z+4);
    }

    public Location pos2(Player player) {
        Location playerLocation = player.getLocation();
        int x = playerLocation.getBlockX();
        int y = playerLocation.getBlockY();
        int z = playerLocation.getBlockZ();

        return new Location(playerLocation.getWorld(), x-4, y, z-4);
    }



}
