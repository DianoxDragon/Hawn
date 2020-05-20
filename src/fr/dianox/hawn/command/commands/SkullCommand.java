package fr.dianox.hawn.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.configs.commands.SkullCommandConfig;

import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;

public class SkullCommand extends BukkitCommand {
	
	private String GeneralPermission = "hawn.command.skull";

	public SkullCommand(String name) {
		 super(name);
		 this.description = "Get player skull";
		 this.usageMessage = "/skull [player]";
	 }

	 @Override
	 public boolean execute(CommandSender sender, String label, String[] args) {
		 
		 // >>> Executed by the console
		 if(!(sender instanceof Player)) {
			 if (ConfigMMsg.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				 for (String msg: ConfigMMsg.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					 MessageUtils.ConsoleMessages(msg);
				 }
			 }
			 return true;
		 }
	    	
		 // >>> Executed by the player
		 Player p = (Player) sender;

		 if (!SkullCommandConfig.getConfig().getBoolean("Skull.Enable")) {
			 if (SkullCommandConfig.getConfig().getBoolean("Skull.Disable-Message")) {
				 if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
					 for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
						 ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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
		 if (args.length == 0) {
			 p.getInventory().addItem(getSkull(p.getName()));
				 
			 if (ConfigMMsg.getConfig().getBoolean("Skull.Self.Enable")) {
				 for (String msg: ConfigMMsg.getConfig().getStringList("Skull.Self.Messages")) {
					 ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				 }
			 }

		 } else if (args.length == 1) {
			 Player target = Bukkit.getServer().getPlayer(args[0]);
				 
			 if (target == null) {
				 MessageUtils.PlayerDoesntExist(p);
				 return true;
			 }

			 p.getInventory().addItem(getSkull(target.getName()));
				 
			 if (ConfigMMsg.getConfig().getBoolean("Skull.Sender.Enable")) {
				 for (String msg: ConfigMMsg.getConfig().getStringList("Skull.Sender.Messages")) {
					 ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%target%", target.getName()), "", "", false);
				 }
			 }
			 
			 if (ConfigMMsg.getConfig().getBoolean("Skull.Target.Enable")) {
				 for (String msg: ConfigMMsg.getConfig().getStringList("Skull.Target.Messages")) {
					 ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()), "", "", false);
				 }
			 }
		 } else {
			 p.sendMessage("§c/skull <player>");
		 }
		 
		 return true;
	 }
	 
	 @SuppressWarnings("deprecation")
	 private ItemStack getSkull(String name) {
		 Material m = XMaterial.PLAYER_HEAD.parseMaterial();
		 ItemStack is = new ItemStack(m, 1);
		 if (Main.Spigot_Version < 113) {
			 is.setDurability((short)3);
		 }
		 SkullMeta meta = (SkullMeta)is.getItemMeta();
		 meta.setOwner(name);
		 is.setItemMeta(meta);
		 return is;
	 }

}