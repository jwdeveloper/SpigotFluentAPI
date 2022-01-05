package spigotTests;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class Testss
{
    private ServerMock server;
    private MockPlugin mockPlugin;

    @Before
    public void setUp()
    {
        server = MockBukkit.mock();
        mockPlugin = MockBukkit.createMockPlugin("TestPlugin");
        FluentPlugin.setPlugin(mockPlugin);
    }

    @Test
    public void test()
    {
        int i =0;
        server.addPlayer("Jacek");
        var players = server.getOnlinePlayers();
        FluentPlugin.logError("error");
        server.getConsoleSender().sendMessage("aaaaa");
        Assertions.assertEquals(1,players.size());

    }

    @After
    public void tearDown()
    {
        MockBukkit.unmock();
    }

}
