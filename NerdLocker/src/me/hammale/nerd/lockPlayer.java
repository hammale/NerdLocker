package me.hammale.lock;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;

public class lockPlayer extends PlayerListener {

	public static lock plugin;
    public lockPlayer(lock instance) {
    	plugin = instance;
    }
	
	public void onPlayerInteract(PlayerInteractEvent e){
		
		ItemStack stack = e.getItem();
		Player p = e.getPlayer();
		
	    if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (stack != null)) {
	  	      if (stack.getTypeId() == 269) {
	  	    	  if(plugin.strike.contains(p.getName())){
	  				Block b = e.getPlayer().getLocation().getBlock();
	  				if(plugin.first == true){
	  					plugin.l1 = b.getLocation();
	  					e.getPlayer().sendMessage(ChatColor.GREEN + "First point set, move to second location!");
	  					plugin.first = false;
	  				}else{
	  					plugin.first = true;
	  					plugin.strike.remove(e.getPlayer().getName());
	  					plugin.l2 = b.getLocation();
	  					e.getPlayer().sendMessage(ChatColor.GREEN + "Second point set! Here comes the lag!");
	  					plugin.startStrike(e.getPlayer());
	  				}
	  	    	  }
	  	      }
	  	    }

	}
	
}
