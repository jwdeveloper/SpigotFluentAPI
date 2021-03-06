package jw.spigot_fluent_api.utilites;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.files.FileUtility;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.stream.Collectors;

public class ClassTypeUtility {
    public static boolean isClassContainsType(Class<?> type, Class<?> searchType) {
        while (true) {
            if (type.isAssignableFrom(searchType)) {
                return true;
            }
            type = type.getSuperclass();

            if (type.equals(Object.class)) {
                return false;
            }
        }
    }


    public static boolean isClassContainsInterface(Class<?> type, Class<?> _interface) {
        while (true) {
            for (var interfaceType : type.getInterfaces()) {
                if (interfaceType.equals(_interface)) {
                    return true;
                }
            }
            type = type.getSuperclass();

            if (type.equals(Object.class)) {
                return false;
            }
        }
    }

    public static List<Class<?>> findClassesInPlugin(JavaPlugin plugin) {
        List<Class<?>> result = new ArrayList<>();
        var pluginFile = FluentPlugin.getPluginFile();
        var packageName = plugin.getClass().getPackageName();
        try {
            JarInputStream is = new JarInputStream(new FileInputStream(pluginFile));
            JarEntry entry;
            while ((entry = is.getNextJarEntry()) != null) {
                try {
                    String name = entry.getName();
                    if (name.endsWith(".class")) {
                        String classPath = name.substring(0, entry.getName().length() - 6);
                        classPath = classPath.replaceAll("[\\|/]", ".");
                        if (classPath.contains(packageName)) {
                            result.add(Class.forName(classPath));
                        }

                    }
                } catch (Exception ex) {
                    FluentPlugin.logException("Could not load class", ex);
                }

            }
        } catch (Exception ex) {
            FluentPlugin.logException("Could not load class", ex);
        }
        return result;
    }



    private static Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            FluentPlugin.logError("Could not load class");
            FluentPlugin.logError(e.getMessage());
        }
        return null;
    }


}
