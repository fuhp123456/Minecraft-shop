package com.fhp.shop.manager;

import com.fhp.shop.Shop;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 数据持久化
 */
public class ConfigManager {
    //金币数据配置文件
    private final File configFile;
    //配置文件
    private final FileConfiguration config;

    /**
     * 创建配置文件
     * @param plugin 插件
     */
    public ConfigManager(JavaPlugin plugin) {
        //通过存放插件文件数据的文件夹+子路径构建文件对象
        configFile = new File(plugin.getDataFolder(), "coins.yml");
        //如果该文件不存在
        if (!configFile.exists()) {
            try {
                //创建新文件
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //从给定文件加载，创建新的YamlConfiguration
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * 根据玩家ID获取金币，如果找不到，则设置为默认值
     * @param playerId 玩家ID
     * @return
     */
    public int getCoins(UUID playerId) {
        return config.getInt(playerId.toString(), config.getInt("Money"));
    }

    /**
     * 根据玩家ID设置金币数量
     * @param playerId 玩家ID
     * @param amount 金币数量
     */
    public void setCoins(UUID playerId, int amount) {
        config.set(playerId.toString(), amount);
        saveConfig();
    }

    //保存金币到配置文件
    private void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
