package fr.dianox.hawn.modules.admin.SetupUtils;

import fr.dianox.hawn.utility.gui.HawnMenu;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.admin.Setup;
import fr.dianox.hawn.modules.world.DefaultWorld;
import fr.dianox.hawn.modules.world.GuiSystem;
import fr.dianox.hawn.utility.load.Reload;
import fr.dianox.hawn.utility.MessageUtils;
import com.cryptomorin.xseries.XMaterial;
import fr.dianox.hawn.utility.config.configs.messages.SetupLangFile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;

public class Setup2World implements Listener {

	public static final String name = "§cHawn Setup - 2";

	// Main
	public static void OpenInventory(Player p) {
		// General Options
		int size = 54;
		Inventory inv = HawnMenu.create(size, name, HawnMenu.Type.SETUP_WORLD);

		// Inventory
		for (int i = 0; i <= 53; i++) {
			inv.setItem(i, item(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		}

		inv.setItem(13, item(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupWorld.Info")), XMaterial.OAK_SIGN.parseMaterial()));
		inv.setItem(21, item(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupWorld.Set-Up-World")), XMaterial.EMERALD_BLOCK.parseMaterial()));
		inv.setItem(23, item(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupWorld.Close-Inventory")), XMaterial.BARRIER.parseMaterial()));
		inv.setItem(45, item(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("Setup.Previous")), Setup.NAVIGATION));
		inv.setItem(53, item(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("Setup.Next")), Setup.NAVIGATION));

		p.openInventory(inv);
	}

	private static ItemStack item(String s, Material parseMaterial) {
		ItemStack itemStack = new ItemStack(parseMaterial);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(s);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	// Interaction
	@EventHandler
	public void onInventory(InventoryClickEvent e) throws IOException {
		if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
		if (e.getCurrentItem() == null) return;
		Player p = (Player) e.getWhoClicked();
		HawnMenu.Type type = HawnMenu.typeOf(e.getView());

		if (type == HawnMenu.Type.SETUP_WORLD) {
			if (e.getCurrentItem().getType() == XMaterial.AIR.parseMaterial()) return;

			if (e.isLeftClick()) {
				if (e.getRawSlot() == 23) {
					e.setCancelled(true);
					Setup.finish(p);
				} else if (e.getRawSlot() == 21) {
					Setup.setupplace = 21;
					e.setCancelled(true);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> GuiSystem.FirstPage(p), 5);
				} else if (e.getRawSlot() == 45 && e.getCurrentItem().getType() == Setup.NAVIGATION) {
					// Back to the language
					e.setCancelled(true);
					Setup.goTo(p, 1);
				} else if (e.getRawSlot() == 53 && e.getCurrentItem().getType() == Setup.NAVIGATION) {
					// The default world stays the one of now
					e.setCancelled(true);
					Setup.goTo(p, 3);
				} else {
					e.setCancelled(true);
				}
			}

			e.setCancelled(true);
		} else if (type == HawnMenu.Type.WORLD_LIST) {
			// 97: only the admin doing the setup, on every page: another player who clicks there doesn't move the setup
			if (Setup.inWorldStep(p)) {
				if (e.getRawSlot() == 48) {
					Setup.goTo(p, 3);
				} else if (e.getRawSlot() == 49 && e.getCurrentItem().getType() == Setup.NAVIGATION) {
					// Back to the step of the world (the arrows of 45 and 46 keep changing the pages)
					Setup.goTo(p, 2);
				}
			}
		}
	}

	public static void chooseworld(String s) {
		// The chosen world replaces the default world, and "world" (the name written in the files at the first start),
		// in the world lists of every file: the other worlds of the lists (nether, end...) are kept
		DefaultWorld.change(s, "world");
		Reload.reloadconfig();
		// 96: the list stays open, "Default world" moves to the chosen world at once
		GuiSystem.refreshWorldPages();
	}

}
