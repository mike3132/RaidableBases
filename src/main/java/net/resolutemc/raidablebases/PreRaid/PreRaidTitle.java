package net.resolutemc.raidablebases.PreRaid;

import net.resolutemc.raidablebases.Chat.ColorTranslate;
import org.bukkit.entity.Player;

public class PreRaidTitle {

    //TODO: Make Title and subTitle configurable
    private final String title = ColorTranslate.chatColor("&5Raidable &dBases");
    private final String subTitle = ColorTranslate.chatColor("&bPlease wait while we setup the raid");
    int fadeIn = 20;
    int stay = 200;
    int fadeOut = 20;

    public void sendTitle(Player player) {
        player.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
    }


}
