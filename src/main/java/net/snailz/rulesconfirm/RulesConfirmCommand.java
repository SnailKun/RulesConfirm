/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.snailz.rulesconfirm;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author snail
 */
public class RulesConfirmCommand implements CommandExecutor {

    RulesConfirm plugin;
    GUI gui;
    AnswerCheck ac;
    AdminCommands cc;
    String command_when_failed_message;
    String admin_message;

    public RulesConfirmCommand(RulesConfirm ins, GUI gui_ins, AnswerCheck ac_ins, AdminCommands cc_ins) {
        plugin = ins;
        gui = gui_ins;
        ac = ac_ins;
        cc = cc_ins;
        command_when_failed_message = plugin.getConfig().getString("confirm.timer_message");
        admin_message = plugin.getConfig().getString("messages.admin_permission");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("rulesconfirm")) {
            if (!(sender instanceof Player) && args.length == 0) {
                sender.sendMessage(plugin.prefix + "This command can only be executed by players!");
                return true;
            }
            try {
                if (args[0].equalsIgnoreCase("admin")) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        if (!player.hasPermission("rulesconfirm.admin")) {
                            player.sendMessage(plugin.prefix + ChatColor.translateAlternateColorCodes('&', admin_message));
                            return true;
                        }
                    }
                    try {
                        cc.adminCommand(args, sender);
                        return true;
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(RulesConfirmCommand.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }
            if (!(sender instanceof Player)) {
                sender.sendMessage(plugin.prefix + "This command can only be executed by players!");
            }
            Player player = (Player) sender;
            if (TestTimer.playertime.containsKey(player)) {
                player.sendMessage(plugin.prefix + ChatColor.translateAlternateColorCodes('&', command_when_failed_message));
                return true;
            }
            if (plugin.getConfig().getBoolean("confirm.enabled") == true) {
                gui.openConfirmGUI(player);
                return true;
            }
            if (plugin.getConfig().getBoolean("confirm.enabled") == false) {
                ac.startTest(player);
                return true;
            }
        }
        return false;
    }
}
