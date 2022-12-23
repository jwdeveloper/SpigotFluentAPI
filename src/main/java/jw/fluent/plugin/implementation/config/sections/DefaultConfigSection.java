package jw.fluent.plugin.implementation.config.sections;

import jw.fluent.api.files.implementation.yaml_reader.api.annotations.YamlProperty;
import jw.fluent.plugin.implementation.FluentApi;
import lombok.Data;

@Data
public class DefaultConfigSection
{
    @YamlProperty(path = "plugin")
    private String version = FluentApi.plugin().getDescription().getVersion();
}