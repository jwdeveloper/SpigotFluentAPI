package jw.fluent_plugin.implementation.modules.player_context;

import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.FluentContainer;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent_api.player_context.api.PlayerContainerBuilder;
import jw.fluent_api.player_context.implementation.PlayerContainerBuilderImpl;
import jw.fluent_api.player_context.api.PlayerContext;
import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.FluentApi;
import jw.fluent_plugin.implementation.modules.player_context.implementation.FluentPlayerContext;
import jw.fluent_plugin.implementation.modules.player_context.implementation.FluentPlayerContextListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FluentPlayerContextExtention implements FluentApiExtention {

    public Consumer<PlayerContainerBuilder>  options;

    public List<RegistrationInfo> registrationInfos;

    public FluentPlayerContextExtention(Consumer<PlayerContainerBuilder> options)
    {
        this.options = options;
        this.registrationInfos = new ArrayList<>();
    }

    @Override
    public void onConfiguration(FluentApiBuilder builder) {
        builder.container().configure(containerConfiguration ->
        {
           containerConfiguration.onRegistration(onRegistrationEvent ->
           {
               if(!onRegistrationEvent.injectionInfo().hasAnnotation(PlayerContext.class))
               {
                   return true;
               }
               registrationInfos.add(onRegistrationEvent.registrationInfo());
               return false;
           });
        });
        builder.container().register(FluentPlayerContext.class, LifeTime.SINGLETON,(e)->
                {

                    var playerContainerBuilder = new PlayerContainerBuilderImpl();
                    options.accept(playerContainerBuilder);
                    var registrations=  playerContainerBuilder.getConfiguration().getRegistrations();
                    registrationInfos.addAll(registrations);

                    var listener = new FluentPlayerContextListener();
                    return new FluentPlayerContext((FluentContainer) e, registrationInfos, listener);
                });
    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) {

    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) {

    }
}
