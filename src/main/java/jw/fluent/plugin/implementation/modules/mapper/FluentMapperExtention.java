package jw.fluent.plugin.implementation.modules.mapper;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.implementation.FluentApiSpigot;

public class FluentMapperExtention implements FluentApiExtension
{


    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        builder.container().register(FluentMapper.class, LifeTime.SINGLETON,(x) ->
        {
            //TODO mapper
         /*   var mappingProfiles = options.getClassTypesManager().findByInterface(MapperProfile.class);
            for(var profile: mappingProfiles)
            {
                // mapper.register((Class<MapperProfile<?,?>>)profile);
            }*/
            return new FluentMapperImpl(x::find);
        });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

    }
}
