---
description: The four-step menu that opens the first time an admin joins.
---

# Welcome setup

The first time an admin joins a server with a new Hawn installation, a setup menu opens. It configures what every lobby needs: the **language**, the **modules**, the **lobby world** and the **default spawn**.

## Who sees it?

Any player with the `hawn.setup` permission (operators have it). The setup opens:

* when the player joins the server;
* or when a player already online becomes operator (Hawn checks every two seconds).

While an admin is in the setup, the usual join features (teleport to spawn, join items...) are skipped for them.

`/hawn setup` opens the setup again later, for you only: the other admins online keep playing normally (since Hawn 1.4.6). Since Hawn 1.5.1, it first asks **Run the setup of Hawn again?**: **No, cancel** closes the menu and changes nothing, so a `/hawn setup` typed by mistake is harmless.

## Step 1: language

Every folder found in `plugins/Hawn/Messages/` is shown as a banner (`en_US`, `fr_FR` and `de_DE` by default). Click one to switch every Hawn message to that language. The choice is saved in `general.yml` → `Plugin.Language-Type`.

* **Yes continue** (emerald block): go to step 2.
* **I don't need a setup** (barrier): end the setup now.

## Step 2: modules

Since Hawn 1.5.1. Turn the main modules on or off with a click: the join items, the tab list, the boss bar and the title on join, the double jump, the void TP, the anti-swear and the anti-advertising. The item shows the state (green: on, red: off); the option is written in its file at once (`Custom-Join-Item.Enable`, `Tablist.enable`, `Boss-Bar.Enable`...).

Only the modules that work without a restart are there. Everything else stays in the files of Hawn.

## Step 3: lobby world

Click **Yes, let's choose a world**: the [world manager](../features/world-manager.md) menu opens. **Left-click** the world that will be your lobby.

This world takes the place of `world` in every `World.Worlds` list of the configuration (join messages, protections, scoreboards, join items, void TP...), so all the lobby features apply to it. The other worlds of the lists (the nether, the end...) are kept. It becomes the [default world](../features/world-manager.md#the-default-world) (`general.yml` → `Plugin.Default-World`): to use another lobby world later, choose it in the world manager and Hawn replaces it everywhere. You can also change these lists by hand, see [Per-world options](../basics/per-world-options.md).

{% hint style="warning" %}
Don't move while the world list is open: moving closes and reopens the menu.
{% endhint %}

Then click **Yes, let's continue** (the emerald block at the bottom of the world manager) to go to step 4.

Since Hawn 1.4.8, the steps have a **Previous step** button (a door) and the steps 2 and 3 a **Next step** button, to keep what is there now. In the world manager of the setup, Previous step is in the middle of the bottom row: the arrows keep changing the pages of the list.

## Step 4: default spawn

If you chose another lobby world at the step 3, Hawn first takes you there (since Hawn 1.4.8): the spawn is created where you stand.

Click **Yes, let's create a spawn**, the menu closes. Go where you want your spawn to be and type:

```
/setspawn <name>
```

For example `/setspawn lobby`. This spawn becomes the default spawn (`Events/OnJoin.yml` → `Spawn.DefaultSpawn`) and the setup ends. `/setlobby` and `/sethub` work too.

You can also click **Yes, let's continue** to end the setup without creating a spawn.

If you close a menu of the first setup with Escape, Hawn reminds you that the setup isn't finished (a message and a sound). `/hawn setup` opens it again. A setup opened again with `/hawn setup` ends with Escape on the steps 1 to 3 (since Hawn 1.5.1).

## After the setup

Since Hawn 1.5.1, **no restart is needed**: at the end of the setup, Hawn reloads its files and every change is applied (`Setup finished: the changes are applied`). When nothing was changed, the setup just closes. Before 1.5.1, Hawn asked to restart the server.

The setup is never shown again, even after a restart: Hawn creates the file `plugins/Hawn/StockageInfo/Setup.lock`.

{% hint style="info" %}
**Want to run the setup again?** Type `/hawn setup` and answer **Yes, run the setup again**: it opens the setup from the first step.
{% endhint %}

Don't forget the spawn permission: to be teleported to the spawn `lobby`, players need `hawn.command.spawn.lobby`. See [Spawns](../features/spawns.md#permissions).
