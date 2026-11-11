package fr.dianox.hawn.modules.menus;

import fr.dianox.hawn.Main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Locale;

/**
 * The menus written the first time (no Menus/ folder): the server selector of the compass, and the profile of the head
 * of the join items, which shows what a menu can do. Since 1.6, their texts are keys of Translations/ ({lang_menu.<key>}),
 * shown in the language of each player, and the language menu is written too.
 */
final class DefaultMenus {

	private DefaultMenus() {}

	static void write(File folder) {
		if (!folder.mkdirs() && !folder.exists()) {
			return;
		}
		// Since 1.6: {lang_menu.<key>}, the text of the key in the language of each player (Translations/)
		String[] t = fr.dianox.hawn.utility.DefaultTranslations.placeholders();

		save(new File(folder, "servers.yml"), servers(t));
		save(new File(folder, "profile.yml"), profile(t));
		writeLanguage(folder);
	}

	/**
	 * The language menu (since 1.6), also written once in the Menus folder of the servers that had it before 1.6
	 * (StockageInfo/LanguageMenu.lock: not again if the admin deletes it).
	 */
	static void writeLanguage(File folder) {
		File lock = new File(Main.getInstance().getDataFolder(), "StockageInfo/LanguageMenu.lock");
		if (lock.exists()) {
			return;
		}
		File file = new File(folder, "language.yml");
		if (!file.exists()) {
			save(file, language(fr.dianox.hawn.utility.DefaultTranslations.placeholders()));
		}
		try {
			lock.getParentFile().mkdirs();
			lock.createNewFile();
		} catch (IOException e) {
			Main.getInstance().getLogger().warning("StockageInfo/LanguageMenu.lock could not be written: " + e);
		}
	}

	// code, name in the language itself, banner
	private static final String[][] LANGUAGES = {
		{"en_US", "English", "RED_BANNER"}, {"fr_FR", "Français", "BLUE_BANNER"}, {"de_DE", "Deutsch", "BLACK_BANNER"},
		{"nl_NL", "Nederlands", "ORANGE_BANNER"}, {"es_ES", "Español", "YELLOW_BANNER"}, {"it_IT", "Italiano", "GREEN_BANNER"},
		{"pt_BR", "Português (Brasil)", "LIME_BANNER"}
	};

	/**
	 * Each language twice on its slot: shown as chosen (the first one whose View-Requirements are met), or to click.
	 */
	private static String language(String[] t) {
		StringBuilder s = new StringBuilder();
		s.append("# The language menu (since 1.6), opened by the book of the profile ([menu]: language) and by /language.\n")
				.append("# [language]: <code> gives this language to the player, [language]: client the one of their game.\n")
				.append("# The texts {lang_menu.<key>} are in Translations/<language>.yml.\n")
				.append("# Documentation: https://hawn.gitbook.io/hawn/help/translating\n")
				.append("Title: ").append(q("&8" + t[31])).append("\n")
				.append("Rows: 3\n")
				.append("Open:\n")
				.append("  Commands: [language]\n")
				.append("  Actions:\n")
				.append("  - '[sounds]: BLOCK_CHEST_OPEN'\n")
				.append("Fill: GRAY_STAINED_GLASS_PANE\n")
				.append("Items:\n");

		int slot = 10;
		for (String[] l : LANGUAGES) {
			String id = l[0].toLowerCase(Locale.ROOT);
			s.append("  ").append(id).append("-chosen:\n")
					.append("    Slot: ").append(slot).append("\n")
					.append("    Material: ").append(l[2]).append("\n")
					.append("    Name: ").append(q("&a» " + l[1] + " «")).append("\n")
					.append("    Lore:\n")
					.append("    - ").append(q("&7" + t[34])).append("\n")
					.append("    Glow: true\n")
					.append("    Priority: 1\n")
					.append("    View-Requirements:\n")
					.append("    - '%player_language_choice% == ").append(l[0]).append("'\n")
					.append("  ").append(id).append(":\n")
					.append("    Slot: ").append(slot).append("\n")
					.append("    Material: ").append(l[2]).append("\n")
					.append("    Name: ").append(q("&e" + l[1])).append("\n")
					.append("    Lore:\n")
					.append("    - ").append(q("&7" + t[35])).append("\n")
					.append("    Priority: 2\n")
					.append("    Actions:\n")
					.append("    - '[language]: ").append(l[0]).append("'\n")
					.append("    - '[refresh]'\n");
			slot++;
		}

		s.append("  # The language of the game of the player (the default one)\n")
				.append("  client-chosen:\n")
				.append("    Slot: 22\n")
				.append("    Material: head:%player%\n")
				.append("    Name: ").append(q("&a» " + t[32] + " «")).append("\n")
				.append("    Lore:\n")
				.append("    - ").append(q("&7" + t[33] + ": &f%player_language%")).append("\n")
				.append("    Glow: true\n")
				.append("    Priority: 1\n")
				.append("    View-Requirements:\n")
				.append("    - '%player_language_choice% == client'\n")
				.append("  client:\n")
				.append("    Slot: 22\n")
				.append("    Material: head:%player%\n")
				.append("    Name: ").append(q("&e" + t[32])).append("\n")
				.append("    Lore:\n")
				.append("    - ").append(q("&7" + t[33])).append("\n")
				.append("    - ").append(q("&7" + t[35])).append("\n")
				.append("    Priority: 2\n")
				.append("    Actions:\n")
				.append("    - '[language]: client'\n")
				.append("    - '[refresh]'\n")
				.append("  back:\n")
				.append("    Slot: 18\n")
				.append("    Material: ARROW\n")
				.append("    Name: ").append(q("&c" + t[22])).append("\n")
				.append("    Lore:\n")
				.append("    - ").append(q("&7" + t[23])).append("\n")
				.append("    Actions:\n")
				.append("    - '[back]'\n")
				.append("  close:\n")
				.append("    Slot: 26\n")
				.append("    Material: BARRIER\n")
				.append("    Name: ").append(q("&c" + t[6])).append("\n")
				.append("    Actions:\n")
				.append("    - '[close]'\n");
		return s.toString();
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
				.append("  # The language of the messages (since 1.6)\n")
				.append("  language:\n")
				.append("    Slot: 31\n")
				.append("    Material: WRITABLE_BOOK\n")
				.append("    Name: ").append(q("&d" + t[29])).append("\n")
				.append("    Lore:\n")
				.append("    - ").append(q("&7" + t[30])).append("\n")
				.append("    - ").append(q("&7" + t[29] + ": &f%player_language%")).append("\n")
				.append("    Actions:\n")
				.append("    - '[menu]: language'\n")
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

}
