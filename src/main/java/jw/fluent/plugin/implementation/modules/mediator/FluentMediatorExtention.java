package jw.fluent.plugin.implementation.modules.mediator;

import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.desing_patterns.mediator.api.MediatorHandler;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.implementation.assembly_scanner.AssemblyScanner;
import jw.fluent.plugin.implementation.FluentApiSpigot;

public class FluentMediatorExtention implements FluentApiExtension {
    private final AssemblyScanner typeManager;
    public FluentMediatorExtention(AssemblyScanner classTypesManager)
    {
        this.typeManager = classTypesManager;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        var mediators = typeManager.findByInterface(MediatorHandler.class);
        builder.container().register(FluentMediator.class,
                LifeTime.SINGLETON,
                (x) -> new FluentMediatorImpl(mediators, x::find));
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) {

    }
}
