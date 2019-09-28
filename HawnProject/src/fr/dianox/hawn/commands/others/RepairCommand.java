package fr.dianox.hawn.commands.others;

import java.util.Locale;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.commands.RepairCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;

public class RepairCommand extends BukkitCommand {
	
	private String GeneralPermission = "hawn.command.repair";

	public RepairCommand(String name) {
		 super(name);
		 this.description = "Repair an item";
		 this.usageMessage = "/repair";
	 }

	 @SuppressWarnings("deprecation")
	@Override
	 public boolean execute(CommandSender sender, String label, String[] args) {
		 
		 // >>> Executed by the console
		 if(!(sender instanceof Player)) {
			 if (ConfigMOStuff.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				 for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					 MessageUtils.ReplaceMessageForConsole(msg);
				 }
			 }
			 return true;
		 }
	    	
		 // >>> Executed by the player
		 Player p = (Player) sender;

		 if (!RepairCommandConfig.getConfig().getBoolean("Repair.Enable")) {
			 if (RepairCommandConfig.getConfig().getBoolean("Repair.Disable-Message")) {
				 if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
					 for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
						 MessageUtils.ReplaceCharMessagePlayer(msg, p);
					 }
				 }
			 }

			 return true;
		 }

		 if (!p.hasPermission(GeneralPermission)) {
			 MessageUtils.MessageNoPermission(p, GeneralPermission);
			 return true;
		 }
		 
		 // >>> Commande
		 
		 ItemStack item = null;
		 
		 if (Main.Spigot_Version > 18) {
			 item = p.getInventory().getItemInMainHand();
		 } else {
			 item = p.getInventory().getItemInHand();
		 }
		 

		 if (item == null) return false;
		 
		 String in = item.getType().toString().toLowerCase(Locale.ENGLISH);
		 
		 if (item == null || item.getDurability() == 0 || !Canberepair(item.getType())) {
			 if (ConfigMCommands.getConfig().getBoolean("Repair.Can-t-Repair.Enable")) {
				 for (String msg: ConfigMCommands.getConfig().getStringList("Repair.Can-t-Repair.Messages")) {
					 MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%item%", in), p);
				 }
			 }
			 
			 return false;
		 } else {
			 item.setDurability((short) 0);
			 
			 if (ConfigMCommands.getConfig().getBoolean("Repair.Repaired.Enable")) {
				 for (String msg: ConfigMCommands.getConfig().getStringList("Repair.Repaired.Messages")) {
					 MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%item%", in), p);
				 }
			 }
		 }
		 
		 return true;
	 }
	 
	 private boolean Canberepair(Material material) {
		 if (material == null) {
			 return false;
		 }
		 if (material == XMaterial.WOODEN_AXE.parseMaterial() || 
		      material == XMaterial.WOODEN_HOE.parseMaterial() || 
		      material == XMaterial.WOODEN_SWORD.parseMaterial() || 
		      material == XMaterial.WOODEN_PICKAXE.parseMaterial() || 
		      material == XMaterial.STONE_AXE.parseMaterial() || 
		      material == XMaterial.STONE_HOE.parseMaterial() || 
		      material == XMaterial.STONE_SWORD.parseMaterial() || 
		      material == XMaterial.STONE_PICKAXE.parseMaterial() || 
		      material == XMaterial.GOLDEN_AXE.parseMaterial() || 
		      material == XMaterial.GOLDEN_HOE.parseMaterial() || 
		      material == XMaterial.GOLDEN_SWORD.parseMaterial() || 
		      material == XMaterial.GOLDEN_PICKAXE.parseMaterial() || 
		      material == XMaterial.IRON_AXE.parseMaterial() || 
		      material == XMaterial.IRON_HOE.parseMaterial() || 
		      material == XMaterial.IRON_SWORD.parseMaterial() || 
		      material == XMaterial.IRON_PICKAXE.parseMaterial() || 
		      material == XMaterial.DIAMOND_AXE.parseMaterial() || 
		      material == XMaterial.DIAMOND_HOE.parseMaterial() || 
		      material == XMaterial.DIAMOND_SWORD.parseMaterial() || 
		      material == XMaterial.DIAMOND_PICKAXE.parseMaterial() || 
		      material == XMaterial.DIAMOND_BOOTS.parseMaterial() || 
		      material == XMaterial.DIAMOND_CHESTPLATE.parseMaterial() || 
		      material == XMaterial.DIAMOND_HELMET.parseMaterial() || 
		      material == XMaterial.DIAMOND_LEGGINGS.parseMaterial() || 
		      material == XMaterial.IRON_BOOTS.parseMaterial() || 
		      material == XMaterial.IRON_CHESTPLATE.parseMaterial() || 
		      material == XMaterial.IRON_HELMET.parseMaterial() || 
		      material == XMaterial.IRON_LEGGINGS.parseMaterial() || 
		      material == XMaterial.GOLDEN_BOOTS.parseMaterial() || 
		      material == XMaterial.GOLDEN_CHESTPLATE.parseMaterial() || 
		      material == XMaterial.GOLDEN_HELMET.parseMaterial() || 
		      material == XMaterial.GOLDEN_LEGGINGS.parseMaterial() || 
		      material == XMaterial.DIAMOND_BOOTS.parseMaterial() || 
		      material == XMaterial.DIAMOND_CHESTPLATE.parseMaterial() || 
		      material == XMaterial.DIAMOND_HELMET.parseMaterial() || 
		      material == XMaterial.DIAMOND_LEGGINGS.parseMaterial() || 
		      material == XMaterial.CHAINMAIL_BOOTS.parseMaterial() || 
		      material == XMaterial.CHAINMAIL_CHESTPLATE.parseMaterial() || 
		      material == XMaterial.CHAINMAIL_HELMET.parseMaterial() || 
		      material == XMaterial.CHAINMAIL_LEGGINGS.parseMaterial() || 
		      material == XMaterial.LEATHER_BOOTS.parseMaterial() || 
		      material == XMaterial.LEATHER_CHESTPLATE.parseMaterial() || 
		      material == XMaterial.LEATHER_HELMET.parseMaterial() || 
		      material == XMaterial.LEATHER_LEGGINGS.parseMaterial() || 
		      material == XMaterial.TURTLE_HELMET.parseMaterial() || 
		      material == XMaterial.ELYTRA.parseMaterial() || 
		      material == XMaterial.TRIDENT.parseMaterial() || 
		      material == XMaterial.CROSSBOW.parseMaterial() || 
		      material == XMaterial.FISHING_ROD.parseMaterial() || 
		      material == XMaterial.SHEARS.parseMaterial() || 
		      material == XMaterial.FLINT_AND_STEEL.parseMaterial() || 
		      material == XMaterial.SHIELD.parseMaterial() || 
		      material == XMaterial.BOW.parseMaterial()) {
			 return true;
		 }
		 return false;
	 }

}