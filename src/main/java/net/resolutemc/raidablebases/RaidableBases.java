package net.resolutemc.raidablebases;

import net.resolutemc.raidablebases.Chat.ColorTranslate;
import net.resolutemc.raidablebases.Command.AdminCommand;
import net.resolutemc.raidablebases.Command.ConsoleCommand;
import net.resolutemc.raidablebases.Config.ConfigCreator;
import net.resolutemc.raidablebases.Event.PostRaidEvent;
import net.resolutemc.raidablebases.Event.PreRaidEvent;
import net.resolutemc.raidablebases.Event.WandEvent;
import net.resolutemc.raidablebases.Utils.ParticleCubeHandler;
import net.resolutemc.raidablebases.Utils.RaidManager;
import net.resolutemc.raidablebases.Utils.RegionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class RaidableBases extends JavaPlugin {

    private static RaidableBases INSTANCE;
    private ParticleCubeHandler particleCubeHandler;
    private final List<UUID> preRaidPlayers = new ArrayList<>();
    private final List<UUID> postRaidPlayers = new ArrayList<>();
    private final Map<UUID, RegionUtils> regionCache = new HashMap<>();
    private final Map<UUID, Location> raidingPlayers = new HashMap<>();
    private final RaidManager raidManager = new RaidManager(this);

    public List<UUID> getPreRaidPlayers() {
        return preRaidPlayers;
    }
    public List<UUID> getPostRaidPlayers() {
        return postRaidPlayers;
    }
    public Map<UUID, Location> getRaidingPlayers() {
        return raidingPlayers;
    }
    public Map<UUID, RegionUtils> getRegionCache() {
        return regionCache;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;

        getServer().getConsoleSender().sendMessage(ColorTranslate.chatColor("&2Enabled"));

        // Event loaders
        Bukkit.getPluginManager().registerEvents(new WandEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new PreRaidEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new PostRaidEvent(this), this);


        // Command registers
        registerAdminCommand();
        registerConsoleCommand();

        // Config loader
        ConfigCreator.MESSAGES.create();
        saveDefaultConfig();

        // Loads particle system
        this.particleCubeHandler = new ParticleCubeHandler(this);

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ColorTranslate.chatColor("&4Disabled"));

        // Disables particle system
        this.particleCubeHandler.disable();

        // Forces all raids to end without a timer and resets the area around the player
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (this.getRaidingPlayers().containsKey(player.getUniqueId())) {
                raidManager.raidForceStop(player);
            }
        }

    }

    public static RaidableBases getInstance() {
        return INSTANCE;
    }

    // Command loaders
    private void registerAdminCommand() {
        new AdminCommand(this);
    }
    private void registerConsoleCommand() {
        new ConsoleCommand(this);
    }

    public ParticleCubeHandler getParticleCubeHandler() {
        return this.particleCubeHandler;
    }



}
