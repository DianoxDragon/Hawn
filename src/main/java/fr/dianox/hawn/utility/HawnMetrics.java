package fr.dianox.hawn.utility;

import fr.dianox.hawn.modules.chat.ChatFormat;
import fr.dianox.hawn.modules.ranks.Ranks;
import fr.dianox.hawn.utility.config.configs.AutoBroadcastConfig;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.ScoreboardMainConfig;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.ConfigFDoubleJump;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.ConfigGLP;
import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiFunGun;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiGrappling;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiHidePlayers;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiLobbyBow;
import fr.dianox.hawn.utility.config.configs.events.OnChatConfig;
import fr.dianox.hawn.utility.config.configs.events.OnJoinConfig;
import fr.dianox.hawn.utility.config.configs.events.VoidTPConfig;
import fr.dianox.hawn.utility.config.configs.tab.TablistConfig;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.AdvancedPie;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The stats of bStats (https://bstats.org/plugin/bukkit/Hawn/4563): since 1.5.5, the modules turned on by each server.
 * bStats reads them every 30 minutes, once the files are loaded.
 */
public final class HawnMetrics {

	private static final int ID = 4563;

	private HawnMetrics() {}

	public static void start(JavaPlugin plugin) {
		Metrics metrics = new Metrics(plugin, ID);
		metrics.addCustomChart(new AdvancedPie("enabled_modules", HawnMetrics::modules));
	}

	// One per module turned on (the pie counts the servers of each)
	private static Map<String, Integer> modules() {
		Map<String, Integer> modules = new LinkedHashMap<>();

		boolean joinItems = on(ConfigCJIGeneral.getConfig(), "Custom-Join-Item.Enable");
		add(modules, "Join items", joinItems);
		add(modules, "Player visibility", joinItems && special("Special-HidePlayers") && on(SpecialCjiHidePlayers.getConfig(), "PV.Enable"));
		add(modules, "Lobby bow", joinItems && special("Special-LobbyBow") && on(SpecialCjiLobbyBow.getConfig(), "LobbyBow.Enable"));
		add(modules, "Fun gun", joinItems && special("Special-FunGun") && on(SpecialCjiFunGun.getConfig(), "FunGun.Enable"));
		add(modules, "Grappling hook", joinItems && special("Special-Grappling") && on(SpecialCjiGrappling.getConfig(), "Grappling.Enable"));

		add(modules, "Scoreboard", on(ScoreboardMainConfig.getConfig(), "Scoreboard.Enable"));
		add(modules, "Tab list", on(TablistConfig.getConfig(), "Tablist.enable"));
		add(modules, "Ranks", Ranks.enabled());
		add(modules, "Chat format", ChatFormat.mode() != ChatFormat.Mode.OFF);
		add(modules, "Anti-swear", on(OnChatConfig.getConfig(), "Anti-Swear.Enable"));
		add(modules, "Anti-advertising", on(OnChatConfig.getConfig(), "Anti-Advertising.Enable"));
		add(modules, "Auto broadcast", on(AutoBroadcastConfig.getConfig(), "Config.Messages.Enable"));
		add(modules, "Join boss bar", on(OnJoinConfig.getConfig(), "Boss-Bar.Enable"));
		add(modules, "Join title", on(OnJoinConfig.getConfig(), "Title.Enable"));
		add(modules, "Double jump", on(ConfigFDoubleJump.getConfig(), "DoubleJump.Enable"));
		add(modules, "Jump pads", on(ConfigGLP.getConfig(), "JumpPads.Enable"));
		add(modules, "Void TP", on(VoidTPConfig.getConfig(), "VoidTP.Enable"));
		add(modules, "MySQL", on(ConfigGeneral.getConfig(), "Plugin.Use.MYSQL.Enable"));

		return modules;
	}

	private static void add(Map<String, Integer> modules, String name, boolean on) {
		if (on) {
			modules.put(name, 1);
		}
	}

	// A file not loaded yet counts as off
	private static boolean on(YamlConfiguration config, String option) {
		return config != null && config.getBoolean(option);
	}

	// A join item gives this special item
	private static boolean special(String special) {
		ConfigurationSection items = ConfigCJIGeneral.getConfig().getConfigurationSection("Custom-Join-Item.Items.Inventory.Items");
		if (items == null) {
			return false;
		}
		for (String key : items.getKeys(false)) {
			String value = items.getString(key + ".Special-Items");
			if (value != null && value.contains(special)) {
				return true;
			}
		}
		return false;
	}

}
