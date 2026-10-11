package fr.dianox.hawn.command.commands.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.config.configs.WarpListConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;


public class TaskOnTPWarp extends BukkitRunnable {
	
	private Player p;
	private String tp;
	
	public TaskOnTPWarp(Player p, String tp) {
        this.p = p;
        this.tp = tp;
	}

	@Override
	public void run() {
		// 50: done, refused or failed, the player teleported is no longer waiting
		try {
			teleport();
		} finally {
			Main.endTeleportDelay(p);
		}
	}

	private void teleport() {
		if (!p.isOnline()) {
			return;
		}
		
		try {
			org.bukkit.World w = org.bukkit.Bukkit.getServer().getWorld(WarpListConfig.getConfig().getString("Coordinated." + tp + ".World"));
			double x = WarpListConfig.getConfig().getDouble("Coordinated." + tp + ".X");
			double y = WarpListConfig.getConfig().getDouble("Coordinated." + tp + ".Y");
			double z = WarpListConfig.getConfig().getDouble("Coordinated." + tp + ".Z");
			float yaw = (float) WarpListConfig.getConfig().getDouble("Coordinated." + tp + ".Yaw");
			float pitch = (float) WarpListConfig.getConfig().getDouble("Coordinated." + tp + ".Pitch");

			p.teleport(new org.bukkit.Location(w, x, y, z, yaw, pitch));

			if (ConfigMMsg.getConfig().getBoolean("Warp.Tp.Self.Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList("Warp.Tp.Self.Messages")) {
					ConfigEventUtils.ExecuteEvent(p, msg.replace("%warp%", ConfigEventUtils.noAction(tp)), "", "", false);
				}
			}

		} catch(Exception e) {
			if (ConfigMMsg.getConfig().getBoolean("Warp.No-Warp.Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList("Warp.No-Warp.Messages")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				}
			}
		}
		
	}

}