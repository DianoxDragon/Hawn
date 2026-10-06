---
description: >-
  Everything that happens when a player joins or leaves: messages, MOTD,
  titles, boss bar, sounds, fireworks, inventory, gamemode, potion effects,
  actions.
---

# Join and quit

When a player joins, Hawn can do a lot of things. Each of them has its own `Enable` option and its own [`World` block](../basics/per-world-options.md), so you can keep only what you need.

| What                                   | File                                         |
| -------------------------------------- | -------------------------------------------- |
| Join / quit messages, join MOTD        | `Messages/<language>/General.yml`            |
| Teleport, inventory, gamemode, title, boss bar, effects... | `Events/OnJoin.yml`      |
| Fireworks, lightning strikes           | `Cosmetics-Fun/OnJoin.yml`                   |
| Actions run on join / quit             | `Events/JoinQuitCommand.yml`                 |
| Items given on join                    | `CustomJoinItem/`, see [Custom join items](custom-join-items.md) |
| Teleport to the spawn                  | see [Spawns](spawns.md#teleport-on-join)     |

{% hint style="info" %}
"New player" or "first join" means a player who never played on **this** server before.
{% endhint %}

## Join messages

`Messages/<language>/General.yml` → `General.On-join.Join-Message`:

```yaml
General:
  On-join:
    Join-Message:
      Enable: true
      Just-Simply-Disable-All-Join-Messages: false
      Disable-Default-Message: true
      Silent-Staff-Join: true
      Disable-For-New-Players: false
      Broadcast-To-Console: true
      Per-Group:
        Options:
          Enable: true
          Disable-Any-Messages-On-Join: false
        Groups:
          Owner:
          - '&cPLEASE WELCOME a owner'
          Admin:
          - unlimited groups of course
      Per-World:
        Options:
          Enable: false
          Disable-Any-Other-Messages-On-Join: false
          Only-Broadcast-Messages-In-The-World: false
        Worlds:
          world:
          - '&ctest1'
      Messages:
      - '&7[&a+&7] %player%'
      World:
        All_World: true
        Worlds:
        - world
```

| Option                                    | Description                                                                                                   |
| ----------------------------------------- | ------------------------------------------------------------------------------------------------------------- |
| `Enable`                                  | `false` = Hawn doesn't touch the join message at all (the vanilla "joined the game" stays).                    |
| `Just-Simply-Disable-All-Join-Messages`   | `true` = no join message at all, neither vanilla nor Hawn.                                                     |
| `Disable-Default-Message`                 | Removes the vanilla yellow message.                                                                           |
| `Silent-Staff-Join`                       | Players with `hawn.event.silentjoin` join without any message.                                                 |
| `Disable-For-New-Players`                 | No join message for new players (they have their own [first join broadcast](#first-join)).                    |
| `Broadcast-To-Console`                    | Also writes the message in the console.                                                                       |
| `Messages`                                | The message sent to everyone. `%player%` and `%player_displayname%` are the joining player.                    |
| `World`                                   | The worlds where the message is sent (the world the player joins in).                                         |

### Per group

With `Per-Group.Options.Enable: true`, a player with `hawn.on-join.custommessage.<group>` also triggers the lines of `Groups.<group>`. For example `hawn.on-join.custommessage.Owner` for the `Owner` group above. You can create as many groups as you want. "Groups" are just permission names, they don't depend on your permission plugin.

`Disable-Any-Messages-On-Join: true` removes the normal `Messages` so that only the group message is sent.

### Per world

With `Per-World.Options.Enable: true`, players who join in a world listed in `Per-World.Worlds` trigger the lines of that world. `Disable-Any-Other-Messages-On-Join: true` sends only these lines and no other message.

## Quit messages

`General.On-Quit.Quit-Message` works exactly the same way, with:

* `Just-Simply-Disable-All-Quit-Messages`, `Disable-Default-Message`, `Broadcast-To-Console`;
* `Silent-Staff-Quit` → `hawn.event.silentquit`;
* `Per-Group` → `hawn.on-quit.custommessage.<group>`;
* `Per-World` → `Disable-Any-Other-Messages-On-Quit`.

## Join MOTD

The MOTD is a private message sent only to the player who joins.

`Messages/<language>/General.yml` → `Spawn.On-join`:

```yaml
Spawn:
  On-join:
    Enable: true
    Messages:
    - '&8&m<=====-------<&r &6Hawn &8&m>-------=====>'
    - '&cHello %player%'
    - '&cDon''t forget to see our rules'
    - '&8&m<=====-------<&r &6Hawn &8&m>-------=====>'
    World:
      All_World: true
      Worlds:
      - world
    Per-World:
      Options:
        Enable: false
        Disable-All-The-Others-Motd: false
      Worlds:
        world:
        - '&ctest1 - motd'
```

* `Per-World`: an extra MOTD for players joining in a listed world. The player needs `hawn.on-join.custom-motd-per-world.<world>`. `Disable-All-The-Others-Motd: true` replaces the normal MOTD instead of adding to it.

## First join

Still in `Spawn.On-join`:

```yaml
    First-Join:
      Broadcast:
        Enable: true
        Broadcast-To-The-Console: true
        Messages:
        - '&eWelcome %player% to the server'
      Motd:
        Enable: true
        Both-Motd: false
        Messages:
        - '&cWelcome %player%'
```

* `Broadcast`: a message sent to everyone when a new player joins.
* `Motd`: a private message for the new player. With `Both-Motd: true` they also receive the normal MOTD.

To send new players to a different spawn, see `FirstJoin-Spawn` on the [Spawns](spawns.md#the-default-spawn) page.

## Title, action bar and boss bar

`Events/OnJoin.yml`. Each one has a `First-Join` version (new players) and a `Join` version (players who already played).

```yaml
Title:
  Enable: true
  First-Join:
    Enable: true
    FadeIn: 20         # ticks, 20 ticks = 1 second
    Stay: 150
    FadeOut: 20
    Title: '&6Welcome %player%'
    SubTitle: '&eThanks to choose &6Hawn'
    World: ...
  Join:
    Enable: true
    ...
Action-Bar:
  Enable: true
  First-Join:
    Enable: true
    Message: '&6Welcome %player%'
    Time-Stay: 150     # ticks
    World: ...
  Join: ...
```

```yaml
Boss-Bar:
  Enable: true
  First-Join:
    Enable: true
    Message: '&6Welcome %player% &e!!'
    Color: BLUE
    Style: SEGMENTED_10
    Progress: 1.0
    Time:
      Keep-Bar: false          # true = the bar stays until the player leaves
      If-not:
        Time-Stay: 150         # ticks before the bar disappears
        Swith-To-OnJoin-BossBar:
          Enable: true         # then show the "Join" bar
          Keep-The-BossBar: false
  Join:
    Enable: true
    Message: '&6Hello %player%'
    Color: PURPLE
    Style: SOLID
    Progress: 0.7
    Time:
      Keep-Bar: false
      If-not:
        Time-Stay: 150
  World:
    All_World: false
    Keep-BossBar-For-Theses-Worlds: true   # show the bar again when coming back to these worlds
    Worlds:
    - world
```

The boss bar is removed when the player goes to a world that is not listed. Colours, styles and progress values: see [Values](../reference/values.md#boss-bar).

## Sound

```yaml
Event:
  OnJoin:
    Sounds:
      Enable: true
      Sound: BLOCK_NOTE_HARP
      Volume: 1
      Pitch: 1
      World: ...
```

The sound is played to the joining player after the teleport. [Sound names](../reference/values.md#sounds).

## Fireworks and lightning

`Cosmetics-Fun/OnJoin.yml`:

```yaml
Cosmetics:
  Firework:
    Enable: true
    Bypass: false              # true = players with hawn.event.onjoin.bypass.firework get none
    Options:
      First-Join-Only: false
      Firework-List:
      - '[FWLU]: Firework1'    # names from Cosmetics-Fun/Utility/Firework-List.yml
    World: ...
  Lightning-Strike:
    Enable: true
    Bypass: false              # true = hawn.event.onjoin.bypass.lightningstrike gets none
    Options:
      First-Join-Only: false
      Number-Of-Strikes: 3
    World: ...
```

The lightning strikes are only visual effects, they don't hurt anyone. They don't work if `Events/WorldEvent.yml` → `World.Weather.Disable.LightningStrike` is enabled in the same world (Hawn warns you in the console).

To create your own fireworks, see [Fireworks](lobby-fun.md#fireworks).

## Inventory, chat, health and XP

`Events/OnJoin.yml`:

```yaml
Inventory:
  Force-Selected-Slot:
    Enable: true
    Slot: 1                  # hotbar slot selected on join (0 to 8)
    World: ...
  Clear:
    Enable: true
    Bypass: true             # hawn.event.onjoin.bypass.clearinv keeps their inventory
    Options:
      Armor: true
      Inventory: true
    World: ...
Chat:
  Clear:
    Enable: true
    Bypass: true             # hawn.event.onjoin.bypass.clearchat keeps their chat
    Lines-To-Clear: 150
    World: ...
Restore:
  Food:
    Enable: true
    Value: 20                # 20 = full
    Bypass-With-Permission: false   # hawn.bypass.foodrestore
    World: ...
  Health:
    Enable: true
    Value: 20.0              # 20 = 10 hearts, at most the max health of the player
    Bypass-With-Permission: false   # hawn.bypass.healthrestore
    World: ...
XP:
  Enable: true
  Options:
    Exp:
      Enable: true
      Value: 0.3             # progress of the XP bar, from 0.0 to 1.0
      World: ...
    Level:
      Enable: true
      Value: 10
      World: ...
```

{% hint style="info" %}
The inventory is cleared **before** the [custom join items](custom-join-items.md) are given.
{% endhint %}

## Gamemode, fly and speed

```yaml
Change-Gamemode:
  Enable: true
  Value: 2                   # 0 survival, 1 creative, 2 adventure, 3 spectator
  Bypass-With-Permission: true   # hawn.bypass.gamemodeonjoin keeps their gamemode
  World: ...
Fly:
  Enable: true               # players with hawn.onjoin.fly can fly when they join
  World: ...
Speed:
  Enable: true
  Value: 2                   # walk speed, 1 to 10
  Option:
    Priority-For-Player-Option: true
  World: ...
FlySpeed:
  Enable: true
  Value: 1                   # fly speed, 1 to 10
  Option:
    Priority-For-Player-Option: true
  World: ...
```

`Priority-For-Player-Option`: when the player changed their own speed with [`/option speed`](player-options.md) and has `hawn.onjoin.playeroption.speed` (or `.flyspeed`), their own value is kept instead of `Value`.

{% hint style="warning" %}
Fly on join and the [double jump](lobby-fun.md#double-jump) both use the flight ability of the player. Hawn warns you in the console if both are enabled: choose one of them for the same players.
{% endhint %}

## Potion effects

```yaml
Potion-Effect:
  BLINDNESS:
    Enable: true
    Use_Permission: false    # true = only players with hawn.onjoin.potion.blindness
    Duration-Second: 2
    Amplifier: 2
    World: ...
  JUMP:
    Enable: true
    Use_Permission: false    # true = only players with hawn.onjoin.potion.jump
    Duration-Second: 2
    Amplifier: 2
    World: ...
```

For other effects, use the `[effect[<amplifier>]]: <effect>` [action](../basics/actions.md) in the join actions below.

## Join and quit actions

`Events/JoinQuitCommand.yml` runs a list of [actions](../basics/actions.md) (or private messages) for the player who joins or quits:

```yaml
JoinCommand:
  Enable: true
  Options:
    New:                     # new players only
      Enable: false
      Commands:
      - '[command-console]: give %player% compass 1'
      World: ...
    No-New:                  # players who already played
      Enable: true
      Commands:
      - '[ping]'
      - '<world>world</world> <perm>server.vip</perm> [command-player]: kit vip'
      World: ...
QuitCommand:
  Enable: false
  Commands:
  - '[command-console]: say %player% left'
  World: ...
```

{% hint style="info" %}
If `New` is disabled, new players run the `No-New` list.
{% endhint %}

## Keeping options between sessions

What a player had when they left (gamemode, vanish, fly, speed, visibility...) can be restored when they come back. See [Player options](player-options.md#keep-options-between-sessions).
