---
description: The most common problems and how to fix them.
---

# Troubleshooting

## Spawn

### "You have to change the spawn on Spawn.DefaultSpawn on Events/OnJoin.yml"

No default spawn has been chosen yet. Stand where you want it and type `/setspawn` (or `/setspawn <name> d:true`). See [Spawns](../features/spawns.md).

### "You don't have the permission hawn.command.spawn.<name>"

Every spawn has its own permission, even when `Use-Permission` is `false`. Give `hawn.command.spawn.<name>` to your players (for example `hawn.command.spawn.lobby`). This permission is also needed for the void TP, and for the teleport on join when `Event.OnJoin.Spawn-Permission.Enable` is `true`.

### "The spawn doesn't exist" with /spawn, /hub or /lobby

1. Check the names of your spawns with `/spawnlist` (or in `spawn.yml`).
2. `Events/OnJoin.yml` → `Spawn.DefaultSpawn` must be one of these names, **with the same upper and lower case**.
3. If `Commands/Spawn.yml` → `Commands.Spawn.CustomSpawn.Enable` is `true`, its `Spawn` must also exist.
4. `/hawn reload`.

### Players are not teleported to the spawn when they join

* `Events/OnJoin.yml` → `Event.OnJoin.Tp-To-Spawn` must be `true`.
* If `Event.OnJoin.Spawn-Permission.Enable` is `true`, players need `hawn.command.spawn.<spawn>`.
* If `Player-Option-General.yml` → `TP.Last-Position-On-Join` is enabled, players with `hawn.betweenservers.tplastposition` go back to their last position instead.
* Another plugin may teleport players after Hawn (Essentials `spawn-on-join`, Multiverse...). Disable the teleport in the other plugin.

### Players are teleported to the spawn while exploring caves

The [void TP](../features/void-tp.md) height is too high for 1.18+ worlds. Set `Events/VoidTP.yml` → `TP-y` to `-70`.

## Permissions

### Players can't do anything

Hawn gives no permission to normal players. Operators have everything, so test with a non-op account. See the [starter list](../reference/permissions.md#starter-list-for-a-lobby).

### /help says I have no permission

`Commands/Help.yml` → `Use-Permissions` is `true` by default. Give `hawn.command.help` and `hawn.command.help.<category>`, or set it to `false`.

## Features

### Players don't receive the join items

* They need `hawn.use.customjoinitem`.
* Their world must be in `CustomJoinItem/General.yml` → `General-Option.World`.
* With `Use_Permission_Per_Item: true`, each item needs `hawn.use.cji.item.<item key>`.
* Every inventory item needs a `Slot`. The console says which item is missing it.

### The scoreboard is not displayed

* The player needs `hawn.scoreboard.<file name>` (check the console at startup for the exact permission).
* The player's world must be in the `World` block of the scoreboard file.
* New files need a restart.
* Another plugin may be using the sidebar.
* The player may have hidden it with `/scoreboard`.

### As an admin, I can't build / open chests in my lobby

That's the lobby protection. Type `/hawn build` to toggle a build bypass for yourself. See [Admin tools](../features/admin-tools.md#build-mode).

### NPCs, pets or mobs don't spawn in the lobby

`Events/WorldEvent.yml` → `World.Disable.Spawning-Monster-Animals` blocks every creature spawn, including the entities of other plugins. Remove your world from its list.

### A Hawn command replaces the command of another plugin

Set `DISABLE_THE_COMMAND_COMPLETELY: true` in the file of the command (`Commands/<command>.yml`) and restart. See [Commands management](../basics/commands-management.md).

### Double jump and fly don't work well together

Both use the flight ability. Use the double jump **or** the fly on join for the same players. Some anti-cheat plugins also need an exception for the double jump.

### Emojis or special characters show as `?`

Save your files in **UTF-8** (the default of Notepad++, VS Code, Sublime Text...).

## Configuration

### My changes are not applied

`/hawn reload` doesn't reload everything. Restart the server after changing commands, aliases, scoreboard files, the auto broadcast or the always day/night. See [Applying your changes](../getting-started/files.md#applying-your-changes).

### "The material X is not recognized" / "The sound X is not recognized"

A material or sound name doesn't exist on your server version. The console says which file and which key. See [Values](../reference/values.md). Hawn uses a barrier (material) or a cave sound instead so that nothing breaks.

### A file is ignored or reset / "InvalidConfigurationException"

The YAML syntax of the file is broken. Common mistakes:

* tabs instead of spaces;
* a missing space after `:` or `-`;
* an apostrophe inside a text between single quotes: write `''`;
* a text starting with `&`, `%`, `[`, `{` or `*` without quotes.

Check the file on [yamllint.com](https://www.yamllint.com/).

### The console is spammed by MVdWPlaceholderAPI

MVdWPlaceholderAPI needs a premium plugin of Maximvdw (FeatherBoard...). Without one, remove MVdWPlaceholderAPI. See [Hooks](../integrations/hooks.md#mvdwplaceholderapi).

## Still stuck?

Ask on the Discord server linked on the [Spigot page](https://www.spigotmc.org/resources/hawn-hub-lobby-management.66907/). Give:

* the Hawn version (`/hawn version`) and the server version;
* the full error of the console, if any (use a paste site like [mclo.gs](https://mclo.gs/));
* the configuration file concerned.
