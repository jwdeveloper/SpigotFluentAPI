package jw.spigot_fluent_api.fluent_plugin.configuration.pipeline;

import jw.spigot_fluent_api.fluent_mapper.implementation.FluentMapper;
import jw.spigot_fluent_api.fluent_mapper.interfaces.MapperProfile;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_validator.api.ValidatorProfile;

public class ValidatorAction  implements PluginPipeline{
    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) throws Exception {

        var mappingProfiles = fluentPlugin.getTypeManager().getByInterface(ValidatorProfile.class);
        for(var profile: mappingProfiles)
        {
            FluentMapper.registerMappingProfile((Class<MapperProfile>)profile);
        }
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}