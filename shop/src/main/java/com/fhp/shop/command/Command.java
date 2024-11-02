package com.fhp.shop.command;

import com.fhp.shop.inventory.ShopGUI;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 指令控制器
 */
public class Command implements CommandExecutor {
    //执行指令的玩家
    private Player player;
    /**
     *
     * @param commandSender 执行命令的玩家
     * @param command 执行的命令
     * @param s 被执行命令的别名
     * @param strings 指令集合
     * @return
     */
    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        //如果指令发送者是玩家
        if(commandSender instanceof Player){
            //获取玩家
            player = (Player)commandSender;
            if (s.equalsIgnoreCase("shopping") || s.equalsIgnoreCase("shop:shopping")){
                //打开贸易界面
                player.openInventory(ShopGUI.getPanel());
            }
            return true;
        }
        return true;
    }
}
