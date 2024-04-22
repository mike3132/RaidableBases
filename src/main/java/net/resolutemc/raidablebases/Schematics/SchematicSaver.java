package net.resolutemc.raidablebases.Schematics;

import net.resolutemc.raidablebases.Hook.WorldEditHook;
import net.resolutemc.raidablebases.RaidableBases;
import net.resolutemc.raidablebases.Utils.PlayerLocationUtils;
import net.resolutemc.raidablebases.Utils.RegionUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;

public class SchematicSaver {

    PlayerLocationUtils playerLocationUtils = new PlayerLocationUtils();

    public void schematicSave(Player player, String string) {
        final File file = new File(RaidableBases.getInstance().getDataFolder(), "schematics/" + string + ".schem");

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (string == null) {
            //TODO: Make messages config for this
            player.sendMessage("Please select a name placeholder");
            return;
        }

        RegionUtils region = RaidableBases.getInstance().getRegionCache().get(player.getUniqueId());
        if (region.getPos1() == null || region.getPos2() == null) {
            //TODO: Make messages config for this
            player.sendMessage("You need to set both pos1 and pos2 first");
            return;
        }

        WorldEditHook.save(region.getPos1(), region.getPos2(), player.getLocation(), file);
        //TODO: Make messages config for this
        player.sendMessage("Schematic " + file.getName() + " Saved");
    }

    // This saves all blocks in a 32x32x32 cuboid around the player to a schematic file,
    // This is just a temporary schematic for "Resetting" the area after a raid.
    public void blockCache(Player player) {
        final File file = new File(RaidableBases.getInstance().getDataFolder(), "schematics/" + player.getName() + ".schem");

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        Location pos1 = playerLocationUtils.pos1(player);
        Location pos2 = playerLocationUtils.pos2(player);

        WorldEditHook.save(pos1, pos2, player.getLocation(), file);
    }


}
