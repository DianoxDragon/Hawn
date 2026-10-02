---
description: >-
  Create, import, load, unload, delete and edit worlds with /hw or a menu,
  including empty void worlds.
---

# World manager

Hawn has a small world manager, enough for a lobby server: create an empty world for your lobby, import a downloaded map, change the time or the difficulty of a world. It can be used with commands or with a menu.

## The menu

`/hw` (or `/hworld`) opens the menu. It is also available from the [admin panel](admin-tools.md#admin-panel).

* Every loaded world is shown (sapling for the overworlds, netherrack for the nethers, end stone for the ends). World folders that are not loaded are shown as red glass panes: load them with `/hw import <name>`.
* **Left-click**: join the world.
* **Right-click**: change the time, the weather and the difficulty of the world.
* **Shift + right-click**: delete the world (a confirmation is asked).
* **Create a new world**: type the name in the chat, then choose the environment (normal, nether, end), the world type (normal, flat, amplified, large biomes) and optionally a generator (the Hawn void generator, or the name of a generator plugin typed in the chat).

## Commands

| Command                                         | Permission                    | Description                                    |
| ----------------------------------------------- | ----------------------------- | ---------------------------------------------- |
| `/hw`                                           | `hawn.command.world.general`  | Opens the menu. **Needed for every sub-command.** |
| `/hw list`                                      | `hawn.command.world.list`     | Lists the loaded worlds.                       |
| `/hw info`                                      | `hawn.command.world.info`     | Shows information about your current world.   |
| `/hw tp <world>`                                | `hawn.command.world.tp`       | Teleports you to a world.                      |
| `/hw create <name> [environment] [type]`        | `hawn.command.world.create`   | Creates a world.                               |
| `/hw import <name> [environment] [type]`        | `hawn.command.world.import`   | Loads a world folder copied on the server.     |
| `/hw unload <world>`                            | `hawn.command.world.unload`   | Unloads a world.                               |
| `/hw delete <world>`                            | `hawn.command.world.delete`   | Deletes a world **and its folder**.            |

`hawn.command.world.*` gives every sub-command.

* `environment`: `normal`, `nether` or `the_end`.
* `type`: `flat`, `amplified`, `large_biomes`, or a generator with `g:<generator>`. `g:hvg` is the Hawn void generator.

```
/hw create lobby normal g:hvg
/hw create minigames normal flat
/hw import downloaded_map normal
```

World names can only contain letters, numbers and `_`.

{% hint style="danger" %}
`/hw delete` removes the world folder from the disk. There is no undo: make a backup first.
{% endhint %}

### Menu permissions

| Permission                            | Allows to...                         |
| ------------------------------------- | ------------------------------------ |
| `hawn.command.world.general`          | open the menu                        |
| `hawn.command.world.tp`               | join a world                         |
| `hawn.command.world.create`           | create a world                       |
| `hawn.command.world.delete`           | delete a world                       |
| `hawn.command.world.import`           | load a world                         |
| `hawn.command.world.modifymain`       | open the settings of a world         |
| `hawn.command.world.modifytime`       | change its time                      |
| `hawn.command.world.modifyweather`    | change its weather                   |
| `hawn.command.world.modifydifficulty` | change its difficulty                |

## Worlds loaded at startup

Hawn remembers the worlds in `World-List.yml`, with their environment, type and generator. The worlds marked `Load: true` are loaded again at every start, so you don't need another world plugin.

`/hw unload` only unloads the world until the next restart. To stop loading it at startup, set its `Load` to `false`.

```yaml
World-List:
  lobby:
    Load: true
    Generator: hvg
  world:
    Load: true
```

## The void generator

The Hawn void generator creates a completely empty world, perfect to build a lobby from scratch.

* With Hawn: `/hw create lobby normal g:hvg`, or "Void-Generator" in the creation menu.
* With another world plugin, use `Hawn` as the generator name, for example with Multiverse: `/mv create lobby normal -g Hawn`.
* For the main world of the server, in `bukkit.yml`:

```yaml
worlds:
  world:
    generator: Hawn
```

`Commands/World.yml` → `DISABLE_THE_COMMAND_COMPLETELY: true` removes `/hw` if you prefer another world plugin.
