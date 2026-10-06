---
description: Quick answers to frequent questions.
---

# FAQ

<details>

<summary>Which server versions are supported?</summary>

Spigot and Paper from **1.16.5** to the latest version (1.21.x, 26.x), with the same jar. Forks of Spigot/Paper (Purpur, Pufferfish...) work too. Versions older than 1.16.5 are only supported by Hawn 1.1.x.

</details>

<details>

<summary>Does Hawn work on Folia?</summary>

No, Hawn is made for Spigot and Paper.

</details>

<details>

<summary>Do I need other plugins?</summary>

No. PlaceholderAPI, WorldGuard, WorldEdit, MVdWPlaceholderAPI and BattleLevels are optional. See [Hooks](../integrations/hooks.md).

</details>

<details>

<summary>Can I use Hawn on a server that is not a lobby?</summary>

Yes. Every feature can be turned off and limited to some worlds, so Hawn can manage the lobby world of a survival or minigame server. See [Per-world options](../basics/per-world-options.md).

</details>

<details>

<summary>How do I disable a feature?</summary>

Every feature has an `Enable` (or `Disable`) option in its file. To limit it to some worlds, use its `World` block. To remove a command, see [Commands management](../basics/commands-management.md).

</details>

<details>

<summary>How do I turn off the automatic announcements?</summary>

They come from the [auto broadcast](../features/autobroadcast.md), in `AutoBroadcast.yml`. Set `Enable: false` in each section you don't want (`Messages`, `Titles`, `Action-Bar`, `BossBar`), then type `/hawn reload`. A player can also hide them for themself with `/option autobc`.

</details>

<details>

<summary>Why can my players not use /spawn?</summary>

They need `hawn.command.spawn.<spawn name>`. See [Spawns](../features/spawns.md#permissions).

</details>

<details>

<summary>How do I make a server selector?</summary>

Give a compass with the [custom join items](../features/custom-join-items.md). In its `Command-List`, either open the menu of your menu plugin with `[command-player]: <command>`, or send the player directly to a server with `[bungee]: <server>`.

</details>

<details>

<summary>How do I hide the plugin list?</summary>

With the command blocker of `Events/OnCommands.yml`, enabled by default for `/pl`, `/plugins`, `/ver`... See [Block commands](../basics/commands-management.md#block-commands).

</details>

<details>

<summary>How do I make the setup appear again?</summary>

Stop the server, delete `plugins/Hawn/StockageInfo/Setup.lock`, start the server.

</details>

<details>

<summary>How do I reset a configuration file?</summary>

Stop the server, delete the file, start the server: Hawn creates it again with the default values.

</details>

<details>

<summary>Which editor should I use for the files?</summary>

Any real text editor: Notepad++, Visual Studio Code, Sublime Text... Avoid the Windows notepad. Always save in UTF-8 and indent with spaces.

</details>

<details>

<summary>Can I translate Hawn?</summary>

Yes, every message can be changed and you can create your own language folder. See [Translating Hawn](translating.md).

</details>

<details>

<summary>Does Hawn send statistics?</summary>

Hawn uses [bStats](https://bstats.org/plugin/bukkit/Hawn/4563) to count the servers using it (anonymous). You can turn it off for all your plugins in `plugins/bStats/config.yml`.

</details>
