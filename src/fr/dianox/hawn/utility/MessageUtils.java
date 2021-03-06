package fr.dianox.hawn.utility;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtils {

    private static final Pattern HEX_PATTERN = Pattern.compile("#<([A-Fa-f0-9]){6}>");

    public static void ClassicMessages(String str, Player p) {
        if (str.startsWith("json:")) {

            str = str.replace("json:", "");
            str = PlaceHolders.ReplaceMainplaceholderP(str, p);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, p);
            }
            BaseComponent[] bc = ComponentSerializer.parse(str);
            p.spigot().sendMessage(bc);
        } else {

            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, p);
            }
            str = PlaceHolders.ReplaceMainplaceholderP(str, p);
            str = colourTheStuff(str);

            if (str.contains("<--center-->")) {
                sendCenteredMessage(p, str);
                return;
            }
            
            p.sendMessage(str);
        }
    }

	public static void ClassicMessagesConsoleSupport(String str, Player p) {
        if (str.startsWith("json:")) {
            str = str.replace("json:", "");
            str = PlaceHolders.ReplaceMainplaceholderP(str, p);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, p);
            }
            BaseComponent[] bc = ComponentSerializer.parse(str);
            p.spigot().sendMessage(bc);

            StringBuilder sb = new StringBuilder();
            for (BaseComponent b: bc) {
                sb.append(b.toLegacyText());
            }

            Bukkit.getConsoleSender().sendMessage(sb.toString());
        } else {
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable")) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
                str = PlaceHolders.BattleLevelPO(str, p);
            }
            str = PlaceHolders.ReplaceMainplaceholderP(str, p);
            str = colourTheStuff(str);

            if (str.contains("<--center-->")) {
                for (Player p1: Bukkit.getServer().getOnlinePlayers()) {
                    sendCenteredMessage(p1, str);
                }
                str = str.replace("<--center-->", "");
                Bukkit.getConsoleSender().sendMessage(str);
                return;
            }

            p.sendMessage(str);
        }
    }

    public static void ConsoleMessages(String str) {
        if (str.startsWith("json:")) {
            str = str.replace("json:", "");
            str = PlaceHolders.ReplaceMainplaceholderC(str);
            str = colourTheStuff(str);

            BaseComponent[] bc = ComponentSerializer.parse(str);

            StringBuilder sb = new StringBuilder();
            for (BaseComponent b: bc) {
                sb.append(b.toLegacyText());
            }

            Bukkit.getConsoleSender().sendMessage(sb.toString());
        } else {
            str = PlaceHolders.ReplaceMainplaceholderC(str);
            str = colourTheStuff(str);

            Bukkit.getConsoleSender().sendMessage(str);
        }
    }

    /*
     * Special features
     */
    private final static int CENTER_PX = 154;

    public static void sendCenteredMessage(Player player, String message) {
        if (message == null || message.equals("")) player.sendMessage("");
        message = colourTheStuff(message);

        message = message.replace("<--center-->", "");

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c: message.toCharArray()) {
            if (c == '§') {
                previousCode = true;
                continue;
            } else if (previousCode) {
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
    public static void MessageNoPermission(Player player, String p) {
        if (ConfigMMsg.getConfig().getBoolean("Error.No-Permissions.Enable")) {
            for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Permissions.Messages")) {

                msg = msg.replaceAll("%noperm%", p);

                ConfigEventUtils.ExecuteEvent(player, msg, "nopermmessage", "MessageUtils", false);
            }
        }
    }

    // > No Spawn
    public static void MessageNoSpawn(Player player) {
        if (ConfigMMsg.getConfig().getBoolean("Error.No-Spawn.Enable")) {
            for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Spawn.Messages")) {
                ConfigEventUtils.ExecuteEvent(player, msg, "nospawn", "MessageUtils", false);
            }
        }
    }

    // > No Player
    public static void PlayerDoesntExist(Player player) {
        if (ConfigMMsg.getConfig().getBoolean("Error.No-Players.Enable")) {
            for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Players.Messages")) {
                ConfigEventUtils.ExecuteEvent(player, msg, "noplayer", "MessageUtils", false);
            }
        }
    }

    // > No Page found
    public static void NoPageFound(Player player) {
        if (ConfigMMsg.getConfig().getBoolean("Error.No-Page-Found.Enable")) {
            for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Page-Found.Messages")) {
                ConfigEventUtils.ExecuteEvent(player, msg, "nopagefound", "MessageUtils", false);
            }
        }
    }

    // > No category
    public static void NoCategory(Player player) {
        if (ConfigMMsg.getConfig().getBoolean("Error.No-Category.Enable")) {
            for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Category.Messages")) {
                ConfigEventUtils.ExecuteEvent(player, msg, "nocategory", "MessageUtils", false);
            }
        }
    }

    // > Use number
    public static void UseNumber(Player player) {
        if (ConfigMMsg.getConfig().getBoolean("Error.Use-Number.Enable")) {
            for (String msg: ConfigMMsg.getConfig().getStringList("Error.Use-Number.Messages")) {
                ConfigEventUtils.ExecuteEvent(player, msg, "usenumber", "MessageUtils", false);
            }
        }
    }

    public static String colourTheStuff(String message) {

        message = message.replaceAll("&", "§");

        if (Main.getInstance().getVersionUtils().Spigot_Version >= 116) {
            for (Matcher matcher = HEX_PATTERN.matcher(message); matcher.find(); matcher = HEX_PATTERN.matcher(message)) {
                String hexString = matcher.group();
                hexString = "#" + hexString.substring(2, hexString.length() - 1);
                ChatColor hex = ChatColor.of(hexString);
                String before = message.substring(0, matcher.start());
                String after = message.substring(matcher.end());
                message = before + hex + after;
            }
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

}