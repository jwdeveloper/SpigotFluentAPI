package example_classes;

import jw.spigot_fluent_api.fluent_mapper.implementation.FluentMapper;
import jw.spigot_fluent_api.fluent_mapper.interfaces.IMap;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestMap implements IMap<TestMap, PlayerStats>
{

    public void test()
    {
        var mapper = new FluentMapper();
        List<TestMap> sa = new ArrayList<>();
        var a = mapper.<TestMap>map(sa);
    }

    @Override
    public PlayerStats map(TestMap testMap)
    {
        return null;
    }
}
