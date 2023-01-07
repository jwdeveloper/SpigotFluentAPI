package code_generator;

import jw.fluent.api.utilites.code_generator.builders.ClassCodeBuilder;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;

public class ClassGeneratorTests {


    @Test
    public void test() throws IOException {
        var generator = new ClassCodeBuilder();


        generator.setClassName("ExampleClass")
                .setModifiers("public")
                .setPackage("jw.example.generators")

                .addAnnotation("Data")
                .addAnnotation("Injection")

                .addInterface("Container")
                .addInterface("Example")

                .addComment("this is generated class")
                .addComment("please not modify it")
                .addField(options ->
                {
                    options.addComment("Siema");
                    options.addComment("Ciema");
                    options.setModifier("public");
                    options.setType("Integer");
                    options.setName("value");
                    options.setValue("12");
                })
                .addConstructor(options ->
                {
                    options.setBody("""
                            value = 12;
                            """);
                    options.setModifiers("public");
                })
                .addMethod(options ->
                {
                    options.addComment("Siema");
                    options.addComment("Ciema");
                    options.setModifiers("public");
                    options.setType("void");
                    options.setName("setValue");
                    options.addParameter("int value");
                    options.setBody("""
                            this.value = value;
                            """);
                })
                .addMethod(options ->
                {
                    options.setModifiers("public");
                    options.setType("Integer");
                    options.setName("getValue");
                    options.addParameter("int a");
                    options.addParameter("string b");
                    options.setBody("""
                            value = value + 1;
                            return value;
                            """);
                });

        var result = generator.build();
        var writer = new FileWriter("D:\\MC\\paper_1.19\\plugins\\JW_Piano\\test.txt");
        writer.write(result);
        writer.close();
    }

}
