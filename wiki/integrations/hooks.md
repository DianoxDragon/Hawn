---
description: How Hawn works with PlaceholderAPI, LuckPerms and WorldGuard.
---

# PlaceholderAPI, LuckPerms, WorldGuard and other hooks

Hawn detects the supported plugins by itself at startup. `/hawn hooks` shows what was detected (✔ or ✗).

## How the detection works

`general.yml`:

```yaml
Plugin:
  Use:
    Hook:
      PlaceholderAPI:
        Enable: false
        Keep-The-Option: false
      WorldGuard:
        Enable: false
        Keep-The-Option: false
```

* When the plugin is installed, Hawn sets `Enable` to `true` and uses it.
* When the plugin is not installed, Hawn sets `Enable` back to `false`, unless `Keep-The-Option` is `true`.

You don't need to edit these lines yourself. To stop using a hook, remove the plugin and **restart** the server. The hooks are checked at startup and on `/hawn reload`, not at every message.

## PlaceholderAPI

[PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) adds thousands of placeholders (`%luckperms_prefix%`, `%vault_eco_balance%`, `%server_online%`...). With PlaceholderAPI installed, you can use them everywhere in Hawn: messages, scoreboards, tab list, join items, titles...

The other way works too (since Hawn 1.4.6): the other plugins can use the placeholders of Hawn with `%hawn_<placeholder>%`, for example `%hawn_tps%` or `%hawn_player_join_number%`. See [In other plugins](../reference/placeholders.md#in-other-plugins).

Don't forget to download the expansions you use: `/papi ecloud download <expansion>` then `/papi reload`.

## LuckPerms

[LuckPerms](https://luckperms.net/) is optional, but it is the permission plugin Hawn is tested with, and Hawn uses it directly in one case:

* **Paper 1.21.7 and newer**: the maintenance bypass (`hawn.maintenance.bypass`) and the full server bypass (`hawn.join.full`) are checked before the player exists. Hawn reads them from LuckPerms. With another permission plugin, only the operators have them (the whitelists still work). See [Maintenance mode](../features/admin-tools.md#maintenance-mode).

Everything else only needs a permission plugin, whichever it is. To show the ranks of LuckPerms in Hawn texts (chat format, scoreboards, tab list...), install PlaceholderAPI and its `luckperms` expansion, then use `%luckperms_prefix%` and `%luckperms_suffix%`.

Wildcards such as `hawn.emoji.*` work with LuckPerms.

## WorldGuard

[WorldGuard](https://enginehub.org/worldguard) **7 or newer** lets you enable or disable Hawn protections per region, with a whitelist or a blacklist. See [Per-world options and WorldGuard](../basics/per-world-options.md#the-worldguard-block).

With an older WorldGuard, the console says `WorldGuard 7+ is required, the region features are disabled`.

## MVdWPlaceholderAPI

Not supported since 1.4.9: MVdWPlaceholderAPI has not been updated since 2020, and the premium plugins of Maximvdw (FeatherBoard...) use PlaceholderAPI since 2021. Use [PlaceholderAPI](#placeholderapi). Hawn removes its section from `general.yml`.

## WorldEdit

Not a hook strictly speaking: WorldEdit is only needed by the `/1`, `/2`, `/c` and `/p` [shortcuts](../reference/commands.md#worldedit-shortcuts).
