package jw.fluent.api.spigot.permissions.implementation;

import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.spigot.permissions.api.PermissionModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PermissionGenerator
{

    public static void generate(Class<?> clazz, String outputPath) throws IOException, IllegalAccessException {
        generate(clazz,outputPath,"PluginPermissions");
    }

    public static void generate(Class<?> clazz, String outputPath, String clazzName) throws IOException, IllegalAccessException {
        var permissionResolver = new PermissionModelResolver();
        var filePath = outputPath+ FileUtility.separator()+clazzName+".java";
        var file = new File(filePath);
        var model = permissionResolver.createModels(clazz);

        var builder = new MessageBuilder();


        var packageName =outputPath.replace("\\",".");
        var startIndex = packageName.indexOf("java");
        startIndex = packageName.indexOf(".",startIndex);
        packageName = packageName.substring(startIndex+1);

        builder.text("package ").text(packageName).text(";").newLine();
        addClass(builder, model,0,clazzName,true);

        var writer = new FileWriter(file);
        writer.write(builder.toString());
        writer.close();
    }




    private static void addClass(MessageBuilder builder, PermissionModel root, int offset, String name, boolean isRoot)
    {

        if(!isRoot)
        {
            name = name.toUpperCase();
            name = name.replace('-','_');
        }

        builder.newLine();

        if(!isRoot)
        {
            builder.space(offset).text("public static class ").text(name).newLine();
        }
        else
        {
            builder.space(offset).text("public class ").text(name).newLine();
        }


        builder.space(offset).text("{").newLine();
        addProperty(builder, "BASE", root.getFullPath(), offset);
        for (var permission : root.getChildren()) {
            if (permission.hasChildren()) {
                addClass(builder, permission,offset+4, permission.getName(), false);
            } else {
                addProperty(builder, permission.getName(), permission.getFullPath(),offset);
            }
        }
        builder.space(offset).text("}");
        builder.newLine();
    }

    private static void addProperty(MessageBuilder builder, String name, String value, int offset) {
        name = name.toUpperCase();
        name = name.replace('-','_');
        builder.space(offset+4).text("public static final String ").text(name).text(" = \"").text(value).text("\";").newLine();
    }
}
