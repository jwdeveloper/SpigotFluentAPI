package jw.fluent.api.spigot.particles.implementation.builder.builders_pipeline;

import jw.fluent.api.spigot.particles.implementation.OnParticleEvent;
import jw.fluent.api.spigot.particles.implementation.ParticleSettings;
import jw.fluent.api.desing_patterns.builder.NextStep;
import jw.fluent.api.spigot.particles.implementation.builder.ParticleBuilderBase;

public class EventBuilder  extends ParticleBuilderBase implements NextStep<FinalizeBuild>
{

    public EventBuilder(final ParticleSettings particleSettings)
    {
        super(particleSettings);
    }

    public EventBuilder onStart(OnParticleEvent particleEvent)
    {
        particleSettings.setOnStart(particleEvent);
        return this;
    }

    public EventBuilder onParticle(OnParticleEvent particleEvent)
    {
        particleSettings.setOnParticleEvent(particleEvent);
        return this;
    }

    public EventBuilder onStop(OnParticleEvent particleEvent)
    {
        particleSettings.setOnStop(particleEvent);
        return this;
    }

    @Override
    public FinalizeBuild nextStep()
    {
        return new FinalizeBuild(particleSettings);
    }
}
