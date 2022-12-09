package jw.fluent.plugin.implementation.assembly_scanner;

import jw.fluent.api.utilites.java.ClassTypeUtility;
import jw.fluent.plugin.api.assembly_scanner.FluentAssemblyScanner;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class AssemblyScanner implements FluentAssemblyScanner {
    @Getter
    private final List<Class<?>> classes;

    private final HashMap<Class<?>, List<Class<?>>> byInterfaceCatch;

    private final HashMap<Class<?>, List<Class<?>>> byParentCatch;

    private final HashMap<Class<? extends Annotation>, List<Class<?>>> byAnnotationCatch;

    private final HashMap<Package, List<Class<?>>> byPackageCatch;


    public AssemblyScanner(JavaPlugin plugin) {
        classes = loadPluginClasses(plugin.getClass());
        byInterfaceCatch = new HashMap<>();
        byParentCatch = new HashMap<>();
        byPackageCatch = new HashMap<>();
        byAnnotationCatch = new HashMap<>();
    }


    public static List<Class<?>> loadPluginClasses(final Class<?> clazz) {
        final var source = clazz.getProtectionDomain().getCodeSource();
        if (source == null) return Collections.emptyList();
        final var url = source.getLocation();
        try (final var zip = new ZipInputStream(url.openStream()))
        {
            final List<Class<?>> classes = new ArrayList<>();
            while (true) {
                final ZipEntry entry = zip.getNextEntry();
                if (entry == null) break;
                if (entry.isDirectory()) continue;
                var name = entry.getName();
                if (!name.endsWith(".class")) continue;
                name = name.replace('/', '.').substring(0, name.length() - 6);
                try {
                    classes.add(Class.forName(name));
                }
                catch (Exception e)
                {
                    FluentLogger.LOGGER.error("Unable to load class", e);
                }
            }
            return classes;
        } catch (IOException e) {
            FluentLogger.LOGGER.error("Unable to open classes loader for: "+clazz.getName(), e);
            return Collections.emptyList();
        }
    }



    public Collection<Class<?>> findByAnnotation(Class<? extends Annotation> annotation) {
        if (byAnnotationCatch.containsKey(annotation)) {
            return byAnnotationCatch.get(annotation);
        }
        var result = new ArrayList<Class<?>>();
        for (var _class : classes) {
            if (_class.isAnnotationPresent(annotation)) {
                result.add(_class);
            }
        }

        return result;
    }

    public Collection<Class<?>> findByInterface(Class<?> _interface) {
        if (byInterfaceCatch.containsKey(_interface)) {
            return byInterfaceCatch.get(_interface);
        }
        var result = new ArrayList<Class<?>>();
        for (var _class : classes) {
            for (var _classInterface : _class.getInterfaces()) {
                if (_classInterface.equals(_interface)) {
                    result.add(_class);
                    break;
                }
            }
        }
        return result;
    }

    public Collection<Class<?>> findBySuperClass(Class<?> parentClass) {
        if (byParentCatch.containsKey(parentClass)) {
            return byParentCatch.get(parentClass);
        }
        var result = new ArrayList<Class<?>>();
        for (var _class : classes) {
            if (ClassTypeUtility.isClassContainsType(_class, parentClass)) {
                result.add(_class);
            }
        }
        return result;
    }

    public Collection<Class<?>> findByPackage(Package _package) {
        if (byPackageCatch.containsKey(_package)) {
            return byPackageCatch.get(_package);
        }
        var result = new ArrayList<Class<?>>();
        for (var _class : classes) {
            for (var _classInterface : _class.getInterfaces()) {
                if (_classInterface.getPackage().equals(_package)) {
                    result.add(_class);
                    break;
                }
            }
        }
        return result;
    }

}
