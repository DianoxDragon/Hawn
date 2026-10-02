---
description: >-
  Create as many spawns as you want, choose a default spawn, a first-join
  spawn and a VIP spawn, and configure /spawn.
---

# Spawns

Hawn manages an unlimited number of named spawns. One of them is the **default spawn**: players are sent there when they join, when they type `/spawn` and when they fall into the void.

## Create a spawn

Stand where you want the spawn and look in the direction players should face.

| Command                      | Result                                                                                  |
| ---------------------------- | --------------------------------------------------------------------------------------- |
| `/setspawn`                  | Creates a spawn named `Spawn1` (then `Spawn2`, `Spawn3`...) **and makes it the default spawn**. |
| `/setspawn <name>`           | Creates a spawn with this name. It does not change the default spawn.                    |
| `/setspawn <name> d:true`    | Creates the spawn and makes it the default spawn.                                       |

Permission: `hawn.admin` (or `hawn.admin.*`). Aliases: `/setlobby`, `/sethub`.

Creating a spawn also sets the vanilla spawn point of the world to this location.

{% hint style="info" %}
The first spawn you create without a name automatically becomes the default spawn, as long as `Spawn.DefaultSpawn` is still `CHANGE ME`.
{% endhint %}

<details>

<summary>Advanced: <code>w:world1,world2</code></summary>

`/setspawn [name] w:world1,world2` creates one spawn per listed world, all at your current coordinates. They are named `<name>0`, `<name>1`... (or `Spawn1`, `Spawn2`... without a name). It can be combined with `d:true`. This is only useful when several worlds share the same map at the same coordinates.

</details>

Spawns are saved in `spawn.yml`:

```yaml
Coordinated:
  lobby:
    World: world
    X: 0.5
    Y: 64.0
    Z: 0.5
    Yaw: 90.0
    Pitch: 0.0
    Info: 'Player Dianox created the spawn at: 02-10-2026, 14:12'
```

## Other spawn commands

| Command                              | Permission                                        | Description                                         |
| ------------------------------------ | ------------------------------------------------- | --------------------------------------------------- |
| `/spawn`                             | `hawn.command.spawn.<spawn>`                       | Teleports you to the default spawn (or the custom spawn, see below). Aliases `/hub`, `/lobby`. |
| `/spawn <spawn>`                     | `hawn.command.spawn.<spawn>`                       | Teleports you to a given spawn.                     |
| `/spawn tp <player> [spawn]`         | `hawn.command.spawn.teleportothers` + `hawn.command.spawn.<spawn>` | Teleports another player. Works from the console. |
| `/spawnlist`                         | `hawn.command.spawn.spawnlist`                     | Lists the spawns. Players only see the spawns they have `hawn.spawn.<spawn>` for. |
| `/delspawn <spawn>`                  | `hawn.admin`                                      | Deletes a spawn.                                    |
| `/hawn spawnmanager remove <spawn>`  | `hawn.admin.command.spawnmanager`                 | Deletes a spawn (works from the console).           |
| `/hawn spawnmanager setspawn [...]`  | `hawn.admin.command.spawnmanager`                 | Same arguments as `/setspawn`.                      |

## Permissions

{% hint style="warning" %}
**Every spawn has its own permission: `hawn.command.spawn.<spawn name>`.** It is needed for `/spawn`, for the teleport on join and for the void TP, **even when `Use-Permission` is `false`**. A player without it gets a "no permission" message instead of being teleported.

For a spawn named `lobby`, give `hawn.command.spawn.lobby` to everyone.
{% endhint %}

Players with `hawn.command.spawn.other.bypassdelay` skip the teleport delay (when `Bypass-Delay` is enabled, see below).

## The default spawn

`Events/OnJoin.yml`:

```yaml
Spawn:
  DefaultSpawn: lobby
  FirstJoin-Spawn:
    Enable: false
    Spawn: CHANGE ME
```

* `DefaultSpawn`: the name of the default spawn. Set automatically by `/setspawn`, `/setspawn <name> d:true` and the welcome setup.
* `FirstJoin-Spawn`: send new players (first connection) to another spawn, a tutorial for example.

## Teleport on join

`Events/OnJoin.yml`:

```yaml
Event:
  OnJoin:
    Tp-To-Spawn: true
    CustomSpawn:
      Enable: false
      Spawn: CHANGE ME
    Custom-Group-Join:
      VIP:
        Enable: false
        Spawn: CHANGE ME
```

When a player joins:

1. **New player** → `FirstJoin-Spawn` if enabled, else the normal rules below.
2. If `Custom-Group-Join.VIP` is enabled and the player has `hawn.event.spawn.join.vip` → the VIP spawn.
3. Else if `CustomSpawn` is enabled → this spawn.
4. Else → the `DefaultSpawn`.

`Tp-To-Spawn: false` disables the teleport for players who already played. If "teleport to the last position" is enabled, players are sent back where they left instead, see [Player options](player-options.md#keep-options-between-sessions).

The teleport message is `Spawn.Teleport` in `Messages/<language>/General.yml` (`Enable-For-On-Join` decides if it is also sent on join).

## /spawn options

`Commands/Spawn.yml`:

```yaml
Commands:
  Spawn:
    Use-Permission: false        # true = also require hawn.command.spawn
    CustomSpawn:
      Enable: false              # /spawn sends to this spawn instead of the default one
      Spawn: CHANGE ME
    Delay:
      Self:
        Enable: true             # wait before being teleported
        Delay-Seconds: 5
        Bypass-Delay: false      # true = hawn.command.spawn.other.bypassdelay skips the delay
      Other:                     # same for /spawn tp <player>
        Enable: true
        Delay-Seconds: 5
        Bypass-Delay: false
      Cancel-Tp-On:
        Any-movements: true      # moving cancels the teleport
        On-Damages: true         # taking damage cancels the teleport
DISABLE_THE_COMMAND_COMPLETELY: false
SetSpawn:
  Enable: true
  ...
```

The delay messages are in `Messages/<language>/Messages.yml` → `Spawn.Tp` and `Cancel-Tp.Spawn`.

## Spawns used elsewhere

You can also send players to a spawn with:

* the `[spawn]: <spawn>` [action](../basics/actions.md) (custom commands, items, signs...);
* the [void TP](void-tp.md), which can use its own spawn per world;
* the respawn options of `Events/PlayerEvents.yml`, see [Protections](protections.md#death-and-respawn).

## Problems?

"The spawn doesn't exist", "You have to change the spawn on Spawn.DefaultSpawn"... See [Troubleshooting](../help/troubleshooting.md#spawn).
