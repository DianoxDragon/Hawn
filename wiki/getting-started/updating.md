---
description: What to check when you update Hawn, from 1.1.x to 1.2, from 1.2 to 1.3, from 1.3 to 1.4, within 1.4 (1.4.1 to 1.4.9), from 1.4 to 1.5, within 1.5 (1.5.1 to 1.5.7), and from 1.5 to 1.6.
---

# Updating from an older version

## How to update

1. Stop the server.
2. **Make a copy of `plugins/Hawn`** (just in case).
3. Replace the old Hawn jar by the new one in `plugins`.
4. Start the server.

Your configuration files are kept: Hawn creates the files that don't exist yet and, since 1.3, adds the options missing from your files (the console lists them). Your values are never changed.

## From 1.5 to 1.6

1.6 adds [Folia](installation.md), the language of each player, Italian and Portuguese, and the [holograms](../features/holograms.md). Your files are kept: the update adds about 90 options (the console lists them) and changes none of your values. Check these points:

* **The language of each player** is on (`Plugin.Language-Per-Player: true` in `general.yml`): a player whose game is in English, French, German, Dutch, Spanish, Italian or Portuguese of Brazil gets the messages of this language, no longer those of the server. **If you changed the messages of your language only**, these players see the messages of their language as Hawn wrote them. Copy your changes into the other folders of `Messages/`, or set `Plugin.Language-Per-Player: false` to give everyone the language of the server, as before. The `Enable` of the messages is still read in the file of the server. Before 1.20.2, the first messages of the join use the language of the server (the game sends its language just after). See [Translating Hawn](../help/translating.md).
* **New files**: `Messages/it_IT/` and `Messages/pt_BR/`, `Translations/` (the texts of the `{lang_<key>}`), `Menus/language.yml` (the language menu, opened by `/language`), and `Holograms/example.yml` (turned off). Nothing else changes in your folders.
* **Your menus, scoreboards, tab list and announcements keep their texts**: only the new installations get the `{lang_<key>}` keys. Your `Menus/profile.yml` has no book for the language menu: add an item with `[menu]: language`, or let the players type `/language`.
* **The holograms** need the plugin [PacketEvents](https://modrinth.com/plugin/packetevents). Without it, the rest of Hawn works as before.
* **Folia**: Hawn 1.5 refused to start on Folia, 1.6 runs on it. The world manager is off there (`/hw` says so) and the worlds of `World-List.yml` are not loaded by Hawn.
* **MySQL**: the table `player_language` is created at the first start; it keeps the language chosen in the menu for the whole network.
* **The messages to an admin** of `/gmc`, `/gms`, `/gma`, `/gmsp` and `/heal` on another player named the admin instead of the player: they name the player now, with the same files.

After the update, type `/hawn debug`: it lists the mistakes of your files.

## Within 1.5 (1.5.1 to 1.5.7)

Each 1.5.x keeps your files and adds the new options. Most changes are fixes or new features that stay off until you use them. These ones change what an existing server does: check them once after the update.

* **`/hw delete`** (1.5.7) also deletes a world that is not loaded (its folder), as the world manager does. The main world and its dimensions are still protected.
* **MySQL** (1.5.7): the options of a player saved in MySQL (fly, speed, player hider...) are copied into the file of the player when they join. With several servers on one database, an option changed on one server is now seen by the others.
* **`/speed <n>` and `/flyspeed <n>`** (1.5.7) while the option is off say to turn it on first (`PlayerOption.Error.Turn-On-First`), instead of "You disabled this option".
* **Teleports on Paper** (1.5.6): `/spawn`, `/warp`, the void TP, `/gotop`, the world manager and Edit Player load the chunk of the destination first, then teleport. The player arrives a few ticks later instead of freezing the server.
* **Join numbers** (1.5.6): a player refused at the last moment (ban, whitelist, full server) no longer takes a number of `%player_join_number%`.
* **Names above the heads** (1.5.6): two ranks whose names begin the same way no longer share a team. Check the prefixes above the heads if you had worked around it.
* **bStats** (1.5.5) also counts the modules that are on (anonymous). See the [FAQ](../help/faq.md).
* **Short commands** (1.5.3): `/editplayer` (`/ep`), `/hrank`, `/hmenu` and `/hdebug` are on for the new installations only. Turn them on in `command-aliases.yml` if you want them. See [Commands management](../basics/commands-management.md#aliases).
* **`[back]` in the menus** (1.5.2) goes back step by step (A, B, C, `[back]` opens B, then A) instead of going back and forth between the last two menus.
* **The conditions of the menus** (1.5.2) are cut where they are written, then the placeholders are replaced on each side: a value with an operator in it (a prefix `&8>> `) no longer breaks a condition. A condition that only worked because of the old cut may behave differently: test your menus.
* **The welcome setup** (1.5.1) no longer restarts the server at its end, and `/hawn setup` asks first.
* **New files only** (1.5.1): the files written by Hawn name the worlds of your server in their `Worlds` lists instead of `world`. Your files keep their lists.

See the [changelog](../help/changelog.md) for everything else.

## From 1.4 to 1.5

1.5 adds the [menus](../features/menus.md). Your files are kept:

* **The menus**: the `Menus/` folder is created with `servers.yml` (the server selector) and `profile.yml` (a profile). Nothing opens them on your server until you use them: `/servers`, `/profile`, or `[menu]: servers` in an item.
* **The compass of the join items** keeps its actions. On the new installations it opens the selector: to do the same, write `[menu]: servers` in its `Command-List` (it ran `/Serverlist`, a command that doesn't exist in Hawn).
* **The join items per world**: `Custom-Join-Item.General-Option.Change-World.Enable` is added with `false`: the join items don't change when the players change world, as before. Turn it on with `Worlds:` per item to give and remove them by world.
* **The console** shows the placeholders of the player in its copy of the join and quit messages (`(player #%player_join_number%)` stayed as it was).

## Within 1.4 (1.4.1 to 1.4.9)

Each 1.4.x keeps your files and adds the new options. Most changes are fixes or new features that are off or invisible until you use them. These ones change what an existing server does: check them once after the update.

* **Gamemode on world change** (1.4.9): `GM` of `Events/PlayerWorldChange.yml` changed the gamemode in the worlds that were **not** listed, and did nothing with `All_World: true`. Now only the listed worlds change it. If your players got a gamemode in another world because of this, list that world in `GM.World.Worlds`. The value of your file is kept (creative before 1.4.9, adventure for the new installations). See [World change events](../features/world-change.md).
* **MVdWPlaceholderAPI** (1.4.9) is no longer supported: its placeholders are not replaced anymore, and its section is removed from `general.yml`. Use PlaceholderAPI.
* **Player hider** (1.4.9): your `Special-HidePlayers.yml` keeps two states (`PV.Option.Three-States.Enable: false`). Turn it on for everyone, then the staff and the VIP, then nobody.
* **Ranks** (1.4.7, 1.4.8): `Ranks.yml` is created with three example ranks (owner, admin, vip) turned on. They are only given by the permission `hawn.rank.<rank>` (never to the operators by default) or by `Ranks.Players`, so nothing changes until you give one. But a permission plugin that gives **every permission** to a group (`*` in LuckPerms) gives `hawn.rank.owner` to it: these players show `[Owner]`. Remove the examples you don't want, or set `Ranks.Enable: false`. With TAB or NametagEdit installed, `Enable: AUTO` leaves the tab list and the heads to them. See [Ranks](../features/ranks.md).
* **Names above the heads** (1.4.8): a player with a rank gets a team of Hawn (sent in packets): a plugin that coloured or hid the names with its own teams loses that for these players. Turn `Ranks.Above-Head.Enable` and `Ranks.Name-Colour.Enable` off if needed. Before 1.21.2, the order of the tab list (`Ranks.Sorting.Enable`) uses these teams for every player.
* **Emergency save of the TPS warning** (1.4.7): under 5 TPS, the worlds and the players are no longer saved by default. `Plugin.Tps.Emergency-Save.Enable: true` in `general.yml` brings it back.
* **`/hw unload`** (1.4.7): the players in the world are sent to the default world, instead of the command being refused.
* **Auto broadcast** (1.4.6): `world_list` now compares the whole name of the world: `world` no longer matches `world_nether`. Write `world*` for the old behaviour.
* **MySQL with several servers** (1.4.4): the first join and the join numbers are counted once for the whole network. `Plugin.Use.MYSQL.First-Join-On-The-Network: false` counts them server by server again. See [Several servers on one database](../features/database.md).
* **Placeholders of the number of players** (1.4.3): `%online%` and the others don't count the vanished players.
* **`hawn.staff.seevanished`** (1.4.3): the staff with it now really see the vanished players.
* **Join and quit messages per world** (1.4.3): with `Only-Broadcast-Messages-In-The-World: true`, they are sent once, only to the players of the world (they were sent to everyone).
* **New installations only** (1.4.8): the example messages (auto broadcast, MOTD, tab list, scoreboard, custom commands) were changed into a demo of a lobby. Your files keep their messages.

After the update, type `/hawn debug` (1.4.8): it lists the mistakes of your files, and the startup console lists the options that contradict each other.

See the [changelog](../help/changelog.md) for everything else.

## From 1.3 to 1.4

Your files are kept and completed with the new options. Check these changes:

* **Tab lists**: the custom tab lists of `Tablist/Tablist.yml` (`Custom-Tablists`) are moved to one file each in the `Tablist` folder at the first start, like the scoreboards. Two examples are created turned off (`staff.yml`, `nether.yml`). See [Tab list](../features/tablist.md).
* **Chat format**: `Chat-Format` (`Events/Chat.yml`) is `AUTO`: Hawn formats the chat only when no known chat plugin is installed (EssentialsChat, LPC...). `false` never formats it. See [Chat](../features/chat.md#chat-format).
* **Void TP**: every player falling in the void is teleported, `hawn.command.spawn.<spawn>` is not needed anymore. `VoidTP.Options.Spawn-Permission.Enable: true` (`Events/VoidTP.yml`) brings the old behaviour back.
* **Paper 1.21.7+**: `hawn.maintenance.bypass` and `hawn.join.full` are read from LuckPerms when a player joins. With another permission plugin, only the operators have them (the whitelists still work).
* **Default world**: Hawn now knows the world written in the world lists of the files (`general.yml` → `Plugin.Default-World`). At the first start it takes the world the most written in your lists: check it in the startup console (`Default world:`). See [The default world](../features/world-manager.md#the-default-world).
* **Configuration files**: on 1.18.1+, a comment is written above each option at the first start (your values and your own comments are kept). The French messages you never changed are corrected.
* **BattleLevels**: the hook is removed, its placeholders are not replaced anymore.

The startup console now lists the options that contradict each other: read it once after the update.

See the [changelog](../help/changelog.md) for everything else.

## From 1.2 to 1.3

Your files are kept and completed with the new options. Check these changes of behaviour:

* **Teleport on join**: `hawn.command.spawn.<spawn>` is not needed anymore to be teleported to the spawn when joining. To keep the old behaviour, set `Event.OnJoin.Spawn-Permission.Enable: true` in `Events/OnJoin.yml`. See [Spawns](../features/spawns.md#teleport-on-join).
* **IP of the players**: not saved anymore, and the saved IP of each player is erased at their next join. To keep saving it, set `Plugin.Players.Save-IP: true` in `general.yml`. See [Player data and MySQL](../features/database.md#ip-of-the-players).
* **Commands**: the commands a player can't use are hidden from them (they get "Unknown command" instead of the Hawn "no permission" message). To show them again, set `Plugin.Commands.Hide-Without-Permission: false` in `general.yml` and restart.
* **Join items**: they are recognised by a hidden tag. The items given by 1.2 do nothing until the player joins again and gets new ones.
* **Emergency mode**: it now removes the operator status and locks the commands and the chat. Read [Admin tools](../features/admin-tools.md) before using it.
* **MySQL**: at the first start, the old tables are copied as `<table>_before_1_3` and cleaned of their duplicates. Delete the copies once everything works.

See the [changelog](../help/changelog.md) for everything else.

## From 1.1.x to 1.2

### Compatibility

* Hawn 1.2 supports **1.16.5 up to 1.21.x and 26.x**, Spigot and Paper, with a single jar.
* **1.8 → 1.16.4 are not supported anymore.** Stay on 1.1.x for these versions.
* **WorldGuard 7+** is required for the region features. WorldGuard 6 is not supported anymore.
* Commons Lang and Commons IO are not needed anymore (recent servers don't ship them).

### Your files

The configuration files of 1.1.6 and 1.2 have the same format, you can keep your `plugins/Hawn` folder as it is.

A few things you may want to change after the update:

* **Material and sound names**: old names (`SKULL_ITEM`, `GOLD_PLATE`, `NOTE_PIANO`, `BLOCK_NOTE_HARP`...) still work, Hawn translates them for your server version. You don't have to rename them, but new names (`PLAYER_HEAD`, `LIGHT_WEIGHTED_PRESSURE_PLATE`, `BLOCK_NOTE_BLOCK_HARP`...) are easier to read.
* **Data values** (`Data-value`) are ignored on 1.13+ servers: use the full material name instead (`RED_WOOL` rather than `WOOL` + `Data-value: 14`).
* **Void TP height**: since 1.18 the overworld goes down to Y = -64. With `TP-y: 0`, players in deep caves are teleported to the spawn. Use a lower value (for example `-70`) in `Events/VoidTP.yml`. See [Void TP](../features/void-tp.md).
* **Hex colours**: you can now use `&#RRGGBB` everywhere, in addition to `#<RRGGBB>`. See [Messages, colours and formatting](../basics/message-format.md#hex-colours).

### The welcome setup

If your installation has no `plugins/Hawn/StockageInfo/Setup.lock` file, the [welcome setup](welcome-setup.md) opens when an admin joins. If your server is already configured, click the barrier (**I don't need a setup**): it will never be shown again.

### What's new

See the [changelog](../help/changelog.md) for the full list of changes and fixes.
