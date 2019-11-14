package fr.dianox.hawn.commands.features;

import fr.dianox.hawn.utility.ActionBar;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class VanishTaskAB extends BukkitRunnable {

	private Player p;

	public VanishTaskAB(Player p) {
        this.p = p;
	}
	
	@Override
	public void run() {
		if (p == null || !p.isOnline()) {
            p = null;
            cancel();
            return;
        }
		
		String str = ConfigMCommands.getConfig().getString("Vanish.Action-Bar");
		str = str.replaceAll("&", "Â§");
		str = PlaceHolders.ReplaceMainplaceholderP(str, p);
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
			str = PlaceholderAPI.setPlaceholders(p, str);
		}
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
			str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
		}
		str = str.substring(1, str.length() - 1);
		
		ActionBar.sendActionBar(p, str);
		
	}

}