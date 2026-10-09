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

Optional hooks: PlaceholderAPI, WorldGuard 7+, MVdWPlaceholderAPI.

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

### 1.2 (Tentative 2)

**Compatibility**
- Minecraft 1.16.5 up to 1.21.x and 26.x, on Spigot and Paper, with a single jar
- No more NMS/reflection hacks: titles, action bars, tab list, ping and scoreboard use the API
  (scoreboard through the up-to-date [FastBoard](https://github.com/MrMicky-FR/FastBoard))
- Up-to-date [XSeries](https://github.com/CryptoMorin/XSeries) for materials, sounds, potions and particles
- Official bStats library, WorldGuard 7+ only
- Commons Lang/IO are not required anymore (recent servers do not ship them)

**Project**
- Gradle build (no more hard-coded IDE paths), dependencies shaded and relocated
- `./gradlew checkLatestApi` compiles the plugin against the newest Paper API
- GitHub Actions build

**New**
- Hex colours `&#RRGGBB` (and the existing `#<RRGGBB>`) in every message, and in the chat with
  the `hawn.use.chatcolor.chat.hex` permission
- Every Hawn menu is locked: items can't be taken, put or dragged anymore
- The update check runs asynchronously and only reports a *newer* version

**Welcome setup**
- Opens for a player made operator while online (not only when joining)
- Does not reopen once finished, nor on a player who just left
- `/setspawn <name>` really sets the default spawn at the last step, and only for admins during the setup
- The "Continue" button of the last step ends the setup

**Fixes**
- The version detection considered every server above 1.16 as a 1.8 server
- Items could be taken from the `/emoji` menu
- Player heads of online players reuse their profile instead of asking Mojang for the skin each time
  a menu is opened (it caused "Couldn't look up profile properties" timeouts)
- Every player shared the same boss bar
- World folders were built with Windows paths (`\`), the world manager failed on Linux
- Emojis/anti-swear words containing regex characters (like `:)`) made the chat crash, and
  the anti-swear replacement is now case-insensitive like the detection
- Placeholders whose value contains `$` or `\` (prefix, display name...) made messages crash
- `%checkupdatehawn%` made a web request on the main thread each time it was displayed
- The mount protection used the drop bypass option on "All_World"
- Per-player boss bars and titles are cleaned when a player leaves

### 1.1.6 BETA

- New welcome setup for the admins: language, default world and spawn
- New void world generator, available when creating a world
- World Manager (`/hw` and its menu) reworked: creation and import with a world type and a generator
- Tab completion for `/hawn` and the player names of the commands
- Admin messages reworked (English and French)
