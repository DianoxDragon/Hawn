---
description: >-
  Protect the players (damage, hunger, items, death) and the lobby (build,
  item frames, interactions, buckets), with WorldGuard support.
---

# Protections

Hawn can make your lobby indestructible and keep players safe. Every protection uses the usual [`World`, bypass and `WorldGuard` blocks](../basics/per-world-options.md).

{% hint style="success" %}
Admins can ignore the build and item protections with `/hawn build` instead of giving themselves every bypass permission. See [Admin tools](admin-tools.md#build-mode).
{% endhint %}

## Player protections

### Anti-damage

`Events/ProtectionPlayer.yml`:

```yaml
Anti-Damage:
  Enable: true
  Custom:
    Enable: false                 # false = block every damage, true = only the types below
  Bypass-With-Permission: false   # true = hawn.bypass.antidamage takes damage normally
  WorldGuard: ...
  World: ...
AntiDamage-Custom:
  Entity:
    Options:
      Damage-Type-List:           # used when Custom.Enable is true
      - FALL
      - VOID
      - ENTITY_ATTACK
      - ...
```

With `Custom.Enable: true`, only the damage types listed in `Damage-Type-List` are blocked. Remove `FALL` to keep fall damage, remove `ENTITY_ATTACK` to allow PvP, etc. The full list of types is on the [Values](../reference/values.md#damage-types) page.

### Hunger

`Events/PlayerEvents.yml`:

```yaml
Keep:
  Food:
    Enable: true                  # players never get hungry
    Bypass-With-Permission: false # true = hawn.bypass.foodkeep gets hungry normally
    World: ...
```

### Items

`Events/PlayerEvents.yml`:

```yaml
Items:
  Drop:
    Disable: true                 # players can't drop items
    Bypass: true                  # hawn.event.playeritem.bypass.drop
    WorldGuard: ...
    World: ...
  PickUp:
    Disable: true                 # players can't pick up items
    Bypass: true                  # hawn.event.playeritem.bypass.pickup
    WorldGuard: ...
    World: ...
  Move:
    Disable: true                 # players can't move items in their inventory
    Bypass: true                  # hawn.event.playeritem.bypass.moveitem
    WorldGuard: ...
    World: ...
  Damage-Item:
    Disable: true                 # tools and armour never lose durability
    Bypass: true                  # hawn.event.playeritem.bypass.damageitem
    World: ...
  Clear-Drops-On-Death:
    Enable: true                  # nothing is dropped when a player dies
    Bypass: true                  # hawn.event.death.bypass.cleardrop
    World: ...
```

### Death and respawn

```yaml
Death:
  Respawn:
    Enable: true                  # skip the death screen
    Use_Permission: false         # true = only for hawn.event.respawn
    Player:
      Respawn-After: 5            # ticks before the automatic respawn
      Teleport-Spawn: true        # teleport to a spawn after respawning
      Custom-Spawn:
        Enable: true
        Spawn: CHANGE ME          # the spawn to use, else the default spawn (or the spawn of the player's group)
      World: ...
      Regive-Hawn-Custom-Join-Items:
        Enable: true              # give the join items back
        World: ...
  Death-Message:
    Disable: true                 # no death message in the chat
    World: ...
```

### Gamemode, off hand and mounts

```yaml
Keep-Gamemode:
  Enable: false                   # nobody can change gamemode
  Bypass-With-Permission: true    # hawn.bypass.keepgamemode can
  World: ...
Block-Off-Hand:
  Enable: true                    # the off hand (F key) can't be used
  Bypass-With-Permission: false   # hawn.bypass.block.offhand can use it
  World: ...
Block-Mount:
  Enable: true                    # players can't ride horses, boats, minecarts...
  Bypass-With-Permission: false   # hawn.event.bypass.player.antimount can
  World: ...
```

{% hint style="warning" %}
`Keep-Gamemode` blocks **every** gamemode change in the listed worlds, including `/gamemode` and the gamemode set on join or on world change. Give the bypass permission to your staff.
{% endhint %}

## World protections

### Build

`Events/ProtectionWorld.yml`:

```yaml
Protection:
  Construct:
    Anti-Place:
      Enable: true
      Bypass: true                # hawn.event.construct.bypass.place can build
      Message: true               # send Protection.Anti-Place of Messages.yml
      Block-Exception:
        Enable: false
        Method: WHITELIST
        Armor_Stand: false        # true = armour stands can always be placed
        Materials:
        - DIRT
      WorldGuard: ...
      World: ...
    Anti-Break:
      Enable: true
      Bypass: true                # hawn.event.construct.bypass.break can break
      Message: true
      Block-Exception:
        Enable: false
        Method: WHITELIST
        Materials:
        - DIRT
      WorldGuard: ...
      World: ...
```

**Block exceptions** let some blocks escape the protection:

* `Method: WHITELIST`: the listed materials **can** be placed / broken, everything else is protected.
* `Method: BLACKLIST`: **only** the listed materials are protected, everything else can be placed / broken.

Anti-break also protects armour stands from being hit.

### Item frames, paintings and interactions

```yaml
  HagingBreakByEntity:            # paintings and item frames can't be broken
    Enable: true
    Bypass: true                  # hawn.bypass.HagingBreakByEntity
    WorldGuard: ...
    World: ...
  PlayerInteractEntity-ItemFrame: # items can't be taken from / put in item frames
    Enable: true
    Bypass: true                  # hawn.bypass.PlayerInteractEntity
    WorldGuard: ...
    World: ...
  PlayerInteract-Items-Blocks:    # blocks players can't use
    Enable: true
    Bypass: true                  # hawn.event.construct.bypass.protectionitemblocks
    Options:
      CHEST: true
      ENDER_CHEST: true
      FURNACE: true
      OAK_DOOR: true
      OAK_TRAPDOOR: true
      LEVER: true
      OAK_BUTTON: true
      OAK_SIGN: false
      ...
    WorldGuard: ...
    World: ...
  Anti-Bucket-Use:                # water and lava buckets can't be used
    Enable: true
    Bypass: true                  # hawn.bypass.protection.buckets
    WorldGuard: ...
    World: ...
```

In `PlayerInteract-Items-Blocks.Options`, set a block to `false` to allow it (for example the buttons of your parkour). The list contains chests, doors, gates, trapdoors, furnaces, anvils, beds, buttons, levers, hoppers, droppers, dispensers, note blocks, comparators, beacons, brewing stands, enchanting tables, minecarts, boats and sweet berry bushes.

The doors, fence gates and trapdoors of the newer woods (`CRIMSON_`, `WARPED_`, `MANGROVE_`, `CHERRY_`, `BAMBOO_`, `PALE_OAK_`) and the copper doors and trapdoors (`COPPER_DOOR`, `COPPER_TRAPDOOR`: one option for all their oxidation and waxed variants) are protected even when they are missing from your file. Add them with `false` to allow them.

{% hint style="warning" %}
Only the blocks already present in `Options` are supported: adding a new line has no effect. Wood types that came after 1.14 (crimson, warped, mangrove, cherry, bamboo, pale oak) and copper doors are not in the list. Protect them with WorldGuard if needed.
{% endhint %}

### Weather, fire, explosions...

These are in `Events/WorldEvent.yml`, see [World events](world-events.md).
