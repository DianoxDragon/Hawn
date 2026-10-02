---
description: Every Hawn command, its aliases, its permission and its configuration file.
---

# Commands

{% hint style="info" %}
`<argument>` is required, `[argument]` is optional, `a/b` means "a or b".

Every command can be disabled or aliased, see [Commands management](../basics/commands-management.md). The aliases below are the default ones.
{% endhint %}

## Administration

| Command                                    | Permission                                        | Description                                        |
| ------------------------------------------ | ------------------------------------------------- | -------------------------------------------------- |
| `/hawn [help] [page]`                      | `hawn.admin`                                      | Main admin command. All its sub-commands are on the [Admin tools](../features/admin-tools.md#hawn) page. |
| `/adminpanel` · `/ap` `/pa` `/paneladmin`  | `hawn.adminpanel` + listed in `Commands/AdminPanel.yml` | Opens the [admin panel](../features/admin-tools.md#admin-panel). |
| `/hw` · `/hworld`                          | `hawn.command.world.general`                      | [World manager](../features/world-manager.md).     |
| `/checkaccount <player>` · `/checka`       | `hawn.command.checkaccount`                       | Shows the stored information of a player.          |
| `/kickall`                                 | `hawn.command.kickall`                            | Kicks every player (except `hawn.command.bypass.kickall`). |

## Spawns and warps

| Command                                    | Permission                                        | Description                              |
| ------------------------------------------ | ------------------------------------------------- | ---------------------------------------- |
| `/spawn [spawn]` · `/hub` `/lobby`         | `hawn.command.spawn.<spawn>`                      | Teleports to the default or a given spawn. |
| `/spawn tp <player> [spawn]`               | `hawn.command.spawn.teleportothers`               | Teleports another player.                |
| `/setspawn [name] [d:true] [w:worlds]` · `/setlobby` `/sethub` | `hawn.admin`                  | Creates a spawn.                         |
| `/delspawn <spawn>`                        | `hawn.admin`                                      | Deletes a spawn.                         |
| `/spawnlist`                               | `hawn.command.spawn.spawnlist`                    | Lists the spawns.                        |
| `/warp [warp] [player]`                    | `hawn.command.warp` + `hawn.warp.<warp>`          | Teleports to a warp.                     |
| `/setwarp <warp> [w:worlds]`               | `hawn.command.warp.setwarp`                       | Creates a warp.                          |
| `/editwarp <warp>`                         | `hawn.command.warp.editwarp`                      | Moves a warp.                            |
| `/delwarp <warp>`                          | `hawn.command.warp.delwarp`                       | Deletes a warp.                          |
| `/warplist`                                | `hawn.command.warp.warplist`                      | Lists the warps.                         |

Details: [Spawns](../features/spawns.md), [Warps](../features/warps.md). Files: `Commands/Spawn.yml`, `Commands/Warp-SetWarp.yml`.

## Announcements

| Command                                                   | Permission                          | Description                                                        |
| --------------------------------------------------------- | ----------------------------------- | ------------------------------------------------------------------ |
| `/broadcast <message>` · `/bc`                            | `hawn.command.broadcast`            | Message to everyone. `//n` starts a new line.                      |
| `/warning <message>` · `/warn`                            | `hawn.command.warning`              | Warning message to everyone.                                       |
| `/titleannouncer <message>` · `/ta` `/titlea` `/btcast`   | `hawn.command.titleannouncer`       | Title to everyone. `//n` separates the title and the subtitle.     |
| `/actionbarannouncer <message>` · `/bacast` `/aba`        | `hawn.command.actionbarannouncer`   | Action bar to everyone.                                            |

Options (sounds, fireworks, title durations, "also write it in the chat"): `Commands/Broadcast.yml`, `Warning.yml`, `TitleAnnouncer.yml`, `ActionBarAnnouncer.yml`. The prefixes and formats are in `Messages.yml` → `Broadcast` and `Warning`.

## Chat

| Command                                 | Permission                        | Description                              |
| --------------------------------------- | --------------------------------- | ---------------------------------------- |
| `/cc [c/a/o/other] [player] [reason]`   | `hawn.command.clearchat.*` (see [Chat](../features/chat.md#clear-chat)) | Clears the chat. |
| `/globalmute [minutes]` · `/gmute`      | `hawn.command.mutechat`           | Mutes the chat.                          |
| `/delaychat <seconds>` · `/dchat`       | `hawn.command.delaychat`          | Sets a delay between two messages.       |
| `/emoji`                                | `hawn.command.emoji` (optional)   | Opens the emoji list.                    |

## Player options

| Command                                    | Permission                               | Description                                      |
| ------------------------------------------ | ---------------------------------------- | ------------------------------------------------ |
| `/option [pv/fly/doublejump/speed/flyspeed/jumpboost/autobc]` | `hawn.command.optionplayer.main` + `hawn.command.optionplayer.<option>` | Toggles a [player option](../features/player-options.md). |
| `/speed [0-10]`                            | `hawn.command.optionplayer.speed`        | Toggles or sets your walk speed.                 |
| `/flyspeed [0-10]` · `/fs`                 | `hawn.command.optionplayer.flyspeed`     | Toggles or sets your fly speed.                  |
| `/scoreboard [set/keep/list] [scoreboard]` | `hawn.command.scoreboard.toggle` (see [Scoreboards](../features/scoreboards.md#scoreboard)) | Manages your scoreboard. |
| `/help [category] [page]` · `/?`           | `hawn.command.help` + `hawn.command.help.<category>` | [Custom help](../features/custom-commands.md#help). |

## Gamemode

| Command                                   | Permission                                                     | Description                       |
| ----------------------------------------- | -------------------------------------------------------------- | --------------------------------- |
| `/gamemode <0/1/2/3/survival/creative/adventure/spectator> [player]` · `/gm` | `hawn.command.gamemode.self`, `hawn.command.gamemode.other` | Changes the gamemode. |
| `/gamemode`                               | `hawn.command.gamemode.quickgm`                                | Quick switch between two gamemodes (see below). |
| `/gms [player]`, `/gmc [player]`, `/gma [player]`, `/gmsp [player]` | `hawn.command.gamemode.self`, `hawn.command.gamemode.other` | Survival, creative, adventure, spectator. |

`Commands/Gamemode.yml`:

```yaml
Gamemode:
  Options:
    Quick-Mode-Change:     # /gamemode without argument
      Enable: true
      Default-Mode: 0
      Mode1: 0             # switches between Mode1 and Mode2
      Mode2: 1
    Hawn-Build-Mode: ...   # see Admin tools > Build mode
```

## Weather and time

| Command                   | Permission                       | Description                               |
| ------------------------- | -------------------------------- | ----------------------------------------- |
| `/sun` · `/clearw`        | `hawn.command.weather.sun`       | Clear weather in your world.              |
| `/rain`                   | `hawn.command.weather.rain`      | Rain in your world.                       |
| `/thunder`                | `hawn.command.weather.thunder`   | Thunderstorm in your world.               |
| `/day`                    | `hawn.command.time.day`          | Sets the time to `Time.Set.Day.Value` (0). |
| `/night`                  | `hawn.command.time.night`        | Sets the time to `Time.Set.Night.Value` (16000). |

File: `Commands/Weather-Time.yml`.

## Player management

| Command                                         | Permission                                                     | Description                                     |
| ----------------------------------------------- | -------------------------------------------------------------- | ----------------------------------------------- |
| `/fly [player]`                                 | `hawn.command.fly`, `hawn.command.fly.others`                  | Toggles flying.                                 |
| `/heal [player]`                                | `hawn.command.heal`, `hawn.command.heal.other`                 | Heals (and feeds, `Heal.Option.Feed`). Others: `Heal.Others.Enable` must be `true`. |
| `/feed [player]`                                | `hawn.command.feed`, `hawn.command.feed.other`                 | Feeds. Others: `Feed.Others.Enable` must be `true`. |
| `/vanish [player/list]` · `/v`                  | `hawn.command.vanish`, `hawn.command.vanish.others`            | [Vanish](../features/admin-tools.md#vanish).    |
| `/clearinventory [player]` · `/clearinv`        | `hawn.command.clearinv`, `hawn.command.clearinv.others`        | Clears an inventory.                            |
| `/invsee <player>`                              | `hawn.command.invsee`                                          | Opens the inventory of a player.                |
| `/enderchest [player]` · `/ec`                  | `hawn.command.enderchest`, `hawn.command.enderchest.other`     | Opens an ender chest.                           |
| `/workbench [player]`                           | `hawn.command.workbench`                                       | Opens a crafting table.                         |
| `/exp <player> <add/set/take/clear> <amount>`   | `hawn.command.exp`                                             | Manages experience.                             |
| `/hat [player]`                                 | `hawn.command.hat`, `hawn.command.hat.other`                   | Puts the item in hand on the head.              |
| `/skull [player]`                               | `hawn.command.skull`                                           | Gives a player head.                            |
| `/repair` · `/fix`                              | `hawn.command.repair`                                          | Repairs the item in hand.                       |
| `/burn <player> <seconds>`                      | `hawn.command.burn`                                            | Sets a player on fire.                          |
| `/gotop [player]`                               | `hawn.command.gotop`, `hawn.command.gotop.other`               | Teleports to the highest block.                 |
| `/getpos <player>`                              | `hawn.command.getpos`                                          | Shows the position of a player.                 |
| `/ip <player>`                                  | `hawn.command.ip`                                              | Shows the IP of a player.                       |
| `/ping [player]`                                | `hawn.command.ping.self`, `hawn.command.ping.other`            | Shows a ping. The permissions are only checked when `Use_Permission: true` in `Commands/Ping.yml`. |
| `/list [page]`                                  | `hawn.command.list`                                            | Online players (as a menu with `Gui-Version: true`). |
| `/suicide`                                      | `hawn.command.suicide`                                         | Kills yourself.                                 |

## World

| Command                                 | Permission                         | Description                               |
| --------------------------------------- | ---------------------------------- | ----------------------------------------- |
| `/cleargrounditems` · `/cleargi`        | `hawn.command.cleargrounditems`    | Removes the items on the ground, in every world. |
| `/clearmobs` · `/clearm`                | `hawn.command.clearmobs`           | Removes the mobs, in every world.         |

## WorldEdit shortcuts

These shortcuts need WorldEdit. They run the WorldEdit command set in their file (the permissions are the WorldEdit ones).

| Command | Runs      | File              |
| ------- | --------- | ----------------- |
| `/1`    | `//pos1`  | `Commands/1-WE.yml` |
| `/2`    | `//pos2`  | `Commands/2-WE.yml` |
| `/c`    | `//copy`  | `Commands/C-WE.yml` |
| `/p`    | `//paste` | `Commands/P-WE.yml` |

{% hint style="warning" %}
`/p` and `/c` are short names that other plugins also use (plot plugins use `/p`). Disable these shortcuts with `DISABLE_THE_COMMAND_COMPLETELY: true` if they conflict.
{% endhint %}

## Your own commands

Commands created in `CustomCommand.yml` (`/rules`, `/discord`...) have the permission you choose. See [Custom commands](../features/custom-commands.md).
