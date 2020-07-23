package fr.dianox.hawn.modules.admin.SetupUtils;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.admin.Setup;
import fr.dianox.hawn.modules.world.GuiSystem;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.configs.AutoBroadcastConfig;
import fr.dianox.hawn.utility.config.configs.commands.OptionPlayerConfigCommand;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.ConfigFDoubleJump;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.ConfigGCos;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.ConfigGLP;
import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.configs.events.*;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMGeneral;
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

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class Setup2World implements Listener {

	public static final String name = "§cHawn Setup - 2";

	// Main
	public static void OpenInventory(Player p) {
		// General Options
		int size = 54;
		Inventory inv = Bukkit.createInventory(null, size, name);

		// Inventory
		for (int i = 0; i <= 53; i++) {
			inv.setItem(i, item(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		}

		inv.setItem(13, item(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupWorld.Info")), XMaterial.OAK_SIGN.parseMaterial()));
		inv.setItem(21, item(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupWorld.Set-Up-World")), XMaterial.EMERALD_BLOCK.parseMaterial()));
		inv.setItem(23, item(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupWorld.Close-Inventory")), XMaterial.BARRIER.parseMaterial()));

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
					Setup.setupplace = 21;
					e.setCancelled(true);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> GuiSystem.FirstPage(p), 5);
				} else {
					e.setCancelled(true);
				}
			}

			e.setCancelled(true);
		} else if (inv.equals("§cWorld Manager - Main") || inv.equals("§cWorld Manager - Main 2")) {
			if (Setup.setupplace == 21) {
				if (e.getRawSlot() == 48) {
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Setup3Spawn.OpenInventory(p), 5);
					Setup.setupplace = 3;
				}
			}
		}
	}

	public static void chooseworld(String s) {
		VoidTPConfig.getConfig().set("VoidTP.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("Change-Gamemode.World.Worlds", Collections.singletonList(s));
		PlayerEventsConfig.getConfig().set("Keep-Gamemode.World.Worlds", Collections.singletonList(s));
		PlayerEventsConfig.getConfig().set("Keep.Food.World.Worlds", Collections.singletonList(s));
		ProtectionPlayerConfig.getConfig().set("Anti-Damage.World.Worlds", Collections.singletonList(s));
		AutoBroadcastConfig.getConfig().set("Config.Messages.World.Worlds", Collections.singletonList(s));
		AutoBroadcastConfig.getConfig().set("Config.Titles.World.Worlds", Collections.singletonList(s));
		AutoBroadcastConfig.getConfig().set("Config.Action-Bar.World.Worlds", Collections.singletonList(s));
		AutoBroadcastConfig.getConfig().set("Config.BossBar.World.Worlds", Collections.singletonList(s));
		PlayerWorldChangeConfigE.getConfig().set("Fly.World.Worlds", Collections.singletonList(s));
		PlayerWorldChangeConfigE.getConfig().set("GM.World.Worlds", Collections.singletonList(s));
		PlayerWorldChangeConfigE.getConfig().set("Player-Options.World.Worlds", Collections.singletonList(s));
		PlayerWorldChangeConfigE.getConfig().set("Execute-Command.World.Worlds", Collections.singletonList(s));
		ConfigCJIGeneral.getConfig().set("Custom-Join-Item.General-Option.World.Worlds", Collections.singletonList(s));
		ConfigGJoinQuitCommand.getConfig().set("JoinCommand.Options.New.World.Worlds", Collections.singletonList(s));
		ConfigGJoinQuitCommand.getConfig().set("JoinCommand.Options.No-New.World.Worlds", Collections.singletonList(s));
		ConfigGJoinQuitCommand.getConfig().set("QuitCommand.World.Worlds", Collections.singletonList(s));
		ConfigGCos.getConfig().set("Cosmetics.Firework.World.Worlds", Collections.singletonList(s));
		ConfigGLP.getConfig().set("JumpPads.World.Worlds", Collections.singletonList(s));
		ConfigGCos.getConfig().set("Cosmetics.Lightning-Strike.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("Boss-Bar.World.Worlds", Collections.singletonList(s));
		ConfigMGeneral.getConfig().set("Spawn.On-join.World.Worlds", Collections.singletonList(s));
		ConfigMGeneral.getConfig().set("General.On-join.Join-Message.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("Restore.Food.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("Restore.Health.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("Inventory.Clear.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("Chat.Clear.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("XP.Options.Exp.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("XP.Options.Level.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("Event.OnJoin.Sounds.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("Fly.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("Title.First-Join.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("Title.Join.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("Action-Bar.First-Join.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("Action-Bar.Join.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("Speed.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("FlySpeed.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("Potion-Effect.JUMP.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("Potion-Effect.BLINDNESS.World.Worlds", Collections.singletonList(s));
		ConfigMGeneral.getConfig().set("General.On-Quit.Quit-Message.World.Worlds", Collections.singletonList(s));
		OtherFeaturesConfig.getConfig().set("ColorSign.World.Worlds", Collections.singletonList(s));
		OtherFeaturesConfig.getConfig().set("EmojiSign.World.Worlds", Collections.singletonList(s));
		PlayerEventsConfig.getConfig().set("Block-Off-Hand.World.Worlds", Collections.singletonList(s));
		OptionPlayerConfigCommand.getConfig().set("PlayerOption.World.Worlds", Collections.singletonList(s));
		PlayerEventsConfig.getConfig().set("Items.Drop.World.Worlds", Collections.singletonList(s));
		PlayerEventsConfig.getConfig().set("Items.PickUp.World.Worlds", Collections.singletonList(s));
		PlayerEventsConfig.getConfig().set("Items.Move.World.Worlds", Collections.singletonList(s));
		PlayerEventsConfig.getConfig().set("Items.Damage-Item.World.Worlds", Collections.singletonList(s));
		PlayerEventsConfig.getConfig().set("Death.Death-Message.World.Worlds", Collections.singletonList(s));
		PlayerEventsConfig.getConfig().set("Death.Respawn.Player.World.Worlds", Collections.singletonList(s));
		OnJoinConfig.getConfig().set("Inventory.Force-Selected-Slot.World.Worlds", Collections.singletonList(s));
		PlayerEventsConfig.getConfig().set("Items.Clear-Drops-On-Death.World.Worlds", Collections.singletonList(s));
		ConfigFDoubleJump.getConfig().set("DoubleJump.Double.World.Worlds", Collections.singletonList(s));
		PlayerEventsConfig.getConfig().set("Death.Respawn.Player.Regive-Hawn-Custom-Join-Items.World.Worlds", Collections.singletonList(s));
		PlayerEventsConfig.getConfig().set("Block-Mount.World.Worlds", Collections.singletonList(s));
		ConfigGProtection.getConfig().set("Protection.Anti-Bucket-Use.World.Worlds", Collections.singletonList(s));
		ConfigGProtection.getConfig().set("Protection.Construct.Anti-Place.World.Worlds", Collections.singletonList(s));
		ConfigGProtection.getConfig().set("Protection.Construct.Anti-Break.World.Worlds", Collections.singletonList(s));
		ConfigGProtection.getConfig().set("Protection.HagingBreakByEntity.World.Worlds", Collections.singletonList(s));
		ConfigGProtection.getConfig().set("Protection.PlayerInteractEntity-ItemFrame.World.Worlds", Collections.singletonList(s));
		ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.World.Worlds", Collections.singletonList(s));
		WorldEventConfig.getConfig().set("World.Weather.Disable.Weather.World.Worlds", Collections.singletonList(s));
		WorldEventConfig.getConfig().set("World.Weather.Disable.ThunderChange.World.Worlds", Collections.singletonList(s));
		WorldEventConfig.getConfig().set("World.Weather.Disable.LightningStrike.World.Worlds", Collections.singletonList(s));
		WorldEventConfig.getConfig().set("World.Burn.Disable.Burn-Block.World.Worlds", Collections.singletonList(s));
		WorldEventConfig.getConfig().set("World.Explosion.Disable.Explosion.World.Worlds", Collections.singletonList(s));
		WorldEventConfig.getConfig().set("World.Blocks.Disable.Leave-Decay.World.Worlds", Collections.singletonList(s));
		WorldEventConfig.getConfig().set("World.Burn.Disable.BlockIgnite-FireSpread.World.Worlds", Collections.singletonList(s));
		WorldEventConfig.getConfig().set("World.Blocks.Disable.Block-Fade.World.Worlds", Collections.singletonList(s));
		WorldEventConfig.getConfig().set("World.Disable.Spawning-Monster-Animals.World.Worlds", Collections.singletonList(s));
		WorldEventConfig.getConfig().set("No-Shears.World.Worlds", Collections.singletonList(s));
		WorldEventConfig.getConfig().set("DenyEntityTravelPortal.World.Worlds", Collections.singletonList(s));

		VoidTPConfig.saveConfigFile();
		OnJoinConfig.saveConfigFile();
		PlayerEventsConfig.saveConfigFile();
		ProtectionPlayerConfig.saveConfigFile();
		AutoBroadcastConfig.saveConfigFile();
		PlayerWorldChangeConfigE.saveConfigFile();
		ConfigCJIGeneral.saveConfigFile();
		ConfigGJoinQuitCommand.saveConfigFile();
		ConfigGCos.saveConfigFile();
		ConfigGLP.saveConfigFile();
		ConfigMGeneral.saveConfigFile();
		OtherFeaturesConfig.saveConfigFile();
		OptionPlayerConfigCommand.saveConfigFile();
		ConfigFDoubleJump.saveConfigFile();
		ConfigGProtection.saveConfigFile();
		WorldEventConfig.saveConfigFile();
	}

}
