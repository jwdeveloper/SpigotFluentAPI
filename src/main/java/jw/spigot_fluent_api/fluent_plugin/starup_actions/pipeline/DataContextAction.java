package jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline;

import jw.spigot_fluent_api.data.implementation.DataContext;
import jw.spigot_fluent_api.data.interfaces.CustomFile;
import jw.spigot_fluent_api.data.implementation.annotation.files.JsonFile;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.config.ConfigFile;
import jw.spigot_fluent_api.fluent_plugin.config.config_sections.FluentConfigSection;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.data.PipelineOptions;
import jw.spigot_fluent_api.fluent_tasks.FluentTaskTimer;
import jw.spigot_fluent_api.fluent_tasks.FluentTasks;
import jw.spigot_fluent_api.utilites.files.yml.api.annotations.YmlFile;
import jw.spigot_fluent_api.data.interfaces.FluentDataContext;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

import java.io.IOException;
import java.util.function.Consumer;

public class DataContextAction implements PluginPipeline
{
    private Consumer<FluentDataContext> configure;
    private final DataContext dataContext;
    private FluentTaskTimer savingTask;

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
    public void pluginEnable(PipelineOptions options) throws IllegalAccessException, InstantiationException, IOException {
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
        FluentLogger.warning("Unable to load `plugin.saving-frequency` from config");
        return 5;
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {
        savingTask.stop();
        dataContext.save();
    }
}
