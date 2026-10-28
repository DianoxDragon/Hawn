package fr.dianox.hawn.utility.tasks;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import fr.dianox.hawn.command.commands.HawnCommand;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.Tasks;
import com.cryptomorin.xseries.XMaterial;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMAdmin;

// Planned through Tasks (since 1.5.5): it stops itself with its own handle
public class TaskNoClipCommand implements Runnable {

	private final Player p;
	private BukkitTask task;

	public TaskNoClipCommand(Player p) {
        this.p = p;
	}

	/**
	 * The no clip of a player, every 2 ticks until it is turned off.
	 */
	public static BukkitTask start(Player p) {
		TaskNoClipCommand runnable = new TaskNoClipCommand(p);
		runnable.task = Tasks.timer(runnable, 5, 2);
		return runnable.task;
	}
	
	@Override
	public void run() {
		// The players are no longer saved here every 2 ticks (since 1.5.5)
		if (HawnCommand.noclip.contains(p) && p.isOnline() && (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR)) {
			boolean Spectator = false;
			
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 2; y++) {
					for (int z = -1; z <= 1; z++) {
						Block b = p.getLocation().getBlock().getRelative(x, y, z);
						if (b.getType() != XMaterial.AIR.parseMaterial() &&
								b.getType() != XMaterial.WATER.parseMaterial() && 
								b.getType() != XMaterial.LAVA.parseMaterial()) {
							p.setGameMode(GameMode.SPECTATOR);
							Spectator = true;
						} 
					}
				}
			}
			
			if (!Spectator) {
				p.setGameMode(GameMode.CREATIVE);
			}
			
		} else {		
			HawnCommand.noclip.remove(p);
			
			if (p.isOnline()) {
				for (String msg: ConfigMAdmin.getConfig().getStringList("Command.No-Clip.Disable")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "Command.No-Clip.Disable", "TaskNoClipCommand", false);
				}
				
				if (p.getGameMode() == GameMode.ADVENTURE) {
					p.setGameMode(GameMode.ADVENTURE);
				} else if (p.getGameMode() == GameMode.SURVIVAL) {
					p.setGameMode(GameMode.SURVIVAL);
				} else {
					p.setGameMode(GameMode.CREATIVE);
				}
			}
			
			Tasks.cancel(task);
		}
	}

}
