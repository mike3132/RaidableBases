package net.resolutemc.raidablebases.PostRaid;

import net.resolutemc.raidablebases.Chat.ColorTranslate;
import org.bukkit.entity.Player;

public class PostRaidTitle {

    //TODO: Make Title and subTitle configurable
    private String title = ColorTranslate.chatColor("&5Raidable &dBases");
    private String subTitle = ColorTranslate.chatColor("&bPlease wait while we remove the raid and reset the area");
    int fadeIn = 20;
    int stay = 200;
    int fadeOut = 20;

    public void sendTitle(Player player) {
        player.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
    }
}
