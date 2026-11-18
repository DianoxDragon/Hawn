package fr.dianox.hawn.modules;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.ServerLinksConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Since 1.6.2: the links of ServerLinks.yml in the pause menu of the clients 1.21+ (Discord, website, rules...). A Type
 * (WEBSITE, SUPPORT, COMMUNITY_GUIDELINES...) is a link the game names itself, in the language of each player; a Name
 * is your own text, with colours. Reached by reflection: Hawn is compiled against 1.16.5.
 */
public final class ServerLinks {

	// The links added by Hawn, removed before the next load (/hawn reload)
	private static final List<Object> added = new ArrayList<>();

	private ServerLinks() {}

	public static synchronized void load() {
		Object links;
		try {
			links = Bukkit.getServer().getClass().getMethod("getServerLinks").invoke(Bukkit.getServer());
		} catch (ReflectiveOperationException | RuntimeException e) {
			if (ServerLinksConfig.getConfig().getBoolean("Server-Links.Enable")) {
				Main.getInstance().getLogger().info("ServerLinks.yml: the links of the pause menu need a 1.21+ server");
			}
			return;
		}

		try {
			Class<?> linksClass = Class.forName("org.bukkit.ServerLinks");
			Class<?> linkClass = Class.forName("org.bukkit.ServerLinks$ServerLink");
			Method remove = linksClass.getMethod("removeLink", linkClass);
			for (Object link : added) {
				remove.invoke(links, link);
			}
			added.clear();

			ConfigurationSection section = ServerLinksConfig.getConfig().getConfigurationSection("Server-Links.Links");
			if (!ServerLinksConfig.getConfig().getBoolean("Server-Links.Enable") || section == null) {
				return;
			}

			@SuppressWarnings({"unchecked", "rawtypes"})
			Class<? extends Enum> typeClass = (Class<? extends Enum>) Class.forName("org.bukkit.ServerLinks$Type");
			Method byType = linksClass.getMethod("addLink", typeClass, URI.class);
			Method byName = linksClass.getMethod("addLink", String.class, URI.class);

			for (String key : section.getKeys(false)) {
				String url = section.getString(key + ".Url", "");
				String type = section.getString(key + ".Type", "");
				String name = section.getString(key + ".Name", "");
				try {
					URI uri = new URI(url.trim());
					if (uri.getScheme() == null) throw new IllegalArgumentException("no https://");
					Object link;
					if (!type.isEmpty()) {
						@SuppressWarnings("unchecked")
						Object value = Enum.valueOf(typeClass, type.trim().toUpperCase(Locale.ROOT));
						link = byType.invoke(links, value, uri);
					} else {
						link = byName.invoke(links, MessageUtils.colourTheStuff(name.isEmpty() ? key : name), uri);
					}
					added.add(link);
				} catch (Exception e) {
					Main.getInstance().getLogger().warning("ServerLinks.yml, Server-Links.Links." + key + ": not added ("
							+ (e.getCause() != null ? e.getCause().getMessage() : e.getMessage()) + ")");
				}
			}
		} catch (ReflectiveOperationException | RuntimeException e) {
			Main.getInstance().getLogger().warning("ServerLinks.yml: the links could not be set (" + e + ")");
		}
	}

	public static synchronized int count() {
		return added.size();
	}
}
