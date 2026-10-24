package fr.dianox.hawn.modules.onjoin.cji;

import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiFunGun;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiHidePlayers;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiLobbyBow;

import java.util.Objects;

public class CjiManager {

	public CjiManager() {
		load();
	}

	public void load() {
		// The titles of the special items, read again at each /hawn reload
		SpecialIteFunGun.Check = SpecialCjiFunGun.getConfig().getString("FunGun.Item.Title");
		SpecialIteLobbyBow.Check = SpecialCjiLobbyBow.getConfig().getString("LobbyBow.Item.Title");

		CustomJoinItem.itemcjiname.clear();
		CustomJoinItem.itemcjislot.clear();
		CustomJoinItem.itemcjislotname.clear();
		CustomJoinItem.warned.clear();
		CustomJoinItem.itemWorlds.clear();

		if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Enable")) {

			if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Helmet.Enable")) {

				String path_item = "Custom-Join-Item.Items.Armor.Helmet.Item.";

				CustomJoinItem.itemcjiname.put("Helmet-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material"), path_item);
			}

			if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Chestplate.Enable")) {

				String path_item = "Custom-Join-Item.Items.Armor.Chestplate.Item.";

				CustomJoinItem.itemcjiname.put("Chestplate-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material"), path_item);
			}

			if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Leggings.Enable")) {

				String path_item = "Custom-Join-Item.Items.Armor.Leggings.Item.";

				CustomJoinItem.itemcjiname.put("Leggings-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material"), path_item);
			}

			if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Boots.Enable")) {

				String path_item = "Custom-Join-Item.Items.Armor.Boots.Item.";

				CustomJoinItem.itemcjiname.put("Boots-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material"), path_item);
			}

			// Give items

			// No item at all (the section removed): nothing to give, Hawn still starts
			org.bukkit.configuration.ConfigurationSection inventoryItems = ConfigCJIGeneral.getConfig().getConfigurationSection("Custom-Join-Item.Items.Inventory.Items");
			for (String string : inventoryItems == null ? java.util.Collections.<String>emptySet() : inventoryItems.getKeys(false)) {
				String path_item = "Custom-Join-Item.Items.Inventory.Items." + string + ".";

				CustomJoinItem.itemcjislot.put(ConfigCJIGeneral.getConfig().getInt(path_item + "Slot"), path_item);
				CustomJoinItem.itemcjislotname.put(ConfigCJIGeneral.getConfig().getInt(path_item + "Slot"), string);
				addWorlds(path_item);
			}

			for (String piece : new String[] {"Helmet", "Chestplate", "Leggings", "Boots"}) {
				if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor." + piece + ".Enable")) {
					addWorlds("Custom-Join-Item.Items.Armor." + piece + ".Item.");
				}
			}
		}
	}

	// 115: the worlds of Worlds: are worlds of the join items too (clicks, drops)
	private static void addWorlds(String path_item) {
		for (String world : ConfigCJIGeneral.getConfig().getStringList(path_item + "Worlds")) {
			CustomJoinItem.itemWorlds.add(world.toLowerCase(java.util.Locale.ROOT));
		}
	}

}
