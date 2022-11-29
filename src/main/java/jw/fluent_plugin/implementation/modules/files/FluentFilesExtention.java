package jw.fluent_plugin.implementation.modules.files;

import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.FluentContainer;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.files.api.CustomFile;
import jw.fluent_api.files.api.annotation.files.JsonFile;
import jw.fluent_api.files.implementation.FilesDataContext;
import jw.fluent_api.files.implementation.SimpleFileBuilderImpl;
import jw.fluent_api.spigot.tasks.FluentTaskTimer;
import jw.fluent_api.spigot.tasks.FluentTasks;
import jw.fluent_api.utilites.files.yml.api.annotations.YmlFile;
import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.FluentApi;
import jw.fluent_plugin.implementation.config.FluentConfig;
import jw.fluent_plugin.implementation.config.config_sections.FluentConfigSection;
import jw.fluent_plugin.implementation.modules.logger.FluentLogger;

public class FluentFilesExtention implements FluentApiExtention {

    private final SimpleFileBuilderImpl builder;
    private FluentTaskTimer savingTask;
    private FilesDataContext filesDataContext;

    public FluentFilesExtention(SimpleFileBuilderImpl builder)
    {
        this.builder =builder;
    }

    public int getConfigSavingFrequency(FluentConfig configFile, FluentLogger logger)
    {
        if(configFile.config().isInt("plugin.saving-frequency"))
        {
            var value = configFile.config().getInt("plugin.saving-frequency");
            return Math.max(value, 1);
        }
        logger.warning("Unable to load `plugin.saving-frequency` from config");
        return 5;
    }

    @Override
    public void onConfiguration(FluentApiBuilder fluentApiBuilder) {
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
    public void onFluentApiEnable(FluentApi fluentAPI) throws Exception {
        filesDataContext.load();
    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) {
        filesDataContext.save();
        savingTask.cancel();
    }
}
