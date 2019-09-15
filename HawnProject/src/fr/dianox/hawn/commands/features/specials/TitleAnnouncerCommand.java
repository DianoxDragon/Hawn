package fr.dianox.hawn.commands.features.specials;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.OtherUtils;
import fr.dianox.hawn.utility.TitleUtils;
import fr.dianox.hawn.utility.XSound;
import fr.dianox.hawn.utility.config.commands.TitleAnnouncerConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;

public class TitleAnnouncerCommand extends BukkitCommand {

    String GeneralPermission = "hawn.command.titleannouncer";

    public TitleAnnouncerCommand(String name) {
        super(name);
        this.description = "Broadcast a title";
        this.usageMessage = "/btcast [msg]";
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        // >>> Executed by the console
        if (!(sender instanceof Player)) {
        	if (args.length == 0) {

                Bukkit.getConsoleSender().sendMessage("§cError");

                return true;
            }

            Boolean activate = false;
            String msgbc = "";

            String title = "";
            String subtitle = "";

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

            if (msgbc.contains("//n")) {
                String[] parts = msgbc.split("//n");
                title = parts[0];
                subtitle = parts[1];

                title = title.replaceAll("//n", "");
                subtitle = subtitle.replaceAll("//n", "");

                activate = true;
            }


            if (activate == false) {
                for (Player all: Bukkit.getServer().getOnlinePlayers()) {
                    TitleUtils.sendTitle(all, TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Title.FadeIn"), TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Title.Stay"), TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Title.FadeOut"), msgbc);
                }
            } else {
                for (Player all: Bukkit.getServer().getOnlinePlayers()) {
                    TitleUtils.sendTitle(all, TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Title.FadeIn"), TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Title.Stay"), TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Title.FadeOut"), title);
                    TitleUtils.sendSubtitle(all, TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Title.FadeIn"), TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Title.Stay"), TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Title.FadeOut"), subtitle);
                }
            }
            
            /*
             * Sound
             */
            if (TitleAnnouncerConfig.getConfig().getBoolean("Title-Announcer.Options.Sound-For-All-Players.Enable")) {
    	        String sound = TitleAnnouncerConfig.getConfig().getString("Title-Announcer.Options.Sound-For-All-Players.Sound");
    	        int volume = TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Options.Sound-For-All-Players.Volume");
    	        int pitch = TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Options.Sound-For-All-Players.Pitch");
    	        for (Player all: Bukkit.getServer().getOnlinePlayers()) {
    	        	all.playSound(all.getLocation(), XSound.matchXSound(sound).parseSound(), volume, pitch);
    	        }
            }
            
            /*
             * Write in the chat
             */
            if (TitleAnnouncerConfig.getConfig().getBoolean("Title-Announcer.Options.Write-In-The-Chat-The-Announce")) {
            	if (activate == false) {
            		msgbc = msgbc.replaceAll("&", "§");
            		Bukkit.broadcastMessage(msgbc);
                } else {
                	title = title.replaceAll("&", "§");
                	subtitle = subtitle.replaceAll("&", "§");
                    	
                	Bukkit.broadcastMessage(title);
                	Bukkit.broadcastMessage(subtitle);
                }
            }
            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!TitleAnnouncerConfig.getConfig().getBoolean("Title-Announcer.Enable")) {
            if (TitleAnnouncerConfig.getConfig().getBoolean("Title-Announcer.Disable-Message")) {
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

        Boolean activate = false;
        String msgbc = "";

        String title = "";
        String subtitle = "";

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

        if (msgbc.contains("//n")) {
            String[] parts = msgbc.split("//n");
            title = parts[0];
            subtitle = parts[1];

            title = title.replaceAll("//n", "");
            subtitle = subtitle.replaceAll("//n", "");

            activate = true;
        }


        if (activate == false) {
            for (Player all: Bukkit.getServer().getOnlinePlayers()) {
                TitleUtils.sendTitle(all, TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Title.FadeIn"), TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Title.Stay"), TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Title.FadeOut"), msgbc);
            }
        } else {
            for (Player all: Bukkit.getServer().getOnlinePlayers()) {
                TitleUtils.sendTitle(all, TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Title.FadeIn"), TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Title.Stay"), TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Title.FadeOut"), title);
                TitleUtils.sendSubtitle(all, TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Title.FadeIn"), TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Title.Stay"), TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Title.FadeOut"), subtitle);
            }
        }
        
        /*
         * Firework cos
         */
        if (TitleAnnouncerConfig.getConfig().getBoolean("Title-Announcer.Options.Firework.Enable")) {
        	for (String s: TitleAnnouncerConfig.getConfig().getStringList("Title-Announcer.Options.Firework.Firework-List")) {
				if (s.startsWith("[FWLU]: ")) {
					
					s = s.replace("[FWLU]: ", "");
					
					OtherUtils.Fireworkmethod(p, s);
				}
			}
        }
        
        /*
         * Sound
         */
        if (TitleAnnouncerConfig.getConfig().getBoolean("Title-Announcer.Options.Sound-For-All-Players.Enable")) {
	        String sound = TitleAnnouncerConfig.getConfig().getString("Title-Announcer.Options.Sound-For-All-Players.Sound");
	        int volume = TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Options.Sound-For-All-Players.Volume");
	        int pitch = TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Options.Sound-For-All-Players.Pitch");
	        for (Player all: Bukkit.getServer().getOnlinePlayers()) {
	        	all.playSound(all.getLocation(), XSound.matchXSound(sound).parseSound(), volume, pitch);
	        }
        }
        
        /*
         * Write in the chat
         */
        if (TitleAnnouncerConfig.getConfig().getBoolean("Title-Announcer.Options.Write-In-The-Chat-The-Announce")) {
        	if (activate == false) {
        		msgbc = msgbc.replaceAll("&", "§");
        		Bukkit.broadcastMessage(msgbc);
            } else {
            	title = title.replaceAll("&", "§");
            	subtitle = subtitle.replaceAll("&", "§");
                	
            	Bukkit.broadcastMessage(title);
            	Bukkit.broadcastMessage(subtitle);
            }
        }
        
        return false;
    }
}