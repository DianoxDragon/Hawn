---
description: >-
  Automatic announcements at regular intervals, in the chat, as titles, in the
  action bar and in a boss bar.
---

# Auto broadcast

Hawn has four independent auto broadcasts, all configured in `AutoBroadcast.yml`:

| Section      | Shows the messages...  | Permission (when `Use-Permission-To-Get-Messages: true`) |
| ------------ | ---------------------- | -------------------------------------------------------- |
| `Messages`   | in the chat            | `hawn.get.autobroadcast`                                 |
| `Titles`     | as titles              | `hawn.get.autobroadcast_titles`                          |
| `Action-Bar` | in the action bar      | `hawn.get.autobroadcast_ab`                              |
| `BossBar`    | in a boss bar          | `hawn.get.autobroadcastbb`                               |

Players can hide all of them with [`/option autobc`](player-options.md).

## Common options

Each section starts with the same options:

```yaml
Config:
  Messages:
    Enable: true
    Random: false                          # true = random order, false = in the file order
    Interval: 600                          # ticks between two messages (20 ticks = 1 s)
    Use-Permission-To-Get-Messages: false
    World:
      All_World: false
      Worlds:
      - world
```

`/hawn reload` applies the changes of `AutoBroadcast.yml` (a section turned on or off, new messages, a new interval) without a restart.

In a new configuration, the chat messages are on and the titles, the action bar and the boss bar are off.

## Chat messages

```yaml
  Messages:
    Enable: true
    Random: false
    Interval: 600
    Broadcast-To-Console: false
    Use-Permission-To-Get-Messages: false
    Custom-Header-Footer:
      Header:
        Enable: true
        messages:
        - ''
        - '°·..·°¯°·._.·  &e&lANNOUNCEMENT&r ·._.·°¯°·..·°'
        - ''
      Footer:
        Enable: true
        messages:
        - ''
    Options:
      Auto-Center: true                    # centre every line
    World: ...
    messages:
      firstmessage:
        message:
        - The best way to support me is to put 5 stars on spigot
      vote:
        message:
        - '&eVote for the server!'
        - 'json:{"text":"§6§l[CLICK HERE]","clickEvent":{"action":"open_url","value":"https://example.com/vote"}}'
        - '[sounds]: ENTITY_EXPERIENCE_ORB_PICKUP'
      nethermessage:
        message:
        - You are in the nether
        world_list:                        # optional: only for players in these worlds
        - world_nether
```

* Each entry of `messages` is one announcement, with any key name.
* The header and footer are added around every announcement.
* Each line supports colours, placeholders, JSON, `<perm>`, `<world>` and [actions](../basics/actions.md) like `[sounds]:`.
* `world_list` sends this announcement only to players whose world name contains one of the listed names.

## Titles

```yaml
  Titles:
    Enable: false
    Random: false
    Interval: 1200
    Use-Permission-To-Get-Messages: false
    Options-Default:
      FadeIn: 20
      Stay: 30
      FadeOut: 20
    World: ...
    messages:
      default:
        Title:
          Message: '&e&lANNOUNCEMENT'
        SubTitle:
          Message: '&7This plugin is full of possibilities'
      custom:
        FadeIn: 20                         # overrides Options-Default
        Stay: 150
        FadeOut: 20
        Sound: ENTITY_PLAYER_LEVELUP       # optional
        Title:
          Message: '&c&lANNOUNCEMENT'
        SubTitle:
          Message: '&7You can manage options on your title %player%'
      only-subtitle:
        SubTitle:
          Message: '&7Only a subtitle'
```

`Title` and `SubTitle` are both optional.

## Action bar

```yaml
  Action-Bar:
    Enable: false
    Random: false
    Interval: 600
    Use-Permission-To-Get-Messages: false
    Options-Default:
      Time-Stay: 120                       # ticks on screen
    World: ...
    messages:
      default:
        Message: '&eDefault Action-Bar &7(autobroadcast)'
      custom:
        Time-Stay: 60
        Sound: BLOCK_NOTE_BLOCK_PLING      # optional
        Message: '&6custom Action-Bar &7(autobroadcast)'
```

## Boss bar

```yaml
  BossBar:
    Enable: false
    Random: false
    Interval: 600
    Use-Permission-To-Get-Messages: false
    Options-Default:
      Color: PURPLE
      Style: SOLID
      Progress: 0.7
    World: ...
    messages:
      default:
        Message: '&eDefault message bossbar without settings'
      totalcustom:
        Message: '&cBossbar - %player%'
        Color: BLUE
        Style: SEGMENTED_20
        Progress: 1.0
        Sound: BLOCK_NOTE_BLOCK_BELL       # optional
```

The boss bar stays until the next message replaces it. Colours, styles and progress values: see [Values](../reference/values.md#boss-bar).

{% hint style="info" %}
The join boss bar of `Events/OnJoin.yml` and the auto broadcast boss bar use the same bar: if you enable both, the auto broadcast replaces the join bar.
{% endhint %}
