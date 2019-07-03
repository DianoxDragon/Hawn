package fr.Dianox.Hawn.Utility.World;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import fr.Dianox.Hawn.Utility.Config.Events.ConfigGJoinQuitCommand;

public class CommandsPW {
	
	public static List<String> worlds_JoinCommands_Player_New = new ArrayList<String>();
	public static List<String> worlds_JoinCommands_Player_No_New = new ArrayList<String>();
	public static List<String> worlds_JoinCommands_Console_New = new ArrayList<String>();
	public static List<String> worlds_JoinCommands_Console_No_New = new ArrayList<String>();
	public static List<String> worlds_QuitCommands_Console = new ArrayList<String>();
	
	public static void setWGetWorldJoinCommandPlayerNew() {
		if (ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.New.JoinCommand-Player.Enable")) {
	        if (!ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.New.JoinCommand-Player.World.All_World")) {
	            for (String worldHunger : ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.New.JoinCommand-Player.World.Worlds")) {
	            	if (Bukkit.getWorld(worldHunger) == null) {
	            		System.out.println("| Invalid world in Events/JoinQuitCommand.yml, JoinCommand.Options.New.JoinCommand-Player.World: "+worldHunger);
	            	} else {
	            		worlds_JoinCommands_Player_New.add(worldHunger);
	            	}
	            }
	        }
        }
	}
	
	public static void setWGetWorldJoinCommandPlayerNoNew() {
		if (ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.No-New.JoinCommand-Player.Enable")) {
	        if (!ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.No-New.JoinCommand-Player.World.All_World")) {
	            for (String worldHunger : ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.No-New.JoinCommand-Player.World.Worlds")) {
	            	if (Bukkit.getWorld(worldHunger) == null) {
	            		System.out.println("| Invalid world in Events/JoinQuitCommand.yml, JoinCommand.Options.No-New.JoinCommand-Player.World: "+worldHunger);
	            	} else {
	            		worlds_JoinCommands_Player_No_New.add(worldHunger);
	            	}
	            }
	        }
        }
	}
	
	public static void setWGetWorldJoinCommandConsoleNew() {
		if (ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.New.JoinCommand-Console.Enable")) {
	        if (!ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.New.JoinCommand-Console.World.All_World")) {
	            for (String worldHunger : ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.New.JoinCommand-Console.World.Worlds")) {
	            	if (Bukkit.getWorld(worldHunger) == null) {
	            		System.out.println("| Invalid world in Events/JoinQuitCommand.yml, JoinCommand.Options.New.JoinCommand-Console.World: "+worldHunger);
	            	} else {
	            		worlds_JoinCommands_Console_New.add(worldHunger);
	            	}
	            }
	        }
        }
	}
	
	public static void setWGetWorldJoinCommandConsoleNoNew() {
		if (ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.No-New.JoinCommand-Console.Enable")) {
	        if (!ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.No-New.JoinCommand-Console.World.All_World")) {
	            for (String worldHunger : ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.No-New.JoinCommand-Console.World.Worlds")) {
	            	if (Bukkit.getWorld(worldHunger) == null) {
	            		System.out.println("| Invalid world in Events/JoinQuitCommand.yml, JoinCommand.Options.No-New.JoinCommand-Console.World: "+worldHunger);
	            	} else {
	            		worlds_JoinCommands_Console_No_New.add(worldHunger);
	            	}
	            }
	        }
        }
	}
	
	public static void setWGetWorldQuitCommandConsole() {
		if (ConfigGJoinQuitCommand.getConfig().getBoolean("QuitCommand.Enable")) {
	        if (!ConfigGJoinQuitCommand.getConfig().getBoolean("QuitCommand.QuitCommand-Console.World.All_World")) {
	            for (String worldHunger : ConfigGJoinQuitCommand.getConfig().getStringList("QuitCommand.QuitCommand-Console.World.Worlds")) {
	            	if (Bukkit.getWorld(worldHunger) == null) {
	            		System.out.println("| Invalid world in Events/JoinQuitCommand.yml, QuitCommand.QuitCommand-Console.World: "+worldHunger);
	            	} else {
	            		worlds_QuitCommands_Console.add(worldHunger);
	            	}
	            }
	        }
        }
	}
	
	public static List<String> getWConsoleJoinCommandNew() {
		return worlds_JoinCommands_Console_New;
	}
	
	public static List<String> getWConsoleJoinCommandNoNew() {
		return worlds_JoinCommands_Console_No_New;
	}
	
	public static List<String> getWPlayerJoinCommandNew() {
		return worlds_JoinCommands_Player_New;
	}
	
	public static List<String> getWPlayerJoinCommandNoNew() {
		return worlds_JoinCommands_Player_No_New;
	}
	
	public static List<String> getWConsoleQuitCommand() {
		return worlds_QuitCommands_Console;
	}

}
