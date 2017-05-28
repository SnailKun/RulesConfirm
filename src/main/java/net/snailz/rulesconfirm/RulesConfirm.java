/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.snailz.rulesconfirm;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import sun.applet.Main;
import net.snailz.rulesconfirm.AnswerCheck;
import net.snailz.rulesconfirm.GUI;

/**
 *
 * @author snail
 */
public class RulesConfirm extends JavaPlugin{
    
    public String prefix = ChatColor.RED + "[" + ChatColor.LIGHT_PURPLE + getConfig().getString("prefix") + ChatColor.RED + "] " + ChatColor.RESET;
//BEGIN CONFIG LOADING
    private FileConfiguration players = null;
    private File playersfile = null;

    public void reloadPlayersFile() throws UnsupportedEncodingException {
        if (playersfile == null) {
            playersfile = new File(getDataFolder(), "players.yml");
        }
        players = YamlConfiguration.loadConfiguration(playersfile);

        // Look for defaults in the jar
        Reader defConfigStream;
        defConfigStream = new InputStreamReader(this.getResource("players.yml"), "UTF8");

        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            players.setDefaults(defConfig);
        }

    }

    public void savePlayersFile() {
        if (players == null || playersfile == null) {
            return;
        }
        try {
            getPlayersFile().save(playersfile);
        } catch (IOException ex) {
            getLogger().log(SEVERE, "Could not save config to " + playersfile + " Please report this to Snailz", ex);
        }
    }

    public FileConfiguration getPlayersFile() {
        if (players == null) {
            try {
                reloadPlayersFile();
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return players;
    }

    public void saveDefaultPlayersFile() {
        if (playersfile == null) {
            playersfile = new File(getDataFolder(), "players.yml");
        }
        if (!playersfile.exists()) {
            this.saveResource("players.yml", false);
        }
    }
//END CONFIG LOADING
    public void onEnable(){
        this.saveDefaultPlayersFile();
        this.saveDefaultConfig();
        if (this.getConfig().getBoolean("confirm.enabled")){
            this.getServer().getScheduler().runTaskTimer(this, new TestTimer(this), 0L, 10L);
        }
        this.getCommand("rulesconfirm").setExecutor(new RulesConfirmCommand(this, new GUI(this), new AnswerCheck(this, new GUI(this))));
        this.getServer().getPluginManager().registerEvents(new AnswerCheckEvent(this, new AnswerCheck(this, new GUI(this))), this);
        this.getServer().getPluginManager().registerEvents(new PlayerRestrict(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerKeyRemove(this), this);
    }
}
