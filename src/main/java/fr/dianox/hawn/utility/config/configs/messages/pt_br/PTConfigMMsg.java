package fr.dianox.hawn.utility.config.configs.messages.pt_br;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * The Portuguese (Brazil) messages (since 1.6), made from the English ones.
 */
public class PTConfigMMsg {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public PTConfigMMsg() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		
		file = new File(pl.getDataFolder(), "Messages/pt_BR/Messages.yml");
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
         * Chat stuff
         */
        Config.set("ClearChat.No-Reason", "nenhum motivo");
        Config.set("ClearChat.Anonymously", java.util.Arrays.asList(new String[] {" &4&l* &cO chat foi limpo &7(motivo: %reason%&7) &4&l*"}));
        Config.set("ClearChat.Normal", java.util.Arrays.asList(new String[] {" &4&l* &cO chat foi limpo por &6%player% &7(motivo: %reason%&7) &4&l*"}));
        Config.set("ClearChat.Own", java.util.Arrays.asList(new String[] {"%prefix% &e%player%&7, seu chat foi limpo"}));
        Config.set("ClearChat.Other.Target", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7, seu chat foi limpo"}));
        Config.set("ClearChat.Other.Sender", java.util.Arrays.asList(new String[] {"%prefix% &7O chat de &e%player%&7 foi limpo"}));

        Config.set("MuteChat.Can-t-Speak", java.util.Arrays.asList(new String[] {"%prefix% &cVocê não pode falar: o chat está bloqueado"}));
        Config.set("MuteChat.Admin.On", java.util.Arrays.asList(new String[] {" &4&m&l-<-=->-&r &cO chat foi bloqueado por &6%player% &4&m&l-<-=->-"}));
        Config.set("MuteChat.Admin.On-Time", java.util.Arrays.asList(new String[] {" &4&m&l-<-=->-&r &cO chat foi bloqueado por &6%player%&c por &6%minutes% minutos &4&m&l-<-=->-"}));
        Config.set("MuteChat.Admin.Off", java.util.Arrays.asList(new String[] {" &3&m&l->-=-<-&r &bO chat foi &adesbloqueado&b por &e%player% &3&m&l->-=-<-"}));

        Config.set("Ping.Self", java.util.Arrays.asList(new String[] {"%prefix% &7Seu ping é &e%ping%&7 ms"}));
        Config.set("Ping.Other", java.util.Arrays.asList(new String[] {"%prefix% &7O ping de &b%target%&7 é &e%ping%&7 ms"}));

        Config.set("ChatDelay.Delay", java.util.Arrays.asList(new String[] {"%prefix% &cVocê ainda não pode falar: espere &6%DELAY%&c segundo(s) entre duas mensagens"}));
        Config.set("ChatDelay.Admin.Set", java.util.Arrays.asList(new String[] {"%prefix% &7O intervalo do chat agora é de &e%DELAY%&7 segundo(s)", 
        		"          &7&oNão esqueça de mudar esse valor na configuração", 
        		"          &7&oSenão ele se perde no próximo reinício"}));
        Config.set("ChatDelay.Admin.Removed", java.util.Arrays.asList(new String[] {"%prefix% &7O intervalo do chat foi &cdesativado"}));

        Config.set("Broadcast", java.util.Arrays.asList(new String[] {"&8[&eAnúncio&8]&r %broadcast%"}));

        /*
         * Weather and Time
         */
        Config.set("Weather.Set.Sun.Enable", true);
        Config.set("Weather.Set.Sun.Message", java.util.Arrays.asList(new String[] {"%prefix% &7A chuva e a tempestade acabaram, &eviva o sol&7!"}));
        Config.set("Weather.Set.Rain.Enable", true);
        Config.set("Weather.Set.Rain.Message", java.util.Arrays.asList(new String[] {"%prefix% &7A &bchuva&7 cai neste mundo, &bcuidado para não se molhar&7!"}));
        Config.set("Weather.Set.Thunder.Enable", true);
        Config.set("Weather.Set.Thunder.Message", java.util.Arrays.asList(new String[] {"%prefix% &6&k!!!!&7 &eA tempestade é sinal de desastre&7... &eCuidado para não levar um raio &6&k!!!!"}));

        Config.set("Time.Set.Day.Enable", true);
        Config.set("Time.Set.Day.Message", java.util.Arrays.asList(new String[] {"%prefix% &7É de &emanhã&7, hora de ir para a escola"}));
        Config.set("Time.Set.Night.Enable", true);
        Config.set("Time.Set.Night.Message", java.util.Arrays.asList(new String[] {"%prefix% &f&lEstá escuro lá fora. Cuidado, a noite é escura e cheia de terrores!"}));

        /*
         * FLY COMMANDS
         */
        Config.set("Fly.Enable.Enable", true);
        Config.set("Fly.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu voo foi &aativado"}));
        Config.set("Fly.Enable-Other.Enable", true);
        Config.set("Fly.Enable-Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu voo foi &aativado&7 por &e%player%"}));
        Config.set("Fly.Enable-Other-Executor.Enable", true);
        Config.set("Fly.Enable-Other-Executor.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O voo de &e%target%&7 foi &aativado"}));

        Config.set("Fly.Disable.Enable", true);
        Config.set("Fly.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu voo foi &cdesativado"}));
        Config.set("Fly.Disable-Other.Enable", true);
        Config.set("Fly.Disable-Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu voo foi &cdesativado&7 por &e%player%"}));
        Config.set("Fly.Disable-Other-Executor.Enable", true);
        Config.set("Fly.Disable-Other-Executor.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O voo de &e%target%&7 foi &cdesativado"}));

        /* ----------------- *
			 * GAMEMODE COMMANDS *
			 * ----------------- */
        Config.set("Gamemode.Self.Survival.Enable", true);
        Config.set("Gamemode.Self.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu modo de jogo agora é &esobrevivência"}));
        Config.set("Gamemode.Self.Creative.Enable", true);
        Config.set("Gamemode.Self.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu modo de jogo agora é &ecriativo"}));
        Config.set("Gamemode.Self.Adventure.Enable", true);
        Config.set("Gamemode.Self.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu modo de jogo agora é &eaventura"}));
        Config.set("Gamemode.Self.Spectator.Enable", true);
        Config.set("Gamemode.Self.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu modo de jogo agora é &eespectador"}));

        Config.set("Gamemode.Other.Survival.Enable", true);
        Config.set("Gamemode.Other.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu modo de jogo agora é &esobrevivência&7, por &b%player%"}));
        Config.set("Gamemode.Other.Creative.Enable", true);
        Config.set("Gamemode.Other.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu modo de jogo agora é &ecriativo&7, por &b%player%"}));
        Config.set("Gamemode.Other.Adventure.Enable", true);
        Config.set("Gamemode.Other.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu modo de jogo agora é &eaventura&7, por &b%player%"}));
        Config.set("Gamemode.Other.Spectator.Enable", true);
        Config.set("Gamemode.Other.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu modo de jogo agora é &eespectador&7, por &b%player%"}));

        Config.set("Gamemode.Other-Sender.Survival.Enable", true);
        Config.set("Gamemode.Other-Sender.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O modo de jogo de &b%player%&7 agora é &esobrevivência"}));
        Config.set("Gamemode.Other-Sender.Creative.Enable", true);
        Config.set("Gamemode.Other-Sender.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O modo de jogo de &b%player%&7 agora é &ecriativo"}));
        Config.set("Gamemode.Other-Sender.Adventure.Enable", true);
        Config.set("Gamemode.Other-Sender.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O modo de jogo de &b%player%&7 agora é &eaventura"}));
        Config.set("Gamemode.Other-Sender.Spectator.Enable", true);
        Config.set("Gamemode.Other-Sender.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O modo de jogo de &b%player%&7 agora é &eespectador"}));

        Config.set("Gamemode.Error.Alread-In-The-Good-GM.Enable", true);
        Config.set("Gamemode.Error.Alread-In-The-Good-GM.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cVocê já está nesse modo de jogo"}));
        Config.set("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable", true);
        Config.set("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cO jogador %target% já está nesse modo de jogo"}));

        /* -------------- *
			 * GOTOP COMMANDS *
			 * -------------- */
        Config.set("Gotop.Enable", true);
        Config.set("Gotop.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Você foi teleportado para o bloco mais alto da sua posição"}));

        /* ------------- *
			 * FEED COMMANDS *
			 * ------------- */
        Config.set("Feed.Self.Enable", true);
        Config.set("Feed.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Sua fome foi saciada"}));

        Config.set("Feed.Other.Enable", true);
        Config.set("Feed.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Sua fome foi saciada por &e%player%"}));
        Config.set("Feed.Other-Sender.Enable", true);
        Config.set("Feed.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7A fome de &e%target%&7 foi saciada"}));

        /* ------------- *
			 * HEAL COMMANDS *
			 * ------------- */
        Config.set("Heal.Self.Enable", true);
        Config.set("Heal.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Você foi curado"}));

        Config.set("Heal.Other.Enable", true);
        Config.set("Heal.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Você foi curado por &e%player%"}));
        Config.set("Heal.Other-Sender.Enable", true);
        Config.set("Heal.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7 foi curado"}));

        /* ------------------- *
			 * ENDERCHEST COMMANDS *
			 * ------------------- */

        Config.set("EnderChest.Self.Enable", true);
        Config.set("EnderChest.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Você abriu seu baú do Ender"}));

        Config.set("EnderChest.Other-Sender.Enable", true);
        Config.set("EnderChest.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Você abriu o baú do Ender de &e%target%"}));

        /* --------------- *
			 * INVSEE COMMANDS *
			 * --------------- */

        Config.set("InvSee.Other-Sender.Enable", true);
        Config.set("InvSee.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Você abriu o inventário de &e%target%"}));

        /* ------------------- *
			 * SCOREBOARD COMMANDS *
			 * ------------------- */
        Config.set("Scoreboard.Toggle.On.Enable", true);
        Config.set("Scoreboard.Toggle.On.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O scoreboard foi &aativado"}));
        Config.set("Scoreboard.Toggle.Off.Enable", true);
        Config.set("Scoreboard.Toggle.Off.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O scoreboard foi &cdesativado"}));

        Config.set("Scoreboard.Changes.Enable", true);
        Config.set("Scoreboard.Changes.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu scoreboard agora é &e%arg1%"}));
        Config.set("Scoreboard.Error-Changes.Enable", true);
        Config.set("Scoreboard.Error-Changes.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cO scoreboard &6%arg1%&c não existe"}));

        Config.set("Scoreboard.Keep-On.Enable", true);
        Config.set("Scoreboard.Keep-On.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Agora você vai manter este scoreboard"}));
        Config.set("Scoreboard.Keep-Off.Enable", true);
        Config.set("Scoreboard.Keep-Off.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Você &cnão vai mais&7 manter este scoreboard"}));

        Config.set("Scoreboard.Error-No-Perm-For-Any-Score.Enable", true);
        Config.set("Scoreboard.Error-No-Perm-For-Any-Score.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cVocê não tem permissão para nenhum scoreboard"}));

        /* ------------- *
			 * WARP COMMANDS *
			 * ------------- */
        Config.set("Warp.Tp.Self.Enable", true);
        Config.set("Warp.Tp.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Você foi teleportado para o warp &e%warp%"}));
        Config.set("Warp.Tp.Self-Delay.Enable", true);
        Config.set("Warp.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Espere &4&l%second%&c segundo(s)&7 antes do teleporte"}));

        Config.set("Warp.Tp.Other.Enable", true);
        Config.set("Warp.Tp.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Você foi teleportado para o warp &e%warp%&7 por &b%player%"}));
        Config.set("Warp.Tp.Other-Sender.Enable", true);
        Config.set("Warp.Tp.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Você teleportou &b%target%&7 para o warp&e %warp%"}));
        Config.set("Warp.Tp.Other-Sender-Delay.Enable", true);
        Config.set("Warp.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Espere &4&l%second%&c segundo(s)&7 antes que este jogador seja teleportado"}));

        Config.set("Warp.List.Enable", true);
        Config.set("Warp.List.Messages", java.util.Arrays.asList(new String[] {"%prefix% &bLista de warps:&e %warplist%"}));

        Config.set("Warp.No-Warp.Enable", true);
        Config.set("Warp.No-Warp.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDesculpe, não há nenhum warp"}));

        Config.set("Warp.Set.Warp-Set.Enable", true);
        Config.set("Warp.Set.Warp-Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O warp foi criado com o nome &e%arg%"}));
        Config.set("Warp.Set.Warp-Already-Exist.Enable", true);
        Config.set("Warp.Set.Warp-Already-Exist.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEsse nome já existe"}));

        Config.set("Warp.Del.Warp-Doesnt-Exist.Enable", true);
        Config.set("Warp.Del.Warp-Doesnt-Exist.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEsse warp não existe"}));
        Config.set("Warp.Del.Warp-Delete.Enable", true);
        Config.set("Warp.Del.Warp-Delete.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O warp &e%warp%&7 foi &capagado"}));

        Config.set("Warp.Edit.Warp-Edited.Enable", true);
        Config.set("Warp.Edit.Warp-Edited.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O novo local do warp &e%warp% foi definido"}));

        /* ---------------- *
			 * SUICIDE COMMANDS *
			 * ---------------- */

        Config.set("Suicide.Self.Enable", true);
        Config.set("Suicide.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Você se matou"}));

        Config.set("Suicide.Broadcast.Enable", true);
        Config.set("Suicide.Broadcast.Messages", java.util.Arrays.asList(new String[] {"&7%player% deu adeus ao mundo"}));

        /* --------------- *
			 * REPAIR COMMANDS *
			 * --------------- */

        Config.set("Repair.Repaired.Enable", true);
        Config.set("Repair.Repaired.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O item &e%item% &7foi consertado"}));

        Config.set("Repair.Can-t-Repair.Enable", true);
        Config.set("Repair.Can-t-Repair.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDesculpe, você não pode consertar esse item"}));

        /* ------------ *
			 * EXP COMMANDS *
			 * ------------ */

        Config.set("Exp.Add.Sender.Enable", true);
        Config.set("Exp.Add.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%number_exp% &7foram adicionados aos pontos de experiência de &b%target%"}));
        Config.set("Exp.Add.Target.Enable", true);
        Config.set("Exp.Add.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%number_exp% &7foram adicionados aos seus pontos de experiência"}));

        Config.set("Exp.Set.Sender.Enable", true);
        Config.set("Exp.Set.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Os pontos de experiência de &b%target%&7 agora são &e%number_exp%"}));
        Config.set("Exp.Set.Target.Enable", true);
        Config.set("Exp.Set.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seus pontos de experiência agora são &e%number_exp%"}));

        Config.set("Exp.Take.Sender.Enable", true);
        Config.set("Exp.Take.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%target%&7 perdeu &e%number_exp%&7 pontos de experiência"}));
        Config.set("Exp.Take.Target.Enable", true);
        Config.set("Exp.Take.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Você perdeu &e%number_exp%&7 pontos de experiência"}));
        Config.set("Exp.Take.Sender-Error.Enable", true);
        Config.set("Exp.Take.Sender-Error.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cVocê não pode tirar mais que &6%target_exp_points%&c do jogador &e%target%"}));

        Config.set("Exp.Clear.Sender.Enable", true);
        Config.set("Exp.Clear.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%target%&7 agora tem 0 pontos de experiência"}));
        Config.set("Exp.Clear.Target.Enable", true);
        Config.set("Exp.Clear.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seus pontos de experiência foram zerados"}));

        /* ------------------ *
			 * WORKBENCH COMMANDS *
			 * ------------------ */
        Config.set("WorkBench.Self.Enable", true);
        Config.set("WorkBench.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Você abriu sua bancada de trabalho"}));

        Config.set("WorkBench.Target.Enable", true);
        Config.set("WorkBench.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Sua bancada de trabalho foi aberta por &e%player%"}));
        Config.set("WorkBench.Sender.Enable", true);
        Config.set("WorkBench.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Você abriu a bancada de trabalho para &e%target%&7"}));

        /* ------------- *
			 * BURN COMMANDS *
			 * ------------- */
        Config.set("Burn.Target.Enable", true);
        Config.set("Burn.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Você está pegando fogo por causa de &e%player%&7 por &b%duration%&7 segundos"}));
        Config.set("Burn.Sender.Enable", true);
        Config.set("Burn.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Você colocou fogo em &e%target%&7 por &b%duration%&7 segundos"}));

        /* -------------- *
			 * SKULL COMMANDS *
			 * -------------- */
        Config.set("Skull.Self.Enable", true);
        Config.set("Skull.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Você pegou sua própria cabeça"}));

        Config.set("Skull.Sender.Enable", true);
        Config.set("Skull.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Você pegou a cabeça de &e%target%"}));
        Config.set("Skull.Target.Enable", false);
        Config.set("Skull.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%player%&7 pegou sua cabeça"}));

        /*
         * Hat
         */
        Config.set("Hat.Self.Set.Enable", true);
        Config.set("Hat.Self.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7O novo chapéu foi colocado"}));
        Config.set("Hat.Self.Removed.Enable", true);
        Config.set("Hat.Self.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7O chapéu foi tirado"}));

        Config.set("Hat.Other-Target.Set.Enable", true);
        Config.set("Hat.Other-Target.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Você ganhou um novo chapéu de &e%player%"}));
        Config.set("Hat.Other-Target.Removed.Enable", true);
        Config.set("Hat.Other-Target.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Seu chapéu foi tirado por &e%player%"}));

        Config.set("Hat.Other-Sender.Set.Enable", true);
        Config.set("Hat.Other-Sender.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Você colocou um novo chapéu em &e%target%"}));
        Config.set("Hat.Other-Sender.Removed.Enable", true);
        Config.set("Hat.Other-Sender.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Você tirou o chapéu de &e%target%"}));

        Config.set("Hat.Error.No-Hat-Can-Be-Set.Enable", true);
        Config.set("Hat.Error.No-Hat-Can-Be-Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &cDesculpe, você não pode colocar um novo chapéu"}));
        Config.set("Hat.Error.Need-Have-NoEmpty-Helmet.Enable", true);
        Config.set("Hat.Error.Need-Have-NoEmpty-Helmet.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &cDesculpe, você não pode tirar o chapéu: está vazio"}));

        /*
         * GetPos
         */
        Config.set("GetPos.Enable", true);
        Config.set("GetPos.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% A posição do jogador é: &e%X% %Y% %Z%&7 no mundo&b %world%"}));

        /*
         * Vanish stuff
         */
        Config.set("Vanish.Self.Enable", true);
        Config.set("Vanish.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O vanish foi &aativado"}));
        Config.set("Vanish.Self-Disabled.Enable", true);
        Config.set("Vanish.Self-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O vanish foi &cdesativado"}));
        /*Config.set("Vanish.Self-Still.Enable", true);
        Config.set("Vanish.Self-Still.Messages", java.util.Arrays.asList(new String[] {"&bO vanish ainda está ativo"}));*/
        Config.set("Vanish.Other-Target.Enable", true);
        Config.set("Vanish.Other-Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O vanish foi &aativado&7 por&e %player%"}));
        Config.set("Vanish.Other-Target-Disabled.Enable", true);
        Config.set("Vanish.Other-Target-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O vanish foi &cdesativado&7 por&e %player%"}));
        Config.set("Vanish.Other-Sender.Enable", true);
        Config.set("Vanish.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O vanish de &b%target%&7 foi &aativado&7 por&e %player%"}));
        Config.set("Vanish.Other-Sender-Disabled.Enable", true);
        Config.set("Vanish.Other-Sender-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O vanish de &b%target%&7 foi &cdesativado&7 por&e %player%"}));
        Config.set("Vanish.Action-Bar", java.util.Arrays.asList(new String[] {"&aVocê está invisível"}));

        /*
         * Clear inv stuff
         */
        Config.set("ClearInv.Self.Enable", true);
        Config.set("ClearInv.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu inventário foi esvaziado"}));
        Config.set("ClearInv.Other-Target.Enable", true);
        Config.set("ClearInv.Other-Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu inventário foi esvaziado por &e%player%"}));
        Config.set("ClearInv.Other-Sender.Enable", true);
        Config.set("ClearInv.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7O inventário de &e%target%&7 foi esvaziado"}));

        /*
         * Spawn stuff
         */
        Config.set("Spawn.Tp.Self-Delay.Enable", true);
        Config.set("Spawn.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Espere &4&l%second%&c segundo(s)&7 antes do teleporte"}));
        Config.set("Spawn.Tp.Other-Sender-Delay.Enable", true);
        Config.set("Spawn.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Espere &4&l%second%&c segundo(s)&7 antes que este jogador seja teleportado"}));

        Config.set("Spawn.List.Enable", true);
        Config.set("Spawn.List.Messages", java.util.Arrays.asList(new String[] {"%prefix% &bLista de spawns:&e %spawnlist%"}));

        Config.set("Spawn.No-Spawn.Enable", true);
        Config.set("Spawn.No-Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDesculpe, não há nenhum spawn"}));

        /*
         * Warning stuff
         */
        Config.set("Warning", java.util.Arrays.asList(new String[] {
        		"<--center--> &4&m>-----------------------<", 
        		"", 
        		"%broadcast%",
        		"",
        		"<--center--> &4&m>-----------------------<"}));

        /*
         * 
         * 
         * EVENTS
         * 
         * 
         */

        Config.set("Teleport.VoidTP", java.util.Arrays.asList(new String[] {"%prefix% &7Ah não, você tentou chegar ao vazio"}));
        Config.set("Anti-Swear.Notify-Staff", java.util.Arrays.asList(new String[] {"&8[&eAnti&7-&ePalavrão&8] &b%player% &7disse &e%message%"}));
        Config.set("Anti-Spam.Repeat", java.util.Arrays.asList(new String[] {"%prefix% &cNão envie a mesma mensagem de novo"}));
        Config.set("Anti-Spam.Caps", java.util.Arrays.asList(new String[] {"%prefix% &cLetras maiúsculas demais na sua mensagem"}));
        Config.set("Anti-Advertising.Blocked", java.util.Arrays.asList(new String[] {"%prefix% &cSem propaganda no chat: endereços de outros servidores não são permitidos"}));
        Config.set("Anti-Advertising.Notify-Staff", java.util.Arrays.asList(new String[] {"&8[&eAnti&7-&eProp&8] &b%player% &7tentou enviar &e%message%"}));

        Config.set("LaunchPad.Cant-Use-Cooldown.Enable", true);
        Config.set("LaunchPad.Cant-Use-Cooldown.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Desculpe, você não pode usar o trampolim agora"}));

        Config.set("Cancel-Tp.Warp.Enable", true);
        Config.set("Cancel-Tp.Warp.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Teleporte &ccancelado"}));
        Config.set("Cancel-Tp.Spawn.Enable", true);
        Config.set("Cancel-Tp.Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Teleporte &ccancelado"}));

        Config.set("Custom-Join-Item.Error.FunGun.Time.Enable", true);
        Config.set("Custom-Join-Item.Error.FunGun.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Espere &4&l%timedelayfunguncji% &c&lsegundo(s)&7!"}));
        // The grappling hook (since 1.5.5)
        Config.set("Custom-Join-Item.Error.Grappling.Time.Enable", true);
        Config.set("Custom-Join-Item.Error.Grappling.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Espere &4&l%timedelaygrapplingcji% &c&lsegundo(s)&7!"}));
        Config.set("Custom-Join-Item.Error.Grappling.Too-Far.Enable", true);
        Config.set("Custom-Join-Item.Error.Grappling.Too-Far.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cO gancho está longe demais!"}));
        // The pearl mount (since 1.5.6)
        Config.set("Custom-Join-Item.Error.PearlMount.Time.Enable", true);
        Config.set("Custom-Join-Item.Error.PearlMount.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Espere &4&l%timedelaypearlmountcji% &c&lsegundo(s)&7!"}));
        Config.set("Custom-Join-Item.Error.PearlMount.Height.Enable", true);
        Config.set("Custom-Join-Item.Error.PearlMount.Height.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cA pérola montável só funciona entre as alturas &e%arg1% &ce &e%arg2%"}));

        /*
         * 
         * 
         * PLAYER OPTION
         * 
         * 
         */

        Config.set("PlayerOption.Help.Enable", true);
        Config.set("PlayerOption.Help.Messages", java.util.Arrays.asList(new String[] {
        		"&8//&7&m---------------&r&8\\\\ &3[&bPlayerOption&3] &8//&7&m---------------&r&8\\\\",
        		"",
        		"     &l>> &e&o&lAjuda das opções do jogador",
        		"",
        		" &8>> &7/option fly - &eAtiva o voo",
        		" &8>> &7/option doublejump - &eAtiva ou desativa o pulo duplo",
        		" &8>> &7/option speed - &eAtiva ou desativa a velocidade",
        		" &8>> &7/option flyspeed - &eAtiva ou desativa a velocidade de voo",
        		" &8>> &7/option jumpboost - &eAtiva ou desativa o super pulo",
        		" &8>> &7/option autobc - &eMostra ou esconde os anúncios automáticos",
        		" &8>> &7/option pv - &eMostra ou esconde os jogadores",
        		"",
        		"&8\\\\&7&m---------------&r&8// &3[&bPlayerOption&3] &8\\\\&7&m---------------&r&8//"}));

        Config.set("PlayerOption.DoubleJump.Enable.Enable", true);
        Config.set("PlayerOption.DoubleJump.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu pulo duplo foi &aativado"}));
        Config.set("PlayerOption.DoubleJump.Disable.Enable", true);
        Config.set("PlayerOption.DoubleJump.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu pulo duplo foi &cdesativado"}));

        Config.set("PlayerOption.Speed.Enable.Enable", true);
        Config.set("PlayerOption.Speed.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Sua velocidade foi &aativada"}));
        Config.set("PlayerOption.Speed.Disable.Enable", true);
        Config.set("PlayerOption.Speed.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Sua velocidade foi &cdesativada"}));
        Config.set("PlayerOption.Speed.Set.Enable", true);
        Config.set("PlayerOption.Speed.Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Sua velocidade agora é &e%arg1%"}));

        Config.set("PlayerOption.FlySpeed.Enable.Enable", true);
        Config.set("PlayerOption.FlySpeed.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Sua velocidade de voo foi &aativada"}));
        Config.set("PlayerOption.FlySpeed.Disable.Enable", true);
        Config.set("PlayerOption.FlySpeed.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Sua velocidade de voo foi &cdesativada"}));
        Config.set("PlayerOption.FlySpeed.Set.Enable", true);
        Config.set("PlayerOption.FlySpeed.Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Sua velocidade de voo agora é &e%arg1%"}));

        Config.set("PlayerOption.JumpBoost.Enable.Enable", true);
        Config.set("PlayerOption.JumpBoost.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu super pulo foi &aativado"}));
        Config.set("PlayerOption.JumpBoost.Disable.Enable", true);
        Config.set("PlayerOption.JumpBoost.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Seu super pulo foi &cdesativado"}));

        Config.set("PlayerOption.AutoBroadcast.Enable.Enable", true);
        Config.set("PlayerOption.AutoBroadcast.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Os anúncios automáticos agora estão &avisíveis"}));
        Config.set("PlayerOption.AutoBroadcast.Disable.Enable", true);
        Config.set("PlayerOption.AutoBroadcast.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Os anúncios automáticos agora estão &cescondidos"}));

        Config.set("PlayerOption.PlayerVisibility.ON.Enable", true);
        Config.set("PlayerOption.PlayerVisibility.ON.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Puf! Os jogadores sumiram"}));
        Config.set("PlayerOption.PlayerVisibility.OFF.Enable", true);
        Config.set("PlayerOption.PlayerVisibility.OFF.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Os jogadores estão visíveis de novo"}));
        // Since 1.4.9: the third state of the player hider
        Config.set("PlayerOption.PlayerVisibility.STAFF.Enable", true);
        Config.set("PlayerOption.PlayerVisibility.STAFF.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Agora só a equipe e os VIPs estão visíveis"}));
        // Since 1.5: the menus
        Config.set("Menus.Not-Found", java.util.Arrays.asList("%prefix% &cO menu &e%arg% &cnão existe"));
        Config.set("Menus.Not-In-This-World", java.util.Arrays.asList("%prefix% &cEste menu não pode ser aberto neste mundo"));

        Config.set("PlayerOption.Error.DoubleJump-Disabled.Enable", true);
        Config.set("PlayerOption.Error.DoubleJump-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cO pulo duplo está desativado em &6Cosmetics-Fun/DoubleJump.yml"}));
        Config.set("PlayerOption.Error.DoubleJump-Not-Good-World.Enable", true);
        Config.set("PlayerOption.Error.DoubleJump-Not-Good-World.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cO pulo duplo não está ativo neste mundo"}));

        // Since 1.5.7: /speed <n> and /flyspeed <n> while the option is off, with the command that turns it on
        Config.set("PlayerOption.Error.Turn-On-First.Enable", true);
        Config.set("PlayerOption.Error.Turn-On-First.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEsta opção está desligada: ative-a antes com &e%arg1%"}));
        //Config.set("PlayerOption.Error.Option-Already-Set.Enable", Boolean.valueOf(true));
        //Config.set("PlayerOption.Error.Option-Already-Set.Messages", java.util.Arrays.asList(new String[] {"&cEsta opção já está definida ou é a mesma"}));
        Config.set("PlayerOption.Error.Player-Visibility.Time.Enable", true);
        Config.set("PlayerOption.Error.Player-Visibility.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Espere &4&l%timedelaypvcji% &c&lsegundo(s)&7!"}));

        Config.set("PlayerOption.Error.Not-Enable-In-A-World.Enable", true);
        Config.set("PlayerOption.Error.Not-Enable-In-A-World.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cAs opções do jogador não estão ativas neste mundo"}));
        /*Config.set("PlayerOption.Error.Player-Visibility.Time-Command", java.util.Arrays.asList(new String[] {"&c&lEspere &4%timedelaypvcommands% &c&lsegundos!"}));*/            

        /*
         * 
         * OTHER STUFF
         * 
         */

        Config.set("Error.No-Permissions.Enable", true);
        Config.set("Error.No-Permissions.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDesculpe, você não tem a permissão: %noperm%"}));
        Config.set("Error.No-Spawn.Enable", true);
        Config.set("Error.No-Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cO spawn não existe"}));
        Config.set("Error.Change-Me.Enable", true);
        Config.set("Error.Change-Me.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cVocê precisa mudar o spawn/warp/etc. em &6%arg1%&c no arquivo &e%arg2%"}));
        Config.set("Error.No-Players.Enable", true);
        Config.set("Error.No-Players.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cO jogador não está online ou não existe"}));
        Config.set("Error.No-Page-Found.Enable", true);
        Config.set("Error.No-Page-Found.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cA página não foi encontrada"}));
        Config.set("Error.No-Category.Enable", true);
        Config.set("Error.No-Category.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cA categoria não existe"}));
        Config.set("Error.Use-Number.Enable", true);
        Config.set("Error.Use-Number.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cInforme um número"}));
        Config.set("Error.Command-Disable.Enable", true);
        Config.set("Error.Command-Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDesculpe, este comando está desativado"}));
        // Since 1.4.5: an error in a command (written in the console), instead of "Ocorreu um erro interno"
        Config.set("Error.Command-Failed.Enable", true);
        Config.set("Error.Command-Failed.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEste comando falhou: o erro está escrito no console, avise um admin"}));
        Config.set("Error.Command-Wrong-Arguments.Enable", true);
        Config.set("Error.Command-Wrong-Arguments.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cArgumentos errados. Uso: &e%usage%"}));
        Config.set("Error.Argument-Missing.Enable", true);
        Config.set("Error.Argument-Missing.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDesculpe, falta um ou dois argumentos"}));
        Config.set("Error.Not-A-Player.Enable", true);
        Config.set("Error.Not-A-Player.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cVocê não é um jogador"}));
        Config.set("Protection.Anti-Place", java.util.Arrays.asList(new String[] {"%prefix% &cDesculpe, você não pode colocar blocos aqui!"}));
        // The language menu (since 1.6)
        Config.set("Language.Changed.Enable", true);
        Config.set("Language.Changed.Messages", java.util.Arrays.asList("%prefix% &7Seu idioma agora é &e%arg1%"));
        Config.set("Language.Client.Enable", true);
        Config.set("Language.Client.Messages", java.util.Arrays.asList("%prefix% &7Agora você recebe as mensagens no idioma do seu jogo &8(&e%arg1%&8)"));
        Config.set("Protection.Anti-Break", java.util.Arrays.asList(new String[] {"%prefix% &cDesculpe, você não pode quebrar blocos aqui!"}));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }

}