package jw.spigot_fluent_api.data.implementation;

import jw.spigot_fluent_api.data.implementation.file_handlers.CustomFilesHandler;
import jw.spigot_fluent_api.data.implementation.file_handlers.JsonFilesHandler;
import jw.spigot_fluent_api.data.implementation.file_handlers.YamlFilesHandler;
import jw.spigot_fluent_api.data.interfaces.FileHandler;
import jw.spigot_fluent_api.data.interfaces.FluentDataContext;
import jw.spigot_fluent_api.data.interfaces.CustomFile;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

import java.util.Collection;
import java.util.HashMap;

public class DataContext implements FluentDataContext {
    protected final String path;
    protected final HashMap<Class<? extends FileHandler>, FileHandler> fileHandlers;

    public DataContext() {
        this(FluentPlugin.getPath());
    }

    public DataContext(String path) {
        this.path = path;
        this.fileHandlers = new HashMap<>();
        this.registerFileHandler(new JsonFilesHandler(path));
        this.registerFileHandler(new YamlFilesHandler());
        this.registerFileHandler(new CustomFilesHandler());
    }

    @Override
    public void addCustomFileObject(CustomFile object) {
        final var handler = fileHandlers.get(CustomFilesHandler.class);
        handler.addObject(object);
    }

    @Override
    public void addYmlObject(Object object) {
        final var handler = fileHandlers.get(YamlFilesHandler.class);
        handler.addObject(object);
    }

    @Override
    public void addJsonObject(Object object) {
       final var handler = fileHandlers.get(JsonFilesHandler.class);
       handler.addObject(object);
    }

    @Override
    public void addObject(Class<? extends FileHandler> handlerType, Object object) {
        if (!fileHandlers.containsKey(handlerType))
            return;

        var handler = fileHandlers.get(handlerType);
        handler.addObject(object);
    }

    @Override
    public void registerFileHandler(FileHandler fileHandler) {
        if (fileHandlers.containsKey(fileHandler.getClass())) {
            FluentPlugin.logError("DataContext already contains file handler of type " + fileHandler.getClass().getSimpleName());
            return;
        }

        fileHandlers.put(fileHandler.getClass(), fileHandler);
    }

    public void load() {
        for (var filesHandler : fileHandlers.values()) {
            filesHandler.load();
        }
    }

    public void save() {
        for (var filesHandler : fileHandlers.values()) {
            filesHandler.save();
        }
    }
}