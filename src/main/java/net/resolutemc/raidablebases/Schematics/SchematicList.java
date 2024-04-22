package net.resolutemc.raidablebases.Schematics;

import net.resolutemc.raidablebases.RaidableBases;
import org.bukkit.entity.Player;

import java.io.File;

public class SchematicList {

    public void listSchematics(Player player) {
        File folder = new File(RaidableBases.getInstance().getDataFolder(), "schematics/");

        //TODO: Make messages config for this
        if (!folder.exists()) {
            player.sendMessage("That directory doesn't exist placeholder");
            return;
        }
        File[] files = folder.listFiles();
        if (files == null) return;
        if (files.length == 0) {
            //TODO: Make messages config for this
            player.sendMessage("There is no schematics");
            return;
        }
        for (File file : files) {
            String fileName = file.getName();
            //TODO: Make messages config for this
            player.sendMessage("Current schematics are " + fileName);
        }

    }


}
