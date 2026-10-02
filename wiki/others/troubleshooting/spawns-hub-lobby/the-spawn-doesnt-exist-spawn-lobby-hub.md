# The spawn doesn't exist - /spawn /lobby /hub

### When I'm typing /spawn, /hub or /lobby, it says the spawn doesn't exist

{% hint style="danger" %}
The commands /spawn, /lobby and /hub are the same by default. The true command is /spawn, but the /lobby and /hub are just aliases by default.
{% endhint %}

1.  **Have you created a spawn?**&#x20;

    You need to create a spawn before doing these commands with **`/setspawn`**\
    The **`/setspawn`** create a default spawn, with an automatically chosen name, but the `/setspawn yourspawname` create a spawn with the name you have chosen. \
    \
    To check the spawns name that you got, please do `/spawnlist`\
    You will have the list of spawns you created\
    \
    If this is the first time you create a spawn, it will automatically change the configuration files of Hawn, and make the commands working, but if not, go to the number 2 of this problem.<br>
2.  **Have you changed the default spawn in the configuration files?**\
    If you have ever created a new spawn, by deleting another one, or just that don't work., so you need to change/check the spawn name in the configuration files.\
    \
    By getting the different spawns name with the `/spawnlist`, you will have to take one, the one you want to be teleported to, and open a specific file. The file is located in the Hawn plugin configuration folder, in the `Events` folder named `OnJoin.yml`. \
    \
    _&#x53;earch:_

    ```yaml
    Spawn:
      DefaultSpawn: CHANGE ME
      FirstJoin-Spawn: 
        Enable: false
        Spawn: CHANGE ME
    ```

    \
    Once you found the part we are interested in the configuration files, check the `DefaultSpawn` line. This line must have the name of the spawn where you want to be teleported, if not, change the name of the `DefaultSpawn` line, and replace it by the spawn name you want to be teleported to.\
    \
    Then save the file, and reload the plugin while doing `/hawn reload`, you can also reload the server or restart it, then the command should work, if not, go the number 3 of the problem.<br>
3.  **If this still doesn't work, check the custom spawn**\
    In Hawn, you can put a custom spawn for these commands, but to change it works exactly like step 2, but you have to check the file `Spawn.yml` in the folder `Commands`.\
    \
    _&#x53;earch:_

    ```yaml
    CustomSpawn:
      Enable: true
      Spawn: CHANGE ME
    ```

    You must do your verification as in step 2 on the `Spawn` line, then save the file and reload/restart all. The command should now really work !

{% hint style="warning" %}
Do you still have a problem?&#x20;

Let me know on Discord, by sending me the error/problem directly in the reserved channel to avoid requesting them.
{% endhint %}
