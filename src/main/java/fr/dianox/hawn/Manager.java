package fr.dianox.hawn;

import fr.dianox.hawn.event.*;
import fr.dianox.hawn.event.modules.DisableOffHand;
import fr.dianox.hawn.event.ping.ServerPingEvent;
import fr.dianox.hawn.modules.onjoin.cji.SpecialIteLobbyBow;
import fr.dianox.hawn.modules.onjoin.cji.SpecialItemPlayerVisibility;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class Manager {
	
	public Main pl;
	
	public Manager(Main h) {
		this.pl = h;
	}
	
	public void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new OnJoin(), pl);
		pm.registerEvents(new OnQuit(), pl);
		pm.registerEvents(new BasicFeatures(), pl);
		pm.registerEvents(new ProtectionsEventWorld(), pl);
		pm.registerEvents(new FunFeatures(), pl);
		pm.registerEvents(new WorldEvent(), pl);
		pm.registerEvents(new PlayerEvents(), pl);
		pm.registerEvents(new OnCommandEvent(), pl);
		pm.registerEvents(new OnChatEvent(), pl);
		pm.registerEvents(new ServerPingEvent(), pl);
		pm.registerEvents(new OnGuiInteract(), pl);
		pm.registerEvents(new OnScoreboard(), pl);
		pm.registerEvents(new PlayerChangeWorld(), pl);
		pm.registerEvents(new CancelTPWarpSpawn(), pl);
		pm.registerEvents(new SpecialItemPlayerVisibility(), pl);
		pm.registerEvents(new OnInventoryInteract(), pl);
		pm.registerEvents(new SpecialIteLobbyBow(), pl);
		
		if (Main.Spigot_Version >= 19) {
			pm.registerEvents(new DisableOffHand(), pl);
		}
	}

}