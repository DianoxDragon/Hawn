package fr.Dianox.Hawn.Commands.Features.Specials;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.OtherUtils;
import fr.Dianox.Hawn.Utility.TitleUtils;
import fr.Dianox.Hawn.Utility.XSound;
import fr.Dianox.Hawn.Utility.Config.Commands.TitleAnnouncerConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;

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
            sender.sendMessage("§cAnother day for sure");
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
	        for (int i = 1; i < TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Options.Firework.Amount"); i++) {
	            ArrayList < Color > colors = new ArrayList < Color > ();
	            ArrayList < Color > fade = new ArrayList < Color > ();
	            List < String > lore = TitleAnnouncerConfig.getConfig().getStringList("Title-Announcer.Options.Firework.Colors");
	            List < String > lore2 = TitleAnnouncerConfig.getConfig().getStringList("Title-Announcer.Options.Firework.Fade");
	            for (String l1: lore) {
	                colors.add(OtherUtils.getColor(l1));
	            }
	            for (String l2: lore2) {
	                fade.add(OtherUtils.getColor(l2));
	            }
	            final Firework f = p.getWorld().spawn(p.getLocation().add(0.5D, TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Options.Firework.Height"), 0.5D), Firework.class);
	
	            FireworkMeta fm = f.getFireworkMeta();
	            fm.addEffect(FireworkEffect.builder().flicker(TitleAnnouncerConfig.getConfig().getBoolean("Title-Announcer.Options.Firework.Flicker"))
	                .trail(TitleAnnouncerConfig.getConfig().getBoolean("Title-Announcer.Options.Firework.Trail"))
	                .with(FireworkEffect.Type.valueOf(TitleAnnouncerConfig.getConfig().getString("Title-Announcer.Options.Firework.Type"))).withColor(colors).withFade(fade)
	                .build());
	
	            if (!TitleAnnouncerConfig.getConfig().getBoolean("Title-Announcer.Options.Firework.Instant-explode")) {
	                fm.setPower(TitleAnnouncerConfig.getConfig().getInt("Title-Announcer.Options.Firework.Power"));
	            }
	            f.setFireworkMeta(fm);
	            if (TitleAnnouncerConfig.getConfig().getBoolean("Title-Announcer.Options.Firework.Instant-explode")) {
	                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
	                    public void run() {
	                        f.detonate();
	                    }
	                }, 5L);
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
