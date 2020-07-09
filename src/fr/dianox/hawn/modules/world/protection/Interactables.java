package fr.dianox.hawn.modules.world.protection;

import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.configs.events.ConfigGProtection;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Interactables {

	public List<Material> interactables = new ArrayList<>();

	public Interactables() {
		load();
	}

	public void load() {
		if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Enable")) {

			interactables.clear();

			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ACACIA_DOOR")) {
				interactables.add(XMaterial.ACACIA_DOOR.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ACACIA_FENCE_GATE")) {
				interactables.add(XMaterial.ACACIA_FENCE_GATE.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ANVIL")) {
				interactables.add(XMaterial.ANVIL.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BEACON")) {
				interactables.add(XMaterial.BEACON.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.RED_BED")) {
				interactables.add(XMaterial.RED_BED.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BIRCH_DOOR")) {
				interactables.add(XMaterial.BIRCH_DOOR.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BIRCH_FENCE_GATE")) {
				interactables.add(XMaterial.BIRCH_FENCE_GATE.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_BOAT")) {
				interactables.add(XMaterial.OAK_BOAT.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BREWING_STAND")) {
				interactables.add(XMaterial.BREWING_STAND.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.COMMAND_BLOCK")) {
				interactables.add(XMaterial.COMMAND_BLOCK.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.CHEST")) {
				interactables.add(XMaterial.CHEST.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DARK_OAK_DOOR")) {
				interactables.add(XMaterial.DARK_OAK_DOOR.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DARK_OAK_FENCE_GATE")) {
				interactables.add(XMaterial.DARK_OAK_FENCE_GATE.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DAYLIGHT_DETECTOR")) {
				interactables.add(XMaterial.DAYLIGHT_DETECTOR.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DISPENSER")) {
				interactables.add(XMaterial.DISPENSER.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DROPPER")) {
				interactables.add(XMaterial.DROPPER.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ENCHANTING_TABLE")) {
				interactables.add(XMaterial.ENCHANTING_TABLE.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ENDER_CHEST")) {
				interactables.add(XMaterial.ENDER_CHEST.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_FENCE_GATE")) {
				interactables.add(XMaterial.OAK_FENCE_GATE.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.FURNACE")) {
				interactables.add(XMaterial.FURNACE.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.HOPPER")) {
				interactables.add(XMaterial.HOPPER.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.HOPPER_MINECART")) {
				interactables.add(XMaterial.HOPPER_MINECART.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.JUNGLE_DOOR")) {
				interactables.add(XMaterial.JUNGLE_DOOR.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.JUNGLE_FENCE_GATE")) {
				interactables.add(XMaterial.JUNGLE_FENCE_GATE.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.LEVER")) {
				interactables.add(XMaterial.LEVER.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.MINECART")) {
				interactables.add(XMaterial.MINECART.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.NOTE_BLOCK")) {
				interactables.add(XMaterial.NOTE_BLOCK.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.MINECART")) {
				interactables.add(XMaterial.MINECART.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.COMPARATOR")) {
				interactables.add(XMaterial.COMPARATOR.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_SIGN")) {
				interactables.add(XMaterial.OAK_SIGN.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.CHEST_MINECART")) {
				interactables.add(XMaterial.CHEST_MINECART.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_DOOR")) {
				interactables.add(XMaterial.OAK_DOOR.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_TRAPDOOR")) {
				interactables.add(XMaterial.OAK_TRAPDOOR.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.TRAPPED_CHEST")) {
				interactables.add(XMaterial.TRAPPED_CHEST.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_BUTTON")) {
				interactables.add(XMaterial.OAK_BUTTON.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_DOOR")) {
				interactables.add(XMaterial.OAK_DOOR.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.SPRUCE_DOOR")) {
				interactables.add(XMaterial.SPRUCE_DOOR.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.SPRUCE_FENCE_GATE")) {
				interactables.add(XMaterial.SPRUCE_FENCE_GATE.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.SPRUCE_TRAPDOOR")) {
				interactables.add(XMaterial.SPRUCE_TRAPDOOR.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BIRCH_TRAPDOOR")) {
				interactables.add(XMaterial.BIRCH_TRAPDOOR.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.JUNGLE_TRAPDOOR")) {
				interactables.add(XMaterial.JUNGLE_TRAPDOOR.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ACACIA_TRAPDOOR")) {
				interactables.add(XMaterial.ACACIA_TRAPDOOR.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DARK_OAK_TRAPDOOR")) {
				interactables.add(XMaterial.DARK_OAK_TRAPDOOR.parseMaterial());
			}
			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.SWEET_BERRY_BUSH")) {
				interactables.add(XMaterial.SWEET_BERRY_BUSH.parseMaterial());
			}
		}
	}

	public List<Material> getInteractableList() {
		return interactables;
	}
}
