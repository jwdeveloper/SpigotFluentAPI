package jw.fluent.plugin.implementation.config.config_sections;

import jw.fluent.api.utilites.files.yml.api.annotations.YmlFile;
import jw.fluent.api.utilites.files.yml.api.annotations.YmlProperty;
import jw.fluent.plugin.implementation.FluentApi;
import lombok.Data;

@YmlFile(globalPath = "plugin")
@Data
public class DefaultConfigSection
{
    private String version = FluentApi.plugin().getDescription().getVersion();

    @YmlProperty(description = "If you want add your language open `languages` folder copy `en.yml` call it as you want \n" +
            "set `language` property to your path name and /reload server ")
    private String language = "en";

    @YmlProperty(description = "Determinate how frequent data is saved to files, value in minutes",
    name = "saving-frequency")
    private int savingDataFrequency = 5;
}