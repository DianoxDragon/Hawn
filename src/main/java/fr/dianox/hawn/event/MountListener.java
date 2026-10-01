package fr.dianox.hawn.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;

/**
 * The entity mount event is "org.spigotmc.event.entity.EntityMountEvent" up to 1.20.4
 * and "org.bukkit.event.entity.EntityMountEvent" afterwards (the old one is gone on Paper).
 * It is registered by reflection so the same jar works on every version.
 */
public final class MountListener implements Listener {

	private MountListener() {}

	public static void register(Plugin plugin) {
		Class<? extends Event> eventClass = findEvent("org.bukkit.event.entity.EntityMountEvent", "org.spigotmc.event.entity.EntityMountEvent");
		if (eventClass == null) {
			plugin.getLogger().warning("No entity mount event found, the Block-Mount option is disabled");
			return;
		}

		final Method getEntity;
		try {
			getEntity = eventClass.getMethod("getEntity");
		} catch (NoSuchMethodException e) {
			plugin.getLogger().warning("Unsupported entity mount event, the Block-Mount option is disabled");
			return;
		}

		Bukkit.getPluginManager().registerEvent(eventClass, new MountListener(), EventPriority.NORMAL, (listener, event) -> {
			if (!eventClass.isInstance(event)) {
				return;
			}

			try {
				PlayerEvents.onMount((Entity) getEntity.invoke(event), (Cancellable) event);
			} catch (ReflectiveOperationException e) {
				throw new EventException(e);
			}
		}, plugin);
	}

	@SuppressWarnings("unchecked")
	private static Class<? extends Event> findEvent(String... names) {
		for (String name : names) {
			try {
				Class<?> clazz = Class.forName(name);
				if (Event.class.isAssignableFrom(clazz)) {
					return (Class<? extends Event>) clazz;
				}
			} catch (ClassNotFoundException ignored) {}
		}
		return null;
	}
}
