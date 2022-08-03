package jw.spigot_fluent_api.fluent_mapper.implementation.builder;

import jw.spigot_fluent_api.desing_patterns.builder.FinishBuilder;
import jw.spigot_fluent_api.fluent_mapper.api.Mapper;
import jw.spigot_fluent_api.fluent_mapper.api.MapperProfile;

public class MappingProfileBuilder implements FinishBuilder<MapperProfile> {
    private final Mapper mapper;
    private MapperProfile mappingProfile;

    public MappingProfileBuilder(Mapper mapper) {
        this.mapper = mapper;
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
        //if (profile != null)
           // mapper.registerMappingProfile(mappingProfile);
    }
}
