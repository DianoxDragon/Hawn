package fr.dianox.hawn.hook;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.hook.hooklist.BattleLevel;
import fr.dianox.hawn.hook.hooklist.MVdWPlaceholderAPI;
import fr.dianox.hawn.hook.hooklist.PlaceHolderAPI;
import fr.dianox.hawn.hook.hooklist.WorldGuard;

public class HooksManager {

	private final PlaceHolderAPI papi;
	private final MVdWPlaceholderAPI mdvpapi;
	private final WorldGuard wg;
	private final BattleLevel bl;

	public HooksManager(Main plugin) {
		papi = new PlaceHolderAPI(plugin);
		mdvpapi = new MVdWPlaceholderAPI(plugin);
		wg = new WorldGuard(plugin);
		bl = new BattleLevel(plugin);
	}

	public PlaceHolderAPI getPapi() {
		return papi;
	}

	public MVdWPlaceholderAPI getMdvpapi() {
		return mdvpapi;
	}

	public WorldGuard getWg() {
		return wg;
	}

	public BattleLevel getBl() {
		return bl;
	}

}