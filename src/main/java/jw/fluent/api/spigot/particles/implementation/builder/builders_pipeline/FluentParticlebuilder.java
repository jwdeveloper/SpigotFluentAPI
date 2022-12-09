package jw.fluent.api.spigot.particles.implementation.builder.builders_pipeline;

import jw.fluent.api.spigot.particles.implementation.ParticleSettings;
import jw.fluent.api.spigot.particles.implementation.builder.ParticleBuilderBase;
import jw.fluent.api.desing_patterns.builder.NextStep;

public class FluentParticlebuilder extends ParticleBuilderBase implements NextStep<DetailBuilder>
{
    public FluentParticlebuilder(final ParticleSettings particleSettings)
    {
       super(particleSettings);
        particleSettings.setTriggerEveryTicks(20);
        particleSettings.setStopAfterTicks(Integer.MAX_VALUE);
        particleSettings.setStartAfterTicks(Integer.MIN_VALUE);
    }

    public FluentParticlebuilder triggerEveryTicks(int ticks)
    {
        particleSettings.setTriggerEveryTicks(ticks);
        return this;
    }

    public FluentParticlebuilder triggerEverySeconds(int seconds)
    {
        particleSettings.setTriggerEveryTicks(seconds*20);
        return this;
    }

    public FluentParticlebuilder startAfterTicks(int ticks)
    {
        particleSettings.setStartAfterTicks(ticks);
        return this;
    }

    public FluentParticlebuilder startAfterSeconds(int seconds)
    {
        particleSettings.setStartAfterTicks(seconds*20);
        return this;
    }
    public FluentParticlebuilder startAfterMinutes(int minute)
    {
        particleSettings.setStartAfterTicks(minute*60*20);
        return this;
    }
    public FluentParticlebuilder stopAfterTicks(int ticks)
    {
        particleSettings.setStopAfterTicks(ticks);
        return this;
    }
    public FluentParticlebuilder stopAfterSeconds(int seconds)
    {
        particleSettings.setStopAfterTicks(seconds*20);
        return this;
    }
    public FluentParticlebuilder stopAfterMinutes(int minute)
    {
        particleSettings.setStopAfterTicks(minute*60*20);
        return this;
    }

    @Override
    public DetailBuilder nextStep()
    {
        return new DetailBuilder(particleSettings);
    }
}
