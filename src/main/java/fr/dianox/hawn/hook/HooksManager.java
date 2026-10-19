package fr.dianox.hawn.hook;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.hook.hooklist.HawnExpansion;
import fr.dianox.hawn.hook.hooklist.PlaceHolderAPI;
import fr.dianox.hawn.hook.hooklist.WorldGuard;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import org.bukkit.Bukkit;

public class HooksManager {

	// Known once at startup and at /hawn reload, not read in general.yml at every message
	private static boolean papi = false;
	private static boolean worldGuard = false;
	private static WorldGuard wgHook;
	// %hawn_...% for the other plugins: an Object, the class of PlaceholderAPI is only loaded when it is there
	private static Object expansion;

	private final PlaceHolderAPI papiHook;
	private final WorldGuard wg;

	public HooksManager(Main plugin) {
		papiHook = new PlaceHolderAPI(plugin);
		wg = new WorldGuard();
		wgHook = wg;

		// BattleLevels (abandoned) was removed in 1.4
		if (ConfigGeneral.getConfig().contains("Plugin.Use.Hook.BattleLevels")) {
			ConfigGeneral.getConfig().set("Plugin.Use.Hook.BattleLevels", null);
			ConfigGeneral.saveConfigFile();
		}

		// MVdWPlaceholderAPI (abandoned since 2020, its premium plugins use PlaceholderAPI) was removed in 1.4.9
		if (ConfigGeneral.getConfig().contains("Plugin.Use.Hook.MVdWPlaceholderAPI")) {
			ConfigGeneral.getConfig().set("Plugin.Use.Hook.MVdWPlaceholderAPI", null);
			ConfigGeneral.saveConfigFile();
		}

		refresh();
	}

	/**
	 * Reads the hook options again (startup and /hawn reload). A hook is used only when its plugin is there:
	 * Keep-The-Option can keep Enable at true without the plugin.
	 */
	public static void refresh() {
		papi = ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")
				&& Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
		worldGuard = ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")
				&& wgHook != null && wgHook.isAvailable();
		expansion();
	}

	// Registered or removed after each reading of general.yml
	private static void expansion() {
		boolean wanted = papi && ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Expansion.Enable", true);
		try {
			if (wanted && expansion == null) {
				HawnExpansion hawn = new HawnExpansion();
				if (hawn.register()) {
					expansion = hawn;
				}
			} else if (!wanted && expansion != null) {
				((HawnExpansion) expansion).unregister();
				expansion = null;
			}
		} catch (Throwable t) {
			Main.getInstance().getLogger().warning("The placeholders %hawn_...% could not be given to PlaceholderAPI: " + t);
		}
	}

	public static boolean expansionRegistered() {
		return expansion != null;
	}

	/**
	 * /hawn reload: a hook whose plugin was removed is turned off in general.yml too, then the hooks are read again.
	 */
	public static void reload() {
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable") && !Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			Bukkit.getConsoleSender().sendMessage("| Please note that to remove the PlaceHolderAPI support, you must restart the server");
			Bukkit.getConsoleSender().sendMessage("| The plugin supports fast removal, but does not guarantee a return to normal with a hawn reload");
			ConfigGeneral.getConfig().set("Plugin.Use.Hook.PlaceholderAPI.Enable", false);
			ConfigGeneral.saveConfigFile();
		}

		refresh();
	}

	public static boolean papi() {
		return papi;
	}

	public static boolean worldGuard() {
		return worldGuard;
	}

	// Not a hook of general.yml: its API is only read when it is there (the permissions checked before a player exists, Paper 1.21.7+)
	public static boolean luckPerms() {
		return Bukkit.getPluginManager().isPluginEnabled("LuckPerms");
	}

	public PlaceHolderAPI getPapi() {
		return papiHook;
	}

	public WorldGuard getWg() {
		return wg;
	}

}
