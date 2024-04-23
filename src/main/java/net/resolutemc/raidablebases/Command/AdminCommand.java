package net.resolutemc.raidablebases.Command;

import net.resolutemc.raidablebases.Chat.ConsoleMessages;
import net.resolutemc.raidablebases.Items.WandItem;
import net.resolutemc.raidablebases.RaidManager.RaidManager;
import net.resolutemc.raidablebases.RaidableBases;
import net.resolutemc.raidablebases.Schematics.SchematicList;
import net.resolutemc.raidablebases.Schematics.SchematicSaver;
import net.resolutemc.raidablebases.Utils.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AdminCommand implements CommandExecutor {

    private final RaidableBases raidableBases;
    private final RaidManager raidManager = new RaidManager(RaidableBases.getInstance());
    private final SchematicSaver schematicSaver = new SchematicSaver();
    private final SchematicList schematicList = new SchematicList();
    private final RegionViewer regionViewer = new RegionViewer();

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
        WandItem wandItem = new WandItem();
        switch (args[0].toUpperCase()) {
            case "SETUP":
                if (args.length != 2) {
                    player.sendMessage("Pick either enable or disable");
                    return false;
                }
                if (args[1].equalsIgnoreCase("Enable")) {
                    if (player.getInventory().firstEmpty() == -1) {
                        player.sendMessage("Wand Inventory full placeholder");
                        return false;
                    }
                    ItemStack wand = wandItem.getWand();
                    player.getInventory().addItem(wand);
                    raidableBases.getSetupModePlayers().add(player.getUniqueId());
                    player.sendMessage("Given wand placeholder");
                    player.sendMessage("Setup enabled");
                    player.sendMessage("Press your 'swap item with offhand' keybind to copy your inventory to the chest you are looking at");
                    return false;
                }
                if (args[1].equalsIgnoreCase("Disable")) {
                    ItemStack wand = wandItem.getWand();
                    for (ItemStack item : player.getInventory()) {
                        if (item == null) continue;
                        if (item.isSimilar(wand)) {
                            item.setAmount(0);
                        }
                    }
                    raidableBases.getSetupModePlayers().remove(player.getUniqueId());
                    player.sendMessage("Setup disabled");
                    return false;
                }
                player.sendMessage("That is not enable or disable");
                break;
            case "WAND" :
                if (player.getInventory().firstEmpty() == -1) {
                    player.sendMessage("Wand Inventory full placeholder");
                    return false;
                }
                ItemStack wand = wandItem.getWand();
                player.getInventory().addItem(wand);
                player.sendMessage("Given a wand");
                break;
            case "SCHEMATIC":
                if (args.length > 1) {
                    if (args[1].equalsIgnoreCase("List")) {
                        schematicList.listSchematics(player);
                        return false;
                    }
                    if (args[1].equalsIgnoreCase("Save")) {
                        if (args.length > 2) {
                            if (region == null) {
                                player.sendMessage("Please select both pos1 and pos2 first placeholder");
                                return false;
                            }
                            schematicSaver.schematicSave(player, args[2]);
                            return false;
                        }
                        player.sendMessage("Please select a name for your schematic placeholder");
                        return false;
                    }
                    player.sendMessage("That arg is not found, Pick save or list placeholder");
                    return false;
                }
                player.sendMessage("Please pick save or list");
                break;
            case "START":
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
            case "STOP":
                if (args.length != 2) {
                    player.sendMessage("Please select a player's raid to stop");
                    return false;
                }
                raidManager.raidEnd(player);
                break;
            case "DESELECT":
                if (region.getPos1() != null && region.getPos2() != null) {
                    region.setPos1(null);
                    region.setPos2(null);
                    player.sendMessage("Selections cleared placeholder");
                    raidableBases.getRegionCache().remove(player.getUniqueId());
                    return false;
                }
                player.sendMessage("There is no regions to clear");
                break;
            case "POSITIONS":
                if (region == null || region.getPos1() == null || region.getPos2() == null) {
                    player.sendMessage("You only have 1 or no regions set");
                    return false;
                }
                // Create a system to show where positions are set
                player.sendMessage("Region 1 is set at " + region.getPos1());
                player.sendMessage("Region 2 is set at " + region.getPos2());
                break;
            case "REGION":
                if (args.length != 2) {
                    player.sendMessage("Please select either show or remove");
                    return false;
                }
                if (args[1].equalsIgnoreCase("Show")) {
                    regionViewer.showRegion(player);
                    return false;
                }
                if (args[1].equalsIgnoreCase("Remove")) {
                    regionViewer.removeRegion(player);
                    return false;
                }
                player.sendMessage("That is not show or remove");
                break;
            //TODO: Remove all this debug code
            case "RESET":
                if (args.length != 2) {
                    player.sendMessage("Please select a name or random placeholder");
                    return true;
                }
                raidManager.raidEnd(player);
                break;
            case "MAPADD":
                raidableBases.getRaidingPlayers().put(player.getUniqueId(), player.getLocation());
                player.sendMessage("Added to the map");
                break;
            case "MAPREMOVE":
                raidableBases.getRaidingPlayers().remove(player.getUniqueId());
                player.sendMessage("Removed from the map");
                break;
            default:
                player.sendMessage("Please select pos1, pos2, save, or load placeholder");
        }
        return false;
    }
}
