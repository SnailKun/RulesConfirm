/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.snailz.rulesconfirm;

import java.util.logging.Level;
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
    boolean chat;
    boolean move;
    String movemessage;
    String chatmessage;
    
    public PlayerRestrict(RulesConfirm ins){
        plugin = ins;
        move = plugin.getConfig().getBoolean("restrictions.move.enabled");
        chat = plugin.getConfig().getBoolean("restrictions.chat.enabled");
        movemessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("restrictions.move.message"));
        chatmessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("restrictions.chat.message"));
    }
    

    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if (move != true){
            return;
        }
        if (plugin.getPlayersFile().contains(e.getPlayer().getUniqueId().toString())){
            return;
        }
        e.setCancelled(true);
        e.getPlayer().sendMessage(plugin.prefix + movemessage);
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
        e.getPlayer().sendMessage(plugin.prefix + chatmessage);
    }
}
