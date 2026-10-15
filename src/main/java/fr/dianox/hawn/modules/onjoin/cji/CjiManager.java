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
		SpecialItemPlayerVisibility.Check = SpecialCjiHidePlayers.getConfig().getString("PV.OFF.Title");
		SpecialItemPlayerVisibility.CheckTwo = SpecialCjiHidePlayers.getConfig().getString("PV.ON.Title");

		CustomJoinItem.itemcjiname.clear();
		CustomJoinItem.itemcjislot.clear();
		CustomJoinItem.itemcjislotname.clear();
		CustomJoinItem.warned.clear();

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

			for (String string : Objects.requireNonNull(ConfigCJIGeneral.getConfig().getConfigurationSection("Custom-Join-Item.Items.Inventory.Items")).getKeys(false)) {
				String path_item = "Custom-Join-Item.Items.Inventory.Items." + string + ".";

				CustomJoinItem.itemcjislot.put(ConfigCJIGeneral.getConfig().getInt(path_item + "Slot"), path_item);
				CustomJoinItem.itemcjislotname.put(ConfigCJIGeneral.getConfig().getInt(path_item + "Slot"), string);
			}
		}
	}

}
