package jw.spigot_fluent_api.fluent_plugin.configuration.pipeline;
import jw.spigot_fluent_api.fluent_mapper.FluentMapper;
import jw.spigot_fluent_api.fluent_mapper.interfaces.MapperProfile;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

public class MapperAction implements PluginPipeline{
    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) throws Exception {

        var mappingProfiles = fluentPlugin.getTypeManager().getByInterface(MapperProfile.class);
        for(var profile: mappingProfiles)
        {
            FluentPlugin.logSuccess(profile.getName());
            FluentMapper.registerMappingProfile((Class<MapperProfile>)profile);
        }
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}
