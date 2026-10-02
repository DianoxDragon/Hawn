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

{% hint style="warning" %}
The comparison is done on the **whole command line** (case does not matter). `/pl` is blocked, but `/pl Hawn` is not. Add every variant you want to block.
{% endhint %}

The message sent to the staff is `Command-Blocker.Notify-Staff` in `Messages/<language>/Admin.yml`. The `Message` lines support [actions](actions.md).

## Create your own commands

To create information commands such as `/discord`, `/rules` or `/store`, see [Custom commands and /help](../features/custom-commands.md).
