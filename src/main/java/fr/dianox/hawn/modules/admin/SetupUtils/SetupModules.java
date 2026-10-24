package fr.dianox.hawn.modules.admin.SetupUtils;

import com.cryptomorin.xseries.XMaterial;
import fr.dianox.hawn.modules.admin.Setup;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.ConfigFDoubleJump;
import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.configs.events.OnChatConfig;
import fr.dianox.hawn.utility.config.configs.events.OnJoinConfig;
import fr.dianox.hawn.utility.config.configs.events.VoidTPConfig;
import fr.dianox.hawn.utility.config.configs.messages.SetupLangFile;
import fr.dianox.hawn.utility.config.configs.tab.TablistConfig;
import fr.dianox.hawn.utility.gui.HawnMenu;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * The modules of the setup (since 1.5.1), after the language: the modules that take effect without a restart, turned on
 * or off with a click. Their option is written at once, Hawn reloads at the end of the setup.
 */
public class SetupModules implements Listener {

	public static final String name = "§cHawn Setup - 2";

	private static final class Module {
		final String key;
		final Material material;
		final Supplier<YamlConfiguration> config;
		final Runnable save;
		final String option;
		final int slot;

		Module(String key, XMaterial material, Supplier<YamlConfiguration> config, Runnable save, String option, int slot) {
			this.key = key;
			this.material = material.parseMaterial();
			this.config = config;
			this.save = save;
			this.option = option;
			this.slot = slot;
		}

		boolean on() {
			return config.get().getBoolean(option);
		}
	}

	private static final Module[] MODULES = {
		new Module("Join-Items", XMaterial.CHEST, ConfigCJIGeneral::getConfig, ConfigCJIGeneral::saveConfigFile, "Custom-Join-Item.Enable", 19),
		new Module("Tablist", XMaterial.NAME_TAG, TablistConfig::getConfig, TablistConfig::saveConfigFile, "Tablist.enable", 20),
		new Module("Boss-Bar", XMaterial.DRAGON_BREATH, OnJoinConfig::getConfig, OnJoinConfig::saveConfigFile, "Boss-Bar.Enable", 21),
		new Module("Join-Title", XMaterial.OAK_SIGN, OnJoinConfig::getConfig, OnJoinConfig::saveConfigFile, "Title.Enable", 22),
		new Module("Double-Jump", XMaterial.FEATHER, ConfigFDoubleJump::getConfig, ConfigFDoubleJump::saveConfigFile, "DoubleJump.Enable", 23),
		new Module("Void-TP", XMaterial.ENDER_PEARL, VoidTPConfig::getConfig, VoidTPConfig::saveConfigFile, "VoidTP.Enable", 24),
		new Module("Anti-Swear", XMaterial.WRITABLE_BOOK, OnChatConfig::getConfig, OnChatConfig::saveConfigFile, "Anti-Swear.Enable", 25),
		new Module("Anti-Advertising", XMaterial.PAPER, OnChatConfig::getConfig, OnChatConfig::saveConfigFile, "Anti-Advertising.Enable", 31)
	};

	public static void OpenInventory(Player p) {
		Inventory inv = HawnMenu.create(54, name, HawnMenu.Type.SETUP_MODULES);

		for (int i = 0; i <= 53; i++) {
			inv.setItem(i, item(" ", null, XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		}

		inv.setItem(4, item(text("SetupModules.Info"), lines("SetupModules.Info-Lore"), XMaterial.OAK_SIGN.parseMaterial()));
		for (Module module : MODULES) {
			inv.setItem(module.slot, moduleItem(module));
		}

		inv.setItem(45, item(text("Setup.Previous"), null, Setup.NAVIGATION));
		inv.setItem(49, item(text("SetupModules.Close-Inventory"), null, XMaterial.BARRIER.parseMaterial()));
		inv.setItem(53, item(text("Setup.Next"), null, Setup.NAVIGATION));

		p.openInventory(inv);
	}

	private static ItemStack moduleItem(Module module) {
		boolean on = module.on();
		String title = (on ? "&a" : "&c") + SetupLangFile.getConfig().getString("SetupModules." + module.key) + "&7: "
				+ SetupLangFile.getConfig().getString(on ? "SetupModules.On" : "SetupModules.Off");
		List<String> lore = new ArrayList<>();
		lore.add(text(on ? "SetupModules.Click-To-Turn-Off" : "SetupModules.Click-To-Turn-On"));
		return item(MessageUtils.colourTheStuff(title), lore, module.material);
	}

	private static String text(String path) {
		return MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString(path, ""));
	}

	private static List<String> lines(String path) {
		List<String> lines = new ArrayList<>();
		for (String line : SetupLangFile.getConfig().getStringList(path)) {
			lines.add(MessageUtils.colourTheStuff(line));
		}
		return lines;
	}

	private static ItemStack item(String title, List<String> lore, Material material) {
		ItemStack itemStack = new ItemStack(material);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(title);
		if (lore != null) {
			itemMeta.setLore(lore);
		}
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	@EventHandler
	public void onInventory(InventoryClickEvent e) {
		if (HawnMenu.typeOf(e.getView()) != HawnMenu.Type.SETUP_MODULES) {
			return;
		}

		e.setCancelled(true);
		if (e.getCurrentItem() == null || !e.isLeftClick() || e.getRawSlot() >= e.getView().getTopInventory().getSize()) {
			return;
		}

		Player p = (Player) e.getWhoClicked();
		int slot = e.getRawSlot();

		if (slot == 45 && e.getCurrentItem().getType() == Setup.NAVIGATION) {
			Setup.goTo(p, 1);
		} else if (slot == 53 && e.getCurrentItem().getType() == Setup.NAVIGATION) {
			Setup.goTo(p, 2);
		} else if (slot == 49) {
			Setup.finish(p);
		} else {
			for (Module module : MODULES) {
				if (module.slot == slot) {
					module.config.get().set(module.option, !module.on());
					module.save.run();
					Setup.changed();
					e.getView().getTopInventory().setItem(slot, moduleItem(module));
					return;
				}
			}
		}
	}
}
