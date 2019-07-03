package fr.Dianox.Hawn.Utility.Config.Messages.Adminstration;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class InfoServerOverviewC {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public InfoServerOverviewC() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/Administration/Server-Info.yml");
		Config = YamlConfiguration.loadConfiguration(file);
		
		if (!pl.getDataFolder().exists()) {
			pl.getDataFolder().mkdir();
		}
		
		create();
	}
	
    public static File getFile() {
        return file;
    }

    public static YamlConfiguration getConfig() {
        return Config;
    }

    public static void reloadConfig() {
        loadConfig(pl);
    }

    public static void saveConfigFile() {
        try {
            Config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void create() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {}
                        
            /* ----------------- *
			 * COMMANDS COMMANDS *
			 * ----------------- */
            Config.set("Command.Server-Info.General", java.util.Arrays.asList(new String[] {
            		"&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
            		"",
            		"     &l>> &e&o&lServer information (All)",
            		"        &3created by Dianox",
            		"",
            		"  &8→ &6&lServer",
            		"&7&lTps&r&8:&r %tps%",
            		"&7&lJava version&r&8:&r %javaversion%",
            		"&7&lOS&r&8:&r %osversion%",
            		"&7&lHawn check update&r&8:&r %checkupdatehawn%",
            		"&7&lServer version&r&8:&r %serverversion%",
            		"",
            		"  &8→ &6&lMemory",
            		"&7&lMemory max &o(java)&r&8:&r %maxmemory%&8MB",
            		"&a&lFree&7&l/&c&lTotal&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
            		"&8Bar-[%barmemory%&8]",
            		"",
            		"  &8→ &6&lCPU (Processor)",
            		"&7&lAverage load&8:&r %averagecpuload%&8%",
            		"&7&lActual load&8:&r %cpuload%&8%",
            		"&8Bar-[%barcpu%&8]",
            		"",
            		"  &8→ &6&lDisk space",
            		"&7&lTotal disk&r&8:&r %totalspace%&8MB",
            		"&a&lFree disk&7&l/&c&lTotal disk&7&l usable&r&8:&r %freespace%&8MB&7/&7%totalspaceusable%&8MB",
            		"&8Bar-[%bardisk%&8]",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"
            		}));
            
            Config.set("Command.Server-Info.Memory", java.util.Arrays.asList(new String[] {
            		"&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
            		"",
            		"     &l>> &e&o&lServer information (Memory)",
            		"",
            		"  &8→ &6&lMemory",
            		"&7&lMemory max &o(java)&r&8:&r %maxmemory%&8MB",
            		"&a&lFree&7&l/&c&lTotal&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
            		"&8Bar-[%barmemory%&8]",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"
            		}));
            
            Config.set("Command.Server-Info.CPU", java.util.Arrays.asList(new String[] {
            		"&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
            		"",
            		"     &l>> &e&o&lServer information (CPU)",
            		"",
            		"  &8→ &6&lCPU (Processor)",
            		"&7&lAverage load&8:&r %averagecpuload%&8%",
            		"&7&lActual load&8:&r %cpuload%&8%",
            		"&8Bar-[%barcpu%&8]",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"
            		}));
            
            Config.set("Command.Server-Info.Disk", java.util.Arrays.asList(new String[] {
            		"&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
            		"",
            		"     &l>> &e&o&lServer information (Disk)",
            		"",
            		"  &8→ &6&lDisk space",
            		"&7&lTotal disk&r&8:&r %totalspace%&8MB",
            		"&a&lFree disk&7&l/&c&lTotal disk&7&l usable&r&8:&r %freespace%&8MB&7/&7%totalspaceusable%&8MB",
            		"&8Bar-[%bardisk%&8]",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"
    				}));
            
            Config.set("Command.Server-Info.Server", java.util.Arrays.asList(new String[] {
            		"&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
            		"",
            		"     &l>> &e&o&lServer information (Server)",
            		"",
            		"  &8→ &6&lServer",
            		"&7&lTps&r&8:&r %tps%",
            		"&7&lJava version&r&8:&r %javaversion%",
            		"&7&lOS&r&8:&r %osversion%",
            		"&7&lHawn check update&r&8:&r %checkupdatehawn%",
            		"&7&lServer version&r&8:&r %serverversion%",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"
    				}));
            
            Config.set("Command.Server-Info.Tps", java.util.Arrays.asList(new String[] {
            		"  &8→ &6&lTps&8: &r%tps%"
    				}));
            
            Config.set("Command.Version", java.util.Arrays.asList(new String[] {
            		"  &8→ &6&lHawn version (created by Dianox)&8: &r%gethawnversion%"
    				}));
            
            Config.set("TPS.Check.15", java.util.Arrays.asList(new String[] {"&cYour TPS is under 15, done something to improve the stability of your Lobby"}));
            Config.set("TPS.Check.5", java.util.Arrays.asList(new String[] {"&cYour TPS is under 5, your server may shut down, done /stop to avoid any problems.", "&cCRITICAL SERVER CRITICAL STATE ATTENTION"}));
            
            saveConfigFile();

        }
    }
}
