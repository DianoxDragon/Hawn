---
description: Text floating at a place, in the language and with the placeholders of each player.
---

# Holograms

Since Hawn 1.6, a hologram shows lines of text at a place of a world. It is sent in packets to each player near it: there is no entity on the server (nothing to clean after a crash, nothing that ticks), and every player sees their own text, with their placeholders and their [language](../help/translating.md).

{% hint style="warning" %}
The holograms need the plugin [PacketEvents](https://modrinth.com/plugin/packetevents) (2.14 or newer for 26.x). Without it, Hawn writes a warning in the console when `Holograms/` has holograms, and `/hawn hologram` tells you to install it.
{% endhint %}

On 1.19.4 and newer, a hologram is one text display: its lines can have a background, a shadow, a scale, and turn to face the player. Before 1.19.4, each line is an invisible armor stand with a name.

## Create a hologram

Stand where the hologram must be, then:

```
/hawn hologram create welcome &6Welcome on our server
/hawn hologram addline welcome &7%online% players online
```

The hologram is at the height of your eyes. Every change is saved in `Holograms/<name>.yml` at once, and shown to the players near it.

| Command | What it does |
| ------- | ------------ |
| `/hawn hologram create <name> [text]` | Creates a hologram where you are, with one line. |
| `/hawn hologram delete <name>` | Deletes it and its file. |
| `/hawn hologram movehere <name>` | Moves it where you are. |
| `/hawn hologram addline <name> <text>` | Adds a line at the end. |
| `/hawn hologram setline <name> <line> <text>` | Changes a line (1 is the first one). |
| `/hawn hologram removeline <name> <line>` | Removes a line. |
| `/hawn hologram list` | The holograms of `Holograms/`, those turned off with `(off)`. |
| `/hawn hologram enable <name>` | Turns it on (`Enable: true`) and shows it. |
| `/hawn hologram disable <name>` | Turns it off (`Enable: false`) and hides it: its file is kept. |

`/hawn holo` works too. The permission is `hawn.admin.command.hologram`.

## A hologram file

Each file of `Holograms/` is a hologram, named after its file. Edit it, then `/hawn reload`.

```yaml
Location:
  World: world
  X: 0.5
  Y: 66.0
  Z: 0.5
Lines:
- '&6&l{lang_text.hologram-title}'
- '&7%online% players online'
- '{anim_arrows}'
View-Distance: 32
Refresh: 20
Billboard: CENTER
Background: DEFAULT
Shadow: true
Scale: 1.0
Line-Width: 200
Line-Height: 0.28
Permission: ''
Animations:
  arrows:
    refresh-time-ticks: 10
    text:
    - '&e> &6/spawn &e<'
    - '&6> &e/spawn &6<'
```

| Option | What it does |
| ------ | ------------ |
| `Enable` | `false` hides the hologram (the example written the first time is off: `/hawn hologram enable example` shows it). |
| `Lines` | The lines: [colours](../basics/message-format.md), the [placeholders](../reference/placeholders.md) of Hawn and of PlaceholderAPI, `{lang_<key>}` and `{anim_<name>}`. |
| `View-Distance` | The players closer than this (in blocks) see the hologram. |
| `Refresh` | Ticks between two updates of the placeholders (20 = one second). `0`: the text is never updated. Only what changed is sent again. |
| `Billboard` | 1.19.4+: `CENTER` turns to face the player, `VERTICAL` turns around, `HORIZONTAL` tilts, `FIXED` doesn't move. |
| `Background` | 1.19.4+: `DEFAULT` (the grey of Minecraft), `NONE`, or a colour `#AARRGGBB` (`#80000000`: half transparent black). |
| `Shadow` | 1.19.4+: a shadow under the letters. |
| `Scale` | 1.19.4+: the size of the text (`2.0`: twice bigger). |
| `Line-Width` | 1.19.4+: the width in pixels after which a line is cut. |
| `Line-Height` | Before 1.19.4: the space between two lines. |
| `Permission` | Only the players with this permission see the hologram (empty: everyone). |

The animations work like those of the [menus](menus.md#animations): every player sees the same frame at the same time.

## Folia

The holograms work on Folia: the packets are sent from the thread of each player, and nothing is created in the world.
