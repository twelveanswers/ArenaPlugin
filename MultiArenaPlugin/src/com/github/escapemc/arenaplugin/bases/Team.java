package com.github.escapemc.arenaplugin.bases;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Team {

	public static String name;
	public static double spawnX;
	public static double spawnY;
	public static double spawnZ;
	
	public static int num;
	public static Location teleLoc;
		
	public static ArrayList<String> redTeam = new ArrayList<String>();
	public static ArrayList<String> blueTeam = new ArrayList<String>();
	
	public static String onlinePlayers;
		
	public static boolean isInTeam(String player) {
		
		return redTeam.contains(player) || blueTeam.contains(player);
	
	}
	
	public static void addToTeam(TeamType type, Player player, String p) {
		
		if(isInTeam(p)) {
			
			player.sendMessage(p + " is already on a team!");
			return;
			
		}else if(!isInTeam(p)){
		
			switch(type) {
		
			case RED:
			
				redTeam.add(p);
				
				player.sendMessage(ChatColor.RED + p + " was assigned to Red Team.");
				break;
		
			case BLUE:
			
				blueTeam.add(p);
				player.sendMessage(ChatColor.BLUE + p + " was assigned to Blue Team.");
				break;
		
			}
			
		}else{
			
			player.sendMessage(ChatColor.DARK_RED + "Something broke.");
			
		}
		
	}
	
	public static void removeFromTeam(TeamType type, Player player, String p) {
		
		if(!isInTeam(p)) {
			
			player.sendMessage(p + " is not on a team!");
			return;
			
		}else if(isInTeam(p)){
		
			switch(type) {
		
				case RED:
			
					redTeam.remove(p);
					player.sendMessage(ChatColor.RED + p + " was removed from Red Team.");
					break;
		
				case BLUE:
			
					blueTeam.remove(p);
					player.sendMessage(ChatColor.BLUE + p + " was removed from Blue Team.");
					break;
		
			}
			
		}else{
			
			player.sendMessage(ChatColor.DARK_RED + "Something broke.");
			
		}
		
	}
	
	public static void clearTeams() {
		
		redTeam.clear();
		blueTeam.clear();
		
	}
	
	public static ArrayList<String> getRedTeam() {
		
		return redTeam;
		
	}

	public static ArrayList<String> getBlueTeam() {
		
		return blueTeam;
		
	}
	
	public static ArrayList<String> getAllPlayersInTeams() {
		
		ArrayList<String> combinedTeams = new ArrayList<String>();
		combinedTeams.addAll(redTeam);
		combinedTeams.addAll(blueTeam);
		return combinedTeams;
		
	}
	
}
