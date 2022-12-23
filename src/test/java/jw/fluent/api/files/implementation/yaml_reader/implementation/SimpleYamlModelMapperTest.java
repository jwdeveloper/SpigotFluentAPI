package jw.fluent.api.files.implementation.yaml_reader.implementation;

import be.seeseemelk.mockbukkit.MockBukkit;
import jw.fluent.api.files.implementation.yaml_reader.api.models.YamlModel;
import jw.fluent.api.files.implementation.yaml_reader.implementation.assets.Example;
import jw.fluent.api.files.implementation.yaml_reader.implementation.assets.ExampleYamlData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleYamlModelMapperTest {

    private final String path = "D:\\tmp\\config.yaml";
    public SimpleYamlModelMapper mapper;
    private YamlModel model;
    private YamlConfiguration configuration;
    private ExampleYamlData data;

    @Before
    public void before() throws ClassNotFoundException {

        if(!MockBukkit.isMocked())
        {
            MockBukkit.mock();
        }

        var factory = new SimpleYamlModelFactory();
        var clazz = ExampleYamlData.class;
        model = factory.createModel(clazz);
        data = new ExampleYamlData();
        data.setListProp(createExamples());

        var file = new File(path);
        configuration  = YamlConfiguration.loadConfiguration(file);
        mapper = new SimpleYamlModelMapper();
    }


    @Test
    public void mapToConfiguration() throws IOException, IllegalAccessException {
        configuration = mapper.mapToConfiguration(data, model, configuration);
        configuration.save(path);
    }

    @Test
    public void mapFromConfiguration() throws IllegalAccessException, InstantiationException {
        var data = new ExampleYamlData();
        var result =  mapper.mapFromConfiguration(data, model, configuration);
    }


    public List<Example> createExamples()
    {
        var result = new ArrayList<Example>();
        for(var i =0;i<5;i++)
        {
            var example = new Example();
            example.setChild1("value"+i);
            example.setChild2("value funny"+i);
            result.add(example);
        }
        return result;
    }
}