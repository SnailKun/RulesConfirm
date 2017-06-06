/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.snailz.rulesconfirm;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author snail
 */
public class AdminCommands{
    RulesConfirm plugin;
    
    public AdminCommands(RulesConfirm ins){
        plugin = ins;
    }
    
    void help(CommandSender sender){
        sender.sendMessage(ChatColor.GREEN + "-----------------RulesConfirm Admin-----------------");
        sender.sendMessage(ChatColor.GREEN + "/rulesconfirm admin reload - Reloads the config");
        sender.sendMessage(ChatColor.GREEN + "/rulesconfirm admin add <question> <answer(true/false)>");
        sender.sendMessage(ChatColor.GREEN + "/rulesconfirm admin remove <question>");
        sender.sendMessage(ChatColor.GREEN + "--------------------------------------------------");
    }
    void reload(CommandSender sender) throws UnsupportedEncodingException{
        plugin.reloadConfig();
        plugin.reloadPlayersFile();
        sender.sendMessage(plugin.prefix + ChatColor.GREEN + "Configuration Files Have Been Reloaded");
    }
    
    void add(CommandSender sender, String[] args){
        String new_args = "";
        String answer = "";
        for (int x = 2; x < args.length; x++){
            plugin.getLogger().log(Level.INFO, Integer.toString(args.length - 1));
            plugin.getLogger().log(Level.INFO, "index " + x);
            if (args[x].equalsIgnoreCase("false") || args[x].equalsIgnoreCase("true")){
                answer = args[x];
                plugin.getLogger().log(Level. INFO, "answer " + answer);
                break;
            }
            if (new_args.equalsIgnoreCase("")){
                new_args = args[x];
            } else{
                new_args = new_args + " " + args[x];
            }
            plugin.getLogger().log(Level.INFO, "new args = " + new_args);
        }
        if (answer.equalsIgnoreCase("") || new_args.equalsIgnoreCase("")){
            sender.sendMessage(plugin.prefix + ChatColor.RED + "You must specify a question and an answer with either true or false!");
            return;
        }
        String fullquestion = answer + ":" + new_args;
        ArrayList<String> questions = new ArrayList<String>();
        for (String question : plugin.getConfig().getStringList("questions")){
            questions.add(question);
        }
        questions.add(fullquestion);
        plugin.getConfig().set("questions", questions);
        plugin.saveConfig();
        sender.sendMessage(plugin.prefix + ChatColor.GOLD + new_args + ChatColor.GREEN + " has been added to the question list!");
        
    }
    
    void remove(CommandSender sender, String[] args){
        String new_args = "";
        for (int x = 2; x < args.length; x++) {
            new_args = new_args + " " + args[x];
        }
        for (String question : plugin.getConfig().getStringList("questions")){
            String [] q_split = question.split(":");
            if (q_split[1].equalsIgnoreCase(new_args)){
                plugin.getConfig().set("questions", plugin.getConfig().getStringList("questions").remove(question));
                plugin.saveConfig();
                sender.sendMessage(plugin.prefix + ChatColor.GOLD + new_args + ChatColor.GREEN + " has been removed from the question list!");
            }
        }
    }

    
    public void adminCommand(String[] args, CommandSender sender) throws UnsupportedEncodingException{
        if (args.length == 1){
           help(sender); 
           return;
        }
        if (args[1].equalsIgnoreCase("reload")){
            reload(sender);
            return;
        }
        
        if (args[1].equalsIgnoreCase("add")){
            add(sender, args);
        }
        if (args[1].equalsIgnoreCase("remove")){
            remove(sender, args);
        }

    }
  
    
}
