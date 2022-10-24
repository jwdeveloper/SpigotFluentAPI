package jw.fluent_api.spigot.particles.implementation.builder.builders_pipeline;

import jw.fluent_api.spigot.particles.implementation.ParticleSettings;
import jw.fluent_api.desing_patterns.builder.NextStep;
import jw.fluent_api.spigot.particles.implementation.builder.ParticleBuilderBase;

public class TimeBuilder extends ParticleBuilderBase implements NextStep<DetailBuilder>
{
    public TimeBuilder(final ParticleSettings particleSettings)
    {
       super(particleSettings);
        particleSettings.setTriggerEveryTicks(20);
        particleSettings.setStopAfterTicks(Integer.MAX_VALUE);
        particleSettings.setStartAfterTicks(Integer.MIN_VALUE);
    }

    public TimeBuilder triggerEveryTicks(int ticks)
    {
        particleSettings.setTriggerEveryTicks(ticks);
        return this;
    }

    public TimeBuilder triggerEverySeconds(int seconds)
    {
        particleSettings.setTriggerEveryTicks(seconds*20);
        return this;
    }

    public TimeBuilder startAfterTicks(int ticks)
    {
        particleSettings.setStartAfterTicks(ticks);
        return this;
    }

    public TimeBuilder startAfterSeconds(int seconds)
    {
        particleSettings.setStartAfterTicks(seconds*20);
        return this;
    }
    public TimeBuilder startAfterMinutes(int minute)
    {
        particleSettings.setStartAfterTicks(minute*60*20);
        return this;
    }
    public TimeBuilder stopAfterTicks(int ticks)
    {
        particleSettings.setStopAfterTicks(ticks);
        return this;
    }
    public TimeBuilder stopAfterSeconds(int seconds)
    {
        particleSettings.setStopAfterTicks(seconds*20);
        return this;
    }
    public TimeBuilder stopAfterMinutes(int minute)
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
