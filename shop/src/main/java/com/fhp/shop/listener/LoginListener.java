package com.fhp.shop.listener;

import com.fhp.shop.manager.CoinManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * 玩家上线事件监听
 */
public class LoginListener implements Listener {
    //玩家类
    private static Player player;
    /**
     * 玩家上线
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        //定制欢迎文字
        String welcomeMessage = ChatColor.GOLD + "欢迎 " + ChatColor.BLUE + player.getName() + ChatColor.GOLD + "  加入服务器!";
        //发送消息给所有玩家
        Bukkit.broadcastMessage(welcomeMessage);
        //更新记分板金币信息
        CoinManager.updateScoreboard(player);
    }
}
