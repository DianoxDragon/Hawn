---
description: Compile Hawn yourself from the source code.
---

# Building from source

Hawn is open source: [github.com/DianoxDragon/Hawn](https://github.com/DianoxDragon/Hawn).

## Requirements

Only a **JDK 17 or newer**. Gradle is downloaded by the wrapper.

## Build

```bash
./gradlew build
```

On Windows:

```bash
gradlew.bat build
```

The plugin is created in `build/libs/Hawn-<version>.jar`.

The jar is compiled for Java 8 against the Spigot 1.16.5 API, so it loads on every supported server. XSeries, FastBoard and bStats are shaded and relocated in `fr.dianox.hawn.libs`.

## Check against the latest API

```bash
./gradlew checkLatestApi
```

This compiles Hawn against the newest Paper API (a JDK 25 is downloaded automatically) to detect removed or changed methods before a new Minecraft version breaks the plugin.

## Project layout

| Folder                                      | Content                                                |
| ------------------------------------------- | ------------------------------------------------------ |
| `src/main/java/fr/dianox/hawn/command`      | Commands (`CommandManager` registers them)             |
| `src/main/java/fr/dianox/hawn/event`        | Event listeners (join, chat, protections...)           |
| `src/main/java/fr/dianox/hawn/modules`      | Features (join items, scoreboard, tab, world manager, setup...) |
| `src/main/java/fr/dianox/hawn/utility/config` | Configuration files and their default values         |
| `src/main/java/fr/dianox/hawn/hook`         | PlaceholderAPI, WorldGuard, MVdW, BattleLevels         |
| `src/stubs`                                 | Compile-time stubs for plugins without a Maven repository (not shipped) |
| `wiki`                                      | This wiki (synchronised with GitBook)                  |

The default configuration files are not resources: each file is created by a class of `utility/config/configs` (`Config.set(...)` calls) the first time Hawn starts.

## License

Hawn is distributed under the [GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.html), see the [`LICENSE`](https://github.com/DianoxDragon/Hawn/blob/master/LICENSE) file. You can use, modify and share Hawn, including on a commercial server, as long as any version you distribute stays under the GPL v3 with its source code.
