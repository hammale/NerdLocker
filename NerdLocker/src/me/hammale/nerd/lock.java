package me.hammale.nerd;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class lock extends JavaPlugin {

	public FileConfiguration config;
	
	Logger log = Logger.getLogger("Minecraft");
	
	public boolean first = true;
	
	public Location l1 = null;
	public Location l2 = null;
	
	public HashSet<String> strike = new HashSet<String>();
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("[NerdLocker] " + pdfFile.getVersion() + " Disabled!");
	}

	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();		
		log.info("[NerdLocker] " + pdfFile.getVersion() + " Enabled!");
		
	    loadConfiguration();
	    PluginManager pm = getServer().getPluginManager();
		getServer().getPluginManager().registerEvents(new lockPlayer(this), this);
		getServer().getPluginManager().registerEvents(new lockBlock(this), this);
	}
	
	public void startStrike(final Player p) {
		p.sendMessage(ChatColor.BLUE + "Starting...");
		strike.clear();
	    loopThrough(l1, l2, p.getWorld());
	    p.sendMessage(ChatColor.GREEN + "Complete!");		
	}
 
	public void loadConfiguration(){
	    if(exists() == false){
		    config = getConfig();
		    config.options().copyDefaults(false);   
		    String path1 = "Message";	    
		    config.addDefault(path1, "No can do partner!");
		    config.options().copyDefaults(true);
		    saveConfig();
	    }
	}
	
	public String getMessage(){
	    config = getConfig();
	    String message = config.getString("Message"); 
	    return message;
	}
	
	private boolean exists() {	
			try{
			File file = new File("plugins/NerdLocker/config.yml"); 
	        if (file.exists()) { 
	        	return true;
	        }else{
	        	return false;
	        }

			}catch (Exception e){
			  System.err.println("Error3: " + e.getMessage());
			  return true;
			}
	}
	
	public boolean blockExists() {	
		try{
		File file = new File("plugins/NerdLocker/blocks.lock"); 
        if (file.exists()) { 
        	return true;
        }else{
        	return false;
        }

		}catch (Exception e){
		  System.err.println("Error3: " + e.getMessage());
		  return true;
		}
}
	
	  public boolean onCommand(final CommandSender sender, Command cmd, String commandLabel, String[] args){
			if(cmd.getName().equalsIgnoreCase("nerd")){
				if(sender instanceof Player){
					Player p = (Player) sender;
					p.sendMessage(ChatColor.GOLD + "Please mark points with a wooden shovel");
					strike.add(p.getName());
				}
			}
			return true;	
	  }
	
	public void addBlock(Block b) {
		if(b != null){
			try{
			File file = new File("plugins/NerdLocker/blocks.lock");
	        Scanner scan = null;  
	        String str = null;  
	  
	        if (file.exists()) {
	            scan = new java.util.Scanner(file);  
	            str = scan.nextLine();  
	            while (scan.hasNextLine()) {  
	                str = str.concat("\n" + scan.nextLine());  
	            }  
	        }
	     
			  int x = (int)b.getLocation().getX();
			  int y = (int)b.getLocation().getY();
			  int z = (int)b.getLocation().getZ();
			  
			  str = (x + "," + y + "," + z);
	        
	        PrintWriter out = new PrintWriter(new FileWriter(file, true));  
	  
	        out.println(str);  
	        out.close();
	        scan.close();
			}catch (Exception e){
			 //TODO: solve error not just cover it up :D
			}
		}
	}
	
	public boolean readBlocks(Location l1, World w){	
		try{
			  FileInputStream fstream = new FileInputStream("plugins/NerdLocker/blocks.lock");
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  while ((strLine = br.readLine()) != null){
				  String delims = ",";
				  String[] cords = strLine.split(delims);
	
				  int x = Integer.parseInt(cords[0]);
				  int y = Integer.parseInt(cords[1]);
				  int z = Integer.parseInt(cords[2]);
				  
				  Location l = w.getBlockAt(x, y, z).getLocation();
				  
				  if(l1.getBlock().getX() == l.getBlock().getX() && l1.getBlock().getY() == l.getBlock().getY() && l1.getBlock().getZ() == l.getBlock().getZ()){
					  return false;
				  }
			  }
			  in.close();
			  br.close();
			  fstream.close();
			  return true;
		}catch (Exception e){
			  System.err.println("Error1: " + e.getMessage());
			  }
		return false;		
	}
	
	  public void loopThrough(Location loc1, Location loc2, World w) {
		  
		  File file = new File("plugins/NerdLocker/blocks.lock");
		  if(!(file.exists())){
			  try {
				  FileInputStream fstream = new FileInputStream("plugins/NerdLocker/blocks.lock");
				  fstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		  }
		  
		  int minx = Math.min(loc1.getBlockX(), loc2.getBlockX()),
		  miny = Math.min(loc1.getBlockY(), loc2.getBlockY()),
		  minz = Math.min(loc1.getBlockZ(), loc2.getBlockZ()),
		  maxx = Math.max(loc1.getBlockX(), loc2.getBlockX()),
		  maxy = Math.max(loc1.getBlockY(), loc2.getBlockY()),
		  maxz = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
		  for(int x = minx; x<=maxx;x++){
			  for(int y = miny; y<=maxy;y++){
				  for(int z = minz; z<=maxz;z++)
				  {
					  Block b = w.getBlockAt(x, y, z);
					  if(b.getTypeId() != 0){
						  addBlock(b);
					  }
				  }
			  }
		  }
	  }
}