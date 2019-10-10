package fr.dianox.hawn.utility;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.config.ConfigGeneral;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;

public class MessageUtils {
	
    @SuppressWarnings({
        "deprecation"
    })
    public static void ReplaceCharMessagePlayer(String str, Player player) {
        Player p = player;

        String perm = "";
        String world = "";
        
        if (str.startsWith("<world>") && str.contains("</world>")) {
        	world = StringUtils.substringBetween(str, "<world>", "</world>");
        	str = str.replace("<world>" + world + "</world> ", "");

            if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                return;
            }
        }
        
        if (str.startsWith("<perm>") && str.contains("</perm>")) {
            perm = StringUtils.substringBetween(str, "<perm>", "</perm>");
            str = str.replace("<perm>" + perm + "</perm> ", "");

            if (!p.hasPermission(perm)) {
                return;
            }
        }

        if (str.startsWith("json:")) {

            str = str.replace("json:", "");
            str = PlaceHolders.ReplaceMainplaceholderP(str, player);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, player);
            }
            BaseComponent[] bc = ComponentSerializer.parse(str);
            p.spigot().sendMessage(bc);
        } else if (str.startsWith("[send-title]: ")) {

            str = str.replace("[send-title]: ", "");
            str = str.replaceAll("&", "§");

            str = PlaceHolders.ReplaceMainplaceholderP(str, player);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, player);
            }
            Boolean activate = false;

            String title = "";
            String subtitle = "";

            if (str.contains("//n")) {
                String[] parts = str.split("//n");
                title = parts[0];
                subtitle = parts[1];

                title = title.replaceAll("//n", "");
                subtitle = subtitle.replaceAll("//n", "");

                activate = true;
            }

            if (activate == false) {
                TitleUtils.sendTitle(p, 20, 150, 75, str);
            } else {
                TitleUtils.sendTitle(p, 20, 150, 75, title);
                TitleUtils.sendSubtitle(p, 20, 150, 75, subtitle);
            }
        } else if (str.startsWith("[send-title[")) {

            str = str.replace("[send-title[", "");

            str = PlaceHolders.ReplaceMainplaceholderP(str, player);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, player);
            }
            String[] parts = str.split("]]: ");

            Boolean activate = false;

            String title = "";
            String subtitle = "";

            if (str.contains("//n")) {
                String[] part = parts[1].split("//n");
                title = part[0];
                subtitle = part[1];

                title = title.replaceAll("//n", "");
                subtitle = subtitle.replaceAll("//n", "");

                activate = true;
            }

            if (activate == false) {
                TitleUtils.sendTitle(p, 20, Integer.valueOf(parts[0]), 75, parts[1]);
            } else {
                TitleUtils.sendTitle(p, 20, Integer.valueOf(parts[0]), 75, title);
                TitleUtils.sendSubtitle(p, 20, Integer.valueOf(parts[0]), 75, subtitle);
            }
        } else if (str.startsWith("[send-actionbar]: ")) {

            str = str.replace("[send-actionbar]: ", "");
            str = str.replaceAll("&", "§");

            str = PlaceHolders.ReplaceMainplaceholderP(str, player);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, player);
            }
            ActionBar.sendActionBar(p, str);
        } else if (str.startsWith("[send-actionbar[")) {

            str = str.replace("[send-actionbar[", "");

            str = PlaceHolders.ReplaceMainplaceholderP(str, player);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, player);
            }
            String[] parts = str.split("]]: ");

            ActionBar.sendActionBar(p, parts[1], Integer.valueOf(parts[0]));
        } else {

            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, player);
            }
            str = PlaceHolders.ReplaceMainplaceholderP(str, player);
            str = str.replaceAll("&", "§");

            if (str.contains("<--center-->")) {
                sendCenteredMessage(p, str);
                return;
            }
            
            p.sendMessage(str);
        }
    }


    @SuppressWarnings({
        "deprecation"
    })
    public static void ReplaceCharBroadcastPlayer(String str, Player player) {

        String perm = "";
        Boolean yes = false;

        String world = "";
        Boolean yes2 = false;
        
        if (str.startsWith("<world>") && str.contains("</world>")) {
        	world = StringUtils.substringBetween(str, "<world>", "</world>");
        	str = str.replace("<world>" + world + "</world> ", "");

        	yes2 = true;
        }
        
        if (str.startsWith("<perm>") && str.contains("</perm>")) {
            perm = StringUtils.substringBetween(str, "<perm>", "</perm>");
            str = str.replace("<perm>" + perm + "</perm> ", "");

            yes = true;
        }

        if (str.startsWith("json:")) {
            for (Player p: Bukkit.getServer().getOnlinePlayers()) {

                if (yes) {
                    if (!p.hasPermission(perm)) {
                        continue;
                    }
                }
                
                if (yes2) {
                	 if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                         continue;
                     }
                }

                str = str.replace("json:", "");
                str = PlaceHolders.ReplaceMainplaceholderP(str, player);
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                    str = PlaceholderAPI.setPlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                    str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                    str = PlaceHolders.BattleLevelPO(str, player);
                }
                BaseComponent[] bc = ComponentSerializer.parse(str);
                p.spigot().sendMessage(bc);
            }
        } else if (str.startsWith("[send-title]: ")) {
            for (Player p: Bukkit.getServer().getOnlinePlayers()) {

                if (yes) {
                    if (!p.hasPermission(perm)) {
                        continue;
                    }
                }
                
                if (yes2) {
                	if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                        continue;
                    }
                }

                str = str.replace("[send-title]: ", "");
                str = str.replaceAll("&", "§");

                str = PlaceHolders.ReplaceMainplaceholderP(str, player);
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                    str = PlaceholderAPI.setPlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                    str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                    str = PlaceHolders.BattleLevelPO(str, player);
                }
                Boolean activate = false;

                String title = "";
                String subtitle = "";

                if (str.contains("//n")) {
                    String[] parts = str.split("//n");
                    title = parts[0];
                    subtitle = parts[1];

                    title = title.replaceAll("//n", "");
                    subtitle = subtitle.replaceAll("//n", "");

                    activate = true;
                }

                if (activate == false) {
                    TitleUtils.sendTitle(p, 20, 150, 75, str);
                } else {
                    TitleUtils.sendTitle(p, 20, 150, 75, title);
                    TitleUtils.sendSubtitle(p, 20, 150, 75, subtitle);
                }
            }
        } else if (str.startsWith("[send-title[")) {
            for (Player p: Bukkit.getServer().getOnlinePlayers()) {

                if (yes) {
                    if (!p.hasPermission(perm)) {
                        continue;
                    }
                }
                
                if (yes2) {
                	if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                        continue;
                    }
                }

                str = str.replace("[send-title[", "");

                str = PlaceHolders.ReplaceMainplaceholderP(str, player);
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                    str = PlaceholderAPI.setPlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                    str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                    str = PlaceHolders.BattleLevelPO(str, player);
                }
                String[] parts = str.split("]]: ");

                Boolean activate = false;

                String title = "";
                String subtitle = "";

                if (str.contains("//n")) {
                    String[] part = parts[1].split("//n");
                    title = part[0];
                    subtitle = part[1];

                    title = title.replaceAll("//n", "");
                    subtitle = subtitle.replaceAll("//n", "");

                    activate = true;
                }

                if (activate == false) {
                    TitleUtils.sendTitle(p, 20, Integer.valueOf(parts[0]), 75, parts[1]);
                } else {
                    TitleUtils.sendTitle(p, 20, Integer.valueOf(parts[0]), 75, title);
                    TitleUtils.sendSubtitle(p, 20, Integer.valueOf(parts[0]), 75, subtitle);
                }
            }
        } else if (str.startsWith("[send-actionbar]: ")) {
            for (Player p: Bukkit.getServer().getOnlinePlayers()) {

                if (yes) {
                    if (!p.hasPermission(perm)) {
                        continue;
                    }
                }
                
                if (yes2) {
                	if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                        continue;
                    }
                }

                str = str.replace("[send-actionbar]: ", "");
                str = str.replaceAll("&", "§");

                str = PlaceHolders.ReplaceMainplaceholderP(str, player);
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                    str = PlaceholderAPI.setPlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                    str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                    str = PlaceHolders.BattleLevelPO(str, player);
                }
                ActionBar.sendActionBar(p, str);
            }
        } else if (str.startsWith("[send-actionbar[")) {
            for (Player p: Bukkit.getServer().getOnlinePlayers()) {

                if (yes) {
                    if (!p.hasPermission(perm)) {
                        continue;
                    }
                }
                
                if (yes2) {
                	if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                        continue;
                    }
                }

                str = str.replace("[send-actionbar[", "");

                str = PlaceHolders.ReplaceMainplaceholderP(str, player);
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                    str = PlaceholderAPI.setPlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                    str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                    str = PlaceHolders.BattleLevelPO(str, player);
                }
                String[] parts = str.split("]]: ");

                ActionBar.sendActionBar(p, parts[1], Integer.valueOf(parts[0]));
            }
        } else {

            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                str = PlaceholderAPI.setPlaceholders(player, str);
            }

            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(player, str);
            }

            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, player);
            }

            str = PlaceHolders.ReplaceMainplaceholderP(str, player);
            str = str.replaceAll("&", "§");

            if (str.contains("<--center-->")) {
                for (Player p: Bukkit.getServer().getOnlinePlayers()) {
                    if (yes) {
                        if (!p.hasPermission(perm)) {
                            continue;
                        }
                    }
                    
                    if (yes2) {
                    	if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                            continue;
                        }
                    }

                    sendCenteredMessage(p, str);
                }
                return;
            }

            for (Player p: Bukkit.getServer().getOnlinePlayers()) {
                if (yes) {
                    if (!p.hasPermission(perm)) {
                        continue;
                    }
                }
                
                if (yes2) {
                	if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                        continue;
                    }
                }
                
                p.sendMessage(str);
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static void ReplaceCharBroadcastNoPlayer(String str) {

        String perm = "";
        Boolean yes = false;

        String world = "";
        Boolean yes2 = false;
        
        if (str.startsWith("<world>") && str.contains("</world>")) {
        	world = StringUtils.substringBetween(str, "<world>", "</world>");
        	str = str.replace("<world>" + world + "</world> ", "");

        	yes2 = true;
        }
        
        if (str.startsWith("<perm>") && str.contains("</perm>")) {
            perm = StringUtils.substringBetween(str, "<perm>", "</perm>");
            str = str.replace("<perm>" + perm + "</perm> ", "");

            yes = true;
        }

        if (str.startsWith("json:")) {
            for (Player p: Bukkit.getServer().getOnlinePlayers()) {

                if (yes) {
                    if (!p.hasPermission(perm)) {
                        continue;
                    }
                }
                
                if (yes2) {
                	if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                        continue;
                    }
                }

                str = str.replace("json:", "");
                str = PlaceHolders.ReplaceMainplaceholderP(str, p);
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                    str = PlaceholderAPI.setPlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                    str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                    str = PlaceHolders.BattleLevelPO(str, p);
                }
                BaseComponent[] bc = ComponentSerializer.parse(str);
                p.spigot().sendMessage(bc);
            }
        } else if (str.startsWith("[send-title]: ")) {
            for (Player p: Bukkit.getServer().getOnlinePlayers()) {

                if (yes) {
                    if (!p.hasPermission(perm)) {
                        continue;
                    }
                }
                
                if (yes2) {
                	if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                        continue;
                    }
                }

                str = str.replace("[send-title]: ", "");
                str = str.replaceAll("&", "§");

                str = PlaceHolders.ReplaceMainplaceholderP(str, p);
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                    str = PlaceholderAPI.setPlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                    str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                    str = PlaceHolders.BattleLevelPO(str, p);
                }
                Boolean activate = false;

                String title = "";
                String subtitle = "";

                if (str.contains("//n")) {
                    String[] parts = str.split("//n");
                    title = parts[0];
                    subtitle = parts[1];

                    title = title.replaceAll("//n", "");
                    subtitle = subtitle.replaceAll("//n", "");

                    activate = true;
                }

                if (activate == false) {
                    TitleUtils.sendTitle(p, 20, 150, 75, str);
                } else {
                    TitleUtils.sendTitle(p, 20, 150, 75, title);
                    TitleUtils.sendSubtitle(p, 20, 150, 75, subtitle);
                }
            }
        } else if (str.startsWith("[send-title[")) {
            for (Player p: Bukkit.getServer().getOnlinePlayers()) {

                if (yes) {
                    if (!p.hasPermission(perm)) {
                        continue;
                    }
                }
                
                if (yes2) {
                	if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                        continue;
                    }
                }

                str = str.replace("[send-title[", "");

                str = PlaceHolders.ReplaceMainplaceholderP(str, p);
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                    str = PlaceholderAPI.setPlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                    str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                    str = PlaceHolders.BattleLevelPO(str, p);
                }
                String[] parts = str.split("]]: ");

                Boolean activate = false;

                String title = "";
                String subtitle = "";

                if (str.contains("//n")) {
                    String[] part = parts[1].split("//n");
                    title = part[0];
                    subtitle = part[1];

                    title = title.replaceAll("//n", "");
                    subtitle = subtitle.replaceAll("//n", "");

                    activate = true;
                }

                if (activate == false) {
                    TitleUtils.sendTitle(p, 20, Integer.valueOf(parts[0]), 75, parts[1]);
                } else {
                    TitleUtils.sendTitle(p, 20, Integer.valueOf(parts[0]), 75, title);
                    TitleUtils.sendSubtitle(p, 20, Integer.valueOf(parts[0]), 75, subtitle);
                }
            }
        } else if (str.startsWith("[send-actionbar]: ")) {
            for (Player p: Bukkit.getServer().getOnlinePlayers()) {

                if (yes) {
                    if (!p.hasPermission(perm)) {
                        continue;
                    }
                }
                
                if (yes2) {
                	if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                        continue;
                    }
                }

                str = str.replace("[send-actionbar]: ", "");
                str = str.replaceAll("&", "§");

                str = PlaceHolders.ReplaceMainplaceholderP(str, p);
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                    str = PlaceholderAPI.setPlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                    str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                    str = PlaceHolders.BattleLevelPO(str, p);
                }
                ActionBar.sendActionBar(p, str);
            }
        } else if (str.startsWith("[send-actionbar[")) {
            for (Player p: Bukkit.getServer().getOnlinePlayers()) {

                if (yes) {
                    if (!p.hasPermission(perm)) {
                        continue;
                    }
                }
                
                if (yes2) {
                	if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                        continue;
                    }
                }

                str = str.replace("[send-actionbar[", "");

                str = PlaceHolders.ReplaceMainplaceholderP(str, p);
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                    str = PlaceholderAPI.setPlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                    str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                    str = PlaceHolders.BattleLevelPO(str, p);
                }
                String[] parts = str.split("]]: ");

                ActionBar.sendActionBar(p, parts[1], Integer.valueOf(parts[0]));
            }
        } else {
            for (Player p: Bukkit.getServer().getOnlinePlayers()) {

                if (yes) {
                    if (!p.hasPermission(perm)) {
                        continue;
                    }
                }
                
                if (yes2) {
                	if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                        continue;
                    }
                }

                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                    str = PlaceholderAPI.setPlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                    str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
                }
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                    str = PlaceHolders.BattleLevelPO(str, p);
                }
                str = PlaceHolders.ReplaceMainplaceholderP(str, p);
                str = str.replaceAll("&", "§");

                if (str.contains("<--center-->")) {
                    sendCenteredMessage(p, str);
                } else {
                    p.sendMessage(str);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static void ReplaceCharBroadcastGeneral(String str, Player p) {
        if (str.startsWith("json:")) {
            str = str.replace("json:", "");
            str = PlaceHolders.ReplaceMainplaceholderP(str, p);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, p);
            }
            BaseComponent[] bc = ComponentSerializer.parse(str);
            Bukkit.spigot().broadcast(bc);

            StringBuilder sb = new StringBuilder();
            for (BaseComponent b: bc) {
                sb.append(b.toLegacyText());
            }

            Bukkit.getConsoleSender().sendMessage(sb.toString());
        } else if (str.startsWith("[send-title]: ")) {
            str = str.replace("[send-title]: ", "");
            str = str.replaceAll("&", "§");

            str = PlaceHolders.ReplaceMainplaceholderP(str, p);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, p);
            }
            Boolean activate = false;

            String title = "";
            String subtitle = "";

            if (str.contains("//n")) {
                String[] parts = str.split("//n");
                title = parts[0];
                subtitle = parts[1];

                title = title.replaceAll("//n", "");
                subtitle = subtitle.replaceAll("//n", "");

                activate = true;
            }

            if (activate == false) {
                for (Player p1: Bukkit.getServer().getOnlinePlayers()) {
                    TitleUtils.sendTitle(p1, 20, 150, 75, str);
                }
            } else {
                for (Player p1: Bukkit.getServer().getOnlinePlayers()) {
                    TitleUtils.sendTitle(p1, 20, 150, 75, title);
                    TitleUtils.sendSubtitle(p1, 20, 150, 75, subtitle);
                }
            }
        } else if (str.startsWith("[send-title[")) {
            str = str.replace("[send-title[", "");

            str = PlaceHolders.ReplaceMainplaceholderP(str, p);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, p);
            }
            String[] parts = str.split("]]: ");

            Boolean activate = false;

            String title = "";
            String subtitle = "";

            if (str.contains("//n")) {
                String[] part = parts[1].split("//n");
                title = part[0];
                subtitle = part[1];

                title = title.replaceAll("//n", "");
                subtitle = subtitle.replaceAll("//n", "");

                activate = true;
            }

            if (activate == false) {
                for (Player p1: Bukkit.getServer().getOnlinePlayers()) {
                    TitleUtils.sendTitle(p1, 20, Integer.valueOf(parts[0]), 75, parts[1]);
                }
            } else {
                for (Player p1: Bukkit.getServer().getOnlinePlayers()) {
                    TitleUtils.sendTitle(p1, 20, Integer.valueOf(parts[0]), 75, title);
                    TitleUtils.sendSubtitle(p1, 20, Integer.valueOf(parts[0]), 75, subtitle);
                }
            }
        } else if (str.startsWith("[send-actionbar]: ")) {
            str = str.replace("[send-actionbar]: ", "");
            str = str.replaceAll("&", "§");

            str = PlaceHolders.ReplaceMainplaceholderP(str, p);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, p);
            }
            for (Player p1: Bukkit.getServer().getOnlinePlayers()) {
                ActionBar.sendActionBar(p1, str);
            }
        } else if (str.startsWith("[send-actionbar[")) {
            str = str.replace("[send-actionbar[", "");

            str = PlaceHolders.ReplaceMainplaceholderP(str, p);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, p);
            }
            String[] parts = str.split("]]: ");

            for (Player p1: Bukkit.getServer().getOnlinePlayers()) {
                ActionBar.sendActionBar(p1, parts[1], Integer.valueOf(parts[0]));
            }
        } else {
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, p);
            }
            str = PlaceHolders.ReplaceMainplaceholderP(str, p);
            str = str.replaceAll("&", "§");

            if (str.contains("<--center-->")) {
                for (Player p1: Bukkit.getServer().getOnlinePlayers()) {
                    sendCenteredMessage(p1, str);
                }
                str = str.replace("<--center-->", "");
                Bukkit.getConsoleSender().sendMessage(str);
                return;
            }

            Bukkit.broadcastMessage(str);
        }
    }

    public static void ReplaceMessageForConsole(String str) {
        if (str.startsWith("json:")) {
            str = str.replace("json:", "");
            str = PlaceHolders.ReplaceMainplaceholderC(str);
            str = str.replaceAll("&", "§");

            BaseComponent[] bc = ComponentSerializer.parse(str);

            StringBuilder sb = new StringBuilder();
            for (BaseComponent b: bc) {
                sb.append(b.toLegacyText());
            }

            Bukkit.getConsoleSender().sendMessage(sb.toString());
        } else {
            str = PlaceHolders.ReplaceMainplaceholderC(str);
            str = str.replaceAll("&", "§");

            Bukkit.getConsoleSender().sendMessage(str);
        }
    }

    public static void ReplaceMessageForConsolePingCommand(String str, CommandSender sender, Player target) {
        if (str.startsWith("json:")) {
            str = str.replace("json:", "");
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                str = PlaceholderAPI.setPlaceholders(target, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(target, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, target);
            }
            str = PlaceHolders.ReplaceMainplaceholderP(str, target);
            str = str.replaceAll("&", "§");

            BaseComponent[] bc = ComponentSerializer.parse(str);

            StringBuilder sb = new StringBuilder();
            for (BaseComponent b: bc) {
                sb.append(b.toLegacyText());
            }

            Bukkit.getConsoleSender().sendMessage(sb.toString());
        } else {
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                str = PlaceholderAPI.setPlaceholders(target, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(target, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, target);
            }
            str = PlaceHolders.ReplaceMainplaceholderP(str, target);
            str = str.replaceAll("&", "§");

            Bukkit.getConsoleSender().sendMessage(str);
        }
    }

    /*
     * Special features
     */
    private final static int CENTER_PX = 154;

    public static void sendCenteredMessage(Player player, String message) {
        if (message == null || message.equals("")) player.sendMessage("");
        message = ChatColor.translateAlternateColorCodes('&', message);

        message = message.replace("<--center-->", "");

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c: message.toCharArray()) {
            if (c == '§') {
                previousCode = true;
                continue;
            } else if (previousCode == true) {
                previousCode = false;
                if (c == 'l' || c == 'L') {
                    isBold = true;
                    continue;
                } else isBold = false;
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }
        player.sendMessage(sb.toString() + message);
    }

    // Just for some messages

    // >> No permissions
 // >> No permissions
    public static void MessageNoPermission(Player player, String p) {
        if (ConfigMOStuff.getConfig().getBoolean("Error.No-Permissions.Enable")) {
            for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Permissions.Messages")) {

                msg = msg.replaceAll("%noperm%", p);

                ReplaceCharMessagePlayer(msg, player);
            }
        }
    }

    // > No Spawn
    public static void MessageNoSpawn(Player player) {
        if (ConfigMOStuff.getConfig().getBoolean("Error.No-Spawn.Enable")) {
            for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Spawn.Messages")) {
                ReplaceCharMessagePlayer(msg, player);
            }
        }
    }

    // > No Player
    public static void PlayerDoesntExist(Player player) {
        if (ConfigMOStuff.getConfig().getBoolean("Error.No-Players.Enable")) {
            for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Players.Messages")) {
                ReplaceCharMessagePlayer(msg, player);
            }
        }
    }

    // > No Page found
    public static void NoPageFound(Player player) {
        if (ConfigMOStuff.getConfig().getBoolean("Error.No-Page-Found.Enable")) {
            for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Page-Found.Messages")) {
                ReplaceCharMessagePlayer(msg, player);
            }
        }
    }

    // > No category
    public static void NoCategory(Player player) {
        if (ConfigMOStuff.getConfig().getBoolean("Error.No-Category.Enable")) {
            for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Category.Messages")) {
                ReplaceCharMessagePlayer(msg, player);
            }
        }
    }

    // > Use number
    public static void UseNumber(Player player) {
        if (ConfigMOStuff.getConfig().getBoolean("Error.Use-Number.Enable")) {
            for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Use-Number.Messages")) {
                ReplaceCharMessagePlayer(msg, player);
            }
        }
    }

}