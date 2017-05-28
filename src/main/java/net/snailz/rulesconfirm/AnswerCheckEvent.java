/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.snailz.rulesconfirm;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author snail
 */
public class AnswerCheckEvent implements Listener{
    
    RulesConfirm plugin;
    AnswerCheck ac;
    
    public AnswerCheckEvent(RulesConfirm ins, AnswerCheck ac_ins){
        plugin = ins;
        ac = ac_ins;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked(); // The player that clicked the item
        ItemStack clicked = event.getCurrentItem(); // The item that was clicked
        Inventory inventory = event.getInventory(); // The inventory that was clicked in
        if (GUI.testtakers.contains(player)){
            event.setCancelled(true);
            ac.checkAnswer(player, clicked);
        }
    }
}
