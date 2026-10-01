package fr.dianox.hawn.modules.VoidTP;

import fr.dianox.hawn.utility.config.configs.events.VoidTPConfig;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VoidTPManager {

	public List<String> world_voidtp = new ArrayList<>();
	public List<Player> antispam = new ArrayList<>();

	public VoidTPManager() {
		load();
	}

	public void load() {
		if (VoidTPConfig.getConfig().getBoolean("VoidTP.Enable") && VoidTPConfig.getConfig().getBoolean("VoidTP.Options.VoidTP-Per-World.Enable")) {
			world_voidtp.clear();
			world_voidtp.addAll(Objects.requireNonNull(VoidTPConfig.getConfig().getConfigurationSection("VoidTP.Options.VoidTP-Per-World.World-List")).getKeys(false));
		}
	}

	public List<String> getWorld_voidtp() {
		return world_voidtp;
	}

	public List<Player> getAntispam() {
		return antispam;
	}
}
