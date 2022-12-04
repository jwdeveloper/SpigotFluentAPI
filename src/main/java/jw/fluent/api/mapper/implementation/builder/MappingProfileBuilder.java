package jw.fluent.api.mapper.implementation.builder;

import jw.fluent.api.mapper.api.MapperProfile;
import jw.fluent.api.desing_patterns.builder.FinishBuilder;

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
