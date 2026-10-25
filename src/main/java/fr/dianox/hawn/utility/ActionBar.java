package fr.dianox.hawn.utility;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Action bars through the Spigot API (no NMS, works on every supported version).
 * Messages are not colorized by default.
 */
public final class ActionBar {

    private ActionBar() {}

    public static void sendActionBar(Player player, String message) {
        if (player == null || !player.isOnline()) return;
        if (RichText.isRich(message)) {
            RichText.actionBar(player, message);
            return;
        }
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message == null ? "" : MessageUtils.plain(message)));
    }

    public static void sendPlayersActionBar(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) sendActionBar(player, message);
    }

    /**
     * Keeps an action bar on the screen for the given duration (in ticks).
     * Action bars fade after ~2 seconds, so the message is re-sent every 40 ticks.
     */
    public static void sendActionBar(JavaPlugin plugin, Player player, String message, long duration) {
        if (duration < 1) return;

        // Through Tasks (since 1.5.2): the task stops itself with its own handle
        long[] repeater = {duration};
        org.bukkit.scheduler.BukkitTask[] task = new org.bukkit.scheduler.BukkitTask[1];
        task[0] = Tasks.timer(() -> {
            if (!player.isOnline()) {
                Tasks.cancel(task[0]);
                return;
            }

            sendActionBar(player, message);
            repeater[0] -= 40L;
            if (repeater[0] - 40L < -20L) Tasks.cancel(task[0]);
        }, 0L, 40L);
    }
}
