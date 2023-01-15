package jw.fluent.api.utilites;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.FluentApiBuilder;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.fluent.plugin.implementation.modules.dependecy_injection.FluentInjectionImpl;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class FluentApiMock
{
    private static FluentApiMock INSTANCE;

    @Getter
    private MockPlugin pluginMock;

    @Getter
    private ServerMock serverMock;

    @Getter
    private WorldMock worldMock;

    public PlayerMock getPlayer()
    {
      return  serverMock.addPlayer();
    }

    @Getter
    private FluentInjectionImpl injection;

    private static boolean enabled;

    public FluentApiMock(FluentApiExtension extension)
    {
        if(!enabled)
        {
            return;
        }
        if(MockBukkit.isMocked())
        {
            return;
        }
        serverMock = MockBukkit.mock();
        worldMock = serverMock.addSimpleWorld("world");
        serverMock.addWorld(worldMock);

        pluginMock = MockBukkit.createMockPlugin();

        var apiBuilder = FluentApiBuilder.create(pluginMock);
        apiBuilder.useExtension(extension);
        apiBuilder.build();
        injection = (FluentInjectionImpl) FluentApi.container();
    }

    public static FluentApiMock getInstance()
    {
        enabled = true;
        if(INSTANCE == null)
        {
            INSTANCE = new FluentApiMock(new FluentApiExtension() {
                @Override
                public void onConfiguration(FluentApiSpigotBuilder builder) {

                }

                @Override
                public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

                }

                @Override
                public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

                }
            });
        }
        return INSTANCE;
    }

    public static FluentApiMock getInstance(FluentApiExtension extension)
    {
        enabled = true;
        if(INSTANCE == null)
        {
            INSTANCE = new FluentApiMock(extension);
        }
        return INSTANCE;
    }
}
