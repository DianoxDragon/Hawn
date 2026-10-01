package fr.dianox.hawn.modules.admin.SetupUtils;

import fr.dianox.hawn.utility.gui.HawnMenu;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.admin.Setup;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import com.cryptomorin.xseries.XMaterial;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.messages.*;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Setup1Language implements Listener {

	private static final HashMap<Integer, String> getlang = new HashMap<>();
	public static final String name = "§cHawn Setup - 1";

	// Main
	public static void OpenInventory(Player p) {
		// General Options
		int size = 54;
		Inventory inv = HawnMenu.create(size, name);

		// Inventory
		File file = new File(Main.getInstance().getDataFolder(), "/Messages");
		File[] files = file.listFiles();
		int slot = 0;

		if (files != null && files.length > 0) {
			for (File filemeep : files) {
				if (filemeep.isDirectory()) {
					inv.setItem(slot, countryflag(filemeep.getName()));
					getlang.put(slot, filemeep.getName());
					slot++;
				}
			}
		}

		inv.setItem(45, item(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupLanguage.Done")), XMaterial.EMERALD_BLOCK.parseMaterial()));
		inv.setItem(46, item(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(47, item(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(48, item(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(49, item(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(50, item(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(51, item(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(52, item(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(53, item(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupLanguage.Close-Inventory")), XMaterial.BARRIER.parseMaterial()));

		p.openInventory(inv);
	}

	private static ItemStack item(String s, Material parseMaterial) {
		ItemStack itemStack = new ItemStack(parseMaterial);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(s);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	// Utils
	public static ItemStack countryflag(String lang) {
		ItemStack itemStack = new ItemStack(XMaterial.WHITE_BANNER.parseMaterial(), 1);
		BannerMeta m = (BannerMeta) itemStack.getItemMeta();

		try {
			List<Pattern> patterns = new ArrayList<>();

			if (lang.startsWith("fr")) {
				patterns.add(new Pattern(DyeColor.BLUE, pattern("HALF_VERTICAL")));
				patterns.add(new Pattern(DyeColor.RED, pattern("HALF_VERTICAL_RIGHT", "HALF_VERTICAL_MIRROR")));
				patterns.add(new Pattern(DyeColor.WHITE, pattern("STRIPE_CENTER")));
			} else if (lang.startsWith("en")) {
				if (lang.contains("en_US")) {
					patterns.add(new Pattern(DyeColor.RED, pattern("BASE")));
					patterns.add(new Pattern(DyeColor.BLUE, pattern("SQUARE_TOP_LEFT")));
					patterns.add(new Pattern(DyeColor.WHITE, pattern("SMALL_STRIPES", "STRIPE_SMALL")));
				} else if (lang.contains("en_UK")) {
					patterns.add(new Pattern(DyeColor.BLUE, pattern("BASE")));
					patterns.add(new Pattern(DyeColor.WHITE, pattern("CROSS")));
					patterns.add(new Pattern(DyeColor.RED, pattern("STRAIGHT_CROSS")));
				}
			} else if (lang.startsWith("ge")) {
				patterns.add(new Pattern(DyeColor.BLACK, pattern("HALF_HORIZONTAL")));
				patterns.add(new Pattern(DyeColor.YELLOW, pattern("HALF_HORIZONTAL_BOTTOM", "HALF_HORIZONTAL_MIRROR")));
				patterns.add(new Pattern(DyeColor.RED, pattern("STRIPE_MIDDLE")));
			} else if (lang.startsWith("el")) {
				patterns.add(new Pattern(DyeColor.BLUE, pattern("BASE")));
				patterns.add(new Pattern(DyeColor.WHITE, pattern("SMALL_STRIPES", "STRIPE_SMALL")));
				patterns.add(new Pattern(DyeColor.BLUE, pattern("SQUARE_TOP_LEFT")));
			} else if (lang.startsWith("es")) {
				patterns.add(new Pattern(DyeColor.RED, pattern("HALF_HORIZONTAL")));
				patterns.add(new Pattern(DyeColor.RED, pattern("HALF_HORIZONTAL_BOTTOM", "HALF_HORIZONTAL_MIRROR")));
				patterns.add(new Pattern(DyeColor.YELLOW, pattern("STRIPE_MIDDLE")));
			} else if (lang.startsWith("fi")) {
				patterns.add(new Pattern(DyeColor.BLUE, pattern("STRAIGHT_CROSS")));
			} else if (lang.startsWith("it")) {
				patterns.add(new Pattern(DyeColor.GREEN, pattern("HALF_VERTICAL")));
				patterns.add(new Pattern(DyeColor.RED, pattern("HALF_VERTICAL_RIGHT", "HALF_VERTICAL_MIRROR")));
				patterns.add(new Pattern(DyeColor.WHITE, pattern("STRIPE_CENTER")));
			} else if (lang.startsWith("ja")) {
				patterns.add(new Pattern(DyeColor.RED, pattern("CIRCLE", "CIRCLE_MIDDLE")));
			} else if (lang.startsWith("ne")) {
				patterns.add(new Pattern(DyeColor.RED, pattern("HALF_HORIZONTAL")));
				patterns.add(new Pattern(DyeColor.BLUE, pattern("HALF_HORIZONTAL_BOTTOM", "HALF_HORIZONTAL_MIRROR")));
				patterns.add(new Pattern(DyeColor.WHITE, pattern("STRIPE_MIDDLE")));
			} else if (lang.startsWith("zh")) {
				patterns.add(new Pattern(DyeColor.RED, pattern("BASE")));
			}
			m.setPatterns(patterns);
		} catch (Exception ignored) {}

		m.setDisplayName("§b" + lang);

		itemStack.setItemMeta(m);

		return itemStack;
	}

	// Interaction
	@EventHandler
	public void onInventory(InventoryClickEvent e) throws IOException {
		if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
		if (e.getCurrentItem() == null) return;
		String inv = e.getWhoClicked().getOpenInventory().getTitle();
		Player p = (Player) e.getWhoClicked();

		if (inv.equals(name)) {
			e.setCancelled(true);
			if (e.getCurrentItem().getType() == XMaterial.AIR.parseMaterial()) return;
			// Ignore the clicks in the player's own inventory
			if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

			if (e.isLeftClick()) {
				if (e.getRawSlot() == 53) {
					e.setCancelled(true);
					Setup.finish(p);
				} else if (e.getRawSlot() == 45) {
					Setup.setupplace = 2;
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Setup2World.OpenInventory(p), 5);
				} else if (e.getCurrentItem().getType() == XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()) {
					e.setCancelled(true);
				} else if (getlang.containsKey(e.getRawSlot())) {
					String langtype = getlang.get(e.getRawSlot());
					langtype = langtype.replace("§b", "");
					ConfigGeneral.getConfig().set("Plugin.Language-Type", langtype);
					ConfigGeneral.saveConfigFile();
					Main.LanguageType = langtype;
					AdminPanelConfig.reloadConfig();
					ConfigMAdmin.reloadConfig();
					ConfigMGeneral.reloadConfig();
					ConfigMMsg.reloadConfig();
					SetupLangFile.reloadConfig();
					WorldManagerPanelConfig.reloadConfig();
					e.setCancelled(true);
					p.closeInventory();
					for (String msg : SetupLangFile.getConfig().getStringList("SetupLanguage.Language-Changed")) {
						ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg 1%", langtype), "", "", false);
					}
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> OpenInventory(p), 5);
				}
			}

			e.setCancelled(true);
		}
	}


	/**
	 * Banner patterns became a registry and some were renamed in 1.20.5:
	 * the static fields are read by name so the same jar works everywhere.
	 */
	private static PatternType pattern(String... names) {
		for (String name : names) {
			try {
				return (PatternType) PatternType.class.getField(name).get(null);
			} catch (ReflectiveOperationException ignored) {}
		}
		throw new IllegalStateException("Unknown banner pattern " + names[0]);
	}
}
