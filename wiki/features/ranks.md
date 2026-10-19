---
description: >-
  Ranks for your players: a prefix and a suffix in the tab list, the tab list
  sorted by rank, and %rank_prefix% everywhere in Hawn.
---

# Ranks

Since Hawn 1.4.7, `Ranks.yml` gives each player a rank. The rank shows in the tab list (a prefix before the name, a suffix after it), sorts the tab list (the owner at the top, then the admins...), and can be used in every text of Hawn with `%rank%`, `%rank_prefix%` and `%rank_suffix%`: the chat format, the scoreboards, the header of the tab list...

Since Hawn 1.4.8, the rank also shows above the head of the player (prefix, name in its colour, suffix), and the ranks can come from LuckPerms.

```yaml
Ranks:
  Enable: AUTO
  Default-Rank: default
  Refresh-Seconds: 5
  Source: HAWN
  Tablist:
    Enable: true
  Sorting:
    Enable: true
  Above-Head:
    Enable: true
  Name-Colour:
    Enable: true
  List:
    owner:
      Enable: true
      Prefix: '&4[Owner] &c'
      Suffix: ''
      Priority: 1
      Name-Colour: RED
    admin:
      Enable: true
      Prefix: '&c[Admin] &6'
      Suffix: ''
      Priority: 2
      Name-Colour: GOLD
    vip:
      Enable: true
      Prefix: '&6[VIP] &e'
      Suffix: ''
      Priority: 10
      Name-Colour: YELLOW
    default:
      Enable: false
      Prefix: '&7'
      Suffix: ''
      Priority: 100
      Name-Colour: GRAY
  Players:
    Notch: vip
  Worlds:
    minigames:
      Tablist: false
```

## The rank of a player

1. A rank given by hand in `Players` (`<player>: <rank>`).
2. Else, the rank of the permission `hawn.rank.<rank>`. With several, the one with the lowest `Priority`.
3. Else, the `Default-Rank` (off in the example: turn its `Enable` to `true` to give it to everyone else).

The permissions are checked again every `Refresh-Seconds`: a rank given in LuckPerms shows without a rejoin.

{% hint style="info" %}
`hawn.rank.<rank>` is not given to the operators: an operator doesn't get every rank at once. Give it to them like to the others (`/lp user <player> permission set hawn.rank.owner`).
{% endhint %}

## The options

* `Enable`: `AUTO` (by default), `true` or `false`.
  * `AUTO`: when TAB or NametagEdit is installed, Hawn leaves the tab list to it (the console says it at startup). The ranks and `%rank_prefix%` still work in the texts of Hawn.
  * `true`: the tab list of Hawn, even with these plugins.
  * `false`: no rank at all, `%rank_prefix%` is empty.
* `Source`: `HAWN` (the ranks of this file) or `LUCKPERMS` (see [LuckPerms](#luckperms)).
* `Tablist.Enable`: the prefix and the suffix in the tab list.
* `Sorting.Enable`: the tab list sorted by `Priority`, the lowest first. The players without a rank come last.
* `Above-Head.Enable`: the prefix and the suffix above the head (since 1.4.8).
* `Name-Colour.Enable`: the name above the head in the colour of the rank (since 1.4.8).
* Each rank: `Enable`, `Prefix`, `Suffix` (colours, `&#RRGGBB`), `Priority`, `Name-Colour`.
  * `Name-Colour`: one of the 16 colours (`RED`, `GOLD`, `YELLOW`, `GREEN`, `AQUA`, `BLUE`, `LIGHT_PURPLE`, `GRAY`, `WHITE`, `DARK_RED`...). Empty or missing: the last colour of the prefix (a hex colour gives the nearest of the 16). `NONE`: no colour. Above the head, Minecraft only has these 16 colours for the name; the prefix and the suffix keep their hex colours.
* `Worlds.<world>`: `Tablist`, `Sorting`, `Above-Head` or `Name-Colour` false for the players of this world. A world that is not listed keeps everything.

Change the file, then `/hawn reload`.

## Commands

| Command                            | Description                                                         |
| ---------------------------------- | ------------------------------------------------------------------- |
| `/hawn rank set <player> <rank>`   | Gives a rank by hand (written in `Ranks.Players`), shown at once.    |
| `/hawn rank remove <player>`       | Removes the rank given by hand: the permissions decide again.       |
| `/hawn rank info <player>`         | The rank of the player and where it comes from (by hand, permission, LuckPerms, default rank). |
| `/hawn rank list`                  | The ranks, their priority and the players online who have them.    |

Permission: `hawn.admin.command.rank` (with `hawn.admin`). From the console too.

## LuckPerms

With `Source: LUCKPERMS`, the rank of a player is their primary group in LuckPerms:

* the prefix and the suffix of LuckPerms (`/lp group admin meta setprefix 100 "&c[Admin] "`);
* the weight of the group for the order: the highest weight first (`/lp group admin setweight 50`);
* the colour of the name: the `Name-Colour` of a rank of `Ranks.List` with the name of the group, else the meta `name-colour` of LuckPerms (`/lp group admin meta set name-colour RED`), else the last colour of the prefix.

A change in LuckPerms (a group, a prefix) shows at once, without a rejoin. `/hawn rank set` doesn't write anything then: change the group in LuckPerms. A player that LuckPerms doesn't know yet gets the ranks of `Ranks.yml`.

## Placeholders

| Placeholder       | Value                       |
| ----------------- | --------------------------- |
| `%rank%`          | The name of the rank: `owner` |
| `%rank_prefix%`   | Its prefix                  |
| `%rank_suffix%`   | Its suffix                  |

With PlaceholderAPI, the other plugins can use them too: `%hawn_rank_prefix%` (see [In other plugins](../reference/placeholders.md#in-other-plugins)).

A chat format with the rank:

```yaml
# Events/Chat.yml
Chat-Format:
  Format: '%rank_prefix%%player%&8: &f%message%'
```

## How it works

Above the heads, Minecraft shows the prefix and the suffix of the team of the player. Hawn sends these teams to the players in packets only: nothing is created on the server, no team stays after a crash, and the teams of the other plugins (NPCs, mini-games...) are not changed on the server. A player who joins gets the teams of the players already there.

The order of the tab list: on 1.21.2 and later, Minecraft has an order for it, and Hawn uses it. Before 1.21.2, the teams sort it too.

A plugin that colours the names above the heads with its own teams can lose these colours for the players who have a rank: turn `Above-Head.Enable`, `Name-Colour.Enable` (and before 1.21.2 `Sorting.Enable`) off then, or use `Enable: AUTO` with TAB or NametagEdit.

The [player hider](custom-join-items.md#player-visibility-special-hideplayers) uses the ranks too: its second state shows only the ranks of `Staff-And-VIP-Ranks` (owner, admin and vip by default).
