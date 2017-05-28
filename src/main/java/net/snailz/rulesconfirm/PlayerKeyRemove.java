/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.snailz.rulesconfirm;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author snail
 */
public class PlayerKeyRemove implements Listener{
    
    RulesConfirm plugin;
    public PlayerKeyRemove(RulesConfirm ins){
        plugin = ins;
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();
        if (AnswerCheck.right.containsKey(player)){
            AnswerCheck.right.remove(player);
        }
        if (AnswerCheck.wrong.containsKey(player)){
            AnswerCheck.wrong.remove(player);
        }
        if (AnswerCheck.currentquestion.containsKey(player)){
            AnswerCheck.currentquestion.remove(player);
        }
        if (AnswerCheck.failedplayers.contains(player)){
            AnswerCheck.failedplayers.remove(player);
        }
        if (AnswerCheck.playerquestions.containsKey(player)){
            AnswerCheck.playerquestions.remove(player);
        }
        if (AnswerCheck.playerquestions_left.containsKey(player)){
            AnswerCheck.playerquestions_left.remove(player);
        }
        if (TestTimer.playertime.containsKey(player)){
            TestTimer.playertime.remove(player);
        }
    }
}
