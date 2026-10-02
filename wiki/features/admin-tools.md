---
description: >-
  Admin panel, player editor, build mode, maintenance and emergency modes,
  vanish, and the other staff tools of /hawn.
---

# Admin tools

## /hawn

`/hawn` is the main admin command. Every sub-command needs **`hawn.admin`** plus its own permission, or **`hawn.admin.*`** which gives them all. `/hawn help [page]` lists them in game.

| Command                                     | Permission                          | Description                                                     |
| ------------------------------------------- | ----------------------------------- | --------------------------------------------------------------- |
| `/hawn reload` (`rl`)                       | `hawn.admin.command.reload`         | Reloads the configuration. See [what needs a restart](../getting-started/files.md#applying-your-changes). |
| `/hawn version` (`v`, `ver`)                | `hawn.admin.command.info`           | Hawn version and update status.                                 |
| `/hawn tps`                                 | `hawn.admin.command.info`           | Exact server TPS.                                               |
| `/hawn info [all/memory/cpu/disk/tps/server/version]` | `hawn.admin.command.info` | Memory, CPU, disk usage and server information.                 |
| `/hawn hooks`                               | `hawn.editplayer`                   | Shows which [hooks](../integrations/hooks.md) are detected.     |
| `/hawn parse <player\|me> <placeholder>`    | `hawn.admin.command.parseholders`   | Tests a placeholder, for example `/hawn parse me %player_world%`. |
| `/hawn build`                               | `hawn.admin.command.bypassbuild`    | Toggles the [build mode](#build-mode).                          |
| `/hawn noclip`                              | `hawn.admin.command.noclip`         | Toggles no-clip in creative: you go through walls (Hawn switches you to spectator while you are inside blocks). |
| `/hawn nightvision` (`nv`)                  | `hawn.admin.command.nightvision`    | Gives you night vision.                                         |
| `/hawn slotview` (`sv`)                     | `hawn.admin.command.slotview`       | Shows the number of the inventory slot you click (useful for [join items](custom-join-items.md)). |
| `/hawn editplayer <player>`                 | `hawn.editplayer`                   | Opens the [player editor](#player-editor).                      |
| `/hawn spawnmanager <remove/setspawn> ...`  | `hawn.admin.command.spawnmanager`   | Manages the [spawns](spawns.md).                                |
| `/hawn maintenance` (`m`)                   | `hawn.admin.command.maintenance`    | Toggles the [maintenance mode](#maintenance-mode).              |
| `/hawn urgent`                              | `hawn.admin.command.urgent`         | Turns on the [emergency mode](#emergency-mode).                 |
| `/hawn about`, `/hawn donors`               | —                                   | About Hawn.                                                     |

From the console, `/hawn` accepts `help`, `urgent`, `parse <placeholder>`, `spawnmanager remove <spawn>`, `about`, `reload`, `version`, `donors`, `maintenance`, `info`, `tps` and `hooks`.

## Build mode

By default your lobby is protected: nobody can build, drop items, use chests... `/hawn build` toggles a personal bypass for all these [protections](protections.md) and for the [join items](custom-join-items.md), without changing any permission. It lasts until you type the command again or leave.

The build mode can also follow the gamemode: `Commands/Gamemode.yml` → `Hawn-Build-Mode`:

```yaml
Gamemode:
  Options:
    Hawn-Build-Mode:
      Enable: false
      Change-For-Others-Too: true    # also when you change the gamemode of another player
      When-Enter-Into:
        Gamemode-0: false
        Gamemode-1: true             # going to creative turns the build mode on
        Gamemode-2: false
        Gamemode-3: false
```

Players need `hawn.command.gamemode.buildmode` for this.

## Admin panel

`/adminpanel` (also `/ap`, `/pa`, `/paneladmin`) opens a menu to manage the server and Hawn without typing commands.

Two conditions:

* the permission **`hawn.adminpanel`**;
* your name in `Commands/AdminPanel.yml`:

```yaml
General-Options:
  List-Of-People-Can-Use-The-Panel:
  - Dianox
  - YourName
  Warn-when-people-make-change: true
```

The panel gives access to:

* **Hawn configuration**: browse the configuration files and switch `true`/`false` options with a click. The change is saved and applied immediately. Lists and texts must still be edited in the files.
* **Reload Hawn**, **Reload the server**, **Shutdown the server**, **Save players**.
* **Player list**: every online player, click one to open the [player editor](#player-editor).
* **World System**: the [world manager](world-manager.md).

With `Warn-when-people-make-change: true`, players with `hawn.spy.adminpanel` are warned of every change made in the panel (message `Warning.Hawn-Watch-Panel-Admin` of `AdminPanel.yml`).

## Player editor

`/hawn editplayer <player>`, or a click in the player list of the admin panel. Permission `hawn.editplayer`, then:

| Button          | Permission                  |
| --------------- | --------------------------- |
| Change gamemode | `hawn.editplayer.gamemode`  |
| Clear inventory | `hawn.editplayer.clearinv`  |
| Teleport to the player | `hawn.editplayer.tp` |

## Maintenance mode

`/hawn maintenance` kicks every player who is not in the maintenance whitelist, and refuses their connection until you type the command again. The state survives a restart.

`Commands/Hawn.yml`:

```yaml
Maintenance:
  Enable: false                  # current state, changed by the command
  Kick-Message:
  - '&cThe server is in maintenance'
  - '&bCome back later %player%'
  whitelist:
  - Dianox
```

The server list shows the maintenance MOTD, see [Server list](server-list.md#special-motds). The messages are in `Messages/<language>/Admin.yml` → `Maintenance`.

## Emergency mode

`/hawn urgent` is for when something goes really wrong (a hacked staff account, a griefing in progress...). In one command it:

1. kicks every player who is not in `Urgent-mode.whitelist`, and refuses their connections;
2. makes a **zip backup of the whole server folder** next to the Hawn folder (`plugins/Hawn-save-1.zip`, `-save-2.zip`...);
3. disables every other plugin (except those in `Plugin-Ignored`);
4. warns everyone with the `Urgent-mode` messages of `Admin.yml`.

```yaml
Urgent-mode:
  Enable: false                         # current state
  Use-It-Only-On-The-Console: false     # true = the command can only be used from the console
  Plugin-desactivation:
    Disable-All-Plugins-When-Enabled: true
    Plugin-Ignored:
    - Hawn
    - LuckPerms
  Kick-Message:
  - '&cThe server is closed for now'
  whitelist:
  - Dianox
  Can-Use-Urgent-Mode:                  # players allowed to start it (with the permission)
  - Dianox
```

{% hint style="warning" %}
The emergency mode can only be turned **off from the console**: type `hawn urgent` again in the console. The disabled plugins are enabled again.
{% endhint %}

The backup copies every file of the server: make sure you have enough disk space.

## Vanish

`/vanish` (alias `/v`), permission `hawn.command.vanish`.

| Command              | Permission                     | Description                       |
| -------------------- | ------------------------------ | --------------------------------- |
| `/vanish`            | `hawn.command.vanish`          | Hides you from the other players. |
| `/vanish <player>`   | `hawn.command.vanish.others`   | Vanishes another player.          |
| `/vanish list`       | `hawn.command.vanish`          | Lists the vanished players.       |

* `hawn.staff.seevanished`: still sees the vanished players.
* `hawn.command.vanish.actionbar`: shows a "you are vanished" action bar while vanished.

```yaml
# Commands/Vanish.yml
Vanish:
  Enable: true
  Action-Bar-If-Vanished: true
  Action-Bar:
    Message-blinking: true
```

The vanish state can be kept when the player reconnects, see [Player options](player-options.md#keep-options-between-sessions).

## TPS warning

With `general.yml` → `Plugin.Tps.Warn-system: true`, players with `hawn.event.warn.tps` receive a warning when the TPS drops to 15 or below (messages `TPS.Check` of `Admin.yml`).

## Other staff commands

`/checkaccount`, `/invsee`, `/ec`, `/ip`, `/getpos`, `/kickall`, `/clearinv`, `/cleargrounditems`, `/clearmobs`, `/broadcast`, `/warning`... see the full list in [Commands](../reference/commands.md).
