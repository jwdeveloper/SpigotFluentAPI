package files;

import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.utilites.files.yml.implementation.reader.YmlReader;
import jw.spigot_fluent_api.utilites.java.ClassTypeUtility;
import org.bukkit.configuration.InvalidConfigurationException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class YmlReaderTests
{
    @Test
    public void shouldReadFile() throws IOException, InvalidConfigurationException {
        var reader = new YmlReader();
        var result= reader.read("D:\\tmp\\test.yml");
        for(var k : result.entrySet())
        {
            FluentLogger.info("Key "+k.getKey(),"value "+k.getValue());
        }
    }
    @Test
    public void shouldFindYmlFiles()
    {
        var file = new File("D:\\MC\\paper_1.19\\plugins\\JW_Piano.jar");
        var result =  ClassTypeUtility.findAllYmlFiles(file);
        for(var k :result)
        {
            FluentLogger.info("Name "+k);
        }
    }
}
