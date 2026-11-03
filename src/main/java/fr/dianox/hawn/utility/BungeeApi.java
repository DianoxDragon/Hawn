package fr.dianox.hawn.utility;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.dianox.hawn.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.*;

public class BungeeApi implements PluginMessageListener {

	// Safe for several threads (since 1.5.6)
	public Map<String, Integer> PlayerCountVar = new java.util.concurrent.ConcurrentHashMap<>();
	public List<String> servers = new java.util.concurrent.CopyOnWriteArrayList<>();

	public BungeeApi(Plugin plugin) {
		// Planned through Tasks (since 1.5.6)
		Tasks.timer(() -> {
			try {
				for (String s : servers) {
					getCount(s);
				}

				getCount("ALL");
			} catch (Exception ignored) {
				System.out.println("error");
			}
		}, 100, 100);
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
