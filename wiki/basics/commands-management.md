---
description: >-
  Turn Hawn commands on and off, remove them completely, add aliases and block
  other plugins' commands.
---

# Enabling, disabling and aliasing commands

Hawn comes with around 60 commands. You decide which ones exist on your server.

## Disable a command

Each command has its own file in `plugins/Hawn/Commands/`. For example `Commands/Fly.yml`:

```yaml
Fly:
  Enable: true
  Disable-Message: true
DISABLE_THE_COMMAND_COMPLETELY: false
```

You have two ways to disable it:

| Option                                  | Result                                                                                                                                       | Applied with   |
| --------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------- | -------------- |
| `Enable: false`                         | The command still exists but answers "this command is disabled" (if `Disable-Message: true`) and does nothing.                               | `/hawn reload` |
| `DISABLE_THE_COMMAND_COMPLETELY: true`  | Hawn does not register the command at all. Useful when another plugin provides the same command (`/fly`, `/spawn`, `/list`, `/help`...).      | **Restart**    |

{% hint style="info" %}
Some files contain several commands, each with its own options: `Spawn.yml` (`/spawn`, `/setspawn`, `/delspawn`, `/spawnlist`), `Warp-SetWarp.yml` (the five warp commands), `Gamemode.yml` (`/gamemode`, `/gms`, `/gmc`, `/gma`, `/gmsp`), `Weather-Time.yml` (`/sun`, `/rain`, `/thunder`, `/day`, `/night`).
{% endhint %}

`/hawn`, `/adminpanel` (and its aliases) can't be disabled. `/hw` can be removed with `Commands/World.yml` → `DISABLE_THE_COMMAND_COMPLETELY`.

The message sent for a disabled command is `Error.Command-Disable` in `Messages/<language>/Messages.yml`.

## Hide the commands a player can't use

`general.yml`:

```yaml
Plugin:
  Commands:
    Hide-Without-Permission: true
```

* `true` (default): a command that needs a permission (`/gmc`, `/kickall`, `/hw`, `/broadcast`...) is hidden from the players who don't have it: it is not in their list of commands nor in their tab completion. If they type it anyway, the server answers "Unknown or incomplete command".
* `false`: every command is shown to everyone, and a player without the permission gets the Hawn "no permission" message.

A restart is needed after changing it. The commands whose permission depends on your configuration (`/spawn`, `/ping`, `/cc`, `/help`, `/hawn`...) are always shown.

The tab completion of the arguments follows the permissions too: `/spawn` and `/warp` only suggest the spawns and warps the player can go to, `/scoreboard set` the scoreboards they can use.

## Aliases

`command-aliases.yml` lists the extra names of every command:

```yaml
Spawn:
  Enable: true
  Cannot-Be-changed:
    Main-Command-Is: spawn
  Aliases:
  - hub
  - lobby
```

* `Enable`: register the aliases of this command (`false` = only the main name exists).
* `Aliases`: as many aliases as you want, without `/`.
* `Cannot-Be-changed`: just a reminder of the main name, editing it does nothing.

Aliases are real registered commands: they appear in the tab completion. A **restart** is needed after a change.

Aliases enabled by default:

| Command                | Aliases                    |
| ---------------------- | -------------------------- |
| `/spawn`               | `/hub`, `/lobby`           |
| `/setspawn`            | `/setlobby`, `/sethub`     |
| `/broadcast`           | `/bc`                      |
| `/titleannouncer`      | `/ta`, `/titlea`, `/btcast` |
| `/actionbarannouncer`  | `/bacast`, `/aba`          |
| `/warning`             | `/warn`                    |
| `/gamemode`            | `/gm`                      |
| `/vanish`              | `/v`                       |
| `/help`                | `/?`                       |
| `/clearinventory`      | `/clearinv`                |
| `/enderchest`          | `/ec`                      |
| `/repair`              | `/fix`                     |
| `/sun`                 | `/clearw`                  |
| `/flyspeed`            | `/fs`                      |
| `/globalmute`          | `/gmute`                   |
| `/delaychat`           | `/dchat`                   |
| `/checkaccount`        | `/checka`                  |
| `/cleargrounditems`    | `/cleargi`                 |
| `/clearmobs`           | `/clearm`                  |

The other commands have aliasing disabled (`Enable: false`) with an empty list: fill the list and set `Enable: true` to add some.

## Block commands

`Events/OnCommands.yml` blocks commands of any plugin, typically to hide your plugin list:

```yaml
Block-Commands:
  Enable: true
  Bypass: true            # players with hawn.event.bypass.blockcommands can use them
  Message-Enable: true
  Message:
  - '&cSorry... But ! You''re noob'
  Options:
    Face-Guardian-1-13-1-14: true   # elder guardian effect + sound on the player
    Notify-Staff: true              # warn players with hawn.notify.staff.commandblocker
  List:
  - /pl
  - /plugins
  - /bukkit:pl
  - /ver
  - /version
  - ...
```

{% hint style="info" %}
Hawn compares the **command**, not the whole line: case, extra spaces and arguments do not matter, and the `plugin:` prefix is removed. `/pl` also blocks `/PL`, `/pl Hawn` and `/bukkit:pl`, but not `/plugins`. An entry with several words (`/gamemode creative`) blocks the commands that start with these words. The blocked commands are also hidden from the tab completion.
{% endhint %}

The message sent to the staff is `Command-Blocker.Notify-Staff` in `Messages/<language>/Admin.yml`. The `Message` lines support [actions](actions.md).

## Create your own commands

To create information commands such as `/discord`, `/rules` or `/store`, see [Custom commands and /help](../features/custom-commands.md).
