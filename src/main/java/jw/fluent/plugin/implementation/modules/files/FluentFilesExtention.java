package jw.fluent.plugin.implementation.modules.files;

import jw.fluent.api.desing_patterns.dependecy_injection.api.containers.FluentContainer;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.files.api.CustomFile;
import jw.fluent.api.files.api.annotation.files.JsonFile;
import jw.fluent.api.files.implementation.FilesDataContext;
import jw.fluent.api.files.implementation.SimpleFileBuilderImpl;
import jw.fluent.api.spigot.tasks.SimpleTaskTimer;
import jw.fluent.plugin.implementation.config.ConfigProperty;
import jw.fluent.plugin.implementation.modules.tasks.FluentTasks;
import jw.fluent.api.files.implementation.yml.api.annotations.YmlFile;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.fluent.plugin.implementation.config.FluentConfig;
import jw.fluent.plugin.implementation.config.config_sections.FluentConfigSection;

public class FluentFilesExtention implements FluentApiExtension {

    private final SimpleFileBuilderImpl builder;
    private SimpleTaskTimer savingTask;
    private FilesDataContext filesDataContext;

    public FluentFilesExtention(SimpleFileBuilderImpl builder)
    {
        this.builder =builder;
    }


    @Override
    public void onConfiguration(FluentApiSpigotBuilder fluentApiBuilder) {
        fluentApiBuilder.container().register(FluentFiles.class, LifeTime.SINGLETON,(x) ->
        {
            filesDataContext = builder.build();
            var searchContainer = (FluentContainer)x;

            var logger = (FluentLogger)searchContainer.find(FluentLogger.class);
            var config = (FluentConfig)searchContainer.find(FluentConfig.class);

            var customFiles = searchContainer.findAllByInterface(CustomFile.class);
            var ymlFiles = searchContainer.findAllByAnnotation(YmlFile.class);
            var jsonFiles =  searchContainer.findAllByAnnotation(JsonFile.class);
            var configSections =  searchContainer.findAllByInterface(FluentConfigSection.class);
            for (var file: customFiles)
            {
                filesDataContext.addCustomFileObject(file);
            }
            for (var file: ymlFiles)
            {
                filesDataContext.addYmlObject(file);
            }
            for (var file: jsonFiles)
            {
                filesDataContext.addJsonObject(file);
            }
            for(var file : configSections)
            {
                filesDataContext.addConfigFileObject(file);
            }
            var savingFrequency = getConfigSavingFrequency(config, logger);
            savingTask = FluentTasks.taskTimer(20*savingFrequency*60,(iteration, task) ->
            {
                filesDataContext.save();
            });
            savingTask.runAsync();
            return filesDataContext;
        });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        filesDataContext.load();
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) {
        if(filesDataContext == null)
        {
            return;
        }
        filesDataContext.save();
        if(savingTask == null)
        {
            return;
        }
        savingTask.cancel();
    }

    private int getConfigSavingFrequency(FluentConfig configFile, FluentLogger logger)
    {
        var property = createSavingPropertyConfig();
        var propertyValue =  configFile.getOrCreate(property);
        return propertyValue;
    }


    public ConfigProperty<Integer> createSavingPropertyConfig()
    {
        return new ConfigProperty<Integer>("plugin.saving-frequency", 5,"Determinate how frequent data is saved to files, value in minutes");
    }
}
