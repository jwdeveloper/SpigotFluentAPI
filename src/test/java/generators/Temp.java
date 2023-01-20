package generators;

import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.utilites.TemplateUtility;
import jw.fluent.api.utilites.code_generator.builders.ClassCodeBuilder;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import org.bukkit.util.FileUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class Temp {
    @Test
    public void run() throws IOException {
        String input = "D:\\Git\\SpigotFluentAPI\\temp.txt";
        String output = "D:\\Git\\SpigotFluentAPI\\src\\test\\java";
        String outputName = "BaseClassesNMS";
        String namesace = "io.github.jwdeveloper.spigot.protocolcore";

        var builder = new ClassCodeBuilder();
          builder.setPackage(namesace);
          builder.addImport("io.github.jwdeveloper.reflect.implementation.FluentBuilder");
        builder.addImport("io.github.jwdeveloper.reflect.api.models.ClassModel");
        builder.addImport("io.github.jwdeveloper.reflect.implementation.FluentReflect");
        builder.addImport("io.github.jwdeveloper.reflect.implementation.builders.JavaClassBuilder");
        builder.addImport("io.github.jwdeveloper.reflect.implementation.models.JavaClassModel");
        builder.setModifiers("public");
        builder.setClassName(outputName);

        var content = FileUtility.loadFile(input);

        var mb = new MessageBuilder();
        for (var value : content) {


            var val = value;
            var name = getFieldName(value);
            if (value.contains("+")) {
                name += "_V";
                val = val.replace("+ ver +", "+ version +");
            }
            var body = bodyTemplate(name, val);
            //FluentLogger.LOGGER.log(name, value);
            mb.textNewLine(body);
            String finalName = name;
            builder.addField(e ->
            {
                e.setModifier("public static");
                e.setType("FluentBuilder<JavaClassBuilder, JavaClassModel>");
                e.setName(finalName);
            });
        }

        builder.addMethod(e ->
        {
            e.addParameter("FluentReflect reflect");
            e.addParameter("String version");
            e.setName("setup");
            e.setModifiers("public static");
            e.setType("void");
            e.addBodyLine(mb.toString());
        });

        var clazz = builder.build();
        FileUtility.saveClassFile(clazz, false, namesace, outputName,"D:\\Git\\ProtocolCore\\");
    }

    private String bodyTemplate(String field, String value) {
        var mb = new MessageBuilder();
        mb.text(field).space().textNewLine(" = reflect.findClass().forAnyVersion(finder ->");
        mb.textNewLine("{");
        mb.textNewLine("  finder.withName(\"" + value + "\");");
        mb.textNewLine("  });");
        return mb.toString();
    }

    public String getFieldName(String name) {
        int lastDotIndex = name.lastIndexOf(".");
        name = name.substring(lastDotIndex + 1);
        return name.replaceAll("(?<=[a-z])(?=[A-Z])", "_").toUpperCase();
    }

}
