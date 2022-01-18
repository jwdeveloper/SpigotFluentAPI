package jw.spigot_fluent_api.fluent_plugin.configuration.pipeline;

import jw.spigot_fluent_api.data.DataContext;
import jw.spigot_fluent_api.data.DataHandler;
import jw.spigot_fluent_api.data.Saveable;
import jw.spigot_fluent_api.data.annotation.files.JsonFile;
import jw.spigot_fluent_api.data.annotation.files.YmlFile;
import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

import java.util.function.Consumer;

public class DataContextAction implements PluginPipeline
{
    private Consumer<DataHandler> configure;
    private final DataContext dataContext;

    public DataContextAction(Consumer<DataHandler> configure)
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
        //load files from files
        var container = FluentInjection.getInjectionContainer();

        var savableFiles = container.getAllByInterface(Saveable.class);
        var ymlFiles = container.getAllByAnnotation(YmlFile.class);
        var jsonFiles =  container.getAllByAnnotation(JsonFile.class);

        dataContext.addSaveableObject(savableFiles);
        dataContext.addYmlObjects(ymlFiles);
        dataContext.addJsonObjects(jsonFiles);
        configure.accept(dataContext);


        dataContext.load();
        /*
        for (var yml : savableFiles) {
            FluentPlugin.logSuccess(yml.getClass().getSimpleName());
        }
         */
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {
        dataContext.save();
    }
}
