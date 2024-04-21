package net.resolutemc.raidablebases.Schematics;

import net.resolutemc.raidablebases.RaidableBases;
import org.bukkit.entity.Player;

import java.io.File;

public class SchematicRemover {

   public void removeSchematic(Player player) {
       File file = new File(RaidableBases.getInstance().getDataFolder(), "schematics/" + player.getName() + ".schem");

       if (!file.exists()) {
           //TODO: Make messages config for this
           player.sendMessage("Name not found placeholder");
           return;
       }
       file.delete();
   }


}
