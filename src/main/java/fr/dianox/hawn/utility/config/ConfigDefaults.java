package fr.dianox.hawn.utility.config;

import fr.dianox.hawn.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Writes the default values of a configuration file.
 * A new file gets every default value. An existing file only gets the options missing from it
 * (added by a new version of Hawn, or deleted by mistake), its own values are never changed.
 */
public final class ConfigDefaults {

    private static final String WIKI = "https://hawn.gitbook.io/hawn/";

    // First match wins: file (or folder) of the Hawn folder -> page of the documentation (GitBook)
    private static final Map<String, String> PAGES = new LinkedHashMap<>();

    static {
        PAGES.put("spawn.yml", "features/spawns");
        PAGES.put("warplist.yml", "features/warps");
        PAGES.put("World-List.yml", "features/world-manager");
        PAGES.put("command-aliases.yml", "configuration-basics/commands-management");
        PAGES.put("CustomCommand.yml", "features/custom-commands");
        PAGES.put("AutoBroadcast.yml", "features/autobroadcast");
        PAGES.put("ServerList.yml", "features/server-list");
        PAGES.put("Player-Option-General.yml", "features/player-options");
        PAGES.put("Scoreboard-General.yml", "features/scoreboards");
        PAGES.put("Commands/Hawn.yml", "features/admin-tools");
        PAGES.put("Commands/Spawn.yml", "features/spawns");
        PAGES.put("Commands/Warp-SetWarp.yml", "features/warps");
        PAGES.put("Commands/PlayerOption.yml", "features/player-options");
        PAGES.put("Commands/", "reference/commands");
        PAGES.put("Events/OnJoin.yml", "features/join-and-quit");
        PAGES.put("Events/JoinQuitCommand.yml", "features/join-and-quit");
        PAGES.put("Events/Chat.yml", "features/chat");
        PAGES.put("Events/OnCommands.yml", "configuration-basics/commands-management");
        PAGES.put("Events/WorldEvent.yml", "features/world-events");
        PAGES.put("Events/VoidTP.yml", "features/void-tp");
        PAGES.put("Events/PlayerWorldChange.yml", "features/world-change");
        PAGES.put("Events/OtherFeatures.yml", "features/lobby-fun");
        PAGES.put("Events/", "features/protections");
        PAGES.put("Cosmetics-Fun/Utility/Book-List.yml", "features/custom-join-items");
        PAGES.put("Cosmetics-Fun/Utility/Emojis-List.yml", "features/chat");
        PAGES.put("Cosmetics-Fun/", "features/lobby-fun");
        PAGES.put("CustomJoinItem/", "features/custom-join-items");
        PAGES.put("Tablist/", "features/tablist");
        PAGES.put("Messages/", "help/translating");
        PAGES.put("", "getting-started/files");
    }

    private ConfigDefaults() {}

    /**
     * @param file        the file
     * @param loaded      the content of the file, as loaded
     * @param defaults    the default values
     * @param collections the sections filled by the user (items, messages, emojis...): once they exist,
     *                    nothing is added in them, so a deleted example never comes back
     * @return the configuration to use
     */
    public static YamlConfiguration apply(File file, YamlConfiguration loaded, YamlConfiguration defaults, String... collections) {
        String name = relativeName(file);

        if (!file.exists()) {
            defaults.options().header(header(name));
            save(defaults, file);
            return defaults;
        }

        // A file with a YAML error is loaded empty: it must not be overwritten with the default values
        if (loaded.getKeys(false).isEmpty() && file.length() > 0) {
            try {
                new YamlConfiguration().load(file);
            } catch (IOException | InvalidConfigurationException e) {
                Bukkit.getLogger().warning("[Hawn] " + name + " can't be read (YAML error), its missing options were not added: " + e.getMessage());
                return loaded;
            }
        }

        List<String> added = new ArrayList<>();
        for (String path : defaults.getKeys(true)) {
            if (defaults.isConfigurationSection(path) || loaded.contains(path)) continue;
            if (inCollection(loaded, path, collections) || hiddenByValue(loaded, path)) continue;

            loaded.set(path, defaults.get(path));
            added.add(path);
        }

        if (!added.isEmpty()) {
            if (loaded.options().header() == null || loaded.options().header().isEmpty()) {
                loaded.options().header(header(name));
            }
            save(loaded, file);
            Bukkit.getLogger().info("[Hawn] " + name + ": " + added.size() + " missing option(s) added: "
                + String.join(", ", added.size() > 10 ? added.subList(0, 10) : added) + (added.size() > 10 ? "..." : ""));
        }
        return loaded;
    }

    private static boolean inCollection(YamlConfiguration loaded, String path, String[] collections) {
        for (String collection : collections) {
            if (path.startsWith(collection + ".") && loaded.contains(collection)) {
                return true;
            }
        }
        return false;
    }

    // "a.b.c" can't be added when the user wrote "a.b: something"
    private static boolean hiddenByValue(YamlConfiguration loaded, String path) {
        int dot = path.indexOf('.');
        while (dot > 0) {
            String parent = path.substring(0, dot);
            if (loaded.contains(parent) && !loaded.isConfigurationSection(parent)) {
                return true;
            }
            dot = path.indexOf('.', dot + 1);
        }
        return false;
    }

    private static void save(YamlConfiguration config, File file) {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String relativeName(File file) {
        String folder = Main.getInstance().getDataFolder().getAbsolutePath();
        String path = file.getAbsolutePath();
        if (path.startsWith(folder)) {
            path = path.substring(folder.length() + 1);
        }
        return path.replace('\\', '/');
    }

    private static String header(String name) {
        String page = "getting-started/files";
        for (Map.Entry<String, String> entry : PAGES.entrySet()) {
            if (entry.getKey().endsWith("/") ? name.startsWith(entry.getKey()) : name.equals(entry.getKey()) || entry.getKey().isEmpty()) {
                page = entry.getValue();
                break;
            }
        }
        return "Hawn - " + name + "\n"
            + "Documentation: " + WIKI + page + "\n"
            + "The options missing from this file are added again at startup and on /hawn reload.";
    }
}
