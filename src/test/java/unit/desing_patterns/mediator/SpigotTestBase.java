package unit.desing_patterns.mediator;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import jw.fluent_plugin.implementation.FluentApi;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Before;

public abstract class SpigotTestBase
{
    protected ServerMock server;
    protected MockPlugin mockPlugin;

    @SneakyThrows
    @Before
    public void init()
    {
        server = MockBukkit.mock();
        mockPlugin = MockBukkit.createMockPlugin("TestPlugin");
        FluentApi.init(mockPlugin);
    }

    @After
    public void stop()
    {
        MockBukkit.unmock();
    }
}
