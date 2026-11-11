package fr.dianox.hawn.utility.config.configs.messages.pt_br;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * The Portuguese (Brazil) messages (since 1.6), made from the English ones.
 */
public class PTAdminAMConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public PTAdminAMConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		
		file = new File(pl.getDataFolder(), "Messages/pt_BR/Admin.yml");
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

        /* -------------- *
			 * SPAWN COMMANDS *
			 * -------------- */
        Config.set("Error.Console.Not-A-Player", java.util.Arrays.asList("&cVocê não é um jogador"));
        Config.set("Error.Command.Hawn", java.util.Arrays.asList("&cErro, tente /hawn"));
        Config.set("Error.Command.Delspawn", java.util.Arrays.asList("&c/hawn delspawn <spawn>"));
        Config.set("Error.Command.Name-already-exist", java.util.Arrays.asList("&cEsse nome já existe"));
        Config.set("Error.Argument-Missing", java.util.Arrays.asList("&cDesculpe, falta um ou dois argumentos."));
        Config.set("Error.No-Spawn", java.util.Arrays.asList("&cO spawn não existe"));

	        /* ------------ *
	         * HELP COMMAND *
	         * ------------ */

	        Config.set("Command.Help.Hawn-SpawnManager", "Gerencia os spawns");
	        Config.set("Command.Help.Hawn-Reload", "Recarrega alguns arquivos de configuração");
	        Config.set("Command.Help.Hawn-Version", "Mostra a versão do plugin");
	        Config.set("Command.Help.Hawn-Tps", "Mostra o TPS do servidor");
	        Config.set("Command.Help.Hawn-Info", "Mostra as informações do servidor");
	        Config.set("Command.Help.Hawn-Build", "Para ignorar as proteções por um tempo");
	        Config.set("Command.Help.Hawn-Hooks", "Para ver se as dependências do plugin estão ativas");
	        Config.set("Command.Help.Hawn-Urgent", "Para usar o modo de urgência");
	        Config.set("Command.Help.Hawn-Maintenance", "Para usar o modo de manutenção");
	        Config.set("Command.Help.Hawn-Donors", "Mostra quem fez doações para o plugin");
	        Config.set("Command.Help.Hawn-About", "Mostra só algumas informações");
	        Config.set("Command.Help.Hawn-Parse", "Mostra o que um placeholder retorna");
	        Config.set("Command.Help.Hawn-NightVision", "Enxergue no escuro");
	        Config.set("Command.Help.Hawn-NoClip", "Atravesse os blocos ficando no criativo");
	        Config.set("Command.Help.Hawn-SlotView", "Mostra o número do slot clicado");
	        Config.set("Command.Help.Hawn-EditPlayer", "Edita um jogador");
	        Config.set("Command.Help.Hawn-Setup", "Configura o Hawn pela primeira vez");
	        Config.set("Command.Help.Hawn-Debug", "Os erros dos arquivos de configuração e os erros escondidos");
	        Config.set("Command.Help.Hawn-Rank", "Dá, tira ou mostra o rank de um jogador (Ranks.yml)");
	        Config.set("Command.Help.Broadcast", "Envia um anúncio");
	        Config.set("Command.Help.Warning", "Envia um aviso");
	        Config.set("Command.Help.Broadcast-Title", "Envia um anúncio como título");
	        Config.set("Command.Help.Broadcast-ActionBar", "Envia um anúncio na barra de ação");
	        Config.set("Command.Help.AdminPanel", "Abre o painel admin");
	        Config.set("Command.Help.CheckAccount", "Verifica um jogador");
	        Config.set("Command.Help.ClearInv", "Esvazia o inventário de um jogador");
	        Config.set("Command.Help.InvSee", "Mostra o inventário de um jogador");
	        Config.set("Command.Help.Ip", "Mostra o IP de um jogador");
	        Config.set("Command.Help.List", "Mostra a lista de jogadores do lobby");
	        Config.set("Command.Help.Spawn", "Vai para o spawn");
	        Config.set("Command.Help.Spawn-Tp", "Teleporta um jogador para um spawn");
	        Config.set("Command.Help.DelSpawn", "Apaga um spawn");
	        Config.set("Command.Help.SpawnList", "Mostra a lista de spawns");
	        Config.set("Command.Help.SetSpawn", "Define um spawn");
	        Config.set("Command.Help.SetWarp", "Define um warp");
	        Config.set("Command.Help.Warp", "Vai para o warp");
	        Config.set("Command.Help.WarpList", "Mostra a lista de warps");
	        Config.set("Command.Help.DelWarp", "Apaga o warp");
	        Config.set("Command.Help.EditWarp", "Muda o local de um warp");
	        Config.set("Command.Help.Sun", "Traz o sol de volta");
	        Config.set("Command.Help.Rain", "Faz chover no mundo");
	        Config.set("Command.Help.Thunder", "Se você gosta de tempo ruim");
	        Config.set("Command.Help.Day", "Coloca o dia");
	        Config.set("Command.Help.Night", "Coloca a noite");
	        Config.set("Command.Help.Fly", "Ativa o voo");
	        Config.set("Command.Help.FlySpeed", "Muda a velocidade de voo de um jogador");
	        Config.set("Command.Help.Speed", "Muda, ativa ou desativa a velocidade");
	        Config.set("Command.Help.Heal", "Cura um jogador");
	        Config.set("Command.Help.Feed", "Sacia a fome de um jogador");
	        Config.set("Command.Help.Ping", "Mostra o ping de um jogador");
	        Config.set("Command.Help.Vanish", "Deixa um jogador invisível");
	        Config.set("Command.Help.Gamemode", "Muda o modo de jogo de um jogador");
	        Config.set("Command.Help.Burn", "Coloca fogo em um jogador");
	        Config.set("Command.Help.Cleargrounditems", "Tira os itens do chão");
	        Config.set("Command.Help.Clearmobs", "Tira os mobs");
	        Config.set("Command.Help.EnderChest", "Abre o baú do Ender");
	        Config.set("Command.Help.Exp", "Edita a experiência de um jogador");
	        Config.set("Command.Help.Getpos", "Mostra a posição de um jogador");
	        Config.set("Command.Help.Hat", "Coloque um chapéu");
	        Config.set("Command.Help.Kickall", "Expulsa todo mundo");
	        Config.set("Command.Help.Repair", "Conserta um item");
	        Config.set("Command.Help.Skull", "Pega a cabeça de um jogador");
	        Config.set("Command.Help.Suicide", "Mate-se");
	        Config.set("Command.Help.Workbench", "Abre a bancada de trabalho de um jogador");
	        Config.set("Command.Help.ClearChat", "Mostra a ajuda do clearchat");
	        Config.set("Command.Help.DelayChat", "Coloca um intervalo no chat");
	        Config.set("Command.Help.Gmute", "Bloqueia o chat");
	        Config.set("Command.Help.Help", "Mostra a ajuda personalizada, se ativa");
	        Config.set("Command.Help.Gotop", "Vai para o bloco mais alto da sua posição");
	        Config.set("Command.Help.Emoji", "Mostra o menu de emojis");
	        Config.set("Command.Help.Scoreboard", "Ativa ou desativa o scoreboard");
	        Config.set("Command.Help.Scoreboard-Set", "Para mudar o scoreboard atual");
	        Config.set("Command.Help.Scoreboard-Keep", "Mantém o scoreboard entre os servidores");
	        Config.set("Command.Help.Scoreboard-List", "Mostra todos os scoreboards registrados");
	        Config.set("Command.Help.Option", "Para as opções principais do jogador");
	        Config.set("Command.Help.Hworld", "Gerencia os mundos");

        /* ----------------- *
			 * COMMANDS COMMANDS *
			 * ----------------- */
        Config.set("Command.Server-Info.General", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformações do servidor (Tudo)",
		            "        &3criado por Dianox",
		            "",
		            "  &8→ &6&lServidor",
		            "&7&lTps&r&8:&r %tps%",
		            "&7&lVersão do Java&r&8:&r %javaversion%",
		            "&7&lSO&r&8:&r %osversion%",
		            "&7&lAtualização do Hawn&r&8:&r %checkupdatehawn%",
		            "&7&lVersão do servidor&r&8:&r %serverversion%",
		            "",
		            "  &8→ &6&lMemória",
		            "&7&lMemória máxima &o(java)&r&8:&r %maxmemory%&8MB",
		            "&a&lLivre&7&l/&c&lTotal&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
		            "&8Barra-[%barmemory%&8]",
		            "",
		            "  &8→ &6&lCPU (Processador)",
		            "&7&lCarga média&8:&r %averagecpuload%&8%",
		            "&7&lCarga atual&8:&r %cpuload%&8%",
		            "&8Barra-[%barcpu%&8]",
		            "",
		            "  &8→ &6&lEspaço em disco",
		            "&7&lDisco total&r&8:&r %totalspace%&8MB",
		            "&a&lDisco livre&7&l/&c&lDisco total&7&l utilizável&r&8:&r %freespace%&8MB&7/&7%totalspace%&8MB",
		            "&8Barra-[%bardisk%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Memory", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformações do servidor (Memória)",
		            "",
		            "  &8→ &6&lMemória",
		            "&7&lMemória máxima &o(java)&r&8:&r %maxmemory%&8MB",
		            "&a&lLivre&7&l/&c&lTotal&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
		            "&8Barra-[%barmemory%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.CPU", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformações do servidor (CPU)",
		            "",
		            "  &8→ &6&lCPU (Processador)",
		            "&7&lCarga média&8:&r %averagecpuload%&8%",
		            "&7&lCarga atual&8:&r %cpuload%&8%",
		            "&8Barra-[%barcpu%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Disk", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformações do servidor (Disco)",
		            "",
		            "  &8→ &6&lEspaço em disco",
		            "&7&lDisco total&r&8:&r %totalspace%&8MB",
		            "&a&lDisco livre&7&l/&c&lDisco total&7&l utilizável&r&8:&r %freespace%&8MB&7/&7%totalspace%&8MB",
		            "&8Barra-[%bardisk%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Server", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformações do servidor (Servidor)",
		            "",
		            "  &8→ &6&lServidor",
		            "&7&lTps&r&8:&r %tps%",
		            "&7&lVersão do Java&r&8:&r %javaversion%",
		            "&7&lSO&r&8:&r %osversion%",
		            "&7&lAtualização do Hawn&r&8:&r %checkupdatehawn%",
		            "&7&lVersão do servidor&r&8:&r %serverversion%",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Tps", java.util.Arrays.asList("  &8→ &6&lTps&8: &r%tps%"));

        Config.set("Command.Version", java.util.Arrays.asList("  &8→ &6&lVersão do Hawn (criado por Dianox)&8: &r%gethawnversion%"));

        // Since 1.4.8: /hawn debug
        Config.set("Debug.Header", java.util.Arrays.asList("&8&m-----&r &6Debug do Hawn &8&m-----"));
        Config.set("Debug.No-Config-Error", java.util.Arrays.asList("&aNenhum erro encontrado nos arquivos de configuração."));
        Config.set("Debug.Config-Errors", java.util.Arrays.asList("&e%arg 1% &7erro(s) nos arquivos de configuração:"));
        Config.set("Debug.Auto", java.util.Arrays.asList("&e%arg 1% &7opção(ões) em AUTO deixadas para outro plugin:"));
        Config.set("Debug.No-Hidden-Error", java.util.Arrays.asList("&aNenhum erro escondido desde o início."));
        Config.set("Debug.Hidden-Errors", java.util.Arrays.asList("&e%arg 1% &7erro(s) que o Hawn contornou desde o início:"));
        Config.set("Debug.Tip", java.util.Arrays.asList("&7Plugin.Debug: true no general.yml os escreve no console, com os detalhes."));
        // Since 1.4.8: /hawn rank
        Config.set("Ranks.Usage", java.util.Arrays.asList("%prefix% &7/hawn rank set <jogador> <rank> &8| &7remove <jogador> &8| &7info <jogador> &8| &7list"));
        Config.set("Ranks.Off", java.util.Arrays.asList("%prefix% &cOs ranks estão desligados (Ranks.yml, Ranks.Enable: false)"));
        Config.set("Ranks.Unknown-Rank", java.util.Arrays.asList("%prefix% &cO rank &e%arg 1% &cnão existe ou está desligado. Ranks: &7%arg 2%"));
        Config.set("Ranks.LuckPerms-Source", java.util.Arrays.asList("%prefix% &7Os ranks vêm do LuckPerms (Ranks.Source): mude o grupo de &e%arg 1% &7no LuckPerms (&e/lp user %arg 1% parent set <grupo>&7)"));
        Config.set("Ranks.Set", java.util.Arrays.asList("%prefix% &e%arg 1% &7agora tem o rank &e%arg 2%"));
        Config.set("Ranks.Removed", java.util.Arrays.asList("%prefix% &e%arg 1% &7não tem mais um rank dado à mão"));
        Config.set("Ranks.No-Rank-By-Hand", java.util.Arrays.asList("%prefix% &e%arg 1% &cnão tem rank dado à mão (as permissões ou o LuckPerms dão)"));
        // 123: a Bedrock name with a dot (since 1.5.6)
        Config.set("Ranks.Name-With-Dot", java.util.Arrays.asList("%prefix% &e%arg 1% &ctem um ponto no nome (Bedrock): precisa estar online para receber um rank, guardado no UUID dele"));
        Config.set("Ranks.Info", java.util.Arrays.asList("%prefix% &e%arg 1%&7: rank &e%arg 2% &7(%arg 3%) &8| &r%arg 4%"));
        Config.set("Ranks.Info-Offline", java.util.Arrays.asList("%prefix% &e%arg 1% &7está offline. Rank dado à mão: &e%arg 2%"));
        Config.set("Ranks.List-Header", java.util.Arrays.asList("%prefix% &e%arg 1% &7rank(s), o primeiro no topo da tab list:"));
        Config.set("Ranks.List-Line", java.util.Arrays.asList("&8- &e%arg 1% &7(prioridade %arg 2%) &r%arg 3% &8| &7online: &f%arg 4%"));
        Config.set("Ranks.Source.HAND", "dado à mão");
        Config.set("Ranks.Source.PERMISSION", "por permissão");
        Config.set("Ranks.Source.LUCKPERMS", "do LuckPerms");
        Config.set("Ranks.Source.DEFAULT", "o rank padrão");
        Config.set("Ranks.Source.NONE", "none");
        // Since 1.5: /hawn menu
        Config.set("Command.Help.Hawn-Menu", "Abre um menu da pasta Menus para você ou para um jogador, ou lista os menus");
        // The holograms (since 1.6)
        Config.set("Holograms.Usage", java.util.Arrays.asList("%prefix% &7/hawn hologram create <nome> [texto] &8| &7delete <nome> &8| &7movehere <nome> &8| &7addline <nome> <texto> &8| &7setline <nome> <linha> <texto> &8| &7removeline <nome> <linha> &8| &7list &8| &7enable <nome> &8| &7disable <nome>"));
        Config.set("Holograms.Created", java.util.Arrays.asList("%prefix% &7O holograma &e%arg 1% &7foi criado onde você está"));
        Config.set("Holograms.Exists", java.util.Arrays.asList("%prefix% &cO holograma &e%arg 1% &cjá existe"));
        Config.set("Holograms.Not-Found", java.util.Arrays.asList("%prefix% &cO holograma &e%arg 1% &cnão existe"));
        Config.set("Holograms.Deleted", java.util.Arrays.asList("%prefix% &7O holograma &e%arg 1% &7foi apagado"));
        Config.set("Holograms.Moved", java.util.Arrays.asList("%prefix% &7O holograma &e%arg 1% &7agora está onde você está"));
        Config.set("Holograms.Changed", java.util.Arrays.asList("%prefix% &7O holograma &e%arg 1% &7agora tem &e%arg 2% &7linha(s)"));
        Config.set("Holograms.Bad-Line", java.util.Arrays.asList("%prefix% &cO holograma &e%arg 1% &cnão tem a linha &e%arg 2%"));
        Config.set("Holograms.List", java.util.Arrays.asList("%prefix% &7Hologramas: &e%arg 1%"));
        Config.set("Holograms.None", java.util.Arrays.asList("%prefix% &7Nenhum holograma na pasta Holograms"));
        Config.set("Holograms.Enabled", java.util.Arrays.asList("%prefix% &7O holograma &e%arg 1% &7está ativado: ele é mostrado"));
        Config.set("Holograms.Disabled", java.util.Arrays.asList("%prefix% &7O holograma &e%arg 1% &7está desativado: ele fica escondido"));
        Config.set("Holograms.Off-Tag", java.util.Arrays.asList("&8(desativado)"));
        Config.set("Holograms.No-PacketEvents", java.util.Arrays.asList("%prefix% &cOs hologramas precisam do plugin &ePacketEvents&c: modrinth.com/plugin/packetevents"));
        Config.set("Holograms.Bad-Name", java.util.Arrays.asList("%prefix% &cO nome de um holograma: letras, números, _ e -"));
        Config.set("Command.Help.Hawn-Hologram", "Mostra um texto em um lugar, no idioma de cada jogador");
        Config.set("Menus.Usage", java.util.Arrays.asList("%prefix% &7/hawn menu open <menu> [jogador] &8| &7list"));
        Config.set("Menus.Opened", java.util.Arrays.asList("%prefix% &7O menu &e%arg 1% &7está aberto para &e%arg 2%"));
        Config.set("Menus.Not-Opened", java.util.Arrays.asList("%prefix% &cO menu &e%arg 1% &cnão foi aberto para &e%arg 2% &c(permissão ou mundo)"));
        Config.set("Menus.List", java.util.Arrays.asList("%prefix% &7Menus: &e%arg 1%"));
        Config.set("Menus.None", java.util.Arrays.asList("%prefix% &7Nenhum menu na pasta Menus"));
        Config.set("TPS.Check.15", java.util.Arrays.asList("&cSeu TPS está abaixo de %arg 1%, faça algo para melhorar a estabilidade do seu lobby"));
        Config.set("TPS.Check.5", java.util.Arrays.asList("&cSeu TPS está abaixo de %arg 1%, o servidor pode desligar: faça /stop para evitar problemas.", "&cATENÇÃO: SERVIDOR EM ESTADO CRÍTICO"));

        /* --------------- *
         * RELOAD COMMANDS *
         * --------------- */
        Config.set("Command.Reload", java.util.Arrays.asList("&aConfiguração recarregada"));

        Config.set("Command.Build-Bypass.On", java.util.Arrays.asList("&bAgora você pode ignorar todas as restrições de construção"));
        Config.set("Command.Build-Bypass.Off", java.util.Arrays.asList("&cVocê não pode mais ignorar as restrições de construção"));


        /*
         * Vanish
         */
        Config.set("Vanish.Vanish-On.Enable", true);
        Config.set("Vanish.Vanish-On.Messages", java.util.Arrays.asList("&7[ %player% agora está invisível ]"));

        Config.set("Vanish.Vanish-Off.Enable", true);
        Config.set("Vanish.Vanish-Off.Messages", java.util.Arrays.asList("&7[ %player% não está mais invisível ]"));
        Config.set("Vanish.Vanish-On-Others", java.util.Arrays.asList("&7[ %target% agora está invisível, por %player% ]"));
        Config.set("Vanish.Vanish-Off-Others", java.util.Arrays.asList("&7[ %target% não está mais invisível, por %player% ]"));

        Config.set("Maintenance.On", java.util.Arrays.asList("%prefix% &7Você &aativou&7 a manutenção"));
        Config.set("Maintenance.Off", java.util.Arrays.asList("%prefix% &7Você &cdesativou&7 a manutenção"));
        Config.set("Maintenance.Broadcast.On", java.util.Arrays.asList(" &4* &cA manutenção está &eativa&4 *"));
        Config.set("Maintenance.Broadcast.Off", java.util.Arrays.asList(" &4* &cA manutenção está &edesativada&4 *"));

        Config.set("Urgent-mode.On", java.util.Arrays.asList("%prefix% &7Você &aativou&7 o modo de urgência"));
        Config.set("Urgent-mode.Off", java.util.Arrays.asList("%prefix% &7Você &cdesativou&7 o modo de urgência"));
        Config.set("Urgent-mode.Broadcast.On", java.util.Arrays.asList(" &4* &cO modo de urgência está &eativo&4 *"));
        Config.set("Urgent-mode.Broadcast.Off", java.util.Arrays.asList(" &4* &cO modo de urgência está &edesativado&4 *"));
        Config.set("Urgent-mode.Zip", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &c&cUm backup do Hawn foi feito"));
        Config.set("Urgent-mode.Error-Disable", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cErro, você precisa estar no console para desativar o modo de urgência"));
        Config.set("Urgent-mode.Error-cant-use-the-command", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &c&cDesculpe, você não pode usar este comando"));
        Config.set("Urgent-mode.Hawn-Watch-Panel-Admin", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cUma mudança foi feita por %player% no painel admin",
		            "%arg1% no arquivo %arg2%"));
        Config.set("Urgent-mode.Disabled-Plugin-function", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cTodos os plugins foram desativados"));
        Config.set("Urgent-mode.Ops-Removed", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &c%arg1% operador(es) removido(s) até o fim do modo de urgência"));
        Config.set("Urgent-mode.Ops-Restored", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &7%arg1% operador(es) devolvido(s)"));
        Config.set("Urgent-mode.Lockdown-Command", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cO servidor está em modo de urgência: os comandos estão desativados"));
        Config.set("Urgent-mode.Lockdown-Chat", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cO servidor está em modo de urgência: o chat está desativado"));
        Config.set("Urgent-mode.Zip-Failed", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cO backup falhou, veja o console"));
        Config.set("Urgent-mode.Console-Only", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cO modo de urgência só pode ser iniciado pelo console"));
        Config.set("Urgent-mode.Back-To-Normal-For-All-Plugins", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &7Todos os plugins foram &aativados",
		            "&eÉ preciso reiniciar para evitar problemas"));

        Config.set("Command-Blocker.Notify-Staff", java.util.Arrays.asList("%prefix% &e%player% tentou este comando: &b%arg1%"));

        Config.set("Command.SlotView.On", java.util.Arrays.asList("%prefix% &7Agora você &avê&7 o número de cada slot de um menu ou inventário ao clicar"));
        Config.set("Command.SlotView.Off", java.util.Arrays.asList("%prefix% &7O SlotView está &cdesativado"));

        Config.set("Command.IP", java.util.Arrays.asList("%prefix% &7O IP do jogador é: &e%getplayerip%"));

        Config.set("Command.Kickall", java.util.Arrays.asList("%prefix% &7Todos os jogadores foram expulsos"));

        Config.set("Command.ClearGroundItems", java.util.Arrays.asList("%prefix% &7Todos os itens foram tirados"));

        Config.set("Command.ClearMobs", java.util.Arrays.asList("%prefix% &7Todos os mobs foram tirados"));

        Config.set("Command.CheckAccount", java.util.Arrays.asList("",
		            "  &8→ &r&lInformações do jogador&8:&r &b%target%",
		            "&7&lData de entrada&r&8:&e %hawn_player_join_date%",
		            "&7&lData da primeira entrada&r&8:&e %hawn_player_first_join_date%",
		            "&7&lIP&r&8:&e %player_ip%",
		            "",
		            "  &8→ &r&lOpções&8:&r &8(&aVerde &8=&7 sim / &cVermelho&8 = &7não&8)",
		            "&7&lVisibilidade dos jogadores: %pv_point%",
		            "&7&lVelocidade: %ps_point% &7(&e%ps_number%&7)",
		            "&7&lVoo: %pof_point%",
		            "&7&lPulo duplo: %dj_point%",
		            "&7&lAnúncios automáticos: %ab_point%",
		            "&7&lInvisível: %v_point%",
		            "&7&lManter o scoreboard: %ksb_point% &8(&e%scorename%&8)",
		            "&7&lSuper pulo: %jb_point%",
		            "",
		            "&7&lModo de jogo: &e%gm_number%",
		            ""));

        // List

        Config.set("Command.List.Part-One", java.util.Arrays.asList("§8//§7§m---------------§r§8\\\\ §3[§bLista§3] §8//§7§m---------------§r§8\\\\",
		            " ",
		            "  &8→ &6&lPágina %number%",
		            " "));

        Config.set("Command.List.Part-Two", java.util.Arrays.asList("§8\\\\§7§m---------------§r§8// §3[§bLista§3] §8\\\\§7§m---------------§r§8//"));

        Config.set("Command.List.Gui.Other.Page.Next", "&bPróxima");
        Config.set("Command.List.Gui.Other.Page.Back", "&bAnterior");
        Config.set("Command.List.Gui.Other.Back.PanelAdmin", "&c&lVoltar ao painel admin");
        Config.set("Command.List.Gui.Player.Survival", "&cSOBREVIVÊNCIA");
        Config.set("Command.List.Gui.Player.Spectator", "&eESPECTADOR");
        Config.set("Command.List.Gui.Player.Creative", "&6CRIATIVO");
        Config.set("Command.List.Gui.Player.Adventure", "&aAVENTURA");
        Config.set("Command.List.Gui.Player.Gamemode", "&7Modo de jogo:");
        Config.set("Command.List.Gui.Player.World", "&7Mundo: &e");
        Config.set("Command.List.Gui.Player.LeftClick", "&eClique esquerdo&7 para editar o jogador");

        // EDIT PLAYER

        Config.set("Command.EditPlayer.Gui.Gamemode.LeftClick", "&eClique esquerdo&7 para mudar o modo de jogo do jogador");
        Config.set("Command.EditPlayer.Gui.Gamemode.Survival", "&cSOBREVIVÊNCIA");
        Config.set("Command.EditPlayer.Gui.Gamemode.Spectator", "&eESPECTADOR");
        Config.set("Command.EditPlayer.Gui.Gamemode.Creative", "&6CRIATIVO");
        Config.set("Command.EditPlayer.Gui.Gamemode.Adventure", "&aAVENTURA");

        Config.set("Command.EditPlayer.Gui.ClearInv.LeftClick", "&eClique esquerdo&7 para esvaziar o inventário e a armadura do jogador");
        Config.set("Command.EditPlayer.Gui.ClearInv.Item-Name", "&6Esvaziar o inventário");

        Config.set("Command.EditPlayer.Gui.Teleport.LeftClick", "&eClique esquerdo&7 para se teleportar até o jogador");
        Config.set("Command.EditPlayer.Gui.Teleport.Item-Name", "&6Teleportar até este jogador");

        Config.set("Command.EditPlayer.Gui.MoreSoon.Item-Name", "Mais em breve");

        Config.set("Command.EditPlayer.Gui.BackToPlayerList.Item-Name", "&cVoltar à lista de jogadores");

        // Since 1.5.3: the new items of Edit Player
        Config.set("Command.EditPlayer.Gui.Gamemode.Lore", java.util.Arrays.asList("", "&7Clique esquerdo: o próximo modo", "&7Clique direito: o anterior"));
        Config.set("Command.EditPlayer.Gui.Head.Lore", java.util.Arrays.asList("&7Mundo: &f%world%", "&7Modo de jogo: &f%gamemode%", "&7Rank: &f%rank%"));
        Config.set("Command.EditPlayer.Gui.On", "&aligado");
        Config.set("Command.EditPlayer.Gui.Off", "&cdesligado");
        Config.set("Command.EditPlayer.Gui.Toggle.Lore", java.util.Arrays.asList("", "&7Clique para mudar"));
        Config.set("Command.EditPlayer.Gui.Fly.Item-Name", "&eVoo: %value%");
        Config.set("Command.EditPlayer.Gui.Vanish.Item-Name", "&eVanish: %value%");
        Config.set("Command.EditPlayer.Gui.Walk-Speed.Item-Name", "&eVelocidade a pé: &f%value%/10");
        Config.set("Command.EditPlayer.Gui.Fly-Speed.Item-Name", "&eVelocidade de voo: &f%value%/10");
        Config.set("Command.EditPlayer.Gui.Speed.Lore", java.util.Arrays.asList("", "&7Clique esquerdo: &a+1&7, clique direito: &c-1", "&7Shift + clique: volta ao normal"));
        Config.set("Command.EditPlayer.Gui.Heal.Item-Name", "&eCurar e alimentar");
        Config.set("Command.EditPlayer.Gui.Heal.Lore", java.util.Arrays.asList("", "&7Clique esquerdo: curar", "&7Clique direito: alimentar"));
        Config.set("Command.EditPlayer.Gui.Teleport-Here.Item-Name", "&6Trazer este jogador até você");
        Config.set("Command.EditPlayer.Gui.Teleport-Here.LeftClick", "&eClique esquerdo&7 para teleportar o jogador até você");
        Config.set("Command.EditPlayer.Gui.Rank.Item-Name", "&eRank dado à mão: &f%value%");
        Config.set("Command.EditPlayer.Gui.Rank.Lore", java.util.Arrays.asList("", "&7Clique esquerdo: o próximo rank", "&7Clique direito: o anterior", "&7(- : nenhum rank à mão)"));
        Config.set("Command.EditPlayer.Gui.Rank.LuckPerms", java.util.Arrays.asList("", "&7Os ranks vêm do LuckPerms:", "&7mude o grupo do jogador lá"));
        Config.set("Command.EditPlayer.Gui.Hider.Item-Name", "&eQuem o jogador vê: %value%");
        Config.set("Command.EditPlayer.Gui.Hider.Everyone", "&atodos");
        Config.set("Command.EditPlayer.Gui.Hider.Staff", "&eequipe e VIP");
        Config.set("Command.EditPlayer.Gui.Hider.Nobody", "&cninguém");
        Config.set("Command.EditPlayer.Gui.Hider.Lore", java.util.Arrays.asList("", "&7Clique: o próximo estado"));
        Config.set("Command.EditPlayer.Changed", java.util.Arrays.asList("%prefix% &e%arg2% &8» %arg1%"));

        //

        Config.set("Command.No-Clip.Enable", java.util.Arrays.asList("%prefix% &7O No clip está &aativado"));

        Config.set("Command.No-Clip.Disable", java.util.Arrays.asList("%prefix% &7O No clip está &cdesativado"));

        Config.set("Command.NightVision", java.util.Arrays.asList("%prefix% &7Agora você enxerga à noite"));
        Config.set("Command.NightVision-Off", java.util.Arrays.asList("%prefix% &7Você não enxerga mais à noite"));

        /* -------------- *
			 * SPAWN COMMANDS *
			 * -------------- */
        Config.set("Command.Spawn.Spawn-Set.Default", java.util.Arrays.asList("&cVocê não deu um nome a este spawn: um nome automático foi escolhido",
		            "§eSpawn definido com o nome %spawnName%"));

        Config.set("Command.Spawn.Spawn-Set.Other", java.util.Arrays.asList("§eSpawn definido com o nome %spawnName%"));

        Config.set("Command.Del.Spawn-Delete", java.util.Arrays.asList("&bO spawn &e%spawn%&b foi apagado"));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }
}