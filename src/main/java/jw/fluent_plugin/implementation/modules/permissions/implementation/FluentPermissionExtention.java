package jw.fluent_plugin.implementation.modules.permissions.implementation;

import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.spigot.permissions.api.PermissionModel;
import jw.fluent_api.spigot.permissions.api.PermissionSection;
import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.FluentApi;
import jw.fluent_plugin.implementation.modules.permissions.api.FluentPermission;

import java.util.List;

public class FluentPermissionExtention implements FluentApiExtention
{

    private FluentPermissionBuilderImpl builder;

    public FluentPermissionExtention(FluentPermissionBuilderImpl builder)
    {
        this.builder = builder;
    }


    @Override
    public void onConfiguration(FluentApiBuilder api) {

        api.container().register(FluentPermission.class, LifeTime.SINGLETON,container ->
        {
           return builder.build();
        });
    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) throws Exception {

    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) throws Exception {

    }


}
