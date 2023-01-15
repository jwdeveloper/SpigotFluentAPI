package jw.fluent.api.files.implementation.yaml_reader.implementation;


import be.seeseemelk.mockbukkit.MockBukkit;
import jw.fluent.api.files.implementation.yaml_reader.implementation.assets.Example;
import jw.fluent.api.files.implementation.yaml_reader.implementation.assets.ExampleYamlData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SimpleYamlValueResolverTest {


    private final String path = "D:\\tmp\\config.yaml";


    @Test
    public void makeTest() throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        MockBukkit.mock();
        saveToYaml();
        loadFromYaml();
    }


    @Test
    public void loadFromYaml() throws ClassNotFoundException {
        var factory = new SimpleYamlModelFactory();
        var clazz = ExampleYamlData.class;
        var model = factory.createModel(clazz);
        var data = new ExampleYamlData();
        data.setListProp(createExamples());

        var resolver = new SimpleYamlValueResolver();

        var file = new File(path);
        var configuration  = YamlConfiguration.loadConfiguration(file);

        for(var content : model.getContents())
        {
            if(!content.isList())
            {
                continue;
            }
            var list = resolver.getListContent(configuration, content);
        }
    }

    @Test
    public void saveToYaml() throws ClassNotFoundException, IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        var factory = new SimpleYamlModelFactory();
        var clazz = ExampleYamlData.class;
        var model = factory.createModel(clazz);

        var data = new ExampleYamlData();
        data.setListProp(createExamples());


        var resolver = new SimpleYamlValueResolver();


        var file = new File(path);
        var configuration  = YamlConfiguration.loadConfiguration(file);

        for(var content : model.getContents())
        {
            if(content.isList())
            {
                resolver.setListContent(data, configuration, content);
                continue;
            }
            resolver.setValue(data, configuration,content, true);
        }
        configuration.save(path);
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