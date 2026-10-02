---
description: How Hawn works with PlaceholderAPI, WorldGuard, MVdWPlaceholderAPI and BattleLevels.
---

# PlaceholderAPI, WorldGuard and other hooks

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
      MVdWPlaceholderAPI:
        Enable: false
        Keep-The-Option: false
      WorldGuard:
        Enable: false
        Keep-The-Option: false
      BattleLevels:
        Enable: false
        Keep-The-Option: false
```

* When the plugin is installed, Hawn sets `Enable` to `true` and uses it.
* When the plugin is not installed, Hawn sets `Enable` back to `false`, unless `Keep-The-Option` is `true`.

You don't need to edit these lines yourself. To stop using a hook, remove the plugin and **restart** the server.

## PlaceholderAPI

[PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) adds thousands of placeholders (`%luckperms_prefix%`, `%vault_eco_balance%`, `%server_online%`...). With PlaceholderAPI installed, you can use them everywhere in Hawn: messages, scoreboards, tab list, join items, titles...

Don't forget to download the expansions you use: `/papi ecloud download <expansion>` then `/papi reload`.

## WorldGuard

[WorldGuard](https://enginehub.org/worldguard) **7 or newer** lets you enable or disable Hawn protections per region, with a whitelist or a blacklist. See [Per-world options and WorldGuard](../basics/per-world-options.md#the-worldguard-block).

With an older WorldGuard, the console says `WorldGuard 7+ is required, the region features are disabled`.

## MVdWPlaceholderAPI

Adds the MVdW placeholders (`{onlineplayers}`...) to Hawn messages.

{% hint style="warning" %}
MVdWPlaceholderAPI only works when at least one premium plugin of Maximvdw (FeatherBoard, AnimatedNames...) is installed. Without one, it spams the console. In that case, remove MVdWPlaceholderAPI.
{% endhint %}

## BattleLevels

Adds the `%h_battlelevels_...%` placeholders, see [Placeholders](../reference/placeholders.md#battlelevels).

## WorldEdit

Not a hook strictly speaking: WorldEdit is only needed by the `/1`, `/2`, `/c` and `/p` [shortcuts](../reference/commands.md#worldedit-shortcuts).
