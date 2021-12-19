package jw.spigot_fluent_api.utilites;
import jw.spigot_fluent_api.initialization.FluentPlugin;
import jw.spigot_fluent_api.utilites.files.FileUtility;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ClassTypeUtility
{
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

    public static List<Class<?>> getClassesInPackage(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        String path = FileUtility.pluginsPath();
        ArrayList<String> files = FileUtility.getFolderFilesName(path, "jar");



        for (String file : files) {
            File jar = new File(path + File.separator + file);
            try {
                JarInputStream is = new JarInputStream(new FileInputStream(jar));
                JarEntry entry;
                while ((entry = is.getNextJarEntry()) != null)
                {
                    try {
                        String name = entry.getName();
                        if (name.endsWith(".class")) {
                            String classPath = name.substring(0, entry.getName().length() - 6);
                            classPath = classPath.replaceAll("[\\|/]", ".");
                            if (classPath.contains(packageName)) {
                                classes.add(Class.forName(classPath));
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        FluentPlugin.logError("Could not load class");
                        FluentPlugin.logError(e.getMessage());
                    }

                }
            } catch (Exception ex)
            {
                FluentPlugin.logError("Could not load class");
                FluentPlugin.logError(ex.getMessage());
            }
        }
        return classes;
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
