package fr.Dianox.Hawn.Utility.Config.Messages.fr_FR;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class AdminInfoServerOverviewC {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public AdminInfoServerOverviewC() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/fr_FR/Administration/Server-Info.yml");
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
            		"     &l>> &e&o&lInformations sur le serveur (Tous)",
            		"        &3créé par Dianox",
            		"",
            		"  &8→ &6&lServeur",
            		"&7&lTps&r&8:&r %tps%",
            		"&7&lVersion de java&r&8:&r %javaversion%",
            		"&7&lOS&r&8:&r %osversion%",
            		"&7&lVérification des mises à jour de Hawn&r&8:&r %checkupdatehawn%",
            		"&7&lVersion du serveur&r&8:&r %serverversion%",
            		"",
            		"  &8→ &6&lMémoire",
            		"&7&lMémoire max &o(java)&r&8:&r %maxmemory%&8MB",
            		"&a&lLibre&7&l/&c&lTotal&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
            		"&8Bar-[%barmemory%&8]",
            		"",
            		"  &8→ &6&lCPU (Processeur)",
            		"&7&lCharge moyenne&8:&r %averagecpuload%&8%",
            		"&7&lCharge réelle&8:&r %cpuload%&8%",
            		"&8Bar-[%barcpu%&8]",
            		"",
            		"  &8→ &6&lEspace disque",
            		"&7&lDisque total&r&8:&r %totalspace%&8MB",
            		"&a&lDisque libre&7&l/&c&lDisque total&7&l utilisable&r&8:&r %freespace%&8MB&7/&7%totalspaceusable%&8MB",
            		"&8Bar-[%bardisk%&8]",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"
            		}));
            
            Config.set("Command.Server-Info.Memory", java.util.Arrays.asList(new String[] {
            		"&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
            		"",
            		"     &l>> &e&o&lInformations sur le serveur (Mémoire)",
            		"",
            		"  &8→ &6&lMémoire",
            		"&7&lMémoire max &o(java)&r&8:&r %maxmemory%&8MB",
            		"&a&lLibre&7&l/&c&lTotal&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
            		"&8Bar-[%barmemory%&8]",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"
            		}));
            
            Config.set("Command.Server-Info.CPU", java.util.Arrays.asList(new String[] {
            		"&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
            		"",
            		"     &l>> &e&o&lInformations sur le serveur (CPU)",
            		"",
            		"  &8→ &6&lCPU (Processeur)",
            		"&7&lCharge moyenne&8:&r %averagecpuload%&8%",
            		"&7&lCharge réelle&8:&r %cpuload%&8%",
            		"&8Bar-[%barcpu%&8]",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"
            		}));
            
            Config.set("Command.Server-Info.Disk", java.util.Arrays.asList(new String[] {
            		"&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
            		"",
            		"     &l>> &e&o&lInformations sur le serveur (Disque)",
            		"",
            		"  &8→ &6&lEspace disque",
            		"&7&lDisque total&r&8:&r %totalspace%&8MB",
            		"&a&lDisque libre&7&l/&c&lDisque total&7&l utilisable&r&8:&r %freespace%&8MB&7/&7%totalspaceusable%&8MB",
            		"&8Bar-[%bardisk%&8]",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"
    				}));
            
            Config.set("Command.Server-Info.Server", java.util.Arrays.asList(new String[] {
            		"&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
            		"",
            		"     &l>> &e&o&lInformations sur le serveur (Serveur)",
            		"",
            		"  &8→ &6&lServeur",
            		"&7&lTps&r&8:&r %tps%",
            		"&7&lVersion de java&r&8:&r %javaversion%",
            		"&7&lOS&r&8:&r %osversion%",
            		"&7&lVérification des mises à jour de Hawn&r&8:&r %checkupdatehawn%",
            		"&7&lVersion du serveur&r&8:&r %serverversion%",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"
    				}));
            
            Config.set("Command.Server-Info.Tps", java.util.Arrays.asList(new String[] {
            		"  &8→ &6&lTps&8: &r%tps%"
    				}));
            
            Config.set("Command.Version", java.util.Arrays.asList(new String[] {
            		"  &8→ &6&lVersion de Hawn (créé par Dianox)&8: &r%gethawnversion%"
    				}));
            
            Config.set("TPS.Check.15", java.util.Arrays.asList(new String[] {"&cVos TPS sont à moins de 15, faites quelque chose pour améliorer la stabilité de votre Lobby"}));
            Config.set("TPS.Check.5", java.util.Arrays.asList(new String[] {"&cVos TPS sont à moins de 5, votre serveur peut s'éteindre, fait /stop pour éviter tout problème.", "&cETAT CRITIQUE DU SERVEUR"}));
            
            saveConfigFile();

        }
    }
}
