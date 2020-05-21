package fr.dianox.hawn.utility;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.dianox.hawn.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class BungeeApi implements PluginMessageListener {

	private final Plugin plugin;
	public HashMap<String, Integer> PlayerCountVar = new HashMap<>();
	public List<String> servers = new ArrayList<>();

	public BungeeApi(Plugin plugin) {
		this.plugin = plugin;

		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					for (String s : servers) {
						getCount(s);
					}

					getCount("ALL");
				} catch (Exception ignored) {
					System.out.println("error");
				}
			}
		}.runTaskTimer(plugin, 100, 100);
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord")) {
			return;
		}
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();

		if (subchannel.equals("PlayerCount")) {
			String server = in.readUTF();
			int playerCount = in.readInt();

			if (PlayerCountVar.containsKey(server)) {
				PlayerCountVar.replace(server, playerCount);
			} else {
				PlayerCountVar.put(server, playerCount);
			}
		} else if (subchannel.equals("GetServers")) {
			String[] serverList = in.readUTF().split(", ");
			servers.clear();
			Collections.addAll(servers, serverList);
		}
	}

	public void getCount(String server) {
		if (server == null) {
			server = "ALL";
		}

		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("PlayerCount");
		out.writeUTF(server);

		Bukkit.getServer().sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());
	}

	public void getServers() {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("GetServers");

		Bukkit.getServer().sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());
	}

	public void ConnectOthers(String server, Player p) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("ConnectOther");
		out.writeUTF(p.getName());
		out.writeUTF(server);

		Bukkit.getServer().sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());
	}
}
