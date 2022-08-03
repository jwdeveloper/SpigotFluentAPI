package jw.spigot_fluent_api.fluent_particle;
import jw.spigot_fluent_api.fluent_particle.implementation.ParticleSettings;
import jw.spigot_fluent_api.fluent_particle.implementation.builder.builders_pipeline.TimeBuilder;

public class FluentParticle
{
    public static TimeBuilder create()
    {
        return new TimeBuilder(new ParticleSettings());
    }
}
