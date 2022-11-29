package jw.fluent_api.files.implementation;

import jw.fluent_api.files.implementation.file_handlers.ConfigFileHandler;
import jw.fluent_api.files.implementation.file_handlers.CustomFilesHandler;
import jw.fluent_api.files.implementation.file_handlers.JsonFilesHandler;
import jw.fluent_api.files.implementation.file_handlers.YamlFilesHandler;
import jw.fluent_api.files.api.FileHandler;
import jw.fluent_api.files.api.DataContext;
import jw.fluent_api.files.api.CustomFile;
import jw.fluent_plugin.implementation.FluentApi;

import java.io.IOException;
import java.util.HashMap;

public class FilesDataContext implements DataContext {
    protected final String path;
    protected final HashMap<Class<? extends FileHandler>, FileHandler> fileHandlers;

    public FilesDataContext() {

        this(FluentApi.path());
    }

    public FilesDataContext(String path) {
        this.path = path;
        this.fileHandlers = new HashMap<>();
        this.registerFileHandler(new JsonFilesHandler(path));
        this.registerFileHandler(new YamlFilesHandler());
        this.registerFileHandler(new CustomFilesHandler());
        this.registerFileHandler(new ConfigFileHandler());
    }

    public void addConfigFileObject(Object object) {
        final var handler = fileHandlers.get(ConfigFileHandler.class);
        handler.addObject(object);
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

            FluentApi.logger().info("DataContext already contains file handler of type " + fileHandler.getClass().getSimpleName());
            return;
        }

        fileHandlers.put(fileHandler.getClass(), fileHandler);
    }

    public void load() throws IllegalAccessException, InstantiationException, IOException {
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