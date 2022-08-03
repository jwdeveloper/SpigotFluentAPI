package jw.spigot_fluent_api.fluent_particle.implementation.builder.builders_pipeline;

import jw.spigot_fluent_api.fluent_particle.implementation.ParticleDisplayMode;
import jw.spigot_fluent_api.fluent_particle.implementation.ParticleSettings;
import jw.spigot_fluent_api.desing_patterns.builder.NextStep;
import jw.spigot_fluent_api.fluent_particle.implementation.builder.ParticleBuilderBase;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;

public class DetailBuilder extends ParticleBuilderBase implements NextStep<EventBuilder>
{

    public DetailBuilder(ParticleSettings particleSettings)
    {
      super(particleSettings);
      particleSettings.setParticleCount(1);
    }

    public DetailBuilder withParticleCount(int number)
    {
        particleSettings.setParticleCount(number);
        return this;
    }

    public DetailBuilder withEntityTracking(Entity entity)
    {
        particleSettings.setEntityToTrack(entity);
        return this;
    }

    public DetailBuilder withFixedLocation(Location location)
    {
        particleSettings.setLocation(location);
        return this;
    }

    public DetailBuilder withParticle(Particle particle)
    {
        particleSettings.setParticle(particle);
        return this;
    }

    public DetailBuilder withDisplayMode(ParticleDisplayMode displayMode)
    {
        particleSettings.setParticleDisplayMode(displayMode);
        return this;
    }

    public DetailBuilder withColor(Color color)
    {
        particleSettings.setColor(color);
        return this;
    }

    @Override
    public EventBuilder nextStep() {
        return new EventBuilder(particleSettings);
    }
}
