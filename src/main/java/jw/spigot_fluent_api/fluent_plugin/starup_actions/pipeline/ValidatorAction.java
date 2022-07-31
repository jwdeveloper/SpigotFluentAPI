package jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline;

import jw.spigot_fluent_api.fluent_mapper.FluentMapper;
import jw.spigot_fluent_api.fluent_mapper.api.MapperProfile;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.data.PipelineOptions;
import jw.spigot_fluent_api.fluent_validator.api.ValidatorProfile;

public class ValidatorAction  implements PluginPipeline{
    @Override
    public void pluginEnable(PipelineOptions options) throws Exception {

        var mappingProfiles = options.getPlugin().getTypeManager().getByInterface(ValidatorProfile.class);
        for(var profile: mappingProfiles)
        {
            FluentMapper.registerMappingProfile((Class<MapperProfile>)profile);
        }
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}