package fr.dianox.hawn.utility;

import fr.dianox.hawn.hook.HooksManager;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtils {

    private static final Pattern HEX_PATTERN = Pattern.compile("#<[A-Fa-f0-9]{6}>");
    private static final Pattern HEX_AMPERSAND_PATTERN = Pattern.compile("&#[A-Fa-f0-9]{6}");

    public static void ClassicMessages(String str, Player p) {
        if (str.startsWith("json:")) {

            str = str.replace("json:", "");
            str = PlaceHolders.ReplaceMainplaceholderP(str, p);
            if (HooksManager.papi()) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (HooksManager.mvdw()) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            BaseComponent[] bc = ComponentSerializer.parse(plain(str));
            p.spigot().sendMessage(bc);
        } else {

            if (HooksManager.papi()) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (HooksManager.mvdw()) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            str = PlaceHolders.ReplaceMainplaceholderP(str, p);
            str = colourTheStuff(str);

            if (str.contains("<--center-->")) {
                sendCenteredMessage(p, str);
                return;
            }

            send(p, str);
        }
    }

    /**
     * Sends a coloured text: MiniMessage on Paper when it has tags, the legacy text otherwise.
     */
    public static void send(CommandSender to, String str) {
        if (RichText.isRich(str)) {
            RichText.send(to, str);
        } else {
            to.sendMessage(plain(str));
        }
    }

    /**
     * The text as shown: the "<" typed by a player (see ConfigEventUtils#noAction) comes back.
     */
    public static String plain(String str) {
        return str == null ? null : str.replace(RichText.LT, '<');
    }

	public static void ClassicMessagesConsoleSupport(String str, Player p) {
        if (str.startsWith("json:")) {
            str = str.replace("json:", "");
            str = PlaceHolders.ReplaceMainplaceholderP(str, p);
            if (HooksManager.papi()) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (HooksManager.mvdw()) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            BaseComponent[] bc = ComponentSerializer.parse(plain(str));
            p.spigot().sendMessage(bc);

            StringBuilder sb = new StringBuilder();
            for (BaseComponent b: bc) {
                sb.append(b.toLegacyText());
            }

            Bukkit.getConsoleSender().sendMessage(sb.toString());
        } else {
            if (HooksManager.papi()) {
                str = PlaceholderAPI.setPlaceholders(p, str);
            }
            if (HooksManager.mvdw()) {
                str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
            }
            str = PlaceHolders.ReplaceMainplaceholderP(str, p);
            str = colourTheStuff(str);

            if (str.contains("<--center-->")) {
                for (Player p1: Bukkit.getServer().getOnlinePlayers()) {
                    sendCenteredMessage(p1, str);
                }
                str = str.replace("<--center-->", "");
                Bukkit.getConsoleSender().sendMessage(plain(str));
                return;
            }

            send(p, str);
        }
    }

    public static void ConsoleMessages(String str) {
        if (str.startsWith("json:")) {
            str = str.replace("json:", "");
            str = PlaceHolders.ReplaceMainplaceholderC(str);
            str = colourTheStuff(str);

            BaseComponent[] bc = ComponentSerializer.parse(plain(str));

            StringBuilder sb = new StringBuilder();
            for (BaseComponent b: bc) {
                sb.append(b.toLegacyText());
            }

            Bukkit.getConsoleSender().sendMessage(sb.toString());
        } else {
            str = PlaceHolders.ReplaceMainplaceholderC(str);
            str = colourTheStuff(str);

            send(Bukkit.getConsoleSender(), str);
        }
    }

    /*
     * Special features
     */
    private final static int CENTER_PX = 154;

    public static void sendCenteredMessage(Player player, String message) {
        if (message == null || message.equals("")) player.sendMessage("");
        message = colourTheStuff(message);

        message = plain(message.replace("<--center-->", ""));

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

                msg = msg.replace("%noperm%", p);

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

    /**
     * Translates the colour codes: "&a", hex colours "&#RRGGBB" and "#<RRGGBB>".
     */
    public static String colourTheStuff(String message) {
        if (message == null) {
            return null;
        }

        return ChatColor.translateAlternateColorCodes('&', colourHex(message));
    }

    /**
     * Only translates the hex colours: "&#RRGGBB" and "#<RRGGBB>".
     */
    public static String colourHex(String message) {
        message = replaceHex(message, HEX_PATTERN, 2);
        return replaceHex(message, HEX_AMPERSAND_PATTERN, 2);
    }

    private static String replaceHex(String message, Pattern pattern, int prefixLength) {
        Matcher matcher = pattern.matcher(message);
        if (!matcher.find()) {
            return message;
        }

        StringBuffer sb = new StringBuffer();
        do {
            String hex = matcher.group().substring(prefixLength, prefixLength + 6);
            matcher.appendReplacement(sb, Matcher.quoteReplacement(ChatColor.of("#" + hex).toString()));
        } while (matcher.find());
        matcher.appendTail(sb);

        return sb.toString();
    }

}