---
description: Named teleport points, with a permission per warp and a teleport delay.
---

# Warps

Warps are named locations players can teleport to with `/warp <name>`. Unlike spawns, they are not used by the join or void features.

## Commands

| Command                               | Permission                                                | Description                                    |
| ------------------------------------- | --------------------------------------------------------- | ---------------------------------------------- |
| `/setwarp <warp>`                     | `hawn.command.warp.setwarp`                               | Creates a warp where you stand.                |
| `/setwarp <warp> w:world1,world2`     | `hawn.command.warp.setwarp`                               | Creates the warp in several worlds, at the same coordinates. |
| `/editwarp <warp>`                    | `hawn.command.warp.editwarp`                              | Moves an existing warp to your position.       |
| `/delwarp <warp>`                     | `hawn.command.warp.delwarp`                               | Deletes a warp.                                |
| `/warplist`                           | `hawn.command.warp.warplist`                              | Lists the warps you can use.                   |
| `/warp`                               | `hawn.command.warp` + `hawn.command.warp.warplist`        | Shows the warp list.                           |
| `/warp <warp>`                        | `hawn.command.warp` + `hawn.warp.<warp>`                  | Teleports you to the warp.                     |
| `/warp <warp> <player>`               | `hawn.command.warp` + `hawn.command.warp.others` + `hawn.warp.<warp>` | Teleports another player.          |

{% hint style="warning" %}
Like spawns, **each warp has its own permission**: `hawn.warp.<warp name>`. `/warplist` only shows the warps the player has the permission for.
{% endhint %}

Warps are saved in `warplist.yml`.

## Options

`Commands/Warp-SetWarp.yml`:

```yaml
Warp:
  Enable: true
  Disable-Message: true
  Delay:
    Self:
      Enable: true
      Delay-Seconds: 5
      Bypass-Delay: false     # true = hawn.command.warp.bypassdelay.self skips the delay
    Other:
      Enable: true
      Delay-Seconds: 5
      Bypass-Delay: false     # true = hawn.command.warp.bypassdelay.other skips the delay
    Cancel-Tp-On:
      Any-movements: true
      On-Damages: true
  DISABLE_THE_COMMAND_COMPLETELY: false
WarpList:
  ...
SetWarp:
  ...
DelWarp:
  ...
EditWarp:
  ...
```

Each of the five commands has its own `Enable`, `Disable-Message` and `DISABLE_THE_COMMAND_COMPLETELY`, see [Commands management](../basics/commands-management.md).

The messages are in `Messages/<language>/Messages.yml` → `Warp` and `Cancel-Tp.Warp`.

## Using warps elsewhere

The `[warp]: <warp>` [action](../basics/actions.md) teleports the player to a warp from a custom command, a join item, a sign... The action teleports directly: no permission and no delay. If you want a permission, put the line behind a `<perm>` tag or run `[command-player]: warp <warp>` instead.

```yaml
# CustomJoinItem/General.yml - a compass that sends to the "games" warp
CompassGames:
  Material: COMPASS
  Slot: 4
  Title: '&aMinigames'
  Command-List:
  - '[warp]: games'
```
