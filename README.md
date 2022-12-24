## 🤔 What is SpigotFluentAPI?
It is the library/framework build on spigot library
it providers dozen of solution for common tedious task in spigot 
like # Commands, Events, Particle registration and more

## 🤔 Why should i use it?
 Spigot is grate api but you need to write massive tones of code
 to atchieve simple golas like command registration system.
 
## 🤔 For who?
- 👦🏻 For begginers how wants quickly build up some plugin for own purpose.
- 👨‍💻 For proffestion programmers who want to get more clean project structure. 
 
 
 # 👨🏼‍🔧 Features
 - ⚡ FlunetCommand
 - ⚡ FluentEvent
 - ⚡ FluentMessage
 - ⚡ FluentParticle
 - ⚡ FluentTasks
  
 - ⚡ FluentInjection 
 - ⚡ FluentMeditaor 
 - ⚡ FluentMapper  (in progress)
  
 - ⚡ FluentWebSocket (in progress)
 - ⚡ Json/YAML file mappers

## Code example


### Example event
```java
  FluentEvent.onEvent(PlayerJoinEvent.class, event ->
        {
            Player player = event.getPlayer();
            player.sendMessage("hello on the server");
        });
```        
### Example message
```java
 FluentMessage.message()
              .color(ChatColor.GREEN)
              .inBrackets("Information")
              .space()
              .color(ChatColor.GREEN)
              .bold("All players will receive free stick!!")
              .sendToAllPlayer();
```                
                
