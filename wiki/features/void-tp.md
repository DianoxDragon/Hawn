---
description: Teleport players back to the spawn when they fall into the void.
---

# Void TP

When a player goes below a given height, Hawn teleports them to the spawn. Configuration: `Events/VoidTP.yml`.

```yaml
VoidTP:
  Enable: true
  Options:
    TP-y: 0                         # teleport when the player is at or below this Y
    Bypass-With-Permission: true    # hawn.bypass.voidtp is not teleported
    Message:
      Custom: true                  # true = Teleport.VoidTP of Messages.yml, false = the normal teleport message
      Disable: false                # true = no message
    Sounds:
      Enable: true
      Sound: BLOCK_NOTE_HARP
      Volume: 1
      Pitch: 1
    Fireworks:
      Enable: true
      Firework-List:
      - '[FWLU]: Firework1'
    Execute-Commands:
      Enable: true
      Commands:
      - '[send-title]: &cOops! //n &7Be careful'
      - '[command-console]: say %player% fell into the void'
    VoidTP-Per-World:
      Enable: true
      World-List: ...               # see below
  Custom-Spawn:
    Enable: false                   # use this spawn instead of the default spawn
    Spawn: CHANGE ME
  World:
    All_World: false
    Worlds:
    - world
```

{% hint style="warning" %}
**Since 1.18 the overworld goes down to Y = -64.** With `TP-y: 0`, players exploring caves below Y 0 are teleported to the spawn. On a normal overworld, use `TP-y: -70`. On a void or flat lobby, `0` is fine.
{% endhint %}

The spawn is the `Custom-Spawn` if enabled, else the default spawn (`Events/OnJoin.yml` → `Spawn.DefaultSpawn`). As for `/spawn`, the player needs **`hawn.command.spawn.<spawn>`**, see [Spawns](spawns.md#permissions).

`Execute-Commands` runs [actions](../basics/actions.md) after the teleport.

## Per world

`VoidTP-Per-World` gives different settings to some worlds:

```yaml
    VoidTP-Per-World:
      Enable: true
      World-List:
        world:
          Enable: true
          VoidTP: true                  # false = no teleport in this world (only the commands)
          Custom-Spawn:
            Enable: true
            Spawn: lobby
          TP-y: 0
          Execute-Commands:
            Enable: true
            Override-Default-Commands: true   # true = replaces the default commands, false = adds to them
            Commands:
            - '[command-player]: a command'
        skyblock:
          Enable: true
          VoidTP: false
          TP-y: -10
          Execute-Commands:
            Enable: true
            Override-Default-Commands: true
            Commands:
            - '[command-player]: is home'
```

The world must also be enabled in the main `World` block. With `VoidTP: false`, the player is not teleported but the commands still run: in the example, a skyblock player who falls is sent back to their island by the skyblock plugin.
