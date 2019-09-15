package fr.dianox.hawn.utility;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.dianox.hawn.Main;

public class ConfigPlayerGet {
	
	private static YamlConfiguration cfg;
	
	public static YamlConfiguration getFile(String file) {
        
		File f = new File(Main.getInstance().getDataFolder(), "StockageInfo/YamlPlayer/" + file + ".yml");
		
		if (!f.exists()) {
			createNewFile(file);
		}
		
		cfg = YamlConfiguration.loadConfiguration(f);
		
		return cfg;
	}
	
	public static YamlConfiguration writeString(String file, String link, String string) {
        
		File f = new File(Main.getInstance().getDataFolder(), "StockageInfo/YamlPlayer/" + file + ".yml");
		
		if (!f.exists()) {
			createNewFile(file);
		}
		
		cfg = YamlConfiguration.loadConfiguration(f);
		
		cfg.set(link, string);
		
		try {
            cfg.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return cfg;
	}
	
	public static YamlConfiguration writeInt(String file, String link, Integer i) {
        
		File f = new File(Main.getInstance().getDataFolder(), "StockageInfo/YamlPlayer/" + file + ".yml");
		
		if (!f.exists()) {
			createNewFile(file);
		}
		
		cfg = YamlConfiguration.loadConfiguration(f);
		
		cfg.set(link, i);
		
		try {
            cfg.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return cfg;
	}
	
	public static YamlConfiguration writeDouble(String file, String link, Double i) {
        
		File f = new File(Main.getInstance().getDataFolder(), "StockageInfo/YamlPlayer/" + file + ".yml");
		
		if (!f.exists()) {
			createNewFile(file);
		}
		
		cfg = YamlConfiguration.loadConfiguration(f);
		
		cfg.set(link, i);
		
		try {
            cfg.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return cfg;
	}
	
	public static YamlConfiguration writeLong(String file, String link, Long i) {
        
		File f = new File(Main.getInstance().getDataFolder(), "StockageInfo/YamlPlayer/" + file + ".yml");
		
		if (!f.exists()) {
			createNewFile(file);
		}
		
		cfg = YamlConfiguration.loadConfiguration(f);
		
		cfg.set(link, i);
		
		try {
            cfg.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return cfg;
	}
	
	public static YamlConfiguration writeFloat(String file, String link, Float i) {
        
		File f = new File(Main.getInstance().getDataFolder(), "StockageInfo/YamlPlayer/" + file + ".yml");
		
		if (!f.exists()) {
			createNewFile(file);
		}
		
		cfg = YamlConfiguration.loadConfiguration(f);
		
		cfg.set(link, i);
		
		try {
            cfg.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return cfg;
	}
	
	public static YamlConfiguration writeBoolean(String file, String link, Boolean b) {
        
		File f = new File(Main.getInstance().getDataFolder(), "StockageInfo/YamlPlayer/" + file + ".yml");
		
		if (!f.exists()) {
			createNewFile(file);
		}
		
		cfg = YamlConfiguration.loadConfiguration(f);
		
		cfg.set(link, b);
		
		try {
            cfg.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return cfg;
	}
	
	public static YamlConfiguration writeList(String file, String link, List<String> asList) {
		File f = new File(Main.getInstance().getDataFolder(), "StockageInfo/YamlPlayer/" + file + ".yml");
		
		if (!f.exists()) {
			createNewFile(file);
		}
		
		cfg = YamlConfiguration.loadConfiguration(f);
		
		cfg.set(link, asList);
		
		try {
            cfg.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return cfg;
		
	}
	
	private static void createNewFile(String file) {
		File f = new File(Main.getInstance().getDataFolder(), "StockageInfo/YamlPlayer/" + file + ".yml");
		
		File folder = new File(Main.getInstance().getDataFolder(), "StockageInfo/YamlPlayer/");
		
		if (!Main.getInstance().getDataFolder().exists()) {
			Main.getInstance().getDataFolder().mkdir();
		}
		
		if (f.exists()) {
			Bukkit.getConsoleSender().sendMessage("The file "+ file +" exists");
		} else {
			try {
				if (!folder.exists()) {
					folder.mkdirs();
				}
                f.createNewFile();
            } catch (IOException e) {
            	Bukkit.getConsoleSender().sendMessage(f.getAbsolutePath());
            	e.printStackTrace();
            }
		}
	}

}