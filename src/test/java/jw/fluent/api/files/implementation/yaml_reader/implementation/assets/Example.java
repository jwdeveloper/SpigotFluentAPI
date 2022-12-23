package jw.fluent.api.files.implementation.yaml_reader.implementation.assets;

import jw.fluent.api.files.implementation.yaml_reader.api.annotations.YamlProperty;
import lombok.Data;

@Data
public class Example
{
    @YamlProperty(path = "one",name = "one",description = "desc one")
    private String child1;

    @YamlProperty(path = "two",name = "two",description = "desc two")
    private String  child2;
}
