---
description: >-
  Let players choose their own settings with /option (visibility, fly, double
  jump, speed, jump boost, broadcasts) and keep them between sessions.
---

# Player options

Player options are personal settings that each player can turn on and off. They are saved per player (in YAML or [MySQL](database.md)) and can be restored when the player comes back, even on another lobby of your network.

## /option

| Command                        | Permission                                                         | Description                                                    |
| ------------------------------ | ------------------------------------------------------------------ | -------------------------------------------------------------- |
| `/option`                      | `hawn.command.optionplayer.main`                                   | Shows the list of options. **Needed for every sub-command.**   |
| `/option pv`                   | `hawn.command.optionplayer.pv`                                     | Hides / shows the other players.                               |
| `/option fly`                  | `hawn.command.optionplayer.fly`                                    | Turns flying on / off.                                         |
| `/option doublejump` (`dj`)    | `hawn.command.optionplayer.doublejump` or `hawn.fun.doublejump.double` | Turns the [double jump](lobby-fun.md#double-jump) on / off. |
| `/option speed`                | `hawn.command.optionplayer.speed`                                  | Turns the speed boost on / off.                                |
| `/option flyspeed` (`fs`)      | `hawn.command.optionplayer.flyspeed`                               | Turns the fly speed boost on / off.                            |
| `/option jumpboost`            | `hawn.command.optionplayer.jumpboost`                              | Turns a permanent jump boost on / off.                         |
| `/option autobc`               | `hawn.command.optionplayer.autobc`                                 | Hides / shows the [auto broadcast](autobroadcast.md) (chat, titles, action bar, boss bar). |

Fly and double jump are mutually exclusive: turning one on turns the other off.

### Speed commands

| Command             | Permission                            | Description                                                                 |
| ------------------- | ------------------------------------- | --------------------------------------------------------------------------- |
| `/speed`            | `hawn.command.optionplayer.speed`     | Toggles the speed boost (same as `/option speed`).                          |
| `/speed <0-10>`     | `hawn.command.optionplayer.speed`     | Sets your walk speed (2 is the vanilla speed). The boost must be on.        |
| `/flyspeed`         | `hawn.command.optionplayer.flyspeed`  | Toggles the fly speed boost.                                                |
| `/flyspeed <0-10>`  | `hawn.command.optionplayer.flyspeed`  | Sets your fly speed (1 is the vanilla speed). The boost must be on.         |

When the boost is turned on, the speed is the value of `Events/OnJoin.yml` → `Speed.Value` (or `FlySpeed.Value`). With `Priority-For-Player-Option: true` in that file and `hawn.command.optionplayer.speed.priorityoptionplayer` (or `.flyspeed.priorityoptionplayer`), the player gets back the last value they chose instead.

## Configuration

`Commands/PlayerOption.yml`:

```yaml
PlayerOption:
  Enable: true
  Disable-Message: true
  Option:
    Jumpboost:
      Value: 2        # amplifier of the jump boost
  World:              # worlds where /option can be used
    All_World: false
    Worlds:
    - world
DISABLE_THE_COMMAND_COMPLETELY: false
```

The messages are in `Messages/<language>/Messages.yml` → `PlayerOption`.

## Keep options between sessions

`Player-Option-General.yml`:

```yaml
General:
  Enable: true
Keep:
  Gamemode-On-Join:
    Enable: false
  Vanish-On-Join:
    Enable: false
  PlayerVisibility-OnJoin:
    Enable: false
  Speed-OnJoin:
    Enable: false
  FlySpeed-OnJoin:
    Enable: false
  DoubleJump-Fly-OnJoin:
    Enable: false
  JumpBoost-OnJoin:
    Enable: true
TP:
  Last-Position-On-Join:
    Enable: false
Options:
  Flying:
    Put-boots: true
```

| Option                     | What is restored when the player joins                              | Permission needed                          |
| -------------------------- | ------------------------------------------------------------------- | ------------------------------------------ |
| `Gamemode-On-Join`         | Their gamemode when they left.                                       | `hawn.onjoin.keepgamemode`                 |
| `Vanish-On-Join`           | Their [vanish](admin-tools.md#vanish) state.                         | `hawn.betweenservers.keepvanish`           |
| `Speed-OnJoin`             | Their walk speed.                                                    | `hawn.onjoin.playeroption.speed`           |
| `FlySpeed-OnJoin`          | Their fly speed.                                                     | `hawn.onjoin.playeroption.flyspeed`        |
| `DoubleJump-Fly-OnJoin`    | Fly or double jump.                                                  | —                                          |
| `PlayerVisibility-OnJoin`  | Whether they hid the other players (same as `OnJoin-Priority-For-Player-Option` of the [player visibility item](custom-join-items.md#player-visibility-special-hideplayers)). | — |
| `JumpBoost-OnJoin`         | Their jump boost (`/option jumpboost`). When `false`, the jump boost is removed and turned off when they join. | — |
| `TP.Last-Position-On-Join` | They are teleported back where they left instead of the spawn (to the spawn if that world doesn't exist anymore). | `hawn.betweenservers.tplastposition`       |

`General.Enable: false` turns off the whole restoration: only the speeds of `Events/OnJoin.yml` are applied.

`JumpBoost-OnJoin` is `true` in new configurations. A configuration generated before Hawn 1.3 has `false`: set it to `true` to keep the jump boost of your players.

`Options.Flying.Put-boots`: players with `hawn.fun.boots.flying` get a pair of diamond boots while they fly (just for fun, they are removed when landing).

### Between several lobbies

With [MySQL](database.md), the options are stored in the database: a player who changes lobby finds their settings, scoreboard choice and even their last position on the new lobby.

## Options on world change

You can also reset or keep the options when a player changes world. See [World change events](world-change.md#player-options).
