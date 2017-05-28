/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.snailz.rulesconfirm;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 *
 * @author snail
 */
public class PlayerRestrict implements Listener{
    
    RulesConfirm plugin;
    
    public PlayerRestrict(RulesConfirm ins){
        plugin = ins;
    }
    
    boolean move = plugin.getConfig().getBoolean("restrictions.move.enabled");
    boolean chat = plugin.getConfig().getBoolean("restrictions.chat.enabled");
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if (move != true){
            return;
        }
        if (plugin.getPlayersFile().contains(e.getPlayer().getUniqueId().toString())){
            return;
        }
        e.setCancelled(true);
        e.getPlayer().sendMessage(plugin.prefix + ChatColor.RED + plugin.getPlayersFile().getString("restrictions.move.message"));
    }
    
    @EventHandler
    public void onPlayerChat(PlayerChatEvent e){
        if (chat != true) {
            return;
        }
        if (plugin.getPlayersFile().contains(e.getPlayer().getUniqueId().toString())) {
            return;
        }
        e.setCancelled(true);
        e.getPlayer().sendMessage(plugin.prefix + ChatColor.RED + plugin.getPlayersFile().getString("restrictions.chat.message"));
    }
}
