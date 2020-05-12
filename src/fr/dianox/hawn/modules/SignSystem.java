package fr.dianox.hawn.modules;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.cosmeticsfun.SignListCUtility;
import fr.dianox.hawn.utility.config.events.OtherFeaturesConfig;
import net.md_5.bungee.api.ChatColor;

public class SignSystem implements Listener {
	
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
						
						SignListCUtility.getConfig().set("Signs." + l.getWorld().getName()  + "." + l.getBlockX() + "." + l.getBlockY() + "." + l.getBlockZ(), keepline);
						SignListCUtility.saveConfigFile();
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onClickSign(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();
		
		try {
			if (b.getType() != XMaterial.OAK_SIGN.parseMaterial() && b.getType() != XMaterial.OAK_WALL_SIGN.parseMaterial() &&
					b.getType() != XMaterial.ACACIA_SIGN.parseMaterial() && b.getType() != XMaterial.ACACIA_WALL_SIGN.parseMaterial() &&
					b.getType() != XMaterial.BIRCH_SIGN.parseMaterial() && b.getType() != XMaterial.BIRCH_WALL_SIGN.parseMaterial() &&
					b.getType() != XMaterial.DARK_OAK_SIGN.parseMaterial() && b.getType() != XMaterial.DARK_OAK_WALL_SIGN.parseMaterial() &&
					b.getType() != XMaterial.JUNGLE_SIGN.parseMaterial() && b.getType() != XMaterial.JUNGLE_WALL_SIGN.parseMaterial() &&
					b.getType() != XMaterial.SPRUCE_SIGN.parseMaterial() && b.getType() != XMaterial.SPRUCE_WALL_SIGN.parseMaterial()) {
				return;
			}
			
			if (OtherFeaturesConfig.getConfig().getBoolean("SignSystem.Enable")) {
				Location l = b.getLocation();
				
				EquipmentSlot es = null;
				
				if (Main.Spigot_Version >= 19) {
					es = e.getHand();
				}
				
				try {
					if (Main.Spigot_Version >= 19) {
						if (es.equals(EquipmentSlot.HAND)) {
							if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
								if (SignListCUtility.getConfig().isSet("Signs." + l.getWorld().getName()  + "." + l.getBlockX() + "." + l.getBlockY() + "." + l.getBlockZ())) {
									String sign = SignListCUtility.getConfig().getString("Signs." + l.getWorld().getName()  + "." + l.getBlockX() + "." + l.getBlockY() + "." + l.getBlockZ());
									
									if (p.hasPermission("hawn.sign.interact." + sign)) {
										if (SignListCUtility.getConfig().isSet("Sign-List." + sign + ".Event")) {
											for (String msg: SignListCUtility.getConfig().getStringList("Sign-List." + sign + ".Event")) {
						                		ConfigEventUtils.ExecuteEvent(p, msg, "SignSystem", sign, false);
						                	}
										}
									}
								}
							}
						}
					} else {
						if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
							if (SignListCUtility.getConfig().isSet("Signs." + l.getWorld().getName()  + "." + l.getBlockX() + "." + l.getBlockY() + "." + l.getBlockZ())) {
								String sign = SignListCUtility.getConfig().getString("Signs." + l.getWorld().getName()  + "." + l.getBlockX() + "." + l.getBlockY() + "." + l.getBlockZ());
								
								if (p.hasPermission("hawn.sign.interact." + sign)) {
									if (SignListCUtility.getConfig().isSet("Sign-List." + sign + ".Event")) {
										for (String msg: SignListCUtility.getConfig().getStringList("Sign-List." + sign + ".Event")) {
					                		ConfigEventUtils.ExecuteEvent(p, msg, "SignSystem", sign, false);
					                	}
									}
								}
							}
						}
					}
				} catch (Exception e1) {}
			}
		} catch (Exception e3) {}
	}
	
	@EventHandler
	public void onDestroySign(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		
		if (OtherFeaturesConfig.getConfig().getBoolean("SignSystem.Enable")) {
			Location l = b.getLocation();
			
			if (b.getType() == XMaterial.OAK_SIGN.parseMaterial() || b.getType() == XMaterial.OAK_WALL_SIGN.parseMaterial() ||
					b.getType() == XMaterial.ACACIA_SIGN.parseMaterial() || b.getType() == XMaterial.ACACIA_WALL_SIGN.parseMaterial() ||
					b.getType() == XMaterial.BIRCH_SIGN.parseMaterial() || b.getType() == XMaterial.BIRCH_WALL_SIGN.parseMaterial() ||
					b.getType() == XMaterial.DARK_OAK_SIGN.parseMaterial() || b.getType() == XMaterial.DARK_OAK_WALL_SIGN.parseMaterial() ||
					b.getType() == XMaterial.JUNGLE_SIGN.parseMaterial() || b.getType() == XMaterial.JUNGLE_WALL_SIGN.parseMaterial() ||
					b.getType() == XMaterial.SPRUCE_SIGN.parseMaterial() || b.getType() == XMaterial.SPRUCE_WALL_SIGN.parseMaterial()) {
				if (SignListCUtility.getConfig().isSet("Signs." + l.getWorld().getName()  + "." + l.getBlockX() + "." + l.getBlockY() + "." + l.getBlockZ())) {
					if (!p.hasPermission("hawn.sign.delete")) {
						e.setCancelled(true);
						return;
					}
					
					SignListCUtility.getConfig().set("Signs." + l.getWorld().getName()  + "." + l.getBlockX() + "." + l.getBlockY() + "." + l.getBlockZ(), null);
					SignListCUtility.saveConfigFile();
				}
			}
		}
	}

}
