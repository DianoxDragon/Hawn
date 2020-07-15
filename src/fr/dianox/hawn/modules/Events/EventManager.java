package fr.dianox.hawn.modules.Events;

import fr.dianox.hawn.utility.config.configs.events.ProtectionPlayerConfig;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

	public List<EntityDamageEvent.DamageCause> damageEventList = new ArrayList<>();

	public EventManager() {
		loaddamageEvent();
	}

	public void loaddamageEvent() {
		damageEventList.clear();
		for (String s : ProtectionPlayerConfig.getConfig().getStringList("AntiDamage-Custom.Entity.Options.Damage-Type-List")) {
			if (s.equals("Entity")) continue;
			try {
				damageEventList.add(EntityDamageEvent.DamageCause.valueOf(s));
			} catch (Exception ignored) {}
		}
	}

	public List<EntityDamageEvent.DamageCause> getDamageEventList() {
		return damageEventList;
	}
}
