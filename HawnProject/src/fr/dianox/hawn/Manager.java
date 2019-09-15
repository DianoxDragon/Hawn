package fr.Dianox.Hawn;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.dianox.hawn.Event.BasicFeatures;
import fr.dianox.hawn.Event.CancelTPWarpSpawn;
import fr.dianox.hawn.Event.FunFeatures;
import fr.dianox.hawn.Event.OnChatEvent;
import fr.dianox.hawn.Event.OnCommandEvent;
import fr.dianox.hawn.Event.OnGuiInteract;
import fr.dianox.hawn.Event.OnInventoryInteract;
import fr.dianox.hawn.Event.OnJoin;
import fr.dianox.hawn.Event.OnQuit;
import fr.dianox.hawn.Event.OnScoreboard;
import fr.dianox.hawn.Event.PlayerChangeWorld;
import fr.dianox.hawn.Event.PlayerEvents;
import fr.dianox.hawn.Event.ProtectionsEventWorld;
import fr.dianox.hawn.Event.WorldEvent;
import fr.dianox.hawn.Event.CustomJoinItem.SpecialCJIPlayerVisibility;
import fr.dianox.hawn.Event.Modules.DisableOffHand;
import fr.dianox.hawn.Event.Ping.ServerPingEvent;

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
		
		if (Main.HandMethod) {
			pm.registerEvents(new DisableOffHand(), pl);
		}
	}

}