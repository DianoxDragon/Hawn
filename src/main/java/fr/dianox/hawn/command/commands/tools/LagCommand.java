package fr.dianox.hawn.command.commands.tools;

import fr.dianox.hawn.command.commands.tab.Tab;
import fr.dianox.hawn.utility.server.Tps;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

/**
 * /lag (since 1.6.2; /tps is the command of Spigot and Paper): the ticks per second of the last 1, 5 and 15 minutes (Paper), or of the last seconds (Spigot),
 * and the memory used.
 */
public class LagCommand extends ToolCommand {

	public LagCommand(String name) {
		super(name, "Lag", "hawn.command.lag", "/lag", "The TPS and the memory of the server", false);
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
		return Tab.none();
	}

	@Override
	void run(CommandSender sender, String[] args) {
		double[] tps = paperTps();
		String one, five, fifteen;
		if (tps != null && tps.length >= 3) {
			one = format(tps[0]);
			five = format(tps[1]);
			fifteen = format(tps[2]);
		} else {
			// Spigot: the last seconds measured by Hawn
			one = format(Tps.getTPS());
			five = "-";
			fifteen = "-";
		}

		Runtime r = Runtime.getRuntime();
		long used = (r.totalMemory() - r.freeMemory()) / (1024 * 1024);
		long max = r.maxMemory() / (1024 * 1024);

		say(sender, "Lag.Show", "%tps_1m%", one, "%tps_5m%", five, "%tps_15m%", fifteen,
				"%ram_used%", String.valueOf(used), "%ram_max%", String.valueOf(max));
	}

	private static double[] paperTps() {
		try {
			Method m = Bukkit.getServer().getClass().getMethod("getTPS");
			return (double[]) m.invoke(Bukkit.getServer());
		} catch (ReflectiveOperationException | RuntimeException e) {
			return null;
		}
	}

	// Green from 18, yellow from 15, red below, never above 20
	private static String format(double tps) {
		double shown = Math.min(20.0, tps);
		String colour = shown >= 18 ? "&a" : shown >= 15 ? "&e" : "&c";
		return colour + String.format(Locale.ROOT, "%.1f", shown);
	}
}
