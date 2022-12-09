package jw.fluent.plugin.api;

import jw.fluent.plugin.implementation.FluentApiSpigot;

public interface FluentApiExtension {

    public void onConfiguration(FluentApiSpigotBuilder builder);

    void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception;

    void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception;
}
