package assets;

import jw.spigot_fluent_api.fluent_mapper.implementation.SimpleMapper;
import jw.spigot_fluent_api.fluent_mapper.interfaces.IMap;

import java.util.ArrayList;
import java.util.List;

public class TestMap implements IMap<TestMap, PlayerStats>
{

    public void test()
    {
        var mapper = new SimpleMapper();
        List<TestMap> sa = new ArrayList<>();
        var a = mapper.map(sa,PlayerStats.class);
    }

    @Override
    public PlayerStats map(TestMap testMap)
    {
        return null;
    }
}
