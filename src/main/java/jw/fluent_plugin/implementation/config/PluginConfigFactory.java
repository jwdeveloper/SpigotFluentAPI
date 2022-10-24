package jw.fluent_plugin.implementation.config;

import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_plugin.implementation.config.config_sections.DefaultConfigSection;
import jw.fluent_plugin.implementation.config.config_sections.FluentConfigSection;
import jw.fluent_api.utilites.files.FileUtility;
import jw.fluent_api.utilites.files.yml.implementation.YmlConfigurationImpl;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PluginConfigFactory {

    private final YmlConfigurationImpl ymlConfiguration;
    public PluginConfigFactory()
    {
        ymlConfiguration =  new YmlConfigurationImpl();
    }
    private boolean isUpdated;
    private boolean isCreated;

    public ConfigFile create(FluentPlugin plugin) throws Exception {
            var sections = plugin.getTypeManager().getByInterface(FluentConfigSection.class);
            var tempObj = new ArrayList<FluentConfigSection>();
            for(var section : sections)
            {
                tempObj.add((FluentConfigSection)section.newInstance());
            }

            var path = FluentPlugin.getPath() + File.separator + "config.yml";
            if (!FileUtility.pathExists(path)) {
                createConfig(path, tempObj);
                isCreated = true;
            }
            var config = loadConfig(path,tempObj);
            return new ConfigFileImpl(config, path,isUpdated,isCreated);
    }

    public YamlConfiguration loadConfig(String path, List<FluentConfigSection> configSections) throws IllegalAccessException, InstantiationException, IOException {
        var file = new File(path);
        var yamlConfig = YamlConfiguration.loadConfiguration(file);
        var versionChanged = isVersionChanged(yamlConfig);
        for (var configSection : configSections) {
            ymlConfiguration.fromConfiguration(yamlConfig, configSection.getClass());
        }
        if(versionChanged)
        {
            var output = new YamlConfiguration();
            ymlConfiguration.toConfiguration(new DefaultConfigSection(),output);
            for (var configSection : configSections) {
                configSection.migrate(yamlConfig);
                ymlConfiguration.toConfiguration(configSection,output);
            }
            output.save(path);
            isUpdated = true;
        }

        return yamlConfig;
    }



    private boolean isVersionChanged(YamlConfiguration yamlConfig)
    {
        var pluginVersion = FluentPlugin.getPlugin().getDescription().getVersion();
        var configVersion = yamlConfig.getString("plugin.version");
        return !pluginVersion.equals(configVersion);
    }

    public void createConfig(String path, Collection<FluentConfigSection> configSections) throws IOException, IllegalAccessException {

        var yamlConfig = new YamlConfiguration();
        ymlConfiguration.toConfiguration(new DefaultConfigSection(),yamlConfig);
        for (var configSection : configSections) {
            ymlConfiguration.toConfiguration(configSection,yamlConfig);
        }

        yamlConfig.save(path);
    }

}
