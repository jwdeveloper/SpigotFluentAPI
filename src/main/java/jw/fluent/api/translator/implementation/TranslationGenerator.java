package jw.fluent.api.translator.implementation;

import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TranslationGenerator {
    public static void generate(String input, String output) throws IOException, IllegalAccessException {
        generate(input, output, "PluginTranslations");
    }

    public static void generate(String input, String output, String clazzName) throws IOException, IllegalAccessException {
        var filePath = output + FileUtility.separator() + clazzName + ".java";
        var file = new File(filePath);
        var configuration = YamlConfiguration.loadConfiguration(new File(input));

        var builder = new MessageBuilder();


        var packageName = output.replace("\\", ".");
        var startIndex = packageName.indexOf("java");
        startIndex = packageName.indexOf(".", startIndex);
        packageName = packageName.substring(startIndex + 1);

        builder.text("package ").text(packageName).text(";").newLine();
        addClass(builder, configuration, 0, clazzName, true);

        var writer = new FileWriter(file);
        writer.write(builder.toString());
        writer.close();
    }


    private static void addClass(MessageBuilder builder, ConfigurationSection root, int offset, String name, boolean isRoot) {
        if (!isRoot) {
            name = name.toUpperCase();
            name = name.replace('-', '_');
        }

        builder.newLine();

        if (!isRoot) {
            builder.space(offset).text("public static class ").text(name).newLine();
        } else {
            builder.space(offset).text("public class ").text(name).newLine();
        }


        builder.space(offset).text("{").newLine();
        for (var permission : root.getKeys(false)) {
            var section = root.getConfigurationSection(permission);
            if (section != null) {
                addClass(builder, section, offset + 4, permission, false);
            } else {
                addProperty(builder, permission, root.getCurrentPath()+"."+permission, offset);
            }
        }
        builder.space(offset).text("}");
        builder.newLine();
    }

    private static void addProperty(MessageBuilder builder, String name, String value, int offset) {
        name = name.toUpperCase();
        name = name.replace('-', '_');
        builder.space(offset + 4).text("public static final String ").text(name).text(" = \"").text(value).text("\";").newLine();
    }
}
