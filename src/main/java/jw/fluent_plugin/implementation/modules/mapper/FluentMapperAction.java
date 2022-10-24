package jw.fluent_plugin.implementation.modules.mapper;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.RegistrationType;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import jw.fluent_api.mapper.api.MapperProfile;
import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_plugin.api.PluginAction;
import jw.fluent_plugin.api.options.PipelineOptions;
import jw.fluent_plugin.implementation.FluentAPI;
import jw.fluent_plugin.implementation.modules.dependecy_injection.FluentInjectionImpl;

public class FluentMapperAction implements PluginAction
{
    @Override
    public void pluginEnable(PipelineOptions options) throws Exception
    {
        var mappingProfiles = options.getPlugin().getTypeManager().getByInterface(MapperProfile.class);
        var mapper = new FluentMapperImpl();

        for(var profile: mappingProfiles)
        {
            mapper.register((Class<MapperProfile<?,?>>)profile);
        }

        var registerInfo = new RegistrationInfo(FluentMapper.class,FluentMapperImpl.class, null, LifeTime.SINGLETON, RegistrationType.InterfaceAndIml);
        var injection = (FluentInjectionImpl)FluentAPI.injection();
        injection.getContainer().register(registerInfo);
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception
    {

    }
}
