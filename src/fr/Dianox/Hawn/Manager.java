package fr.Dianox.Hawn;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.Dianox.Hawn.Event.BasicFeatures;
import fr.Dianox.Hawn.Event.CancelTPWarpSpawn;
import fr.Dianox.Hawn.Event.FunFeatures;
import fr.Dianox.Hawn.Event.OnChatEvent;
import fr.Dianox.Hawn.Event.OnCommandEvent;
import fr.Dianox.Hawn.Event.OnGuiInteract;
import fr.Dianox.Hawn.Event.OnJoin;
import fr.Dianox.Hawn.Event.OnQuit;
import fr.Dianox.Hawn.Event.OnScoreboard;
import fr.Dianox.Hawn.Event.PlayerChangeWorld;
import fr.Dianox.Hawn.Event.PlayerEvents;
import fr.Dianox.Hawn.Event.ProtectionsEventWorld;
import fr.Dianox.Hawn.Event.WorldEvent;
import fr.Dianox.Hawn.Event.CustomJoinItem.SpecialCJIPlayerVisibility;
import fr.Dianox.Hawn.Event.Ping.ServerPingEvent;

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
	}

}