package net.resolutemc.raidablebases.Utils;

import net.resolutemc.raidablebases.Chat.ColorTranslate;
import net.resolutemc.raidablebases.RaidableBases;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;

public class RegionViewer {

    private final PlayerLocationUtils playerLocationUtils = new PlayerLocationUtils();
    private final NamespacedKey key = new NamespacedKey(RaidableBases.getInstance(), "Bar-Key");

    public void removeRegion(Player player) {
        player.sendMessage("Removing region preview");
        player.sendMessage("To remove fake blocks teleport away or click them");
        RaidableBases.getInstance().getParticleCubeHandler().removeCube(player);
        removeBossBar(player);
    }

    public void showRegion(Player player) {
        Location pos1 = playerLocationUtils.pos1(player);
        Location pos2 = playerLocationUtils.pos2(player);
        RaidableBases.getInstance().getParticleCubeHandler().removeCube(player);
        removeBossBar(player);
        addTitle(player);
        addBossBar(player);
        beaconMaker(player);
        createPos1Sign(player);
        createPos2Sign(player);

        //TODO: Make messages config for this
        player.sendMessage("Please note that any blocks shown here are not real and only show for you");
        player.sendMessage("If you interact with them in anyway they will disappear");
        player.sendMessage("To disable use /rb region remove");

        RaidableBases.getInstance().getParticleCubeHandler().addCube(player, pos1, pos2);
    }

    private void beaconMaker(Player player) {
        for (int x = -1; x <= 1; ++x) {
            for (int z = -1; z <= 1; ++z) {
                player.sendBlockChange(player.getLocation().add(x, -2, z), Material.DIAMOND_BLOCK.createBlockData());
            }
        }
        player.sendBlockChange(player.getLocation().add(0, -1, 0), Material.BEACON.createBlockData());
    }

    private void addTitle(Player player) {
        //TODO: Make these config variables
        String title = ColorTranslate.chatColor("&5Raidable &dBases");
        String subTitle = ColorTranslate.chatColor("&bDrawing the size of a raid now");
        int fadeIn = 10;
        int stay = 100;
        int fadeOut = 10;
        player.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
    }


    private void addBossBar(Player player) {
        //TODO: Make these config variables
        String title = ColorTranslate.chatColor("&bRegion view active at ");
        String color = "RED";
        String style = "SOLID";
        KeyedBossBar bar = Bukkit.createBossBar(key, title, BarColor.valueOf(color), BarStyle.valueOf(style));
        String world = player.getWorld().getName();
        int x = player.getLocation().getBlockX();
        int y = player.getLocation().getBlockY();
        int z = player.getLocation().getBlockZ();
        String barTitle = world + " " + x + " " + y + " " + z;

        bar.setTitle(title + barTitle);
        bar.addPlayer(player);
    }

    private void removeBossBar(Player player) {
        KeyedBossBar bar = Bukkit.getBossBar(key);
        if (bar == null) return;
        bar.removePlayer(player);
    }

    private void createPos1Sign(Player player) {
        Location pos1 = playerLocationUtils.pos1(player);
        player.sendBlockChange(pos1, Material.DIAMOND_BLOCK.createBlockData());
        player.sendBlockChange(pos1.add(0,1,0), Material.MANGROVE_SIGN.createBlockData());
        String[] signString = new String[] {
                ColorTranslate.chatColor("&4&l&ka&bRaidable &bBases&4&l&ka"),
                ColorTranslate.chatColor("&2&lPosition 1"),
                ColorTranslate.chatColor("&6Left Click With"),
                ColorTranslate.chatColor("&d&lThe Wand V")
        };
        player.sendSignChange(pos1, signString);
    }
    private void createPos2Sign(Player player) {
        Location pos2 = playerLocationUtils.pos2(player);
        player.sendBlockChange(pos2, Material.EMERALD_BLOCK.createBlockData());
        player.sendBlockChange(pos2.add(0,1,0), Material.MANGROVE_SIGN.createBlockData());
        String[] signString = new String[] {
                ColorTranslate.chatColor("&4&l&ka&bRaidable &bBases&4&l&ka"),
                ColorTranslate.chatColor("&2&lPosition 2"),
                ColorTranslate.chatColor("&6Right Click With"),
                ColorTranslate.chatColor("&d&lThe Wand V")
        };
        player.sendSignChange(pos2, signString);
    }



}
