# Hawn - All-In-One hub / lobby plugin

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/ae299dfa7f23483c80c17417f239fcf7)](https://app.codacy.com/gh/DianoxDragon/Hawn/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)

The most powerful and overkill lobby / hub plugin... and can fit any server type. Spawns, join messages, hotbar items, scoreboards, tab lists, chat, protections, jump pads, a world manager... and almost everything can be configured per world.​.. per permission... per wishes.

## Download

If you wish to download the plugin, please go to the [spigot page](https://www.spigotmc.org/resources/hawn-all-in-one-hub-lobby-plugin.66907/)

## Supported versions

| Server             | Versions                                   |
|--------------------|--------------------------------------------|
| Spigot / Paper     | **1.16.5 → 26.x**                          |
| Java               | 8+ (whatever your server version requires) |

The plugin does not use any NMS code anymore: everything goes through the Bukkit/Spigot API,
so the same jar works on every supported version.

Optional hooks: PlaceholderAPI, WorldGuard 7+, WorldEdit, MVdWPlaceholderAPI, LuckPerms.

## Building

Only a JDK 17+ is needed, Gradle is downloaded by the wrapper.

```bash
./gradlew build
```

The plugin jar is created in `build/libs/Hawn-<version>.jar`. The Paper part of the plugin (`src/paper`: the Paper chat
and connection events, MiniMessage) is compiled against the Paper API with a JDK 25, downloaded automatically.

To check that the code still compiles against the newest Paper API (a JDK 25 is downloaded automatically):

```bash
./gradlew checkLatestApi
```

## Changelog

[Click to know more](https://hawn.gitbook.io/hawn/help/changelog)
