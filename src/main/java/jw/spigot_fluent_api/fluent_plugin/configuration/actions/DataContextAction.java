package jw.spigot_fluent_api.fluent_plugin.configuration.actions;

import jw.spigot_fluent_api.data.DataContext;
import jw.spigot_fluent_api.data.DataHandler;
import jw.spigot_fluent_api.data.Saveable;
import jw.spigot_fluent_api.data.annotation.files.JsonFile;
import jw.spigot_fluent_api.data.annotation.files.YmlFile;
import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

import java.util.function.Consumer;

public class DataContextAction implements ConfigAction
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
        var savableFiles = InjectionManager.getObjectsByParentType(Saveable.class);
        var ymlFiles = InjectionManager.getObjectByAnnotation(YmlFile.class);
        var jsonFiles = InjectionManager.getObjectByAnnotation(JsonFile.class);
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
