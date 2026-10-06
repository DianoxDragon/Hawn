---
description: What every file of the plugins/Hawn folder is for.
---

# Files and folders

Hawn splits its configuration into many small files: one per feature, one per command. You never have to read them all, open only the file of the feature you want to change.

{% hint style="info" %}
Missing files are recreated with their default values at startup. To reset a file, stop the server, delete it and start the server again.

Missing **options** are added too, at startup and on `/hawn reload`: the new options of an update, or an option deleted by mistake, come back with their default value (the console lists them). Your values are never changed, and the lists you fill yourself (custom commands, auto broadcast messages, join items, emojis, books, signs, fireworks, help pages, animations, per-world and per-group messages...) are never refilled: a deleted example stays deleted. A file with a YAML error is left as it is (the console says so).

Each file starts with a comment that gives the page of this wiki that explains it.
{% endhint %}

## Overview

```
plugins/Hawn/
├── general.yml                 Main options: language, update check, hooks, MySQL
├── spawn.yml                   Your spawns (written by /setspawn)
├── warplist.yml                Your warps (written by /setwarp)
├── World-List.yml              Worlds created or imported with the world manager
├── command-aliases.yml         Extra aliases of every command
├── CustomCommand.yml           Your own commands (/rules, /discord...)
├── AutoBroadcast.yml           Automatic messages, titles, action bars, boss bars
├── ServerList.yml              MOTD, slots, full server
├── Player-Option-General.yml   What is kept between sessions / lobbies
├── Scoreboard-General.yml      Internal scoreboard settings
├── Commands/                   One file per command (see below)
├── Events/                     Join, chat, protections, void TP, world events...
├── Cosmetics-Fun/              Join fireworks, jump pads, double jump
│   └── Utility/                Lists: fireworks, books, emojis, signs
├── CustomJoinItem/             Items given on join and the special items
├── Scoreboard/                 One file per scoreboard
├── Tablist/                    Tab list header, footer and animations
├── Messages/                   Every message, one folder per language
│   ├── en_US/
│   └── fr_FR/
└── StockageInfo/               Data written by Hawn, don't edit it
```

## Root files

| File                        | Content                                                                                                         | Page                                                   |
| --------------------------- | --------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------ |
| `general.yml`               | Update check, date format (`dd-MM-yyyy`), 12/24 hours format, TPS warning, language, hooks, MySQL.              | [Hooks](../integrations/hooks.md), [MySQL](../features/database.md) |
| `spawn.yml`                 | Spawn coordinates. Written by `/setspawn`, `/delspawn`.                                                          | [Spawns](../features/spawns.md)                        |
| `warplist.yml`              | Warp coordinates. Written by `/setwarp`, `/editwarp`, `/delwarp`.                                                | [Warps](../features/warps.md)                          |
| `World-List.yml`            | Worlds created/imported with `/hw`, loaded again at every start.                                                 | [World manager](../features/world-manager.md)          |
| `command-aliases.yml`       | Aliases of every command (`/hub`, `/lobby`, `/gm`...).                                                           | [Commands management](../basics/commands-management.md) |
| `CustomCommand.yml`         | Your own information commands.                                                                                  | [Custom commands](../features/custom-commands.md)      |
| `AutoBroadcast.yml`         | The four auto broadcasts.                                                                                       | [Auto broadcast](../features/autobroadcast.md)         |
| `ServerList.yml`            | MOTD, fake slots, joining a full server, anti world downloader kick message.                                    | [Server list](../features/server-list.md)              |
| `Player-Option-General.yml` | Options kept when a player reconnects (gamemode, vanish, speed, fly...), last position, flying boots.            | [Player options](../features/player-options.md)        |
| `Scoreboard-General.yml`    | `Scoreboard.Enable: false` turns off every scoreboard (restart needed). Also remembers that the default scoreboards were generated. | [Scoreboards](../features/scoreboards.md)              |

## Commands/

One file per command. Every file has at least:

```yaml
<Command>:
  Enable: true            # false = the command answers "disabled"
  Disable-Message: true   # send the "command disabled" message
DISABLE_THE_COMMAND_COMPLETELY: false   # true = the command doesn't exist at all (restart needed)
```

Some files have more options: `Spawn.yml` (teleport delay, custom spawn), `Warp-SetWarp.yml` (delay), `Help.yml` (the whole `/help`), `Hawn.yml` (maintenance and emergency mode), `Gamemode.yml` (quick gamemode, build mode), `ClearChat.yml`, `TitleAnnouncer.yml`... They are described on the page of each feature and in [Commands](../reference/commands.md).

## Events/

| File                    | Content                                                                                               | Page                                                 |
| ----------------------- | ----------------------------------------------------------------------------------------------------- | ---------------------------------------------------- |
| `OnJoin.yml`            | Default spawn, teleport on join, inventory/chat clear, gamemode, food/health, XP, fly, title, action bar, speed, potions, boss bar. | [Join and quit](../features/join-and-quit.md), [Spawns](../features/spawns.md) |
| `JoinQuitCommand.yml`   | Actions run when a player joins or quits.                                                             | [Join and quit](../features/join-and-quit.md)        |
| `Chat.yml`              | Anti-swear, chat colours, mentions, emojis.                                                           | [Chat](../features/chat.md)                          |
| `OnCommands.yml`        | Command blocker.                                                                                      | [Commands management](../basics/commands-management.md) |
| `ProtectionPlayer.yml`  | Anti-damage.                                                                                          | [Protections](../features/protections.md)            |
| `PlayerEvents.yml`      | Drops, pick-up, inventory, death, respawn, hunger, off hand, mounts.                                  | [Protections](../features/protections.md)            |
| `ProtectionWorld.yml`   | Build, item frames, interactions, buckets.                                                            | [Protections](../features/protections.md)            |
| `WorldEvent.yml`        | Weather, always day/night, fire, explosions, leaves, mob spawning, shears, portals.                   | [World events](../features/world-events.md)          |
| `VoidTP.yml`            | Teleport players who fall into the void.                                                              | [Void TP](../features/void-tp.md)                    |
| `PlayerWorldChange.yml` | What happens when a player changes world.                                                             | [World change events](../features/world-change.md)   |
| `OtherFeatures.yml`     | Coloured signs, emoji signs, sign system.                                                             | [Lobby fun](../features/lobby-fun.md)                |

## Cosmetics-Fun/

| File                          | Content                                                       |
| ----------------------------- | ------------------------------------------------------------- |
| `OnJoin.yml`                  | Fireworks and lightning strikes when a player joins.          |
| `JumpPads.yml`                | Jump pads.                                                    |
| `DoubleJump.yml`              | Double jump.                                                  |
| `Utility/Firework-List.yml`   | Named fireworks, used with the `[FWLU]: <name>` action.       |
| `Utility/Book-List.yml`       | Written books given as custom join items.                     |
| `Utility/Emojis-List.yml`     | Chat emojis and the `/emoji` menu.                            |
| `Utility/Sign-List.yml`       | Signs of the sign system (and the placed signs, written by Hawn). |

See [Join and quit](../features/join-and-quit.md), [Lobby fun](../features/lobby-fun.md), [Chat](../features/chat.md) and [Custom join items](../features/custom-join-items.md).

## CustomJoinItem/

| File                      | Content                                                   |
| ------------------------- | --------------------------------------------------------- |
| `General.yml`             | Items given on join (armour and inventory).              |
| `Special-HidePlayers.yml` | The "player visibility" item.                             |
| `Special-LobbyBow.yml`    | The lobby bow.                                            |
| `Special-FunGun.yml`      | The fun gun.                                              |

See [Custom join items](../features/custom-join-items.md).

## Scoreboard/ and Tablist/

* `Scoreboard/` contains one file per scoreboard. Two are created the first time: `scoreboard.default.yml` and `scoreboard.worldnetherbecausewelikethat.yml`. See [Scoreboards](../features/scoreboards.md).
* `Tablist/Tablist.yml` contains the header, the footer and the animations. See [Tab list](../features/tablist.md).

## Messages/

One folder per language, selected by `general.yml` → `Plugin.Language-Type`. Each folder contains:

| File                | Content                                                                    |
| ------------------- | -------------------------------------------------------------------------- |
| `General.yml`       | Prefix, join and quit messages, join MOTD, first join broadcast, teleport messages. |
| `Messages.yml`      | Messages of the player commands and features, error messages.             |
| `Admin.yml`         | Messages of the admin commands (`/hawn`, maintenance, emergency mode...). |
| `AdminPanel.yml`    | Texts of the admin panel.                                                  |
| `WorldManager.yml`  | Texts of the world manager.                                                |
| `SetupLang.yml`     | Texts of the welcome setup.                                                |

See [Translating Hawn](../help/translating.md).

## StockageInfo/

Written by Hawn, you don't need to edit it.

* `YamlPlayer/<uuid>.yml`: player data (name, join dates, options, last position, IP if `Save-IP` is on...) when MySQL is not used. See [Player data and MySQL](../features/database.md).
* `Setup.lock`: created when the [welcome setup](welcome-setup.md) is finished. Delete it to run the setup again.

## Applying your changes

`/hawn reload` (or `/hawn rl`) reloads almost every file. A **restart** is still needed for:

* enabling, disabling or aliasing commands (`DISABLE_THE_COMMAND_COMPLETELY`, `command-aliases.yml`);
* new scoreboard files, or changes to the worlds of a scoreboard;
* the always day / always night tasks;
* removing a hook (PlaceholderAPI...).

{% hint style="info" %}
Use a real text editor (Notepad++, VS Code, Sublime Text...) and keep the YAML indentation with **spaces**, never tabs. You can check a file on [yamllint.com](https://www.yamllint.com/) if Hawn complains about it.
{% endhint %}
