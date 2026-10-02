---
description: >-
  Give items when players join (server selector, profile head, books, armour)
  and use the special items: player visibility, lobby bow, fun gun.
---

# Custom join items

Custom join items (CJI) are the items placed in the player's inventory when they join: a compass to choose a server, their head to open a profile menu, a book with the rules... A right click on the item runs a list of [actions](../basics/actions.md).

The items are locked: players can't drop them, move them or lose them.

## Requirements

* `CustomJoinItem/General.yml` → `Custom-Join-Item.Enable: true`.
* The player is in one of the worlds of `General-Option.World`.
* **The player has `hawn.use.customjoinitem`.** Without it, they don't receive any item.

## General options

```yaml
Custom-Join-Item:
  Enable: true
  General-Option:
    Use_Permission_Per_Item: false
    Use_In_Creative_Mode_In_Any_Case: false
    Inventory-Click: true
    World:
      All_World: false
      Worlds:
      - world
```

| Option                              | Description                                                                                         |
| ----------------------------------- | --------------------------------------------------------------------------------------------------- |
| `Use_Permission_Per_Item`           | Each item needs its own permission: `hawn.use.cji.item.<item key>` (see below).                     |
| `Use_In_Creative_Mode_In_Any_Case`  | `false` = in creative mode, the items behave like normal items (they can be moved and dropped). `true` = they stay locked and usable in creative too. |
| `Inventory-Click`                   | Clicking the item inside the inventory also runs its actions.                                       |

## Inventory items

```yaml
  Items:
    Inventory:
      Enable: true
      Items:
        CompassSelectyourServer:          # the item key, any name you want
          Material: COMPASS
          Slot: 0
          Title: '&cServers'
          Lore:
          - '&7Right click to choose a server'
          Command-List:
          - '[command-player]: serverselector'
          - '[sounds]: BLOCK_NOTE_BLOCK_HAT'
        Player-Material:
          Material: PLAYER_HEAD
          Skull-Name: '%player%'
          Slot: 1
          Title: '&cProfile'
          Command-List:
          - '[command-player]: profile'
        Player-LobbyBow:
          Special-Items: Special-LobbyBow
          Slot: 7
        Player-Visibility:
          Special-Items: Special-HidePlayers
          Slot: 8
```

| Key            | Required | Description                                                                                                  |
| -------------- | -------- | ------------------------------------------------------------------------------------------------------------ |
| `Material`     | yes\*    | The item. Old and new names are accepted, see [Materials](../reference/values.md#materials).                 |
| `Slot`         | yes      | Inventory slot: `0`–`8` is the hotbar, `9`–`35` the rest of the inventory.                                   |
| `Title`        | no       | Display name. Colours and placeholders work.                                                                 |
| `Lore`         | no       | Lines under the name. Colours and placeholders work.                                                         |
| `Amount`       | no       | Number of items (default 1).                                                                                 |
| `Skull-Name`   | no       | With a player head material: the owner of the head. `%player%` = the player's own head.                     |
| `Data-value`   | no       | Legacy data value. Ignored on recent versions: use the full material name (`RED_WOOL`).                     |
| `Command-List` | no       | [Actions](../basics/actions.md) run on right click.                                                          |
| `Special-Items`| \*       | Replaces `Material` to give a special item: `Special-HidePlayers`, `Special-LobbyBow`, `Special-FunGun` or `Special-Book:<book>`. |

Add as many items as you want, each with a different key.

{% hint style="info" %}
To open a menu of another plugin (DeluxeMenus, a server selector...), use `[command-player]: <the command that opens the menu>`. To send the player to another server directly, use `[bungee]: <server>`.
{% endhint %}

## Armour

```yaml
    Armor:
      Helmet:
        Enable: true
        Item:
          Material: DIAMOND_HELMET
      Chestplate:
        Enable: true
        Item:
          Material: LEATHER_CHESTPLATE
          Title: '&6Lobby chestplate'
          Lore:
          - '&7Click me'
          Command-List:
          - '[command-player]: heal'
      Leggings: ...
      Boots: ...
```

The armour pieces accept the same keys as the inventory items (except `Slot`). With `Use_Permission_Per_Item`, their permissions are `hawn.use.cji.item.helmet`, `.chestplate`, `.leggings` and `.boots`.

## Books

Create the book in `Cosmetics-Fun/Utility/Book-List.yml`:

```yaml
Book-List:
  Rules:
    Title: '&bServer rules'
    Author: Dianox
    page1:
      page:
      - '&lRules'
      - ''
      - '1. Be nice'
      - '2. No cheating'
    page2:
      page:
      - 'Another page'
```

Every key except `Title` and `Author` is a page (the name doesn't matter), and every line of `page` is a line of the book. Colours and placeholders work.

Then give it as a join item with `Special-Items: Special-Book:<book>`:

```yaml
        RulesBook:
          Special-Items: Special-Book:Rules
          Slot: 4
```

## Special items

### Player visibility (`Special-HidePlayers`)

A clock that hides or shows the other players. The state is saved as the player's "player visibility" [option](player-options.md).

`CustomJoinItem/Special-HidePlayers.yml`:

```yaml
PV:
  Enable: true
  Option:
    OnJoin-ShowPlayers: true                  # players are visible when joining
    OnJoin-Priority-For-Player-Option: true   # ...unless the player chose to hide them before
    Item-Delay:
      Enable: true
      Delay: 5                                # seconds between two uses
    Inventory-Click:
      Interact-With-The-Object: true          # clicking it in the inventory also toggles
      Show-Messages: true
      Sounds: { Enable: true, Sound: NOTE_PIANO, Volume: 10, Pitch: 1 }
    Interact-With-Item:
      Sounds: { Enable: true, Sound: NOTE_PIANO, Volume: 10, Pitch: 1 }
  'OFF':                                      # item when the players are visible
    Title: '&6Invisible player &8→ &cDisabled'
    Lore: [' ', '&c&lRight click to hide players']
    Material: { Material: CLOCK, Amount: 1 }
  'ON':                                       # item when the players are hidden
    Title: '&6Invisible player &8→ &aEnabled'
    Lore: [' ', '&a&lRight click to show players']
    Material: { Material: CLOCK, Amount: 1 }
```

Like every join item, it needs `hawn.use.customjoinitem` (and `hawn.use.cji.item.<item key>` with `Use_Permission_Per_Item`, for example `hawn.use.cji.item.Player-Visibility`). The remaining delay is available with the `%timedelaypvcji%` placeholder.

{% hint style="info" %}
`hawn.event.interact.item.playervisibility` and `hawn.event.interact.item.lobbybow`, mentioned in old versions of this wiki, are not used anymore.
{% endhint %}

### Lobby bow (`Special-LobbyBow`)

A bow that teleports the player where the arrow lands. The arrow is given back automatically.

```yaml
LobbyBow:
  Enable: true
  Item:
    Title: '&6Lobby bow'
    Lore: [' ', '&c&lAaaaaahhhh']
    Material:
      Amount: 1
```

### Fun gun (`Special-FunGun`)

A blaze rod that shoots snowballs exploding into hearts and lava particles, with a cat sound.

```yaml
FunGun:
  Enable: true
  Option:
    Item-Delay:
      Enable: true
      Delay: 5          # seconds between two shots, see %timedelayfunguncji%
  Item:
    Title: '&6FunGun'
    Lore: [' ', '&c&lAaaaaahhhh']
    Material:
      Amount: 1
```

## After death

The items can be given back when the player respawns: `Events/PlayerEvents.yml` → `Death.Respawn.Player.Regive-Hawn-Custom-Join-Items`. See [Protections](protections.md#death-and-respawn).
