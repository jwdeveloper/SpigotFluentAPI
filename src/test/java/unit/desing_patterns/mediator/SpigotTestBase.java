package unit.desing_patterns.mediator;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.junit.After;
import org.junit.Before;

public abstract class SpigotTestBase
{
    protected ServerMock server;
    protected MockPlugin mockPlugin;

    @Before
    public void init()
    {
        server = MockBukkit.mock();
        mockPlugin = MockBukkit.createMockPlugin("TestPlugin");
        FluentPlugin.setPlugin(mockPlugin);
    }

    @After
    public void stop()
    {
        MockBukkit.unmock();
    }
}
