/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.snailz.rulesconfirm;

import java.util.HashMap;
import java.util.logging.Level;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author snail
 */
public class TestTimer extends BukkitRunnable {

    public static HashMap<Player, Integer> playertime = new HashMap<>();
    int time;
    RulesConfirm plugin;

    public TestTimer(RulesConfirm ins) {
        plugin = ins;
        time = plugin.getConfig().getInt("confirm.time");
    }
    
    public void run() {
        if (!(AnswerCheck.failedplayers.isEmpty())) {
            for (Player player : AnswerCheck.failedplayers) {
                if (playertime.get(player) == null) {
                    playertime.put(player, 0);
                    player.sendMessage(plugin.prefix + ChatColor.RED + plugin.getConfig().getString("confirm.start_timer_message"));
                    break;
                }
                if (playertime.get(player) == time) {
                    AnswerCheck.failedplayers.remove(player);
                    playertime.remove(player);
                    player.sendMessage(plugin.prefix + ChatColor.GREEN + plugin.getConfig().getString("confirm.end_timer_message"));
                    break;
                }
                playertime.put(player, playertime.get(player) + 1);
            }
        }
    }
}
