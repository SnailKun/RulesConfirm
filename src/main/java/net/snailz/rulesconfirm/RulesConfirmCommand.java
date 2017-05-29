/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.snailz.rulesconfirm;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author snail
 */
public class RulesConfirmCommand implements CommandExecutor{
    RulesConfirm plugin;
    GUI gui;
    AnswerCheck ac;
    String command_when_failed_message;
    public RulesConfirmCommand(RulesConfirm ins, GUI gui_ins, AnswerCheck ac_ins){
        plugin = ins;
        gui = gui_ins;
        ac = ac_ins;
        command_when_failed_message = ChatColor.RED + plugin.getConfig().getString("confirm.timer_message");
        
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (command.getName().equalsIgnoreCase("rulesconfirm")){
            Player player = (Player) sender;
            if (TestTimer.playertime.containsKey(player)){
                player.sendMessage(plugin.prefix + command_when_failed_message);
                return true;
            }
            if (plugin.getConfig().getBoolean("confirm.enabled") == true){
                gui.openConfirmGUI(player);
                return true;
            }
            if (plugin.getConfig().getBoolean("confirm.enabled") == false){
                ac.startTest(player);
                return true;
            }
        }
        return false;
    }
}
