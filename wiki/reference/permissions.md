---
description: Every Hawn permission, and a ready-to-use list for a basic lobby.
---

# Permissions

Hawn doesn't declare its permissions in its `plugin.yml`: **operators have all of them, normal players have none**. Give them with your permission plugin.

{% hint style="info" %}
Replace the `<...>` parts by your own names: `hawn.command.spawn.<spawn>` becomes `hawn.command.spawn.lobby` for a spawn named `lobby`. Wildcards such as `hawn.emoji.*` work if your permission plugin supports them (LuckPerms does).
{% endhint %}

## Starter list for a lobby

What a normal player usually needs on a lobby with the default configuration:

```
hawn.command.spawn.<your spawn>        # /spawn and void TP (and teleport on join if Spawn-Permission is on)
hawn.use.customjoinitem                # receive the join items
hawn.scoreboard.<your scoreboard>      # see the scoreboard
hawn.fun.doublejump.double             # double jump
hawn.fun.jumppads                      # jump pads (only if Use_Permission is added)
hawn.command.optionplayer.main         # /option
hawn.command.optionplayer.pv           # /option pv
hawn.command.help                      # /help
hawn.command.help.lobbyhelp            # the default /help category
hawn.chat.emoji                        # emojis in the chat
hawn.emoji.*                           # every emoji
hawn.chat.can.mention                  # @mentions
hawn.sign.interact.<sign>              # action signs
hawn.command.warp                      # /warp
hawn.warp.<warp>                       # each warp
```

## Administration

| Permission                            | Description                                                |
| ------------------------------------- | ---------------------------------------------------------- |
| `hawn.admin`                          | `/hawn`, `/setspawn`, `/delspawn`. Required with every `/hawn` permission below. |
| `hawn.admin.*`                        | Every `/hawn` sub-command.                                 |
| `hawn.admin.command.reload`           | `/hawn reload`                                             |
| `hawn.admin.command.info`             | `/hawn info`, `/hawn tps`, `/hawn version`                 |
| `hawn.admin.command.parseholders`     | `/hawn parse`                                              |
| `hawn.admin.command.bypassbuild`      | `/hawn build`                                              |
| `hawn.admin.command.noclip`           | `/hawn noclip`                                             |
| `hawn.admin.command.nightvision`      | `/hawn nightvision`                                        |
| `hawn.admin.command.slotview`         | `/hawn slotview`                                           |
| `hawn.admin.command.spawnmanager`     | `/hawn spawnmanager`                                       |
| `hawn.admin.command.hooks`            | `/hawn hooks`                                              |
| `hawn.admin.command.maintenance`      | `/hawn maintenance`                                        |
| `hawn.maintenance.bypass`             | Join during the maintenance without being in its whitelist (operators by default) |
| `hawn.admin.command.urgent`           | `/hawn urgent` (the player must also be listed in `Commands/Hawn.yml`) |
| `hawn.editplayer`                     | `/hawn editplayer`, the player list of the panel           |
| `hawn.editplayer.gamemode`            | Player editor: change the gamemode                         |
| `hawn.editplayer.clearinv`            | Player editor: clear the inventory                         |
| `hawn.editplayer.tp`                  | Player editor: teleport                                    |
| `hawn.adminpanel`                     | `/adminpanel` (the player must also be listed in `Commands/AdminPanel.yml`) |
| `hawn.spy.adminpanel`                 | Warned when someone changes something in the admin panel   |
| `hawn.urgent.spy.adminpanel`          | Same, during the emergency mode                            |
| `hawn.setup`                          | Sees the [welcome setup](../getting-started/welcome-setup.md), `/hawn setup` |
| `hawn.event.warn.tps`                 | Warned when the TPS is low                                 |
| `hawn.notify.staff.commandblocker`    | Warned when someone uses a blocked command                 |
| `hawn.antiswear.benotified`           | Warned when someone swears                                 |
| `hawn.staff.seevanished`              | Sees vanished players                                      |

## Commands

| Permission                                   | Command                                          |
| -------------------------------------------- | ------------------------------------------------ |
| `hawn.command.spawn.<spawn>`                 | `/spawn` to this spawn and see it in `/spawnlist` (also needed for the void TP, and for the teleport on join when `Spawn-Permission` is on) |
| `hawn.command.spawn`                         | `/spawn`, only when `Use-Permission: true`       |
| `hawn.command.spawn.teleportothers`          | `/spawn tp <player>`                             |
| `hawn.command.spawn.other.bypassdelay`       | No `/spawn` delay (when `Bypass-Delay: true`)    |
| `hawn.command.spawn.spawnlist`               | `/spawnlist`                                     |
| `hawn.command.warp`                          | `/warp`                                          |
| `hawn.warp.<warp>`                           | Use (and see) this warp                          |
| `hawn.command.warp.others`                   | `/warp <warp> <player>`                          |
| `hawn.command.warp.bypassdelay.self`         | No `/warp` delay                                 |
| `hawn.command.warp.bypassdelay.other`        | No delay for `/warp <warp> <player>`             |
| `hawn.command.warp.setwarp`                  | `/setwarp`                                       |
| `hawn.command.warp.editwarp`                 | `/editwarp`                                      |
| `hawn.command.warp.delwarp`                  | `/delwarp`                                       |
| `hawn.command.warp.warplist`                 | `/warplist`                                      |
| `hawn.command.world.general`                 | `/hw` and the world menu                         |
| `hawn.command.world.*`                       | Every `/hw` sub-command                          |
| `hawn.command.world.list` / `.info` / `.tp` / `.create` / `.import` / `.unload` / `.delete` | `/hw list`, `info`, `tp`, `create`, `import`, `unload`, `delete` |
| `hawn.command.world.modifymain` / `.modifytime` / `.modifyweather` / `.modifydifficulty` | World settings in the menu |
| `hawn.command.world.setdefault`              | Make a world the [default world](../features/world-manager.md#the-default-world) in the menu |
| `hawn.command.broadcast`                     | `/broadcast`                                     |
| `hawn.command.warning`                       | `/warning`                                       |
| `hawn.command.titleannouncer`                | `/titleannouncer`                                |
| `hawn.command.actionbarannouncer`            | `/actionbarannouncer`                            |
| `hawn.command.clearchat.help`                | `/cc`                                            |
| `hawn.command.clearchat.normal`              | `/cc c`                                          |
| `hawn.command.clearchat.anonymous`           | `/cc a`                                          |
| `hawn.command.clearchat.own`                 | `/cc o` (when `Use_Permission: true`)            |
| `hawn.command.clearchat.other`               | `/cc other` (when `Use_Permission: true`)        |
| `hawn.command.mutechat`                      | `/globalmute`                                    |
| `hawn.command.delaychat`                     | `/delaychat`                                     |
| `hawn.command.emoji`                         | `/emoji` (when `Gui.Use_Permission: true`)       |
| `hawn.command.help`                          | `/help` (when `Use-Permissions: true`)           |
| `hawn.command.help.<category>`               | A `/help` category                               |
| `hawn.command.optionplayer.main`             | `/option`                                        |
| `hawn.command.optionplayer.pv` / `.fly` / `.doublejump` / `.speed` / `.flyspeed` / `.jumpboost` / `.autobc` | `/option <option>` |
| `hawn.command.optionplayer.speed`            | `/speed`                                         |
| `hawn.command.optionplayer.flyspeed`         | `/flyspeed`                                      |
| `hawn.command.optionplayer.speed.priorityoptionplayer` | Get back your own speed when turning the boost on |
| `hawn.command.optionplayer.flyspeed.priorityoptionplayer` | Same for the fly speed                     |
| `hawn.command.scoreboard.toggle`             | `/scoreboard`                                    |
| `hawn.scoreboard.command.set`                | `/scoreboard set`                                |
| `hawn.scoreboard-keep-scoreboard-change`     | `/scoreboard keep`                               |
| `hawn.scoreboard-list`                       | `/scoreboard list`                               |
| `hawn.command.gamemode.self`                 | `/gamemode`, `/gms`, `/gmc`, `/gma`, `/gmsp`     |
| `hawn.command.gamemode.other`                | Same, for another player                         |
| `hawn.command.gamemode.quickgm`              | `/gamemode` without argument                     |
| `hawn.command.gamemode.buildmode`            | Build mode linked to the gamemode                |
| `hawn.command.weather.sun` / `.rain` / `.thunder` | `/sun`, `/rain`, `/thunder`                 |
| `hawn.command.time.day` / `.night`           | `/day`, `/night`                                 |
| `hawn.command.fly` / `hawn.command.fly.others` | `/fly`                                         |
| `hawn.command.heal` / `hawn.command.heal.other` | `/heal`                                       |
| `hawn.command.feed` / `hawn.command.feed.other` | `/feed`                                       |
| `hawn.command.vanish` / `hawn.command.vanish.others` | `/vanish`                                |
| `hawn.command.vanish.actionbar`              | "Vanished" action bar                            |
| `hawn.command.clearinv` / `hawn.command.clearinv.others` | `/clearinventory`                    |
| `hawn.command.invsee`                        | `/invsee`                                        |
| `hawn.command.enderchest` / `hawn.command.enderchest.other` | `/enderchest`                     |
| `hawn.command.workbench`                     | `/workbench`                                     |
| `hawn.command.exp`                           | `/exp`                                           |
| `hawn.command.hat` / `hawn.command.hat.other` | `/hat`                                          |
| `hawn.command.skull`                         | `/skull`                                         |
| `hawn.command.repair`                        | `/repair`                                        |
| `hawn.command.burn`                          | `/burn`                                          |
| `hawn.command.gotop` / `hawn.command.gotop.other` | `/gotop`                                    |
| `hawn.command.getpos`                        | `/getpos`                                        |
| `hawn.command.ip`                            | `/ip`                                            |
| `hawn.command.ping.self` / `hawn.command.ping.other` | `/ping` (when `Use_Permission: true`)    |
| `hawn.command.list`                          | `/list`                                          |
| `hawn.command.suicide`                       | `/suicide`                                       |
| `hawn.command.checkaccount`                  | `/checkaccount`                                  |
| `hawn.command.kickall`                       | `/kickall`                                       |
| `hawn.command.bypass.kickall`                | Not kicked by `/kickall`                         |
| `hawn.command.cleargrounditems`              | `/cleargrounditems`                              |
| `hawn.command.clearmobs`                     | `/clearmobs`                                     |

## Join and quit

| Permission                                       | Description                                                  |
| ------------------------------------------------ | ------------------------------------------------------------ |
| `hawn.event.silentjoin`                          | Join without message (`Silent-Staff-Join`)                   |
| `hawn.event.silentquit`                          | Quit without message (`Silent-Staff-Quit`)                   |
| `hawn.on-join.custommessage.<group>`             | Join message of a group                                      |
| `hawn.on-quit.custommessage.<group>`             | Quit message of a group                                      |
| `hawn.on-join.custom-message-per-world.<world>`  | Join message of a world                                      |
| `hawn.on-quit.custom-message-per-world.<world>`  | Quit message of a world                                      |
| `hawn.on-join.custom-motd-per-world.<world>`     | Join MOTD of a world                                         |
| `hawn.event.spawn.join.vip`                      | Teleported to the VIP spawn on join                          |
| `hawn.event.onjoin.bypass.clearinv`              | Inventory not cleared on join                                |
| `hawn.event.onjoin.bypass.clearchat`             | Chat not cleared on join                                     |
| `hawn.bypass.gamemodeonjoin`                     | Gamemode not changed on join                                 |
| `hawn.bypass.foodrestore`                        | Food not restored on join                                    |
| `hawn.bypass.healthrestore`                      | Health not restored on join                                  |
| `hawn.event.onjoin.bypass.firework`              | No join firework                                             |
| `hawn.event.onjoin.bypass.lightningstrike`       | No join lightning                                            |
| `hawn.onjoin.potion.blindness`                   | Join blindness (when `Use_Permission: true`)                 |
| `hawn.onjoin.potion.jump`                        | Join jump boost (when `Use_Permission: true`)                |
| `hawn.onjoin.fly`                                | Can fly on join                                              |
| `hawn.onjoin.playeroption.speed`                 | Keeps their own walk speed on join                           |
| `hawn.onjoin.playeroption.flyspeed`              | Keeps their own fly speed on join                            |
| `hawn.onjoin.keepgamemode`                       | Keeps their last gamemode on join                            |
| `hawn.betweenservers.keepvanish`                 | Keeps their vanish state on join                             |
| `hawn.betweenservers.tplastposition`             | Teleported to their last position on join                    |
| `hawn.join.full`                                 | Can join when the server is full                             |

## Items, fun and signs

| Permission                          | Description                                                  |
| ----------------------------------- | ------------------------------------------------------------ |
| `hawn.use.customjoinitem`           | Receives and uses the join items                             |
| `hawn.use.cji.item.<item key>`      | One join item (when `Use_Permission_Per_Item: true`)         |
| `hawn.use.cji.item.helmet` / `.chestplate` / `.leggings` / `.boots` | The join armour (when `Use_Permission_Per_Item: true`) |
| `hawn.fun.doublejump.double`        | Double jump (when `Use_Permission: true`)                    |
| `hawn.fun.jumppads`                 | Jump pads (when `Use_Permission: true`)                      |
| `hawn.fun.boots.flying`             | Diamond boots while flying                                   |
| `hawn.sign.color`                   | Colours on signs                                             |
| `hawn.sign.emoji`                   | Emojis on signs                                              |
| `hawn.sign.command`                 | Create action signs                                          |
| `hawn.sign.delete`                  | Break action signs                                           |
| `hawn.sign.interact.<sign>`         | Click an action sign                                         |

## Chat

| Permission                                   | Description                                         |
| -------------------------------------------- | --------------------------------------------------- |
| `hawn.chat.emoji`                            | Use emojis                                          |
| `hawn.emoji.<emoji>`                         | Use one emoji (when its `Use_Permission: true`)     |
| `hawn.chat.can.mention`                      | Mention players                                     |
| `hawn.use.chatcolor.chat.basic.light`        | `&c &e &a &b &3 &d &f &7`                           |
| `hawn.use.chatcolor.chat.basic.dark`         | `&4 &6 &2 &1 &9 &5 &8 &0`                           |
| `hawn.use.chatcolor.chat.special.format`     | `&l &m &n &o &r`                                    |
| `hawn.use.chatcolor.chat.special.magic`      | `&k`                                                |
| `hawn.use.chatcolor.chat.code.<code>`        | One code (when `Per-Color-Permission: true`)        |
| `hawn.use.chatcolor.chat.hex`                | Hex colours                                         |
| `hawn.bypass.antiswear`                      | Not filtered by the anti-swear                      |
| `hawn.bypass.antispam`                       | Not checked by the anti-spam (repeated messages, capital letters) |
| `hawn.event.chat.bypass.mutechat`            | Can talk when the chat is muted                     |
| `hawn.event.chat.bypass.chatdelay`           | No chat delay                                       |
| `hawn.event.bypass.blockcommands`            | Can use blocked commands                            |

## Auto broadcast

Only when `Use-Permission-To-Get-Messages: true`:

| Permission                       | Receives                    |
| -------------------------------- | --------------------------- |
| `hawn.get.autobroadcast`         | Chat announcements          |
| `hawn.get.autobroadcast_titles`  | Title announcements         |
| `hawn.get.autobroadcast_ab`      | Action bar announcements    |
| `hawn.get.autobroadcastbb`       | Boss bar announcements      |

## Protections (bypass)

| Permission                                         | Ignores                                    |
| -------------------------------------------------- | ------------------------------------------ |
| `hawn.event.construct.bypass.place`                | Anti-place                                 |
| `hawn.event.construct.bypass.break`                | Anti-break                                 |
| `hawn.event.construct.bypass.protectionitemblocks` | Blocked interactions (chests, doors...)    |
| `hawn.bypass.HagingBreakByEntity`                  | Paintings / item frames protection         |
| `hawn.bypass.PlayerInteractEntity`                 | Item frame content protection              |
| `hawn.bypass.protection.buckets`                   | Anti-bucket                                |
| `hawn.bypass.armorstand`                     | Can use and hit the armor stands (`Armor-Stand`)    |
| `hawn.bypass.hangingplace`                   | Can place item frames and paintings (`Hanging-Place`) |
| `hawn.bypass.trample`                        | Can trample the farmland (`Anti-Trample`)           |
| `hawn.bypass.world.event.shears`                   | No-shears                                  |
| `hawn.bypass.antidamage`                           | Anti-damage                                |
| `hawn.bypass.foodkeep`                             | No hunger                                  |
| `hawn.bypass.keepgamemode`                         | Gamemode lock                              |
| `hawn.bypass.block.offhand`                        | Off hand lock                              |
| `hawn.event.bypass.player.antimount`               | Mount lock                                 |
| `hawn.event.playeritem.bypass.drop`                | Anti-drop                                  |
| `hawn.event.playeritem.bypass.pickup`              | Anti-pickup                                |
| `hawn.event.playeritem.bypass.moveitem`            | Anti-move items                            |
| `hawn.event.playeritem.bypass.damageitem`          | Item durability lock                       |
| `hawn.event.death.bypass.cleardrop`                | Drops cleared on death                     |
| `hawn.event.respawn`                               | Instant respawn (when `Use_Permission: true`) |
| `hawn.bypass.voidtp`                               | Void TP                                    |
| `hawn.antiwdl.bypass`                              | Anti world downloader                      |

Most bypass permissions only work when the `Bypass` (or `Bypass-With-Permission`) option of the feature is `true`, see [Per-world options](../basics/per-world-options.md#bypass-options).

## Scoreboards

| Permission                     | Description                                         |
| ------------------------------ | --------------------------------------------------- |
| `hawn.scoreboard.<file name>`  | Sees this scoreboard, for example `hawn.scoreboard.scoreboard.default` |

## Tab list

| Permission                     | Description                                         |
| ------------------------------ | --------------------------------------------------- |
| `hawn.tablist.<file name>` | Sees the tab list of `Tablist/<file name>.yml` when it has `permission: true`, for example `hawn.tablist.staff`. Since Hawn 1.4, see [Tab list](../features/tablist.md#per-world-and-per-permission) |
