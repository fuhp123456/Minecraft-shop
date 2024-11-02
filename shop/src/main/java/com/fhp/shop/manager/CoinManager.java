package com.fhp.shop.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.UUID;

/**
 * 金币管理器
 */
public class CoinManager {
    //金币数据持久化类
    private static ConfigManager configManager;

    /**
     * 初始化
     * @param plugin 插件
     */
    public static void initialize(JavaPlugin plugin) {
        configManager = new ConfigManager(plugin);
    }

    /**
     * 向游戏中的一个玩家添加一定数量的金币
     * @param player 玩家
     * @param amount 金币数
     */
    public static void addCoins(Player player, int amount) {
        //返回该玩家的游戏UUID标识
        UUID playerId = player.getUniqueId();
        //根据UUID获取当前金币
        int currentCoins = configManager.getCoins(playerId);
        //设置获取到的金币数给该玩家
        configManager.setCoins(playerId, currentCoins + amount);
        //更新游戏内的记分板，以反映玩家硬币数量的变化。
        updateScoreboard(player);
    }

    /**
     * 从玩家的账户中移除一定数量的硬币
     * @param player 玩家
     * @param amount 金币数
     * @return
     */
    public static boolean removeCoins(Player player, int amount) {
        //返回该玩家的游戏UUID标识
        UUID playerId = player.getUniqueId();
        //根据UUID获取当前金币
        int currentCoins = configManager.getCoins(playerId);
        //如果当前金币大于该商品价格
        if (currentCoins >= amount) {
            //更新该玩家的金币
            configManager.setCoins(playerId, currentCoins - amount);
            //更新游戏内的记分板，以反映玩家硬币数量的变化。
            updateScoreboard(player);
            return true;
        }
        //玩家金币不够
        return false;
    }

    /**
     * 更新记分板金币信息
     * @param player 玩家
     */
    public static void updateScoreboard(Player player) {
        //获取记分板管理器的实例
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        //使用记分板管理器创建一个新的记分板实例
        Scoreboard board = manager.getNewScoreboard();
        //在记分板上注册一个新的目标（模板名称, 类型为自定义, 向玩家显示的名称）
        Objective objective = board.registerNewObjective("coins", Criteria.DUMMY, "金币");
        //设置对象显示的计分板位置区域为侧边栏
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        //获取一个与玩家名称相关联的分数对象，这个分数对象将用于显示玩家的硬币数量
        Score score = objective.getScore(player.getName());
        //将返回的硬币数量设置为玩家分数对象的分数值
        score.setScore(configManager.getCoins(player.getUniqueId()));

        //将更新后的记分板设置给玩家
        player.setScoreboard(board);
    }
}
