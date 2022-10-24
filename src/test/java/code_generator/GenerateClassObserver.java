package code_generator;

import org.junit.Ignore;
import unit.assets.PlayerStats;
import jw.fluent_api.utilites.code_generator.ObserverClassGenerator;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

public class GenerateClassObserver
{

    private String path;

    @Before
    @Ignore
    public void init()
    {
       path = Paths.get("").toAbsolutePath().toString()+ File.separator+"out"+File.separator+ "code_generator";
    }

    @Test
    @Ignore
    public void shouldGenerateObserverClass()
    {

        ObserverClassGenerator.generate(PlayerStats.class,path);
    }
}


