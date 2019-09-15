package fr.dianox.hawn;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.dianox.hawn.event.BasicFeatures;
import fr.dianox.hawn.event.CancelTPWarpSpawn;
import fr.dianox.hawn.event.FunFeatures;
import fr.dianox.hawn.event.OnChatEvent;
import fr.dianox.hawn.event.OnCommandEvent;
import fr.dianox.hawn.event.OnGuiInteract;
import fr.dianox.hawn.event.OnInventoryInteract;
import fr.dianox.hawn.event.OnJoin;
import fr.dianox.hawn.event.OnQuit;
import fr.dianox.hawn.event.OnScoreboard;
import fr.dianox.hawn.event.PlayerChangeWorld;
import fr.dianox.hawn.event.PlayerEvents;
import fr.dianox.hawn.event.ProtectionsEventWorld;
import fr.dianox.hawn.event.WorldEvent;
import fr.dianox.hawn.event.customjoinitem.SpecialCJIPlayerVisibility;
import fr.dianox.hawn.event.modules.DisableOffHand;
import fr.dianox.hawn.event.ping.ServerPingEvent;

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
		pm.registerEvents(new SpecialCJIPlayerVisibility(), pl);
		pm.registerEvents(new OnInventoryInteract(), pl);
		
		if (Main.Spigot_Version >= 19) {
			pm.registerEvents(new DisableOffHand(), pl);
		}
	}

}