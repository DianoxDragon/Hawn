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

    // For the startup report: what happened to the files since reset()
    private static final java.util.Set<String> files = new java.util.HashSet<>();
    private static int optionsAdded = 0;
    private static int commented = 0;
    private static final List<String> yamlErrors = new ArrayList<>();

    public static void reset() {
        files.clear();
        optionsAdded = 0;
        commented = 0;
        yamlErrors.clear();
    }

    public static int files() { return files.size(); }
    public static int optionsAdded() { return optionsAdded; }
    public static int commented() { return commented; }
    public static List<String> yamlErrors() { return yamlErrors; }

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
        files.add(name);

        if (!file.exists()) {
            defaults.options().header(header(name));
            ConfigComments.comment(name, defaults, defaults);
            save(defaults, file);
            return defaults;
        }

        // A file with a YAML error is loaded empty: it must not be overwritten with the default values
        if (loaded.getKeys(false).isEmpty() && file.length() > 0) {
            try {
                new YamlConfiguration().load(file);
            } catch (IOException | InvalidConfigurationException e) {
                Bukkit.getLogger().warning("[Hawn] " + name + " can't be read (YAML error), its missing options were not added: " + e.getMessage());
                if (!yamlErrors.contains(name)) yamlErrors.add(name);
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

        // 1.18.1+: a comment above the options that have none (the new ones, and all of them the first time)
        boolean commented = ConfigComments.comment(name, loaded, defaults);
        if (commented) ConfigDefaults.commented++;
        optionsAdded += added.size();

        if (!added.isEmpty() || commented) {
            if (loaded.options().header() == null || loaded.options().header().isEmpty()) {
                loaded.options().header(header(name));
            }
            save(loaded, file);
        }

        if (!added.isEmpty()) {
            Bukkit.getLogger().info("[Hawn] " + name + ": " + added.size() + " missing option(s) added: "
                + String.join(", ", added.size() > 10 ? added.subList(0, 10) : added) + (added.size() > 10 ? "..." : ""));
        }
        return loaded;
    }

    /**
     * Default texts corrected by a new version: an option that still has the old default text gets the new one,
     * an option changed by the owner is kept.
     * @param config   the configuration, as returned by apply
     * @param defaults the default values
     * @param changes  {old part, new part} of the texts, in the order they were changed
     */
    public static void replaceOldDefaults(File file, YamlConfiguration config, YamlConfiguration defaults, String[][] changes) {
        List<String> updated = new ArrayList<>();

        for (String path : defaults.getKeys(true)) {
            Object now = defaults.get(path);
            Object current = config.get(path);
            if (current == null || current.equals(now)) continue;

            if (now instanceof String && current instanceof String) {
                if (fixed((String) current, changes).equals(now)) {
                    config.set(path, now);
                    updated.add(path);
                }
            } else if (now instanceof List && current instanceof List && ((List<?>) now).size() == ((List<?>) current).size()) {
                List<?> news = (List<?>) now;
                List<?> currents = (List<?>) current;
                boolean same = true;
                for (int i = 0; i < news.size() && same; i++) {
                    same = fixed(String.valueOf(currents.get(i)), changes).equals(String.valueOf(news.get(i)));
                }
                if (same) {
                    config.set(path, now);
                    updated.add(path);
                }
            }
        }

        if (!updated.isEmpty()) {
            save(config, file);
            Bukkit.getLogger().info("[Hawn] " + relativeName(file) + ": " + updated.size() + " default text(s) corrected");
        }
    }

    // The text with the corrections: the old default text becomes the new one
    private static String fixed(String text, String[][] changes) {
        for (String[] change : changes) {
            String anchor = change.length > 2 ? change[2] : "";
            if (anchor.equals("whole")) {
                if (text.equals(change[0])) text = change[1];
            } else if (anchor.equals("end")) {
                if (text.endsWith(change[0])) text = text.substring(0, text.length() - change[0].length()) + change[1];
            } else if (anchor.equals("start")) {
                if (text.startsWith(change[0])) text = change[1] + text.substring(change[0].length());
            } else {
                text = text.replace(change[0], change[1]);
            }
        }
        return text;
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
