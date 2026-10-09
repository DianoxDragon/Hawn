---
description: >-
  Unlimited animated scoreboards, chosen by world and by permission, with
  changing lines and scrolling text.
---

# Scoreboards

Hawn shows a sidebar scoreboard to the players. You can create as many scoreboards as you want: each one is a file in `plugins/Hawn/Scoreboard/`, with its own worlds and its own permission. They are animated and flicker-free (Hawn uses [FastBoard](https://github.com/MrMicky-FR/FastBoard)).

To turn off all the scoreboards, set `Scoreboard.Enable: false` in `Scoreboard-General.yml` and restart the server.

## Who sees which scoreboard?

When a player joins or changes world, Hawn looks at the scoreboard files and picks the first one that:

1. is enabled in the player's world (`World` block), **and**
2. the player has the permission for: **`hawn.scoreboard.<file name without .yml>`**.

For the two default files, the permissions are:

* `hawn.scoreboard.scoreboard.default`
* `hawn.scoreboard.scoreboard.worldnetherbecausewelikethat`

{% hint style="info" %}
To avoid long permissions, rename the files: `lobby.yml` → `hawn.scoreboard.lobby`. **Restart** the server after adding, renaming or removing a file.
{% endhint %}

A player who has chosen a scoreboard with `/scoreboard set` keeps it (see below).

## A scoreboard file

`Scoreboard/scoreboard.default.yml`:

```yaml
title:
- '&f&l> &3&lHawn&f&l <'
- '&f&l> &3&lHaw&f&l <'
- '&3&lHawn'
- '&b&lHawn'
text:
- ' '
- '&7Welcome &e%player%'
- ' '
- '{CH_infoloc}'
- '{CH_infoloc2}'
- ' '
- '&7Your latency'
- '&e%ping% ms'
- ' '
- '{SC_info}'
- ' '
- '&7Thanks to use &e&lHawn'
updater:
  title: 5            # ticks between two frames of the title
  scoreboard: 5       # ticks between two refreshes of the lines
World:
  All_World: false
  Worlds:
  - world
changeableText:
  infoloc:
    text:
    - '&e%player_x% %player_y% %player_z%'
    - '&7Exp: &e%player_level%'
    - '&7Time: &e%gettime%'
    interval: 120     # ticks between two texts
  infoloc2:
    text:
    - '&7Health: &e%player_health% &6&lHP'
    - '&7Food: &e%player_food_level%'
    interval: 120
scroller:
  info:
    text: '&7Welcome on this server! This server is running on &eHawn&r  -'
    width: 27         # visible characters
    spaceBetween: 2   # spaces between two loops
    update: 3         # ticks between two moves
```

### title

A list of frames. Hawn shows them one after the other every `updater.title` ticks. Put a single line for a static title.

### text

The lines of the scoreboard, from top to bottom (15 lines maximum, a limit of Minecraft). Colours, [Hawn placeholders](../reference/placeholders.md) and PlaceholderAPI placeholders work. Lines are refreshed every `updater.scoreboard` ticks.

### Changing lines: `{CH_<name>}`

A line containing `{CH_infoloc}` is **replaced** by the texts of `changeableText.infoloc`, which change every `interval` ticks. Put the tag alone on its line: the whole line is replaced.

### Scrolling text: `{SC_<name>}`

A line containing `{SC_info}` is replaced by the text of `scroller.info`, scrolling from right to left. Use it for long messages.

{% hint style="success" %}
The lines are read from the file at every refresh: changes to `text`, `changeableText` and `scroller` texts are visible immediately, without reload. New files and changes of `World`, `updater`, `interval` or `update` need a restart.
{% endhint %}

## /scoreboard

| Command                          | Permission                                        | Description                                                              |
| -------------------------------- | ------------------------------------------------- | ------------------------------------------------------------------------ |
| `/scoreboard`                    | `hawn.command.scoreboard.toggle`                  | Hides / shows your scoreboard.                                           |
| `/scoreboard list`               | `hawn.scoreboard-list`                            | Lists the scoreboards you can use.                                       |
| `/scoreboard set <scoreboard>`   | `hawn.scoreboard.command.set` + `hawn.scoreboard.<scoreboard>` | Shows another scoreboard (file name without `.yml`). |
| `/scoreboard keep`               | `hawn.scoreboard-keep-scoreboard-change`          | Keeps your chosen scoreboard when you change world or reconnect.         |

Every sub-command also needs `hawn.command.scoreboard.toggle`.

`Commands/Scoreboard.yml`:

```yaml
Scoreboard:
  Enable: true
  Option:
    Keep-Scoreboard-Change: true    # allow /scoreboard keep
  Disable-Message: true
DISABLE_THE_COMMAND_COMPLETELY: false
```

## Disabling the scoreboards

Don't give any `hawn.scoreboard.<name>` permission, or delete the files of the `Scoreboard` folder. Operators have every permission: if they don't want the scoreboard, they can hide it with `/scoreboard`.

{% hint style="warning" %}
Hawn replaces the sidebar of the player. If another plugin also shows a sidebar, keep only one of them.
{% endhint %}
