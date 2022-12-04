package unit.files;

import jw.fluent.api.utilites.files.json.JsonUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.nio.file.Paths;

public class JsonUtilityTests {

    private static String path;

    @Before
    public  void SetUp()
    {
        path= Paths.get("").toAbsolutePath().toString();
        path += File.separator + "testfiles";
    }

    @Test
    public void ShouldLoadFile() {
        var testdto = JsonUtility.load(path,"TestDto",TestDto.class);
        Assertions.assertNotNull(testdto);
    }

    @Test
    public void ShouldSaveFile() {
        TestDto testDto = new TestDto();

        var result = JsonUtility.save(testDto,path,"TestDto");
        Assertions.assertTrue(result);
    }
}
