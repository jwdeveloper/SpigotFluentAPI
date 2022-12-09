package jw.fluent.plugin.implementation.extentions;

import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.api.extention.ExtentionModel;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.api.extention.ExtentionPiority;
import jw.fluent.plugin.api.extention.FluentApiExtensionsManager;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class FluentApiExtentionsManagerImpl implements FluentApiExtensionsManager {
    private final Collection<ExtentionModel> extentions = new ConcurrentLinkedDeque<>();

    @Override
    public void register(FluentApiExtension extension) {
        register(extension, ExtentionPiority.MEDIUM);
    }

    @Override
    public void register(FluentApiExtension extention, ExtentionPiority piority) {
        extentions.add(new ExtentionModel(extention,piority));
    }

    @Override
    public void registerLow(FluentApiExtension extention) {
        register(extention, ExtentionPiority.LOW);
    }


    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        //FluentLogger.LOGGER.success("onConfiguration");
        for(var extention : extentions)
        {
           // FluentLogger.LOGGER.log("Piority",extention.getPiority().name(),extention.getExtention().getClass().getSimpleName());
            extention.getExtention().onConfiguration(builder);
        }
    }

    @Override
    public void onEnable(FluentApiSpigot fluentAPI) {
        try
        {
            var sorted = sortByPiority();
            for(var extension : sorted)
            {
                extension.getExtention().onFluentApiEnable(fluentAPI);
            }
        }
        catch (Exception e)
        {
            FluentLogger.LOGGER.error("onFluentApiEnable Fluent API Extension exception",e);
        }
    }

    @Override
    public void onDisable(FluentApiSpigot fluentAPI) {
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
