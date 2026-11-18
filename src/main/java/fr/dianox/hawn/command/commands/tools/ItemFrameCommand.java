package fr.dianox.hawn.command.commands.tools;

import fr.dianox.hawn.command.commands.tab.Tab;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

import java.util.List;
import java.util.Locale;

/**
 * /itemframe &lt;invisible|visible|fixed|unfixed&gt; (since 1.6.2): the item frame the player looks at (5 blocks) becomes
 * invisible (only its item shows) or fixed (its item can't be taken or turned, it can't be broken).
 */
public class ItemFrameCommand extends ToolCommand {

	private static final String[] MODES = {"invisible", "visible", "fixed", "unfixed"};

	public ItemFrameCommand(String name) {
		super(name, "ItemFrame", "hawn.command.itemframe", "/itemframe <invisible|visible|fixed|unfixed>", "Makes an item frame invisible or fixed", true);
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
		return args.length == 1 ? Tab.of(args, MODES) : Tab.none();
	}

	@Override
	void run(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		String mode = args.length == 1 ? args[0].toLowerCase(Locale.ROOT) : "";
		if (!java.util.Arrays.asList(MODES).contains(mode)) {
			usage(p);
			return;
		}

		ItemFrame frame = target(p);
		if (frame == null) {
			say(p, "ItemFrame.No-Frame");
			return;
		}

		switch (mode) {
			case "invisible": frame.setVisible(false); say(p, "ItemFrame.Invisible"); break;
			case "visible": frame.setVisible(true); say(p, "ItemFrame.Visible"); break;
			case "fixed": frame.setFixed(true); say(p, "ItemFrame.Fixed"); break;
			default: frame.setFixed(false); say(p, "ItemFrame.Unfixed"); break;
		}
	}

	private static ItemFrame target(Player p) {
		Location eye = p.getEyeLocation();
		RayTraceResult hit = p.getWorld().rayTraceEntities(eye, eye.getDirection(), 5.0, 0.2,
				(Entity e) -> e instanceof ItemFrame);
		return hit != null && hit.getHitEntity() instanceof ItemFrame ? (ItemFrame) hit.getHitEntity() : null;
	}
}
