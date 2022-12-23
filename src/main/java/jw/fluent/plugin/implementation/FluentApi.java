package jw.fluent.plugin.implementation;

import jw.fluent.api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent.api.spigot.events.FluentEvents;
import jw.fluent.api.spigot.messages.SimpleMessage;
import jw.fluent.api.spigot.particles.implementation.builder.builders_pipeline.FluentParticlebuilder;
import jw.fluent.api.spigot.tasks.SimpleTasks;
import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.plugin.api.config.FluentConfig;
import jw.fluent.plugin.implementation.modules.dependecy_injection.FluentInjection;
import jw.fluent.plugin.implementation.modules.files.FluentFiles;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.fluent.plugin.implementation.modules.mapper.FluentMapper;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediator;
import jw.fluent.plugin.implementation.modules.permissions.api.FluentPermission;
import jw.fluent.plugin.implementation.modules.player_context.implementation.FluentPlayerContext;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import org.bukkit.plugin.java.JavaPlugin;

public class FluentApi {
    private static FluentApiSpigot fluentApiSpigot;
    private static JavaPlugin plugin;


    static void setPlugin(JavaPlugin plugin) {
       FluentApi.plugin = plugin;
    }

    public static void setFluentApiSpigot(FluentApiSpigot fluentApiSpigot) {
        if (FluentApi.fluentApiSpigot != null) {
            FluentLogger.LOGGER.error("FluentAPI has been already initialized ");

            return;
        }
        FluentApi.fluentApiSpigot = fluentApiSpigot;
    }

    public static FluentApiSpigot getFluentApiSpigot() {
        if (fluentApiSpigot == null) {
            FluentLogger.LOGGER.error("FluentAPI has not been initialized initialize");
            FluentLogger.LOGGER.error("Remember not to use FluentApi class inside OnConfiguration(FluentApiSpigotBuilder builder) method");
            return null;
        }
        return fluentApiSpigot;
    }


    public static FluentPlayerContext playerContext() {
        return getFluentApiSpigot().playerContext();
    }

    public static FluentPermission permission() {
        return getFluentApiSpigot().permission();
    }

    public static FluentInjection container() {
        return getFluentApiSpigot().container();
    }

    public static FluentMediator mediator() {
        return getFluentApiSpigot().mediator();
    }

    public static FluentMapper mapper() {
        return getFluentApiSpigot().mapper();
    }

    public static FluentFiles files() {
        return getFluentApiSpigot().files();
    }

    public static FluentLogger logger() {
        return FluentLogger.LOGGER;
    }

    public static FluentTranslator translator() {
        return getFluentApiSpigot().translator();
    }

    public static FluentConfig config() {
        return getFluentApiSpigot().config();
    }

    public static FluentEvents events() {
        return getFluentApiSpigot().events();
    }

    public static SimpleMessage messages(){
        return getFluentApiSpigot().messages();
    }

    public static SimpleTasks tasks() {
        return getFluentApiSpigot().tasks();
    }

    public static CommandBuilder commands(String commandName) {
        return getFluentApiSpigot().commands(commandName);
    }

    public static FluentParticlebuilder particles() {
        return getFluentApiSpigot().particles();
    }

    public static JavaPlugin plugin() {
        return plugin;
    }

    public static String path() {

        return FileUtility.pluginPath(plugin);
    }
}
