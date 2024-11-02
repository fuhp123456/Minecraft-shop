package com.fhp.shop.inventory;

import com.fhp.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * 商店面板生成器
 */
public class ShopGUI {
    //商店物品栏
    private static Inventory panel;
    //config.yml文件配置
    private static FileConfiguration config;
    //商店物品枚举名
    private static List<String> goodsEnumName;
    //商品在商店价格
    private static List<Integer> goodsPrices;
    //商品在商店下标
    private static List<Integer> goodsPlace;
    //装饰品在商店下标
    private static List<Integer> fixedTrim;
    //货币名称
    private static String coin;
    public static Inventory createShopInventory(){
        config = Shop.getShop().getConfig();
        //设置商店物品栏
        panel = Bukkit.createInventory(null, 54, config.getString("text.shopTitle"));
        //获取所有商品类型
        goodsEnumName = config.getStringList("shop.goods");
        //获取所有商品价格
        goodsPrices = config.getIntegerList("shop.goodsPrices");
        //获取所有商品在商店的下标
        goodsPlace = config.getIntegerList("shop.goodsPlace");
        //获取装饰品在商店的下标
        fixedTrim = config.getIntegerList("shop.trimPlace");
        //货币名称
        coin = config.getString("text.coin");

        // 使用末地水晶装饰商店界面
        ItemStack itemStack = new ItemStack(Material.END_CRYSTAL);
        ItemMeta itemMeta = itemStack.getItemMeta();

        //设置末地水晶装饰的名字和内容
        itemMeta.setDisplayName(config.getString("text.trimName"));
        itemMeta.setLore(config.getStringList("text.trimContent"));
        itemStack.setItemMeta(itemMeta);
        //放置末地水晶装饰
        for (int index : fixedTrim) {
            //使用 clone() 避免后续修改影响装饰品
            panel.setItem(index,itemStack.clone());
        }

        //添加商品到商店界面
        for (int i = 0; i < goodsPlace.size(); i++) {
            //设置商品物品堆
            itemStack = new ItemStack(Material.getMaterial(goodsEnumName.get(i)));
            //获取商品元数据
            itemMeta = itemStack.getItemMeta();
            //获取商品的信息内容
            List<String> lore = itemMeta.getLore() == null ? new ArrayList<>() : new ArrayList<>(itemMeta.getLore());
            //添加商品的价格信息
            lore.add("§6价格: §c" + goodsPrices.get(i) + "§6 " + coin);
            //将添加了商品价格的信息设置给元数据
            itemMeta.setLore(lore);
            //将元数据设置给商品
            itemStack.setItemMeta(itemMeta);
            //根据商品下标设置商品
            panel.setItem(goodsPlace.get(i),itemStack);
        }
        return panel;
    }

    public static Inventory getPanel() {
        return panel;
    }

    public static List<String> getGoodsEnumName() {
        return goodsEnumName;
    }

    public static List<Integer> getGoodsPlace() {
        return goodsPlace;
    }

    public static List<Integer> getGoodsPrices() {
        return goodsPrices;
    }
}
