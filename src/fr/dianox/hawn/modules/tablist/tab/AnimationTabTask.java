package fr.dianox.hawn.modules.tablist.tab;

import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.config.configs.tab.TablistConfig;

public class AnimationTabTask extends BukkitRunnable {

	private String anim;
	
	public AnimationTabTask(String anim) {
		this.anim = anim;
	}
	
	@SuppressWarnings("unused")
	@Override
	public void run() {
		if (!Main.getInstance().getTabManager().animationtab.containsKey(anim)) {
			Main.getInstance().getTabManager().animationtab.put(anim, 0);
		}
				
		try {
			Integer numberline = Main.getInstance().getTabManager().animationtab.get(anim);
			String animation = TablistConfig.getConfig().getStringList("Animations." + anim + ".text").get(numberline);
			
			try {
				numberline++;
				String animation2 = TablistConfig.getConfig().getStringList("Animations." + anim + ".text").get(numberline);
				Main.getInstance().getTabManager().animationtab.replace(anim, numberline);
			} catch (Exception e) {
				Main.getInstance().getTabManager().animationtab.replace(anim, 0);
			}
		} catch (Exception e) {			
			Main.getInstance().getTabManager().animationtab.replace(anim, 0);
		}
	}

}
