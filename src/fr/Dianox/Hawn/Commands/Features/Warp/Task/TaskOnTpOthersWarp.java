package fr.Dianox.Hawn.Commands.Features.Warp.Task;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.WarpListConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;

public class TaskOnTpOthersWarp extends BukkitRunnable {
	
	private Player p, other;
	private String tp;
	
	public TaskOnTpOthersWarp(Player p, Player other, String tp) {
        this.p = p;
        this.tp = tp;
        this.other = other;
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

			other.teleport(new org.bukkit.Location(w, x, y, z, yaw, pitch));

			if (ConfigMCommands.getConfig().getBoolean("Warp.Tp.Other-Sender.Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList("Warp.Tp.Other-Sender.Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%warp%", tp).replaceAll("%target%", other.getName()), p);
				}
			}

			if (ConfigMCommands.getConfig().getBoolean("Warp.Tp.Other.Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList("Warp.Tp.Other.Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%warp%", tp).replaceAll("%player%", p.getName()), other);
				}
			}

		} catch(Exception e) {
			if (ConfigMCommands.getConfig().getBoolean("Warp.No-Warp.Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList("Warp.No-Warp.Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
			}
		}
		
		Main.player_spawnwarpdelay.remove(other.getUniqueId());
		Main.inspawnd.remove(p);
		Main.inwarpd.remove(p);
	}


}