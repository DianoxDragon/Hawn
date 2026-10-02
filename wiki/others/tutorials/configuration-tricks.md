# Configuration tricks

### How works center message and permission per line ?

To put a permission you have to put this variable (**\<perm>**&#x65;res&#x72;**\</perm>**) at the beginning of the line, At each line where there must be have a permission.

```yaml
Message1:
- 'This line don''t have permission'
- '<perm>perm.perm</perm> This line has a permission'
- '<perm>perm.perm</perm> [send-title]: This line has a permission //n with a custom event'
```

&#x20;To put a centered message just add **<--center-->**

```yaml
Message2:
- 'This line is not centered'
- '<--center--> This line is centered'
- '<perm>perm.perm</perm> <--center--> This line is centered with a permission'
```

### How make a line for one world only ?

To put a world for one line you have to put this variable (**\<world>**&#x65;res&#x72;**\</world>**) at the beginning of the line (and before **\<perm>**&#x65;res&#x72;**\</perm>**), At each line where there must be have a permission

```yaml
Message2:
- '<world>world</world> <perm>serer</perm> the line'
- '<world>world</world> the line2'
```

### How the animated tablist works ?

It's all in the file, you can create your own custom animations, and more\
To add a custom animation you need to add this **{anim\_animation name}**\
\
_**For example:**_

```yaml
Tablist:
  enable: true
  refresh-time-ticks: 1
  header:
    enabled: true
    message:
   - '{anim_separator}'
  footer:
    enabled: false
    message: []
Animations:
  separator:
    refresh-time-ticks: 2
    text:
   - '&e&l>> &8&m-------------------&r &e&l<<'
    - '&7&l>&e&l> &8&m-------------------&r &e&l<&7&l<'
    - '&7&l>> &8&m-------------------&7 &7&l<<'
    - '&7&l>> &8&m-------------------&7 &7&l<<'
    - '&7&l>> &8&m-------------------&7 &7&l<<'
    - '&7&l>> &8&m-------------------&7 &7&l<<'
    - '&7&l>> &8&m-------------------&7 &7&l<<'
    - '&7&l>> &8&m-------------------&7 &7&l<<'
    - '&7&l>> &8&m-------------------&7 &7&l<<'
    - '&7&l>> &8&m-------------------&7 &7&l<<'
    - '&e&l>&7&l> &8&m-------------------&7 &7&l<&e&l<'
    - '&e&l>> &8&m-------------------&7 &e&l<<'
```

&#x20;I added only in the header an animation called **separator**, so you have to put **{anim\_separator}**

### How to set a cooldown for a custom command?

It's simple just look at this configfile

```yaml
commands-general:
  enable: true
commands:
  rules:
    enable: true
    command: /rules
    permission:
      enable: true
      message: hawn.command.rules
    Cooldown:
      enable: true
      Ticks: 100
      messages:
      - '%prefix% &7Please wait before using the command'
    no-permission-message-enable: true
    message:
    - 'a message'
```

You have to add this part in the config file

```yaml
Cooldown:
  enable: true
  Ticks: 100
  messages:
  - '%prefix% &7Please wait before using the command'
```
