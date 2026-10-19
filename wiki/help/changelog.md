---
description: What changed in the recent versions of Hawn.
---

# Changelog

## 1.4.9 BETA

### New

* The player hider has three states: everyone, only the staff and the VIP, nobody. The staff and the VIP are the ranks of `PV.Option.Three-States.Staff-And-VIP-Ranks` (owner, admin, vip) and the players with `hawn.playervisibility.shown`; a rank given or removed shows at once. A third item, `PV.STAFF`, shows this state. On for the new installations; the files already there keep two states (`PV.Option.Three-States.Enable`). See [Custom join items](../features/custom-join-items.md#player-visibility-special-hideplayers).
* `/option pv all`, `staff` or `none` chooses the state; `/option pv` alone goes to the next one.
* MVdWPlaceholderAPI is no longer supported: it has not been updated since 2020, and the premium plugins of Maximvdw use PlaceholderAPI since 2021. Its section is removed from `general.yml`.

### Fixes

* `PlayerWorldChange.yml`, `GM`: the gamemode was changed in the worlds that were **not** listed (with the default file, a player going to the End became creative), and `All_World: true` did nothing. Only the listed worlds change it now. The new installations use adventure, like the join.
* `PlayerWorldChange.yml`, `Fly`: `All_World: true` did nothing. The gamemode is now changed before the fly, so a new gamemode no longer removes it.
* The player hider: with `/v` turned off completely (`DISABLE_THE_COMMAND_COMPLETELY`), a player who joined was seen by the players who hide everyone.
* The player hider: a player back from the vanish was shown to everyone, even to the players who hide everyone.
* The player hider item: during its delay, a click was not cancelled (a block could be placed with it). The item is recognized by Hawn itself now, not by its title: a title with placeholders works.
* `PV.Option.Inventory-Click.Interact-With-The-Object` was never read. `false` now keeps the click in the inventory from changing the state.
* `Fly.Enable.Player-Options-primary` and `GM.Player-Options-primary` were read but never created: removed.

## 1.4.8 BETA

### New

* Ranks above the heads: the prefix and the suffix of the rank above the head of the players, and the name in the `Name-Colour` of the rank (one of the 16 colours; without it, the last colour of the prefix, a hex colour giving the nearest one). `Ranks.Above-Head.Enable` and `Ranks.Name-Colour.Enable`, and `Above-Head` / `Name-Colour` per world. Like the order of the tab list, it is sent to the players in packets: nothing is created on the server, and the teams of the other plugins are not touched. See [Ranks](../features/ranks.md).
* `Ranks.Source: LUCKPERMS`: the rank is the group of the player in LuckPerms, with its prefix, its suffix and its weight (the highest first in the tab list). A change in LuckPerms shows at once, without a rejoin.
* `/hawn rank set <player> <rank>`, `remove <player>`, `info <player>` and `list`: the ranks given by hand, written in `Ranks.Players` and shown at once (`hawn.admin.command.rank`). With LuckPerms, `set` says to change the group in LuckPerms.
* `/hawn debug` (`hawn.admin.command.debug`): the mistakes of the configuration files (unknown sounds, materials, effects, damage types, actions written wrong), the options in `AUTO` left to another plugin (with its version), and the errors Hawn went on without since the start. `Plugin.Debug: true` in `general.yml` writes these errors in the console. A wrong sound or material is now warned once, not at each use.
* The welcome setup: Previous step and Next step buttons (doors, never arrows: the arrows of the world manager keep changing its pages). At the step of the spawn, the admin goes to the default world chosen at the step 2, since the spawn is created where the admin stands.
* Cleaner examples on a new installation: the auto broadcast, the server list MOTD, the tab list, the scoreboard, the custom commands and the join commands show a coherent lobby (Your Server, discord.gg/yourserver...). The files already there don't change.

### Fixes

* The menus of Hawn were recognized by their title: a chest or the menu of another plugin with the same title ran the actions of Hawn. Each menu is recognized by its type now.
* Paper: a message changed by Hawn (an emoji, a word of the anti-swear, a highlighted mention) lost the clicks and the hover texts added by another plugin. Hawn now only replaces its words inside the message.
* `/hawn nightvision` could only turn the night vision on. It now turns it off when it is on.
* `/hawn help` didn't list `/hawn debug` and `/hawn rank`.
* The world manager: the title "Import started" (or "World in creation") stayed on screen when the message of the end had no title (the French one). It goes away as soon as the world is loaded or created, and the French messages have their title of the end too (new installations).
* A wrong damage type in `AntiDamage-Custom.Entity.Options.Damage-Type-List` was ignored without a word. It is said in the console now.
* Some errors were hidden without a trace (scoreboard animations, signs, the player list menu): they are counted in `/hawn debug`.

## 1.4.7 BETA

### New

* Ranks: `Ranks.yml` gives each player a rank, shown in the tab list before and after the name, and the tab list is sorted by rank (the lowest `Priority` first). A rank comes from the permission `hawn.rank.<rank>` (not given to the operators), from `Players` (given by hand), or else from the default rank. `%rank%`, `%rank_prefix%` and `%rank_suffix%` work in every text of Hawn (chat format, scoreboards...), and `%hawn_rank_prefix%` in the other plugins. On by default with three examples (owner, admin, vip); the default rank is off. With `Enable: AUTO`, Hawn leaves the tab list to TAB or NametagEdit when one of them is installed. Each world can turn the prefix or the order off. The ranks above the heads come in 1.4.8. See [Ranks](../features/ranks.md).
* The world manager loads and unloads worlds: a left click on a world that is not loaded (red pane) loads it like `/hw import`, and the menu of a world has an Unload button. With the button and with `/hw unload`, the players in the world go to the default world first (before, `/hw unload` was refused while players were in it).
* The thresholds of the TPS warning: `Plugin.Tps.Warning-At` and `Plugin.Tps.Critical-At` in `general.yml` (15 and 5, as before). On new installations the messages show the threshold.
* A Discord for help and news: https://discord.gg/yeUg5mbJuz. It is in `/hawn about`, at startup, and in the update notice of the admins (new installations).
* At startup and at `/hawn reload`, a warning when the drop protection (`Items.Drop.Disable` in `Events/PlayerEvents.yml`) and `Block-Drop` of the join items work against each other: for example `Block-Drop: NONE` while the protection still blocks every drop.

### Fixes

* Under 5 TPS, the TPS warning saved every world and every player while the server was already lagging. This emergency save is off by default now (`Plugin.Tps.Emergency-Save.Enable`). When it is on, the save is spread over the ticks (one world, then a few players, at each tick), and you choose what it saves (`Worlds`, `Players`).
* The double jump sent the abilities of every player on the ground to their client at each move, up to 20 times a second per player. Now only when they change.
* The chat per group of worlds looked for the group of every player who receives a message, at each message. The groups are now read once, and again at `/hawn reload`.
* The default world chosen in the welcome setup or with `/hw default` didn't show in a world list already open: "Default world" stayed on the old world until the list was opened again.
* During the welcome setup of an admin, any player who clicked the slot 48 of the world list moved the setup to the next step and got the spawn menu. This also worked on the next pages of the list.
* A file where a whole list was missing (all the join items, all the ranks...) only got the first option of that list back. Without join items in `CustomJoinItem/General.yml`, Hawn then failed to start. The whole list is added back now, and Hawn starts without join items.
* Paper 26.x: a world created with Hawn and then unloaded disappeared from the world list, because its folder is in the folder of the main world. It is listed now, and can be loaded or deleted from the menu.

## 1.4.6 BETA

### New

* German: Hawn now comes with `Messages/de_DE` (about 600 messages), next to English and French. Choose it in the welcome setup or with `Plugin.Language-Type: de_DE` in `general.yml`. The setup now shows the German flag for it.
* The placeholders of Hawn in the other plugins: with PlaceholderAPI, `%hawn_<placeholder>%` (`%hawn_tps%`, `%hawn_online_world_nether%`, `%hawn_player_join_number%`...) works in TAB, DeluxeMenus, holograms... Nothing to download. `%player_ip%` is kept inside Hawn by default. See [In other plugins](../reference/placeholders.md#in-other-plugins).
* At startup and at `/hawn reload`, a warning lists the protections limited to WorldGuard regions while WorldGuard is not installed (or its hook is off): they apply in the whole worlds, as they always did.
* `Block-Drop` in `CustomJoinItem/General.yml`: in the worlds of the join items, `ALL` (as before) blocks the drop of every item, `JOIN-ITEMS` only of the join items, `NONE` of nothing. See [Custom join items](../features/custom-join-items.md#general-options).
* The auto broadcast: in `world_list`, `*` stands for any text (`world*`).

### Fixes

* A scoreboard kept with `/scoreboard keep` whose file was deleted or renamed gave an error at every refresh of the player. The player now gets the scoreboard of the world.
* `/hawn setup` opened the setup for every admin online: the world manager opened at each step of the others, their join items, spawn and scoreboard were skipped when they joined, and a `/setspawn` of another admin ended the setup. Only the admin who opens it is in it now (at the first start of Hawn, every admin still gets it).
* The auto broadcast in order: after the last message, one turn passed without a message.
* The auto broadcast: `world_list` compared a part of the name: `world` also sent the message in `world_nether`. The whole name counts now (write `world*` for the old behaviour). The example message "You are in the nether" has `world_nether` in its list (new installations).
* Every snowball thrown by a player with `hawn.use.customjoinitem` made the hearts, lava and meow of the fun gun. Only the snowballs of the fun gun do now.
* The titles of the fun gun, the lobby bow and the player hider were read once at startup: after a change and `/hawn reload`, the items stopped working until a restart.
* A sign of the sign system that disappeared without being broken by a player (explosion, WorldEdit, piston) stayed registered: another sign put at the same place ran its actions.
* `%hawn_player_join_date%` and `%hawn_player_first_join_date%` gave an error for a player without dates. A scroller line `{SC_...}` of a scoreboard could give an error before its first turn.
* A join item with a wrong leather colour (`Color: 255,0`) or a slot above 40 stopped the join of the player (the items after it were not given). They now give a warning in the console, once.
* `/checka <player>` showed the options (fly, speed, double jump, vanish...) of the admin who typed it, not of the player checked.
* `/hw import` of a copy of a loaded world (same `uid.dat`): Spigot refused it as a "duplicate" and kept its folder locked until a restart, so the next import failed with `AccessDeniedException`. Hawn now removes the `uid.dat` of the copy before loading it (at the import and at startup), and says it in the console.
* Spigot 26.x: the spawn of a world imported from an older version stayed in the main world. `/hw tp` and the world manager said "teleported in the world" but sent the player to the main world, at the same coordinates. Hawn now puts the spawn back in the world itself (at startup, after an import and before a teleport), and says it in the console.

## 1.4.5 BETA

### New

* Anti-advertising in the chat: the IP addresses and the domain names of other servers are blocked, also hidden as `server . net` or `server(dot)com`, with a whitelist for your own addresses. Off by default: `Anti-Advertising.Enable` in `Events/Chat.yml`. See [Anti-advertising](../features/chat.md#anti-advertising).
* A command of Hawn that fails no longer shows "An internal error occurred while attempting to perform this command". When the arguments caused it, the player gets `Error.Command-Wrong-Arguments` with the usage of the command; otherwise `Error.Command-Failed`. Both are in `Messages.yml`, and the console gets the whole error with the command typed (to report on GitHub). Every command of Hawn was tried with wrong arguments (1481 combinations): none fails.

### Fixes

* A wrong number or a missing argument made commands fail ("An internal error occurred"): `/burn <player> abc`, `/exp <player> add` without an amount, `/delaychat abc`, `/list abc`, the page numbers of `/ap`, `/ap edit file` without a file, `/hw tp` without a world. They now say what is wrong. `/hw tp` to a world that doesn't exist says it too.
* Old actions written wrongly stopped the whole list of messages: `[send-title]: Title//n` without a subtitle, `[send-title[...` or `[send-actionbar[...` without `]]:` or with a wrong number, `[effect[abc]]`. They now use a default value.
* A message blocked by the anti-spam started the chat delay: the next message of the player was refused.
* With the emergency mode and the maintenance both on, a player who could not join got the message of the maintenance instead of the one of the emergency mode.
* `/warp` and `/v` showed `Â` in front of their usage.
* `/hawn parse <player> <text>` ran the actions of the text (`[gamemode-creative]` changed the game mode). The text is now only shown, and can have spaces.
* `/hawn spawnmanager remove <spawn>` from the console said that the spawn "remove" was deleted.

## 1.4.4 BETA

### New

* Placeholders of the player: `%player_gamemode%`, `%player_vanished%`, `%player_flying%`, `%player_option_<option>%` (the choices of `/option`), `%player_connections%`. See [Placeholders](../reference/placeholders.md#player).
* Placeholders of the server: `%server_uptime%`, `%chat_muted%`, `%maintenance%`. The yes/no values are `Placeholders.Yes` and `Placeholders.No` of `General.yml`.
* `%player_join_number%` and `%unique_players%`: the order of arrival of the players ("you are the player #1234"). The players who already came are numbered in their order of arrival at the first start. The default first join broadcast shows it (new installations). See [Order of arrival](../reference/placeholders.md#order-of-arrival).
* With several servers on the same MySQL database: the numbers are the ones of the network, and the first join happens once for the whole network (`Plugin.Use.MYSQL.First-Join-On-The-Network`). See [Several servers on one database](../features/database.md#several-servers-on-one-database).
* World manager: the size of the worlds is computed in the background and kept one minute. Before, the menu read every file of every world on the main thread at each opening (and at each step of the player during the setup).

### Fixes

* World manager menu: a right click on something else than a loaded world (a pane, an arrow, a world not loaded) gave an error, and a shift + right click opened the delete page for a pane.
* World manager menu: the hours were wrong: 00:00 set 22:00, and 18:00 set 17:00.
* World manager menu: with more than 36 worlds, the 36th world was on both pages; with more than 72, the worlds went over the bar of the menu and the last ones could not be reached. The worlds are now shown 36 per page, on as many pages as needed.
* `/hw import <world>` without an environment: the world was not loaded again at the next start.
* `/hw create` with an unknown type: an error, then "world created", and an entry for a world that doesn't exist in `World-List.yml`.
* `/hw unload` unloaded the main world and the default world, and said "unloaded" when the server refused (players in the world).
* World names: `/`, `..` and the dots were accepted (`/hw create ../name` created the world outside the folder of the worlds). Now letters, numbers, `_` and `-`, in the commands and in the menu.
* Creating a world from the menu: the generator typed in the chat could not be cancelled (the next message of the player was taken as a generator). A left click now cancels it, like for the name.
* MySQL with several servers: a table created by one server (the first time a player used an option) was not seen by the others until their restart. They tried to create it again and to add rows that already existed: the options changed on one server could be ignored on the other.

## 1.4.3 BETA

### New

* Placeholders for the number of players: `%online%`, `%max_players%`, `%online_<world>%`, `%player_world_online%` and `%staff_online%` (players with `hawn.staff`). The vanished players are not counted. The default scoreboard and tab list show `%online%/%max_players%` (new installations). See [Placeholders](../reference/placeholders.md#server).
* `Anti-WDL.Enable` in `ServerList.yml`: the anti world downloader can be turned off. See [Anti world downloader](../features/server-list.md#anti-world-downloader).

### Fixes

* `hawn.staff.seevanished` only received the vanish messages: the staff didn't see the vanished players, as the wiki said. Now they do, after a `/vanish`, when they join, with the player hider item and `/option`.
* Every join: the staff received "<player> is now vanished" for the player who joined, once per vanished player and once per player hiding the others, and the player who joined could get the "you are vanished" action bar.
* `General.On-Quit.Quit-Message.Just-Simply-Disable-All-Quit-Messages` did nothing.
* `Cancel-Tp-On.On-Damages` (`/spawn` and `/warp`) was never read: a damage cancelled the teleport according to `Any-movements`.
* `/cc other <player>`: `ClearChat.Other.Message-Clear` was never read, the message for the sender was sent to the player (with their own name), and the reason started with the name of the player.
* Join and quit messages per world with `Only-Broadcast-Messages-In-The-World: true`: they were sent to everyone, several times. Now they are sent once, only to the players of the world.
* `Items.Clear-Drops-On-Death.Bypass` was ignored with `All_World: true`.
* `Chat-Emoji-Player.Emojis-list.Option.Gui.Enable: false` didn't stop `/emoji` from opening its menu.
* Messages never shown: `/setspawn` on a name already taken, `/delspawn` without a name, `/sun` from the console, `/skull <player>` for the player whose skull was taken (new `Skull.Target`, off by default), the "command disabled" message of `/p`.
* Join items with `Material: PLAYER_HEAD` and `Skull-Name` (the example of the wiki) gave a Steve head.
* With `Use_Permission_Per_Item: true`, every click in an inventory sent a debug message (`hawn.use.cji.item.null`).

## 1.4.2 BETA

### New

* Update check: when a new version is out, the players with `hawn.admin` are told when they join, with a link to the Spigot page. Hawn also looks again every 6 hours, not only at startup. `Plugin.Update.Notify-Admins` in `general.yml` turns the message off. See [Update check](../features/admin-tools.md#update-check).
* Startup and `/hawn reload` warn about the spawns whose world doesn't exist anymore.
* LuckPerms is declared as an optional dependency, and shown by `/hawn hooks` and at startup. See [LuckPerms](../integrations/hooks.md#luckperms).

### Fixes

* `/spawn tp <player>` teleported the one who typed the command instead of the player, with the default spawn (and with a spawn group, the spawn was chosen for the wrong player). Now the player is teleported, with or without the delay.
* After `/spawn tp <player>` or `/warp <warp> <player>` with a delay, the player who was teleported got "Tp cancelled" at the first step. The same after a teleport that failed (spawn missing, no permission).
* `/spawn tp <player>` announced the delay of `/spawn` (`Delay.Self`) instead of its own (`Delay.Other`), and the message to the sender showed its own name instead of the player teleported.
* Void TP to a spawn whose world was deleted or unloaded: at every step under the limit, the void TP message, sound and commands were run and the console was warned, without any teleport. Now the player is told once every 6 seconds, the console once.
* MySQL enabled but unreachable: a join could try to reconnect on the main thread and freeze the server. A lost connection is now tried again at most once a minute from the main thread, with a 5 seconds connection timeout.
* `/spawn` with a delay and a custom spawn that doesn't exist said "no permission" instead of "the spawn doesn't exist".

## 1.4.1 BETA

### Fixes

* `/gmute <minutes>` stopped the server at the end of the minutes, instead of opening the chat again. The chat is now opened again, even if the one who muted it has left, and `/gmute abc` asks for a number instead of an error.
* Vanish (`/v`): a vanished player was shown again to a player who joined (by the player hider item, on by default), who used the player hider item, `/option` or who changed world, and to every player who joined with `Keep.Vanish-On-Join.Enable: true`. The vanished players stay hidden now.
* Admin panel (`/ap`): its buttons (stop, reload, save the server...) only work in the menu opened by Hawn, and only for the players who can still open it (`hawn.adminpanel` and `List-Of-People-Can-Use-The-Panel`); otherwise the menu closes. Before, a menu of another plugin with the same title was enough.
* `/hawn editplayer <player>` and the player list of the admin panel opened the menu for the chosen player instead of the one who asked for it.

## 1.4 BETA

### New

* Chat format (`Chat-Format` in `Events/Chat.yml`): prefix, name and message, with the placeholders (`%luckperms_prefix%`...). `Enable: AUTO` by default: used when no known chat plugin is installed (EssentialsChat, ChatControl, LPC...), and a chat plugin that changes the format after Hawn wins (the console says it once); `true` forces it, `false` turns it off. See [Chat format](../features/chat.md#chat-format).
* Anti-spam (`Anti-Spam`): the same message can't be sent again within 30 seconds, and a message with too many capital letters is put in lower case (or blocked). Bypass: `hawn.bypass.antispam`. See [Anti-spam](../features/chat.md#anti-spam).
* Chat per group of worlds (`Per-World-Chat`, off by default): the players only see the messages of their group of worlds, and each group can change any chat option for itself (format, mentions, emojis, anti-swear, colours, anti-spam), plus its own mute and delay. See [Chat per group of worlds](../features/chat.md#chat-per-group-of-worlds).
* A comment above each option of the configuration files, on 1.18.1+ servers: it says what the option does. They are added to your files at the first start of 1.4, your values and your own comments are kept. Nothing changes on 1.16 and 1.17. See [Files and folders](../getting-started/files.md).
* New actions: `[delay[ticks]]:` and `[chance[percent]]:` in front of any line, `[broadcast]: <message>`, `[sound]: <sound> [volume] [pitch]` and `[title[in,stay,out]]: <title> //n <subtitle>`. See [Actions](../basics/actions.md).
* The volume and the pitch of every sound option accept decimals (`1.5`); they were read as whole numbers.
* New protections in `Events/ProtectionWorld.yml`, with the same options as the others (worlds, WorldGuard regions, bypass): `Armor-Stand` (take or put an item on an armor stand, hit it), `Hanging-Place` (place an item frame or a painting), `Explosion-Blocks` (the blocks broken by an explosion), `Liquid-Flow` (water and lava that flow, off by default) and `Anti-Trample` (farmland trampled by players and mobs). See [Protections](../features/protections.md#more-protections).
* World manager: the default world. Shift + click the nether star in the menu of a world: it replaces the old default world in the world lists of every file (join messages, protections, scoreboards, tab lists, void TP...), the options set for the old world only are moved to it, and the files are read again. The default world is saved in `general.yml` (`Plugin.Default-World`), shown at startup, and can't be deleted. `/hw default [world]` does the same, also from the console. Permission `hawn.command.world.setdefault`. See [The default world](../features/world-manager.md#the-default-world).
* Welcome setup: closing a menu with Escape says that the setup isn't finished, with a sound.
* World manager: the creation of a world fits in one page (the generator is chosen on it), every click gives an answer (sound, message), and the menu of each world shows its information (players, type, generator, chunks, entities, spawn, time, PvP, seed). A world that can't be created says so, instead of "created". See [World manager](../features/world-manager.md).
* MiniMessage on Paper 1.19.1+: the messages, titles and action bars can use gradients, hover texts, clicks... (`<gradient:red:blue>`, `<hover:show_text:'...'>`, `<click:run_command:/spawn>`), mixed with the `&` codes and hex colours. Nothing changes on Spigot. A text typed by a player is never read as tags. See [MiniMessage](../basics/message-format.md#minimessage-paper).
* Tab lists per world and per permission, like the scoreboards: every file of the `Tablist` folder is a tab list, with its worlds, a priority, `permission: true` to be seen only with `hawn.tablist.<file name>`, its own header, footer and animations; a missing header or footer is the one of `Tablist.yml`, which stays the default tab list for the players who match none of them. Two examples (`staff.yml`, `nether.yml`) are created once with `enable: false`, so nothing changes until you turn one on. See [Tab list](../features/tablist.md#per-world-and-per-permission).

### Performance

* The hooks (PlaceholderAPI, MVdWPlaceholderAPI, WorldGuard) are checked at startup and on `/hawn reload`, instead of reading `general.yml` for every message, item or scoreboard line.

### Changes

* Startup: the console says on what Hawn runs (Paper or Bukkit events, MiniMessage), the hooks used, the storage and the language, what is loaded (scoreboards, tab lists, spawns, warps, join items, custom commands, emojis), what happened to the files, the time it took, and what is on (maintenance, emergency mode, chat per world, chat format left to a chat plugin). It also warns about the options that contradict each other: a spawn on join, a spawn group or a void TP without spawn, a void TP spawn below the void TP height, always day and always night in the same world, a world in two chat groups, an anti-swear that does nothing, tab list files while the tab list is off, scoreboards on without any file, a hook kept on without its plugin...
* Void TP: every player falling in the void is teleported to the spawn; `hawn.command.spawn.<spawn>` is not needed anymore. `VoidTP.Options.Spawn-Permission.Enable: true` (`Events/VoidTP.yml`) brings the old behaviour back. See [Void TP](../features/void-tp.md).
* On Paper 1.19.1+, the chat goes through the Paper chat event (`AsyncChatEvent`), and on Paper 1.21.7+ the join checks (maintenance, emergency mode, full server) through the new connection event: Paper no longer warns about Hawn listening to the `PlayerLoginEvent`. Spigot keeps the Bukkit events.
* On Paper 1.21.7+, `hawn.maintenance.bypass` and `hawn.join.full` are read from LuckPerms at join, since the player doesn't exist yet; with another permission plugin, only the operators have them (the whitelists still work). The kick messages of the maintenance and of the emergency mode only replace `%player%` and the server placeholders.
* The BattleLevels hook is removed (the plugin is abandoned): the `%h_battlelevels_...%` placeholders are not replaced anymore, and its options are removed from `general.yml` at the first start.

### Fixes

* The void generator uses the generator API of 1.17.1+ on these versions.
* French messages (`Messages/fr_FR/`): 184 texts corrected (spelling, agreement, "tu" and "vous" mixed, `/hawn maintenance` said "activé" when turning it off...). The messages you never changed are updated in your files at the first start; the ones you changed are kept.
* Mentions: `@Bot` doesn't mention `BotAdmin` anymore, only the whole name counts.
* On Paper, the chat message of another plugin (with its clicks or hover texts) is kept when Hawn has nothing to change in it.
* An item frame or a painting broken by an arrow, a mob or an explosion threw an error and was not protected.
* Spawns and warps: the angle of the camera (yaw and pitch) was rounded to a whole degree when teleporting.
* Mentions: the highlighted message is only sent again to the mentioned player. Before, Hawn cancelled the message and sent it itself to everybody, which skipped the format and the logging of the other chat plugins.
* When the server stops, Hawn only removes its own sidebar. Before, it gave a new empty scoreboard to every player, which removed the scoreboard and the teams of the other plugins.
* Welcome setup: the world chosen replaced every list of worlds by itself alone (the nether and the end were removed from them). It now takes the place of `world` and the other worlds are kept.
* `/hw create`, `/hw import` and the creation menu looked for the existing worlds in the server folder only: on Paper 26.x (or with `--world-container`), a world that already existed was "created" again. The creation menu didn't check at all.
* World names: the check against parentheses never worked (`te(st)` was accepted).
* `/hawn reload` shows the same warnings as the startup (options that contradict each other), instead of the old fly and double jump message.
* Paper 26.x: the worlds created with the world manager (kept in `world/dimensions/minecraft/`) were not loaded again after a restart.
* World manager: a world deleted from the menu was still listed when the menu opened again (its folder was not deleted yet). It is hidden at once, and the open menus are refreshed when the folder is gone.

## 1.3 BETA

### Security

* Text typed by a player (`/warning`, `/broadcast`, a `/cc` reason, a warp or world name, a chat message warned to the staff...) can't run [actions](../basics/actions.md) anymore. With the default `Warning` message, `/warning [command-console]: op <player>` ran the command in the console.
* A muted player (mute chat, or a mute of another plugin such as LiteBans) could talk by writing `@` in their message. The mentions also ignored the chat channels.
* The command blocker compares the command only: `/pl `, `/PL x` or `/bukkit:pl` don't get through anymore, and the blocked commands are hidden from the tab completion. The staff warning was sent to the player who typed the command instead of the staff. A blocked command starting with `/help` (like `/helpop`) also got through the `/help` interception.
* Emergency mode (`/hawn urgent`): it now takes the operator status away from everyone and locks the commands and the chat of the players until the console turns it off (the operators are then given back). Before, a hacked operator listed in the whitelist kept all their rights.

### Performance

* The scoreboard files are read once (and again at `/hawn reload`) instead of several times per player at every refresh.
* The player files are kept in memory while the player is online and written outside the main thread (when they leave, every 5 minutes and at the server stop), instead of being read and rewritten at each change.
* MySQL no longer blocks the server: the data of a player is loaded before they join, read from memory while they play, and written in the background. The join and the quit don't wait for the database anymore.
* MySQL: the connection is kept alive and reopened when lost (before, nothing was saved anymore after the `wait_timeout` of MySQL, 8 h by default). Queries use prepared statements and close their results.
* MySQL: every table gets a primary key on `player_UUID` (one row per player). At the first start of 1.3, the duplicates of the old tables are removed and each old table is kept as `<table>_before_1_3`: you can delete these copies once everything works.

### New

* The missing options are added to your files at startup and on `/hawn reload` (the new options of an update, or an option deleted by mistake), with their default value; the console lists them. Your values are never changed, the lists you fill yourself (custom commands, auto broadcast messages, join items, emojis...) are never refilled, and a file with a YAML error is left alone. Each file starts with a link to its page of the wiki. See [Files and folders](../getting-started/files.md).
* `Event.OnJoin.Spawn-Permission` (`Events/OnJoin.yml`): choose if `hawn.command.spawn.<spawn>` is needed to be teleported to the spawn on join, and if the players without it get a message. See [Spawns](../features/spawns.md#teleport-on-join).
* `Plugin.Commands.Hide-Without-Permission` (`general.yml`, on by default): the commands a player can't use are hidden from their list of commands and their tab completion. See [Commands management](../basics/commands-management.md#hide-the-commands-a-player-cant-use).
* `Plugin.Players.Save-IP` (`general.yml`): choose if the IP of the players is saved. See [Player data and MySQL](../features/database.md#ip-of-the-players).
* `/hawn setup` opens the welcome setup again (it was listed in the help but did not exist).
* `hawn.maintenance.bypass` lets a player join during the maintenance without being in its whitelist (operators by default).
* Spawn groups (`Spawn.Spawn-Group` in `Events/OnJoin.yml`): the players are spread between several spawns, to the least used one, in turn or at random. See [Spawns](../features/spawns.md#spreading-the-players-between-several-spawns).
* Emergency mode: who started it and when is written in `plugins/Hawn/urgent-mode.log`, and can be sent to a Discord webhook (`Discord-Webhook`).
* `/hawn reload` applies the changes of `AutoBroadcast.yml` (before, a restart was needed to turn it off or change it).

### Changes

* The teleport to the spawn on join doesn't need `hawn.command.spawn.<spawn>` anymore: every player is teleported (before, the players without it stayed where they were and got a "no permission" message at each join). `Spawn-Permission: true` brings the old behaviour back. `/spawn` and the void TP still need the permission.
* The IP of the players is not saved anymore by default (GDPR), and the one saved by an older version is erased at the next join of each player. `Save-IP: true` brings the old behaviour back. `/ip`, `/checkaccount` and `%player_ip%` are not affected.
* The commands that need a permission are hidden from the players without it (`Hide-Without-Permission`). A player who types one anyway gets the "Unknown or incomplete command" of the server instead of the Hawn "no permission" message; `Hide-Without-Permission: false` brings the old behaviour back.
* Tab completion: `/spawn`, `/warp` and `/scoreboard set` only suggest what the player can use, and `/hawn`, `/hw` suggest nothing to the players without their permission.
* `/hawn hooks` now uses its own permission, `hawn.admin.command.hooks` (it was `hawn.editplayer`).
* `/hw delete <world>` asks to be typed again with `confirm`. The main world, its nether and its end can't be deleted anymore, and the folder is only deleted when the world could be unloaded (outside the main thread).
* The TPS warnings are sent at most once per minute (they were sent every 3 seconds), and so is the emergency save under 5 TPS.
* `/spawnlist` shows the spawns the player can go to, with the same permission as `/spawn`: `hawn.command.spawn.<spawn>` (it was `hawn.spawn.<spawn>`).
* `Keep.JumpBoost-OnJoin` (`Player-Option-General.yml`) now works and is `true` by default. With `false`, the jump boost of `/option jumpboost` is removed when the player joins.
* The doors, fence gates and trapdoors of the crimson, warped, mangrove, cherry, bamboo and pale oak woods, and the copper doors and trapdoors, are protected by `PlayerInteract-Items-Blocks`.
* The sign system works with every sign (all the woods and the hanging signs).
* The join items (and the player visibility item, the FunGun and the lobby bow) are recognised by a hidden tag instead of their name: a copy renamed on an anvil does nothing. An armour piece of the join items only runs its commands when it is the one Hawn gave.
* The whitelists of the maintenance and of the emergency mode ignore the case of the names, and accept UUIDs. So does `Can-Use-Urgent-Mode`.
* Emergency mode: the backup is made outside the main thread (the server froze until the end), holds the worlds and the `plugins` folder without the jars and the previous backups (each backup contained the previous ones), and only the `Keep` newest are kept. Disabling the other plugins is off in new configurations (it could make them lose data); a configuration made before 1.3 keeps its value. When the mode is turned off, only the plugins it disabled are enabled again.
* Auto broadcast: the titles and the action bar are off in new configurations.
* After the update, the join items given by Hawn 1.2 do nothing until they are given again, at the next join.
* WorldGuard: the region names of the configs ignore the case (WorldGuard saves them in lower case, so `Spawn` never matched).

### Fixes

* Respawn after a death (`Death.Respawn.Player.Teleport-Spawn`): without `Custom-Spawn`, the default spawn was read in `spawn.yml` instead of `Events/OnJoin.yml`, so the player got "the spawn doesn't exist" instead of being teleported.
* Tab completion: the suggestions are filtered on what is typed (`/gm cr` → `creative`). `/delspawn`, `/delwarp`, `/editwarp`, `/warp`, `/spawn`, `/hawn spawnmanager remove` and `/help <category>` threw an error when there was no spawn, no warp or no such category. `/speed` suggested the warps, and the commands that take a text (`/broadcast`, `/warning`...) suggested the player names.
* Auto broadcast: the world filter was inverted (`All_World: true` sent nothing, `false` sent everywhere).
* Fireworks launched one rocket less than `Amount` (`Amount: 1` launched none).
* The critical TPS warning (5 TPS or less) never triggered, and the emergency save ran outside the main thread.
* Join: the world filter (`All_World`) of the chat clear and of the join sound was read in the wrong file and ignored.
* The admin panel alert of the emergency mode (`hawn.urgent.spy.adminpanel`) never showed up.
* The pitch of the mention sound was always 0.
* The `Data-value` of the `/emoji` menu items was read in the wrong file.
* `Scoreboard.Enable` (`Scoreboard-General.yml`) and `Keep.PlayerVisibility-OnJoin` (`Player-Option-General.yml`) were never read.
* `JumpPads.Use_Permission` was missing from the generated `Cosmetics-Fun/JumpPads.yml`.
* Double jump: a player with `hawn.fun.doublejump.double` could fly in the worlds where the double jump is off. The flight given for the double jump is now taken back there (a flight given by `/fly` or another plugin is kept).
* Chat: the actions of the mute, chat delay, anti-swear and mention messages ran outside the main thread.
* Scoreboards: after a `/hawn reload` that made a title or an animated line shorter, the scoreboard threw errors until the animation started again.
* MySQL: the first join date of a player who already had a YAML file was never saved (the query had one value too many).
* MySQL: when the connection failed at startup, a NullPointerException was printed in the console.
* World manager: the error message of the delete menu was never shown (wrong key).
* World manager: choosing the void generator in the creation menu only changed its icon, and the choice was kept by the "back" item without saying so. A click now goes back to the creation page with the void generator chosen.
* Void TP: when its spawn did not exist, "the spawn doesn't exist" was sent at each movement, even far above the void. The error is now shown only under the limit, once every 6 seconds.
* Void TP: with `VoidTP: false` for a world, the messages, sounds and commands of the void TP were still run at each movement under the limit.
* The delay of `/spawn` and `/warp` was cancelled when the player only turned their head.
* The lists kept per player (vanish, cooldowns, build mode, no-clip, double jump, teleports waiting for their delay...) were never emptied when the player left. A `/spawn` or `/warp` waiting for its delay is now cancelled.
* MySQL: the first read of the walk and fly speeds inserted an empty value in a number column (an error on servers in strict mode).
* `/hawn reload`: when PlaceholderAPI or MVdWPlaceholderAPI was removed, Hawn kept calling it (the option was written under a wrong key).
* Protections: a click in the air made errors in the console (the interactions protection, and the armor stand protection with WorldGuard).
* Without MySQL, `Keep.Gamemode-On-Join` always gave back the survival mode (the saved gamemode was read under a wrong key).
* `/helpop`, `/helpme` and the other commands starting with `/help` were taken by the `/help` interception, and every `/` of the arguments was removed.
* Join: a `Restore.Health.Value` above the max health of the player made an error (it is now capped).
* `/hawn spawnmanager` and `/hawn editplayer` without argument made an error.
* World manager: a click on an empty slot or in the inventory of the player does nothing anymore (a click refused for lack of permission let the item be taken).
* World manager: the world folders were looked for in the server folder, not in the folder of the worlds (`--world-container`).
* Maintenance and emergency mode: the players kicked by the command saw the lines of `Kick-Message` as `[line 1, line 2]`.
* `%bungee_<server>%`: only the first server of a line was replaced.
* `%timedelaypvcji%` and `%timedelayfunguncji%` printed an error in the console when the player had no cooldown.
* `/broadcast` and `/warning` were shown twice in the console.
* Join items without `Title` (like the player head of the default config) did nothing when clicked.
* Emergency mode: with `Use-It-Only-On-The-Console`, the error was sent to every player instead of the one who typed the command.
* Lobby bow: any arrow shot by a player teleported them, not only the arrows of the lobby bow; the item put aside for the arrow was lost when the player left while aiming.
* Paper 26.x keeps the nether, the end and the created worlds inside `world/dimensions/`: the world manager menu did not show them, and the emergency mode backup missed `level.dat` and the player data. The menu now lists every loaded world, the backup takes the whole world folder, and deleting a world only removes its own folder, never the main world.
* A few French messages had broken accents (`Ãªtre`).
* `TP.Last-Position-On-Join`: when the last world of the player was deleted or renamed, the join made an error (the player now goes to the spawn). The yaw and pitch lost their decimals.

## 1.2

### Compatibility

* Minecraft **1.16.5 up to 1.21.x and 26.x**, on Spigot and Paper, with a single jar.
* No more NMS/reflection hacks: titles, action bars, tab list, ping and scoreboard use the API (scoreboard through the up-to-date [FastBoard](https://github.com/MrMicky-FR/FastBoard)).
* Up-to-date [XSeries](https://github.com/CryptoMorin/XSeries) for materials, sounds, potions and particles.
* Official bStats library, **WorldGuard 7+ only**.
* Commons Lang/IO are not required anymore (recent servers do not ship them).

### New

* Hex colours `&#RRGGBB` (and the existing `#<RRGGBB>`) in every message, and in the chat with the `hawn.use.chatcolor.chat.hex` permission.
* Every Hawn menu is locked: items can't be taken, put or dragged anymore.
* The update check runs asynchronously and only reports a *newer* version.

### Welcome setup

* Opens for a player made operator while online (not only when joining).
* Does not reopen once finished, nor on a player who just left.
* `/setspawn <name>` really sets the default spawn at the last step, and only for admins during the setup.
* The "Continue" button of the last step ends the setup.

### Fixes

* The version detection considered every server above 1.16 as a 1.8 server.
* Items could be taken from the `/emoji` menu.
* Player heads of online players reuse their profile instead of asking Mojang for the skin each time a menu is opened (it caused "Couldn't look up profile properties" timeouts).
* Every player shared the same boss bar.
* World folders were built with Windows paths (`\`), the world manager failed on Linux.
* Emojis/anti-swear words containing regex characters (like `:)`) made the chat crash, and the anti-swear replacement is now case-insensitive like the detection.
* Placeholders whose value contains `$` or `\` (prefix, display name...) made messages crash.
* `%checkupdatehawn%` made a web request on the main thread each time it was displayed.
* The mount protection used the drop bypass option on "All_World".
* Per-player boss bars and titles are cleaned when a player leaves.

## 1.1.6 BETA

* New welcome setup for the admins: language, default world and spawn.
* New void world generator, available when creating a world.
* World Manager (`/hw` and its menu) reworked: creation and import with a world type and a generator.
* Tab completion for `/hawn` and the player names of the commands.
* Admin messages reworked (English and French).
