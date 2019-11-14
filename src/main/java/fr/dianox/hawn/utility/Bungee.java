package fr.dianox.hawn.utility;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.dianox.hawn.Main;
import org.bukkit.entity.Player;

public class Bungee {

	public static void changeServer(Player player, String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("ConnectOther");
		out.writeUTF(player.getName());
		out.writeUTF(server);
		
		player.sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());
	}
	
	public static String getServerIP(String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("ServerIP");
		out.writeUTF(server);
		
		ByteArrayDataInput in = ByteStreams.newDataInput(out.toByteArray());
		String ip = in.readUTF();
		return ip;
	}
	
	public static int getServerIPPort(String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("ServerIP");
		out.writeUTF(server);
		
		ByteArrayDataInput in = ByteStreams.newDataInput(out.toByteArray());
		int port = in.readUnsignedShort();
		return port;
	}
	
}
