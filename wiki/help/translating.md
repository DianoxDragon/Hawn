---
description: Change the language of Hawn, edit the messages or create your own language.
---

# Translating Hawn

Every message of Hawn is in `plugins/Hawn/Messages/<language>/`. Two languages are provided: **English** (`en_US`) and **French** (`fr_FR`).

## Change the language

* In game: the first step of the [welcome setup](../getting-started/welcome-setup.md).
* In the files: `general.yml` → `Plugin.Language-Type`, then restart the server.

```yaml
Plugin:
  Language-Type: fr_FR
```

## Edit the messages

Open the files of your language folder and change what you want. The files are described on the [Files](../getting-started/files.md#messages) page. Every message supports the [message format](../basics/message-format.md) (colours, JSON, centred lines...) and the [actions](../basics/actions.md).

To disable a message, empty its list (`[]`) or set its `Enable` to `false` when it has one.

## Create a new language

1. In `general.yml`, set `Plugin.Language-Type` to the name of your language, for example `de_DE`.
2. Restart the server: Hawn creates `Messages/de_DE/` with the English messages.
3. Translate the files of this folder.
4. `/hawn reload` or restart to see your changes.

{% hint style="info" %}
Working in a new folder is safer: if something goes wrong, set `Language-Type` back to `en_US` and everything is like before.
{% endhint %}

The welcome setup shows a flag for the folders whose name starts with `fr`, `en_US`, `en_UK`, `ge`, `el`, `es`, `fi`, `it`, `ja`, `ne` or `zh` (other languages are shown with a white banner).

## Share your translation

Translated Hawn into your language? Share it on the Discord server: it can be added to the next versions.
