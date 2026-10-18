package fr.dianox.hawn.modules.ranks;

import fr.dianox.hawn.utility.MessageUtils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Map;
import java.util.OptionalInt;

/**
 * Source: LUCKPERMS (1.4.8): the rank of a player is their primary group in LuckPerms, with the prefix, the suffix and
 * the weight (the highest first in the tab list) of LuckPerms. A rank of Ranks.List with the name of the group gives
 * its Name-Colour. Only loaded when LuckPerms is installed.
 */
final class RanksLuckPerms {

	private RanksLuckPerms() {}

	/**
	 * Null when LuckPerms doesn't know the player yet (Hawn then uses the ranks of Ranks.yml).
	 */
	static Ranks.Rank rankOf(Player p, Map<String, Ranks.Rank> ranks) {
		LuckPerms lp;
		try {
			lp = LuckPermsProvider.get();
		} catch (IllegalStateException e) {
			return null;
		}

		User user = lp.getUserManager().getUser(p.getUniqueId());
		if (user == null) {
			return null;
		}

		String group = user.getPrimaryGroup();
		Ranks.Rank own = ranks.get(group.toLowerCase(Locale.ROOT));

		CachedMetaData meta = user.getCachedData().getMetaData();
		String prefix = meta.getPrefix();
		String suffix = meta.getSuffix();

		Group g = lp.getGroupManager().getGroup(group);
		OptionalInt weight = g == null ? OptionalInt.empty() : g.getWeight();
		// The weight of LuckPerms: the highest first. Hawn: the lowest Priority first
		int priority = weight.isPresent() ? Math.max(0, 9999 - weight.getAsInt()) : (own != null ? own.priority : 9999);

		String shownPrefix = MessageUtils.colourTheStuff(prefix == null ? "" : prefix);
		// The colour of the name: the Name-Colour of the rank of the same name, the meta name-colour of LuckPerms,
		// else the last colour of the prefix of LuckPerms
		String colour = own != null && own.nameColour != null ? own.nameColour : Ranks.nameColour(meta.getMetaValue("name-colour"), shownPrefix);

		return new Ranks.Rank(group, shownPrefix, MessageUtils.colourTheStuff(suffix == null ? "" : suffix), priority, colour);
	}

	private static net.luckperms.api.event.EventSubscription<net.luckperms.api.event.user.UserDataRecalculateEvent> subscription;
	private static net.luckperms.api.event.EventSubscription<net.luckperms.api.event.group.GroupDataRecalculateEvent> groupSubscription;

	/**
	 * A user recalculated by LuckPerms (their group, a permission changed), or a group (its prefix, its weight): refreshed
	 * on the main thread (the events of LuckPerms come outside of it).
	 */
	static void listen() {
		if (subscription != null) return;
		try {
			net.luckperms.api.event.EventBus bus = LuckPermsProvider.get().getEventBus();
			subscription = bus.subscribe(net.luckperms.api.event.user.UserDataRecalculateEvent.class, e -> {
				java.util.UUID id = e.getUser().getUniqueId();
				org.bukkit.Bukkit.getScheduler().runTask(fr.dianox.hawn.Main.getInstance(), () -> {
					Player p = org.bukkit.Bukkit.getPlayer(id);
					if (p != null) Ranks.refresh(p, false);
				});
			});
			// A group changed: every player online (a group can be inherited)
			groupSubscription = bus.subscribe(net.luckperms.api.event.group.GroupDataRecalculateEvent.class, e ->
				org.bukkit.Bukkit.getScheduler().runTask(fr.dianox.hawn.Main.getInstance(), () -> {
					for (Player p : org.bukkit.Bukkit.getOnlinePlayers()) {
						Ranks.refresh(p, false);
					}
				}));
		} catch (IllegalStateException ignored) {
			// LuckPerms not ready: the refresh every Refresh-Seconds still shows the changes
		}
	}

	static void stopListening() {
		if (subscription != null) {
			subscription.close();
			subscription = null;
		}
		if (groupSubscription != null) {
			groupSubscription.close();
			groupSubscription = null;
		}
	}
}
