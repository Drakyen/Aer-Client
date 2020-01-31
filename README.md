# Aer-Client

A custom minecraft client that I decided to opensource since authentication and distribution is hard.
I believe it's the first free and open source client to have premium features and exploits freely visible and copyable to eveyone.
e
Feel free to make contribution requests to this project, and use the code in your own projects, provided you follow the license terms.

It's going to get skidded by people, I know full well. But hopefully it will get enough of a reputation for skidding it to not matter.
Since I'm not selling the client, I set up a [patreon](https://www.patreon.com/AerClient "My patreon!") that you can support me at here

In order to import this client and devlop it yourself, currently you will need [1.12.2 Optifine HD U C6 sources](https://optifinesource.co.uk "Optifine Sources"), the darkmgician6 event API,
and decompiled minecraft 1.12.2 with MCP mappings. I'm planning to make it easier by changing all hooks to be injected, but as of right now, you'll need to re-add the hooks yourself.
The names of the events should give you a good idea of where to hook them. (There's also several methods that I've remapped myself, so you may have issues with those too. As I said, I'll try simplify it soon)

In order to prevent breaching the [Minecraft TOS](https://account.mojang.com/documents/minecraft_eula "Minecraft's End User License Agreenment"), I won't upload any minecraft source code here. Unfortunately, that means I cannot just upload .jar
and .json version files for releases. To get around this, at some point in the future (Hopefully soon after I make all my hooks injection based), I will make
a installer that can inject my source code into a unmodified 1.12.2 version.

Hope ya'll enjoy the project!
