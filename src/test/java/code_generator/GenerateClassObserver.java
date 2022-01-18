package code_generator;

import example_classes.PlayerStats;
import jw.spigot_fluent_api.utilites.code_generator.ObserverClassGenerator;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

public class GenerateClassObserver
{

    private String path;

    @Before
    public void init()
    {
       path = Paths.get("").toAbsolutePath().toString()+ File.separator+"out"+File.separator+"code_generator";
    }


    @Test
    public void shouldGenerateObserverClass()
    {

        ObserverClassGenerator.generate(PlayerStats.class,path);
    }
}


