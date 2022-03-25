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

```java
public void ExampleCommandRegistration()
    {
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
    }
```
