package fr.dianox.hawn.modules.menus;

import fr.dianox.hawn.Main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Locale;

/**
 * The menus written the first time (no Menus/ folder), in the language of the server: the server selector of the compass,
 * and the profile of the head of the join items, which shows what a menu can do.
 */
final class DefaultMenus {

	private DefaultMenus() {}

	static void write(File folder) {
		if (!folder.mkdirs() && !folder.exists()) {
			return;
		}
		String lang = String.valueOf(Main.LanguageType).toLowerCase(Locale.ROOT);
		String[] t = lang.startsWith("fr") ? FR : lang.startsWith("de") ? DE : lang.startsWith("nl") ? NL : EN;

		save(new File(folder, "servers.yml"), servers(t));
		save(new File(folder, "profile.yml"), profile(t));
	}

	private static void save(File file, String content) {
		try {
			Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			Main.getInstance().getLogger().warning("Menus/" + file.getName() + " could not be written: " + e);
		}
	}

	// A YAML text between single quotes
	private static String q(String s) {
		return "'" + s.replace("'", "''") + "'";
	}

	private static String servers(String[] t) {
		return "# The server selector, opened by the compass of the join items ([menu]: servers) and by /servers.\n"
				+ "# Write the names of your servers (those of your proxy) in [bungee]: and %bungee_<server>%.\n"
				+ "# Documentation: https://hawn.gitbook.io/hawn/features/menus\n"
				+ "Title: " + q("&8" + t[0]) + "\n"
				+ "Rows: 3\n"
				+ "# {anim_<name>} in a title, a name, a lore or a material: its frames, one after the other\n"
				+ "Animations:\n"
				+ "  join:\n"
				+ "    refresh-time-ticks: 10\n"
				+ "    text:\n"
				+ "    - " + q("&e" + t[3]) + "\n"
				+ "    - " + q("&6&l" + t[3]) + "\n"
				+ "Open:\n"
				+ "  Commands: [servers]\n"
				+ "  Actions:\n"
				+ "  - '[sounds]: BLOCK_CHEST_OPEN'\n"
				+ "# Ticks between two updates of the items with Update: true (the number of players)\n"
				+ "Refresh: 40\n"
				+ "Fill: GRAY_STAINED_GLASS_PANE\n"
				+ "Items:\n"
				+ "  survival:\n"
				+ "    Slot: 11\n"
				+ "    Material: GRASS_BLOCK\n"
				+ "    Name: " + q("&a" + t[1]) + "\n"
				+ "    Lore:\n"
				+ "    - " + q("&7%bungee_survival% " + t[2]) + "\n"
				+ "    - ''\n"
				+ "    - '{anim_join}'\n"
				+ "    Update: true\n"
				+ "    Actions:\n"
				+ "    - '[bungee]: survival'\n"
				+ "  minigames:\n"
				+ "    Slot: 13\n"
				+ "    Material: DIAMOND_SWORD\n"
				+ "    Name: " + q("&b" + t[4]) + "\n"
				+ "    Lore:\n"
				+ "    - " + q("&7%bungee_minigames% " + t[2]) + "\n"
				+ "    - ''\n"
				+ "    - '{anim_join}'\n"
				+ "    Update: true\n"
				+ "    Glow: true\n"
				+ "    Actions:\n"
				+ "    - '[bungee]: minigames'\n"
				+ "  you:\n"
				+ "    Slot: 15\n"
				+ "    Material: head:%player%\n"
				+ "    Name: '&6%player%'\n"
				+ "    Lore:\n"
				+ "    - " + q("&7" + t[5] + ": &e%bungee_total%") + "\n"
				+ "    - ''\n"
				+ "    - " + q("&e" + t[7]) + "\n"
				+ "    Update: true\n"
				+ "    Actions:\n"
				+ "    - '[menu]: profile'\n"
				+ "  close:\n"
				+ "    Slot: 26\n"
				+ "    Material: BARRIER\n"
				+ "    Name: " + q("&c" + t[6]) + "\n"
				+ "    Actions:\n"
				+ "    - '[close]'\n";
	}

	/**
	 * Several items on one slot (the first one whose View-Requirements are met), the placeholders of the player, clicks per
	 * type, a condition to click with its message, live items, [menu], [refresh], [back] and the sounds of open and close.
	 */
	private static String profile(String[] t) {
		StringBuilder s = new StringBuilder();
		s.append("# The profile, opened by the head of the join items ([menu]: profile) and by /profile.\n")
				.append("# It shows what a menu can do: the placeholders of the player, several items on one slot (the first one\n")
				.append("# whose View-Requirements are met), the kinds of clicks, a condition to click, live items.\n")
				.append("# Documentation: https://hawn.gitbook.io/hawn/features/menus\n")
				.append("# The title is animated (1.20+): {anim_title}, see Animations below\n")
				.append("Title: '{anim_title}'\n")
				.append("Rows: 5\n")
				.append("Animations:\n")
				.append("  title:\n")
				.append("    refresh-time-ticks: 40\n")
				.append("    text:\n")
				.append("    - ").append(q("&8" + t[8])).append("\n")
				.append("    - ").append(q("&5" + t[8])).append("\n")
				.append("Open:\n")
				.append("  Commands: [profile]\n")
				.append("  Actions:\n")
				.append("  - '[sounds]: BLOCK_CHEST_OPEN'\n")
				.append("Close-Actions:\n")
				.append("- '[sounds]: BLOCK_CHEST_CLOSE'\n")
				.append("Refresh: 40\n")
				.append("Fill: BLACK_STAINED_GLASS_PANE\n")
				.append("Items:\n")
				// The player: their head and their placeholders, updated while the menu is open
				.append("  me:\n")
				.append("    Slot: 4\n")
				.append("    Material: head:%player%\n")
				.append("    Name: '&e%player%'\n")
				.append("    Lore:\n")
				.append("    - ").append(q("&7" + t[9] + ": &f%rank%")).append("\n")
				.append("    - ").append(q("&7" + t[10] + ": &f%player_world%")).append("\n")
				.append("    - ").append(q("&7" + t[11] + ": &f#%player_join_number%")).append("\n")
				.append("    - ").append(q("&7" + t[12] + ": &f%player_connections%")).append("\n")
				.append("    - ").append(q("&7" + t[13] + ": &f%player_level%")).append("\n")
				.append("    Update: true\n");

		// Fly and speed: on, off, or locked without the permission (three items on the same slot)
		toggle(s, t, "fly", 20, "FEATHER", t[14]);
		toggle(s, t, "speed", 22, "SUGAR", t[15]);

		s.append("  # A condition to click, with its message, and an action for each kind of click\n")
				.append("  visibility:\n")
				.append("    Slot: 24\n")
				.append("    Material: ENDER_EYE\n")
				.append("    Name: ").append(q("&e" + t[16])).append("\n")
				.append("    Lore:\n")
				.append("    - ").append(q("&7" + t[17])).append("\n")
				.append("    - ").append(q("&7" + t[18])).append("\n")
				.append("    Click-Requirements:\n")
				.append("      Conditions:\n")
				.append("      - 'permission: hawn.command.optionplayer.pv'\n")
				.append("      Deny:\n")
				.append("      - ").append(q("%prefix% &c" + t[19])).append("\n")
				.append("    Left-Actions:\n")
				.append("    - '[command-player]: option pv'\n")
				.append("    Right-Actions:\n")
				.append("    - '[command-player]: option pv all'\n")
				.append("  servers:\n")
				.append("    Slot: 38\n")
				.append("    Material: COMPASS\n")
				.append("    Name: ").append(q("&b" + t[20])).append("\n")
				.append("    Lore:\n")
				.append("    - ").append(q("&7" + t[5] + ": &f%bungee_total%")).append("\n")
				.append("    Update: true\n")
				.append("    Actions:\n")
				.append("    - '[menu]: servers'\n")
				.append("  online:\n")
				.append("    Slot: 40\n")
				.append("    Material: CLOCK\n")
				.append("    Name: ").append(q("&a" + t[21] + ": &f%online%/%max_players%")).append("\n")
				.append("    Update: true\n")
				.append("  back:\n")
				.append("    Slot: 42\n")
				.append("    Material: ARROW\n")
				.append("    Name: ").append(q("&c" + t[22])).append("\n")
				.append("    Lore:\n")
				.append("    - ").append(q("&7" + t[23])).append("\n")
				.append("    Actions:\n")
				.append("    - '[back]'\n");
		return s.toString();
	}

	private static void toggle(StringBuilder s, String[] t, String option, int slot, String material, String name) {
		String permission = "hawn.command.optionplayer." + option;
		s.append("  # The same slot, three items: the first one whose View-Requirements are met is shown\n")
				.append("  ").append(option).append("-on:\n")
				.append("    Slot: ").append(slot).append("\n")
				.append("    Material: ").append(material).append("\n")
				.append("    Name: ").append(q("&a" + name + ": " + t[24])).append("\n")
				.append("    Lore:\n")
				.append("    - ").append(q("&7" + t[26])).append("\n")
				.append("    Glow: true\n")
				.append("    Priority: 1\n")
				.append("    View-Requirements:\n")
				.append("    - 'permission: ").append(permission).append("'\n")
				.append("    - '%player_option_").append(option).append("_state% == on'\n")
				.append("    Actions:\n")
				.append("    - '[command-player]: option ").append(option).append("'\n")
				.append("    - '[delay[2]]: [refresh]'\n")
				.append("  ").append(option).append("-off:\n")
				.append("    Slot: ").append(slot).append("\n")
				.append("    Material: ").append(material).append("\n")
				.append("    Name: ").append(q("&c" + name + ": " + t[25])).append("\n")
				.append("    Lore:\n")
				.append("    - ").append(q("&7" + t[27])).append("\n")
				.append("    Priority: 2\n")
				.append("    View-Requirements:\n")
				.append("    - 'permission: ").append(permission).append("'\n")
				.append("    Actions:\n")
				.append("    - '[command-player]: option ").append(option).append("'\n")
				.append("    - '[delay[2]]: [refresh]'\n")
				.append("  ").append(option).append("-locked:\n")
				.append("    Slot: ").append(slot).append("\n")
				.append("    Material: GRAY_DYE\n")
				.append("    Name: ").append(q("&7" + name + " &8(" + t[28] + ")")).append("\n")
				.append("    Lore:\n")
				.append("    - ").append(q("&7" + t[19])).append("\n")
				.append("    Priority: 3\n");
	}

	// 0 title of the selector, 1 survival, 2 players, 3 click to join, 4 mini-games, 5 network, 6 close, 7 your profile,
	// 8 title of the profile, 9 rank, 10 world, 11 player number, 12 connections, 13 level, 14 fly, 15 speed,
	// 16 who you see, 17 left click, 18 right click, 19 permission needed, 20 servers, 21 online, 22 back, 23 back lore,
	// 24 on, 25 off, 26 click to turn off, 27 click to turn on, 28 locked
	private static final String[] EN = {"Choose a server", "Survival", "players", "Click to join", "Mini-games", "Network", "Close",
			"Click: your profile", "Profile of %player%", "Rank", "World", "Player number", "Connections", "Level", "Fly", "Speed",
			"Who you see", "Left click: everyone, staff and VIP, nobody", "Right click: everyone again", "You don't have the permission",
			"Servers", "Online", "Back", "The menu opened before, or close", "on", "off", "Click to turn it off", "Click to turn it on", "locked"};
	private static final String[] FR = {"Choisis un serveur", "Survie", "joueurs", "Clique pour rejoindre", "Mini-jeux", "Réseau", "Fermer",
			"Clique : ton profil", "Profil de %player%", "Rang", "Monde", "Joueur numéro", "Connexions", "Niveau", "Vol", "Vitesse",
			"Qui tu vois", "Clic gauche : tout le monde, staff et VIP, personne", "Clic droit : de nouveau tout le monde", "Tu n'as pas la permission",
			"Serveurs", "En ligne", "Retour", "Le menu ouvert avant, ou fermer", "activé", "désactivé", "Clique pour le désactiver", "Clique pour l'activer", "verrouillé"};
	private static final String[] DE = {"Wähle einen Server", "Survival", "Spieler", "Klicke zum Beitreten", "Minispiele", "Netzwerk", "Schließen",
			"Klick: dein Profil", "Profil von %player%", "Rang", "Welt", "Spieler Nummer", "Verbindungen", "Level", "Fliegen", "Geschwindigkeit",
			"Wen du siehst", "Linksklick: alle, Team und VIP, niemand", "Rechtsklick: wieder alle", "Du hast keine Berechtigung",
			"Server", "Online", "Zurück", "Das Menü davor, oder schließen", "an", "aus", "Klicke zum Ausschalten", "Klicke zum Einschalten", "gesperrt"};
	private static final String[] NL = {"Kies een server", "Survival", "spelers", "Klik om te joinen", "Minigames", "Netwerk", "Sluiten",
			"Klik: je profiel", "Profiel van %player%", "Rang", "Wereld", "Speler nummer", "Verbindingen", "Level", "Vliegen", "Snelheid",
			"Wie je ziet", "Linkerklik: iedereen, staff en VIP, niemand", "Rechterklik: weer iedereen", "Je hebt geen permissie",
			"Servers", "Online", "Terug", "Het menu ervoor, of sluiten", "aan", "uit", "Klik om uit te zetten", "Klik om aan te zetten", "vergrendeld"};
}
