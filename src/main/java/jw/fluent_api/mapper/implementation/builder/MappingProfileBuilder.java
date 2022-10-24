package jw.fluent_api.mapper.implementation.builder;

import jw.fluent_api.desing_patterns.builder.FinishBuilder;
import jw.fluent_api.mapper.api.Mapper;
import jw.fluent_api.mapper.api.MapperProfile;

public class MappingProfileBuilder implements FinishBuilder<MapperProfile> {
    private MapperProfile mappingProfile;

    public MappingProfileBuilder()
    {

    }

    public <Input, Output> MappingProfileBuilder setMapping(MapperProfile<Input, Output> mappingProfile) {
        this.mappingProfile = mappingProfile;
        return this;
    }

    public MapperProfile build() {
        return mappingProfile;
    }

    public void register() {
        var profile = build();

        //TODO this one
        //if (profile != null)
           // mapper.registerMappingProfile(mappingProfile);
    }
}
