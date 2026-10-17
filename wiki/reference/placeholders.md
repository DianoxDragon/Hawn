---
description: The placeholders you can use in Hawn messages, scoreboards, tab list, items...
---

# Placeholders

Placeholders are replaced by a value when the message is sent. They work in almost every Hawn text: messages, scoreboards, tab list, item names and lores, titles, boss bars...

With [PlaceholderAPI](../integrations/hooks.md#placeholderapi) installed, **every PlaceholderAPI placeholder** works too (`%luckperms_prefix%`, `%vault_eco_balance%`...), and the other plugins can use the placeholders of Hawn: see [In other plugins](#in-other-plugins).

{% hint style="info" %}
Test a placeholder in game with `/hawn parse me <placeholder>`, for example `/hawn parse me %player_world%`.
{% endhint %}

## Player

| Placeholder                       | Value                                        |
| --------------------------------- | -------------------------------------------- |
| `%player%`, `%player_name%`       | Player name                                  |
| `%player_displayname%`            | Display name (with the prefix of your chat plugin, if any) |
| `%player_uuid%`                   | UUID                                         |
| `%player_ip%`                     | IP address                                   |
| `%ping%`                          | Ping in milliseconds                         |
| `%player_world%`                  | Current world                                |
| `%player_x%`, `%player_y%`, `%player_z%` | Position                              |
| `%player_biome%`                  | Current biome                                |
| `%player_health%`                 | Health                                       |
| `%player_max_health%`, `%player_max_health_rounded%` | Maximum health            |
| `%player_health_scale%`           | Health scale                                 |
| `%player_food_level%`             | Food level (0–20)                            |
| `%player_saturation%`             | Saturation                                   |
| `%player_level%`                  | XP level                                     |
| `%player_exp%`                    | XP progress in the current level             |
| `%player_exp_to_level%`           | XP needed for the next level                 |
| `%player_bed_x%`, `%player_bed_y%`, `%player_bed_z%`, `%player_bed_world%` | Bed location |
| `%worldtime%`                     | Time of the player's world (ticks)           |
| `%hawn_player_join_date%`         | Last join date                               |
| `%hawn_player_first_join_date%`   | First join date                              |
| `%player_join_number%`            | The order of arrival of the player: `1` for the first player of the server (since Hawn 1.4.4) |
| `%player_connections%`            | How many times the player joined             |
| `%player_gamemode%`               | Gamemode: `survival`, `creative`, `adventure` or `spectator` |
| `%player_vanished%`               | Vanished ([yes or no](#yes-no-and-unknown-values)) |
| `%player_flying%`                 | Flying right now (yes or no)                 |
| `%player_option_<option>%`        | A choice of [`/option`](../features/player-options.md) (yes or no): `fly`, `doublejump`, `speed`, `flyspeed`, `jumpboost`, `autobc`, `pv` |
| `%rank%`, `%rank_prefix%`, `%rank_suffix%` | The [rank](../features/ranks.md) of the player, its prefix and its suffix (since Hawn 1.4.7) |
| `%target%`                        | In some messages: the player targeted by a command |

## Server

| Placeholder             | Value                                         |
| ----------------------- | --------------------------------------------- |
| `%online%`              | Players online (since Hawn 1.4.3)             |
| `%max_players%`         | Maximum number of players                     |
| `%online_<world>%`      | Players in a world, for example `%online_world_nether%` |
| `%player_world_online%` | Players in the world of the player            |
| `%staff_online%`        | Players online with `hawn.staff`              |
| `%unique_players%`      | Different players who ever joined (since Hawn 1.4.4) |
| `%server_uptime%`       | Time since the start of the server: `2d 5h 12m`, `3h 4m`, `7m` |
| `%chat_muted%`          | The chat is muted with `/gmute` (yes or no)   |
| `%maintenance%`         | The [maintenance mode](../features/admin-tools.md#maintenance-mode) is on (yes or no) |
| `%tps%`                 | Server TPS                                    |
| `%gettime%`             | Current time (12 or 24 hours, see `general.yml`) |
| `%getdate%`             | Current date (format of `general.yml`)        |
| `%serverversion%`       | Server version                                |
| `%javaversion%`         | Java version                                  |
| `%osversion%`           | Operating system                              |
| `%maxmemory%`, `%totalmemory%`, `%freememory%` | Memory                 |
| `%barmemory%`           | Memory usage as a bar                         |
| `%cpuload%`, `%averagecpuload%` | CPU load                              |
| `%barcpu%`              | CPU usage as a bar                            |
| `%totalspace%`, `%freespace%`, `%totalspaceusable%` | Disk space        |
| `%bardisk%`             | Disk usage as a bar                           |
| `%bungee_total%`        | Players on the whole network (see [BungeeCord](../integrations/bungeecord.md)) |
| `%bungee_<server>%`     | Players on one server of the network          |

## Hawn

| Placeholder              | Value                                                   |
| ------------------------ | ------------------------------------------------------- |
| `%prefix%`               | The Hawn prefix (`General.Prefix` in `Messages/<language>/General.yml`) |
| `%gethawnversion%`       | Hawn version                                            |
| `%checkupdatehawn%`      | Update status ("Plugin up to date", "Old Version detected"...) |
| `%DELAY%`                | Current [chat delay](../features/chat.md#chat-delay)    |
| `%timedelaypvcji%`       | Seconds before the player visibility item can be used again |
| `%timedelayfunguncji%`   | Seconds before the fun gun can be used again            |

{% hint style="info" %}
The vanished players are not counted by `%online%`, `%online_<world>%`, `%player_world_online%` and `%staff_online%`: the number would give them away. A world that doesn't exist leaves `%online_<world>%` as it is written.
{% endhint %}

## Yes, no and unknown values

`%player_vanished%`, `%player_flying%`, `%player_option_<option>%`, `%chat_muted%` and `%maintenance%` give `Placeholders.Yes` or `Placeholders.No` of `Messages/<language>/General.yml`, with colours if you want:

```yaml
Placeholders:
  Yes: '&ayes'
  No: '&cno'
  Unknown: '?'
```

`Unknown` is shown by `%player_join_number%` and `%unique_players%` while the number can't be known (MySQL unreachable).

## Order of arrival

`%player_join_number%` and `%unique_players%` welcome the new players with their number, for example in the [first join broadcast](../features/join-and-quit.md#first-join): `&eWelcome %player%, you are the player #%player_join_number%`.

* **Without MySQL**, each server counts its own players. The order is kept in `StockageInfo/join-order.txt`, one UUID per line. At the first start of 1.4.4, the players who already have a file are numbered in their order of arrival (their first join date).
* **With [MySQL](../features/database.md#several-servers-on-one-database)**, the servers on the same database count together: a player has the same number on every server, and two servers never give the same number, even at the same second. The players already in the database are numbered in their order of arrival at the first start.

## Where player placeholders don't work

Some texts are not linked to a player, only the server placeholders work there:

* the [server list MOTD](../features/server-list.md);
* messages written in the console.

## Special placeholders

Some messages have their own placeholders, written in the default message: `%sender%` (mentions), `%message%` (anti-swear), `%spawnName%`, `%arg1%`, `%minutes%`, `%noperm%`...

## In other plugins

Since Hawn 1.4.6, with [PlaceholderAPI](../integrations/hooks.md#placeholderapi) installed, the other plugins (TAB, DeluxeMenus, a hologram plugin...) can use every placeholder of this page: write `%hawn_<placeholder>%`, the placeholder without its `%`.

| In Hawn                          | In another plugin                    |
| -------------------------------- | ------------------------------------ |
| `%tps%`                          | `%hawn_tps%`                         |
| `%online_world_nether%`          | `%hawn_online_world_nether%`         |
| `%player_join_number%`           | `%hawn_player_join_number%`          |
| `%server_uptime%`                | `%hawn_server_uptime%`               |
| `%hawn_player_join_date%`        | `%hawn_player_join_date%` (the same) |

Nothing to install: Hawn gives them to PlaceholderAPI itself (no `/papi ecloud download`). Without a player (a hologram of the server, the console...), only the server placeholders have a value. The options are in `general.yml`:

```yaml
Plugin:
  Use:
    Hook:
      PlaceholderAPI:
        Expansion:
          Enable: true          # false: %hawn_...% is not given to the other plugins
          Hidden:               # the placeholders kept inside Hawn, without the %
          - player_ip
```

`%player_ip%` is hidden by default: a plugin that shows placeholders to other players could show it.
