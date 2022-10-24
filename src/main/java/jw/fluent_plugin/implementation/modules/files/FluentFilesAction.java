package jw.fluent_plugin.implementation.modules.files;

import jw.fluent_api.files.api.DataContext;
import jw.fluent_api.files.implementation.DefaultDataContext;
import jw.fluent_api.files.api.CustomFile;
import jw.fluent_api.files.api.annotation.files.JsonFile;
import jw.fluent_api.logger.OldLogger;
import jw.fluent_plugin.api.PluginAction;
import jw.fluent_plugin.api.options.PipelineOptions;
import jw.fluent_plugin.implementation.config.ConfigFile;
import jw.fluent_plugin.implementation.config.config_sections.FluentConfigSection;
import jw.fluent_api.spigot.tasks.FluentTaskTimer;
import jw.fluent_api.spigot.tasks.FluentTasks;
import jw.fluent_api.utilites.files.yml.api.annotations.YmlFile;
import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_plugin.implementation.FluentAPI;

import java.io.IOException;
import java.util.function.Consumer;

public class FluentFilesAction implements PluginAction
{
    private Consumer<DataContext> configure;
    private final DefaultDataContext dataContext;
    private FluentTaskTimer savingTask;

    public FluentFilesAction(Consumer<DataContext> configure)
    {
        this();
        this.configure=configure;
    }

    public FluentFilesAction()
    {
        dataContext = new DefaultDataContext();
        this.configure= (x)->{};
    }

    @Override
    public void pluginEnable(PipelineOptions options) throws IllegalAccessException, InstantiationException, IOException {
        var container = FluentAPI.injection();

        var customFiles = container.findAllByInterface(CustomFile.class);
        var ymlFiles = container.findAllByAnnotation(YmlFile.class);
        var jsonFiles =  container.findAllByAnnotation(JsonFile.class);
        var configSections =  container.findAllByInterface(FluentConfigSection.class);
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
        for(var file : configSections)
        {
            dataContext.addConfigFileObject(file);
        }
        configure.accept(dataContext);

        dataContext.load();
        var savingFrequency = getConfigSavingFrequency(options.getConfigFile());
        savingTask = FluentTasks.taskTimer(20*savingFrequency*60,(iteration, task) ->
        {
            dataContext.save();
        });
        savingTask.runAsync();
    }

    public int getConfigSavingFrequency(ConfigFile configFile)
    {
        if(configFile.config().isInt("plugin.saving-frequency"))
        {
            var value = configFile.config().getInt("plugin.saving-frequency");
            if(value < 1)
            {
                return 1;
            }
            return value;
        }
        OldLogger.warning("Unable to load `plugin.saving-frequency` from config");
        return 5;
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {
        savingTask.stop();
        dataContext.save();
    }
}
