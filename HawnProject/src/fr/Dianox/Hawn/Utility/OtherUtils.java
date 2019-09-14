package fr.Dianox.Hawn.Utility;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.Config.ConfigGeneral;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.FireworkListCUtility;

public class OtherUtils {
	
	static double used_memory = 0;
	
	// Stocke les variables
	public static String MemoryUsageBar = "";
	public static String CpuUsageBar = "";
	public static String DiskUsageBar = "";
	
	// Variable memories
	public static String maxmemoryv = "";
	public static String freememoryv = "";
	
	public static String totalspaceusablev = "";
	public static String freespacev = "";
	
	// Lifetime variable
	public static String totalmemoryv = "";
	public static String totaldiskv = "";
	public static String ossystem = "";
	public static String javaver = "";
	
	static OperatingSystemMXBean osBean;
	static double NaND;
	static MBeanServer mbs;
	static File f;
	
	public static int getJavaVersion(){
        String javaSpecVersion = System.getProperty( "java.specification.version" );
        if (javaSpecVersion.contains(".")) {//before jdk 9
            return Integer.parseInt(javaSpecVersion.split("\\.")[1]);
        } else {
            return Integer.parseInt(javaSpecVersion);
        }
	}
	
	public static String getOperatingSystem() {
	    String os = System.getProperty("os.name");
	    
	    ossystem = String.valueOf(os);
	    
	    return os;
	}
	
	public static long freeMemory() {
		
		freememoryv = String.valueOf(OtherUtils.BytesToMegaBytes(Runtime.getRuntime().freeMemory()));
		
		return Runtime.getRuntime().freeMemory();
	}
	
	public static long maxMemory() {
		
		maxmemoryv = String.valueOf(OtherUtils.BytesToMegaBytes(Runtime.getRuntime().maxMemory()));
		
		return Runtime.getRuntime().maxMemory();
	}
	
	public static long totalMemory() {
		
		totalmemoryv = String.valueOf(OtherUtils.BytesToMegaBytes(Runtime.getRuntime().totalMemory()));
		
		return Runtime.getRuntime().totalMemory();
	}
	
	public static double getMemoryUsage() {
	    used_memory = totalMemory() - freeMemory();
	    double percent = (totalMemory() / 100L);
	    return used_memory / percent;
	}
	
	public static String getMemoryUsageBar() {
		MemoryUsageBar = "";
	    double round_usage = getMemoryUsage() * 0.7D;
	    int usage = (int)Math.ceil(round_usage);
	    for (int i = 0; i < 40; i++) {
	    	if (i < usage) {
	    		MemoryUsageBar = String.valueOf(MemoryUsageBar) + "§a▬";
			} else if (usage == i) {
				MemoryUsageBar = String.valueOf(MemoryUsageBar) + "§6▬";
			} else {
				MemoryUsageBar = String.valueOf(MemoryUsageBar) + "§c▬";
			} 
	    }
	    return MemoryUsageBar;
	}
	
	public static double LoadAverange() {
	    osBean = ManagementFactory.getOperatingSystemMXBean();
	    return osBean.getSystemLoadAverage();
	}
	
	public static double getProcessCpuLoad() {
	    mbs = ManagementFactory.getPlatformMBeanServer();
	    ObjectName name;
	    AttributeList list = null;
		try {
			name = ObjectName.getInstance("java.lang:type=OperatingSystem");
			list = mbs.getAttributes(name, new String[] { "ProcessCpuLoad" });
		} catch (MalformedObjectNameException | NullPointerException | InstanceNotFoundException | ReflectionException e) {
			e.printStackTrace();
		}
	    
		if (list.isEmpty()) return NaND;
	    
	    Attribute att = (Attribute)list.get(0);
	    Double value = (Double)att.getValue();

	    
	    if (value.doubleValue() == -1.0D) return NaND;
	    
	    return (int)(value.doubleValue() * 1000.0D) / 10.0D;
	}
	
	public static String getCPUUsageBar() {
		CpuUsageBar = "";
	    double round_usage = getProcessCpuLoad() * 0.7D;
	    int usage = (int)Math.ceil(round_usage);
	    for (int i = 0; i < 40; i++) {
	    	if (i < usage) {
	    		CpuUsageBar = String.valueOf(CpuUsageBar) + "§a▬";
			} else if (usage == i) {
				CpuUsageBar = String.valueOf(CpuUsageBar) + "§6▬";
			} else {
				CpuUsageBar = String.valueOf(CpuUsageBar) + "§c▬";
			} 
	    } 
	    return CpuUsageBar;
	  }
	
	public static long totalDisk() {
	    f = new File("/");
	    
	    totaldiskv = String.valueOf(OtherUtils.BytesToGigaBytes(f.getTotalSpace()));
	    
	    return f.getTotalSpace();
	  }
	  
	  public static long freeDisk() {
	    f = new File("/");
	    
	    freespacev = String.valueOf(OtherUtils.BytesToGigaBytes(f.getFreeSpace()));
	    
	    return f.getFreeSpace();
	  }
	  
	  public static long usableDisk() {
	    f = new File("/");
	    
	    totalspaceusablev = String.valueOf(OtherUtils.BytesToGigaBytes(f.getUsableSpace()));
	    
	    return f.getUsableSpace();
	  }
	  
	  public static double getDiskUsage() {
		    used_memory = totalDisk() - freeDisk();
		    double percent = (totalDisk() / 100L);
		    return used_memory / percent;
		  }
	  
	
	public static String getDiskUsageBar() {
		DiskUsageBar = "";
		double round_usage = getDiskUsage() * 0.7D;
		int usage = (int)Math.ceil(round_usage);
		for (int i = 0; i < 40; i++) {
			if (i < usage) {
				DiskUsageBar = String.valueOf(DiskUsageBar) + "§a▬";
			} else if (usage == i) {
				DiskUsageBar = String.valueOf(DiskUsageBar) + "§6▬";
			} else {
				DiskUsageBar = String.valueOf(DiskUsageBar) + "§c▬";
			} 
	    } 
	    return DiskUsageBar;
	}
	
	public static double BytesToMegaBytes(long bytes) {
	    double kilo_bytes = (bytes / 1024L);
	    double mega_bytes = kilo_bytes / 1024.0D;
	    return round(mega_bytes, 3);
	}
	  
	public static double BytesToGigaBytes(long bytes) {
		double kilo_bytes = (bytes / 1024L);
	    double mega_bytes = kilo_bytes / 1024.0D;
	    double giga_bytes = mega_bytes / 1024.0D;
	    return round(giga_bytes, 3);
	}
	
	static double round(double value, int sec) {
		return Math.round(value * Math.pow(10.0D, sec)) / Math.pow(10.0D, sec);
	}
	
	public static String getDate() {
		String currentDate = new SimpleDateFormat(ConfigGeneral.getConfig().getString("Plugin.Date-Format")).format(System.currentTimeMillis());
		
		return currentDate;
	}
	
	public static String getTime() {
		
		String pattern = "";
		
		if (ConfigGeneral.getConfig().getInt("Plugin.12-Hours-Or-24-Hours-Format") == 24) {
			pattern = "HH:mm:ss";
		} else if (ConfigGeneral.getConfig().getInt("Plugin.12-Hours-Or-24-Hours-Format") == 12) {
			pattern = "h:mm:ss a";
		} else {
			pattern = "HH:mm:ss";
		}
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date = simpleDateFormat.format(new Date());
		
		return date;
	}
	
	public static Color getColor(String paramString) {
	    String temp = paramString;
	    if (temp.equalsIgnoreCase("AQUA")) {
	      return Color.AQUA;
	    }
	    if (temp.equalsIgnoreCase("BLACK")) {
	      return Color.BLACK;
	    }
	    if (temp.equalsIgnoreCase("BLUE")) {
	      return Color.BLUE;
	    }
	    if (temp.equalsIgnoreCase("FUCHSIA")) {
	      return Color.FUCHSIA;
	    }
	    if (temp.equalsIgnoreCase("GRAY")) {
	      return Color.GRAY;
	    }
	    if (temp.equalsIgnoreCase("GREEN")) {
	      return Color.GREEN;
	    }
	    if (temp.equalsIgnoreCase("LIME")) {
	      return Color.LIME;
	    }
	    if (temp.equalsIgnoreCase("MAROON")) {
	      return Color.MAROON;
	    }
	    if (temp.equalsIgnoreCase("NAVY")) {
	      return Color.NAVY;
	    }
	    if (temp.equalsIgnoreCase("OLIVE")) {
	      return Color.OLIVE;
	    }
	    if (temp.equalsIgnoreCase("ORANGE")) {
	      return Color.ORANGE;
	    }
	    if (temp.equalsIgnoreCase("PURPLE")) {
	      return Color.PURPLE;
	    }
	    if (temp.equalsIgnoreCase("RED")) {
	      return Color.RED;
	    }
	    if (temp.equalsIgnoreCase("SILVER")) {
	      return Color.SILVER;
	    }
	    if (temp.equalsIgnoreCase("TEAL")) {
	      return Color.TEAL;
	    }
	    if (temp.equalsIgnoreCase("WHITE")) {
	      return Color.WHITE;
	    }
	    if (temp.equalsIgnoreCase("YELLOW")) {
	      return Color.YELLOW;
	    }
	    
	    return null;
	}
	
	public static void Fireworkmethod(Player p, String firework) {		
        for (int i = 1; i < FireworkListCUtility.getConfig().getInt("Firework-List." + firework +".Options.Amount"); i++) {
            ArrayList < Color > colors = new ArrayList < Color > ();
            ArrayList < Color > fade = new ArrayList < Color > ();
            List < String > lore = FireworkListCUtility.getConfig().getStringList("Firework-List." + firework +".Options.Colors");
            List < String > lore2 = FireworkListCUtility.getConfig().getStringList("Firework-List." + firework +".Options.Fade");
            for (String l1: lore) {
                colors.add(getColor(l1));
            }
            for (String l2: lore2) {
                fade.add(getColor(l2));
            }
            
            final Firework f = p.getWorld().spawn(p.getLocation().add(0.5D, FireworkListCUtility.getConfig().getInt("Firework-List." + firework +".Options.Height"), 0.5D), Firework.class);

            FireworkMeta fm = f.getFireworkMeta();
            fm.addEffect(FireworkEffect.builder().flicker(FireworkListCUtility.getConfig().getBoolean("Firework-List." + firework +".Options.Flicker"))
                .trail(FireworkListCUtility.getConfig().getBoolean("Firework-List." + firework +".Options.Trail"))
                .with(FireworkEffect.Type.valueOf(FireworkListCUtility.getConfig().getString("Firework-List." + firework +".Options.Type"))).withColor(colors).withFade(fade)
                .build());

            if (!FireworkListCUtility.getConfig().getBoolean("Firework-List." + firework +".Options.Instant-explode")) {
                fm.setPower(FireworkListCUtility.getConfig().getInt("Firework-List." + firework +".Options.Power"));
            }
            f.setFireworkMeta(fm);
            if (FireworkListCUtility.getConfig().getBoolean("Firework-List." + firework +".Options.Instant-explode")) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                    public void run() {
                        f.detonate();
                    }
                }, 5L);
            }
        }
    }

}
