package fr.dianox.hawn.modules.serverlist;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.config.configs.ServerListConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

public class ServerListManager {

	public HashMap<Integer, String> motd_sl = new HashMap<>();
	public Integer motd_total_sl = 0;

	public ServerListManager() {
		load();
	}

	public void load() {
		motd_total_sl = 0;
		motd_sl.clear();

		if (ServerListConfig.getConfig().getBoolean("Motd.Classic.Enable")) {
			// Since 1.6.2: no Random-List (or an empty one) no longer breaks the server list
			ConfigurationSection list = ServerListConfig.getConfig().getConfigurationSection("Motd.Classic.Random-List");
			if (list != null) {
				int number = 0;
				for (String key : list.getKeys(false)) {
					motd_sl.put(number++, key);
					motd_total_sl++;
				}
			}

			motd_total_sl--;
		}

		// Since 1.6.2: the slots set by /maxplayers
		int slots = ServerListConfig.getConfig().getInt("Slots.Max-Players", 0);
		if (slots > 0 && slots != Bukkit.getMaxPlayers() && !setMaxPlayers(slots)) {
			Main.getInstance().getLogger().warning("ServerList.yml, Slots.Max-Players: this server can't change its slots while it runs, set max-players in server.properties");
		}
	}

	/**
	 * The real slots of the server (Server#setMaxPlayers, 1.17+ on Spigot). False when the server can't do it.
	 */
	public static boolean setMaxPlayers(int slots) {
		try {
			Bukkit.getServer().getClass().getMethod("setMaxPlayers", int.class).invoke(Bukkit.getServer(), slots);
			return true;
		} catch (ReflectiveOperationException | RuntimeException e) {
			return false;
		}
	}

	public HashMap<Integer, String> getMotd_sl() {
		return motd_sl;
	}

	public Integer getMotd_total_sl() {
		return motd_total_sl;
	}
}
