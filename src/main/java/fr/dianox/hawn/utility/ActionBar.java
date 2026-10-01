package fr.dianox.hawn.utility;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Action bars through the Spigot API (no NMS, works on every supported version).
 * Messages are not colorized by default.
 */
public final class ActionBar {

    private ActionBar() {}

    public static void sendActionBar(Player player, String message) {
        if (player == null || !player.isOnline()) return;
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message == null ? "" : message));
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

        new BukkitRunnable() {
            long repeater = duration;

            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    return;
                }

                sendActionBar(player, message);
                repeater -= 40L;
                if (repeater - 40L < -20L) cancel();
            }
        }.runTaskTimer(plugin, 0L, 40L);
    }
}
