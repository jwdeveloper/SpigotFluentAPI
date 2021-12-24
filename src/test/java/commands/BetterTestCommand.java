package commands;

import jw.spigot_fluent_api.simple_commands.SimpleCommand;
import jw.spigot_fluent_api.simple_commands.configuration.SimpleCommandConfiguration;
import jw.spigot_fluent_api.simple_commands.annotations.Command;
import jw.spigot_fluent_api.simple_commands.annotations.CommandPermission;
import jw.spigot_fluent_api.simple_commands.enums.CommandAccessType;
import jw.spigot_fluent_api.simple_commands.models.CommandModel;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.List;


@Command(name = "kick")
public class BetterTestCommand extends SimpleCommand {

    public BetterTestCommand(CommandModel commandModel) {
        super(commandModel);
    }

    public void configure(SimpleCommandConfiguration configurator) {
        configurator.forCommand("gui")
                    .setPermission("jacek")
                    .setAccessType(CommandAccessType.COMMAND_SENDER, CommandAccessType.BLOCK_SENDER)
                    .forArgument(0)
                      .setValidationMessage("incorect value!")
                      .setName("name")
                      .setDisplayedList(() ->
                      {
                       return List.of("a","b","c");
                      }).apply();
    }


    @Command(name = "gui")
    public void onInvoke(CommandSender a, String b, int c)
    {
        System.out.println("Player: " + a.getName() + "Player name: " + b + " amount:" + c);
    }

    @CommandPermission(permission = "admin")
    @CommandPermission(setGenericPermission = true)
    @Command(name = "kick {1} {2}",
            requireAllParameters = false,
            access = CommandAccessType.PLAYER)
    public void playerJoin(Player player, String playerName, int amount)
    {
        System.out.println("Player: " + player.getName() + "Player name: " + playerName + " amount:" + amount);
    }

    @Command(name = "kick playerName} {amount}")
    @CommandPermission(permission = "admin")
    @CommandPermission(setGenericPermission = true)
    public void wrongFormatCmd(Player player, String playerName, int amount) {
        System.out.println("Player: " + player.getName() + "Player name: " + playerName + " amount:" + amount);
    }
}
