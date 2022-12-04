package jw.fluent.api.spigot.particles;
import jw.fluent.api.spigot.particles.implementation.ParticleSettings;
import jw.fluent.api.spigot.particles.implementation.builder.builders_pipeline.TimeBuilder;

public class FluentParticle
{
    public TimeBuilder create()
    {
        return new TimeBuilder(new ParticleSettings());
    }
}
