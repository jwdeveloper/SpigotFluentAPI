package jw.spigot_fluent_api.fluent_commands.implementation.builder;

import jw.spigot_fluent_api.fluent_commands.api.builder.BuilderConfig;
import jw.spigot_fluent_api.fluent_commands.api.builder.CommandBuilder;
import jw.spigot_fluent_api.fluent_commands.api.builder.config.ArgumentConfig;
import jw.spigot_fluent_api.fluent_commands.api.builder.config.EventConfig;
import jw.spigot_fluent_api.fluent_commands.api.builder.config.PropertiesConfig;
import jw.spigot_fluent_api.fluent_commands.api.builder.config.SubCommandConfig;
import jw.spigot_fluent_api.fluent_commands.implementation.builder.config.ArgumentConfigImpl;
import jw.spigot_fluent_api.fluent_commands.implementation.builder.config.EventConfigImpl;
import jw.spigot_fluent_api.fluent_commands.implementation.builder.config.PropertiesConfigImpl;
import jw.spigot_fluent_api.fluent_commands.implementation.services.CommandServiceImpl;
import jw.spigot_fluent_api.fluent_commands.implementation.services.EventsServiceImpl;
import jw.spigot_fluent_api.fluent_commands.implementation.services.MessageServiceImpl;
import jw.spigot_fluent_api.fluent_commands.implementation.SimpleCommand;
import jw.spigot_fluent_api.fluent_commands.implementation.SimpleCommandManger;
import jw.spigot_fluent_api.fluent_commands.api.models.CommandModel;
import jw.spigot_fluent_api.fluent_commands.api.services.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CommandBuilderImpl implements CommandBuilder {
    private final EventsService eventsService;

    private final CommandService commandService;

    private final MessagesService messagesService;

    private final List<SimpleCommand> subCommands;

    private final CommandModel model;

    private final Map<Consumer, BuilderConfig> configs;

    public CommandBuilderImpl(String commandName) {
        configs = new HashMap<>();
        eventsService = new EventsServiceImpl();
        commandService = new CommandServiceImpl();
        messagesService = new MessageServiceImpl();
        subCommands = new ArrayList<>();
        model = new CommandModel();
        model.setName(commandName);
    }

    @Override
    public CommandBuilder propertiesConfig(Consumer<PropertiesConfig> config) {
        configs.put(config, new PropertiesConfigImpl(model));
        return this;
    }

    @Override
    public CommandBuilder eventsConfig(Consumer<EventConfig> config) {
        configs.put(config, new EventConfigImpl(eventsService));
        return this;
    }

    @Override
    public CommandBuilder argumentsConfig(Consumer<ArgumentConfig> config) {
        configs.put(config, new ArgumentConfigImpl(model));
        return this;
    }

    @Override
    public CommandBuilder subCommandsConfig(Consumer<SubCommandConfig> config) {
        configs.put(config, new ArgumentConfigImpl(model));
        return this;
    }

    @Override
    public SimpleCommand buildSubCommand() {
        for (var configurationSet : configs.entrySet()) {
            configurationSet.getKey().accept(configurationSet.getValue());
        }
        return new SimpleCommand(model, subCommands, commandService, messagesService, eventsService);
    }

    public SimpleCommand build() {
        var result = buildSubCommand();
        SimpleCommandManger.register(result);
        return result;
    }
}
