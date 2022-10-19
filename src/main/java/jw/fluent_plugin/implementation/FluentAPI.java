package jw.fluent_plugin.implementation;

import jw.fluent_api.desing_patterns.mediator.FluentMediator;
import jw.fluent_api.desing_patterns.mediator.FluentMediatorFasade;

public final class FluentAPI
{
    public static void depenencyInjection() {

    }

    public static FluentMediatorFasade mediator()
    {
        return FluentMediator.getInstance();
    }

    public static void mapper() {

    }

    public static void validator() {

    }

    public static void database() {

    }

    public static void logger() {

    }

    public static SpigotFluentAPI spigot()
    {
       return new SpigotFluentAPI();
    }

}

