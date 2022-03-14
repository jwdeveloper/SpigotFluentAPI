package jw.spigot_fluent_api.data.implementation.file_handlers;

import jw.spigot_fluent_api.data.interfaces.FileHandler;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.ObjectUtility;
import jw.spigot_fluent_api.utilites.files.json.JsonUtility;

import java.util.ArrayList;
import java.util.List;

public class JsonFilesHandler implements FileHandler {

    private final List<Object> files = new ArrayList<>();
    private final String path;

    public JsonFilesHandler(String path)
    {
        this.path = path;
    }

    @Override
    public void load() {
        for (var file : files) {
            try {
                var loadedObject = JsonUtility.load(
                        path,
                        file.getClass().getSimpleName(),
                        file.getClass());

                if (loadedObject != null) {
                    ObjectUtility.copyToObject(loadedObject, file, loadedObject.getClass());
                }

            } catch (Exception e) {
                FluentPlugin.logException("Loading file data error", e);
            }
        }
    }

    @Override
    public void save() {
        for (var file : files) {
            JsonUtility.save(file, path, file.getClass().getSimpleName());
        }
    }

    @Override
    public void addObject(Object object) {
        files.add(object);
    }

    @Override
    public void removeObject(Object object) {
        files.remove(object);
    }
}
