package net.resolutemc.raidablebases.Schematics;

import net.resolutemc.raidablebases.Hook.WorldEditHook;
import net.resolutemc.raidablebases.RaidableBases;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SchematicLoader {

    public void loadSpecificSchematic(Player player, String string) {
        File file = new File(RaidableBases.getInstance().getDataFolder(), "schematics/" + string + ".schem");

        if (!file.exists()) {
            //TODO: Make messages config for this
            player.sendMessage("Name not found placeholder");
            return;
        }
        WorldEditHook.load(player.getLocation(), file);
    }


    public void loadRandomSchematic(Player player, String string) {
        File folder = new File(RaidableBases.getInstance().getDataFolder(), "schematics");
        //TODO: Make messages config for this
        if (!folder.exists()) {
            player.sendMessage("That directory doesn't exist placeholder");
            return;
        }
        List<String> fileList = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (file == null) return;
            if (file.getName().endsWith(".schem")) {
                fileList.add(String.valueOf(file));
            }
        }
        if (fileList.isEmpty()) return;
        Random random = new Random();
        int randomFromList = random.nextInt(fileList.size());
        String randomFileString = fileList.get(randomFromList);
        File file = new File(randomFileString);

        WorldEditHook.load(player.getLocation(), file);
        fileList.clear();

    }

    public void callBlockCache(Player player) {
        File file = new File(RaidableBases.getInstance().getDataFolder(), "schematics/" + player.getName() + ".schem");

        if (!file.exists()) {
            //TODO: Make messages config for this
            player.sendMessage("Name not found placeholder");
            return;
        }

        WorldEditHook.load(player.getLocation(), file);
    }

}
