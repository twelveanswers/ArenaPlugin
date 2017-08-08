package com.github.escapemc.arenaplugin;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.escapemc.arenaplugin.bases.Team;
import com.github.escapemc.arenaplugin.commands.CommandTeam;
import com.github.escapemc.arenaplugin.teams.BlueTeam;
import com.github.escapemc.arenaplugin.teams.RedTeam;

public class Main extends JavaPlugin {

	PluginDescriptionFile pdfFile = getDescription();
	Logger logger = getLogger();
	
	public void onEnable() {
		
		logger.info("Enabling " + pdfFile.getName() + " Version - " + pdfFile.getVersion() + " Made By " + pdfFile.getAuthors());
		registerCommands();
		Team.clearTeams();
		
	}
	
	public void onDisable() {
		
		logger.info("Disabling " + pdfFile.getName() + " Version - " + pdfFile.getVersion() + " Made By " + pdfFile.getAuthors());
		Team.clearTeams();
		
	}
	
	private void registerCommands() {
		
		getCommand("arena_team").setExecutor(new CommandTeam());
		
	}
		
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("arena_setspawn")) {

			Player p = (Player) sender;
			
			if (args.length == 0) {
				
				p.sendMessage("You forgot to specify a team to set the spawn for. Try /arena_setspawn [Team Name]");
					
			}else if (args[0].equalsIgnoreCase("blue")) {
				
				BlueTeam.setBlueSpawn(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p);
				getConfig().set("spawn.blue.world", p.getLocation().getWorld().getName());
				getConfig().set("spawn.blue.x", p.getLocation().getX());
				getConfig().set("spawn.blue.y", p.getLocation().getY());
				getConfig().set("spawn.blue.z", p.getLocation().getZ());
				getConfig().set("spawn.blue.pitch", p.getLocation().getPitch());
				getConfig().set("spawn.blue.yaw", p.getLocation().getYaw());
				saveConfig();
				p.sendMessage(ChatColor.DARK_PURPLE + "(Pst, also saved Blue Spawn to config file!)");
				
			}else if(args[0].equalsIgnoreCase("red")) {
				
				RedTeam.setRedSpawn(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p);
				getConfig().set("spawn.red.world", p.getLocation().getWorld().getName());
				getConfig().set("spawn.red.x", p.getLocation().getX());
				getConfig().set("spawn.red.y", p.getLocation().getY());
				getConfig().set("spawn.red.z", p.getLocation().getZ());
				getConfig().set("spawn.red.pitch", p.getLocation().getPitch());
				getConfig().set("spawn.red.yaw", p.getLocation().getYaw());
				saveConfig();
				p.sendMessage(ChatColor.DARK_PURPLE + "(Pst, also saved Red Spawn to config file!)");
				
			}
			
		}
		
		if(cmd.getName().equalsIgnoreCase("arena_start")) {
			
			if(getConfig().getConfigurationSection("spawn.red") == null && getConfig().getConfigurationSection("spawn.blue") == null) {
				
				sender.sendMessage(ChatColor.AQUA + "Spawns for Red and Blue not set!!");
				return true;
			
			}else if(getConfig().getConfigurationSection("spawn.red") == null) {
				
				sender.sendMessage(ChatColor.AQUA + "Spawn for Red not Saved");
				return true;
				
			}else if(getConfig().getConfigurationSection("spawn.blue") == null) {
				
				sender.sendMessage(ChatColor.AQUA + "Spawn for Blue not Saved");
				return true;
				
				
			}else{
				
				World blueWorld = Bukkit.getServer().getWorld(getConfig().getString("spawn.blue.world"));
				double blueX = getConfig().getDouble("spawn.blue.x");
				double blueY = getConfig().getDouble("spawn.blue.y");
				double blueZ = getConfig().getDouble("spawn.blue.z");
				double bluePitch = getConfig().getDouble("spawn.blue.pitch");
				double blueYaw = getConfig().getDouble("spawn.blue.yaw");

				World redWorld = Bukkit.getServer().getWorld(getConfig().getString("spawn.red.world"));
				double redX = getConfig().getDouble("spawn.red.x");
				double redY = getConfig().getDouble("spawn.red.y");
				double redZ = getConfig().getDouble("spawn.red.z");
				double redPitch = getConfig().getDouble("spawn.red.pitch");
				double redYaw = getConfig().getDouble("spawn.red.yaw");

				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					
					if(Team.redTeam.contains(p.getName())) {
						
						p.teleport(new Location(redWorld, redX, redY, redZ));
						p.sendMessage(ChatColor.RED + "Teleported to Red Spawn as the match has started!");
						
					}else if(Team.blueTeam.contains(p.getName())) {
						
						p.teleport(new Location(blueWorld, blueX, blueY, blueZ));
						p.sendMessage(ChatColor.BLUE + "Teleported to Blue Spawn as the match has started!");
						
					}else{
						
						p.sendMessage(ChatColor.AQUA + "A fight in an arena has begun!");
						
					}
					
				}
				
			}
			
		}
		
		return false;
	}

}