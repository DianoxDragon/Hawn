---
description: The languages of Hawn, the language of each player, your own texts in several languages.
---

# Translating Hawn

Every message of Hawn is in `plugins/Hawn/Messages/<language>/`. Seven languages are provided: **English** (`en_US`), **French** (`fr_FR`), **German** (`de_DE`, since Hawn 1.4.6), **Dutch** (`nl_NL`, since Hawn 1.5.4), **Spanish** (`es_ES`, since Hawn 1.5.6), **Italian** (`it_IT`) and **Portuguese of Brazil** (`pt_BR`, both since Hawn 1.6).

## The language of each player

Since Hawn 1.6, each player gets the messages in the language of their game: a player whose Minecraft is in French gets the French messages, another one in Brazilian Portuguese the Portuguese ones, on the same server. A language Hawn doesn't have (Polish, Japanese...) gets the language of the server. The messages sent to everyone (the join and quit messages, `/broadcast`, the maintenance...) reach each player in their own language.

{% hint style="info" %}
Before 1.20.2, the game sends its language just after the join: the first messages of the join (the MOTD, the join messages) reach a player who never chose a language in the language of the server, then Hawn speaks their language. Since 1.20.2 (and with ViaVersion on a recent server), the language is known before the join.
{% endhint %}

A player can also choose another language in the language menu (`/language`, or the book of `/profile`): their choice is kept, in their file and in MySQL when the network shares it. The item "Language of my game" goes back to the language of their client.

The console, the admin panel, the world manager and the welcome setup use the language of the server.

```yaml
# general.yml
Plugin:
  Language-Type: en_US          # the language of the server
  Language-Per-Player: true     # false: everyone gets Language-Type, as before 1.6
```

## Change the language of the server

* In game: the first step of the [welcome setup](../getting-started/welcome-setup.md).
* In the files: `general.yml` → `Plugin.Language-Type`, then restart the server.

## Your texts in several languages: `{lang_<key>}`

The texts you write in your files (menus, scoreboards, tab list, announcements, join items, holograms...) can be in every language too. Write a key instead of the text:

```yaml
# Menus/servers.yml
Title: '&8{lang_menu.servers-title}'
```

and the text of the key in each file of `plugins/Hawn/Translations/`:

```yaml
# Translations/en_US.yml          # Translations/fr_FR.yml
menu:                             menu:
  servers-title: Choose a server    servers-title: Choisis un serveur
```

Each player sees the text of their language, else the one of the server, else the English one, else the key itself. A key can hold colours and placeholders. A text without key stays as you wrote it: nothing to change in your files.

Since Hawn 1.6, the files written the first time (the menus, the scoreboards, the tab list, the auto broadcast, the join commands) use keys: `menu.<key>` for the menus, `text.<key>` for the others. Your files from an older version keep their texts.

Add your own keys in every file of `Translations/`. `/hawn reload` reads them again.

## The language menu

`Menus/language.yml` (written once, also on the servers that had their menus before 1.6) has one item per language, and `[language]: <code>` gives this language to the player (`[language]: client` gives back the language of their game). The placeholders `%player_language%` (the language of the messages of the player) and `%player_language_choice%` (their choice, or `client`) show which one is chosen. See [Menus](../features/menus.md#actions-for-the-menus).

The profile of the servers that had it before 1.6 has no book: add an item with `[menu]: language` to `Menus/profile.yml`, or let the players type `/language`.

## Edit the messages

Open the files of your language folder and change what you want. The files are described on the [Files](../getting-started/files.md#messages) page. Every message supports the [message format](../basics/message-format.md) (colours, JSON, centred lines...) and the [actions](../basics/actions.md).

To disable a message, empty its list (`[]`) or set its `Enable` to `false` when it has one. The `Enable` of the file of the server counts for every language.

## Create a new language

1. In `general.yml`, set `Plugin.Language-Type` to the name of your language, for example `pl_PL`.
2. Restart the server: Hawn creates `Messages/pl_PL/` with the English messages.
3. Translate the files of this folder, and add `Translations/pl_PL.yml` for your keys.
4. `/hawn reload` or restart to see your changes.

{% hint style="info" %}
A language of your own is the language of the server: the players get it when their game has no language of Hawn.
{% endhint %}

The welcome setup shows a flag for the folders whose name starts with `fr`, `en_US`, `en_UK`, `de`, `el`, `es`, `fi`, `it`, `ja`, `nl`, `pt` or `zh` (other languages are shown with a white banner).

## Share your translation

Translated Hawn into your language? Share it in an issue on [GitHub](https://github.com/DianoxDragon/Hawn/issues): it can be added to the next versions.
