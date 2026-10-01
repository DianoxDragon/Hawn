# Hawn

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e261810ad5ca401897e184101142b15e)](https://app.codacy.com/app/DianoxDragon/Hawn?utm_source=github.com&utm_medium=referral&utm_content=DianoxDragon/Hawn&utm_campaign=Badge_Grade_Dashboard)

A plugin hub/lobby

## Supported versions

| Server             | Versions                                   |
|--------------------|--------------------------------------------|
| Spigot / Paper     | **1.16.5 → 1.21.x → 26.x**                 |
| Java               | 8+ (whatever your server version requires) |

The plugin does not use any NMS code anymore: everything goes through the Bukkit/Spigot API,
so the same jar works on every supported version.

Optional hooks: PlaceholderAPI, WorldGuard 7+, MVdWPlaceholderAPI, BattleLevels.

## Building

Only a JDK 17+ is needed, Gradle is downloaded by the wrapper.

```bash
./gradlew build
```

The plugin jar is created in `build/libs/Hawn-<version>.jar`.

To check that the code still compiles against the newest Paper API (a JDK 25 is downloaded automatically):

```bash
./gradlew checkLatestApi
```

## Changes in 1.2.0

- Compatible with Minecraft 1.16.5 up to 1.21.x and 26.x (Spigot and Paper)
- Gradle build (no more hard-coded IDE paths), dependencies shaded and relocated
- Removed every NMS/reflection hack: titles, action bars, tab list, ping and scoreboard now use the API
  (scoreboard through the up-to-date [FastBoard](https://github.com/MrMicky-FR/FastBoard))
- Up-to-date [XSeries](https://github.com/CryptoMorin/XSeries) for materials, sounds, potions and particles
- Official bStats library, WorldGuard 7+ only
- Commons Lang/IO are not required anymore (they are not shipped by recent servers)
- Hex colours `&#RRGGBB` (and the existing `#<RRGGBB>`) in every message, and in the chat with
  the `hawn.use.chatcolor.chat.hex` permission
- The update check runs asynchronously and only reports a *newer* version
- Fixes:
  - the version detection considered every server above 1.16 as a 1.8 server
  - every player shared the same boss bar
  - world folders were built with Windows paths (`\`), the world manager failed on Linux
  - emojis/anti-swear words containing regex characters (like `:)`) made the chat crash, and
    the anti-swear replacement is now case-insensitive like the detection
  - placeholders whose value contains `$` or `\` (prefix, display name...) made messages crash
  - `%checkupdatehawn%` made a web request on the main thread each time it was displayed
  - the mount protection used the drop bypass option on "All_World"
  - the setup menu tried to reopen on players who had just left
  - per-player boss bars and titles are cleaned when a player leaves
