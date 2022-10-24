package unit.files;

import jw.fluent_api.logger.OldLogger;
import jw.fluent_api.utilites.files.yml.implementation.reader.YmlReader;
import jw.fluent_api.utilites.java.ClassTypeUtility;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class YmlReaderTests
{

    @Test
    public void shoudReadHashmap() throws IOException, InvalidConfigurationException {

        var configuration = new YamlConfiguration();
        configuration.load("D:\\tmp\\test.yml");


        var def = new ArrayList<ArrayList<Integer>>();
        var a = configuration.getList("rising-sun.time-line",def);
        OldLogger.log("result");
        for(var x : a)
        {
            OldLogger.log("Data",x);
        }
    }

    @Test
    public void shouldReadList() throws IOException, InvalidConfigurationException {

        var configuration = new YamlConfiguration();
        configuration.load("D:\\tmp\\test.yml");


        var type = new ArrayList<HashMap<String,String>>();
        var result = configuration.getConfigurationSection("skins").getKeys(false);
        for(var key : result)
        {
            OldLogger.log(key);
            var subObjects = configuration.getConfigurationSection("skins."+key);
            for(var sub : subObjects.getKeys(false))
            {
                var vakye = configuration.get("skins."+key+"."+sub);
                OldLogger.log("sub object ",sub,vakye);
            }
        }
    }

    @Test
    public void shouldReadFile() throws IOException, InvalidConfigurationException {
        var reader = new YmlReader();
        var result= reader.read("D:\\tmp\\test.yml");
        for(var k : result.entrySet())
        {
            OldLogger.info("Key "+k.getKey(),"value "+k.getValue());
        }
    }
    @Test
    public void shouldFindYmlFiles()
    {
        var file = new File("D:\\MC\\paper_1.19\\plugins\\JW_Piano.jar");
        var result =  ClassTypeUtility.findAllYmlFiles(file);
        for(var k :result)
        {
            OldLogger.info("Name "+k);
        }
    }
}
