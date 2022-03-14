package jw.spigot_fluent_api.fluent_plugin.configuration.pipeline;

import jw.spigot_fluent_api.data.implementation.DataContext;
import jw.spigot_fluent_api.data.interfaces.CustomFile;
import jw.spigot_fluent_api.data.implementation.annotation.files.JsonFile;
import jw.spigot_fluent_api.data.implementation.annotation.files.YmlFile;
import jw.spigot_fluent_api.data.interfaces.FluentDataContext;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import java.util.function.Consumer;

public class DataContextAction implements PluginPipeline
{
    private Consumer<FluentDataContext> configure;
    private final DataContext dataContext;

    public DataContextAction(Consumer<FluentDataContext> configure)
    {
        this();
        this.configure=configure;
    }

    public DataContextAction()
    {
        dataContext = new DataContext();
        this.configure= (x)->{};
    }

    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) {
        var container = FluentInjection.getInjectionContainer();

        var customFiles = container.getAllByInterface(CustomFile.class);
        var ymlFiles = container.getAllByAnnotation(YmlFile.class);
        var jsonFiles =  container.getAllByAnnotation(JsonFile.class);

        for (var file: customFiles)
        {
         dataContext.addCustomFileObject(file);
        }
        for (var file: ymlFiles)
        {
            dataContext.addYmlObject(file);
        }
        for (var file: jsonFiles)
        {
            dataContext.addJsonObject(file);
        }
        configure.accept(dataContext);

        dataContext.load();
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {
        dataContext.save();
    }
}
