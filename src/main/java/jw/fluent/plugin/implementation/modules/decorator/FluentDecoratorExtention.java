package jw.fluent.plugin.implementation.modules.decorator;

import jw.fluent.api.desing_patterns.decorator.api.Decorator;
import jw.fluent.api.desing_patterns.decorator.api.builder.DecoratorBuilder;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;

public class FluentDecoratorExtention implements FluentApiExtension {

    private DecoratorBuilder decoratorBuilder;

    public FluentDecoratorExtention(DecoratorBuilder decoratorBuilder)
    {
        this.decoratorBuilder = decoratorBuilder;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        builder.container().configure(containerConfiguration ->
        {
            try
            {
                Decorator decorator = decoratorBuilder.build();
                containerConfiguration.onEvent(decorator);
            } catch (Exception e) {
                FluentLogger.LOGGER.error("Unable to register decorator ",e);
                return;
            }
        });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

    }
}
