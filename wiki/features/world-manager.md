---
description: >-
  Create, import, load, unload, delete and edit worlds with /hw or a menu,
  including empty void worlds.
---

# World manager

Hawn has a small world manager, enough for a lobby server: create an empty world for your lobby, import a downloaded map, change the time or the difficulty of a world. It can be used with commands or with a menu.

## The menu

`/hw` (or `/hworld`) opens the menu. It is also available from the [admin panel](admin-tools.md#admin-panel).

* Every loaded world is shown (sapling for the overworlds, netherrack for the nethers, end stone for the ends). World folders that are not loaded are shown as red glass panes: load them with `/hw import <name>`. The folders are looked for in the folder of the worlds (`--world-container` if the server uses it).
* **Left-click**: join the world.
* **Right-click**: the menu of the world. The book at the top shows its players, environment and type, generator, loaded chunks, entities, spawn, time, PvP and seed. The other items change the time, the weather and the difficulty, and the nether star makes it the [default world](#the-default-world).
* **Shift + right-click**: delete the world (a confirmation is asked).
* **Create a new world**: type the name in the chat, then everything is on one page: the environment (normal, nether, end), the world type (normal, flat, amplified, large biomes) and the generator (crafting table: left click for the default one or the Hawn void generator, right click to type the name of a generator plugin in the chat). Click the sign to create it.

Each click gives an answer: a sound when you choose something, a message (and another sound) when a change is made, refused (permission), or impossible (a world that can't be created, a confirmation with nothing chosen).

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
| `/hw delete <world> confirm`                    | `hawn.command.world.delete`   | Deletes a world **and its folder**. Without `confirm`, Hawn only says what will be deleted. |
| `/hw default [world]`                           | `hawn.command.world.setdefault` | Shows the [default world](#the-default-world), or makes a loaded world the default one. Works from the console too. |

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
`/hw delete` removes the world folder from the disk. There is no undo: make a backup first. The main world, its nether and its end can't be deleted, and nothing is deleted if the world can't be unloaded.
{% endhint %}

## The default world

The default world is the world written in the `World.Worlds` lists of the configuration files: the world where the join messages, the protections, the scoreboards, the join items, the void TP... apply. It is saved in `general.yml` → `Plugin.Default-World`, and marked "Default world" in the menu.

To use another world, open its menu (right-click on it in `/hw`) and **shift + click the nether star**, or type `/hw default <world>` (also from the console). Hawn then:

* writes the new world in place of the old one in every `Worlds` list of its files (the other worlds of the lists are kept);
* renames the options set for the old world only: the join and quit messages per world, the void TP per world, the commands run when entering the world;
* reads its files again (like `/hawn reload`) and says how many options were changed in how many files.

Nothing else is changed: `World-List.yml`, the spawns, the warps and the player data are left alone. If the default spawn is in the old world, Hawn says so: create one in the new world with `/setspawn`. A file with a YAML error is left alone too, and Hawn names it. When the new world already had its own options somewhere (a join message for it, for example), these are kept and Hawn names the file.

The world chosen in the [welcome setup](../getting-started/welcome-setup.md) becomes the default world. On a server that was set up before Hawn 1.4, the default world is the world the most written in the lists of the files (the main world when in doubt): check it in `general.yml` or in the startup console (`Default world:`).

The default world can't be deleted: choose another one first.

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
| `hawn.command.world.setdefault`       | make it the default world            |

## Worlds loaded at startup

Hawn remembers the worlds in `World-List.yml`, with their environment, type and generator. The worlds marked `Load: true` are loaded again at every start, so you don't need another world plugin. On Paper 26.x, the created worlds are kept in `world/dimensions/minecraft/` and are loaded again too.

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

The Hawn void generator creates a completely empty world, perfect to build a lobby from scratch: one bedrock block under the spawn (0, 63, 0), nothing else. Since Hawn 1.4, it uses the generator API of 1.17.1+ on these versions (the old one is deprecated), and the old one on 1.16.5.

* With Hawn: `/hw create lobby normal g:hvg`, or "Void-Generator" in the creation menu.
* With another world plugin, use `Hawn` as the generator name, for example with Multiverse: `/mv create lobby normal -g Hawn`.
* For the main world of the server, in `bukkit.yml`:

```yaml
worlds:
  world:
    generator: Hawn
```

`Commands/World.yml` → `DISABLE_THE_COMMAND_COMPLETELY: true` removes `/hw` if you prefer another world plugin.
