package net.resolutemc.raidablebases.Command;

import net.resolutemc.raidablebases.RaidableBases;
import net.resolutemc.raidablebases.Schematics.SchematicLoader;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConsoleCommand implements CommandExecutor {

    RaidableBases raidableBases;
    SchematicLoader schematicLoader = new SchematicLoader();

    public ConsoleCommand(RaidableBases raidableBases) {
        RaidableBases.getInstance().getCommand("ConsoleRaidableBases").setExecutor(this);
        this.raidableBases = raidableBases;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            sender.sendMessage("Players cannot use this command placeholder");
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage("No");
            return true;
        }

        if (!args[0].equalsIgnoreCase("Load")) {
            sender.sendMessage("Please use load");
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage("Please select a name placeholder");
            return true;
        }
        if (!args[1].equalsIgnoreCase("Random")) {
            sender.sendMessage("Please use random, You cannot load specific schematics from console placeholder");
            return true;
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            schematicLoader.loadRandomSchematic(player, args[1]);
        }
        sender.sendMessage("Random Schematic loaded placeholder");


        return false;
    }
}
