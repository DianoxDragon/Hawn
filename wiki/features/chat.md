---
description: >-
  Chat format, chat per group of worlds, colours and hex colours, emojis,
  mentions, anti-swear, anti-spam, global mute, chat delay and clear chat.
---

# Chat

Most chat options are in `Events/Chat.yml`. The chat commands have their own files in `Commands/`.

## Chat format

Since Hawn 1.4, Hawn can give the chat its format: a prefix, the name, a separator and the message.

```yaml
Chat-Format:
  Enable: AUTO
  Format: '&7%player% &8» &f%message%'
```

`%message%` is where the message goes. Every [placeholder](../reference/placeholders.md) works, with PlaceholderAPI too: `'%luckperms_prefix%%player% &8» &f%message%'` shows the rank of the player (LuckPerms and the `luckperms` expansion of PlaceholderAPI). `%player_displayname%` is the name given by the other plugins (nickname...). On Paper 1.19.1+, the format can use [MiniMessage](../basics/message-format.md#minimessage-paper).

| `Enable` | What Hawn does |
| -------- | -------------- |
| `AUTO` (default) | Uses its format, unless a known chat plugin is installed (EssentialsChat, ChatControl, VentureChat, LPC, DeluxeChat, CarbonChat...). A chat plugin that changes the format after Hawn wins, and the console says it once. |
| `true` | Always uses its format, after the other plugins. |
| `false` | Never changes the format: the server or your chat plugin keeps it. |

## Chat per group of worlds

With `Per-World-Chat.Enable: true`, the players only see the messages of the players in the same group of worlds. The worlds in no group talk together.

```yaml
Per-World-Chat:
  Enable: false
  Groups:
    main:
      Worlds:
      - world
    nether_end:
      Worlds:
      - world_nether
      - world_the_end
      Chat-Format:
        Format: '&c[Nether] &7%player% &8» &f%message%'
```

A group can change any option of this page for itself: write it in the group, with the same path as in `Events/Chat.yml`. What the group doesn't set comes from the rest of the file. For example:

```yaml
    minigames:
      Worlds:
      - minigames
      Chat-Format:
        Format: '&a[Games] &7%player% &8» &f%message%'
      Anti-Swear:
        List:                     # this list replaces the main one in the group
        - noob
      Chat-Mention:
        Enable: false
      Anti-Spam:
        Caps:
          Enable: false
      Mute: false                 # true: nobody talks in this group (except hawn.event.chat.bypass.mutechat)
      Chat-Delay:                 # its own delay instead of the one of /delaychat
        Enable: true
        Seconds: 10
```

The welcome setup writes the lobby world in the `main` group. The groups are yours: Hawn never adds them back if you delete them.

## Colours in the chat

```yaml
Chat-Color-Player:
  Enable: true
  Per-Color-Permission: false
```

With `Per-Color-Permission: false`, colours are given by groups:

| Permission                                 | Codes                       |
| ------------------------------------------ | --------------------------- |
| `hawn.use.chatcolor.chat.basic.light`      | `&c &e &a &b &3 &d &f &7`   |
| `hawn.use.chatcolor.chat.basic.dark`       | `&4 &6 &2 &1 &9 &5 &8 &0`   |
| `hawn.use.chatcolor.chat.special.format`   | `&l &m &n &o &r`            |
| `hawn.use.chatcolor.chat.special.magic`    | `&k`                        |

With `Per-Color-Permission: true`, every code has its own permission: `hawn.use.chatcolor.chat.code.<code>`, for example `hawn.use.chatcolor.chat.code.c` for `&c`.

**Hex colours** (`&#RRGGBB` and `#<RRGGBB>`) need `hawn.use.chatcolor.chat.hex`, in both modes.

## Emojis

Players type a word like `:heart:` and it is replaced by `❤`.

```yaml
# Events/Chat.yml
Chat-Emoji-Player:
  Enable: true
  Emojis-list:
    Option:
      Gui:
        Enable: true
        Use_Permission: false     # true = /emoji needs hawn.command.emoji
        Title: '&6Emojis list'
        Close-Gui:
          Enable: true
          Title: '&cClose the Gui'
          Material: BARRIER
          Lore: [...]
```

The emojis are defined in `Cosmetics-Fun/Utility/Emojis-List.yml`:

```yaml
Emojis-list:
  Heart:
    Enable: true
    Use_Permission: true          # true = needs hawn.emoji.Heart
    Emoji:
      Shape: ❤                    # what replaces the words
      Replace_With:               # the words players type
      - ':heart:'
      - '<3'
    Gui:                          # how it looks in /emoji
      Title: '&cHeart'
      Material: REDSTONE
      Lore:
      - '&bUse :heart: in the chat'
```

* The player needs **`hawn.chat.emoji`** to use emojis at all.
* With `Use_Permission: true`, each emoji also needs **`hawn.emoji.<emoji key>`** (`hawn.emoji.Heart` above).
* Add as many emojis as you want. The replacement ignores upper and lower case.
* `Gui.Material` accepts a player head with `Skull-Name` (`%player%` for the player's own head).

`/emoji` opens a menu with every emoji the player can use. Emojis also work on signs, see [Lobby fun](lobby-fun.md#coloured-signs-and-emoji-signs).

{% hint style="info" %}
The Emojis-List file is saved in UTF-8. If your emojis show as `?`, check that your editor saves in UTF-8 too.
{% endhint %}

## Mentions

Write `@PlayerName` in the chat to mention someone. Only the players who receive the message can be mentioned: a muted player (by Hawn or another plugin such as LiteBans) or a message sent in a chat channel stays where it is.

```yaml
Chat-Mention:
  Enable: true
  Mentionned:
    Self-Mention:
      Enable: true                # can players mention themselves?
    Chat-Highlight:
      Enable: true
      Highlighting: '&6&l'        # colour of the @name for the mentioned player
    Sound:
      Enable: true
      Sound: BLOCK_NOTE_HARP
      Volume: 1
      Pitch: 1
    Send-Message:
      Enable: true
      Messages:
      - '%prefix% You have been mentionned by %sender%'
    Send-ActionBar:
      Enable: true
      Options:
        Message: '&bYou have been mentionned by &e&l%sender%'
        Time-Stay: 150
    Send-Title:
      Enable: true
      Options:
        Enable: true
        FadeIn: 20
        Stay: 150
        FadeOut: 20
        Title: '&6✉ &bYou have been &ementionned&6 ✉'
        SubTitle: '&bAnswer to &e%sender%'
```

The player who writes the message needs `hawn.chat.can.mention`. `%sender%` is the player who mentioned, `%player%` the mentioned player.

With `Chat-Highlight` enabled, the mentioned player gets their own copy of the message, with the format of the chat; the other players get the normal message.

## Anti-swear

```yaml
Anti-Swear:
  Enable: true
  Bypass: true                    # hawn.bypass.antiswear can swear
  Replace-Message:
    Enable: true
    Message:
    - '*****'
  Notify-Staff: true              # hawn.antiswear.benotified receives a warning
  List:
  - badword
  - another bad word
```

Every word of `List` found in a message (upper or lower case) is replaced by `Message`. The warning sent to the staff is `Anti-Swear.Notify-Staff` in `Messages/<language>/Messages.yml` (`%player%`, `%message%`).

## Anti-spam

Since Hawn 1.4:

```yaml
Anti-Spam:
  Enable: true
  Bypass: true                    # hawn.bypass.antispam is not checked
  Repeat:
    Enable: true
    Seconds: 30                   # the same message can't be sent again during 30 seconds
  Caps:
    Enable: true
    Min-Letters: 6                # shorter messages are not checked
    Max-Percent: 70               # above 70 % of capital letters...
    Block: false                  # ...the message is put in lower case (true: it is not sent)
```

The same message means the same letters and numbers: the colours, the upper case, the spaces and the punctuation don't count. The mentions (`@Name`) are kept as typed. The messages are `Anti-Spam.Repeat` and `Anti-Spam.Caps` in `Messages/<language>/Messages.yml`.

## Global mute

`/globalmute` (alias `/gmute`), permission `hawn.command.mutechat`.

* `/gmute`: mutes or unmutes the chat for everyone.
* `/gmute <minutes>`: mutes the chat for some minutes.

```yaml
# Commands/MuteChat.yml
MuteChat:
  Enable: true
  Mute:
    Enable: false        # current state, changed by the command
    Bypass: false        # true = hawn.event.chat.bypass.mutechat can still talk
```

## Chat delay

`/delaychat <seconds>` (alias `/dchat`), permission `hawn.command.delaychat`: players must wait this delay between two messages.

```yaml
# Commands/DelayChat.yml
DelayChat:
  Enable: true
  Delay:
    Enable: true
    Delay_By_Default: 3  # delay at startup, in seconds
    Bypass: true         # hawn.event.chat.bypass.chatdelay has no delay
```

`%DELAY%` shows the current delay. A [group of worlds](#chat-per-group-of-worlds) can have its own delay with `Chat-Delay`.

## Clear chat

`/cc`, see `Commands/ClearChat.yml`:

| Command                          | Permission                          | Description                                 |
| -------------------------------- | ----------------------------------- | ------------------------------------------- |
| `/cc`                            | `hawn.command.clearchat.help`       | Shows the help.                             |
| `/cc c [reason]`                 | `hawn.command.clearchat.normal`     | Clears the chat of everyone.                |
| `/cc a [reason]`                 | `hawn.command.clearchat.anonymous`  | Clears the chat without showing who did it. |
| `/cc o`                          | `hawn.command.clearchat.own`        | Clears your own chat.                       |
| `/cc other <player> [reason]`    | `hawn.command.clearchat.other`      | Clears the chat of one player.              |

```yaml
ClearChat:
  Enable: true
  Lines-To-Clear: 150
  Anonymous:
    Enable: true
    Message-Clear: true     # send the "chat cleared" message
    Use_Permission: true    # false = no permission needed for this mode
  Normal: ...
  Own:
    Use_Permission: false   # everyone can clear their own chat
  Other:
    Use_Permission: false
```
