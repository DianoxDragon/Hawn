package fr.dianox.hawn.utility.config.configs.messages.it_it;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;


/**
 * The Italian messages (since 1.6), made from the English ones.
 */
public class ITConfigMGeneral {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ITConfigMGeneral() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		
		file = new File(pl.getDataFolder(), "Messages/it_IT/General.yml");
		Config = YamlConfiguration.loadConfiguration(file);
		
		if (!pl.getDataFolder().exists()) {
			pl.getDataFolder().mkdir();
		}
		
		create();
	}
	
    public static File getFile() {
        return file;
    }

    public static YamlConfiguration getConfig() {
        return Config;
    }

    public static void reloadConfig() {
        loadConfig(pl);
    }

    public static void saveConfigFile() {
        try {
            Config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void create() {
        YamlConfiguration loaded = Config;
        Config = new YamlConfiguration();

        Config.set("General.Prefix", " &3Hawn &7|");
        Config.set("General.On-join.Join-Message.Enable", true);
        Config.set("General.On-join.Join-Message.Just-Simply-Disable-All-Join-Messages", false);
        Config.set("General.On-join.Join-Message.Disable-Default-Message", true);
        Config.set("General.On-join.Join-Message.Silent-Staff-Join", true);
        Config.set("General.On-join.Join-Message.Disable-For-New-Players", false);
        Config.set("General.On-join.Join-Message.Broadcast-To-Console", true);

        Config.set("General.On-join.Join-Message.Per-Group.Options.Enable", true);
        Config.set("General.On-join.Join-Message.Per-Group.Options.Disable-Any-Messages-On-Join", false);
        Config.set("General.On-join.Join-Message.Per-Group.Groups.Owner", java.util.Arrays.asList(new String[] {
                "&cDATE IL BENVENUTO a un proprietario"}));
        Config.set("General.On-join.Join-Message.Per-Group.Groups.Admin", java.util.Arrays.asList(new String[] {
                "gruppi illimitati, ovviamente"}));

        Config.set("General.On-join.Join-Message.Per-World.Options.Enable", false);
        Config.set("General.On-join.Join-Message.Per-World.Options.Disable-Any-Other-Messages-On-Join", false);
        Config.set("General.On-join.Join-Message.Per-World.Options.Only-Broadcast-Messages-In-The-World", false);
        Config.set("General.On-join.Join-Message.Per-World.Worlds.world", java.util.Arrays.asList(new String[] {
                "&ctest1"}));
        Config.set("General.On-join.Join-Message.Per-World.Worlds.world_the_end", java.util.Arrays.asList(new String[] {
                "test2"}));

        Config.set("General.On-join.Join-Message.Messages", java.util.Arrays.asList(new String[] {"&7[&a+&7] %player%"}));
        Config.set("General.On-join.Join-Message.World.All_World", true);
        Config.set("General.On-join.Join-Message.World.Worlds", java.util.Arrays.asList(new String[] {
                "world",
                "world_nether"}));

        Config.set("General.On-Quit.Quit-Message.Enable", true);
        Config.set("General.On-Quit.Quit-Message.Just-Simply-Disable-All-Quit-Messages", false);
        Config.set("General.On-Quit.Quit-Message.Disable-Default-Message", true);
        Config.set("General.On-Quit.Quit-Message.Silent-Staff-Quit", true);
        Config.set("General.On-Quit.Quit-Message.Broadcast-To-Console", true);

        Config.set("General.On-Quit.Quit-Message.Per-Group.Options.Enable", true);
        Config.set("General.On-Quit.Quit-Message.Per-Group.Options.Disable-Any-Messages-On-Quit", true);
        Config.set("General.On-Quit.Quit-Message.Per-Group.Groups.Owner", java.util.Arrays.asList(new String[] {
        		"&cSALUTATE un proprietario"}));
        Config.set("General.On-Quit.Quit-Message.Per-Group.Groups.Admin", java.util.Arrays.asList(new String[] {
        		"gruppi illimitati, ovviamente"}));

        Config.set("General.On-Quit.Quit-Message.Per-World.Options.Enable", false);
        Config.set("General.On-Quit.Quit-Message.Per-World.Options.Disable-Any-Other-Messages-On-Quit", false);
        Config.set("General.On-Quit.Quit-Message.Per-World.Options.Only-Broadcast-Messages-In-The-World", false);
        Config.set("General.On-Quit.Quit-Message.Per-World.Worlds.world", java.util.Arrays.asList(new String[] {
                "&ctest1"}));
        Config.set("General.On-Quit.Quit-Message.Per-World.Worlds.world_the_end", java.util.Arrays.asList(new String[] {
                "test2"}));

        Config.set("General.On-Quit.Quit-Message.Messages", java.util.Arrays.asList(new String[] {"&7[&c-&7] %player%"}));
        Config.set("General.On-Quit.Quit-Message.World.All_World", true);
        Config.set("General.On-Quit.Quit-Message.World.Worlds", java.util.Arrays.asList(new String[] {
                "world",
                "world_nether"}));

        Config.set("Spawn.On-join.Enable", true);
        Config.set("Spawn.On-join.Messages", java.util.Arrays.asList(new String[] {
        		"&8&m<=====-------<&r &6Hawn &8&m>-------=====>",
                "&cCiao %player%",
                "&cNon dimenticare di leggere le nostre regole",
        		"&8&m<=====-------<&r &6Hawn &8&m>-------=====>"}));
        Config.set("Spawn.On-join.World.All_World", true);
        Config.set("Spawn.On-join.World.Worlds", java.util.Arrays.asList(new String[] {
                "world",
                "world_nether"}));

        Config.set("Spawn.On-join.Per-World.Options.Enable", false);
        Config.set("Spawn.On-join.Per-World.Options.Disable-All-The-Others-Motd", false);
        Config.set("Spawn.On-join.Per-World.Worlds.world", java.util.Arrays.asList(new String[] {
				"&ctest1 - motd"}));
        Config.set("Spawn.On-join.Per-World.Worlds.world_the_end", java.util.Arrays.asList(new String[] {
				"test2 - motd"}));

        Config.set("Spawn.On-join.First-Join.Broadcast.Enable", true);
        Config.set("Spawn.On-join.First-Join.Broadcast.Broadcast-To-The-Console", true);
        Config.set("Spawn.On-join.First-Join.Broadcast.Messages", java.util.Arrays.asList(new String[] {"&eBenvenuto %player% sul server &7(giocatore n°%player_join_number%)"}));
        Config.set("Spawn.On-join.First-Join.Motd.Enable", true);
        Config.set("Spawn.On-join.First-Join.Motd.Both-Motd", false);
        Config.set("Spawn.On-join.First-Join.Motd.Messages", java.util.Arrays.asList(new String[] {
        		"&8&m<=====-------<&r &6Hawn &8&m>-------=====>",
                "&cBenvenuto %player%",
                "&cNon dimenticare di leggere le nostre regole",
        		"&8&m<=====-------<&r &6Hawn &8&m>-------=====>"}));
        Config.set("Spawn.Teleport.Enable", true);
        Config.set("Spawn.Teleport.Enable-For-On-Join", false);
        Config.set("Spawn.Teleport.Messages", java.util.Arrays.asList(new String[] {"&7Teletrasporto..."}));
        Config.set("Spawn.Teleport-By-Player.Messages", java.util.Arrays.asList(new String[] {"&7Teletrasportato da qualcuno..."}));
        Config.set("Spawn.Teleport-By-Player.Sender", java.util.Arrays.asList(new String[] {"&7%target% teletrasportato..."}));
        Config.set("Update.Notify-Admins.Messages", java.util.Arrays.asList(new String[] {
        		"%prefix% &eÈ uscita una nuova versione di Hawn: &6%new_version% &7(hai la %current_version%)",
        		"json:{\"text\":\"§b» Scaricala su Spigot\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.spigotmc.org/resources/66907/\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Apri la pagina di Hawn\"}}",
        		"json:{\"text\":\"§9» Domande? Unisciti al Discord\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://discord.gg/yeUg5mbJuz\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Supporto, novità e idee\"}}"}));

        // Since 1.4.4: the values of the yes/no placeholders (%player_vanished%...), and of a number not known yet
        Config.set("Placeholders.Yes", "&asì");
        Config.set("Placeholders.No", "&cno");
        Config.set("Placeholders.Unknown", "?");

        Config = ConfigDefaults.apply(file, loaded, Config, "General.On-join.Join-Message.Per-Group.Groups", "General.On-join.Join-Message.Per-World.Worlds", "General.On-Quit.Quit-Message.Per-Group.Groups", "General.On-Quit.Quit-Message.Per-World.Worlds", "Spawn.On-join.Per-World.Worlds");
    }

}