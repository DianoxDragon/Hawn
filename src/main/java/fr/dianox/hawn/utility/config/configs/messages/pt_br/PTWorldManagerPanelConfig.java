package fr.dianox.hawn.utility.config.configs.messages.pt_br;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * The Portuguese (Brazil) messages (since 1.6), made from the English ones.
 */
public class PTWorldManagerPanelConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public PTWorldManagerPanelConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		
		file = new File(pl.getDataFolder(), "Messages/pt_BR/WorldManager.yml");
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

        /*
         * Gui
         */
        // import
        Config.set("Gui.Import.Importing-A-World", java.util.Arrays.asList("[send-title]: &cImportação iniciada //n &7Aguarde..."));
        Config.set("Gui.Import.Importing-With-A-Command", java.util.Arrays.asList("%prefix% &7Use um comando: /hw import nomedomundo"));

        Config.set("Gui.Import.World-Loaded", java.util.Arrays.asList(
        		"%prefix% &7O mundo %arg1% foi carregado",
		            "[send-title]: &3Importação concluída //n &7Você pode entrar neste mundo"));

        Config.set("Gui.Unload", java.util.Arrays.asList("%prefix% &7O mundo %arg1% foi descarregado"));
        // Since 1.4.7
        Config.set("Gui.Unload-Moved", java.util.Arrays.asList("%prefix% &7O mundo &e%arg1% &7foi descarregado: agora você está em &e%arg2%"));
        Config.set("Gui.Import.Failed", java.util.Arrays.asList("%prefix% &cO mundo &e%arg1% &cnão pôde ser carregado, veja o console"));
        // Since 1.6
        Config.set("Folia-Disabled", java.util.Arrays.asList("%prefix% &cO World Manager está desligado no Folia: o Folia não consegue criar nem descarregar um mundo com o servidor ligado"));

        // tp
        Config.set("Gui.Tp.Error-Tp", java.util.Arrays.asList("%prefix% &cO teleporte falhou",
		            "%prefix% &cVocê já está nesse mundo"));

        Config.set("Gui.Tp.Success", java.util.Arrays.asList("%prefix% &7Você foi teleportado para o mundo &e%arg1%"));

        // create
        Config.set("Gui.Create.Choose-A-Name", java.util.Arrays.asList("[send-title]: &eEscolha o nome do mundo //n &7Escreva no chat"));
        Config.set("Gui.Create.Choose-A-Generator", java.util.Arrays.asList("[send-title]: &eEscolha um gerador //n &7Escreva no chat"));

        Config.set("Gui.Create.Creating-The-World", java.util.Arrays.asList("[send-title]: &cMundo sendo criado&7..."));

        Config.set("Gui.Create.World-Created", java.util.Arrays.asList("%prefix% &7O mundo &e%arg1% &7foi criado",
                "[send-title]: &3Mundo criado //n &7Você pode entrar neste mundo"));

        Config.set("Gui.Create.World-Cancelled", java.util.Arrays.asList("%prefix% &7Criação do mundo cancelada..."));

        // Delete
        Config.set("Gui.Delete.World-Deleted", java.util.Arrays.asList("%prefix% &cO mundo &e%arg1% &cfoi apagado"));
        Config.set("Gui.Delete.Confirm-Command", java.util.Arrays.asList("%prefix% &cIsso apaga o mundo &e%arg1% &ce a pasta dele, sem volta. Escreva &e/hw delete %arg1% confirm &cpara fazer"));
        Config.set("Error.Protected-World", java.util.Arrays.asList("%prefix% &cO mundo &e%arg1% &cnão pode ser apagado: o servidor precisa dele"));
        Config.set("Error.Unload-Failed", java.util.Arrays.asList("%prefix% &cO mundo &e%arg1% &cnão pôde ser descarregado: nada foi apagado"));
        Config.set("Gui.Default-World.Changed", java.util.Arrays.asList("%prefix% &e%arg1% &7agora é o mundo padrão: ele substitui &e%arg2% &7em &e%arg3% &7opção(ões) de &e%arg4% &7arquivo(s)"));
        Config.set("Gui.Default-World.Already", java.util.Arrays.asList("%prefix% &e%arg1% &7já é o mundo padrão"));
        Config.set("Gui.Default-World.Shift-Click", java.util.Arrays.asList("%prefix% &eShift + clique &7para fazer de &e%arg1% &7o mundo padrão"));
        Config.set("Gui.Default-World.Conflict", java.util.Arrays.asList("%prefix% &6%arg1%: &e%arg2% &6já tinha suas próprias opções, as de &e%arg3% &6ficam ao lado"));
        Config.set("Gui.Default-World.Spawn-Elsewhere", java.util.Arrays.asList("%prefix% &6O spawn padrão &e%arg1% &6ainda está em &e%arg2%&6: crie um no novo mundo com &e/setspawn"));
        Config.set("Gui.Default-World.Yaml-Error", java.util.Arrays.asList("%prefix% &c%arg1% tem um erro de YAML: não foi mudado"));
        Config.set("Command.Default-World.Current", java.util.Arrays.asList("%prefix% &7O mundo padrão é &e%arg1% &7(&e/hw default <mundo> &7para mudar)"));
        Config.set("Error.Default-World", java.util.Arrays.asList("%prefix% &cO mundo &e%arg1% &cé o mundo padrão do Hawn: escolha outro mundo padrão antes"));
        Config.set("Gui.Other.Default-World.Title", "&6Mundo padrão");
        Config.set("Gui.Other.Default-World.Current", java.util.Arrays.asList("&7Este é o mundo padrão do Hawn:", "&7o que está escrito nas listas de mundos", "&7dos arquivos de configuração"));
        Config.set("Gui.Other.Default-World.Change", java.util.Arrays.asList("&7Agora: &e%arg1%", "&eShift + clique&7 para fazer deste mundo", "&7o padrão: ele substitui &e%arg1%", "&7nas listas de mundos dos arquivos"));
        Config.set("Gui.Other.Main.Default-World", "&6Mundo padrão");

        Config.set("Gui.Delete.Error-Mystery", java.util.Arrays.asList("%prefix% &cAlgo estranho aconteceu... nada acontece, é um erro, ignore..."));

        // Modify World
        Config.set("Gui.Modify-World.Time-Changed", java.util.Arrays.asList("%prefix% &7O horário do mundo &e%arg1%&7 foi mudado"));

        Config.set("Gui.Modify-World.Weather.Sun", java.util.Arrays.asList("%prefix% &7O clima do mundo &e%arg1%&7 agora é sol"));

        Config.set("Gui.Modify-World.Weather.Rain", java.util.Arrays.asList("%prefix% &7O clima do mundo &e%arg1%&7 agora é chuva"));

        Config.set("Gui.Modify-World.Weather.Storm", java.util.Arrays.asList("%prefix% &7O clima do mundo &e%arg1%&7 agora é tempestade"));

        // M. W. Dif.
        Config.set("Gui.Modify-World.Difficulty.Peaceful", java.util.Arrays.asList("%prefix% &7A dificuldade do mundo &e%arg1%&7 agora é pacífica"));

        Config.set("Gui.Modify-World.Difficulty.Easy", java.util.Arrays.asList("%prefix% &7A dificuldade do mundo &e%arg1%&7 agora é fácil"));

        Config.set("Gui.Modify-World.Difficulty.Normal", java.util.Arrays.asList("%prefix% &7A dificuldade do mundo &e%arg1%&7 agora é normal"));

        Config.set("Gui.Modify-World.Difficulty.Hard", java.util.Arrays.asList("%prefix% &7A dificuldade do mundo &e%arg1%&7 agora é difícil"));

        // Other
        Config.set("Gui.Other.Generator", "&7Gerador");
        Config.set("Gui.Other.Generator-Page.Void-Generator", "&7Gerador vazio");
        Config.set("Gui.Other.Generator-Page.Custom-Generator", "&7Escreva seu próprio gerador");
        // Since 1.4
        Config.set("Gui.Create.Failed", java.util.Arrays.asList("%prefix% &cO mundo &e%arg1% &cnão pôde ser criado, veja o console"));
        Config.set("Gui.Modify-World.Nothing-Selected", java.util.Arrays.asList("%prefix% &7Clique primeiro em uma escolha, depois na placa"));
        Config.set("Gui.Other.Generator-Default", "&7Padrão");
        Config.set("Gui.Other.Generator-Help", java.util.Arrays.asList("&8Clique esquerdo: padrão ou vazio", "&8Clique direito: escreva um gerador no chat"));
        Config.set("Gui.Other.Info.Title", "&eInformações");
        Config.set("Gui.Other.Info.Players", "&7Jogadores:");
        Config.set("Gui.Other.Info.Environment", "&7Ambiente:");
        Config.set("Gui.Other.Info.Generator", "&7Gerador:");
        Config.set("Gui.Other.Info.Chunks", "&7Chunks carregados:");
        Config.set("Gui.Other.Info.Entities", "&7Entidades:");
        Config.set("Gui.Other.Info.Spawn", "&7Spawn:");
        Config.set("Gui.Other.Info.Time", "&7Horário:");
        Config.set("Gui.Other.Info.PvP", "&7PvP:");
        Config.set("Gui.Other.Info.Seed", "&7Semente:");
        // Since 1.4.4
        Config.set("Gui.Other.Size-Pending", "&7...");
        Config.set("Gui.Create.Generator-Cancelled", java.util.Arrays.asList("%prefix% &7Nenhum gerador escrito: o padrão continua"));
        Config.set("Error.Unload-Protected", java.util.Arrays.asList("%prefix% &cO mundo &e%arg1% &cnão pode ser descarregado: o servidor precisa dele"));
        Config.set("Command.Unload.Failed", java.util.Arrays.asList("%prefix% &cO mundo &e%arg1% &cnão pôde ser descarregado: não pode ter nenhum jogador nele"));
        Config.set("Gui.Other.WorldType.World-Type", "&7Tipo de mundo:");
        Config.set("Gui.Other.WorldType.Nether", "&cNETHER");
        Config.set("Gui.Other.WorldType.The_End", "&5THE_END");
        Config.set("Gui.Other.WorldType.Normal", "&aNORMAL");

        Config.set("Gui.Other.WorldFace.World-Face", "&7Forma do mundo:");
        Config.set("Gui.Other.WorldFace.Normal", "&aNORMAL");
        Config.set("Gui.Other.WorldFace.Large-Biomes", "&bLARGE_BIOMES");
        Config.set("Gui.Other.WorldFace.Amplified", "&cAMPLIFIED");
        Config.set("Gui.Other.WorldFace.Flat", "&eFLAT");

        Config.set("Gui.Other.ChangeWorld.Weather.Sun", "&7Sol");
        Config.set("Gui.Other.ChangeWorld.Weather.Rain", "&7Chuva");
        Config.set("Gui.Other.ChangeWorld.Weather.Storm", "&7Tempestade");
        Config.set("Gui.Other.ChangeWorld.Weather.Actual-Weather", "Clima atual:");

        Config.set("Gui.Other.Difficulty.Peaceful", "PEACEFUL");
        Config.set("Gui.Other.Difficulty.Easy", "EASY");
        Config.set("Gui.Other.Difficulty.Normal", "NORMAL");
        Config.set("Gui.Other.Difficulty.Hard", "HARD");

        Config.set("Gui.Other.Name", "Name:");
        Config.set("Gui.Other.Players", "Players:");
        Config.set("Gui.Other.Size", "Size:");
        Config.set("Gui.Other.Difficulty-Main", "Difficulty:");
        Config.set("Gui.Other.Difficulty-Two", "Difficulty");
        Config.set("Gui.Other.Environment", "Environment:");
        Config.set("Gui.Other.World-Time", "Horário do mundo");
        Config.set("Gui.Other.World-Weather", "Clima do mundo");
        Config.set("Gui.Other.Done", "&f&lPronto");
        Config.set("Gui.Other.Main.Line-One", "&eClique esquerdo&7 para entrar no mundo");
        Config.set("Gui.Other.Main.Line-Two", "&eClique direito&7 para mudar as configurações do mundo");
        Config.set("Gui.Other.Main.Line-Three", "&eShift &7e &eclique direito&7 para apagar o mundo");
        Config.set("Gui.Other.Main.Line-Four", "&7O mundo não está carregado no momento");
        Config.set("Gui.Other.Main.Line-One-Second", "&eClique esquerdo&7 para carregar o mundo");
        Config.set("Gui.Other.Unload.Name", "&cDescarregar o mundo");
        Config.set("Gui.Other.Unload.Lore", "&7Até o próximo reinício: os jogadores nele vão para o mundo padrão");
        Config.set("Gui.Other.Page.Back", "&bAnterior");
        Config.set("Gui.Other.Page.Next", "&bPróxima");
        Config.set("Gui.Other.Create-a-new-world", "&f&lCriar um novo mundo");
        Config.set("Gui.Other.Back.PanelAdmin", "&c&lVoltar ao painel admin");
        Config.set("Gui.Other.Back.Menu", "&c&lVoltar ao menu");
        Config.set("Gui.Other.Back.Main-Menu", "&c&lVoltar ao menu principal");
        Config.set("Gui.Other.Delete.Confirm", "&aSei o que estou fazendo");
        Config.set("Gui.Other.Delete.No", "&cPensando bem... não");

        /*
         * Command
         */
        Config.set("Command.Unload.Error", java.util.Arrays.asList("%prefix% &cErro, não foi possível descarregar este mundo"));
        Config.set("Command.Other.World", "World:");
        Config.set("Command.Other.Type", "Type:");

        Config.set("Error.NotGoodName", java.util.Arrays.asList("%prefix% &cErro do Hawn: um nome de mundo com esse formato não é permitido",
		            "%prefix% &cOs únicos formatos de nome permitidos são: a-z | A-Z | 0-9 | _ | -"));

        Config.set("Error.WorldNameNotTyped", java.util.Arrays.asList("%prefix% &cVocê não escreveu o nome do mundo"));

        Config.set("Error.WorldCreation", java.util.Arrays.asList("%prefix% &cVocê esqueceu algo ao escrever o comando..."));

        Config.set("Error.World-Already-Exist", java.util.Arrays.asList("%prefix% &cEste mundo existe"));

        Config.set("Error.World-Not-Exist", java.util.Arrays.asList("%prefix% &cEste mundo não existe"));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }
}