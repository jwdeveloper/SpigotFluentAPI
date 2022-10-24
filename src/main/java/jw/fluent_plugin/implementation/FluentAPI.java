package jw.fluent_plugin.implementation;

import jw.fluent_plugin.implementation.modules.files.FluentFiles;
import jw.fluent_plugin.implementation.modules.logger.FluentLogger;
import jw.fluent_plugin.implementation.modules.translator.FluentTranslator;
import jw.fluent_plugin.implementation.modules.mediator.FluentMediator;
import jw.fluent_plugin.implementation.modules.dependecy_injection.FluentInjection;
import jw.fluent_plugin.implementation.modules.mapper.FluentMapper;

public final class FluentAPI
{
    private final static FluentAPI INSTANCE = new FluentAPI();
    private FluentInjection fluentInjection;
    private FluentMapper fluentMapper;
    private FluentMediator fluentMediator;
    private FluentFiles fluentFiles;
    private FluentTranslator fluentTranslator;
    private FluentLogger fluentLogger;

    private final FluentAPISpigot spigotFluentApi = new FluentAPISpigot();

    public static FluentInjection injection()
    {
        return INSTANCE.fluentInjection;
    }

    public static FluentMediator mediator()
    {
        return INSTANCE.fluentMediator;
    }

    public static FluentMapper mapper()
    {
        return INSTANCE.fluentMapper;
    }

    public static FluentFiles files()
    {
        return INSTANCE.fluentFiles;
    }

    public static FluentLogger logger()
    {
        return INSTANCE.fluentLogger;
    }

    public static FluentTranslator lang()
    {
        return INSTANCE.fluentTranslator;
    }

    public static FluentAPISpigot spigot()
    {
       return INSTANCE.spigotFluentApi;
    }

}

