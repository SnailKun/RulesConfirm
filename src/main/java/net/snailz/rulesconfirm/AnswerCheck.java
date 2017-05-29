/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.snailz.rulesconfirm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author snail
 */
public class AnswerCheck {
    RulesConfirm plugin;
    GUI gui;
    public static HashMap<Player, Integer> right = new HashMap<>();
    public static HashMap<Player, Integer> wrong = new HashMap<>();
    public static HashMap<Player, String>currentquestion = new HashMap<>();
    public static ArrayList<Player> failedplayers = new ArrayList();
    public static HashMap<Player, List<String>> playerquestions = new HashMap<>();
    public static HashMap<Player, Integer> playerquestions_left = new HashMap<>();
    
    public AnswerCheck(RulesConfirm pl, GUI gui_ins){
        plugin = pl;
        gui = gui_ins;
    }
    
    public void startTest(Player player){
        right.put(player, 0);
        wrong.put(player, 0);
        Random random = new Random();
        playerquestions_left.put(player, gui.questions_int);
        playerquestions.put(player, plugin.getConfig().getStringList("questions"));
        List<String> possiable_questions = playerquestions.get(player);
        String question = possiable_questions.get(random.nextInt(playerquestions_left.get(player)));
        possiable_questions.remove(question);
        playerquestions_left.put(player, playerquestions_left.get(player) - 1);
        playerquestions.put(player, possiable_questions);
        currentquestion.put(player, question);
        String[] q_split = question.split(":");
        gui.createQuestion(gui.RulesTest, "1", q_split[1], null, player);
    }
    public void checkAnswer(Player player, ItemStack clicked){
        if (clicked.getType() == Material.BARRIER || clicked.getType() == Material.PAPER){
            return;
        }
        if (GUI.unhideID(clicked.getItemMeta().getLore().get(0)).equalsIgnoreCase("0")){
            if (clicked.getType() == Material.EMERALD_BLOCK){
                startTest(player);
            }
            if (clicked.getType() == Material.REDSTONE_BLOCK){
                player.closeInventory();
                GUI.testtakers.remove(player);
            }
            return;
        }
        if (GUI.unhideID(clicked.getItemMeta().getLore().get(0)).equalsIgnoreCase(Integer.toString(gui.questions_int))){
            GUI.testtakers.remove(player);
            //
            String question = currentquestion.get(player);
            String[] q_split = question.split(":");
            Boolean answer = Boolean.getBoolean(q_split[0]);
            Boolean player_answer = null;
            //START ANSWER CHECK
            if (clicked.getType() == Material.EMERALD_BLOCK) {
                player_answer = true;
            }
            if (clicked.getType() == Material.REDSTONE_BLOCK) {
                player_answer = false;
            }
            //
            if (player_answer == answer) {
                try {
                    right.put(player, right.get(player) + 1);
                } catch(NullPointerException e){
                    right.put(player, 1);
                }
            }
            if (player_answer == answer) {
                try{
                    wrong.put(player, wrong.get(player) + 1);
                } catch(NullPointerException e){
                    wrong.put(player, 1);
                }
            }
            //START RESULTS
            double score;
            score = ((double) right.get(player)/(double) gui.questions_int);
            score = score * 100;
            
            if (score >= plugin.getConfig().getInt("passing_grade")){
                plugin.getPlayersFile().set("pass", plugin.getPlayersFile().getStringList("pass").add(player.getUniqueId().toString()));
                String message = plugin.getConfig().getString("messages.pass");
                if (message.contains("%score%")){
                    String replace = message.replace("%score%", "%" + Double.toString(score));
                    player.sendMessage(plugin.prefix + ChatColor.GREEN + replace);
                } else{
                    player.sendMessage(plugin.prefix + ChatColor.GREEN + message);
                }
                plugin.getPlayersFile().set(player.getUniqueId().toString(), true);
                plugin.savePlayersFile();
            } else{
                failedplayers.add(player);
                String message = plugin.getConfig().getString("messages.fail");
                if (message.contains("%score%")) {
                    String replace = message.replace("%score%", "%" + Double.toString(score));
                    player.sendMessage(plugin.prefix + ChatColor.RED + replace); 
                }else{
                    player.sendMessage(plugin.prefix + ChatColor.RED + message);
                }
            }
            player.closeInventory();
            //START HASHMAP REMOVAL
            right.remove(player);
            wrong.remove(player);
            currentquestion.remove(player);
            playerquestions.remove(player);
            playerquestions_left.remove(player);
            return;
        }
        String question = currentquestion.get(player);
        String[] q_split = question.split(":");
        Boolean answer = Boolean.getBoolean(q_split[0]);
        Boolean player_answer = null;
        //START ANSWER CHECK
        if (clicked.getType() == Material.EMERALD_BLOCK){
            player_answer = true;
        }
        if (clicked.getType() == Material.REDSTONE_BLOCK){
            player_answer = false;
        }
        //
        if (player_answer == answer) {
            right.put(player, right.get(player) + 1);
        }
        if (player_answer != answer){
            wrong.put(player, wrong.get(player) + 1);
        }
        //START NEXT QUESTION OPEN
        Random random = new Random();
        List<String> possiable_questions = playerquestions.get(player);
        String new_question = possiable_questions.get(random.nextInt(playerquestions_left.get(player)));
        possiable_questions.remove(new_question);
        playerquestions_left.put(player, playerquestions_left.get(player) - 1);
        playerquestions.put(player, possiable_questions);
        currentquestion.put(player, new_question);
        String[] new_q_split = new_question.split(":");
        int newid = Integer.parseInt(GUI.unhideID(clicked.getItemMeta().getLore().get(0))) + 1;
        String newid_str = Integer.toString(newid);
        gui.createQuestion(gui.RulesTest, newid_str, new_q_split[1], null, player);
        
    }
}
