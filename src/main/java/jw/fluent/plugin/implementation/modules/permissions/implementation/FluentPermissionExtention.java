package jw.fluent.plugin.implementation.modules.permissions.implementation;

import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.plugin.api.FluentApiBuilder;
import jw.fluent.plugin.implementation.modules.permissions.api.FluentPermission;
import jw.fluent.plugin.api.FluentApiExtention;
import jw.fluent.plugin.implementation.FluentApi;

public class FluentPermissionExtention implements FluentApiExtention
{

    private FluentPermissionBuilderImpl builder;

    public FluentPermissionExtention(FluentPermissionBuilderImpl builder)
    {
        this.builder = builder;
    }


    @Override
    public void onConfiguration(FluentApiBuilder api) {

        api.container().register(FluentPermission.class, LifeTime.SINGLETON, container ->
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
