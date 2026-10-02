---
description: Here you can see all Hawn's commands and permissions
---

# Commands/Permissions

## Command category

{% hint style="info" %}
If you see, **\[text]** - it's optional, but if you see **\<text>** it's mandatory\
When you see for example **\[all/memory/tps/disk/cpu/server/version],** is that you have to choose between these proposals.
{% endhint %}

### Hawn main commands - (/hawn, /paneladmin)

{% hint style="warning" %}
All commands that start with /**hawn**, must have one of these 2 permissions mandatory

* `hawn.admin`
* `hawn.admin.*` _(This permission gives you access to the entire command)_
{% endhint %}

| **Command(s)**                                            | **Permission(s)**                   | **Description**                                                                                                                                                                             |
| --------------------------------------------------------- | ----------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| /**hawn**                                                 | `hawn.admin`                        | This command is used to display the different commands of hawn. This command also has subcommands to manage the plugin directly.                                                            |
| /**hawn spawnmanager etc.**                               |  `hawn.admin.command.spawnmanager`  | This command is used to manage the spawn. You can create as many spawn as you want. _For more information, check out the tutorial on spawns._                                               |
|  **/hawn reload** or **rl**                               | `hawn.admin.command.reload`         | This command is used to reload the plugin to 90%. In some cases, you may need to restart the server.                                                                                        |
|  **/hawn version, ver** or **v**                          | `hawn.admin.command.info`           | This command allows you to see the version of the plugin you are using directly                                                                                                             |
|  **/hawn tps**                                            | `hawn.admin.command.info`           | This command displays the EXACT server tps, and no approximation                                                                                                                            |
|  **/hawn info \[all/memory/tps/disk/cpu/server/version]** | `hawn.admin.command.info`           | You can see the use of the disk, processor, server and memory, and other things. Yes, you can use all this, for example, if you just want to see the processor usage, do /**hawn info cpu** |
|  **/hawn debug emoji**                                    | :heavy\_multiplication\_x:          | If you have an error with the configuration of the gui of /**emoji**, especially with materials, this command will tell you where you have an error on the materials.                       |
|  **/hawn about**                                          | :heavy\_multiplication\_x:          | This command only displays simple information about the plugin                                                                                                                              |
|  **/hawn build**                                          | `hawn.admn.command.bypassbuild`     | This command allows you to ignore all hawn's restrictions regarding construction and other matters.                                                                                         |
|  **/hawn hooks**                                          | `hawn.admin.command.hooks`          | This allows you to check if hawn really detects the plugins it supports                                                                                                                     |
|  **/hawn parse \<player> \<placeholder>**                 |  `hawn.admin.command.parseholders`  | This command returns a message, indeed it is intended to test the placeholders of hawn, to check if it works.                                                                               |
| **/hawn nv** or **nightvision**                           | `hawn.admin.command.nightvision`    | The purpose of this command is to give you the view in the dark.                                                                                                                            |
| **/hawn noclip**                                          | `hawn.admin.command.noclip`         | This command gives you the possibility, when you are in creative mode, to go through walls.                                                                                                 |
| **/hawn urgent**                                          | `hawn.admin.command.urgent`         | This emergency mode allows saving the server, disabling all other plugins, and kicking all players from the server, and setting up a whitelist.                                             |
| **/hawn slotview** or **sv**                              | `hawn.admin.command.slotview`       | You will be able to see, when you click in your inventory, what is the number of the slot.                                                                                                  |
| **/hawn editplayer**                                      | `hawn.editplayer`                   | Quick access to the player edition.                                                                                                                                                         |
| **/hawn maintenance** or **m**                            | `hawn.admin.command.maintenance`    | This maintenance mode kicks all players from the server, and setting up a whitelist.                                                                                                        |

| **Command(s)**                                     | **Permission(s)** | **Description**                                                                                |
| -------------------------------------------------- | ----------------- | ---------------------------------------------------------------------------------------------- |
| **/ap**, **/adminpanel**, **/paneladmin**, **/pa** | `hawn.adminpanel` | This command is used to configure and manage the server and the plugin hawn from a simple gui. |

### Spawn commands

{% hint style="warning" %}
If you don't want to have any problems with the teleportation delays you will need this permission. _(If this function is activated of course)_

* `hawn.command.spawn.other.bypassdelay`
{% endhint %}

| **Command(s)**                                       | **Permission(s)**                                                                                        | **Description**                                                                                                                                  |
| ---------------------------------------------------- | -------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------ |
|  **/spawn**, **/lobby** or **/hub** **\[spawnName]** | `hawn.command.spawn`                                                                                     | This command is used to teleport you to one of hawn's spawn, or to the default spawn.                                                            |
|  **/spawn tp \<player> \[spawnName]**                | <p><code>hawn.command.spawn.&#x3C;Spawn></code></p><p><code>hawn.command.spawn.teleportothers</code></p> | This command is used to teleport a player to one of hawn's spawn, or to the default spawn.                                                       |
|  **/setspawn \[spawnName]**                          | `hawn.admin` _or_ `hawn.admin.*`                                                                         | This command is used to save the or a spawn. You can create as many spawn as you want. _For more information, check out the tutorial on spawns._ |
|  **/delspawn \<spawnName>**                          | `hawn.admin` _or_ `hawn.admin.*`                                                                         | This command is used to delete a spawn. _For more information, check out the tutorial on spawns._                                                |
| **/spawnlist**                                       | `hawn.command.spawn.spawnlist`                                                                           | The command allows you to see the list of all spawns already created.                                                                            |

{% hint style="info" %}
_For more information, check out the tutorial on spawns._
{% endhint %}

### Warp commands

{% hint style="warning" %}
If you don't want to have any problems with the teleportation delays you will need this permission. _(If this function is activated of course)_

* `hawn.command.warp.bypassdelay.self`
* `hawn.command.warp.bypassdelay.other`
{% endhint %}

| **Command(s)**              | **Permission(s)**                                                                 | **Description**                                                    |
| --------------------------- | --------------------------------------------------------------------------------- | ------------------------------------------------------------------ |
| **/warp \<warp> \[player]** | <p><code>hawn.command.warp</code></p><p><code>hawn.command.warp.others</code></p> | This allows you to teleport to the warps you created.              |
| **/editwarp \<warp>**       | `hawn.command.warp.editwarp`                                                      | This command allows you to change the position of a warp easily.   |
| **/delwarp \<warp>**        | `hawn.command.warp.delwarp`                                                       | This command removes one warp you created.                         |
| **/setwarp \<warp>**        | `hawn.command.warp.setwarp`                                                       | This command allows you to set up a warp, wherever you want.       |
| **/warplist**               | `hawn.command.warp.warplist`                                                      | This command allows you to see the list of warps you have created. |

{% hint style="info" %}
_For more information, check out the tutorial on warps._
{% endhint %}

### Chat commands

#### - General -

| **Command(s)**                 | **Permission(s)**                                                                     | **Description**                                                                                                            |
| ------------------------------ | ------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------- |
|  **/globalmute** or **/gmute** | `hawn.command.mutechat`                                                               | With this command you can prevent players from talking in the chat. But with special permission, you can talk in the chat. |
| **/delaychat \<delay>**        | `hawn.command.delaychat`                                                              | This command allows you to put a timeout before writing a new message.                                                     |
| **/emoji**                     | <p><code>hawn.command.emoji</code></p><p><code>hawn.emoji.&#x3C;emojiname></code></p> | This command opens a GUI that allows you to see the list of emojis that the player can use.                                |

#### - Clear Chat -

| **Command(s)**                    | **Permission(s)**                  | **Description**                                                      |
| --------------------------------- | ---------------------------------- | -------------------------------------------------------------------- |
| **/cc**                           | `hawn.command.clearchat.help`      | This command shows you the different possibilities of the clearchat. |
| **/cc a \[reason]**               | `hawn.command.clearchat.anonymous` | That clear the chat anonymously with a reason or not.                |
| **/cc o**                         | `hawn.command.clearchat.own`       | That clear your own chat.                                            |
| **/cc c \[reason]**               | `hawn.command.clearchat.normal`    | That clear the chat with a reason or not.                            |
| **/cc other \<player> \[reason]** | `hawn.command.clearchat.other`     | That clear the own chat of another player.                           |

#### - Broadcast -

| **Command(s)**            | **Permission(s)**                 | **Description**                                                                                                                                                                    |
| ------------------------- | --------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **/bacast \<message>**    | `hawn.command.actionbarannouncer` | You can send a broadcast in the actionbar to everyone.                                                                                                                             |
| **/broadcast \<message>** | `hawn.command.broadcast`          | <p>You can send a broadcast to everyone.</p><ul><li>If in the message you put <strong><code>//n</code></strong> then you will skip a line in your message</li></ul>                |
| **/btcast \<message>**    | `hawn.command.titleannouncer`     | <p>You can send a title broadcast to everyone.</p><ul><li>If in the message you put <strong><code>//n</code></strong> then you will skip to the subtitle in your message</li></ul> |
| **/warning \<message>**   | `hawn.command.warning`            | Same as broadcast.                                                                                                                                                                 |

### Player option commands

| **Command(s)**                   | **Permission(s)**                                                                                      | **Description**                                                        |
| -------------------------------- | ------------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------- |
| **/option**                      | `hawn.command.optionplayer.main`                                                                       | This command just shows you the player options commands.               |
| **/option doublejump** or **dj** | <p><code>hawn.command.optionplayer.doublejump</code></p><p><code>hawn.fun.doublejump.double</code></p> | Switching on and off the doublejump.                                   |
| **/option fly**                  | `hawn.command.optionplayer.fly`                                                                        | Switching on and off the fly.                                          |
| **/option jumpboost**            | `hawn.command.optionplayer.jumpboost`                                                                  | Switching on and off the jumpboost.                                    |
| **/option autobc**               | `hawn.command.optionplayer.autobc`                                                                     | Switching on and off the auto broadcast (titles/messages/action bars). |
| **/option speed**                | `hawn.command.optionplayer.speed`                                                                      | Switching on and off the speed.                                        |
| **/option pv**                   | `hawn.command.optionplayer.pv`                                                                         | Switching on and off the possibility to see players.                   |

{% hint style="info" %}
`hawn.command.optionplayer.flyspeed.priorityoptionplayer` and `hawn.command.optionplayer.speed.priorityoptionplayer` is a special permission which allows with the right Hawn configuration, to keep a player's _(flying)_ speed, when switching on and off the flyspeed.
{% endhint %}

### Gamemode commands

| **Command(s)**           | **Permission(s)**                                                                             | **Description**                                                                                 |
| ------------------------ | --------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------- |
| **/gamemode** or **/gm** | <p><code>hawn.command.gamemode.self</code></p><p><code>hawn.command.gamemode.other</code></p> | This command is just another command that manages the change of gamemodes with the Hawn plugin. |
| **/gma**                 | <p><code>hawn.command.gamemode.self</code></p><p><code>hawn.command.gamemode.other</code></p> | This command is just another command to go in gamemode adventure directly.                      |
| **/gmc**                 | <p><code>hawn.command.gamemode.self</code></p><p><code>hawn.command.gamemode.other</code></p> | This command is just another command to go in gamemode creative directly.                       |
| **/gms**                 | <p><code>hawn.command.gamemode.self</code></p><p><code>hawn.command.gamemode.other</code></p> | This command is just another command to go in gamemode survival directly.                       |
| **/gmsp**                | <p><code>hawn.command.gamemode.self</code></p><p><code>hawn.command.gamemode.other</code></p> | This command is just another command to go in gamemode spectator directly.                      |

### World commands

| **Command(s)**                                                               | **Permission(s)**                                                                                                                                                                                                                   | **Description**                                                   |
| ---------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------- |
| **/hw**                                                                      | <p><code>hawn.command.world.general</code></p><p><strong>Gui:</strong></p><p><code>hawn.command.world.modifymain</code></p><p><code>hawn.command.world.modifytime</code></p><p><code>hawn.command.world.modifydifficulty</code></p> | Open the gui to manage worlds.                                    |
| **/hw list**                                                                 | <p><code>hawn.command.world.list</code> or</p><p><code>hawn.command.world.*</code></p>                                                                                                                                              | This command allows you to see the list of worlds.                |
| **/hw info**                                                                 | <p><code>hawn.command.world.info</code>or</p><p><code>hawn.command.world.*</code></p>                                                                                                                                               | This command allows you to see the info of a current world.       |
| **/hw tp \<world>**                                                          | <p><code>hawn.command.world.tp</code> or</p><p><code>hawn.command.world.*</code></p>                                                                                                                                                | This command allows you to teleport yourself to the target world. |
| **/hw delete \<world>**                                                      | <p><code>hawn.command.world.delete</code> or</p><p><code>hawn.command.world.*</code></p>                                                                                                                                            | This command allows you to delete the target world.               |
| **/hw create \<world> \[normal/end/nether] \[flat/amplified/large\_biomes]** | <p><code>hawn.command.world.create</code> or</p><p><code>hawn.command.world.*</code></p>                                                                                                                                            | This command allows you to create a world.                        |
| **/hw import \<world>**                                                      | <p><code>hawn.command.world.import</code> or</p><p><code>hawn.command.world.*</code></p>                                                                                                                                            | This command allows you to import a world.                        |
| **/hw unload \<world>**                                                      | <p><code>hawn.command.world.unload</code> or</p><p><code>hawn.command.world.*</code></p>                                                                                                                                            | This command allows you to unload a world.                        |

### Other commands

| **Command(s)**                                         | **Permission(s)**                                                                                                                                                                                   | **Description**                                                      |
| ------------------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------- |
| **/burn \<player> \<duration>**                        | `hawn.command.burn`                                                                                                                                                                                 | Burns a player defined within a time limit.                          |
| **/checka \<player>**                                  | `hawn.command.checkaccount`                                                                                                                                                                         | Get some informations about the player.                              |
| **/cleargrounditems**                                  | `hawn.command.cleargrounditems`                                                                                                                                                                     | Clear all items on the ground.                                       |
| **/clearinv \[player]**                                | `hawn.command.clearinv`                                                                                                                                                                             | Clear the inventory of a player.                                     |
| **/clearmobs**                                         | `hawn.command.clearmobs`                                                                                                                                                                            | Remove all mobs.                                                     |
| **/day**                                               | `hawn.command.time.day`                                                                                                                                                                             | Put the day on the current world you are in.                         |
| **/ec \[player]**                                      | `hawn.command.enderchest`                                                                                                                                                                           | See a player's enderchest.                                           |
| **/exp \<playername> \<add/set/take/clear> \<amount>** | `hawn.command.exp`                                                                                                                                                                                  | Manage the total of experience point of a player.                    |
| **/feed \<player>**                                    | `hawn.command.feed`                                                                                                                                                                                 | Feed a player.                                                       |
| **/fly \[player]**                                     | <p><code>hawn.command.fly</code></p><p><code>hawn.command.fly.others</code></p>                                                                                                                     | Flying to other skies!                                               |
| **/flyspeed \[number]**                                | `hawn.command.optionplayer.flyspeed`                                                                                                                                                                | Change or enable/disable the flyspeed of a player.                   |
| **/getpos \<player>**                                  | `hawn.command.getpos`                                                                                                                                                                               | Get the position of a player.                                        |
| **/gotop \<player>**                                   | `hawn.command.gotop`                                                                                                                                                                                | This allows you to go to the highest block in your current position. |
| **/hat \[player]**                                     | `hawn.command.hat`                                                                                                                                                                                  | Put a hat to a player.                                               |
| **/heal \<player>**                                    | `hawn.command.heal`                                                                                                                                                                                 | Heal a player.                                                       |
| **/help**                                              | <p><code>hawn.command.help</code></p><p><code>hawn.command.help.&#x3C;categoryname></code></p>                                                                                                      | Open the custom help of hawn.                                        |
| **/invsee \[player]**                                  | `hawn.command.invsee`                                                                                                                                                                               | See player's inventory.                                              |
| **/ip \<player>**                                      | `hawn.command.ip`                                                                                                                                                                                   | Get the ip of a player.                                              |
| **/kickall**                                           | `hawn.command.kickall`                                                                                                                                                                              | Kick all players of the server/lobby.                                |
| **/list \[page number]**                               | `hawn.command.list`                                                                                                                                                                                 | Get the total number of players on the server.                       |
| **/night**                                             | `hawn.command.time.night`                                                                                                                                                                           | Put the night on the current world you are in.                       |
| **/ping \[player]**                                    | <p><code>hawn.command.ping.self</code></p><p><code>hawn.command.ping.other</code></p>                                                                                                               | To know the ping of a player.                                        |
| **/rain**                                              | `hawn.command.weather.rain`                                                                                                                                                                         | Put the rain on the current world you are in.                        |
| **/repair**                                            | `hawn.command.repair`                                                                                                                                                                               | Repair an item easily.                                               |
| **/scoreboard \[set/keep/list] \[scoreboard name]**    | <p><code>hawn.command.scoreboard.toggle</code></p><p><code>hawn.scoreboard.command.set</code></p><p><code>hawn.scoreboard-keep-scoreboard-change</code></p><p><code>hawn.scoreboard-list</code></p> | Toggle on or off the scoreboard and more.                            |
| **/skull \[player]**                                   | `hawn.command.skull`                                                                                                                                                                                | Get player skull.                                                    |
| **/speed \[number]**                                   | `hawn.command.optionplayer.speed`                                                                                                                                                                   | Change or enable/disable speed.                                      |
| **/suicide**                                           | `hawn.command.suicide`                                                                                                                                                                              | Kill yourself.                                                       |
| **/sun** or **/clearw**                                | `hawn.command.weather.sun`                                                                                                                                                                          | Put the sun on the current world you are in.                         |
| **/thunder**                                           | `hawn.command.weather.thunder`                                                                                                                                                                      | Put the storm on the current world you are in.                       |
| **/vanish \[player]**                                  | <p><code>hawn.command.vanish</code></p><p><code>hawn.staff.seevanished</code></p><p><code>hawn.command.vanish.actionbar</code></p><p><code>hawn.command.vanish.other</code></p>                     | Vanish a player                                                      |
| **/workbench \<player>**                               | `hawn.command.workbench`                                                                                                                                                                            | Open player's workbench.                                             |
| **/c**                                                 | :heavy\_multiplication\_x:                                                                                                                                                                          | A shortcut to worldedit.                                             |
| **/1**                                                 | :heavy\_multiplication\_x:                                                                                                                                                                          | A shortcut to worldedit.                                             |
| **/2**                                                 | :heavy\_multiplication\_x:                                                                                                                                                                          | A shortcut to worldedit.                                             |
| **/p**                                                 | :heavy\_multiplication\_x:                                                                                                                                                                          | A shortcut to worldedit.                                             |

## Other Permissions (not command)

### Disable Off Hand (1.9+)

If you want to ignore the fact that you can't use your off (second) hand in game, you will need:

* `hawn.bypass.block.offhand`

### On join - cosmetics

* `hawn.event.onjoin.bypass.firework`\
  To bypass the fact that there're fireworks launching.
* `hawn.event.onjoin.bypass.lightningstrike`\
  To bypass lightning striking.

### On join - messages

* `hawn.event.silentjoin`\
  To connect to the server without sending any message
* `hawn.on-join.custom-message-per-world.<world>`\
  To have permission, if you have the feature enabled, to send a login message in a specific world.
* `hawn.on-join.custom-motd-per-world.<world>`\
  Same thing with motd.
* `hawn.on-join.custommessage.<group>`\
  To have permission, if you have the feature enabled, to send a login message with a specific group.

### On join - Player option

* `hawn.onjoin.playeroption.speed`
* `hawn.onjoin.playeroption.flyspeed`
* `hawn.bypass.gamemodeonjoin`
* `hawn.betweenservers.keepvanish`
* `hawn.onjoin.fly`\
  The purpose of these permissions are if the player has the right to use the options he has already defined instead of the one defined in the configuration file.

### On join - Events

* `hawn.onjoin.potion.blindness`
* `hawn.onjoin.potion.jump`
* `hawn.event.onjoin.bypass.clearinv`
* `hawn.bypass.healthrestore`
* `hawn.bypass.foodrestore`
* `hawn.event.onjoin.bypass.clearchat`
* `hawn.event.spawn.join.vip`

### On quit - messages

* `hawn.event.silentquit`\
  To lleave the server without sending any message
* `hawn.on-quit.custom-message-per-world.<world>`\
  To have permission, if you have the feature enabled, to send a left message in a specific world.
* `hawn.on-quit.custom-motd-per-world.<world>`\
  Same thing with motd.
* `hawn.on-quit.custommessage.<group>`\
  To have permission, if you have the feature enabled, to send a left message with a specific group.

### Autobroadcast

* `hawn.get.autobroadcast_ab`
* `hawn.get.autobroadcastbb`
* `hawn.get.autobroadcast_titles`
* `hawn.get.autobroadcast`\
  These permissions simply allow access to the various autobroadcasts

### Signs

* `hawn.sign.color`
* `hawn.sign.emoji`
* `hawn.sign.command`\
  To allow to put signs with commands
* `hawn.sign.delete`\
  To allow to remove signs with commands on it
* `hawn.sign.interact.<sign name>`\
  To allow the interaction to a particular sign\
  \
  These permissions allow you to have additional features for signs.

### Fun Features

* hawn.fun.jumppads
* hawn.fun.doublejump.double
* hawn.fun.boots.flying\
  These permissions allow you to have access to more features.

### Chat

* `hawn.event.chat.bypass.mutechat`\
  To ignore the chat's mute.
* `hawn.event.chat.bypass.chatdelay`\
  To ignore the chat's delay.
* `hawn.bypass.antiswear`\
  To ignore the chat's antiswear.
* `hawn.antiswear.benotified`\
  To be notified when someone swear
* `hawn.chat.emoji`\
  Can use emojis in the chat (you must have permission for the emoji you want to use)
* `hawn.emoji.<emojiname>`\
  Can use one emoji
* `hawn.chat.can.mention`\
  Can mention in the chat<br>
* `hawn.use.chatcolor.chat.code.<code>`\
  To use color chat in the chat. These colors can be `c e a b 3 d f 7 4 6 2 1 9 5 8 0 l m n o r k`
* `hawn.use.chatcolor.chat.basic.light`\
  Acess to `c e a b 3 d f 7`
* `hawn.use.chatcolor.chat.basic.dark`\
  Acess to `4 6 2 1 9 5 8 0`
* `hawn.use.chatcolor.chat.special.format`\
  Acess to `l m n o r`
* `hawn.use.chatcolor.chat.special.magic`\
  Acess to `k`

### Custom join item

_**If you want to use the custom join item, you need as first permission**_ `hawn.use.customjoinitem`

* `hawn.event.interact.item.playervisibility`
* `hawn.event.interact.item.lobbybow`
* `hawn.use.cji.item.helmet`
* `hawn.use.cji.item.chestplate`
* `hawn.use.cji.item.leggings`
* `hawn.use.cji.item.boots`
* `hawn.use.cji.item.<custom name item>`\
  Only if you enabled **Use\_Permission\_Per\_Item**

For the player visibility item, you need both **hawn.use.cji.item.\<custom name item>** and **hawn.event.interact.item.playervisibility**\
For the player visibility item only, without **Use\_Permission\_Per\_Item** you need **hawn.event.interact.item.playervisibility**

### **Player Events**

* `hawn.event.playeritem.bypass.drop`
* `hawn.event.playeritem.bypass.pickup`
* `hawn.event.playeritem.bypass.moveitem`
* `hawn.event.playeritem.bypass.damageitem`
* `hawn.event.respawn`
* `hawn.event.death.bypass.cleardrop`
* `hawn.event.bypass.player.antimount`

### **Protection World**

* `hawn.bypass.protection.buckets`
* `hawn.event.construct.bypass.place`
* `hawn.event.construct.bypass.break`
* `hawn.bypass.HagingBreakByEntity`
* `hawn.bypass.PlayerInteractEntity`
* `hawn.event.construct.bypass.protectionitemblocks`
* `hawn.bypass.world.event.shears`

### Edit player

* `hawn.editplayer.gamemode`
* `hawn.editplayer.clearinv`
* `hawn.editplayer.tp`
* `hawn.editplayer`

### World system (Gui)

* `hawn.command.world.import`
* `hawn.command.world.tp`
* `hawn.command.world.create`
* `hawn.command.world.*`
* `hawn.command.world.delete`
* `hawn.command.world.modifymain`
* `hawn.command.world.modifytime`
* `hawn.command.world.modifyweather`
* `hawn.command.world.modifydifficulty`

### The rest of the permissions

* `hawn.join.full`\
  The purpose of this command is, if the function is enabled in the configuration files, to allow you to reach the server even if it is full.
* `Hawn.onjoin.keepgamemode` \
  The purpose of the permission is that if there is a function in the configuration files to enable, which allows to block the gamemode, then this permission will allow to ignore this block.
* `hawn.bypass.voidtp`
* `hawn.bypass.foodkeep`
* `hawn.bypass.antidamage`
* `hawn.event.bypass.blockcommands`\
  These permissions will allow to ignore the actual events.
* `hawn.notify.staff.commandblocker`\
  Notify the staff when someone try to use a blocked command
*   `hawn.urgent.spy.adminpanel`

    `hawn.spy.adminpanel`\
    Receive notifications when the admin panel is used
* `hawn.betweenservers.tplastposition`\
  If enabled, you can tp to your last position when you login
* `hawn.event.warn.tps`
* `hawn.antiwdl.bypass`\
  To bypass the anti world downloader
* `hawn.setup`
