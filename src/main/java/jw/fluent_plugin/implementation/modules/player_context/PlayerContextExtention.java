package jw.fluent_plugin.implementation.modules.player_context;

import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.FluentContainer;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.FluentApi;
import jw.fluent_plugin.implementation.modules.logger.FluentLogger;

import java.util.function.Consumer;

public class PlayerContextExtention implements FluentApiExtention {


    public Consumer<PlayerContainerBuilder>  options;

    public PlayerContainerBuilderImpl playerContainerBuilder;

    public PlayerContextExtention(Consumer<PlayerContainerBuilder> options)
    {
        this.options = options;
        this.playerContainerBuilder = new PlayerContainerBuilderImpl();
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
               FluentLogger.LOGGER.log("Rejestration added",onRegistrationEvent.input().getSimpleName());
               playerContainerBuilder.getConfiguration().addRegistration(onRegistrationEvent.registrationInfo());
               return false;
           });
        });
        builder.container().register(FluentPlayerContext.class, LifeTime.SINGLETON,(e)->
                {
                    options.accept(playerContainerBuilder);
                    var registrations=  playerContainerBuilder.getConfiguration().getRegistrations();
                    return new FluentPlayerContext((FluentContainer) e, registrations);
                });
    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) {

    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) {

    }
}
