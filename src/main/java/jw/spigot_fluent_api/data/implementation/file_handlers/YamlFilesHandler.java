package jw.spigot_fluent_api.data.implementation.file_handlers;

import jw.spigot_fluent_api.data.interfaces.FileHandler;
import jw.spigot_fluent_api.utilites.java.ObjectUtility;
import jw.spigot_fluent_api.utilites.files.yml.YmlFileUtility;

import java.util.ArrayList;
import java.util.List;

public class YamlFilesHandler implements FileHandler {

    private final List<Object> files = new ArrayList<>();

    @Override
    public void load() {
        for (var file: files)
        {
            var data = YmlFileUtility.load(file.getClass().getSimpleName(), file.getClass());
            if (data == null) {
                return;
            }
            ObjectUtility.copyToObject(data, file, data.getClass());
        }
    }

    @Override
    public void save() {
        for (var file: files)
        {
            YmlFileUtility.save(file.getClass().getSimpleName(), file);
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
