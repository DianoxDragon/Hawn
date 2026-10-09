---
description: >-
  Colours, hex colours, JSON, centred lines, per-line permissions and worlds:
  the syntax that works in every Hawn message.
---

# Messages, colours and formatting

Almost every message in Hawn is a **list of lines**. Each line is sent one after the other, and each line can use the features below.

```yaml
Messages:
- '&8&m-------------------------'
- '<--center--> &6Welcome &e%player%'
- '<perm>server.vip</perm> &bThanks for supporting the server!'
- '[sounds]: ENTITY_PLAYER_LEVELUP'
```

{% hint style="info" %}
To disable a message, keep the key and leave an empty list: `Messages: []`. Most features also have an `Enable` option next to their messages.
{% endhint %}

## Colours

### Classic colour codes

Put `&` followed by a code just before the text to colour. `§` works too.

| Code | Colour      | Code | Colour       | Code | Format        |
| ---- | ----------- | ---- | ------------ | ---- | ------------- |
| `&0` | Black       | `&8` | Dark gray    | `&l` | **Bold**      |
| `&1` | Dark blue   | `&9` | Blue         | `&m` | ~~Strike~~    |
| `&2` | Dark green  | `&a` | Green        | `&n` | Underline     |
| `&3` | Dark aqua   | `&b` | Aqua         | `&o` | _Italic_      |
| `&4` | Dark red    | `&c` | Red          | `&k` | Magic (obfuscated) |
| `&5` | Dark purple | `&d` | Light purple | `&r` | Reset         |
| `&6` | Gold        | `&e` | Yellow       |      |               |
| `&7` | Gray        | `&f` | White        |      |               |

```yaml
- 'This word is &4red&r and this one is &a&lbold green'
```

### Hex colours

Any RGB colour, in two formats:

* `&#RRGGBB`, for example `&#3BD1C4`
* `#<RRGGBB>`, for example `#<3BD1C4>`

```yaml
- '&#FF8800Orange text, #<3BD1C4>turquoise text'
```

You can pick a colour on [htmlcolorcodes.com](https://htmlcolorcodes.com/). Players can also use hex colours in the chat with the `hawn.use.chatcolor.chat.hex` permission, see [Chat](../features/chat.md#colours-in-the-chat).

## Centred lines

Add `<--center-->` at the start of the line (after `<world>` and `<perm>` if you use them):

```yaml
- '<--center--> &e&lANNOUNCEMENT'
```

Hawn computes the width of the text, bold included, and adds spaces to centre it in the default chat window.

## JSON messages

Start the line with `json:` and write a [raw JSON text](https://minecraft.wiki/w/Raw_JSON_text_format). This lets you add clickable links, hover texts and commands:

```yaml
- 'json:{"text":"§eClick here to join our Discord","clickEvent":{"action":"open_url","value":"https://discord.com/"},"hoverEvent":{"action":"show_text","value":"Open the link"}}'
- 'json:["",{"text":"Run ","color":"gray"},{"text":"/spawn","color":"gold","clickEvent":{"action":"run_command","value":"/spawn"}}]'
```

{% hint style="info" %}
JSON generators such as [minecraft.tools](https://minecraft.tools/en/tellraw.php) write the JSON for you. Copy only the JSON part (what comes after `tellraw @a`).
{% endhint %}

Placeholders work inside JSON. `&` colour codes and `<--center-->` do not: use the JSON `"color"` field or `§` codes instead.

## MiniMessage (Paper)

Since Hawn 1.4, on **Paper 1.19.1 or newer**, the messages, titles and action bars of Hawn can use the [MiniMessage](https://docs.advntr.dev/minimessage/format.html) tags: gradients, rainbows, hover texts, clicks... A line with at least one tag is read by MiniMessage, and you can still mix it with `&` codes and hex colours.

```yaml
- '<gradient:#FF8800:#3BD1C4>Welcome on the server</gradient>'
- '&7Join our <hover:show_text:"Open the link"><click:open_url:https://discord.com/>&bDiscord</click></hover>'
- '<rainbow>Rainbow text</rainbow> &8| &7Type <click:run_command:/spawn><gold>/spawn</gold></click>'
- '[send-title]: <gradient:red:blue>NEW SEASON</gradient> //n &7Starts today'
```

* On Spigot, and on Paper older than 1.19.1, nothing changes: the tags stay as text, use `json:` lines for clicks and hover texts there.
* A `&` colour code ends the bold, italic... like it always did, without closing a hover or a click around it.
* Lines with `<--center-->` and `json:` lines are not read by MiniMessage.
* What a player types (a `/broadcast` text, a reason, a warp name...) is never read as tags: a player can't add a click to a message.

## A line for some players only

### Per permission

Start the line with `<perm>the.permission</perm>`: only players with this permission receive it.

```yaml
- 'Everyone sees this line'
- '<perm>server.vip</perm> Only the VIPs see this line'
- '<perm>server.vip</perm> [send-title]: Hello VIP //n &6Welcome back'
```

### Per world

Start the line with `<world>world_name</world>`: only players in this world receive it. It must be **the very first thing of the line**, before `<perm>`.

```yaml
- '<world>world</world> You are in the lobby'
- '<world>world_nether</world> <perm>server.vip</perm> VIP in the nether'
```

{% hint style="warning" %}
Keep exactly **one space** after `</perm>` and `</world>`. Without it, the tag is not removed from the message.
{% endhint %}

## Actions

A line can also be an **action** instead of a message: send a title, play a sound, run a command, teleport to a spawn, connect to another server... For example:

```yaml
- '[send-title]: &6Welcome //n &7on the server'
- '[command-console]: give %player% diamond 1'
- '[bungee]: survival'
```

The full list is on the [Actions](actions.md) page.

## Placeholders

`%player%`, `%ping%`, `%prefix%`... and every PlaceholderAPI placeholder if it is installed. See [Placeholders](../reference/placeholders.md).

`%prefix%` is defined in `Messages/<language>/General.yml` → `General.Prefix` (default: ` &3Hawn &7|`).

## Order of the tags

When you combine everything, use this order:

```
<world>world</world> <perm>permission</perm> <--center--> your message
<world>world</world> <perm>permission</perm> [action]: value
```

## YAML reminders

* Put the line between single quotes `'...'` as soon as it contains `&`, `:`, `#`, `[`, `{`, `%` or starts with a special character.
* Inside single quotes, write an apostrophe twice: `'Don''t forget the rules'`.
* Indent with spaces, never with tabs.
