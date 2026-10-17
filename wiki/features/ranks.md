---
description: >-
  Ranks for your players: a prefix and a suffix in the tab list, the tab list
  sorted by rank, and %rank_prefix% everywhere in Hawn.
---

# Ranks

Since Hawn 1.4.7, `Ranks.yml` gives each player a rank. The rank shows in the tab list (a prefix before the name, a suffix after it), sorts the tab list (the owner at the top, then the admins...), and can be used in every text of Hawn with `%rank%`, `%rank_prefix%` and `%rank_suffix%`: the chat format, the scoreboards, the header of the tab list...

The ranks above the heads of the players come in Hawn 1.4.8, with the ranks of LuckPerms.

```yaml
Ranks:
  Enable: AUTO
  Default-Rank: default
  Refresh-Seconds: 5
  Tablist:
    Enable: true
  Sorting:
    Enable: true
  List:
    owner:
      Enable: true
      Prefix: '&4[Owner] &c'
      Suffix: ''
      Priority: 1
    admin:
      Enable: true
      Prefix: '&c[Admin] &6'
      Suffix: ''
      Priority: 2
    vip:
      Enable: true
      Prefix: '&6[VIP] &e'
      Suffix: ''
      Priority: 10
    default:
      Enable: false
      Prefix: '&7'
      Suffix: ''
      Priority: 100
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
* `Tablist.Enable`: the prefix and the suffix in the tab list.
* `Sorting.Enable`: the tab list sorted by `Priority`, the lowest first. The players without a rank come last.
* Each rank: `Enable`, `Prefix`, `Suffix` (colours, `&#RRGGBB`), `Priority`.
* `Worlds.<world>`: `Tablist: false` or `Sorting: false` for the players of this world. A world that is not listed keeps everything.

Change the file, then `/hawn reload`.

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

## How the tab list is sorted

On 1.21.2 and later, Minecraft has an order for the tab list: Hawn uses it. Before 1.21.2, Hawn sends teams to the players, in packets only: nothing is created on the server, no team stays after a crash, and the teams of the other plugins are not changed on the server. These teams have no prefix and no colour; a plugin that colours the names above the heads with teams can lose these colours on these versions: turn `Sorting.Enable` off then.
