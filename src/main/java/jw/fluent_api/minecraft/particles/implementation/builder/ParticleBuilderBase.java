package jw.fluent_api.minecraft.particles.implementation.builder;

import jw.fluent_api.minecraft.particles.implementation.ParticleSettings;

public class ParticleBuilderBase
{
    protected final ParticleSettings particleSettings;

    public ParticleBuilderBase(final ParticleSettings settings)
    {
        this.particleSettings = settings;
    }
}
