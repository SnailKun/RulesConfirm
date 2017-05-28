/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.snailz.rulesconfirm;

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
    public RulesConfirmCommand(RulesConfirm ins, GUI gui_ins, AnswerCheck ac_ins){
        plugin = ins;
        gui = gui_ins;
        ac = ac_ins;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (command.getName().equalsIgnoreCase("rulesconfirm")){
            Player player = (Player) sender;
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
