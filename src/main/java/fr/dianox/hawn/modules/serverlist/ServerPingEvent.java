package fr.dianox.hawn.modules.serverlist;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.config.configs.ServerListConfig;
import fr.dianox.hawn.utility.config.configs.commands.HawnCommandConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ServerPingEvent implements Listener {

	Integer MaxPlayers = Bukkit.getMaxPlayers();
	
	@EventHandler
	public void ping(ServerListPingEvent e) {
		if (ServerListConfig.getConfig().getBoolean("Slots.One-Slot-Free")) {
			if (e.getNumPlayers() < MaxPlayers) {
				e.setMaxPlayers(e.getNumPlayers() + 1);
			} else {
				e.setMaxPlayers(MaxPlayers);
			}
		} else {
			if (ServerListConfig.getConfig().getBoolean("Slots.Fake-Max-Player.Enable")) {
				e.setMaxPlayers(ServerListConfig.getConfig().getInt("Slots.Fake-Max-Player.Number"));
			} else {
				e.setMaxPlayers(MaxPlayers);
			}
		}
		
		if (ServerListConfig.getConfig().getBoolean("Motd.Classic.Enable")) {
			if (ServerListConfig.getConfig().getBoolean("Motd.Classic.Random")) {
				String msg = "";
				
				Random rand = new Random();
				
				int value = rand.nextInt(Main.getInstance().getServerListManager().getMotd_total_sl()+1);
				msg = String.valueOf(Main.getInstance().getServerListManager().getMotd_sl().get(value));
				
				String line1 = ServerListConfig.getConfig().getString("Motd.Classic.Random-List."+msg+".Line-1");
				String line2 = ServerListConfig.getConfig().getString("Motd.Classic.Random-List."+msg+".Line-2");
				
				line1 = MessageUtils.colourTheStuff(line1);
				line2 = MessageUtils.colourTheStuff(line2);
				
				line1 = PlaceHolders.ReplaceMainplaceholderC(line1);
				line2 = PlaceHolders.ReplaceMainplaceholderC(line2);
				
				e.setMotd(String.valueOf(line1) + "\n" + line2);
			} else {
				String line1 = ServerListConfig.getConfig().getString("Motd.Classic.Main.Line-1");
				String line2 = ServerListConfig.getConfig().getString("Motd.Classic.Main.Line-2");
					
				line1 = MessageUtils.colourTheStuff(line1);
				line2 = MessageUtils.colourTheStuff(line2);
				
				line1 = PlaceHolders.ReplaceMainplaceholderC(line1);
				line2 = PlaceHolders.ReplaceMainplaceholderC(line2);
					
				e.setMotd(String.valueOf(line1) + "\n" + line2);
			}
		}
		
		if (Bukkit.hasWhitelist()) {
			if (ServerListConfig.getConfig().getBoolean("Motd.WhiteList.Enable")) {
				String line1 = ServerListConfig.getConfig().getString("Motd.WhiteList.Line-1");
				String line2 = ServerListConfig.getConfig().getString("Motd.WhiteList.Line-2");
				
				line1 = MessageUtils.colourTheStuff(line1);
				line2 = MessageUtils.colourTheStuff(line2);
				
				line1 = PlaceHolders.ReplaceMainplaceholderC(line1);
				line2 = PlaceHolders.ReplaceMainplaceholderC(line2);
				
				e.setMotd(String.valueOf(line1) + "\n" + line2);
			}
		}
		
		if (HawnCommandConfig.getConfig().getBoolean("Maintenance.Enable") && ServerListConfig.getConfig().getBoolean("Motd.Maintenance.Enable")) {
			String line1 = ServerListConfig.getConfig().getString("Motd.Maintenance.Line-1");
			String line2 = ServerListConfig.getConfig().getString("Motd.Maintenance.Line-2");
			
			line1 = MessageUtils.colourTheStuff(line1);
			line2 = MessageUtils.colourTheStuff(line2);
			
			line1 = PlaceHolders.ReplaceMainplaceholderC(line1);
			line2 = PlaceHolders.ReplaceMainplaceholderC(line2);
			
			e.setMotd(String.valueOf(line1) + "\n" + line2);
		}
		
		if (HawnCommandConfig.getConfig().getBoolean("Urgent-mode.Enable") && ServerListConfig.getConfig().getBoolean("Motd.Urgent.Enable")) {
			String line1 = ServerListConfig.getConfig().getString("Motd.Urgent.Line-1");
			String line2 = ServerListConfig.getConfig().getString("Motd.Urgent.Line-2");
			
			line1 = MessageUtils.colourTheStuff(line1);
			line2 = MessageUtils.colourTheStuff(line2);
			
			line1 = PlaceHolders.ReplaceMainplaceholderC(line1);
			line2 = PlaceHolders.ReplaceMainplaceholderC(line2);
			
			e.setMotd(String.valueOf(line1) + "\n" + line2);
		}
	}
	
	/**
	 * What a player can do on join, asked by the login listener (Spigot) or by the Paper connection listener,
	 * which has no Player yet.
	 */
	public interface Joining {
		UUID uuid();
		String name();
		boolean hasPermission(String permission);
	}

	/**
	 * Whitelist of the maintenance ("Maintenance") or of the emergency mode ("Urgent-mode").
	 * The names are compared without case (a UUID works too). The maintenance has a bypass permission,
	 * not the emergency mode: it is made for a hacked staff account.
	 */
	public static boolean canJoin(Joining p, String mode) {
		if (mode.equals("Maintenance") && p.hasPermission("hawn.maintenance.bypass")) {
			return true;
		}

		for (String name: HawnCommandConfig.getConfig().getStringList(mode + ".whitelist")) {
			if (name.equalsIgnoreCase(p.name()) || name.equalsIgnoreCase(p.uuid().toString())) {
				return true;
			}
		}

		return false;
	}

	public static boolean canJoin(Player p, String mode) {
		return canJoin(joining(p), mode);
	}

	public static Joining joining(Player p) {
		return new Joining() {
			public UUID uuid() { return p.getUniqueId(); }
			public String name() { return p.getName(); }
			public boolean hasPermission(String permission) { return p.hasPermission(permission); }
		};
	}

	public static String kickMessage(Player p, String mode) {
		return PlaceHolders.ReplaceMainplaceholderP(kickText(mode), p);
	}

	// Before the join: only the name of the player and the server placeholders
	public static String kickMessage(String name, String mode) {
		return PlaceHolders.ReplaceMainplaceholderC(kickText(mode).replace("%player%", name));
	}

	private static String kickText(String mode) {
		List<String> lines = HawnCommandConfig.getConfig().getStringList(mode + ".Kick-Message");
		String message = lines.isEmpty() ? HawnCommandConfig.getConfig().getString(mode + ".Kick-Message", "") : String.join("\n", lines);
		return MessageUtils.colourTheStuff(message);
	}

	/**
	 * The emergency mode and the maintenance.
	 * @return the kick message, or null when the player can join
	 */
	public static String closedFor(Joining p) {
		String kick = null;

		if (HawnCommandConfig.getConfig().getBoolean("Urgent-mode.Enable") && !canJoin(p, "Urgent-mode")) {
			kick = kickMessage(p.name(), "Urgent-mode");
		}

		if (HawnCommandConfig.getConfig().getBoolean("Maintenance.Enable") && !canJoin(p, "Maintenance")) {
			kick = kickMessage(p.name(), "Maintenance");
		}

		return kick;
	}

	/**
	 * The server is full: true when the player can join anyway (option on and hawn.join.full).
	 */
	public static boolean canJoinFull(Joining p) {
		return ServerListConfig.getConfig().getBoolean("On-Join.Player-With-Permission-Join-Full-Server") && p.hasPermission("hawn.join.full");
	}

	// The message of On-Join.Message, or null to keep the one of the server
	public static String fullMessage(String name) {
		if (!ServerListConfig.getConfig().getBoolean("On-Join.Player-With-Permission-Join-Full-Server")) {
			return null;
		}

		String message = String.join("\n", ServerListConfig.getConfig().getStringList("On-Join.Message"));
		message = MessageUtils.colourTheStuff(message);
		return PlaceHolders.ReplaceMainplaceholderC(message.replace("%player%", name));
	}

	/**
	 * The join on Spigot (and on a Paper without the new connection event). On a recent Paper, PaperLogin does the same.
	 */
	@SuppressWarnings("deprecation")
	public static class SpigotLogin implements Listener {

		@EventHandler(priority = EventPriority.HIGHEST)
		public void login(PlayerLoginEvent e) {
			Joining p = joining(e.getPlayer());

			String kick = closedFor(p);
			if (kick != null) {
				e.disallow(PlayerLoginEvent.Result.KICK_OTHER, kick);
				return;
			}

			if (e.getResult().equals(PlayerLoginEvent.Result.KICK_FULL)) {
				if (canJoinFull(p)) {
					e.allow();
				} else {
					String message = fullMessage(p.name());
					if (message != null) {
						e.setKickMessage(message);
					}
				}
			}
		}
	}
}
