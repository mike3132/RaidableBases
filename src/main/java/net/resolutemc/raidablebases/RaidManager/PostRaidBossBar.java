package net.resolutemc.raidablebases.RaidManager;

import net.resolutemc.raidablebases.Chat.ColorTranslate;
import net.resolutemc.raidablebases.RaidableBases;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PostRaidBossBar {

    //TODO: Make these config variables
    private final String barTitle = ColorTranslate.chatColor("&bRemoving the raid in &d");
    private final String barEnding = ColorTranslate.chatColor(" &bseconds");
    private final String barColor = "GREEN";
    private final String barStyle = "SOLID";
    private final BossBar bar = Bukkit.createBossBar(barTitle, BarColor.valueOf(barColor), BarStyle.valueOf(barStyle));
    private int time = 11;

    public void addBar(Player player) {
        bar.addPlayer(player);
        barChanger(player);
    }

    private void barChanger(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (time != 0) {
                    time --;
                    bar.setProgress(time / 10D);
                    bar.setTitle(barTitle + time + barEnding);
                    return;
                }
                bar.removePlayer(player);
                this.cancel();
            }
        }.runTaskTimer(RaidableBases.getInstance(), 0, 20L);
    }


}
