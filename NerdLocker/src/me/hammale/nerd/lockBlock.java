package me.hammale.nerd;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class lockBlock implements Listener{
	
	public static lock plugin;
    public lockBlock(lock instance) {
    	plugin = instance;
    }
    @EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		if(plugin.blockExists()){
			if(plugin.readBlocks(e.getBlock().getLocation(), e.getBlock().getWorld()) == false && e.getPlayer().isOp() == false){
				e.getPlayer().sendMessage(ChatColor.RED + plugin.getMessage());
				e.setCancelled(true);
			}
		}
	}
    @EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		if(plugin.blockExists()){
			if(plugin.readBlocks(e.getBlock().getLocation(), e.getBlock().getWorld()) == false && e.getPlayer().isOp() == false){
				e.getPlayer().sendMessage(ChatColor.RED + plugin.getMessage());
				e.setCancelled(true);
			}
		}
	}
	
}