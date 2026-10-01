package fr.dianox.hawn.modules.serverlist;

import fr.dianox.hawn.utility.config.configs.ServerListConfig;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

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
			Iterator<?> iterator5 = Objects.requireNonNull(ServerListConfig.getConfig().getConfigurationSection("Motd.Classic.Random-List")).getKeys(false).iterator();

			Integer bbnumberput = 0;

			while (iterator5.hasNext()) {
				String string = (String) iterator5.next();
				motd_sl.put(bbnumberput, string);
				bbnumberput++;
				motd_total_sl++;
			}

			motd_total_sl--;
		}
	}

	public HashMap<Integer, String> getMotd_sl() {
		return motd_sl;
	}

	public Integer getMotd_total_sl() {
		return motd_total_sl;
	}
}
