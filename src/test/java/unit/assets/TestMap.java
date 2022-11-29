package unit.assets;

import jw.fluent_api.mapper.implementation.SimpleMapper;
import jw.fluent_api.mapper.api.IMap;

import java.util.ArrayList;
import java.util.List;

public class TestMap implements IMap<TestMap, PlayerStats>
{

    public void test()
    {
        var mapper = new SimpleMapper(null);
        List<TestMap> sa = new ArrayList<>();
        var a = mapper.map(sa,PlayerStats.class);
    }

    @Override
    public PlayerStats map(TestMap testMap)
    {
        return null;
    }
}
