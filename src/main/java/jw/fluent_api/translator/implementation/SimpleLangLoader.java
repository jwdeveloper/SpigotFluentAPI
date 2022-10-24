package jw.fluent_api.translator.implementation;

import jw.fluent_api.translator.api.models.LangData;
import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_api.utilites.files.FileUtility;
import jw.fluent_api.utilites.files.yml.implementation.reader.YmlReader;
import jw.fluent_api.utilites.java.ClassTypeUtility;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class SimpleLangLoader {

    private final String languagePath = "languages";
    private final String baseTranslation = "en";

    public List<LangData> load(String folderPath, String languageName) throws IOException, InvalidConfigurationException {

        var result = new ArrayList<LangData>();
        var reader = new YmlReader();
        var files = FileUtility.getFolderFilesName(folderPath, "yml");
        for (var file : files) {
            var name = StringUtils.split(file, ".")[0];
            var langData = new LangData();
            langData.setCountry(name);
            Map<String, String> translations = new LinkedHashMap<String, String>();
            if (name.equals(baseTranslation)) {
                var path = folderPath + File.separator + file;
                translations = reader.read(path);
            }
            if (!languageName.equals("en") && languageName.equals(name)) {
                var path = folderPath + File.separator + file;
                translations = reader.read(path);
            }
            langData.setTranslations(translations);
            result.add(langData);
        }
        return result;
    }


    public void generateFiles(String outputPath) throws IOException, InvalidConfigurationException {
        var file = FluentPlugin.getPluginFile();
        var langPaths = ClassTypeUtility.findAllYmlFiles(file);
        var reader = new YmlReader();
        var results = new HashMap<String, YamlConfiguration>();
        for (var path : langPaths) {
            if (!path.contains(languagePath)) {
                continue;
            }
            var fileName = getPathName(path);
            var resource = FluentPlugin.getPlugin().getResource(path);
            var generated = reader.read(resource);

            var configuration = new YamlConfiguration();
            if (results.containsKey(fileName)) {
                configuration = results.get(fileName);
            } else {
                results.put(fileName, configuration);
            }

            for (var line : generated.entrySet()) {
                configuration.set(line.getKey(), line.getValue());
            }
        }

        for (var result : results.entrySet()) {
            result.getValue().save(outputPath + File.separator + result.getKey());
        }
    }

    private String getPathName(String path) {
        return Paths.get(path).getFileName().toString();
    }
}
