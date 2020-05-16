package fr.dianox.hawn.command.commands;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ActionBar;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.Titles;
import fr.dianox.hawn.utility.XSound;
import fr.dianox.hawn.utility.config.commands.ActionbarAnnouncerConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMMsg;

public class ABAnnouncerCommand extends BukkitCommand {

    String GeneralPermission = "hawn.command.actionbarannouncer";

    public ABAnnouncerCommand(String name) {
        super(name);
        this.description = "Broadcast an actionbar";
        this.usageMessage = "/bacast [msg]";
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        // >>> Executed by the console
        if (!(sender instanceof Player)) {
        	if (args.length == 0) {

        		Bukkit.getConsoleSender().sendMessage("§cError");

                return true;
            }

            String msgbc = "";

            if (args.length >= 1) {
                if (!args[0].isEmpty()) {
                    for (int i = 1; i < args.length; i++) {
                        if (!Objects.equals(msgbc, "")) {
                            msgbc = msgbc + " ";
                        }
                        msgbc = msgbc + args[i];
                    }
                }
            }

            msgbc = args[0] + " " + msgbc;

            msgbc = msgbc.replaceAll("&", "§");
            
            for (Player all : Bukkit.getServer().getOnlinePlayers()) {
            	ActionBar.sendActionBar(all, msgbc, ActionbarAnnouncerConfig.getConfig().getInt("ActionBar-Announcer.Action-Bar.Stay"));
            }
            
            /*
             * Sound
             */
            if (ActionbarAnnouncerConfig.getConfig().getBoolean("ActionBar-Announcer.Options.Sound-For-All-Players.Enable")) {
    	        String sound = ActionbarAnnouncerConfig.getConfig().getString("ActionBar-Announcer.Options.Sound-For-All-Players.Sound");
    	        int volume = ActionbarAnnouncerConfig.getConfig().getInt("ActionBar-Announcer.Options.Sound-For-All-Players.Volume");
    	        int pitch = ActionbarAnnouncerConfig.getConfig().getInt("ActionBar-Announcer.Options.Sound-For-All-Players.Pitch");
    	        for (Player all: Bukkit.getServer().getOnlinePlayers()) {
    	        	all.playSound(all.getLocation(), XSound.getSound(sound, "ActionBar-Announcer.Options.Sound-For-All-Players.Sound"), volume, pitch);
    	        }
            }
            
            /*
             * Write in the chat
             */
            if (ActionbarAnnouncerConfig.getConfig().getBoolean("ActionBar-Announcer.Options.Write-In-The-Chat-The-Announce")) {
            	Bukkit.broadcastMessage(msgbc);
            }
            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!ActionbarAnnouncerConfig.getConfig().getBoolean("ActionBar-Announcer.Enable")) {
            if (ActionbarAnnouncerConfig.getConfig().getBoolean("ActionBar-Announcer.Disable-Message")) {
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

        // The command		

        if (args.length == 0) {

            Titles.sendTitle(p, 20, 75, 20, " ", "§cError");

            return true;
        }

        String msgbc = "";

        if (args.length >= 1) {
            if (!args[0].isEmpty()) {
                for (int i = 1; i < args.length; i++) {
                    if (!Objects.equals(msgbc, "")) {
                        msgbc = msgbc + " ";
                    }
                    msgbc = msgbc + args[i];
                }
            }
        }

        msgbc = args[0] + " " + msgbc;

        msgbc = msgbc.replaceAll("&", "§");
        
        for (Player all : Bukkit.getServer().getOnlinePlayers()) {
        	ActionBar.sendActionBar(all, msgbc, ActionbarAnnouncerConfig.getConfig().getInt("ActionBar-Announcer.Action-Bar.Stay"));
        }
        
        /*
         * Sound
         */
        if (ActionbarAnnouncerConfig.getConfig().getBoolean("ActionBar-Announcer.Options.Sound-For-All-Players.Enable")) {
	        String sound = ActionbarAnnouncerConfig.getConfig().getString("ActionBar-Announcer.Options.Sound-For-All-Players.Sound");
	        int volume = ActionbarAnnouncerConfig.getConfig().getInt("ActionBar-Announcer.Options.Sound-For-All-Players.Volume");
	        int pitch = ActionbarAnnouncerConfig.getConfig().getInt("ActionBar-Announcer.Options.Sound-For-All-Players.Pitch");
	        for (Player all: Bukkit.getServer().getOnlinePlayers()) {
	        	all.playSound(all.getLocation(), XSound.getSound(sound, "ActionBar-Announcer.Options.Sound-For-All-Players.Sound"), volume, pitch);
	        }
        }
        
        /*
         * Write in the chat
         */
        if (ActionbarAnnouncerConfig.getConfig().getBoolean("ActionBar-Announcer.Options.Write-In-The-Chat-The-Announce")) {
        	Bukkit.broadcastMessage(msgbc);
        }
        
        return false;
    }
}