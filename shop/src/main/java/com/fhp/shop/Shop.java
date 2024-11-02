package com.fhp.shop;

import com.fhp.shop.listener.KillListener;
import com.fhp.shop.listener.LoginListener;
import com.fhp.shop.listener.ShopListener;
import com.fhp.shop.command.Command;
import com.fhp.shop.inventory.ShopGUI;
import com.fhp.shop.manager.CoinManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Shop extends JavaPlugin {

    //供其他类调用主类
    private static Shop shop;

    //控制台输出日志
    private static CommandSender sender;

    //config.yml文件配置
    private static FileConfiguration config;

    /**
     * 初始化
     */
    private void pluginInit(){
        shop = this;
        sender = Bukkit.getConsoleSender();
        config = this.getConfig();

        //保存默认配置文件
        this.saveDefaultConfig();

        //注册指令
        Bukkit.getPluginCommand("shopping").setExecutor(new Command());

        //注册事件监听
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new LoginListener(), this);
        pm.registerEvents(new ShopListener(), this);
        pm.registerEvents(new KillListener(), this);

        //初始化商店面板
        ShopGUI.createShopInventory();
        //初始化金币管理器
        CoinManager.initialize(this);
    }

    @Override
    public void onEnable() {
        //初始化
        pluginInit();
        //控制台打印欢迎文字
        sender.sendMessage(config.getString("text.pluginStart"));
    }

    @Override
    public void onDisable() {
        //控制台打印离开文字
        sender.sendMessage(config.getString("text.pluginStop"));
    }

    public static Shop getShop() {
        return shop;
    }

    public static CommandSender getSender() {
        return sender;
    }
}
