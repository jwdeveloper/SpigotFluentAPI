package jw.fluent_plugin.starup_actions;

import jw.fluent_api.mapper.FluentMapper;
import jw.fluent_api.mapper.api.MapperProfile;
import jw.fluent_plugin.starup_actions.api.PluginPipeline;
import jw.fluent_plugin.starup_actions.data.PipelineOptions;
import jw.fluent_plugin.FluentPlugin;
import jw.fluent_api.validator.api.ValidatorProfile;

public class ValidatorAction  implements PluginPipeline {
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