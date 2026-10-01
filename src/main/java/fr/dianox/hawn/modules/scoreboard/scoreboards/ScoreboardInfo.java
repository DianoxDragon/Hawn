package fr.dianox.hawn.modules.scoreboard.scoreboards;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ScoreboardInfo {

    private final YamlConfiguration cfg;
    private final String permission;
    private List<String> text = new ArrayList<>();
    private List<String> title = new ArrayList<>();
    private int titleUpdate = 2;
    private int textUpdate = 5;
    private List<String> enabledWorlds = new ArrayList<>();
    private final HashMap<String, List<String>> changeText = new HashMap<>();
    private final HashMap<String, Integer> changeTextInterval = new HashMap<>();
    private final HashMap<String, Scroller> scrollerText = new HashMap<>();
    private final HashMap<String, Integer> scrollerInterval = new HashMap<>();

    public ScoreboardInfo(YamlConfiguration config, String permission) {
        cfg = config;
        this.permission = permission;

        initialize();
    }

    protected void initialize() {
        text = cfg.getStringList("text");
        title = cfg.getStringList("title");
        titleUpdate = cfg.getInt("updater.title", 2);
        textUpdate = cfg.getInt("updater.text", 5);
        if (cfg.getConfigurationSection("changeableText") != null) {
            for (String s : Objects.requireNonNull(cfg.getConfigurationSection("changeableText")).getKeys(false)) {
                cfg.getStringList("changeableText." + s + ".text");
                changeText.put(s, cfg.getStringList("changeableText." + s + ".text"));
                changeTextInterval.put(s, cfg.getInt("changeableText." + s + ".interval", 30));
            }
        }
        if (cfg.getConfigurationSection("scroller") != null) {
            for (String s : Objects.requireNonNull(cfg.getConfigurationSection("scroller")).getKeys(false)) {
                String text = cfg.getString("scroller." + s + ".text");
                if (text == null) {
                    break;
                }
                scrollerText.put(s, new Scroller(text, cfg.getInt("scroller." + s + ".width", 26), cfg.getInt("scroller." + s + ".spaceBetween", 6), '&'));
                scrollerInterval.put(s, cfg.getInt("scroller." + s + ".update", 1));
            }
        }
        
        if (cfg.getBoolean("World.All_World")) {
        	enabledWorlds = null;
        } else {
        	if (cfg.getStringList("World.Worlds").isEmpty()) {
                enabledWorlds = null;
            } else {
                enabledWorlds = cfg.getStringList("World.Worlds");
            }
        }
    }

    public HashMap<String, List<String>> getChangeText() {
        return changeText;
    }

    public HashMap<String, Integer> getChangeTextInterval() {
        return changeTextInterval;
    }

    public HashMap<String, Scroller> getScrollerText() {
        return scrollerText;
    }

    public HashMap<String, Integer> getScrollerInterval() {
        return scrollerInterval;
    }

    public int getTitleUpdate() {
        return titleUpdate;
    }

    public int getTextUpdate() {
        return textUpdate;
    }

    public String getPermission() {
        return permission;
    }

    public List<String> getText() {
        return text;
    }

    public List<String> getTitle() {
        return title;
    }

	public List<String> getEnabledWorlds() {
		return enabledWorlds;
	}
}