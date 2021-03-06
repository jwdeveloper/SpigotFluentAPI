package jw.spigot_fluent_api.fluent_particle.implementation.builder;
import jw.spigot_fluent_api.fluent_particle.implementation.ParticleSettings;
import jw.spigot_fluent_api.fluent_particle.implementation.builder.sub_builders.TimeBuilder;

public class FluentParticle
{
    public static TimeBuilder create()
    {
        return new TimeBuilder(new ParticleSettings());
    }
}
