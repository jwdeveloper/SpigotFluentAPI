package jw.fluent.api.files.implementation.yaml_reader.implementation.assets;

import jw.fluent.api.files.implementation.yaml_reader.api.annotations.YamlFile;
import jw.fluent.api.files.implementation.yaml_reader.api.annotations.YamlProperty;
import lombok.Getter;

@Getter
public class ExampleYamlNested
{
    @YamlProperty(name = "one",description = "desc one")
    private String child1;

    @YamlProperty(path = "nested-path",name = "two",description = "desc two")
    private String child2;
}
