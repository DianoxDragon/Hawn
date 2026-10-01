package fr.dianox.hawn.modules.admin.SetupUtils;

import fr.dianox.hawn.utility.gui.HawnMenu;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.admin.Setup;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import com.cryptomorin.xseries.XMaterial;
import fr.dianox.hawn.utility.config.configs.ConfigSpawn;
import fr.dianox.hawn.utility.config.configs.events.OnJoinConfig;
import fr.dianox.hawn.utility.config.configs.messages.SetupLangFile;
import fr.dianox.hawn.utility.load.Reload;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;

public class Setup3Spawn implements Listener {

	public static final String name = "§cHawn Setup - 3";
	private static final String spawnname = "";

	// Main
	public static void OpenInventory(Player p) {
		// General Options
		int size = 54;
		Inventory inv = HawnMenu.create(size, name);

		// Inventory
		for (int i = 0; i <= 53; i++) {
			inv.setItem(i, item(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		}

		inv.setItem(13, item(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupSpawn.Info")), XMaterial.OAK_SIGN.parseMaterial()));
		inv.setItem(21, item(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupSpawn.Set-Up-Spawn")), XMaterial.GOLD_BLOCK.parseMaterial()));
		inv.setItem(30, item(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupSpawn.Done")), XMaterial.EMERALD_BLOCK.parseMaterial()));

		inv.setItem(23, item(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupSpawn.Close-Inventory")), XMaterial.BARRIER.parseMaterial()));

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
		String inv = e.getWhoClicked().getOpenInventory().getTitle();
		Player p = (Player) e.getWhoClicked();

		if (inv.equals(name)) {
			if (e.getCurrentItem().getType() == XMaterial.AIR.parseMaterial()) return;

			if (e.isLeftClick()) {
				if (e.getRawSlot() == 23) {
					e.setCancelled(true);
					Setup.finish(p);
				} else if (e.getRawSlot() == 21) {
					e.setCancelled(true);
					p.closeInventory();
					for (String msg : SetupLangFile.getConfig().getStringList("SetupSpawn.WARNING")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					}
				} else if (e.getRawSlot() == 30) {
					e.setCancelled(true);
					Setup.finish(p);
				} else {
					e.setCancelled(true);
				}
			}

			e.setCancelled(true);
		}
	}

	/**
	 * Last step: the admin creates a spawn with /setspawn (or /setlobby, /sethub) <name>,
	 * this spawn becomes the default one and the setup ends.
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();

		if (!Setup.needsetup || !p.hasPermission("hawn.setup")) {
			return;
		}

		String[] parts = e.getMessage().trim().split("\\s+");
		String command = parts[0].toLowerCase();

		if (!command.equals("/setspawn") && !command.equals("/setlobby") && !command.equals("/sethub")) {
			return;
		}

		if (parts.length < 2) {
			return;
		}

		String spawn = parts[1];

		// The command is run after this event: check the spawn on the next tick
		Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
			if (!Setup.needsetup || !ConfigSpawn.getConfig().isSet("Coordinated." + spawn)) {
				return;
			}

			OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", spawn);
			OnJoinConfig.saveConfigFile();

			Setup.finish(p);
			Reload.reloadconfig();

			for (String msg1 : SetupLangFile.getConfig().getStringList("SetupSpawn.Spawn-Changed")) {
				ConfigEventUtils.ExecuteEvent(p, msg1.replace("%arg 1%", spawn), "", "", false);
			}
		});
	}

}
