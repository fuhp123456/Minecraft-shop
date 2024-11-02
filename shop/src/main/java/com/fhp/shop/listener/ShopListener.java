package com.fhp.shop.listener;

import com.fhp.shop.Shop;
import com.fhp.shop.inventory.ShopGUI;
import com.fhp.shop.manager.CoinManager;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

/**
 * 商店事件监听器
 */
public class ShopListener implements Listener {
    //config.yml文件配置
    private static FileConfiguration config;
    //商店物品栏
    private static Inventory panel;
    //每个商品的枚举名
    private static List<String> goodsEnumName;
    //每个商店的价格
    private static List<Integer> prices;
    /**
     * 商店库存点击
     * @param event 库存点击事件信息
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        config = Shop.getShop().getConfig();
        panel = ShopGUI.getPanel();
        goodsEnumName = ShopGUI.getGoodsEnumName();
        prices = ShopGUI.getGoodsPrices();
        // 不是商店界面则返回
        if (!event.getView().getTitle().equals(config.getString("text.shopTitle"))) {
            return;
        }
        // 确保点击的是玩家
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        // 确保点击的是商店物品栏，不能是玩家物品栏
        if (event.getInventory() != panel || event.getRawSlot() >= panel.getSize()) {
            return;
        }

        // 取消默认点击行为
        event.setCancelled(true);

        //获取点击的玩家
        Player player = (Player) event.getWhoClicked();
        //获取点击的商品堆
        ItemStack clickedGoodsItem = event.getCurrentItem();
        //该商品堆操作且是商品
        if (null != clickedGoodsItem && ShopGUI.getGoodsPlace().contains(event.getSlot())) {
            //获取点击的商品类型
            Material goodsType = clickedGoodsItem.getType();
            //获取该商品在数组中的下标
            int goodsEnumIndex = goodsEnumName.indexOf(goodsType.toString());
            //在金币足够时购买商品
            if (CoinManager.removeCoins(player, prices.get(goodsEnumIndex))) {
                player.getInventory().addItem(new ItemStack(goodsType, 1));
            } else {
                //金币不足时提示
                player.sendMessage("§4你的金币不足！需要至少 " + prices.get(goodsEnumIndex) + " 金币。");
            }
        }
    }
}
