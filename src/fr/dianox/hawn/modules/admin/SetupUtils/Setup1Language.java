package fr.dianox.hawn.modules.admin.SetupUtils;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.admin.Setup;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.XMaterial;
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
		Inventory inv = Bukkit.createInventory(null, size, name);

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
				patterns.add(new Pattern(DyeColor.BLUE, PatternType.HALF_VERTICAL));
				patterns.add(new Pattern(DyeColor.RED, PatternType.HALF_VERTICAL_MIRROR));
				patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
			} else if (lang.startsWith("en")) {
				if (lang.contains("en_US")) {
					patterns.add(new Pattern(DyeColor.RED, PatternType.BASE));
					patterns.add(new Pattern(DyeColor.BLUE, PatternType.SQUARE_TOP_LEFT));
					patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_SMALL));
				} else if (lang.contains("en_UK")) {
					patterns.add(new Pattern(DyeColor.BLUE, PatternType.BASE));
					patterns.add(new Pattern(DyeColor.WHITE, PatternType.CROSS));
					patterns.add(new Pattern(DyeColor.RED, PatternType.STRAIGHT_CROSS));
				}
			} else if (lang.startsWith("ge")) {
				patterns.add(new Pattern(DyeColor.BLACK, PatternType.HALF_HORIZONTAL));
				patterns.add(new Pattern(DyeColor.YELLOW, PatternType.HALF_HORIZONTAL_MIRROR));
				patterns.add(new Pattern(DyeColor.RED, PatternType.STRIPE_MIDDLE));
			} else if (lang.startsWith("el")) {
				patterns.add(new Pattern(DyeColor.BLUE, PatternType.BASE));
				patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_SMALL));
				patterns.add(new Pattern(DyeColor.BLUE, PatternType.SQUARE_TOP_LEFT));
			} else if (lang.startsWith("es")) {
				patterns.add(new Pattern(DyeColor.RED, PatternType.HALF_HORIZONTAL));
				patterns.add(new Pattern(DyeColor.RED, PatternType.HALF_HORIZONTAL_MIRROR));
				patterns.add(new Pattern(DyeColor.YELLOW, PatternType.STRIPE_MIDDLE));
			} else if (lang.startsWith("fi")) {
				patterns.add(new Pattern(DyeColor.BLUE, PatternType.STRAIGHT_CROSS));
			} else if (lang.startsWith("it")) {
				patterns.add(new Pattern(DyeColor.GREEN, PatternType.HALF_VERTICAL));
				patterns.add(new Pattern(DyeColor.RED, PatternType.HALF_VERTICAL_MIRROR));
				patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
			} else if (lang.startsWith("ja")) {
				patterns.add(new Pattern(DyeColor.RED, PatternType.CIRCLE_MIDDLE));
			} else if (lang.startsWith("ne")) {
				patterns.add(new Pattern(DyeColor.RED, PatternType.HALF_HORIZONTAL));
				patterns.add(new Pattern(DyeColor.BLUE, PatternType.HALF_HORIZONTAL_MIRROR));
				patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE));
			} else if (lang.startsWith("zh")) {
				patterns.add(new Pattern(DyeColor.RED, PatternType.BASE));
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
			if (e.getCurrentItem().getType() == XMaterial.AIR.parseMaterial()) return;

			if (e.isLeftClick()) {
				if (e.getRawSlot() == 53) {
					File file = new File(Main.getInstance().getDataFolder(), "StockageInfo/Setup.lock");
					if (!file.exists()) {
						file.createNewFile();
					}
					e.setCancelled(true);
					p.closeInventory();
					Setup.needsetup = false;
					for (String msg1 : SetupLangFile.getConfig().getStringList("Setup.Restart-Server")) {
						ConfigEventUtils.ExecuteEvent(p, msg1, "", "", false);
					}
				} else if (e.getRawSlot() == 45) {
					Setup.setupplace = 2;
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Setup2World.OpenInventory(p), 5);
				} else if (e.getCurrentItem().getType() == XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()) {
					e.setCancelled(true);
				} else {
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

}
