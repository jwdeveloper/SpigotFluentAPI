package jw.fluent.api.files.implementation.yaml_reader.implementation.assets;

import jw.fluent.api.files.implementation.yaml_reader.api.annotations.YamlProperty;
import lombok.Data;

import java.util.List;

@Data
public class ExampleYamlData
{
    @YamlProperty
    private Integer intProp;

    @YamlProperty
    private Boolean boolProp;

    @YamlProperty
    private String stringProp;

    @YamlProperty(name = "example-list")
    private List<Example> listProp;

    @YamlProperty(name = "example")
    private ExampleYamlNested exampleYamlNested;
}
