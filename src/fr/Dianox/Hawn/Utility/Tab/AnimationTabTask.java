package fr.Dianox.Hawn.Utility.Tab;

import org.bukkit.scheduler.BukkitRunnable;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.Config.Tab.TablistConfig;

public class AnimationTabTask extends BukkitRunnable {

	private String anim;
	
	public AnimationTabTask(String anim) {
		this.anim = anim;
	}
	
	@SuppressWarnings("unused")
	@Override
	public void run() {
		if (!Main.animationtab.containsKey(anim)) {
			Main.animationtab.put(anim, 0);
		}
				
		try {
			Integer numberline = Main.animationtab.get(anim);
			String animation = TablistConfig.getConfig().getStringList("Animations." + anim + ".text").get(numberline);
			
			try {
				numberline++;
				String animation2 = TablistConfig.getConfig().getStringList("Animations." + anim + ".text").get(numberline);
				Main.animationtab.replace(anim, numberline);
			} catch (Exception e) {
				Main.animationtab.replace(anim, 0);
			}
		} catch (Exception e) {			
			Main.animationtab.replace(anim, 0);
		}
	}

}
