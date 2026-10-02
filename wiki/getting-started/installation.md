---
description: Install Hawn on your server and check that it runs correctly.
---

# Installation

## Requirements

* A **Spigot** or **Paper** server (or a fork of them) between **1.16.5** and the latest version (1.21.x, 26.x).
* The Java version your server already needs. Hawn itself runs on Java 8 and newer.
* No other plugin is required. Hawn is a "core" lobby plugin and works on its own.

{% hint style="warning" %}
Versions older than 1.16.5 (1.8 → 1.16.4) are not supported anymore since Hawn 1.2. If you really need them, stay on Hawn 1.1.x.
{% endhint %}

## Install the plugin

1. Download `Hawn-<version>.jar` from the [Spigot page](https://www.spigotmc.org/resources/hawn-hub-lobby-management.66907/).
2. Stop your server.
3. Put the jar in the `plugins` folder of your server.
4. Start the server.

In the console you should see the Hawn logo, then `Configurations files loaded`, `Events loaded` and finally `Hawn ready !`.

A `plugins/Hawn` folder is created, with around 90 configuration files. Don't panic: every feature has its own file, and you only need to open the ones you want to change. See [Files and folders](files.md) for the full list.

## First connection

When an operator (or anyone with the `hawn.setup` permission) joins the server for the first time, Hawn opens the [welcome setup](welcome-setup.md). It lets you choose the language, the lobby world and the default spawn in three clicks.

## Permissions

Hawn does **not** declare any permission in its `plugin.yml`. This means that:

* **operators** have every Hawn permission;
* **normal players have none**, not even `/spawn`.

Use a permission plugin (LuckPerms for example) to give your players what they need. The [Permissions](../reference/permissions.md) page lists all of them, and there is a ready-to-use list for a basic lobby at the top of the page.

## Optional plugins

These plugins are not needed, but Hawn uses them when they are installed:

| Plugin                                                                   | What it adds                                                          |
| ------------------------------------------------------------------------ | --------------------------------------------------------------------- |
| [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) | Every PlaceholderAPI placeholder in Hawn messages, scoreboards, tab... |
| [WorldGuard](https://enginehub.org/worldguard) **7+**                     | Protections enabled or disabled per region.                           |
| WorldEdit                                                                | Needed by the `/1`, `/2`, `/c`, `/p` shortcuts.                       |
| MVdWPlaceholderAPI                                                       | MVdW placeholders.                                                    |
| BattleLevels                                                             | `%h_battlelevels_...%` placeholders.                                  |

Hawn detects them by itself at startup. You can check what was detected with `/hawn hooks`. More details: [Hooks](../integrations/hooks.md).

{% hint style="success" %}
You are ready. Next step: the [welcome setup](welcome-setup.md).
{% endhint %}
