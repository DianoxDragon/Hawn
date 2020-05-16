package fr.dianox.hawn.command.commands.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.config.WarpListConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMMsg;


public class TaskOnTPWarp extends BukkitRunnable {
	
	private Player p;
	private String tp;
	
	public TaskOnTPWarp(Player p, String tp) {
        this.p = p;
        this.tp = tp;
	}

	@Override
	public void run() {
		if (p == null || !p.isOnline()) {
            p = null;
            cancel();
            return;
        }
		
		try {
			org.bukkit.World w = org.bukkit.Bukkit.getServer().getWorld(WarpListConfig.getConfig().getString("Coordinated." + tp + ".World"));
			double x = WarpListConfig.getConfig().getDouble("Coordinated." + tp + ".X");
			double y = WarpListConfig.getConfig().getDouble("Coordinated." + tp + ".Y");
			double z = WarpListConfig.getConfig().getDouble("Coordinated." + tp + ".Z");
			float yaw = WarpListConfig.getConfig().getInt("Coordinated." + tp + ".Yaw");
			float pitch = WarpListConfig.getConfig().getInt("Coordinated." + tp + ".Pitch");

			p.teleport(new org.bukkit.Location(w, x, y, z, yaw, pitch));

			if (ConfigMMsg.getConfig().getBoolean("Warp.Tp.Self.Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList("Warp.Tp.Self.Messages")) {
					ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%warp%", tp), "", "", false);
				}
			}

		} catch(Exception e) {
			if (ConfigMMsg.getConfig().getBoolean("Warp.No-Warp.Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList("Warp.No-Warp.Messages")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				}
			}
		}
		
		Main.player_spawnwarpdelay.remove(p.getUniqueId());
		Main.inspawnd.remove(p);
		Main.inwarpd.remove(p);
	}

}