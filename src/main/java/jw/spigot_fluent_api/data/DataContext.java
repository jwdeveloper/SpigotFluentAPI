package jw.spigot_fluent_api.data;

import jw.spigot_fluent_api.data.repositories.Repository;
import jw.spigot_fluent_api.utilites.files.json.JsonUtility;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.ObjectUtility;
import jw.spigot_fluent_api.utilites.files.yml.YmlFileUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataContext implements DataHandler {
    protected final String path;
    protected final List<Saveable> saveables = new ArrayList<>();
    protected final List<Object> ymlFiles = new ArrayList<>();
    protected final List<Object> jsonFiles = new ArrayList<>();

    public DataContext() {
        this(FluentPlugin.getPath());
    }

    public DataContext(String path) {
        this.path = path;
    }

    public void addSaveableObject(Collection<Saveable> objects) {
        saveables.addAll(objects);
    }
    public void addYmlObjects(Collection<Object> objects) {
        ymlFiles.addAll(objects);
    }

    public void addJsonObjects(Collection<Object> objects) {
        jsonFiles.addAll(objects);
    }

    public void load() {
        for (var saveable : saveables) {
            saveable.load();
        }
        for (var obj : ymlFiles) {
            var data = YmlFileUtility.load(obj.getClass().getSimpleName(), obj.getClass());
            if (data == null) {
                return;
            }
            ObjectUtility.copyToObject(data, obj, data.getClass());
        }
        for (var jsonObject : jsonFiles) {
            try {
                var loadedObject = JsonUtility.load(
                        path,
                        jsonObject.getClass().getSimpleName(),
                        jsonObject.getClass());

                if (loadedObject != null) {
                    ObjectUtility.copyToObject(loadedObject, jsonObject, loadedObject.getClass());
                }

            } catch (Exception e) {
                FluentPlugin.logException("Loading file data error", e);
            }
        }
    }

    public void save() {
        for (Saveable saveable : saveables) {
            saveable.save();
        }
        for (var obj : ymlFiles) {
            YmlFileUtility.save(obj.getClass().getSimpleName(), obj);
        }
        for (var obj : jsonFiles) {
            JsonUtility.save(obj, path, obj.getClass().getSimpleName());
        }
    }
}