package fr.dianox.hawn.paper;

import com.destroystokyo.paper.profile.PlayerProfile;
import fr.dianox.hawn.modules.serverlist.ServerPingEvent;
import fr.dianox.hawn.utility.OfflinePermissions;
import io.papermc.paper.connection.PlayerConfigurationConnection;
import io.papermc.paper.connection.PlayerLoginConnection;
import io.papermc.paper.event.connection.PlayerConnectionValidateLoginEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.UUID;

/**
 * The join on a recent Paper (PlayerConnectionValidateLoginEvent instead of PlayerLoginEvent, which disables
 * the configuration APIs of Paper). There is no Player yet: see OfflinePermissions for the permissions.
 */
public class PaperLogin implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void login(PlayerConnectionValidateLoginEvent e) {
		// Called at the login, then again at the end of the configuration: the server checks it again, so do we
		PlayerProfile profile;
		if (e.getConnection() instanceof PlayerLoginConnection) {
			profile = ((PlayerLoginConnection) e.getConnection()).getAuthenticatedProfile();
		} else if (e.getConnection() instanceof PlayerConfigurationConnection) {
			profile = ((PlayerConfigurationConnection) e.getConnection()).getProfile();
		} else {
			return;
		}

		if (profile == null || profile.getId() == null) {
			return;
		}

		UUID uuid = profile.getId();
		String name = profile.getName() == null ? "" : profile.getName();
		ServerPingEvent.Joining p = new ServerPingEvent.Joining() {
			public UUID uuid() { return uuid; }
			public String name() { return name; }
			public boolean hasPermission(String permission) { return OfflinePermissions.has(uuid, permission); }
		};

		String kick = ServerPingEvent.closedFor(p);
		if (kick != null) {
			e.kickMessage(PaperChat.legacy(kick));
			return;
		}

		if (isFull(e.getKickMessage())) {
			if (ServerPingEvent.canJoinFull(p)) {
				e.allow();
			} else {
				String message = ServerPingEvent.fullMessage(name);
				if (message != null) {
					e.kickMessage(PaperChat.legacy(message));
				}
			}
		}
	}

	// Only the "server full" refusal of the server, never a ban or the whitelist
	private static boolean isFull(Component kick) {
		return kick instanceof TranslatableComponent
				&& ((TranslatableComponent) kick).key().equals("multiplayer.disconnect.server_full");
	}
}
