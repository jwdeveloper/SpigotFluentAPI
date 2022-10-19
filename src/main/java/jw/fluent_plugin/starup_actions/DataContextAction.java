package jw.fluent_plugin.starup_actions;

import jw.fluent_api.desing_patterns.unit_of_work.api.DataContext;
import jw.fluent_api.desing_patterns.unit_of_work.implementation.DefaultDataContext;
import jw.fluent_api.desing_patterns.unit_of_work.api.CustomFile;
import jw.fluent_api.desing_patterns.unit_of_work.api.annotation.files.JsonFile;
import jw.fluent_plugin.starup_actions.api.PluginPipeline;
import jw.fluent_plugin.starup_actions.data.PipelineOptions;
import jw.fluent_api.minecraft.logger.FluentLogger;
import jw.fluent_plugin.config.ConfigFile;
import jw.fluent_plugin.config.config_sections.FluentConfigSection;
import jw.fluent_api.minecraft.tasks.FluentTaskTimer;
import jw.fluent_api.minecraft.tasks.FluentTasks;
import jw.fluent_api.utilites.files.yml.api.annotations.YmlFile;
import jw.fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.fluent_plugin.FluentPlugin;

import java.io.IOException;
import java.util.function.Consumer;

public class DataContextAction implements PluginPipeline
{
    private Consumer<DataContext> configure;
    private final DefaultDataContext dataContext;
    private FluentTaskTimer savingTask;

    public DataContextAction(Consumer<DataContext> configure)
    {
        this();
        this.configure=configure;
    }

    public DataContextAction()
    {
        dataContext = new DefaultDataContext();
        this.configure= (x)->{};
    }

    @Override
    public void pluginEnable(PipelineOptions options) throws IllegalAccessException, InstantiationException, IOException {
        var container = FluentInjection.getContainer();

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
        FluentLogger.warning("Unable to load `plugin.saving-frequency` from config");
        return 5;
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {
        savingTask.stop();
        dataContext.save();
    }
}
