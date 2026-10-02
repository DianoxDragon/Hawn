---
description: >-
  Weather, time, fire, explosions, leaves, mob spawning, shears and portals,
  per world.
---

# World events

`Events/WorldEvent.yml` controls what the worlds themselves do. Each option has its own [`World` block](../basics/per-world-options.md), and some have a `WorldGuard` block.

## Weather

```yaml
World:
  Weather:
    Disable:
      Weather:
        Enable: true          # it never rains
        World: ...
      ThunderChange:
        Enable: true          # no thunderstorm
        World: ...
      LightningStrike:
        Disable: true         # no lightning (this also blocks the join lightning effect)
        World: ...
```

The `/sun`, `/rain` and `/thunder` commands change the weather of your current world, see [Commands](../reference/commands.md#weather-and-time).

## Always day / always night

```yaml
  Time:
    Always-Day:
      Enable: true
      World:
        All_World: false
        Worlds:
        - world
    Always-Night:
      Enable: true
      World:
        All_World: false
        Worlds:
        - worldtest
```

Hawn regularly sets the time back to day (or night) in the listed worlds. A **restart** is needed after a change.

{% hint style="info" %}
You can also simply stop the daylight cycle with the vanilla game rule after setting the time you want (`/gamerule doDaylightCycle false`).
{% endhint %}

## Fire and explosions

```yaml
  Burn:
    Disable:
      Burn-Block:
        Disable: true         # blocks don't burn
        WorldGuard: ...
        World: ...
      BlockIgnite-FireSpread:
        Disable: true         # fire doesn't spread (lighting a fire is still possible)
        WorldGuard: ...
        World: ...
  Explosion:
    Disable:
      Explosion:
        Disable: true         # TNT, creepers... don't explode at all
        WorldGuard: ...
        World: ...
```

## Blocks

```yaml
  Blocks:
    Disable:
      Leave-Decay:
        Disable: true         # leaves never decay
        World: ...
      Block-Fade:
        Disable: true         # ice/snow don't melt, farmland doesn't dry
        World: ...
```

## Mob spawning

```yaml
  Disable:
    Spawning-Monster-Animals:
      Disable: true           # no mob can spawn
      World: ...
```

{% hint style="warning" %}
This blocks **every** creature spawn in the listed worlds (except armour stands): natural spawns, spawn eggs, and the entities spawned by other plugins (NPCs, pets, mounts...). If an NPC plugin doesn't work in your lobby, disable this option for that world.
{% endhint %}

## Shears

```yaml
No-Shears:
  Enable: true                # players can't use shears (on sheep...)
  Bypass: true                # hawn.bypass.world.event.shears can
  WorldGuard: ...
  World: ...
```

## Portals

```yaml
DenyEntityTravelPortal:
  Enable: true                # mobs and items can't go through portals
  World: ...
```
