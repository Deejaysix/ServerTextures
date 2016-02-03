package me.deejaystix.genova;

import de.inventivegames.rpapi.ResourcePackStatusEvent;
import de.inventivegames.rpapi.Status;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;

public class ServerTextures extends JavaPlugin implements Listener {

    static File defaultFile;
    static FileConfiguration defaultConfig;
    
    @Override
    public void onEnable()
    {
        this.initalizeConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Enabled.");
    }
    
    public void initalizeConfig() {
        if(defaultFile == null) {
           defaultFile = new File(this.getDataFolder(), "config.yml");
        }
        if(!defaultFile.exists()) {
           this.saveResource("config.yml", false);
        }
        defaultConfig = YamlConfiguration.loadConfiguration(defaultFile);
    }
    
    @Override
    public void onDisable()
    {
      getLogger().info("Disabled.");
    }
    
    @EventHandler
    public void onResourcePackStatus(ResourcePackStatusEvent e) {
        Player player = e.getPlayer();
        Status status = e.getStatus();
        String eject = getConfig().get("MsgKick").toString();
        String thanks = getConfig().get("MsgThanks").toString();
        String download = getConfig().get("MsgDownload").toString();

        if (status == status.DECLINED) {
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + eject);
            Plugin pg = JavaPlugin.getPlugin(ServerTextures.class);
                Bukkit.getScheduler().runTaskLater(pg, new Runnable() {
                    @Override
                    public void run() {
                            player.kickPlayer(ChatColor.RED + "" + ChatColor.BOLD + eject);
                            getLogger().log(Level.INFO, player.getName() + " was kicked by ServerTextures !");
                    }
                }, 60L);
            }
        else {
            if (status == status.ACCEPTED) {
                player.sendMessage(ChatColor.GOLD + thanks);
                }
            else {
            if (status == status.SUCCESSFULLY_LOADED); {
                player.sendMessage(ChatColor.GOLD + download);
                }
            }
        }
    }
}
    
