---
description: >-
  The accepted values for sounds, materials, potion effects, boss bars, damage
  types, gamemodes and particles.
---

# Values (sounds, colours, damage types...)

## Sounds

Use the names of the [Bukkit Sound list](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html) of your server version, for example `ENTITY_PLAYER_LEVELUP`, `BLOCK_NOTE_BLOCK_PLING`, `ENTITY_EXPERIENCE_ORB_PICKUP`.

Old names from 1.8 – 1.12 also work (`NOTE_PIANO`, `BLOCK_NOTE_HARP`, `LEVEL_UP`...): Hawn translates them for your version thanks to [XSeries](https://github.com/CryptoMorin/XSeries).

If a sound is not recognised, Hawn writes a warning in the console and plays `AMBIENT_CAVE` instead.

Volume: `1` is normal (higher values are heard from farther). Pitch: between `0.5` (low) and `2` (high), `1` is normal.

## Materials

Use the names of the [Bukkit Material list](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html): `COMPASS`, `PLAYER_HEAD`, `RED_WOOL`, `LIGHT_WEIGHTED_PRESSURE_PLATE`...

Old names also work: `SKULL_ITEM` → player head, `GOLD_PLATE` → light weighted pressure plate, `WOOL:14` → red wool...

If a material is not recognised, Hawn writes a warning in the console and uses a `BARRIER` instead.

## Potion effects

For the `[effect[<amplifier>]]: <effect>` action: `SPEED`, `SLOWNESS`, `HASTE`, `JUMP_BOOST`, `NIGHT_VISION`, `INVISIBILITY`, `GLOWING`, `LEVITATION`, `REGENERATION`, `RESISTANCE`, `FIRE_RESISTANCE`, `WATER_BREATHING`, `SATURATION`, `BLINDNESS`, `SLOW_FALLING`... Old names (`JUMP`, `SLOW`, `FAST_DIGGING`...) work too. An unknown effect is replaced by `HUNGER`.

The amplifier starts at `0` (level I).

## Gamemodes

| Number | Gamemode  |
| ------ | --------- |
| `0`    | Survival  |
| `1`    | Creative  |
| `2`    | Adventure |
| `3`    | Spectator |

## Boss bar

| Option     | Values                                                          |
| ---------- | --------------------------------------------------------------- |
| `Color`    | `BLUE`, `GREEN`, `PINK`, `PURPLE`, `RED`, `WHITE`, `YELLOW`     |
| `Style`    | `SOLID`, `SEGMENTED_6`, `SEGMENTED_10`, `SEGMENTED_12`, `SEGMENTED_20` |
| `Progress` | From `0.0` (empty) to `1.0` (full)                              |

## Titles and durations

Durations are in **ticks**: 20 ticks = 1 second. A title with `FadeIn: 20`, `Stay: 60`, `FadeOut: 20` appears in 1 s, stays 3 s and disappears in 1 s.

## Damage types

For `Events/ProtectionPlayer.yml` → `AntiDamage-Custom.Entity.Options.Damage-Type-List`. The full list for your version is on the [DamageCause page](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html). The most useful ones:

| Type                                     | Damage from...                    |
| ---------------------------------------- | --------------------------------- |
| `FALL`                                   | Falling                           |
| `VOID`                                   | The void                          |
| `ENTITY_ATTACK`, `ENTITY_SWEEP_ATTACK`   | Hits from players and mobs (PvP)  |
| `PROJECTILE`                             | Arrows, snowballs, tridents...    |
| `FIRE`, `FIRE_TICK`, `LAVA`, `HOT_FLOOR` | Fire, lava, magma blocks          |
| `DROWNING`                               | Drowning                          |
| `SUFFOCATION`                            | Being inside a block              |
| `BLOCK_EXPLOSION`, `ENTITY_EXPLOSION`    | Explosions                        |
| `STARVATION`                             | Hunger                            |
| `POISON`, `WITHER`, `MAGIC`              | Potion effects                    |
| `CONTACT`                                | Cactus, sweet berry bushes        |
| `CRAMMING`                               | Too many entities in one place    |
| `FLY_INTO_WALL`                          | Elytra crash                      |
| `LIGHTNING`                              | Lightning                         |
| `THORNS`                                 | Thorns enchantment                |
| `FALLING_BLOCK`                          | Anvils, dripstone...              |
| `DRAGON_BREATH`, `MELTING`, `DRYOUT`, `SUICIDE`, `CUSTOM` | Other causes     |

Unknown names are ignored.

## Jump pad effect

`Cosmetics-Fun/JumpPads.yml` → `Effect.Effect` uses the names of the [Bukkit Effect list](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Effect.html), for example `ENDER_SIGNAL`, `MOBSPAWNER_FLAMES`, `SMOKE`. Write the name exactly: an unknown name stops the jump pad from working (an error is written in the console).

## Firework

| Option | Values                                                                                                         |
| ------ | -------------------------------------------------------------------------------------------------------------- |
| `Type` | `BALL`, `BALL_LARGE`, `STAR`, `BURST`, `CREEPER`                                                               |
| Colours (`Colors`, `Fade`) | `AQUA`, `BLACK`, `BLUE`, `FUCHSIA`, `GRAY`, `GREEN`, `LIME`, `MAROON`, `NAVY`, `OLIVE`, `ORANGE`, `PURPLE`, `RED`, `SILVER`, `TEAL`, `WHITE`, `YELLOW` |
