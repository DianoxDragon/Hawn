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
import fr.dianox.hawn.event.modules.DisableOffHand;
import fr.dianox.hawn.event.ping.ServerPingEvent;
import fr.dianox.hawn.modules.SignSystem;
import fr.dianox.hawn.modules.admin.EditPlayerGui;
import fr.dianox.hawn.modules.admin.ListGui;
import fr.dianox.hawn.modules.onjoin.cji.SpecialIteFunGun;
import fr.dianox.hawn.modules.onjoin.cji.SpecialIteLobbyBow;
import fr.dianox.hawn.modules.onjoin.cji.SpecialItemPlayerVisibility;
import fr.dianox.hawn.modules.worldsystem.GuiSystem;

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
		pm.registerEvents(new SpecialIteFunGun(), pl);
		pm.registerEvents(new GuiSystem(), pl);
		pm.registerEvents(new ListGui(), pl);
		pm.registerEvents(new EditPlayerGui(), pl);
		pm.registerEvents(new SignSystem(), pl);
		
		if (Main.getInstance().getVersionUtils().getSpigot_Version() >= 19) {
			pm.registerEvents(new DisableOffHand(), pl);
		}
	}

}