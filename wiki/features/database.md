---
description: >-
  What Hawn stores about players, and how to share it between several lobbies
  with MySQL.
---

# Player data and MySQL

Hawn stores some information about each player: name, UUID, first and last join date, number of connections, last position, gamemode, vanish state, chosen scoreboard and [player options](player-options.md). The IP only when you ask for it, see below.

## IP of the players

The IP of the players is personal data (GDPR): Hawn doesn't keep it by default. `general.yml`:

```yaml
Plugin:
  Players:
    Save-IP: false
```

* `false` (default): the IP is not written in the player data (YAML or MySQL), and the one written by an older version is erased at the next join of the player.
* `true`: the IP is written at every join, as before 1.3.

`/ip <player>`, `/checkaccount` and the `%player_ip%` placeholder still work in both cases: they read the IP of the online player, not the saved one.

## YAML (default)

Without MySQL, the data is saved in `plugins/Hawn/StockageInfo/YamlPlayer/<uuid>.yml`, one file per player. Nothing to configure.

## MySQL

With MySQL, several lobbies can share the same data: a player who goes from `lobby-1` to `lobby-2` keeps their options, their scoreboard and can even be teleported to their last position.

`general.yml`:

```yaml
Plugin:
  Use:
    MYSQL:
      Enable: true
      Host: localhost
      Username: root
      Password: '123'
      Database: Hawn
      Port: 3306
      Use-SSL: false
```

1. Create the database (`Hawn` here) on your MySQL / MariaDB server.
2. Fill the options above on every lobby, with the same database.
3. Restart the servers.

At startup the console shows `The plugin will now use MySQL as method for information`. Hawn creates its tables by itself:

| Table                               | Content                                 |
| ----------------------------------- | --------------------------------------- |
| `player_info`                       | Name, UUID, join dates, IP (if `Save-IP`) |
| `player_option_number_connections`  | Number of connections                   |
| `player_last_position`              | Last position                           |
| `player_gamemode`                   | Last gamemode                           |
| `player_vanish`                     | Vanish state                            |
| `player_speed`, `player_fly_speed`  | Speed options                           |
| `player_option_fly`, `player_option_doublejump`, `player_option_jumpboost`, `player_option_pv`, `player_option_autobc` | Player options |
| `player_option_keep_sb`             | Chosen scoreboard                       |

Each table has one row per player (primary key on `player_UUID`). The data of a player is loaded before they join and written in the background, so the database never slows the server down. If the connection is lost, Hawn reopens it by itself.

{% hint style="info" %}
**Coming from Hawn 1.2?** At the first start, Hawn adds the primary key to its old tables and removes the duplicate rows. Each old table is kept as `<table>_before_1_3` (for example `player_gamemode_before_1_3`): delete these copies once you have checked that everything works.
{% endhint %}

{% hint style="warning" %}
If the connection fails at startup, Hawn falls back to YAML and says why in the console. Check the host, the port, the user rights and that the database exists.
{% endhint %}

## What is restored on join

Which data is used when a player joins is decided in `Player-Option-General.yml`, see [Keep options between sessions](player-options.md#keep-options-between-sessions).

## Placeholders and commands

* `%hawn_player_join_date%` and `%hawn_player_first_join_date%` show the dates.
* `/checkaccount <player>` (`hawn.command.checkaccount`) shows the stored information of a player.
* The date format is `general.yml` → `Plugin.Date-Format` (default `dd-MM-yyyy`), and the hour format `Plugin.12-Hours-Or-24-Hours-Format` (`12` or `24`).
