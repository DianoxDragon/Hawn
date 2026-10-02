---
description: What to check when you update Hawn, especially from 1.1.x to 1.2.
---

# Updating from an older version

## How to update

1. Stop the server.
2. **Make a copy of `plugins/Hawn`** (just in case).
3. Replace the old Hawn jar by the new one in `plugins`.
4. Start the server.

Your configuration files are kept: Hawn only creates the files that don't exist yet.

## From 1.1.x to 1.2

### Compatibility

* Hawn 1.2 supports **1.16.5 up to 1.21.x and 26.x**, Spigot and Paper, with a single jar.
* **1.8 → 1.16.4 are not supported anymore.** Stay on 1.1.x for these versions.
* **WorldGuard 7+** is required for the region features. WorldGuard 6 is not supported anymore.
* Commons Lang and Commons IO are not needed anymore (recent servers don't ship them).

### Your files

The configuration files of 1.1.6 and 1.2 have the same format, you can keep your `plugins/Hawn` folder as it is.

A few things you may want to change after the update:

* **Material and sound names**: old names (`SKULL_ITEM`, `GOLD_PLATE`, `NOTE_PIANO`, `BLOCK_NOTE_HARP`...) still work, Hawn translates them for your server version. You don't have to rename them, but new names (`PLAYER_HEAD`, `LIGHT_WEIGHTED_PRESSURE_PLATE`, `BLOCK_NOTE_BLOCK_HARP`...) are easier to read.
* **Data values** (`Data-value`) are ignored on 1.13+ servers: use the full material name instead (`RED_WOOL` rather than `WOOL` + `Data-value: 14`).
* **Void TP height**: since 1.18 the overworld goes down to Y = -64. With `TP-y: 0`, players in deep caves are teleported to the spawn. Use a lower value (for example `-70`) in `Events/VoidTP.yml`. See [Void TP](../features/void-tp.md).
* **Hex colours**: you can now use `&#RRGGBB` everywhere, in addition to `#<RRGGBB>`. See [Messages, colours and formatting](../basics/message-format.md#hex-colours).

### The welcome setup

If your installation has no `plugins/Hawn/StockageInfo/Setup.lock` file, the [welcome setup](welcome-setup.md) opens when an admin joins. If your server is already configured, click the barrier (**I don't need a setup**): it will never be shown again.

### What's new

See the [changelog](../help/changelog.md) for the full list of changes and fixes.
