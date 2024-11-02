package com.fhp.shop.listener;

import com.fhp.shop.Shop;
import com.fhp.shop.manager.CoinManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.security.SecureRandom;

/**
 * 击杀事件监听器
 */
public class KillListener implements Listener {
    //config.yml文件配置
    private static FileConfiguration config;
    //击杀生物赚取的最少金币
    private static int min;
    //击杀生物赚取的最高金币
    private static int max;
    //击杀生物增加货币名称
    private static String coin;
    static {
        config = Shop.getShop().getConfig();
        min = config.getInt("MIN_Earned");
        max = config.getInt("MAX_Earned");
        coin = config.getString("text.coin");
    }

    /**
     * 如果生物实体死亡
     * @param event 实体死亡事件信息
     */
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){

        //检查死亡实体的杀手是否是一个玩家
        if (event.getEntity().getKiller() instanceof Player) {
            //获取击杀生物实体的玩家
            Player player = event.getEntity().getKiller();
            int coins = getCoins();
            //增加玩家金币
            CoinManager.addCoins(player,coins);
            //显示金币奖励的悬浮文本
            showFloatingText(event.getEntity().getLocation(), coins);
        }
    }

    /**
     * 显示金币浮动文本
     * @param location Minecraft世界中的一个位置
     * @param coins 增加的金币数
     */
    private void showFloatingText(Location location, int coins) {
        //在被击杀生物位置上创建 盔甲架 来显示金币增加文字
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location.add(0, 0, 0), EntityType.ARMOR_STAND);

        //将 盔甲架 设置为不可见
        armorStand.setVisible(false);
        //设置 盔甲架 的自定义名称
        armorStand.setCustomName(ChatColor.GOLD + "+" + coins + " " + coin);
        //使 盔甲架 的自定义名称可见。
        armorStand.setCustomNameVisible(true);
        //禁用 盔甲架 的重力，这样它就不会掉落到地面上
        armorStand.setGravity(false);

        //在 3 秒后移除 盔甲架
        new BukkitRunnable() {
            @Override
            public void run() {
                armorStand.remove();
            }
        }.runTaskLater(Shop.getShop(), 60L); //60L = 3 秒
    }

    /**
     * 生成[min,max]的随机数金币
     * @return
     */
    private int getCoins(){
        SecureRandom secureRandom = new SecureRandom();
        int coins = secureRandom.nextInt((max - min) + 1) + min;
        return coins;
    }
}
