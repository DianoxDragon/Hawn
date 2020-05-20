package fr.dianox.hawn.command.commands.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.utility.ActionBar;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import me.clip.placeholderapi.PlaceholderAPI;

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
		
		String str = ConfigMMsg.getConfig().getString("Vanish.Action-Bar");
		str = str.replaceAll("&", "§");
		str = PlaceHolders.ReplaceMainplaceholderP(str, p);
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
			str = PlaceholderAPI.setPlaceholders(p, str);
		}
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable")) {
			str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
		}
		str = str.substring(1, str.length() - 1);
		
		ActionBar.sendActionBar(p, str);
		
	}

}