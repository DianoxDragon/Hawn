package fr.dianox.hawn.hook;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.hook.hooklist.MVdWPlaceholderAPI;
import fr.dianox.hawn.hook.hooklist.PlaceHolderAPI;
import fr.dianox.hawn.hook.hooklist.WorldGuard;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import org.bukkit.Bukkit;

public class HooksManager {

	// Known once at startup and at /hawn reload, not read in general.yml at every message
	private static boolean papi = false;
	private static boolean mvdw = false;
	private static boolean worldGuard = false;
	private static WorldGuard wgHook;

	private final PlaceHolderAPI papiHook;
	private final MVdWPlaceholderAPI mdvpapi;
	private final WorldGuard wg;

	public HooksManager(Main plugin) {
		papiHook = new PlaceHolderAPI(plugin);
		mdvpapi = new MVdWPlaceholderAPI(plugin);
		wg = new WorldGuard();
		wgHook = wg;

		// BattleLevels (abandoned) was removed in 1.4
		if (ConfigGeneral.getConfig().contains("Plugin.Use.Hook.BattleLevels")) {
			ConfigGeneral.getConfig().set("Plugin.Use.Hook.BattleLevels", null);
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
		mvdw = ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable")
				&& Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI");
		worldGuard = ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")
				&& wgHook != null && wgHook.isAvailable();
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

		if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable") && !Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
			ConfigGeneral.getConfig().set("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable", false);
			ConfigGeneral.saveConfigFile();
		}

		refresh();
	}

	public static boolean papi() {
		return papi;
	}

	public static boolean mvdw() {
		return mvdw;
	}

	public static boolean worldGuard() {
		return worldGuard;
	}

	public PlaceHolderAPI getPapi() {
		return papiHook;
	}

	public MVdWPlaceholderAPI getMdvpapi() {
		return mdvpapi;
	}

	public WorldGuard getWg() {
		return wg;
	}

}
