package fr.dianox.hawn.modules.world.protection;

import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.configs.events.ConfigGProtection;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class BlockExceptions {

	public List<Material> block_exception_place = new ArrayList<>();
	public List<Material> block_exception_break = new ArrayList<>();

	public BlockExceptions() {
		load();
	}

	public void load() {
		block_exception_break.clear();
		block_exception_place.clear();

		if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Block-Exception.Enable")) {
			for (String str: ConfigGProtection.getConfig().getStringList("Protection.Construct.Anti-Place.Block-Exception.Materials")) {
				try {
					block_exception_place.add(XMaterial.getMat(str, "Protection.Construct.Anti-Place.Block-Exception.Materials"));
				} catch (Exception ignored) {}
			}
		}

		if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Block-Exception.Enable")) {
			for (String str: ConfigGProtection.getConfig().getStringList("Protection.Construct.Anti-Break.Block-Exception.Materials")) {
				try {
					block_exception_break.add(XMaterial.getMat(str, "Protection.Construct.Anti-Break.Block-Exception.Materials"));
				} catch (Exception ignored) {}
			}
		}
	}

	public List<Material> getBlock_exception_break() {
		return block_exception_break;
	}

	public List<Material> getBlock_exception_place() {
		return block_exception_place;
	}
}
