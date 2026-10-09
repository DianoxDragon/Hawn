---
description: >-
  Special lines that do something instead of sending a message: titles, sounds,
  commands, teleports, potion effects, fireworks...
---

# Actions

In every list of messages, a line can be an **action**. Hawn recognises the action by the tag at the start of the line and runs it instead of sending the text.

```yaml
message:
- '&eWelcome to the server!'
- '[send-title]: &6Welcome //n &7%player%'
- '[sounds]: ENTITY_PLAYER_LEVELUP'
- '[command-console]: give %player% cookie 1'
```

## Where can I use them?

Everywhere Hawn sends a list of lines, in particular:

* [custom commands](../features/custom-commands.md) (`CustomCommand.yml`) and `/help`;
* the `Command-List` of [custom join items](../features/custom-join-items.md);
* the [join and quit actions](../features/join-and-quit.md#join-and-quit-actions) (`Events/JoinQuitCommand.yml`);
* the [sign system](../features/lobby-fun.md#sign-system), [void TP](../features/void-tp.md) and [world change](../features/world-change.md) commands;
* the [auto broadcast](../features/autobroadcast.md) messages;
* almost every message of the `Messages/` folder.

## List of actions

{% hint style="warning" %}
Respect the syntax exactly: the tag, then **`: `** (colon + one space), then the value.
{% endhint %}

### Commands

| Action                              | What it does                                                                                     |
| ----------------------------------- | ------------------------------------------------------------------------------------------------ |
| `[command-player]: <command>`       | The player runs the command (without `/`). `%player%` is replaced by their name.                 |
| `[command-console]: <command>`      | The console runs the command (without `/`). `%player%` is replaced by the player name.           |
| `[customcommand-player]: <key>`     | Runs one of your [custom commands](../features/custom-commands.md). Use its **key** in `CustomCommand.yml` (for example `rules`), not its `/command`. |

```yaml
- '[command-player]: warp shop'
- '[command-console]: lp user %player% parent add member'
- '[customcommand-player]: discord'
```

### Teleportation

| Action                     | What it does                                                                     |
| -------------------------- | -------------------------------------------------------------------------------- |
| `[spawn]: <spawn>`         | Teleports the player to a Hawn [spawn](../features/spawns.md), immediately, without permission check. |
| `[warp]: <warp>`           | Teleports the player to a Hawn [warp](../features/warps.md), immediately, without permission check. |
| `[bungee]: <server>`       | Sends the player to another server of your BungeeCord/Velocity network. See [BungeeCord](../integrations/bungeecord.md). |

### Titles and action bar

| Action                               | What it does                                                                            |
| ------------------------------------ | --------------------------------------------------------------------------------------- |
| `[send-title]: <title>//n<subtitle>` | Sends a title. `//n` separates the title and the subtitle (the subtitle is optional). Fade in 20 ticks, stay 150 ticks, fade out 75 ticks. |
| `[send-title[<ticks>]]: <title>//n<subtitle>` | Same, but stays `<ticks>` ticks on the screen.                                 |
| `[send-actionbar]: <message>`        | Sends a message in the action bar.                                                      |
| `[send-actionbar[<ticks>]]: <message>` | Same, but keeps it `<ticks>` ticks on the screen.                                     |
| `[title]: <title>//n<subtitle>`      | Since Hawn 1.4. Sends a title with the fades of Minecraft: fade in 10 ticks, stay 70 ticks, fade out 20 ticks. |
| `[title[<in>,<stay>,<out>]]: <title>//n<subtitle>` | Since Hawn 1.4. Sends a title with your fade in, stay and fade out, in ticks. |

20 ticks = 1 second.

```yaml
- '[send-title]: &6&lWELCOME //n &7Have fun %player%'
- '[send-title[60]]: &cOnly 3 seconds'
- '[send-actionbar[100]]: &eThis stays 5 seconds'
- '[title[5,40,10]]: &a&lGO! //n &7Fast fade in, 2 seconds on the screen'
```

### Sounds, effects and fireworks

| Action                              | What it does                                                                                   |
| ----------------------------------- | ---------------------------------------------------------------------------------------------- |
| `[sound]: <sound> [volume] [pitch]` | Since Hawn 1.4. Plays a sound to the player, with an optional volume and pitch (decimals work: `0.5`, `1.5`; `1` by default). See [sound names](../reference/values.md#sounds). |
| `[sounds]: <sound>`                 | The same (the name of the action before 1.4); it also accepts the volume and the pitch now.    |
| `[effect[<amplifier>]]: <effect>`   | Gives a potion effect for an unlimited time. `0` = level I, `1` = level II...                  |
| `[effectclear]: <effect>`           | Removes one potion effect.                                                                     |
| `[effectclearall]`                  | Removes every potion effect.                                                                   |
| `[FWLU]: <firework>`                | Launches a firework defined in `Cosmetics-Fun/Utility/Firework-List.yml`. See [Fireworks](../features/lobby-fun.md#fireworks). |

```yaml
- '[sound]: BLOCK_NOTE_BLOCK_PLING'
- '[sound]: ENTITY_PLAYER_LEVELUP 0.5 1.8'
- '[effect[1]]: SPEED'
- '[effectclear]: SPEED'
- '[FWLU]: Firework1'
```

### Gamemode and ping

| Action                 | What it does                                  |
| ---------------------- | --------------------------------------------- |
| `[gamemode-survival]`  | Puts the player in survival.                  |
| `[gamemode-creative]`  | Puts the player in creative.                  |
| `[gamemode-adventure]` | Puts the player in adventure.                 |
| `[gamemode-spectator]` | Puts the player in spectator.                 |
| `[ping]`               | Sends the player their ping (message `Ping.Self` of `Messages.yml`). |

### Broadcast

| Action                   | What it does |
| ------------------------ | ------------ |
| `[broadcast]: <message>` | Since Hawn 1.4. Sends the message to every online player and to the console. The placeholders are those of the player who runs the action: `[broadcast]: &e%player% &7found the secret!` |

## Delay and chance

Since Hawn 1.4, two prefixes go in front of any line (message or action):

| Prefix                 | What it does |
| ---------------------- | ------------ |
| `[delay[<ticks>]]: `   | Runs the rest of the line `<ticks>` ticks later (20 ticks = 1 second). The other lines don't wait. |
| `[chance[<percent>]]: `| Runs the rest of the line `<percent>` % of the time (decimals work: `[chance[0.5]]:` is one time out of 200). |

```yaml
- '&7Opening the chest...'
- '[delay[40]]: [sound]: BLOCK_CHEST_OPEN'
- '[delay[40]]: [chance[10]]: [broadcast]: &6%player% &efound a rare item!'
- '[delay[40]]: &7Nothing this time.'
```

Each line has its own delay, counted from the moment the list is read: write the same delay on every line that must wait. They can be chained in any order, after `<world>` and `<perm>`.

## Combining with permissions and worlds

Actions accept the same prefixes as messages, see [Messages](message-format.md#a-line-for-some-players-only):

```yaml
- '<perm>server.vip</perm> [effect[0]]: GLOWING'
- '<world>world</world> [send-actionbar]: &aYou are in the lobby'
```

## Messages sent to everyone

Some messages are broadcast to every online player: join and quit messages, the first-join broadcast, `/broadcast`... If you put an action there, **it runs for every online player**. For example `[sounds]: ...` in a join message plays the sound to everyone, which is nice. But `[command-player]: ...` would make every player run the command, which is usually not what you want: use [join actions](../features/join-and-quit.md#join-and-quit-actions) for that.
