---
description: >-
  Your own menus: a server selector, a profile, a shop of links... One file per
  menu in Menus/, opened by a command, a join item or an action.
---

# Menus

Since Hawn 1.5, the `Menus/` folder holds your menus: one file per menu, the name of the file is the name of the menu (`Menus/servers.yml` is the menu `servers`). A menu opens with its own command, with a [join item](custom-join-items.md), or with the `[menu]: <name>` [action](../basics/actions.md) anywhere in Hawn.

The first time, Hawn writes two examples in the language of the server:

* `Menus/servers.yml`: the server selector opened by the compass of the join items, with the number of players of each server.
* `Menus/profile.yml`: the profile opened by the head of the join items and by `/profile`. It shows what a menu can do: the head and the placeholders of the player, the fly and the speed with three items on the same slot (on, off, or locked without the permission), the player hider with an action for the left click and one for the right click, a condition to click with its message, live items, `[menu]`, `[refresh]` and `[back]`.

{% hint style="info" %}
The format looks like DeluxeMenus, with the words of Hawn (`Title`, `Material`, `Lore`...) and its [actions](../basics/actions.md). Every option is optional, except `Items`.
{% endhint %}

## A menu

```yaml
# Menus/servers.yml
Title: '&8Choose a server'         # colours, placeholders, <glyph:...> with Nexo
Rows: 3                             # 1 to 6 rows of 9 slots
#Type: HOPPER                       # or a hopper (5 slots), DISPENSER or DROPPER (9 slots)
Open:
  Commands: [servers, serveurs]     # /servers and /serveurs open it
  Permission: hawn.menu.servers     # needed to open it (none when not written)
  Worlds: [world]                   # only in these worlds (all when not written)
  Actions:                          # run when it opens
  - '[sounds]: BLOCK_CHEST_OPEN'
Close-Actions: []                   # run when it is closed
Refresh: 40                         # ticks between two updates of the items with Update: true
Fill: GRAY_STAINED_GLASS_PANE       # the empty slots (or an item: Material, Name...)
Items:
  survival:
    Slot: 11
    Material: GRASS_BLOCK
    Name: '&aSurvival'
    Lore:
    - '&7%bungee_survival% players'
    Update: true
    Actions:
    - '[bungee]: survival'
```

| Option          | What it does |
| --------------- | ------------ |
| `Title`         | The title of the menu, with colours and placeholders. |
| `Rows`          | 1 to 6 (3 by default). |
| `Type`          | `HOPPER`, `DISPENSER` or `DROPPER` instead of a chest. |
| `Open.Commands` | Commands that open the menu, with or without `/`. They are checked before the commands of the server and of the other plugins. |
| `Open.Permission` | The permission to open it. Without it, everyone can (the menu can still hide items with `View-Requirements`). |
| `Open.Worlds`   | The worlds where it can be opened. |
| `Open.Actions`  | [Actions](../basics/actions.md) run when it opens. |
| `Close-Actions` | Actions run when the player closes the menu (Escape, `[close]`, `/hawn reload`), not when another menu replaces it: a sound, a message, a command... |
| `Refresh`       | Ticks between two updates of the items with `Update: true` (20 to 40 is good). Only these slots are built again. |
| `Fill`          | The item of the empty slots: a material, or a section like an item. |

## The items

```yaml
Items:
  shop:
    Slots: [0-8, 18, '20-22']      # or Slot: 4
    Material: DIAMOND
    Amount: 1
    Name: '&bShop'
    Lore:
    - '&7Your rank: %rank%'
    Glow: true
    Item-Model: 'minecraft:emerald'  # 1.21.4+: the model of a resource pack
    Custom-Model-Data: 12
    Update: true
    Priority: 1
    View-Requirements:
    - 'permission: hawn.shop'
    Click-Requirements:
      Conditions:
      - '%player_level% >= 10'
      Deny:
      - '&cYou need the level 10'
    Actions:
    - '[command-player]: shop'
    Right-Actions:
    - '[menu]: shop-info'
```

| Option | What it does |
| ------ | ------------ |
| `Slot` / `Slots` | The slot (0 is the top left), or several slots: numbers and ranges (`0-8`). The same item in each slot. |
| `Material` | A [material](../reference/values.md), `head:<...>` (see below) or `nexo:<id>` (an item of [Nexo](../integrations/hooks.md#nexo)). Placeholders work (`head:%player%`). |
| `Amount`, `Name`, `Lore` | The amount, the name and the lore, with colours and placeholders. |
| `Glow` | The item shines like an enchanted item (nothing is shown in its lore). |
| `Item-Model` | 1.21.4+: the model of the item (`namespace:key`), from your resource pack, Nexo included. |
| `Custom-Model-Data` | The custom model data of the item. |
| `Update` | The item is built again every `Refresh` ticks while the menu is open (the number of players, a placeholder that changes...). |
| `Priority` | Several items on the same slot: the first one (the lowest number) whose `View-Requirements` are met is shown. Without a priority, the order of the file. |
| `View-Requirements` | The item is shown only when every condition is met. |
| `Click-Requirements` | The actions run only when every condition is met. `Deny`: the actions run otherwise (a message...). A list of conditions alone works too. |
| `Actions` | The [actions](../basics/actions.md) of a click. |
| `Left-Actions`, `Right-Actions`, `Shift-Left-Actions`, `Shift-Right-Actions`, `Middle-Actions`, `Drop-Actions` | The actions of this kind of click. When one is written, it replaces `Actions` for this click. |

The players can never take, put or move an item in a menu of Hawn. A chest, or the menu of another plugin, with the same title does nothing: Hawn recognizes its menus by themselves.

### Conditions

| Condition | Met when |
| --------- | -------- |
| `permission: <permission>` | The player has the permission. |
| `!permission: <permission>` | The player doesn't have it. |
| `world: <world>` | The player is in this world (several worlds: `world: lobby, hub`). |
| `!world: <world>` | The player is not in this world. |
| `<text> <operator> <text>` | The comparison is true once the placeholders are replaced: `==`, `!=`, `>`, `<`, `>=`, `<=`, `contains`, `!contains`. Two numbers are compared as numbers, texts without the case: `%bungee_survival% < 100`, `%player_world% == lobby`. Since Hawn 1.5.2, a value with an operator in it (a prefix `&8>> `) no longer cuts the condition. |

### Heads

| Material | The head |
| -------- | -------- |
| `head:%player%` | The player who opens the menu. |
| `head:<name>` | A player. Connected: their skin at once. Not connected: their skin is found in the background, the menu shows it as soon as it arrives (a head without a skin in the meantime). |
| `head:<Base64>` | A texture (the `Value` of the heads of minecraft-heads.com), also a texture URL or its hash. Prepared once, no request to Mojang. |

Hawn never asks Mojang for a skin on the main thread: a menu with heads opens at once.

## Animations

Like the [tab list](tablist.md#animations), a menu has its animations: a name, a speed and a list of frames. Put `{anim_<name>}` in the `Title`, a `Name`, a line of `Lore`, a `Material`, or the `Fill`: the frames follow one another while the menu is open.

```yaml
Title: '{anim_title}'
Animations:
  title:
    refresh-time-ticks: 20       # ticks between two frames
    text:
    - '&6&lSERVERS'
    - '&e&lSERVERS'
  join:
    refresh-time-ticks: 10
    text:
    - '&eClick to join'
    - '&6&lClick to join'
  border:
    refresh-time-ticks: 10
    text:
    - RED_STAINED_GLASS_PANE
    - ORANGE_STAINED_GLASS_PANE
    - YELLOW_STAINED_GLASS_PANE
Fill:
  Material: '{anim_border}'
  Name: ' '
Items:
  survival:
    Slot: 13
    Material: GRASS_BLOCK
    Lore:
    - '{anim_join}'
```

* Each animation has its own speed. Every player sees the same frame at the same time.
* Only the slots with an animation are sent again, and only when their frame changes: the other items don't move, and nothing is rebuilt.
* The title of an open chest changes since Minecraft 1.20. Before, the menu shows the first frame of the title (the items are animated on every version). A frame every second or more is enough for a title.
* The frames can hold placeholders and colours.
* [`/hawn debug`](admin-tools.md#debug) lists an `{anim_<name>}` without its animation.

The examples of Hawn use them: "Click to join" blinks in `servers.yml`, and the title of `profile.yml` changes colour.

## Actions for the menus

| Action | What it does |
| ------ | ------------ |
| `[menu]: <name>` | Opens a menu (in a join item, a custom command, a sign, a message, another menu...). |
| `[close]` | Closes the menu. |
| `[refresh]` | Builds the open menu again (after a change). |
| `[back]` | Opens the menu that was open before this one, or closes it when there is none. Since Hawn 1.5.2, it goes back step by step: A, then B, then C, `[back]` opens B, then A. |

## Commands and permissions

| Command | Permission | What it does |
| ------- | ---------- | ------------ |
| `/hawn menu open <menu>` | `hawn.admin.command.menu` | Opens a menu for you (with its permission and its worlds). |
| `/hawn menu open <menu> <player>` | `hawn.admin.command.menu` | Opens it for a player. |
| `/hawn menu list` | `hawn.admin.command.menu` | The menus of `Menus/`. |

`/hawn reload` reads `Menus/` again and closes the open menus. [`/hawn debug`](admin-tools.md) lists the mistakes of the menus: a slot outside the menu, an unknown material, a condition written wrong, a `[menu]:` to a menu that doesn't exist, a `nexo:` item without Nexo.

## The server selector

`Menus/servers.yml` is opened by the compass of the [join items](custom-join-items.md) (`[menu]: servers`, on the new installations) and by `/servers`. Write the names of your servers (those of your proxy) in its `[bungee]:` actions and in `%bungee_<server>%`: the number of players changes while the menu is open. See [BungeeCord / Velocity](../integrations/bungeecord.md).
