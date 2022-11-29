package jw.fluent_plugin.implementation.modules.mediator;

import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.desing_patterns.mediator.api.MediatorHandler;
import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_api.utilites.ClassTypesManager;
import jw.fluent_plugin.implementation.FluentApi;

public class FluentMediatorExtention implements FluentApiExtention {
    private final ClassTypesManager typeManager;
    public FluentMediatorExtention(ClassTypesManager classTypesManager)
    {
        this.typeManager = classTypesManager;
    }

    @Override
    public void onConfiguration(FluentApiBuilder builder) {
        var mediators = typeManager.findByInterface(MediatorHandler.class);
        builder.container().register(FluentMediator.class,
                LifeTime.SINGLETON,
                (x) -> new FluentMediatorImpl(mediators, x::find));
    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) throws Exception {

    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) {

    }
}
