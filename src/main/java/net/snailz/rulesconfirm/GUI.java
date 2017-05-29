/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.snailz.rulesconfirm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author snail
 */
public class GUI {
    RulesConfirm plugin;
    
    Inventory RulesTest;
    int questions_int;
    
    public GUI(RulesConfirm rc){
        plugin = rc;
        
        RulesTest = Bukkit.createInventory(null, 54, plugin.getConfig().getString("test_inventory_title"));
    
        questions_int = plugin.getConfig().getList("questions").size();
    }
    
    Inventory ConfirmStart = Bukkit.createInventory(null, 54, "Confirm that you would like to start");
    ///////
    //////
    //////
    
    public static List testtakers = new ArrayList();
    public static String hideID(String ID) {
        StringBuilder builder = new StringBuilder();

        for (char c : ID.toCharArray()) {
            builder.append(ChatColor.COLOR_CHAR).append(c);
        }

        String hidden = builder.toString();
        return hidden;
    }

    public static String unhideID(String ID) {
        ID = ID.replaceAll(String.valueOf(ChatColor.COLOR_CHAR), "");
        return ID;
    }
    
    public void createQuestion(Inventory inv, String question_id, String title, String moreinfo, Player player){
        ItemStack note = new ItemStack(Material.PAPER, 1);
        ItemMeta noteMeta = note.getItemMeta();
        noteMeta.setDisplayName(title);
        if (moreinfo != null){
            List noteLore = new ArrayList();
            //POSSIBLE BUG
            noteLore.add(0, moreinfo);
            noteMeta.setLore(noteLore);
        }
        note.setItemMeta(noteMeta);
        inv.setItem(4, note);
        for(int x = 0; x <= 53; x++){
            if (x >= 0 && x <= 3 || x >= 9 && x <= 12 || x >= 18 && x <= 21 || x >= 27 && x <= 30 || x >= 36 && x <= 39 || x >= 45 && x <= 48){
                ItemStack yes = new ItemStack(Material.EMERALD_BLOCK, 1);
                ItemMeta meta = yes.getItemMeta();
                meta.setDisplayName(ChatColor.GREEN + "Yes");
                List lore = new ArrayList();
                lore.add(0, hideID(question_id));
                meta.setLore(lore);
                yes.setItemMeta(meta);
                inv.setItem(x, yes);
            }
            if (x == 13 || x == 22 || x == 31 || x == 40 || x == 49){
                ItemStack seperate = new ItemStack(Material.BARRIER, 1);
                ItemMeta meta = seperate.getItemMeta();
                List lore = new ArrayList();
                lore.add(0, hideID(question_id));
                meta.setLore(lore);
                seperate.setItemMeta(meta);
                inv.setItem(x, seperate);
            }
            if (x >= 5 && x <= 8 || x >= 14 && x <= 17 || x >= 23 && x <= 26 || x >= 32 && x <= 35 || x >= 41 && x <= 44 || x >= 50 && x <= 54){
                ItemStack no = new ItemStack(Material.REDSTONE_BLOCK, 1);
                ItemMeta meta = no.getItemMeta();
                meta.setDisplayName(ChatColor.RED + "No");
                List lore = new ArrayList();
                lore.add(0, hideID(question_id));
                meta.setLore(lore);
                no.setItemMeta(meta);
                inv.setItem(x, no);
            }
        }
        player.openInventory(inv);
        testtakers.add(player);
        
    }
    
    public void openConfirmGUI(Player player){
        createQuestion(ConfirmStart,"0", "Are You Sure", "You may only take this test once every " + plugin.getConfig().getInt("confirm.time") + " seconds", player);     
    }
    
}
