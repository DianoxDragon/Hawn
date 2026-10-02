---
description: Send players to other servers and show the player count of your network.
---

# BungeeCord / Velocity

Hawn talks to your proxy with the standard `BungeeCord` plugin channel. Nothing has to be installed on the proxy.

* **BungeeCord / Waterfall**: works out of the box.
* **Velocity**: keep `bungee-plugin-message-channel = true` in `velocity.toml` (the default).

## Send a player to another server

Use the `[bungee]: <server>` [action](../basics/actions.md). `<server>` is the name of the server in your proxy configuration.

```yaml
# CustomJoinItem/General.yml - a compass that sends to the survival server
CompassSurvival:
  Material: COMPASS
  Slot: 0
  Title: '&aSurvival'
  Command-List:
  - '[bungee]: survival'
```

It works in custom commands, join items, signs, join actions...

## Player count

| Placeholder           | Value                                   |
| --------------------- | --------------------------------------- |
| `%bungee_total%`      | Players on the whole network            |
| `%bungee_<server>%`   | Players on one server, e.g. `%bungee_survival%` |

```yaml
# Scoreboard
text:
- '&7Network: &e%bungee_total%'
- '&7Survival: &e%bungee_survival%'
```

The counts are refreshed every 5 seconds. Plugin messages need a player online on the lobby: when the lobby is empty, the values are not updated (and are `0` right after a restart).

## Between several lobbies

To share the player options, the last position and the scoreboard choice between your lobbies, use [MySQL](../features/database.md).
