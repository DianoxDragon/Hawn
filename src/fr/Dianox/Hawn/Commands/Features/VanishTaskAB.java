package fr.Dianox.Hawn.Commands.Features;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Dianox.Hawn.Utility.ActionBar;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.ConfigGeneral;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;
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
		
		String str = ConfigMCommands.getConfig().getString("Vanish.Action-Bar");
		str = str.replaceAll("&", "§");
		str = MessageUtils.ReplaceMainplaceholderP(str, p);
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
