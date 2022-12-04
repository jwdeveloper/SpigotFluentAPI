package jw.fluent.plugin.implementation.extentions;

import jw.fluent.plugin.api.FluentApiBuilder;
import jw.fluent.plugin.api.extention.ExtentionModel;
import jw.fluent.plugin.api.FluentApiExtention;
import jw.fluent.plugin.api.extention.ExtentionPiority;
import jw.fluent.plugin.api.extention.FluentApiExtentionsManager;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.logger.FluentLogger;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class FluentApiExtentionsManagerImpl implements FluentApiExtentionsManager {
    private final Collection<ExtentionModel> extentions = new ConcurrentLinkedDeque<>();

    @Override
    public void register(FluentApiExtention extention) {
        register(extention, ExtentionPiority.MEDIUM);
    }

    @Override
    public void register(FluentApiExtention extention, ExtentionPiority piority) {
        extentions.add(new ExtentionModel(extention,piority));
    }

    @Override
    public void registerLow(FluentApiExtention extention) {
        register(extention, ExtentionPiority.LOW);
    }


    @Override
    public void onConfiguration(FluentApiBuilder builder) {
        //FluentLogger.LOGGER.success("onConfiguration");
        for(var extention : extentions)
        {
           // FluentLogger.LOGGER.log("Piority",extention.getPiority().name(),extention.getExtention().getClass().getSimpleName());
            extention.getExtention().onConfiguration(builder);
        }
    }

    @Override
    public void onEnable(FluentApi fluentAPI) {
        try
        {
            var sorted = sortByPiority();
           // FluentLogger.LOGGER.success("onEnable");
            for(var extension : sorted)
            {
            //    FluentLogger.LOGGER.log("Piority",extension.getPiority().name(),extension.getExtention().getClass().getSimpleName());
                extension.getExtention().onFluentApiEnable(fluentAPI);
            }
        }
        catch (Exception e)
        {
            FluentLogger.LOGGER.error("Unable to load plugin",e);
        }
    }

    @Override
    public void onDisable(FluentApi fluentAPI) {
        var sorted = sortByPiority();
        for(var extention : sorted)
        {
            try
            {
                extention.getExtention().onFluentApiDisabled(fluentAPI);
            }
            catch (Exception e)
            {
                FluentLogger.LOGGER.error("disable error",e);
            }
        }
    }



    private List<ExtentionModel> sortByPiority()
    {
        return extentions.stream().toList().stream().sorted(Comparator.comparing(item -> item.getPiority().getLevel())).toList();
    }
}
