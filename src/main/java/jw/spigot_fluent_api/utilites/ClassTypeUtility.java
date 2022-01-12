package jw.spigot_fluent_api.utilites;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.files.FileUtility;

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
            for (var interfaceType : type.getInterfaces()) {
                if (interfaceType.equals(searchType)) {
                    return true;
                }
            }
            type = type.getSuperclass();

            if (type.equals(Object.class)) {
                return false;
            }
        }
    }

    public static List<Class<?>> getClassesInPackageFromPluginsDirectory(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        String path = FileUtility.pluginsPath();
        ArrayList<String> files = FileUtility.getFolderFilesName(path, "jar");
        for (String file : files) {
            File jar = new File(path + File.separator + file);
            try {
                JarInputStream is = new JarInputStream(new FileInputStream(jar));
                JarEntry entry;
                while ((entry = is.getNextJarEntry()) != null) {
                    try {
                        String name = entry.getName();
                        if (name.endsWith(".class")) {
                            String classPath = name.substring(0, entry.getName().length() - 6);
                            classPath = classPath.replaceAll("[\\|/]", ".");
                            if (classPath.contains(packageName)) {
                                classes.add(Class.forName(classPath));
                            }
                        }
                    } catch (Exception e) {
                        FluentPlugin.logError("Could not load class");
                        FluentPlugin.logError(e.getMessage());
                    }

                }
            } catch (Exception ex) {
                FluentPlugin.logError("Could not load class");
                FluentPlugin.logError(ex.getMessage());
            }
        }
        return classes;
    }

    // not working yet
    public static List<Class<?>> getClassesFromPluginClassLoader(JavaPlugin plugin) {
        try {
            var classLoaderFiled = JavaPlugin.class.getDeclaredField("classLoader");
            classLoaderFiled.setAccessible(true);
            var classLoaderObject = classLoaderFiled.get(plugin);
            classLoaderFiled.setAccessible(false);


            var pluginClassLoaderClass = Class.forName("org.bukkit.plugin.java.PluginClassLoader");
            var pluginClassLoaderObject = pluginClassLoaderClass.cast(classLoaderObject);

            var classesField = pluginClassLoaderObject.getClass().getDeclaredField("classes");
            classesField.setAccessible(true);
            var classes = classesField.get(pluginClassLoaderObject);
            classesField.setAccessible(false);



            var listOfClasses = (Map<String, Class<?>>) classes;
            for(var a : listOfClasses.entrySet())
            {
                FluentPlugin.logSuccess("VAlue "+a.getValue());
            }
            return listOfClasses.values().stream().toList();
        } catch (Exception e) {
            FluentPlugin.logException("Could not load classes from pluginClassLoader", e);
            return findClassesInPackage(plugin.getClass().getPackageName());
        }
    }


    public static List<Class<?>> findClassesInPackage(String packageName) {
        List<Class<?>> result = new ArrayList<>();
        var pluginFile = FluentPlugin.getPluginFile();
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


    public static Set<Class> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
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

    //Not working yet
    public <T> Class<T> getGenericTypeClass() {
        try {
            String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            Class<?> clazz = Class.forName(className);
            return (Class<T>) clazz;
        } catch (Exception e) {
            throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
        }
    }
}
