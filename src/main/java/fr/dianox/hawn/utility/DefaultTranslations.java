package fr.dianox.hawn.utility;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The keys of Translations/ written by Hawn (since 1.6): the texts of the default menus (Menus/servers.yml,
 * profile.yml, language.yml), in every language of Hawn. Until 1.5.x, these texts were written in the menus themselves,
 * in the language of the server.
 */
public final class DefaultTranslations {

	private DefaultTranslations() {}

	/**
	 * The keys, in the order of the texts below. A menu writes {lang_menu.&lt;key&gt;}.
	 */
	static final String[] KEYS = {
		"servers-title", "survival", "players", "click-to-join", "minigames", "network", "close", "your-profile", "profile-title", "rank", "world", "player-number", "connections", "level", "fly", "speed", "who-you-see", "visibility-left", "visibility-right", "no-permission", "servers", "online", "back", "back-lore", "on", "off", "click-to-turn-off", "click-to-turn-on", "locked", "language", "language-lore", "language-title", "language-client", "language-client-lore", "language-chosen", "language-click"
	};

	private static final String[] EN = {"Choose a server", "Survival", "players", "Click to join", "Mini-games", "Network", "Close",
			"Click: your profile", "Profile of %player%", "Rank", "World", "Player number", "Connections", "Level", "Fly", "Speed",
			"Who you see", "Left click: everyone, staff and VIP, nobody", "Right click: everyone again", "You don't have the permission",
			"Servers", "Online", "Back", "The menu opened before, or close", "on", "off", "Click to turn it off", "Click to turn it on", "locked",
			"Language", "Choose the language of the messages", "Choose your language", "Language of my game", "The language of your client", "your language", "Click to choose it"};
	private static final String[] FR = {"Choisis un serveur", "Survie", "joueurs", "Clique pour rejoindre", "Mini-jeux", "Réseau", "Fermer",
			"Clique : ton profil", "Profil de %player%", "Rang", "Monde", "Joueur numéro", "Connexions", "Niveau", "Vol", "Vitesse",
			"Qui tu vois", "Clic gauche : tout le monde, staff et VIP, personne", "Clic droit : de nouveau tout le monde", "Tu n'as pas la permission",
			"Serveurs", "En ligne", "Retour", "Le menu ouvert avant, ou fermer", "activé", "désactivé", "Clique pour le désactiver", "Clique pour l'activer", "verrouillé",
			"Langue", "Choisis la langue des messages", "Choisis ta langue", "Langue de mon jeu", "La langue de ton client", "ta langue", "Clique pour la choisir"};
	private static final String[] DE = {"Wähle einen Server", "Survival", "Spieler", "Klicke zum Beitreten", "Minispiele", "Netzwerk", "Schließen",
			"Klick: dein Profil", "Profil von %player%", "Rang", "Welt", "Spieler Nummer", "Verbindungen", "Level", "Fliegen", "Geschwindigkeit",
			"Wen du siehst", "Linksklick: alle, Team und VIP, niemand", "Rechtsklick: wieder alle", "Du hast keine Berechtigung",
			"Server", "Online", "Zurück", "Das Menü davor, oder schließen", "an", "aus", "Klicke zum Ausschalten", "Klicke zum Einschalten", "gesperrt",
			"Sprache", "Wähle die Sprache der Nachrichten", "Wähle deine Sprache", "Sprache meines Spiels", "Die Sprache deines Clients", "deine Sprache", "Klicke, um sie zu wählen"};
	private static final String[] NL = {"Kies een server", "Survival", "spelers", "Klik om te joinen", "Minigames", "Netwerk", "Sluiten",
			"Klik: je profiel", "Profiel van %player%", "Rang", "Wereld", "Speler nummer", "Verbindingen", "Level", "Vliegen", "Snelheid",
			"Wie je ziet", "Linkerklik: iedereen, staff en VIP, niemand", "Rechterklik: weer iedereen", "Je hebt geen permissie",
			"Servers", "Online", "Terug", "Het menu ervoor, of sluiten", "aan", "uit", "Klik om uit te zetten", "Klik om aan te zetten", "vergrendeld",
			"Taal", "Kies de taal van de berichten", "Kies je taal", "Taal van mijn spel", "De taal van je client", "jouw taal", "Klik om hem te kiezen"};
	private static final String[] ES = {"Elige un servidor", "Supervivencia", "jugadores", "Haz clic para entrar", "Minijuegos", "Red", "Cerrar",
			"Clic: tu perfil", "Perfil de %player%", "Rango", "Mundo", "Jugador número", "Conexiones", "Nivel", "Vuelo", "Velocidad",
			"A quién ves", "Clic izquierdo: todos, staff y VIP, nadie", "Clic derecho: todos otra vez", "No tienes permiso",
			"Servidores", "Conectados", "Volver", "El menú anterior, o cerrar", "activado", "desactivado", "Haz clic para desactivarlo", "Haz clic para activarlo", "bloqueado",
			"Idioma", "Elige el idioma de los mensajes", "Elige tu idioma", "Idioma de mi juego", "El idioma de tu cliente", "tu idioma", "Haz clic para elegirlo"};
	private static final String[] IT = {"Scegli un server", "Sopravvivenza", "giocatori", "Clicca per entrare", "Minigiochi", "Rete", "Chiudi",
			"Clic: il tuo profilo", "Profilo di %player%", "Rango", "Mondo", "Giocatore numero", "Connessioni", "Livello", "Volo", "Velocità",
			"Chi vedi", "Clic sinistro: tutti, staff e VIP, nessuno", "Clic destro: di nuovo tutti", "Non hai il permesso",
			"Server", "Online", "Indietro", "Il menu di prima, o chiudi", "attivo", "spento", "Clicca per spegnerlo", "Clicca per attivarlo", "bloccato",
			"Lingua", "Scegli la lingua dei messaggi", "Scegli la tua lingua", "Lingua del mio gioco", "La lingua del tuo client", "la tua lingua", "Clicca per sceglierla"};
	private static final String[] PT = {"Escolha um servidor", "Sobrevivência", "jogadores", "Clique para entrar", "Minijogos", "Rede", "Fechar",
			"Clique: seu perfil", "Perfil de %player%", "Rank", "Mundo", "Jogador número", "Conexões", "Nível", "Voo", "Velocidade",
			"Quem você vê", "Clique esquerdo: todos, equipe e VIP, ninguém", "Clique direito: todos de novo", "Você não tem permissão",
			"Servidores", "Online", "Voltar", "O menu anterior, ou fechar", "ligado", "desligado", "Clique para desligar", "Clique para ligar", "bloqueado",
			"Idioma", "Escolha o idioma das mensagens", "Escolha seu idioma", "Idioma do meu jogo", "O idioma do seu cliente", "seu idioma", "Clique para escolher"};

	// The texts of the default files (AutoBroadcast.yml, Events/JoinQuitCommand.yml, the scoreboards, Tablist.yml):
	// key, then en_US, fr_FR, de_DE, nl_NL, es_ES, it_IT, pt_BR. A file writes {lang_text.<key>}.
	private static final String[][] TEXTS = {
		{"announcement", "ANNOUNCEMENT", "ANNONCE", "ANKÜNDIGUNG", "AANKONDIGING", "ANUNCIO", "ANNUNCIO", "ANÚNCIO"},
		{"welcome-server", "&7Welcome on &6&lYour Server&7! Type &e/help &7to see what you can do", "&7Bienvenue sur &6&lVotre Serveur&7 ! Tapez &e/help &7pour voir ce que vous pouvez faire", "&7Willkommen auf &6&lDeinem Server&7! Gib &e/help &7ein, um zu sehen, was du tun kannst", "&7Welkom op &6&lJouw Server&7! Typ &e/help &7om te zien wat je kunt doen", "&7¡Bienvenido a &6&lTu Servidor&7! Escribe &e/help &7para ver lo que puedes hacer", "&7Benvenuto su &6&lIl Tuo Server&7! Scrivi &e/help &7per vedere cosa puoi fare", "&7Bem-vindo ao &6&lSeu Servidor&7! Digite &e/help &7para ver o que você pode fazer"},
		{"discord", "&7Join our Discord: &ediscord.gg/yourserver", "&7Rejoignez notre Discord : &ediscord.gg/yourserver", "&7Tritt unserem Discord bei: &ediscord.gg/yourserver", "&7Kom in onze Discord: &ediscord.gg/yourserver", "&7Únete a nuestro Discord: &ediscord.gg/yourserver", "&7Unisciti al nostro Discord: &ediscord.gg/yourserver", "&7Entre no nosso Discord: &ediscord.gg/yourserver"},
		{"vote", "&7You like the server? Vote for it: &eyourserver.net/vote", "&7Vous aimez le serveur ? Votez pour lui : &eyourserver.net/vote", "&7Dir gefällt der Server? Stimme für ihn ab: &eyourserver.net/vote", "&7Vind je de server leuk? Stem erop: &eyourserver.net/vote", "&7¿Te gusta el servidor? Vota por él: &eyourserver.net/vote", "&7Ti piace il server? Votalo: &eyourserver.net/vote", "&7Gosta do servidor? Vote nele: &eyourserver.net/vote"},
		{"nether", "&7You are in the &cnether&7: watch your step!", "&7Vous êtes dans le &cnether&7 : attention où vous marchez !", "&7Du bist im &cNether&7: pass auf, wo du hintrittst!", "&7Je bent in de &cnether&7: kijk uit waar je loopt!", "&7Estás en el &cnether&7: ¡cuidado donde pisas!", "&7Sei nel &cnether&7: attento a dove metti i piedi!", "&7Você está no &cnether&7: cuidado onde pisa!"},
		{"have-fun", "&7Have fun, &e%player%&7!", "&7Amusez-vous bien, &e%player%&7 !", "&7Viel Spaß, &e%player%&7!", "&7Veel plezier, &e%player%&7!", "&7¡Diviértete, &e%player%&7!", "&7Buon divertimento, &e%player%&7!", "&7Divirta-se, &e%player%&7!"},
		{"event", "&b&lEvent", "&b&lÉvénement", "&b&lEvent", "&b&lEvenement", "&b&lEvento", "&b&lEvento", "&b&lEvento"},
		{"event-soon", "&7A new event starts soon", "&7Un nouvel événement commence bientôt", "&7Bald beginnt ein neues Event", "&7Er begint binnenkort een nieuw evenement", "&7Pronto empieza un nuevo evento", "&7Un nuovo evento inizia a breve", "&7Um novo evento começa em breve"},
		{"lost", "&7Lost? Type &e/spawn", "&7Perdu ? Tapez &e/spawn", "&7Verlaufen? Gib &e/spawn &7ein", "&7Verdwaald? Typ &e/spawn", "&7¿Perdido? Escribe &e/spawn", "&7Ti sei perso? Scrivi &e/spawn", "&7Perdido? Digite &e/spawn"},
		{"players-online", "&7Players online: &e%online%&7/&e%max_players%", "&7Joueurs en ligne : &e%online%&7/&e%max_players%", "&7Spieler online: &e%online%&7/&e%max_players%", "&7Spelers online: &e%online%&7/&e%max_players%", "&7Jugadores conectados: &e%online%&7/&e%max_players%", "&7Giocatori online: &e%online%&7/&e%max_players%", "&7Jogadores online: &e%online%&7/&e%max_players%"},
		{"need-help", "&7Need help? Ask the staff or type &e/help", "&7Besoin d'aide ? Demandez au staff ou tapez &e/help", "&7Brauchst du Hilfe? Frag das Team oder gib &e/help &7ein", "&7Hulp nodig? Vraag het aan de staff of typ &e/help", "&7¿Necesitas ayuda? Pregunta al staff o escribe &e/help", "&7Serve aiuto? Chiedi allo staff o scrivi &e/help", "&7Precisa de ajuda? Pergunte à equipe ou digite &e/help"},
		{"bossbar-welcome", "&7Welcome on &6&lYour Server&7, &e%player%", "&7Bienvenue sur &6&lVotre Serveur&7, &e%player%", "&7Willkommen auf &6&lDeinem Server&7, &e%player%", "&7Welkom op &6&lJouw Server&7, &e%player%", "&7Bienvenido a &6&lTu Servidor&7, &e%player%", "&7Benvenuto su &6&lIl Tuo Server&7, &e%player%", "&7Bem-vindo ao &6&lSeu Servidor&7, &e%player%"},
		{"bossbar-event", "&b&lEvent &7in progress: join it with &e/warp event", "&b&lÉvénement &7en cours : rejoignez-le avec &e/warp event", "&b&lEvent &7läuft: mach mit mit &e/warp event", "&b&lEvenement &7bezig: doe mee met &e/warp event", "&b&lEvento &7en curso: únete con &e/warp event", "&b&lEvento &7in corso: partecipa con &e/warp event", "&b&lEvento &7em andamento: participe com &e/warp event"},
		{"bossbar-vote", "&eVote for the server: &fyourserver.net/vote", "&eVotez pour le serveur : &fyourserver.net/vote", "&eStimme für den Server ab: &fyourserver.net/vote", "&eStem op de server: &fyourserver.net/vote", "&eVota por el servidor: &fyourserver.net/vote", "&eVota il server: &fyourserver.net/vote", "&eVote no servidor: &fyourserver.net/vote"},
		{"join-new", "&7Welcome to the server, &e%player%&7! Type &e/rules &7to read the rules", "&7Bienvenue sur le serveur, &e%player%&7 ! Tapez &e/rules &7pour lire les règles", "&7Willkommen auf dem Server, &e%player%&7! Gib &e/rules &7ein, um die Regeln zu lesen", "&7Welkom op de server, &e%player%&7! Typ &e/rules &7om de regels te lezen", "&7¡Bienvenido al servidor, &e%player%&7! Escribe &e/rules &7para leer las reglas", "&7Benvenuto sul server, &e%player%&7! Scrivi &e/rules &7per leggere le regole", "&7Bem-vindo ao servidor, &e%player%&7! Digite &e/rules &7para ler as regras"},
		{"join-back", "&7Welcome back, &e%player%&7!", "&7Bon retour, &e%player%&7 !", "&7Willkommen zurück, &e%player%&7!", "&7Welkom terug, &e%player%&7!", "&7¡Bienvenido de nuevo, &e%player%&7!", "&7Bentornato, &e%player%&7!", "&7Bem-vindo de volta, &e%player%&7!"},
		{"admin-tools", "&7Admin tools: &e/hawn &7and &e/ap", "&7Outils d'admin : &e/hawn &7et &e/ap", "&7Admin-Werkzeuge: &e/hawn &7und &e/ap", "&7Admintools: &e/hawn &7en &e/ap", "&7Herramientas de admin: &e/hawn &7y &e/ap", "&7Strumenti admin: &e/hawn &7e &e/ap", "&7Ferramentas de admin: &e/hawn &7e &e/ap"},
		{"sb-welcome", "&7Welcome &e%player%", "&7Bienvenue &e%player%", "&7Willkommen &e%player%", "&7Welkom &e%player%", "&7Bienvenido &e%player%", "&7Benvenuto &e%player%", "&7Bem-vindo &e%player%"},
		{"sb-latency", "&7Your latency", "&7Votre latence", "&7Deine Latenz", "&7Je latentie", "&7Tu latencia", "&7La tua latenza", "&7Sua latência"},
		{"sb-online", "&7Online: &e%online%&7/&e%max_players%", "&7En ligne : &e%online%&7/&e%max_players%", "&7Online: &e%online%&7/&e%max_players%", "&7Online: &e%online%&7/&e%max_players%", "&7Conectados: &e%online%&7/&e%max_players%", "&7Online: &e%online%&7/&e%max_players%", "&7Online: &e%online%&7/&e%max_players%"},
		{"sb-exp", "&7Exp: &e%player_level% &7(&e%player_exp%&7/&6%player_exp_to_level%&7)", "&7XP : &e%player_level% &7(&e%player_exp%&7/&6%player_exp_to_level%&7)", "&7EP: &e%player_level% &7(&e%player_exp%&7/&6%player_exp_to_level%&7)", "&7XP: &e%player_level% &7(&e%player_exp%&7/&6%player_exp_to_level%&7)", "&7Exp: &e%player_level% &7(&e%player_exp%&7/&6%player_exp_to_level%&7)", "&7Esp: &e%player_level% &7(&e%player_exp%&7/&6%player_exp_to_level%&7)", "&7XP: &e%player_level% &7(&e%player_exp%&7/&6%player_exp_to_level%&7)"},
		{"sb-time", "&7Time: &e%gettime%", "&7Heure : &e%gettime%", "&7Uhrzeit: &e%gettime%", "&7Tijd: &e%gettime%", "&7Hora: &e%gettime%", "&7Ora: &e%gettime%", "&7Hora: &e%gettime%"},
		{"sb-health", "&7Health: &e%player_health% &6&lHP", "&7Vie : &e%player_health% &6&lPV", "&7Leben: &e%player_health% &6&lHP", "&7Leven: &e%player_health% &6&lHP", "&7Vida: &e%player_health% &6&lPV", "&7Salute: &e%player_health% &6&lPV", "&7Vida: &e%player_health% &6&lHP"},
		{"sb-food", "&7Food: &e%player_food_level%", "&7Faim : &e%player_food_level%", "&7Hunger: &e%player_food_level%", "&7Honger: &e%player_food_level%", "&7Hambre: &e%player_food_level%", "&7Fame: &e%player_food_level%", "&7Fome: &e%player_food_level%"},
		{"sb-world", "&7World: &e%player_world%", "&7Monde : &e%player_world%", "&7Welt: &e%player_world%", "&7Wereld: &e%player_world%", "&7Mundo: &e%player_world%", "&7Mondo: &e%player_world%", "&7Mundo: &e%player_world%"},
		{"sb-minimalist", "&cMinimalist scoreboard", "&cScoreboard minimaliste", "&cMinimalistisches Scoreboard", "&cMinimalistisch scoreboard", "&cScoreboard minimalista", "&cScoreboard minimalista", "&cScoreboard minimalista"},
		{"sb-thanks", "&cThanks to use &4&lHawn", "&cMerci d'utiliser &4&lHawn", "&cDanke, dass du &4&lHawn &cnutzt", "&cBedankt voor het gebruik van &4&lHawn", "&cGracias por usar &4&lHawn", "&cGrazie per usare &4&lHawn", "&cObrigado por usar o &4&lHawn"},
		{"tab-welcome", "&7Welcome on &6&lYour Server", "&7Bienvenue sur &6&lVotre Serveur", "&7Willkommen auf &6&lDeinem Server", "&7Welkom op &6&lJouw Server", "&7Bienvenido a &6&lTu Servidor", "&7Benvenuto su &6&lIl Tuo Server", "&7Bem-vindo ao &6&lSeu Servidor"},
		{"tab-you-are", "&7You are &e%player%", "&7Vous êtes &e%player%", "&7Du bist &e%player%", "&7Je bent &e%player%", "&7Eres &e%player%", "&7Sei &e%player%", "&7Você é &e%player%"},
		{"tab-shop", "&7Shop&8: &eshop.yourserver.net", "&7Boutique&8 : &eshop.yourserver.net", "&7Shop&8: &eshop.yourserver.net", "&7Winkel&8: &eshop.yourserver.net", "&7Tienda&8: &eshop.yourserver.net", "&7Negozio&8: &eshop.yourserver.net", "&7Loja&8: &eshop.yourserver.net"},
		{"tab-website", "&7Website&8: &eyourserver.net", "&7Site&8 : &eyourserver.net", "&7Webseite&8: &eyourserver.net", "&7Website&8: &eyourserver.net", "&7Web&8: &eyourserver.net", "&7Sito&8: &eyourserver.net", "&7Site&8: &eyourserver.net"},
		{"tab-online", "&7Online&8: &e%online%&7/&e%max_players%", "&7En ligne&8 : &e%online%&7/&e%max_players%", "&7Online&8: &e%online%&7/&e%max_players%", "&7Online&8: &e%online%&7/&e%max_players%", "&7Conectados&8: &e%online%&7/&e%max_players%", "&7Online&8: &e%online%&7/&e%max_players%", "&7Online&8: &e%online%&7/&e%max_players%"},
		{"tab-time", "&7Time&8: &e%gettime%", "&7Heure&8 : &e%gettime%", "&7Uhrzeit&8: &e%gettime%", "&7Tijd&8: &e%gettime%", "&7Hora&8: &e%gettime%", "&7Ora&8: &e%gettime%", "&7Hora&8: &e%gettime%"},
		{"tab-world", "&7You are in &c%player_world%", "&7Vous êtes dans &c%player_world%", "&7Du bist in &c%player_world%", "&7Je bent in &c%player_world%", "&7Estás en &c%player_world%", "&7Sei in &c%player_world%", "&7Você está em &c%player_world%"}
,
		{"hologram-title", "Welcome on Your Server", "Bienvenue sur Votre Serveur", "Willkommen auf Deinem Server", "Welkom op Jouw Server", "Bienvenido a Tu Servidor", "Benvenuto su Il Tuo Server", "Bem-vindo ao Seu Servidor"},
		{"hologram-online", "%online% player(s) online", "%online% joueur(s) en ligne", "%online% Spieler online", "%online% speler(s) online", "%online% jugador(es) conectado(s)", "%online% giocatore/i online", "%online% jogador(es) online"}
	};

	private static final java.util.List<String> CODES = java.util.Arrays.asList("en_US", "fr_FR", "de_DE", "nl_NL", "es_ES", "it_IT", "pt_BR");

	/**
	 * {lang_menu.&lt;key&gt;} for each text, in the order of KEYS: what the default menus write.
	 */
	public static String[] placeholders() {
		String[] t = new String[KEYS.length];
		for (int i = 0; i < KEYS.length; i++) {
			t[i] = "{lang_menu." + KEYS[i] + "}";
		}
		return t;
	}

	/**
	 * The default texts of a language (menu.&lt;key&gt; -&gt; text), empty for a language Hawn does not have.
	 */
	public static Map<String, String> of(String code) {
		String[] texts;
		switch (code) {
			case "en_US": texts = EN; break;
			case "fr_FR": texts = FR; break;
			case "de_DE": texts = DE; break;
			case "nl_NL": texts = NL; break;
			case "es_ES": texts = ES; break;
			case "it_IT": texts = IT; break;
			case "pt_BR": texts = PT; break;
			default: return Collections.emptyMap();
		}
		Map<String, String> map = new LinkedHashMap<>();
		for (int i = 0; i < KEYS.length && i < texts.length; i++) {
			map.put("menu." + KEYS[i], texts[i]);
		}
		int column = CODES.indexOf(code) + 1;
		for (String[] text : TEXTS) {
			map.put("text." + text[0], text[column]);
		}
		return map;
	}
}
