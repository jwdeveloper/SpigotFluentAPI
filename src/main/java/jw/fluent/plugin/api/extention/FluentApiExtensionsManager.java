package jw.fluent.plugin.api.extention;

import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.fluent.plugin.api.FluentApiExtension;

public interface FluentApiExtensionsManager {
    void register(FluentApiExtension extension);

    void register(FluentApiExtension extention, ExtentionPiority piority);

    void registerLow(FluentApiExtension extention);

    void onEnable(FluentApiSpigot fluentAPI);

    void onDisable(FluentApiSpigot fluentAPI);

    void onConfiguration(FluentApiSpigotBuilder builder);

}
