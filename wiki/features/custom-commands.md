---
description: >-
  Create your own commands (/discord, /rules, /store...) and a fully custom
  /help with categories and pages.
---

# Custom commands and /help

## Custom commands

`CustomCommand.yml` lets you create as many commands as you want. They send messages and run [actions](../basics/actions.md): perfect for `/discord`, `/rules`, `/store`, `/vote`...

```yaml
commands-general:
  enable: true
commands:
  rules:                                   # the key, used by [customcommand-player]
    enable: true
    command: /rules                        # what players type
    permission:
      enable: true
      message: hawn.command.rules          # the permission
    no-permission-message-enable: true
    Cooldown:
      enable: true
      Ticks: 100                           # 20 ticks = 1 second
      messages:
      - '%prefix% &7Please wait before using the command'
    message:
    - '<--center--> °·..·°¯°·._.·  &e&lRULES&r ·._.·°¯°·..·°'
    - ''
    - ' &8→ &7Please be nice'
    - ' &8→ &7Don''t cheat'
    - ''
    - '<perm>server.vip</perm> <--center--> &c&nVIP'
    - '<perm>server.vip</perm> &bOnly VIPs see this part'
    - '[send-title[50]]: &6Read the rules //n &7please'
    - '[sounds]: BLOCK_ANVIL_LAND'
```

| Key                             | Description                                                                                   |
| ------------------------------- | --------------------------------------------------------------------------------------------- |
| `enable`                        | `false` = the command does nothing.                                                           |
| `command`                       | The full command, with the `/`. It can contain spaces (see sub-commands).                     |
| `permission.enable`             | Require a permission.                                                                         |
| `permission.message`            | The permission name (the key is called `message` for historical reasons).                     |
| `no-permission-message-enable`  | Send the "no permission" message when the player doesn't have it.                             |
| `Cooldown`                      | Optional. Time to wait before using the command again, and the message sent meanwhile.        |
| `message`                       | The lines sent: messages, [JSON](../basics/message-format.md#json-messages), `<perm>`, `<world>`, `<--center-->` and [actions](../basics/actions.md). |

{% hint style="info" %}
Custom commands are not registered commands: they don't appear in the tab completion, and a command with the same name in another plugin is overridden by Hawn. Changes are applied with `/hawn reload`.
{% endhint %}

### Sub-commands

The command is compared with the **whole line** typed by the player (case doesn't matter). To create sub-commands, create one entry per line:

```yaml
  shop:
    enable: true
    command: /shop
    permission:
      enable: false
      message: none
    no-permission-message-enable: false
    message:
    - '&eType &6/shop ranks &eor &6/shop cosmetics'
  shop-ranks:
    enable: true
    command: /shop ranks
    permission:
      enable: false
      message: none
    no-permission-message-enable: false
    message:
    - 'json:{"text":"§6Click to see the ranks","clickEvent":{"action":"open_url","value":"https://store.example.com/ranks"}}'
```

### Running a custom command from elsewhere

Use the `[customcommand-player]: <key>` action, for example in a join item or a sign:

```yaml
Command-List:
- '[customcommand-player]: rules'
```

The permission and the cooldown of the custom command are checked.

### Ideas

```yaml
  # Send the player to another server of the network
  survival:
    enable: true
    command: /survival
    permission: { enable: false, message: none }
    no-permission-message-enable: false
    message:
    - '&7Connecting to the survival...'
    - '[bungee]: survival'

  # A small kit with a cooldown of 1 hour
  starter:
    enable: true
    command: /starter
    permission: { enable: true, message: server.kit.starter }
    no-permission-message-enable: true
    Cooldown:
      enable: true
      Ticks: 72000
      messages:
      - '&cYou already took your kit, come back later'
    message:
    - '[command-console]: give %player% bread 16'
    - '&aEnjoy your kit!'
```

{% hint style="warning" %}
Cooldowns are kept in memory: they are reset when the server restarts or Hawn reloads.
{% endhint %}

## /help

Hawn replaces `/help` (and `/?`) by a fully custom help, with categories and pages. Configuration: `Commands/Help.yml`.

```yaml
Help-Command:
  Enable: true
  Use-Permissions: true
  Categories:
    default: lobbyhelp                 # the category of /help without argument
    lobbyhelp:
      '1':
      - '&8----------------------------------'
      - ' - &e&lHelp sample page'
      - 'json:{"text":"§eClick here to go to page 2","clickEvent":{"action":"run_command","value":"/help lobbyhelp 2"}}'
      - '&8----------------------------------'
      '2':
      - 'Lobbyhelp page 2'
    faction:
      '1':
      - 'Faction help page'
DISABLE_THE_COMMAND_COMPLETELY: false
```

| Command                     | Shows                                                |
| --------------------------- | ---------------------------------------------------- |
| `/help`                     | Page `1` of the default category.                    |
| `/help <page>`              | A page of the default category.                      |
| `/help <category>`          | Page `1` of a category.                              |
| `/help <category> <page>`   | A page of a category.                                |

* Create as many categories and pages as you want. Pages are named `'1'`, `'2'`... (with quotes).
* Every line supports the [message format](../basics/message-format.md) and [actions](../basics/actions.md).

{% hint style="warning" %}
`Use-Permissions` is `true` by default: players need **`hawn.command.help`** and **`hawn.command.help.<category>`** (for example `hawn.command.help.lobbyhelp`). Set it to `false` if everyone should see the help.
{% endhint %}

To keep the `/help` of another plugin or of the server, set `DISABLE_THE_COMMAND_COMPLETELY: true` and restart.
