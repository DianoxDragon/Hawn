---
description: >-
  The World / All_World blocks, the bypass options and the WorldGuard
  whitelist/blacklist used by most Hawn features.
---

# Per-world options and WorldGuard

Most Hawn features share the same building blocks. Once you know them, you can read any Hawn file.

## The `World` block

```yaml
World:
  All_World: false
  Worlds:
  - world
  - world_nether
```

* `All_World: true`: the feature works in **every** world. `Worlds` is ignored.
* `All_World: false`: the feature works **only** in the worlds listed in `Worlds`.

By default every feature is limited to `world` and `world_nether`. The [welcome setup](../getting-started/welcome-setup.md) replaces these lists by the lobby world you choose.

{% hint style="info" %}
World names are the names of the world folders, and they are case sensitive. At startup Hawn prints `Invalid world in <file>` in the console for every world that doesn't exist.
{% endhint %}

**Example**: block building everywhere except in the `creative` world.

```yaml
Anti-Place:
  Enable: true
  World:
    All_World: false
    Worlds:
    - world
    - world_nether
    - minigames
```

## Bypass options

Many features have one of these options:

| Option                          | Meaning                                                                                                   |
| ------------------------------- | --------------------------------------------------------------------------------------------------------- |
| `Bypass: true`                  | Players with the bypass permission of the feature are not affected. With `false`, nobody can bypass it.    |
| `Bypass-With-Permission: true`  | Same thing.                                                                                               |
| `Use_Permission: true`          | The opposite: only players **with** the permission get the feature (double jump, join potion effects...). |

The permission of each feature is given on its page and in the [Permissions](../reference/permissions.md) list. For example, `Events/ProtectionWorld.yml` → `Anti-Break` → `Bypass: true` lets players with `hawn.event.construct.bypass.break` break blocks.

{% hint style="success" %}
Admins who want to build in a protected lobby can also use `/hawn build`: it toggles a build bypass for them without any permission change. See [Admin tools](../features/admin-tools.md#build-mode).
{% endhint %}

## The `WorldGuard` block

When [WorldGuard 7+](../integrations/hooks.md#worldguard) is installed, the protections can also depend on the region the player is in:

```yaml
WorldGuard:
  Enable: false
  Method: WHITELIST
  Regions:
  - region1
  - whatyouwant
```

* `Enable`: use the regions for this feature.
* `Regions`: the WorldGuard region names.
* `Method`:
  * **`WHITELIST`**: the protection applies **only inside** the listed regions.
  * **`BLACKLIST`**: the protection applies **everywhere except** inside the listed regions.

The `World` block is still checked first: the protection must be active in the world.

**Example**: no block breaking in the whole lobby world, except in the `parkour-builder` region.

```yaml
Anti-Break:
  Enable: true
  Bypass: true
  WorldGuard:
    Enable: true
    Method: BLACKLIST
    Regions:
    - parkour-builder
  World:
    All_World: false
    Worlds:
    - lobby
```

The features with a `WorldGuard` block are: anti-damage, item drop, pick-up and move, anti-place, anti-break, item frames, hanging entities, block/item interactions, buckets, shears, block burn, fire spread and explosions. See [Protections](../features/protections.md) and [World events](../features/world-events.md).
