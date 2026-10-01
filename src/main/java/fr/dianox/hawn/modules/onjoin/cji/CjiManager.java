package fr.dianox.hawn.modules.onjoin.cji;

import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;

import java.util.Objects;

public class CjiManager {

	public CjiManager() {
		load();
	}

	public void load() {
		if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Enable")) {

			CustomJoinItem.itemcjiname.clear();
			CustomJoinItem.itemcjislot.clear();

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

			for (String string : Objects.requireNonNull(ConfigCJIGeneral.getConfig().getConfigurationSection("Custom-Join-Item.Items.Inventory.Items")).getKeys(false)) {
				String path_item = "Custom-Join-Item.Items.Inventory.Items." + string + ".";

				CustomJoinItem.itemcjislot.put(ConfigCJIGeneral.getConfig().getInt(path_item + "Slot"), path_item);
				CustomJoinItem.itemcjislotname.put(ConfigCJIGeneral.getConfig().getInt(path_item + "Slot"), string);
			}
		}
	}

}
