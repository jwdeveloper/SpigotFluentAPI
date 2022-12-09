package jw.fluent.api.files.implementation.file_handlers;

import jw.fluent.api.files.api.FileHandler;
import jw.fluent.plugin.implementation.config.config_sections.FluentConfigSection;
import jw.fluent.api.utilites.java.ObjectUtility;
import jw.fluent.api.files.implementation.yml.YmlFileUtility;

import java.util.ArrayList;
import java.util.List;

public class YamlFilesHandler implements FileHandler {

    private final List<Object> files = new ArrayList<>();
    @Override
    public void load() {
        for (var file: files)
        {
            if(file instanceof FluentConfigSection)
            {
                continue;
            }
            var data = YmlFileUtility.load(file.getClass().getSimpleName(), file);
            if (data == null) {
                YmlFileUtility.save(file.getClass().getSimpleName(), file);
                return;
            }
            ObjectUtility.copyToObject(data, file, data.getClass());
        }
    }

    @Override
    public void save() {

        for (var file: files)
        {
            if(file instanceof FluentConfigSection)
            {
                continue;
            }
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
