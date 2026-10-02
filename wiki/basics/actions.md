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

20 ticks = 1 second.

```yaml
- '[send-title]: &6&lWELCOME //n &7Have fun %player%'
- '[send-title[60]]: &cOnly 3 seconds'
- '[send-actionbar[100]]: &eThis stays 5 seconds'
```

### Sounds, effects and fireworks

| Action                              | What it does                                                                                   |
| ----------------------------------- | ---------------------------------------------------------------------------------------------- |
| `[sounds]: <sound>`                 | Plays a sound to the player. See [sound names](../reference/values.md#sounds).                 |
| `[effect[<amplifier>]]: <effect>`   | Gives a potion effect for an unlimited time. `0` = level I, `1` = level II...                  |
| `[effectclear]: <effect>`           | Removes one potion effect.                                                                     |
| `[effectclearall]`                  | Removes every potion effect.                                                                   |
| `[FWLU]: <firework>`                | Launches a firework defined in `Cosmetics-Fun/Utility/Firework-List.yml`. See [Fireworks](../features/lobby-fun.md#fireworks). |

```yaml
- '[sounds]: BLOCK_NOTE_BLOCK_PLING'
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

## Combining with permissions and worlds

Actions accept the same prefixes as messages, see [Messages](message-format.md#a-line-for-some-players-only):

```yaml
- '<perm>server.vip</perm> [effect[0]]: GLOWING'
- '<world>world</world> [send-actionbar]: &aYou are in the lobby'
```

## Messages sent to everyone

Some messages are broadcast to every online player: join and quit messages, the first-join broadcast, `/broadcast`... If you put an action there, **it runs for every online player**. For example `[sounds]: ...` in a join message plays the sound to everyone, which is nice. But `[command-player]: ...` would make every player run the command, which is usually not what you want: use [join actions](../features/join-and-quit.md#join-and-quit-actions) for that.
