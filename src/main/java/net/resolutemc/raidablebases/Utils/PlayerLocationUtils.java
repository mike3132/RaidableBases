package net.resolutemc.raidablebases.Utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerLocationUtils {

    // This returns a new location based on the player location, This is top right point
    // Used for saving and resetting blocks around player, before and after a raid. Also used for drawing particle cube
    public Location pos1(Player player) {
        Location playerLocation = player.getLocation();
        int x = playerLocation.getBlockX();
        int y = playerLocation.getBlockY();
        int z = playerLocation.getBlockZ();

        return new Location(playerLocation.getWorld(), x+4, y+8, z+4);
    }

    // This returns a new location based on the player location, This is bottom left point
    // Used for saving and resetting blocks around player, before and after a raid. Also used for drawing particle cube
    public Location pos2(Player player) {
        Location playerLocation = player.getLocation();
        int x = playerLocation.getBlockX();
        int y = playerLocation.getBlockY();
        int z = playerLocation.getBlockZ();

        return new Location(playerLocation.getWorld(), x-4, y, z-4);
    }



}
