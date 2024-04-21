package net.resolutemc.raidablebases.Command;

import net.resolutemc.raidablebases.Chat.ConsoleMessages;
import net.resolutemc.raidablebases.Items.WandItem;
import net.resolutemc.raidablebases.PreRaid.PreRaidBossBar;
import net.resolutemc.raidablebases.RaidableBases;
import net.resolutemc.raidablebases.Schematics.SchematicRemover;
import net.resolutemc.raidablebases.Schematics.SchematicSaver;
import net.resolutemc.raidablebases.Utils.*;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AdminCommand implements CommandExecutor {

    private final RaidableBases raidableBases;
    private final RaidManager raidManager = new RaidManager(RaidableBases.getInstance());
    private final SchematicSaver schematicSaver = new SchematicSaver();

    // TODO: Remove this after debugging as they are only here so I can manually call them
    private final PlayerLocationUtils playerLocationUtils = new PlayerLocationUtils();

    public AdminCommand(RaidableBases raidableBases) {
        RaidableBases.getInstance().getCommand("RaidableBases").setExecutor(this);
        this.raidableBases = raidableBases;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            ConsoleMessages.sendMessage(sender, "Player-Only");
            return false;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("Not enough args placeholder");
            return false;
        }

        //TODO: Add messages for all these placeholders I keep making
        RegionUtils region = this.raidableBases.getRegionCache().get(player.getUniqueId());
        switch (args[0].toUpperCase()) {
            case "WAND":
                if (player.getInventory().firstEmpty() == -1) {
                    player.sendMessage("Wand Inventory full placeholder");
                    return false;
                }
                WandItem wandItem = new WandItem();
                ItemStack wand = wandItem.getWand();
                player.getInventory().addItem(wand);
                player.sendMessage("Given wand placeholder");
                break;
            case "SAVE":
                if (args.length != 2) {
                    player.sendMessage("Please select a name placeholder");
                    return true;
                }
                schematicSaver.schematicSave(player, args[1]);
                break;
            case "LOAD":
                if (args.length != 2) {
                    player.sendMessage("Please select a name or random placeholder");
                    return true;
                }
                if (args[1].equalsIgnoreCase("random")) {
                    raidManager.starRandomRaid(player, args[1]);
                    return false;
                }
                raidManager.startSpecificRaid(player, args[1]);
                break;
            case "DESELECT":
                if (region.getPos1() != null && region.getPos2() != null) {
                    region.setPos1(null);
                    region.setPos2(null);
                    player.sendMessage("Selections cleared placeholder");
                    return false;
                }
                player.sendMessage("There is no regions to clear");
                break;
            case "REGIONS":
                if (region.getPos1() != null || region.getPos2() != null) {
                    player.sendMessage("Region 1 is set at " + region.getPos1());
                    player.sendMessage("Region 2 is set at " + region.getPos2());
                    return false;
                }
                player.sendMessage("You only have 1 or no regions set");
                break;
            //TODO: Remove all this debug code
            case "RESET":
                if (args.length != 2) {
                    player.sendMessage("Please select a name or random placeholder");
                    return true;
                }
                raidManager.raidEnd(player);
                break;
            case "CREATEPARTICLE":
                this.raidableBases.getParticleCubeHandler().addCube(player, playerLocationUtils.pos1(player), playerLocationUtils.pos2(player));
                player.sendMessage("Spawning particle");
                break;
            case "CLEARPARTICLE":
                this.raidableBases.getParticleCubeHandler().removeCubes();
                player.sendMessage("Clearing all active particles");
                break;
            case "FORCESTOP":
                player.sendMessage("Force stopping raids");
                raidManager.raidForceStop(player);
                break;
            case "MAPCHECK":
                player.sendMessage(raidableBases.getRaidingPlayers().keySet().toString());
                player.sendMessage(raidableBases.getRaidingPlayers().values().toString());
                break;
            case "REMOVESCHEM":
                player.sendMessage("Removing schematic");
                SchematicRemover schematicRemover = new SchematicRemover();
                schematicRemover.removeSchematic(player);
                break;
            default:
                player.sendMessage("Please select pos1, pos2, save, or load placeholder");
        }
        return false;
    }


}
