---
description: Quick answers to frequent questions.
---

# FAQ

<details>

<summary>Which server versions are supported?</summary>

Spigot, Paper, Purpur and Folia from **1.16.5** to the latest version (1.21.x, 26.x), with the same jar. Other forks of Spigot/Paper (Pufferfish...) work too. Versions older than 1.16.5 are only supported by Hawn 1.1.x. Hawn 2.0 will need 1.17 or newer.

</details>

<details>

<summary>Does Hawn work on Folia?</summary>

Yes, since Hawn 1.6: every task runs on the thread of the region, the entity or the player it touches. The world manager is off there, because Folia can neither create nor unload a world while the server runs. See [Installation](../getting-started/installation.md).

</details>

<details>

<summary>Do I need other plugins?</summary>

No. PlaceholderAPI, LuckPerms, WorldGuard, WorldEdit and Nexo are optional. The [holograms](../features/holograms.md) need [PacketEvents](https://modrinth.com/plugin/packetevents). See [Hooks](../integrations/hooks.md).

</details>

<details>

<summary>Does Hawn work with EssentialsX or CMI?</summary>

Yes. Since Hawn 1.6.1, the commands that EssentialsX (or another plugin) also declares, such as `/spawn`, `/fly` or `/warp`, are left to it. The ones of Hawn stay at `/hawn:<command>`, and `Plugin.Commands.Conflicts.Keep-Hawn-Commands` (`general.yml`) gives them back to Hawn. CMI registers its commands in its own code, so Hawn can't see them: remove the commands of Hawn you don't want with `DISABLE_THE_COMMAND_COMPLETELY`. See [Commands that another plugin also has](../basics/commands-management.md#commands-that-another-plugin-also-has).

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

They need `hawn.command.spawn.<spawn name>`. See [Spawns](../features/spawns.md#permissions). If another plugin also has `/spawn` (EssentialsSpawn...), `/spawn` is its command since Hawn 1.6.1: the one of Hawn is `/hawn:spawn` (see the question about EssentialsX above).

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

<summary>Where can I get help?</summary>

On the [Discord](https://discord.gg/yeUg5mbJuz) of Hawn (help, news and ideas), or with an issue on [GitHub](https://github.com/DianoxDragon/Hawn/issues) for a bug.

</details>

<details>

<summary>Does Hawn send statistics?</summary>

Hawn uses [bStats](https://bstats.org/plugin/bukkit/Hawn/4563) to count the servers using it (anonymous). Since Hawn 1.5.5, it also sends which modules are on (join items, scoreboard, tab list, double jump...), never their content. You can turn it off for all your plugins in `plugins/bStats/config.yml`.

</details>
