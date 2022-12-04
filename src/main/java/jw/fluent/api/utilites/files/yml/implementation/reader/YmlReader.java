package jw.fluent.api.utilites.files.yml.implementation.reader;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class YmlReader {

    public Map<String, String> read(InputStream inputStream) throws IOException, InvalidConfigurationException {
        var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        var configuration = new YamlConfiguration();
        var result = new LinkedHashMap<String,String>();
        configuration.load(reader);
        var keys = configuration.getKeys(true);
        for(var key: keys)
        {
            if(!configuration.isString(key))
            {
                continue;
            }
            result.put(key,configuration.getString(key));
        }
        return result;
    }

    public Map<String, String> read(String path) throws IOException, InvalidConfigurationException {
        var configuration = new YamlConfiguration();
        var result = new LinkedHashMap<String,String>();
        configuration.load(path);
        var keys = configuration.getKeys(true);
        for(var key: keys)
        {
            if(!configuration.isString(key))
            {
                continue;
            }
            result.put(key,configuration.getString(key));
        }
        return result;
    }
}
