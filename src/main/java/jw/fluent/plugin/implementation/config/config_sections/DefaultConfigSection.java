package jw.fluent.plugin.implementation.config.config_sections;

import jw.fluent.api.files.implementation.yml.api.annotations.YmlFile;
import jw.fluent.api.files.implementation.yml.api.annotations.YmlProperty;
import jw.fluent.plugin.implementation.FluentApi;
import lombok.Data;

@YmlFile(globalPath = "plugin")
@Data
public class DefaultConfigSection
{
    private String version = FluentApi.plugin().getDescription().getVersion();
}