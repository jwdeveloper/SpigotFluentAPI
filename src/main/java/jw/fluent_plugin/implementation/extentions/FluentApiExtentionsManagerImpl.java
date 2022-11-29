package jw.fluent_plugin.implementation.extentions;

import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.api.extention.FluentApiExtentionsManager;
import jw.fluent_plugin.implementation.FluentApi;
import jw.fluent_plugin.implementation.modules.logger.FluentLogger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class FluentApiExtentionsManagerImpl implements FluentApiExtentionsManager {
    private final Collection<FluentApiExtention> extentions = new ConcurrentLinkedDeque<>();

    @Override
    public void register(FluentApiExtention extention) {
        extentions.add(extention);
    }

    public void register(FluentApiExtention extention, int piority) {
        extentions.add(extention);
    }


    @Override
    public void onEnable(FluentApi fluentAPI) {
        try
        {
            for(var extention : extentions)
            {
                extention.onFluentApiEnable(fluentAPI);
            }
        }
        catch (Exception e)
        {
            FluentLogger.LOGGER.error("Unable to load plugin",e);
        }
    }

    @Override
    public void onDisable(FluentApi fluentAPI) {
        for(var extention : extentions)
        {
            try
            {
                extention.onFluentApiDisabled(fluentAPI);
            }
            catch (Exception e)
            {
                FluentLogger.LOGGER.error("disable error",e);
            }
        }
    }

    @Override
    public void onConfiguration(FluentApiBuilder builder) {
        for(var extention : extentions)
        {
            extention.onConfiguration(builder);
        }
    }
}
