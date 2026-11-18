---
description: >-
  The MOTD shown in the multiplayer server list, the number of slots, joining
  a full server and the anti world downloader.
---

# Server list (MOTD and slots)

Everything is in `ServerList.yml`.

## MOTD

```yaml
Motd:
  Classic:
    Enable: true
    Random: true
    Main:
      Line-1: '&6&lYour Server &8- &7Lobby'
      Line-2: '&eplay.yourserver.net &8- &7%gettime%'
    Random-List:
      discord:
        Line-1: '&6&lYour Server &8- &7Lobby'
        Line-2: '&7Join our Discord: &ediscord.gg/yourserver'
      event:
        Line-1: '&6&lYour Server &8- &bEvent this weekend'
        Line-2: '&eplay.yourserver.net'
      vote:
        Line-1: '&6&lYour Server &8- &7Lobby'
        Line-2: '&7Vote for us: &eyourserver.net/vote'
```

* `Random: false`: the `Main` MOTD is always shown.
* `Random: true`: a MOTD of `Random-List` is picked at every ping. Add as many as you want, with any key. An empty `Random-List` shows the `Main` MOTD (since 1.6.2).

`/setmotd <1/2> <text>` changes a line of `Main` from the game or the console, with colours (since 1.6.2). It also sets `Random: false`, so that this MOTD is the one shown. `/setmotd 2` with no text empties the line.

Colours, hex colours and the **server** placeholders work (`%gettime%`, `%getdate%`, `%tps%`, `%bungee_total%`...). Player placeholders don't: the server doesn't know who is pinging it.

### Special MOTDs

These MOTDs replace the classic one in some situations:

```yaml
  WhiteList:              # the server whitelist is on
    Enable: true
    Line-1: '&eThe server is on whitelist'
    Line-2: '&bPlease come back later'
  Maintenance:            # /hawn maintenance is on
    Enable: true
    Line-1: '&cThe server is in maintenance'
    Line-2: '&bPlease come back later'
  Urgent:                 # /hawn urgent is on
    Enable: true
    Line-1: '&cThe server is whitelisted for now'
    Line-2: '&ePlease come back later'
```

See [Admin tools](admin-tools.md#maintenance-mode) for the maintenance and emergency modes.

## Slots

```yaml
Slots:
  One-Slot-Free: true
  Fake-Max-Player:
    Enable: false
    Number: 2000
```

* `One-Slot-Free`: the maximum shown is always "online players + 1" (until the real maximum is reached). This makes the server look full but still joinable.
* `Fake-Max-Player`: shows `Number` as the maximum. Only used when `One-Slot-Free` is `false`.

These options only change what is displayed: the real limit is still `max-players` in `server.properties`.

Since Hawn 1.6.2, `/maxplayers <number>` changes the real limit without a restart (Spigot 1.17+, Paper), and keeps it in `Slots.Max-Players`: Hawn sets it again at each start. `0` (the default) leaves `max-players` of `server.properties`, from the next restart. `/maxplayers` alone shows the current limit.

## Links of the pause menu

Since Hawn 1.6.2, `ServerLinks.yml` puts your links in the pause menu of the players (Esc, then **Server Links**), on a 1.21+ server with 1.21+ clients:

```yaml
Server-Links:
  Enable: true
  Links:
    website:
      Type: WEBSITE
      Url: https://yourserver.net
    discord:
      Name: '&9Discord'
      Url: https://discord.gg/yourserver
```

* `Type`: a link the game names itself, in the language of each player: `WEBSITE`, `SUPPORT`, `COMMUNITY_GUIDELINES`, `COMMUNITY`, `FORUMS`, `NEWS`, `ANNOUNCEMENTS`, `STATUS`, `FEEDBACK`, `REPORT_BUG`.
* `Name`: your own text, with colours, when no `Type` is given.
* `Url`: the address, with `https://`.

The file is off by default (the examples point nowhere). After `/hawn reload`, the players see the new links when they join again.

## Joining a full server

```yaml
On-Join:
  Player-With-Permission-Join-Full-Server: true
  Message:
  - '&cThe multi line'
  - '&bworks like that %player%'
```

When the server is full, players with `hawn.join.full` can still join. The others are kicked with `Message` (one line per list entry).

## Anti world downloader

Players using a World Downloader mod are kicked with:

```yaml
Anti-WDL:
  Enable: true
  Kick-Message:
  - '&cSorry you use a world downloader'
```

`Enable: false` lets the world downloaders in (since Hawn 1.4.3).

Players with `hawn.antiwdl.bypass` are not kicked. This catches the mods that announce themselves to the server, not every possible downloader.
