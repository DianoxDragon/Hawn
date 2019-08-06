package fr.Dianox.Hawn.Commands.Features.Specials;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.Dianox.Hawn.Utility.ActionBar;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.TitleUtils;
import fr.Dianox.Hawn.Utility.XSound;
import fr.Dianox.Hawn.Utility.Config.Commands.ActionbarAnnouncerConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;

public class ABAnnouncerCommand extends BukkitCommand {

    String GeneralPermission = "hawn.command.actionbarannouncer";

    public ABAnnouncerCommand(String name) {
        super(name);
        this.description = "Broadcast an actionbar";
        this.usageMessage = "/bacast [msg]";
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        // >>> Executed by the console
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cAnother day for sure");
            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!ActionbarAnnouncerConfig.getConfig().getBoolean("ActionBar-Announcer.Enable")) {
            if (ActionbarAnnouncerConfig.getConfig().getBoolean("ActionBar-Announcer.Disable-Message")) {
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

        // The command		

        if (args.length == 0) {

            TitleUtils.sendTitle(p, 20, 75, 20, " ");
            TitleUtils.sendSubtitle(p, 20, 75, 20, "§cError");

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
        
        ActionBar.sendActionBarToAllPlayers(msgbc, ActionbarAnnouncerConfig.getConfig().getInt("ActionBar-Announcer.Action-Bar.Stay"));
        
        /*
         * Sound
         */
        if (ActionbarAnnouncerConfig.getConfig().getBoolean("ActionBar-Announcer.Options.Sound-For-All-Players.Enable")) {
	        String sound = ActionbarAnnouncerConfig.getConfig().getString("ActionBar-Announcer.Options.Sound-For-All-Players.Sound");
	        int volume = ActionbarAnnouncerConfig.getConfig().getInt("ActionBar-Announcer.Options.Sound-For-All-Players.Volume");
	        int pitch = ActionbarAnnouncerConfig.getConfig().getInt("ActionBar-Announcer.Options.Sound-For-All-Players.Pitch");
	        for (Player all: Bukkit.getServer().getOnlinePlayers()) {
	        	all.playSound(all.getLocation(), XSound.matchXSound(sound).parseSound(), volume, pitch);
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
