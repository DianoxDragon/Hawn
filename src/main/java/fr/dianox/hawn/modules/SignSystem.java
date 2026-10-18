package fr.dianox.hawn.modules;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.SignListCUtility;
import fr.dianox.hawn.utility.config.configs.events.OtherFeaturesConfig;
import net.md_5.bungee.api.ChatColor;

public class SignSystem implements Listener {

	// On the sign: the name of its model in Sign-List.yml
	private static final NamespacedKey KEY = new NamespacedKey(Main.getInstance(), "sign");

	@EventHandler
	public void onSign(SignChangeEvent e) {
		Player p = e.getPlayer();
				
		if (OtherFeaturesConfig.getConfig().getBoolean("SignSystem.Enable")) {
			if (p.hasPermission("hawn.sign.command")) {
				String line = e.getLine(0);
				
				if (line.startsWith("[SS-")) {
					line = line.replace("[SS-", "");
					line = line.replace("]", "");
					
					String keepline = line;
					
					if (SignListCUtility.getConfig().isSet("Sign-List." + keepline)) {
						for (int i = 0; i <= 3; i++) {
							try {
								line = SignListCUtility.getConfig().getStringList("Sign-List." + keepline + ".Text").get(i);
							} catch (Exception e1) {
								line = " ";
							}
														
							line = ChatColor.translateAlternateColorCodes('&', line);
							
							e.setLine(i, line);
						}
						
						Location l = e.getBlock().getLocation();
						
						SignListCUtility.getConfig().set(path(l), keepline);
						SignListCUtility.saveConfigFile();

						// The mark after the event: the sign is written with its text then
						Block block = e.getBlock();
						Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
							if (isSign(block.getType())) mark(block, keepline);
						});
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onClickSign(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();

		if (b == null || !isSign(b.getType()) || e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		if (!OtherFeaturesConfig.getConfig().getBoolean("SignSystem.Enable")) {
			return;
		}

		if (Main.getInstance().getVersionUtils().getSpigot_Version() >= 19 && e.getHand() != EquipmentSlot.HAND) {
			return;
		}

		try {
			String path = path(b.getLocation());
			String sign = SignListCUtility.getConfig().getString(path);

			if (sign == null || !stillThere(b, sign, path)) {
				return;
			}

			if (p.hasPermission("hawn.sign.interact." + sign)) {
				if (SignListCUtility.getConfig().isSet("Sign-List." + sign + ".Event")) {
					for (String msg: SignListCUtility.getConfig().getStringList("Sign-List." + sign + ".Event")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "SignSystem", sign, false);
					}
				}
			}
		} catch (Exception ignored) {
			fr.dianox.hawn.utility.Debug.hidden("Signs: the actions of a sign", ignored);
		}
	}

	/**
	 * The sign at this place is still the one made by Hawn: a sign put there later, after an explosion, WorldEdit or
	 * a piston, doesn't run the actions. The place is then forgotten.
	 */
	private static boolean stillThere(Block b, String sign, String path) {
		BlockState state = b.getState();
		if (!(state instanceof TileState)) {
			return true;
		}

		String tag = ((TileState) state).getPersistentDataContainer().get(KEY, PersistentDataType.STRING);
		boolean same = tag != null ? tag.equals(sign) : state instanceof Sign && sameText((Sign) state, sign);

		if (same && tag == null) {
			// A sign made before Hawn 1.4.6, recognised by its text: marked now
			mark(b, sign);
		} else if (!same) {
			SignListCUtility.getConfig().set(path, null);
			SignListCUtility.saveConfigFile();
			Main.getInstance().getLogger().info("The sign " + sign + " at " + path.substring("Signs.".length())
					+ " is no longer there (another sign stands at this place): forgotten.");
		}
		return same;
	}

	private static boolean sameText(Sign state, String sign) {
		java.util.List<String> text = SignListCUtility.getConfig().getStringList("Sign-List." + sign + ".Text");
		for (int i = 0; i <= 3; i++) {
			String expected = i < text.size() ? text.get(i) : " ";
			if (!clean(ChatColor.translateAlternateColorCodes('&', expected)).equals(clean(state.getLine(i)))) {
				return false;
			}
		}
		return true;
	}

	private static String clean(String line) {
		return line == null ? "" : ChatColor.stripColor(line).trim();
	}

	private static void mark(Block b, String sign) {
		BlockState state = b.getState();
		if (state instanceof TileState) {
			((TileState) state).getPersistentDataContainer().set(KEY, PersistentDataType.STRING, sign);
			state.update();
		}
	}

	private static String path(Location l) {
		return "Signs." + l.getWorld().getName()  + "." + l.getBlockX() + "." + l.getBlockY() + "." + l.getBlockZ();
	}

	@EventHandler
	public void onDestroySign(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		
		if (OtherFeaturesConfig.getConfig().getBoolean("SignSystem.Enable")) {
			Location l = b.getLocation();
			
			if (isSign(b.getType())) {
				if (SignListCUtility.getConfig().isSet(path(l))) {
					if (!p.hasPermission("hawn.sign.delete")) {
						e.setCancelled(true);
						return;
					}
					
					SignListCUtility.getConfig().set(path(l), null);
					SignListCUtility.saveConfigFile();
				}
			}
		}
	}


	// Every kind of sign: all the woods, wall signs and hanging signs
	private static boolean isSign(Material type) {
		return type.name().endsWith("_SIGN");
	}
}
