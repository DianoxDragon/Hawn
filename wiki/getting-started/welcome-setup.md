---
description: The three-step menu that opens the first time an admin joins.
---

# Welcome setup

The first time an admin joins a server with a new Hawn installation, a setup menu opens. It configures the three things every lobby needs: the **language**, the **lobby world** and the **default spawn**.

## Who sees it?

Any player with the `hawn.setup` permission (operators have it). The setup opens:

* when the player joins the server;
* or when a player already online becomes operator (Hawn checks every two seconds).

While an admin is in the setup, the usual join features (teleport to spawn, join items...) are skipped for them.

## Step 1: language

Every folder found in `plugins/Hawn/Messages/` is shown as a banner (`en_US` and `fr_FR` by default). Click one to switch every Hawn message to that language. The choice is saved in `general.yml` → `Plugin.Language-Type`.

* **Yes continue** (emerald block): go to step 2.
* **I don't need a setup** (barrier): end the setup now.

## Step 2: lobby world

Click **Yes, let's choose a world**: the [world manager](../features/world-manager.md) menu opens. **Left-click** the world that will be your lobby.

This world takes the place of `world` in every `World.Worlds` list of the configuration (join messages, protections, scoreboards, join items, void TP...), so all the lobby features apply to it. The other worlds of the lists (the nether, the end...) are kept. It becomes the [default world](../features/world-manager.md#the-default-world) (`general.yml` → `Plugin.Default-World`): to use another lobby world later, choose it in the world manager and Hawn replaces it everywhere. You can also change these lists by hand, see [Per-world options](../basics/per-world-options.md).

{% hint style="warning" %}
Don't move while the world list is open: moving closes and reopens the menu.
{% endhint %}

Then click **Yes, let's continue** (the emerald block at the bottom of the world manager) to go to step 3.

## Step 3: default spawn

Click **Yes, let's create a spawn**, the menu closes. Go where you want your spawn to be and type:

```
/setspawn <name>
```

For example `/setspawn lobby`. This spawn becomes the default spawn (`Events/OnJoin.yml` → `Spawn.DefaultSpawn`) and the setup ends. `/setlobby` and `/sethub` work too.

You can also click **Yes, let's continue** to end the setup without creating a spawn.

If you close a menu of the setup with Escape, Hawn reminds you that the setup isn't finished (a message and a sound). `/hawn setup` opens it again.

## After the setup

Hawn asks you to **restart the server** so that every module picks up the new settings.

The setup is never shown again, even after a restart: Hawn creates the file `plugins/Hawn/StockageInfo/Setup.lock`.

{% hint style="info" %}
**Want to run the setup again?** Since Hawn 1.3, type `/hawn setup`: it opens the setup from the first step.
{% endhint %}

Don't forget the spawn permission: to be teleported to the spawn `lobby`, players need `hawn.command.spawn.lobby`. See [Spawns](../features/spawns.md#permissions).
