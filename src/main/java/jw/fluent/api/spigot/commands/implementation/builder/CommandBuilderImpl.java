package jw.fluent.api.spigot.commands.implementation.builder;

import jw.fluent.api.spigot.commands.implementation.builder.config.ArgumentConfigImpl;
import jw.fluent.api.spigot.commands.implementation.builder.config.EventConfigImpl;
import jw.fluent.api.spigot.commands.implementation.builder.config.PropertiesConfigImpl;
import jw.fluent.api.spigot.commands.implementation.builder.config.SubCommandConfigImpl;
import jw.fluent.api.spigot.commands.implementation.services.EventsServiceImpl;
import jw.fluent.api.spigot.commands.implementation.services.MessageServiceImpl;
import jw.fluent.api.spigot.commands.api.builder.BuilderConfig;
import jw.fluent.api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent.api.spigot.commands.api.builder.config.ArgumentConfig;
import jw.fluent.api.spigot.commands.api.builder.config.EventConfig;
import jw.fluent.api.spigot.commands.api.builder.config.PropertiesConfig;
import jw.fluent.api.spigot.commands.api.builder.config.SubCommandConfig;
import jw.fluent.api.spigot.commands.api.services.CommandService;
import jw.fluent.api.spigot.commands.api.services.EventsService;
import jw.fluent.api.spigot.commands.api.services.MessagesService;
import jw.fluent.api.spigot.commands.implementation.services.CommandServiceImpl;
import jw.fluent.api.spigot.commands.implementation.SimpleCommand;
import jw.fluent.api.spigot.commands.implementation.SimpleCommandManger;
import jw.fluent.api.spigot.commands.api.models.CommandModel;
import jw.fluent.plugin.implementation.FluentApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CommandBuilderImpl implements CommandBuilder {
    protected final EventsService eventsService;

    protected final CommandService commandService;

    protected final MessagesService messagesService;

    protected final List<SimpleCommand> subCommands;

    protected final CommandModel model;

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

    public CommandBuilderImpl() {
        this(FluentApi.plugin().getName());
    }



    @Override
    public CommandBuilder propertiesConfig(Consumer<PropertiesConfig> config) {
        configs.put(config, new PropertiesConfigImpl(model));
        return this;
    }

    @Override
    public CommandBuilder eventsConfig(Consumer<EventConfig> consumer) {
        configs.put(consumer, new EventConfigImpl(eventsService));
        return this;
    }

    @Override
    public CommandBuilder argumentsConfig(Consumer<ArgumentConfig> config) {
        configs.put(config, new ArgumentConfigImpl(model));
        return this;
    }

    @Override
    public CommandBuilder subCommandsConfig(Consumer<SubCommandConfig> config) {
        configs.put(config, new SubCommandConfigImpl(subCommands));
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
