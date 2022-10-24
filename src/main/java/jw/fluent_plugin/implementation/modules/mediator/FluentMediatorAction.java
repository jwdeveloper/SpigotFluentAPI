package jw.fluent_plugin.implementation.modules.mediator;

import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.RegistrationType;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent_api.desing_patterns.mediator.api.MediatorHandler;
import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_plugin.api.PluginAction;
import jw.fluent_plugin.api.options.PipelineOptions;
import jw.fluent_plugin.implementation.FluentAPI;
import jw.fluent_plugin.implementation.modules.dependecy_injection.FluentInjectionImpl;

public class FluentMediatorAction implements PluginAction
{
    @Override
    public void pluginEnable(PipelineOptions options) throws Exception
    {
       var mediators= FluentAPI.injection()
                                .findAllByInterface(MediatorHandler.class);

       var mediatorImpl = new FluentMediatorImpl();
       for(var mediator : mediators)
       {
           mediatorImpl.register(mediator);
       }

        var registerInfo = new RegistrationInfo(FluentMediator.class, FluentMediatorImpl.class, null, LifeTime.SINGLETON, RegistrationType.InterfaceAndIml);
        var injection = (FluentInjectionImpl)FluentAPI.injection();
        injection.getContainer().register(registerInfo);
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception
    {

    }
}
