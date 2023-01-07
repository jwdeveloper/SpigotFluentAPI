package jw.fluent.api.desing_patterns.dependecy_injection.generator;

import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.api.utilites.benchmark.Benchmarker;
import jw.fluent.api.utilites.code_generator.builders.ClassCodeBuilder;
import jw.fluent.api.utilites.java.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class BenchmarkTest {


  /*  @Test
    public void Benchmark_Default_Container() throws Exception {
        var target = ExampleClass0.class;
        var builder = ContainerBuilder.getContainer();
        var container = builder.build();


        Benchmarker.run("Reflection container", 10,unused ->
        {
            var result = (ExampleClass0) container.find(target);
        });
    }

    @Test
    public void Benchmark_Generated_Container() throws Exception {
        var target = ExampleClass0.class;
        var container = new GeneratedBenchmarkContainer();

        Benchmarker.run("Generated container", 10,unused ->
        {
            var result = (ExampleClass0) container.find(target);
        });
    }

    //@Test
    public void createGeneratedContainer() throws IOException {
        // arrange
        var registrations = ContainerBuilder.getContainer().getConfiguration().getRegistrations();
        var _package = "jw.fluent.api.desing_patterns.dependecy_injection.generator.assets.benchmark";
        var clazzName = "GeneratedBenchmarkContainer";
        var generator = new ContainerGenerator(registrations);

        // act
        var clazzContent = generator.generate(_package, clazzName);
        FileUtility.saveClassFile(clazzContent, true, _package, clazzName);
        // assert
        Assert.assertNotEquals(StringUtils.EMPTY, clazzContent);
    }*/

 //   @Test
    public void createTestingClasses() throws IOException {
        var intAmount = 100;
        var _package = "jw.fluent.api.desing_patterns.dependecy_injection.assets";

        for (var i = 0; i <= intAmount; i++) {
            var builder = new ClassCodeBuilder();
            builder.setPackage(_package);
            builder.setModifiers("public");
            builder.addImport("jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject");
            builder.addImport("jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection");
            var _clazzName = "ExampleClass" + i;
            var dependecyName = "ExampleClass" + (i + 1);

            builder.addAnnotation("Injection");
            builder.setClassName("ExampleClass" + i);
            if (i == intAmount) {
                FileUtility.saveClassFile(builder.build(), true, _package, _clazzName);
                continue;
            }
            var fieldName = "exampleClass" + (i + 1);
            builder.addField(options ->
            {
                options.setType(dependecyName);
                options.setName(fieldName);
                options.setModifier("private final");
            });
            builder.addConstructor(options -> {
                options.setModifiers("public");
                options.addAnnotation("Inject");
                options.addParameter(dependecyName + " " + fieldName);
                options.setBody("this." + fieldName + " = " + fieldName + ";");
            });
            FileUtility.saveClassFile(builder.build(), true, _package, _clazzName);
        }

        var containerBuilder = new ClassCodeBuilder();
        containerBuilder.setClassName("ContainerBuilder");
        containerBuilder.addImport("jw.fluent.api.desing_patterns.dependecy_injection.implementation.containers.builder.ContainerBuilderImpl");
        containerBuilder.setPackage(_package);
        containerBuilder.setModifiers("public");
        containerBuilder.addMethod(options ->
        {

            options.setModifiers("public static");
            options.setType("ContainerBuilderImpl");
            options.setName("getContainer");
            options.setBody(builder ->
            {
                builder.textNewLine("var container = new ContainerBuilderImpl<>();");
                for (var i = 0; i <= intAmount; i++) {
                    var name = "ExampleClass" + i;
                    builder.textNewLine("container.registerTransient(" + name + ".class);");
                }

                builder.textNewLine("return container; ");
            });
        });
        FileUtility.saveClassFile(containerBuilder.build(), true, _package, "ContainerBuilder");



    }


}
