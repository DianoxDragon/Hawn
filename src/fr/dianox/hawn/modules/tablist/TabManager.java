package fr.dianox.hawn.modules.tablist;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.tablist.tab.AnimationTabTask;
import fr.dianox.hawn.modules.tablist.tab.MainTablist;
import fr.dianox.hawn.utility.NMSClass;
import fr.dianox.hawn.utility.config.configs.tab.TablistConfig;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class TabManager {

	public HashMap<String, Integer> animationtab = new HashMap<>();
	public HashMap<String, Integer> animationtabtask = new HashMap<>();
	public Integer tablistnumber = 0;

	private Constructor<?> newPacketPlayOutPlayerListHeaderFooter;
	public String hea = "";
	public String foo = "";

	public TabManager(Main plugin) {
		if (TablistConfig.getConfig().getBoolean("Tablist.enable")) {
			Class<?> packetPlayOutPlayerListHeaderFooter = NMSClass.getNMSClass("PacketPlayOutPlayerListHeaderFooter");
			try {
				assert packetPlayOutPlayerListHeaderFooter != null;
				newPacketPlayOutPlayerListHeaderFooter = packetPlayOutPlayerListHeaderFooter.getConstructor();
			} catch (NoSuchMethodException | SecurityException e1) {
				e1.printStackTrace();
			}
			Class<?> chatComponentText = NMSClass.getNMSClass("ChatComponentText");

			Iterator<?> iteanimtab = Objects.requireNonNull(TablistConfig.getConfig().getConfigurationSection("Animations")).getKeys(false).iterator();

			animationtab.clear();
			while (iteanimtab.hasNext()) {
				String string = (String) iteanimtab.next();

				if (string.contentEquals("Enable")) continue;

				if (TablistConfig.getConfig().getBoolean("Animations.Enable")) {
					BukkitTask task = new AnimationTabTask(string).runTaskTimer(plugin, 20, TablistConfig.getConfig().getInt("Animations." + string + ".refresh-time-ticks"));
					animationtabtask.put(string, task.getTaskId());
				}
			}

			BukkitTask tablistmain = new MainTablist(hea, foo, packetPlayOutPlayerListHeaderFooter, chatComponentText, newPacketPlayOutPlayerListHeaderFooter).runTaskTimer(plugin, 20L, TablistConfig.getConfig().getLong("Tablist.refresh-time-ticks"));

			tablistnumber = tablistmain.getTaskId();
		}
	}
}
