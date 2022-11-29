package jw.fluent_plugin.implementation.modules.mapper;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.FluentApi;

public class FluentMapperExtention implements FluentApiExtention
{


    @Override
    public void onConfiguration(FluentApiBuilder builder) {
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
    public void onFluentApiEnable(FluentApi fluentAPI) throws Exception {

    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) throws Exception {

    }
}
