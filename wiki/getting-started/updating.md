---
description: What to check when you update Hawn, from 1.1.x to 1.2, from 1.2 to 1.3, from 1.3 to 1.4, and within 1.4 (1.4.1 to 1.4.9).
---

# Updating from an older version

## How to update

1. Stop the server.
2. **Make a copy of `plugins/Hawn`** (just in case).
3. Replace the old Hawn jar by the new one in `plugins`.
4. Start the server.

Your configuration files are kept: Hawn creates the files that don't exist yet and, since 1.3, adds the options missing from your files (the console lists them). Your values are never changed.

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
