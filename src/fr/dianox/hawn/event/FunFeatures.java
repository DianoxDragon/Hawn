package fr.dianox.hawn.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.command.commands.FlyCommand;
import fr.dianox.hawn.modules.chat.emojis.ChatEmojisLoad;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.ConfigPlayerGet;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.XSound;
import fr.dianox.hawn.utility.config.configs.PlayerOptionMainConfig;
import fr.dianox.hawn.utility.config.configs.commands.FlyCommandConfig;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.ConfigFDoubleJump;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.ConfigGLP;
import fr.dianox.hawn.utility.config.configs.events.OtherFeaturesConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import fr.dianox.hawn.utility.world.CosmeticsPW;
import fr.dianox.hawn.utility.world.OtherFeaturesPW;
import fr.dianox.hawn.utility.world.PlayerEventsPW;
import net.md_5.bungee.api.ChatColor;

public class FunFeatures implements Listener {
	
	public static List<Player> incooldownjumppads = new ArrayList<Player>();;
	public static HashMap<Player, ItemStack> boots = new HashMap<Player, ItemStack>();
	public static List<Player> player_list_dbenable = new ArrayList<Player>();
	
	@EventHandler
	public void onSign(SignChangeEvent e) {
		Player p = e.getPlayer();
		
		if (OtherFeaturesConfig.getConfig().getBoolean("ColorSign.Enable")) {
			if (!OtherFeaturesConfig.getConfig().getBoolean("ColorSign.World.All_World")) {
				if (!OtherFeaturesPW.getWColorSign().contains(p.getWorld().getName())) {
					return;
				}
			}

			if (p.hasPermission("hawn.sign.color")) {
				for (int i = 0; i <= 3; i++) {
					String line = e.getLine(i);
					
					line = ChatColor.translateAlternateColorCodes('&', line);
					e.setLine(i, line);
				}
			}			
		}
	}
	
	@SuppressWarnings("rawtypes")
	@EventHandler
	public void onSignEmojis(SignChangeEvent e) {
		if (OtherFeaturesConfig.getConfig().getBoolean("EmojiSign.Enable")) {
			
			if (!OtherFeaturesConfig.getConfig().getBoolean("EmojiSign.World.All_World")) {
				if (!OtherFeaturesPW.getWEmojiSign().contains(e.getPlayer().getWorld().getName())) {
					return;
				}
			}
			
			if (e.getPlayer().hasPermission("hawn.sign.emoji")) {
				for (int i = 0; i <= 3; i++) {
					String line = e.getLine(i);
					Iterator it = ChatEmojisLoad.emojislist.entrySet().iterator();
					
					while (it.hasNext()) {
						Map.Entry pair = (Map.Entry)it.next();
						 
						String check = String.valueOf(pair.getKey());
						String value = String.valueOf(pair.getValue());
						 
						if (ChatEmojisLoad.emojislistperm.containsKey(check)) {
							if (!e.getPlayer().hasPermission(ChatEmojisLoad.emojislistperm.get(check))) {
								continue;
							}
						}
	
						if (line.toLowerCase().contains(check.toLowerCase())) {
							line = line.replaceAll(check, value);
							e.setLine(i, line);
						}
					}
				}
			}
		}
	}
	
	// JumpPads
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		if (Main.injumpwithjumppad.contains(p)) {
			return;
		}

		if (ConfigGLP.getConfig().getBoolean("JumpPads.Enable")) {
			if (!ConfigGLP.getConfig().getBoolean("JumpPads.World.All_World")) {
				if (CosmeticsPW.getWJumpPads().contains(p.getWorld().getName())) {
					if (ConfigGLP.getConfig().getBoolean("JumpPads.Use_Permission")) {
						if (p.hasPermission("hawn.fun.jumppads")) {
							onLPmethod(p);
						}
					} else {
						onLPmethod(p);
					}
				}	
			} else {
				if (ConfigGLP.getConfig().getBoolean("JumpPads.Use_Permission")) {
					if (p.hasPermission("hawn.fun.jumppads")) {
						onLPmethod(p);
					}
				} else {
					onLPmethod(p);
				}
			}
		}
	}
		
		@EventHandler
		public void onPM2(PlayerMoveEvent e) {
			
			Player p = e.getPlayer();
			
			if (PlayerOptionMainConfig.getConfig().getBoolean("Options.Flying.Put-boots") && p.hasPermission("hawn.fun.boots.flying")) {
				if (p.isFlying()) {
					try {
						if (p.getInventory().getBoots().getType() != XMaterial.DIAMOND_BOOTS.parseMaterial()) {
							boots.put(p, p.getInventory().getBoots());
							
							ItemStack bootsflying = new ItemStack(XMaterial.DIAMOND_BOOTS.parseMaterial());
							ItemMeta bootsmeta = bootsflying.getItemMeta();
							
							bootsmeta.setDisplayName("§eI'm flyyyyinggggggg");
							
							bootsflying.setItemMeta(bootsmeta);
							p.getInventory().setBoots(bootsflying);
						}
					} catch (Exception e1) {
						boots.put(p, p.getInventory().getBoots());
						
						ItemStack bootsflying = new ItemStack(XMaterial.DIAMOND_BOOTS.parseMaterial());
						ItemMeta bootsmeta = bootsflying.getItemMeta();
						
						bootsmeta.setDisplayName("§eI'm flyyyyinggggggg");
						
						bootsflying.setItemMeta(bootsmeta);
						p.getInventory().setBoots(bootsflying);
					}
				} else {
					try {
						if (p.getInventory().getBoots().getType() == XMaterial.DIAMOND_BOOTS.parseMaterial()) {
							if (p.getInventory().getBoots().getItemMeta().getDisplayName().contains("§eI'm flyyyyinggggggg")) {
								p.getInventory().setBoots(null);
								p.getInventory().setBoots(boots.get(p));
							}
						}
					} catch (Exception e2) {}
				}
			}
			
			if (FlyCommand.player_list_flyc.contains(p)) {
				if (!FlyCommandConfig.getConfig().getBoolean("Fly.Enable")) {
					FlyCommand.player_list_flyc.remove(p);
					p.setAllowFlight(false);
					p.setFlying(false);
				}
			}
			
			if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Enable")) {
				if (ConfigPlayerGet.getFile(p.getUniqueId().toString()).getBoolean("player_option_fly.Activate") || !ConfigPlayerGet.getFile(p.getUniqueId().toString()).getBoolean("player_option_doublejump.Activate")) {
					if (player_list_dbenable.contains(p)) {
						player_list_dbenable.remove(p);
					}
					return;
				}
				
				if (Main.indj.contains(p)) {
					return;
				}
				
				if (p.hasPermission("hawn.fun.doublejump.double")) {		
					if (p.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() != Material.AIR) {
						player_list_dbenable.remove(p);
						p.setAllowFlight(true);
					}
				}
			}
		}
		
		@EventHandler
		public void onInfiniteJump(PlayerToggleFlightEvent e) {
			Player p = e.getPlayer();
			
			if (FlyCommand.player_list_flyc.contains(p)) {
				if (!FlyCommandConfig.getConfig().getBoolean("Fly.Enable")) {
					FlyCommand.player_list_flyc.remove(p);
					p.setAllowFlight(false);
					p.setFlying(false);
				}
				return;
			}
			
			if (player_list_dbenable.contains(p)) {
				return;
			}
			
			if (Main.indj.contains(p)) {
				return;
			}
			
			if (ConfigPlayerGet.getFile(p.getUniqueId().toString()).getBoolean("player_option_fly.Activate") || !ConfigPlayerGet.getFile(p.getUniqueId().toString()).getBoolean("player_option_doublejump.Activate")) {
				return;
			}
			
			if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) {
				return;
			}
			
			if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Enable")) {
				if (!ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.World.All_World")) {
					if (PlayerEventsPW.getWFDoubleJump().contains(p.getWorld().getName())) {
						if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Use_Permission")) {
							if (p.hasPermission("hawn.fun.doublejump.double")) {			
								player_list_dbenable.add(p);

								e.setCancelled(true);
								
								p.setAllowFlight(false);
							    p.setFlying(false);
							    p.setVelocity(p.getLocation().getDirection().multiply(1.5D).setY(1));
							    p.setFallDistance(-999.0F);
							    
							    Main.indj.add(p);
							    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

									@Override
									public void run() {
										Main.indj.remove(p);
									}

								}, 20);
							    
							    if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Sounds.Enable")) {
							    	String sound = ConfigFDoubleJump.getConfig().getString("DoubleJump.Double.Sounds.Sound");
					            	int volume = ConfigFDoubleJump.getConfig().getInt("DoubleJump.Double.Sounds.Volume");
					            	int pitch = ConfigFDoubleJump.getConfig().getInt("DoubleJump.Double.Sounds.Pitch");
					            	p.playSound(p.getLocation(), XSound.getSound(sound, "DoubleJump.Double.Sounds.Sound"), volume, pitch);
							    }
							}
						} else {
							player_list_dbenable.add(p);
							
							
							e.setCancelled(true);
							
							p.setAllowFlight(false);
						    p.setFlying(false);
						    p.setVelocity(p.getLocation().getDirection().multiply(1.5D).setY(1));
						    p.setFallDistance(-999.0F);
						    
						    Main.indj.add(p);
						    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

								@Override
								public void run() {
									Main.indj.remove(p);
								}

							}, 20);
						    
						    if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Sounds.Enable")) {
						    	String sound = ConfigFDoubleJump.getConfig().getString("DoubleJump.Double.Sounds.Sound");
				            	int volume = ConfigFDoubleJump.getConfig().getInt("DoubleJump.Double.Sounds.Volume");
				            	int pitch = ConfigFDoubleJump.getConfig().getInt("DoubleJump.Double.Sounds.Pitch");
				            	p.playSound(p.getLocation(), XSound.getSound(sound, "DoubleJump.Double.Sounds.Sound"), volume, pitch);
						    }
						}
						
					}
				} else {
					if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Use_Permission")) {
						if (p.hasPermission("hawn.fun.doublejump.double")) {
							player_list_dbenable.add(p);
							
							
							e.setCancelled(true);
							
							p.setAllowFlight(false);
						    p.setFlying(false);
						    p.setVelocity(p.getLocation().getDirection().multiply(1.5D).setY(1));
						    p.setFallDistance(-999.0F);
						    
						    Main.indj.add(p);
						    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

								@Override
								public void run() {
									Main.indj.remove(p);
								}

							}, 20);
						    
						    if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Sounds.Enable")) {
						    	String sound = ConfigFDoubleJump.getConfig().getString("DoubleJump.Double.Sounds.Sound");
				            	int volume = ConfigFDoubleJump.getConfig().getInt("DoubleJump.Double.Sounds.Volume");
				            	int pitch = ConfigFDoubleJump.getConfig().getInt("DoubleJump.Double.Sounds.Pitch");
				            	p.playSound(p.getLocation(), XSound.getSound(sound, "DoubleJump.Double.Sounds.Sound"), volume, pitch);
						    }
						}
					} else {
						player_list_dbenable.add(p);
						
						e.setCancelled(true);
						
						p.setAllowFlight(false);
					    p.setFlying(false);
					    p.setVelocity(p.getLocation().getDirection().multiply(1.5D).setY(1));
					    p.setFallDistance(-999.0F);
					    
					    Main.indj.add(p);
					    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

							@Override
							public void run() {
								Main.indj.remove(p);
							}

						}, 20);
					    
					    if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Sounds.Enable")) {
					    	String sound = ConfigFDoubleJump.getConfig().getString("DoubleJump.Double.Sounds.Sound");
			            	int volume = ConfigFDoubleJump.getConfig().getInt("DoubleJump.Double.Sounds.Volume");
			            	int pitch = ConfigFDoubleJump.getConfig().getInt("DoubleJump.Double.Sounds.Pitch");
			            	p.playSound(p.getLocation(), XSound.getSound(sound, "DoubleJump.Double.Sounds.Sound"), volume, pitch);
					    }
					}
				}
			}
		}
		
		@SuppressWarnings("deprecation")
		public void onLPmethod(Player p) {
			Main.getInstance();
			try {
				Material block = XMaterial.getMat(ConfigGLP.getConfig().getString("JumpPads.Options.Block"), "JumpPads.Options.Block");
				Material plate = XMaterial.getMat(ConfigGLP.getConfig().getString("JumpPads.Options.Plate"), "JumpPads.Options.Plate");
				
				if ((p.getLocation().getBlock().getType() == plate) && (p.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() == block)) {
					
					if (incooldownjumppads.contains(p)) {
						Main.injumpwithjumppad.add(p);
						
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

							@Override
							public void run() {
								Main.injumpwithjumppad.remove(p);
							}

						}, 20);
						
						if (ConfigMMsg.getConfig().getBoolean("LaunchPad.Cant-Use-Cooldown.Enable")) {
							for (String s: ConfigMMsg.getConfig().getStringList("LaunchPad.Cant-Use-Cooldown.Messages")) {
								ConfigEventUtils.ExecuteEvent(p, s, "", "", false);
							}
						}
						
						return;
					}
					
					double height = ConfigGLP.getConfig().getDouble("JumpPads.Options.Height");
					double length = ConfigGLP.getConfig().getDouble("JumpPads.Options.Length");
					p.setVelocity(p.getLocation().getDirection().multiply(length).setY(height));
					p.setFallDistance(-999.0F);
					
					String sound = ConfigGLP.getConfig().getString("JumpPads.Sounds.Sound");
					int volume = ConfigGLP.getConfig().getInt("JumpPads.Sounds.Volume");
					int pitch = ConfigGLP.getConfig().getInt("JumpPads.Sounds.Pitch");
					if (ConfigGLP.getConfig().getBoolean("JumpPads.Sounds.Enable")) {
						if (ConfigGLP.getConfig().getBoolean("JumpPads.Sounds.Play-for-all-players")) {
							for (Player all: Bukkit.getServer().getOnlinePlayers()) {
								all.playSound(p.getLocation(), XSound.getSound(sound, "JumpPads.Sounds.Sound"), volume, pitch);
							}
						} else {
							p.playSound(p.getLocation(), XSound.getSound(sound, "JumpPads.Sounds.Sound"), volume, pitch);
						}
					}
					
					
					String effect = ConfigGLP.getConfig().getString("JumpPads.Effect.Effect");
					int pitch2 = ConfigGLP.getConfig().getInt("JumpPads.Effect.Pitch");
					if (ConfigGLP.getConfig().getBoolean("JumpPads.Effect.Enable")) {
						p.playEffect(p.getPlayer().getLocation(), Effect.valueOf(effect), pitch2);
					}
					if (ConfigGLP.getConfig().getBoolean("JumpPads.Send-Message.Enable")) {
						for (String s: ConfigGLP.getConfig().getStringList("JumpPads.Send-Message.Messages")) {
							ConfigEventUtils.ExecuteEvent(p, s, "", "", false);
						}
					}
					
					Main.injumpwithjumppad.add(p);
					
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

						@Override
						public void run() {
							Main.injumpwithjumppad.remove(p);
						}

					}, 20);
					
					if (ConfigGLP.getConfig().getBoolean("JumpPads.Cooldown.Enable")) {
						incooldownjumppads.add(p);
						
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

							@Override
							public void run() {
								incooldownjumppads.remove(p);
							}

						}, ConfigGLP.getConfig().getInt("JumpPads.Cooldown.Ticks"));
					}
				}
			} catch (NoClassDefFoundError e) {
				Bukkit.getConsoleSender().sendMessage("§cPLEASE RESTART THE SERVER");
				Bukkit.getConsoleSender().sendMessage("§cHawn didn't loaded correctly, maybe because you reloaded the server");
				Bukkit.getConsoleSender().sendMessage("§cPLEASE RESTART THE SERVER - CRITICAL ERROR");
			}
		}

}
