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

### Example command
```java
        FluentCommand.create("give-item")
                  .setDescription("give yourself an item")
                  .setUsageMessage("example of use /give-item DIAMOND 1")
                  .addPermissions("example.permission","example.player")
                .nextStep()
                  .withArgument("material", ArgumentType.CUSTOM)
                  .setTabComplete(Arrays.asList("diamond","apple","sword"))
                  .addValidator(mateiral ->
                  {
                      if(mateiral.length() == 1)
                          return new ValidationResult(false,"too short name");
                      else 
                          return new ValidationResult(true,"");
                  })
                  .build()
                  .withArgument("amount",ArgumentType.INT)
                  .build()
                .nextStep()
                  .onPlayerExecute(event ->
                  {
                    Player player = event.getPlayerSender();
                    Material material = Material.valueOf(event.getArgumentValue(0));
                    int amount = event.getArgumentValue(1);
                    ItemStack itemStack = new ItemStack(material,amount);
                    player.getInventory().setItemInMainHand(itemStack);
                  })
                  .onConsoleExecute(event ->
                  {
                    event.getConsoleSender().sendMessage("This command is only for players");
                  })
                .nextStep()
                  .buildAndRegister();
```
### Example task
```java
 FluentTasks.taskTimer(20, (iteration, task) ->
        {
            Bukkit.getConsoleSender().sendMessage("Current iteration "+iteration);
            int players = Bukkit.getOnlinePlayers().size();
            if(players == 0)
            {
                task.cancel();
            }
            Bukkit.getConsoleSender().sendMessage("Current player number "+players);
        }).startAfterIterations(100)
          .onStop(fluentTaskTimer ->
          {
            Bukkit.getConsoleSender().sendMessage("Task is done");
          })
          .run();
```
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
                
