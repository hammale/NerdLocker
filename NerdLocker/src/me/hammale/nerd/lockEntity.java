package me.hammale.nerd;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class lockEntity implements Listener {

	public static lock plugin;
    public lockEntity(lock instance) {
    	plugin = instance;
    }
	
	@EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        if (e.isCancelled()) return;
        List<Block> blockListCopy = new ArrayList<Block>();
        blockListCopy.addAll(e.blockList());
        for (Block b : blockListCopy) {
        	if(plugin.readBlocks(b.getLocation(), b.getWorld()) == false){
            	e.blockList().remove(b);
        	}	
        }
	}    
}
