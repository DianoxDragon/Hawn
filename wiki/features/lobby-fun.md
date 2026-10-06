---
description: >-
  Jump pads, double jump, coloured signs, emoji signs, clickable action signs
  and custom fireworks.
---

# Lobby fun: jump pads, double jump, signs

## Jump pads

Put a **pressure plate on top of a block**: players who walk on it are launched forward.

`Cosmetics-Fun/JumpPads.yml`:

```yaml
JumpPads:
  Enable: true
  Options:
    Block: REDSTONE_BLOCK     # the block under the plate
    Plate: GOLD_PLATE         # the plate (GOLD_PLATE = light weighted pressure plate)
    Height: 1                 # vertical strength
    Length: 3                 # forward strength
  Sounds:
    Enable: true
    Play-for-all-players: true
    Sound: NOTE_PIANO
    Volume: 10
    Pitch: 1
  Effect:
    Enable: true
    Effect: ENDER_SIGNAL
    Pitch: 10
  Send-Message:
    Enable: true
    Messages:
    - '%prefix% &eWhoosh!'
  Cooldown:
    Enable: true
    Ticks: 60                 # time before the same player can use a pad again
  World:
    All_World: false
    Worlds:
    - world
```

Set `Use_Permission: true` under `JumpPads:` to reserve the jump pads to players with `hawn.fun.jumppads` (add the line yourself if your file was generated before Hawn 1.3). The cooldown message is `LaunchPad.Cant-Use-Cooldown` in `Messages.yml`.

## Double jump

Players press the jump key twice to make a double jump.

`Cosmetics-Fun/DoubleJump.yml`:

```yaml
DoubleJump:
  Enable: true
  Double:
    Enable: true
    Use_Permission: true      # only players with hawn.fun.doublejump.double
    Sounds:
      Enable: true
      Sound: NOTE_PIANO
      Volume: 10
      Pitch: 1
    World:
      All_World: false
      Worlds:
      - world
```

* Players can turn it off and on with [`/option doublejump`](player-options.md).
* It doesn't work in creative and spectator, nor when the player's fly option is on.

{% hint style="warning" %}
The double jump uses the "allow flight" ability of the player to detect the second jump. Don't give fly on join (`Events/OnJoin.yml` → `Fly`) to the same players, and be aware that anti-cheat plugins may need an exception.
{% endhint %}

## Coloured signs and emoji signs

`Events/OtherFeatures.yml`:

```yaml
ColorSign:
  Enable: true                # & colour codes on signs, for hawn.sign.color
  World: ...
EmojiSign:
  Enable: true                # :heart: and the other emojis on signs, for hawn.sign.emoji
  World: ...
SignSystem:
  Enable: true
```

Emoji signs use the emojis of `Cosmetics-Fun/Utility/Emojis-List.yml` (and their permission, see [Chat](chat.md#emojis)).

## Sign system

Clickable signs that run [actions](../basics/actions.md): a "Click to play" sign, a warp sign, a rules sign...

**1. Define the sign** in `Cosmetics-Fun/Utility/Sign-List.yml`:

```yaml
Sign-List:
  Parkour:
    Text:                     # the 4 lines written on the sign
    - '&8[&aParkour&8]'
    - '&7Click to'
    - '&7start'
    - ''
    Event:                    # actions run on right click
    - '[warp]: parkour'
    - '[sounds]: ENTITY_ENDERMAN_TELEPORT'
    - '&aGood luck!'
```

**2. Place a sign** and write on its first line `[SS-<name>]`, for example `[SS-Parkour]`. You need `hawn.sign.command`. Hawn replaces the text by the `Text` lines and remembers the sign (in the same file, under `Signs`).

**3. Give the permission to click it:** `hawn.sign.interact.<name>`, for example `hawn.sign.interact.Parkour`.

Breaking a sign of the system needs `hawn.sign.delete`.

{% hint style="warning" %}
The sign system works with every sign: all the woods, standing, on a wall or hanging.
{% endhint %}

## Fireworks

Named fireworks are defined in `Cosmetics-Fun/Utility/Firework-List.yml` and launched with the `[FWLU]: <name>` [action](../basics/actions.md): on join, on void TP, with `/btcast`, in a custom command...

```yaml
Firework-List:
  Firework1:
    Options:
      Amount: 2               # number of rockets
      Height: 3               # spawn height above the player
      Flicker: false
      Trail: false
      Type: BALL              # BALL, BALL_LARGE, STAR, BURST, CREEPER
      Instant-explode: false  # explode immediately
      Power: 3                # flight duration
      Colors:
      - YELLOW
      - RED
      Fade:
      - GREEN
      - RED
```

Colours: `AQUA`, `BLACK`, `BLUE`, `FUCHSIA`, `GRAY`, `GREEN`, `LIME`, `MAROON`, `NAVY`, `OLIVE`, `ORANGE`, `PURPLE`, `RED`, `SILVER`, `TEAL`, `WHITE`, `YELLOW`.
