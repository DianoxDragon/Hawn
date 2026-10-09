---
description: >-
  An animated header and footer for the player list (Tab key), chosen by world
  and by permission.
---

# Tab list

Hawn adds a header and a footer to the player list, with as many animations as you want. Since Hawn 1.4, other tab lists can be shown in some worlds or to the players with a permission, one file per tab list like the scoreboards (see [Per world and per permission](#per-world-and-per-permission)).

`Tablist/Tablist.yml`:

```yaml
Tablist:
  enable: true
  refresh-time-ticks: 2          # how often the header/footer are refreshed
  header:
    enabled: true
    message:
    - ''
    - '&7Thank you to choose &b&lHawn'
    - ''
    - '&7You are &e%player%'
    - '{anim_website}'
    - ''
    - '{anim_separator}'
  footer:
    enabled: true
    message:
    - '{anim_separator}'
    - ''
    - '{anim_hawntitle}'
Animations:
  Enable: true
  separator:
    refresh-time-ticks: 2        # ticks between two frames
    text:
    - '&e&l>> &8&m-------------------&r &e&l<<'
    - '&7&l>&e&l> &8&m-------------------&r &e&l<&7&l<'
    - '&7&l>> &8&m-------------------&7 &7&l<<'
  website:
    refresh-time-ticks: 60
    text:
    - '&7Discord&8: &eLink 1'
    - '&7Shop&8: &eLink 2'
    - '&7Website&8: &eLink 3'
```

## Header and footer

Each line of `message` is a line of the header or footer. Colours, hex colours, [Hawn placeholders](../reference/placeholders.md) and PlaceholderAPI placeholders work, and they are updated every `refresh-time-ticks` ticks.

## Per world and per permission

Since Hawn 1.4, the tab lists work like the [scoreboards](scoreboards.md): every `.yml` file of the `Tablist` folder (except `Tablist.yml`) is a tab list, with its own worlds, its own permission, its own header and footer and its own animations. `Tablist.yml` stays the default one: it is shown to the players who match none of the files.

`Tablist/staff.yml`:

```yaml
enable: true
priority: 10                       # the highest priority is checked first
permission: true                   # needs hawn.tablist.staff (the name of the file)
World:
  All_World: true
  Worlds: []
header:
  enabled: true
  message:
  - '&c&lSTAFF &8- &e%player%'
  - '{anim_separator}'
footer:
  enabled: true
  message:
  - '&7Ping&8: &e%ping% ms'
```

`Tablist/nether.yml`:

```yaml
enable: true
priority: 0
permission: false                  # every player
World:
  All_World: false
  Worlds:
  - world_nether
  - world_the_end
header:
  enabled: true
  message:
  - '&7You are in &c%player_world%'
  - '{anim_flames}'
# no footer: the footer of Tablist.yml is shown
Animations:                        # the animations of this tab list
  flames:
    refresh-time-ticks: 10
    text:
    - '&c&m-------&6&m-------&e&m-------'
    - '&6&m-------&e&m-------&c&m-------'
```

At each refresh, Hawn gives each player the first tab list that:

1. is enabled (`enable`), **and**
2. is set for the player's world (`World`; without `World`, every world), **and**
3. with `permission: true`, the player has **`hawn.tablist.<name of the file>`** (`hawn.tablist.staff` for `staff.yml`). With `permission: false`, no permission is needed.

The files are checked from the highest `priority` to the lowest; with the same priority, in the alphabetical order of their names. With the examples above, a staff member sees `staff.yml` even in the nether.

* A tab list without `header` (or without `footer`) shows the one of `Tablist.yml`. With `enabled: false`, it shows nothing there.
* `{anim_<name>}` takes the animation of the file first, then the one of `Tablist.yml`: the animations of `Tablist.yml` work in every tab list.
* The world names ignore the case. A player who changes world or gets a permission sees the new tab list at the next refresh.
* Operators have every permission, so they see the tab lists with `permission: true`.
* Add, change or delete a file, then `/hawn reload` (no restart needed).

The examples `staff.yml` and `nether.yml` are created once, with `enable: false`: nothing changes until you turn one on. Delete them if you don't need them, they don't come back.

## Animations

1. Create an animation under `Animations` with a name, a `refresh-time-ticks` and a list of `text` frames.
2. Put `{anim_<name>}` in the header or the footer.

```yaml
Tablist:
  header:
    message:
    - '{anim_rainbow}'
Animations:
  Enable: true
  rainbow:
    refresh-time-ticks: 10
    text:
    - '&c&lMY SERVER'
    - '&6&lMY SERVER'
    - '&e&lMY SERVER'
    - '&a&lMY SERVER'
    - '&b&lMY SERVER'
```

Every animation has its own speed: the `website` animation above changes every 3 seconds (60 ticks) while `separator` moves every 2 ticks.

{% hint style="info" %}
Changes are applied with `/hawn reload`, the custom tab lists too.
{% endhint %}

To turn the tab list off, set `Tablist.enable: false`. The header or the footer alone can be turned off with their own `enabled`.
