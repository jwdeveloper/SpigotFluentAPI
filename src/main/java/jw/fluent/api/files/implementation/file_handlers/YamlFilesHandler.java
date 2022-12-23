package jw.fluent.api.files.implementation.file_handlers;

import jw.fluent.api.files.api.FileHandler;
import jw.fluent.api.files.implementation.yaml_reader.implementation.SimpleYamlReader;
import jw.fluent.plugin.api.config.ConfigSection;
import jw.fluent.api.files.implementation.yaml_reader.implementation.YmlFileUtility;

import java.util.ArrayList;
import java.util.List;

public class YamlFilesHandler implements FileHandler {

    private final List<Object> files = new ArrayList<>();
    private SimpleYamlReader yamlReader;

    public YamlFilesHandler()
    {
        yamlReader = new SimpleYamlReader();
    }

    @Override
    public void load() {
        for (var file: files)
        {
            if(file instanceof ConfigSection)
            {
                continue;
            }
         /*   var data = YmlFileUtility.load(file.getClass().getSimpleName(), file);
            if (data == null) {
                YmlFileUtility.save(file.getClass().getSimpleName(), file);
                return;
            }
            ObjectUtility.copyToObject(data, file, data.getClass());*/
        }
    }

    @Override
    public void save() {

        for (var file: files)
        {
            if(file instanceof ConfigSection)
            {
                continue;
            }
           // YmlFileUtility.save(file.getClass().getSimpleName(), file);
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
