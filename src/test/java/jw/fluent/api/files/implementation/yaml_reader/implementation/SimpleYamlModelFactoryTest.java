package jw.fluent.api.files.implementation.yaml_reader.implementation;

import jw.fluent.api.files.implementation.yaml_reader.implementation.assets.ExampleYamlNested;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SimpleYamlModelFactoryTest {

    @Test
    void createModel() throws ClassNotFoundException {
        var exampleYamlRoot  = new ExampleYamlNested();
        var sut = new SimpleYamlModelFactory();
        var model  =sut.createModel(exampleYamlRoot.getClass());

        Assertions.assertEquals("ExampleConfig",model.getFileName());
        Assertions.assertEquals(2,model.getContents().size());

        var child = model.getContents().get(0);
        Assertions.assertEquals(4,child.getChildren().size());
        Assertions.assertEquals("example.path.one",child.getPath());
    }
}