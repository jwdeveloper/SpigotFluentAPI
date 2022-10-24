package jw.fluent_api.spigot.particles;
import jw.fluent_api.spigot.particles.implementation.ParticleSettings;
import jw.fluent_api.spigot.particles.implementation.builder.builders_pipeline.TimeBuilder;

public class FluentParticle
{
    public TimeBuilder create()
    {
        return new TimeBuilder(new ParticleSettings());
    }
}
