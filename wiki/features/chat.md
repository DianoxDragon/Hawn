---
description: >-
  Colours and hex colours in the chat, emojis, mentions, anti-swear, global
  mute, chat delay and clear chat.
---

# Chat

Most chat options are in `Events/Chat.yml`. The chat commands have their own files in `Commands/`.

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

{% hint style="warning" %}
With `Chat-Highlight` enabled, Hawn sends the chat message itself (so that each player sees their own highlight). Chat format plugins that rely on the chat event may then show the message differently for mentions.
{% endhint %}

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

`%DELAY%` shows the current delay.

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
