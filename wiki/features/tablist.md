---
description: An animated header and footer for the player list (Tab key).
---

# Tab list

Hawn adds a header and a footer to the player list, with as many animations as you want.

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
Changes are applied with `/hawn reload`.
{% endhint %}

To turn the tab list off, set `Tablist.enable: false`. The header or the footer alone can be turned off with their own `enabled`.
