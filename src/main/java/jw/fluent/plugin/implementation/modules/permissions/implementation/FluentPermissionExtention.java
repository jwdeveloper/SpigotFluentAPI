package jw.fluent.plugin.implementation.modules.permissions.implementation;

import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.implementation.modules.permissions.api.FluentPermission;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.implementation.FluentApiSpigot;

public class FluentPermissionExtention implements FluentApiExtension
{

    private FluentPermissionBuilderImpl builder;

    public FluentPermissionExtention(FluentPermissionBuilderImpl builder)
    {
        this.builder = builder;
    }


    @Override
    public void onConfiguration(FluentApiSpigotBuilder api) {

        api.container().register(FluentPermission.class, LifeTime.SINGLETON, container ->
        {
           return builder.build();
        });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

    }


}
