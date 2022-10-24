package jw.fluent_api.utilites.java;

import jw.fluent_api.logger.OldLogger;
import jw.fluent_plugin.implementation.FluentPlugin;

import org.bukkit.plugin.java.JavaPlugin;


import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

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
                    OldLogger.error("Could not load class", ex);
                }

            }
        } catch (Exception ex) {
            OldLogger.error("Could not load class", ex);
        }
        return result;
    }

    public static List<String> findAllYmlFiles(File file) {
        List<String> result = new ArrayList<>();
        try {
            JarInputStream is = new JarInputStream(new FileInputStream(file));
            JarEntry entry;
            while ((entry = is.getNextJarEntry()) != null) {
                try {
                    String name = entry.getName();
                    if (!name.endsWith(".yml")) {
                       continue;
                    }
                    result.add(name);
                } catch (Exception ex) {
                    OldLogger.error("Could not load class", ex);
                }

            }
        } catch (Exception ex) {
            OldLogger.error("Could not load class", ex);
        }
        return result;
    }


    private static Type[] getInterfaceGenericTypes(Class<?> _class, Class<?> _interface)
    {
        ParameterizedType validator = null;
        for (var classInterface : _class.getGenericInterfaces()) {
            var name = classInterface.getTypeName();
            if (name.contains(_interface.getClass().getSimpleName())) {
                validator = (ParameterizedType) classInterface;
                break;
            }
        }
        if (validator == null)
            return null;

        return validator.getActualTypeArguments();
    }

    private static Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            OldLogger.error("Could not load class", e);
        }
        return null;
    }


}
