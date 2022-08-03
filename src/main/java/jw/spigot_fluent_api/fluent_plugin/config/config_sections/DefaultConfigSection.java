package jw.spigot_fluent_api.fluent_plugin.config.config_sections;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.files.yml.api.annotations.YmlFile;
import jw.spigot_fluent_api.utilites.files.yml.api.annotations.YmlProperty;
import lombok.Data;

@YmlFile(globalPath = "plugin")
@Data
public class DefaultConfigSection
{
    private String version = FluentPlugin.getPlugin().getDescription().getVersion();

    @YmlProperty(description = "If you want add your language open `languages` folder copy `en.yml` call it as you want \n" +
            "set `language` property to your file name and /reload server ")
    private String language = "en";

    @YmlProperty(description = "Determinate how frequent data is saved to files, value in minutes",
    name = "saving-frequency")
    private int savingDataFrequency = 5;
}