package jw.fluent_api.minecraft.particles;
import jw.fluent_api.minecraft.particles.implementation.ParticleSettings;
import jw.fluent_api.minecraft.particles.implementation.builder.builders_pipeline.TimeBuilder;

public class FluentParticle
{
    public static TimeBuilder create()
    {
        return new TimeBuilder(new ParticleSettings());
    }
}
