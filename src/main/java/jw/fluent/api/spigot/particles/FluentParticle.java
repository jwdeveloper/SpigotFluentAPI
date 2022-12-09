package jw.fluent.api.spigot.particles;
import jw.fluent.api.spigot.particles.implementation.ParticleSettings;
import jw.fluent.api.spigot.particles.implementation.builder.builders_pipeline.FluentParticlebuilder;

public class FluentParticle
{
    //Generator https://cloudwolfyt.github.io/pages/gens/particle-plots.html
    public FluentParticlebuilder create()
    {
        return new FluentParticlebuilder(new ParticleSettings());
    }
}
