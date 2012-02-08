package me.hammale.lock;

import org.bukkit.ChatColor;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class lockBlock extends BlockListener{
	
	public static lock plugin;
    public lockBlock(lock instance) {
    	plugin = instance;
    }
	
	public void onBlockPlace(BlockPlaceEvent e){
		if(plugin.blockExists()){
			if(plugin.readBlocks(e.getBlock().getLocation(), e.getBlock().getWorld()) == false && e.getPlayer().isOp() == false){
				e.getPlayer().sendMessage(ChatColor.RED + plugin.getMessage());
				e.setCancelled(true);
			}
		}
	}
	
	public void onBlockBreak(BlockBreakEvent e){
		if(plugin.blockExists()){
			if(plugin.readBlocks(e.getBlock().getLocation(), e.getBlock().getWorld()) == false && e.getPlayer().isOp() == false){
				e.getPlayer().sendMessage(ChatColor.RED + plugin.getMessage());
				e.setCancelled(true);
			}
		}
	}
	
}