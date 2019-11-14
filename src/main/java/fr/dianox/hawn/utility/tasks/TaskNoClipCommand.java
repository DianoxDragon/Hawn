package fr.dianox.hawn.utility.tasks;

import fr.dianox.hawn.commands.HawnCommand;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.messages.administration.OtherAMConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskNoClipCommand extends BukkitRunnable {

	private Player p;

	public TaskNoClipCommand(Player p) {
        this.p = p;
	}
	
	@Override
	public void run() {
		Bukkit.getServer().savePlayers();
		
		if (HawnCommand.noclip.contains(p) && p.isOnline() && (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR)) {
			Boolean Spectator = false;
			
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
				for (String msg: OtherAMConfig.getConfig().getStringList("Command.No-Clip.Disable")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
				
				if (p.getGameMode() == GameMode.ADVENTURE) {
					p.setGameMode(GameMode.ADVENTURE);
				} else if (p.getGameMode() == GameMode.SURVIVAL) {
					p.setGameMode(GameMode.SURVIVAL);
				} else {
					p.setGameMode(GameMode.CREATIVE);
				}
			}
			
			Bukkit.getServer().getScheduler().cancelTask(getTaskId());
		}
	}

}
