package jw.spigot_fluent_api.fluent_particle.implementation.builder.builders_pipeline;

import jw.spigot_fluent_api.fluent_particle.implementation.OnParticleEvent;
import jw.spigot_fluent_api.fluent_particle.implementation.ParticleSettings;
import jw.spigot_fluent_api.desing_patterns.builder.NextStep;
import jw.spigot_fluent_api.fluent_particle.implementation.builder.ParticleBuilderBase;

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
