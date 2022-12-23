package jw.fluent.plugin.implementation.config;

import jw.fluent.api.files.implementation.yaml_reader.api.YamlReader;
import jw.fluent.api.files.implementation.yaml_reader.implementation.SimpleYamlReader;
import jw.fluent.plugin.implementation.assembly_scanner.AssemblyScanner;
import jw.fluent.plugin.api.config.ConfigSection;
import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.plugin.implementation.config.migrations.FluentConfigMigrator;
import jw.fluent.plugin.implementation.config.sections.DefaultConfigSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FluentConfigLoader {
    private final FluentConfigMigrator migrator;
    private final YamlReader yamlMapper;
    private final AssemblyScanner assemblyScanner;

    public FluentConfigLoader(AssemblyScanner assemblyScanner, FluentConfigMigrator migrator) {
        yamlMapper = new SimpleYamlReader();
        this.assemblyScanner = assemblyScanner;
        this.migrator = migrator;
    }

    public static FluentConfigImpl loadConfig(JavaPlugin javaPlugin, AssemblyScanner assemblyScanner) throws Exception
    {
        var loader = new FluentConfigLoader(assemblyScanner, new FluentConfigMigrator(assemblyScanner, javaPlugin));
        var path = FileUtility.pluginPath(javaPlugin) + File.separator + "config.yml";
        return loader.load(path);
    }


    public FluentConfigImpl load(String path) throws Exception {
        var yamlConfiguration = getConfigFile(path);
        if (migrator.isPluginUpdated(yamlConfiguration)) {
            migrator.makeMigration(yamlConfiguration);
            yamlConfiguration.save(path);
        }
        var sections = createAndMapSections(yamlConfiguration);
        yamlConfiguration.save(path);
        return new FluentConfigImpl(yamlConfiguration, path, false, false);
    }


    private List<ConfigSection> createAndMapSections(YamlConfiguration configuration) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        var sections = new ArrayList<ConfigSection>();
        var sectionsClasses = assemblyScanner.findByInterface(ConfigSection.class);
        for (var clazz : sectionsClasses) {
            var sectionObject = (ConfigSection) clazz.newInstance();
            yamlMapper.toConfiguration(sectionObject, configuration);
        }
        return sections;
    }

    private YamlConfiguration getConfigFile(String path) throws IllegalAccessException, IOException, ClassNotFoundException {
        if (!FileUtility.pathExists(path)) {
            var yamlConfig = new YamlConfiguration();
            yamlMapper.toConfiguration(new DefaultConfigSection(), yamlConfig);
            yamlConfig.save(path);
            return yamlConfig;
        }
        var file = new File(path);
        return YamlConfiguration.loadConfiguration(file);
    }

}
