---
description: The placeholders you can use in Hawn messages, scoreboards, tab list, items...
---

# Placeholders

Placeholders are replaced by a value when the message is sent. They work in almost every Hawn text: messages, scoreboards, tab list, item names and lores, titles, boss bars...

With [PlaceholderAPI](../integrations/hooks.md#placeholderapi) installed, **every PlaceholderAPI placeholder** works too (`%luckperms_prefix%`, `%vault_eco_balance%`...).

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
| `%target%`                        | In some messages: the player targeted by a command |

## Server

| Placeholder             | Value                                         |
| ----------------------- | --------------------------------------------- |
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

## BattleLevels

With the [BattleLevels](../integrations/hooks.md#battlelevels) hook:

`%h_battlelevels_level%`, `%h_battlelevels_score%`, `%h_battlelevels_bar%`, `%h_battlelevels_topstreak%`, `%h_battlelevels_killstreak%`, `%h_battlelevels_kills%`, `%h_battlelevels_deaths%`, `%h_battlelevels_kdr%`, `%h_battlelevels_booster%`, `%h_battlelevels_boosterenabled%`, `%h_battlelevels_globalbooster%`, `%h_battlelevels_globalboosterenabled%`, `%h_battlelevels_neededfornext%`, `%h_battlelevels_neededfornextremaining%`

## Where player placeholders don't work

Some texts are not linked to a player, only the server placeholders work there:

* the [server list MOTD](../features/server-list.md);
* messages written in the console.

## Special placeholders

Some messages have their own placeholders, written in the default message: `%sender%` (mentions), `%message%` (anti-swear), `%spawnName%`, `%arg1%`, `%minutes%`, `%noperm%`...

{% hint style="warning" %}
Hawn placeholders are only available inside Hawn. Hawn doesn't register a PlaceholderAPI expansion, so other plugins can't use `%gettime%` or `%tps%` from Hawn.
{% endhint %}
