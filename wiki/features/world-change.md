---
description: >-
  Change the gamemode, the fly and the player options, or run commands when a
  player enters a world.
---

# World change events

`Events/PlayerWorldChange.yml` runs when a player goes from one world to another (portal, `/spawn`, `/warp`, `/hw tp`...). This is useful when your lobby is on the same server as other worlds: minigames, creative, survival.

## Gamemode

```yaml
GM:
  Enable: true
  CustomMode:
    Enable: true
    GameMode: 1              # 0 survival, 1 creative, 2 adventure, 3 spectator
    If-player-have:          # only change it if the player is currently in...
      Survival: true
      Creative: true
      Adventure: true
      Spectator: true
  World:                     # the worlds where the gamemode is changed
    All_World: false
    Worlds:
    - world
```

## Fly

```yaml
Fly:
  Enable:
    Enable: true
    SetFlyOnChangeWorld: true              # allow flying when entering a listed world
    DisableFlyIfAWorldIsNotListed: true    # remove the fly when entering another world
  Cancel-Event-If-Player-Is-In:
    Gamemode-Creative-Spectator: true      # don't touch players in creative/spectator
  World:
    All_World: false
    Worlds:
    - world
```

## Player options

What happens to the [player options](player-options.md) (fly, double jump, visibility, jump boost, gamemode) when entering or leaving the listed worlds:

```yaml
Player-Options:
  Enable: true
  Keep-Options: true                       # true = the options don't change
  If-Not-Keeping:
    Reset-settings-on-world-change: false  # also save the reset values as the player's options
    Reset-When-Enter-Or-Leave-A-World:
      False-Is-Leave: false                # false = reset when LEAVING the listed worlds, true = when ENTERING them
    Options-Default:                       # the values applied
      GameMode:
        Enable: true
        Value: 1
      Fly: true
      DoubleJump: false
      PlayerVisibility: false
      JumpBoost: false
  World:
    All_World: false
    Worlds:
    - world
```

With `Keep-Options: false`, Hawn applies the `Options-Default` values when the player leaves (or enters, see `False-Is-Leave`) the listed worlds.

## Run commands when entering a world

```yaml
Execute-Command:
  Enable: true
  Options:
    When-Enter-in-The-World:
      world:
        Enable: true
        Command-List:
        - '[send-title]: &6Welcome back //n &7to the lobby'
        - '[command-console]: effect give %player% speed 10 1'
      minigames:
        Enable: true
        Command-List:
        - '&aGood luck!'
  World:
    All_World: false
    Worlds:
    - world
    - minigames
```

Each entry of `When-Enter-in-The-World` is a world name with a list of [actions](../basics/actions.md), run when a player arrives in that world. The world must also be in the `World` block.

## Also on world change

* The [scoreboard](scoreboards.md) is chosen again for the new world.
* The [join boss bar](join-and-quit.md#title-action-bar-and-boss-bar) is removed or shown again.
