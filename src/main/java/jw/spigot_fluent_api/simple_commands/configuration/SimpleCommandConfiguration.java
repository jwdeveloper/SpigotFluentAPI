package jw.spigot_fluent_api.simple_commands.configuration;

import jw.spigot_fluent_api.simple_commands.SimpleCommand;
import jw.spigot_fluent_api.simple_commands.enums.CommandAccessType;

import java.util.function.Supplier;

public class SimpleCommandConfiguration {
    private final SimpleCommand simpleCommand;

    public SimpleCommandConfiguration(SimpleCommand simpleCommand) {
        this.simpleCommand = simpleCommand;
    }

    public SimpleCommandConfiguration forCommand(String name) {
        return this;
    }
    public CommandArgumentConfiguration forArgument(int parameter) {
        return new CommandArgumentConfiguration();
    }

    public SimpleCommandConfiguration setPermissions(String... permissions) {
        return this;
    }

    public SimpleCommandConfiguration setPermission(String permissions) {
        return this;
    }

    public SimpleCommandConfiguration setAccessType(CommandAccessType... accessType) {
        return this;
    }

    public SimpleCommandConfiguration setSubCommand(SimpleCommand autoCommands) {
        return this;
    }

    void apply() {

    }
}
