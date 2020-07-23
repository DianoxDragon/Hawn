package fr.dianox.hawn.modules.admin.SetupUtils;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.admin.Setup;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.XMaterial;
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

import java.io.File;
import java.io.IOException;

public class Setup3Spawn implements Listener {

	public static final String name = "§cHawn Setup - 3";
	private static final String spawnname = "";

	// Main
	public static void OpenInventory(Player p) {
		// General Options
		int size = 54;
		Inventory inv = Bukkit.createInventory(null, size, name);

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
				} else if (e.getRawSlot() == 21) {
					e.setCancelled(true);
					p.closeInventory();
					p.sendMessage(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupSpawn.WARNING")));
				} else if (e.getRawSlot() == 30) {
					File file = new File(Main.getInstance().getDataFolder(), "StockageInfo/Setup.lock");
					if (!file.exists()) {
						file.createNewFile();
					}
					e.setCancelled(true);
					for (String msg1 : SetupLangFile.getConfig().getStringList("Setup.Restart-Server")) {
						ConfigEventUtils.ExecuteEvent(p, msg1, "", "", false);
					}
				} else {
					e.setCancelled(true);
				}
			}

			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent e) throws IOException {
		Player p = e.getPlayer();
		String msg;

		if (e.getMessage().startsWith("/setspawn") || e.getMessage().startsWith("/setlobby") || e.getMessage().startsWith("/sethub")) {
			msg = e.getMessage().replace("/setspawn ", "");
			msg = e.getMessage().replace("/setlobby ", "");
			msg = e.getMessage().replace("/sethub ", "");

			String[] parts = msg.split(" ");

			if (ConfigSpawn.getConfig().isSet("Coordinated."+parts[0])) {
				OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", parts[0]);
				OnJoinConfig.saveConfigFile();

				Reload.reloadconfig();

				for (String msg1 : SetupLangFile.getConfig().getStringList("SetupSpawn.Spawn-Changed")) {
					ConfigEventUtils.ExecuteEvent(p, msg1.replace("%arg 1%", parts[0]), "", "", false);
				}
			}

			File file = new File(Main.getInstance().getDataFolder(), "StockageInfo/Setup.lock");
			if (!file.exists()) {
				file.createNewFile();
			}

			for (String msg1 : SetupLangFile.getConfig().getStringList("Setup.Restart-Server")) {
				ConfigEventUtils.ExecuteEvent(p, msg1, "", "", false);
			}
		}
	}

}
