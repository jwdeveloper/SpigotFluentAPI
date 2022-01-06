package jw.spigot_fluent_api.fluent_plugin.configuration.actions;

import jw.spigot_fluent_api.data.DataContext;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

import java.util.function.Consumer;

public class DataContextAction implements ConfigAction
{
    private final Consumer<DataContext> configure;

    public DataContextAction(Consumer<DataContext> configure)
    {
        this.configure=configure;
    }

    @Override
    public void execute(FluentPlugin fluentPlugin) {
        var dataContext = fluentPlugin.getDataContext();
        if(configure != null)
        configure.accept(dataContext);
        dataContext.load();
    }
}
