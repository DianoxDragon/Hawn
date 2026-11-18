package fr.dianox.hawn.command.commands.tools;

import fr.dianox.hawn.command.commands.tab.Tab;
import fr.dianox.hawn.utility.MessageUtils;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

/**
 * /editsign &lt;1-4&gt; [text] (since 1.6.2): a line of the sign the player looks at (5 blocks), with colours. No text
 * clears the line. On 1.20+, the front of the sign.
 */
public class EditSignCommand extends ToolCommand {

	public EditSignCommand(String name) {
		super(name, "EditSign", "hawn.command.editsign", "/editsign <1-4> [text]", "Changes a line of a sign", true);
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
		if (args.length == 1) return Tab.of(args, "1", "2", "3", "4");
		if (args.length == 2 && sender instanceof Player) {
			Sign sign = target((Player) sender);
			try {
				int line = Integer.parseInt(args[0]);
				if (sign != null && line >= 1 && line <= 4 && !sign.getLine(line - 1).isEmpty()) {
					return Tab.of(args, sign.getLine(line - 1).replace('§', '&'));
				}
			} catch (NumberFormatException ignored) {
				// no suggestion
			}
		}
		return Tab.none();
	}

	@Override
	void run(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		Integer line = args.length == 0 ? null : number(args[0]);
		if (line == null) {
			usage(p);
			return;
		}
		if (line < 1 || line > 4) {
			say(p, "EditSign.Wrong-Line");
			return;
		}

		Sign sign = target(p);
		if (sign == null) {
			say(p, "EditSign.No-Sign");
			return;
		}

		String text = MessageUtils.colourTheStuff(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
		sign.setLine(line - 1, text);
		sign.update(true, false);
		say(p, "EditSign.Done", "%line%", String.valueOf(line), "%text%", text);
	}

	private static Sign target(Player p) {
		Block block = p.getTargetBlockExact(5);
		if (block == null) return null;
		BlockState state = block.getState();
		return state instanceof Sign ? (Sign) state : null;
	}
}
