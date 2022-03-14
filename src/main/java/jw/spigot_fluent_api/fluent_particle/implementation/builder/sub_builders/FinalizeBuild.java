package jw.spigot_fluent_api.fluent_particle.implementation.builder.sub_builders;

import jw.spigot_fluent_api.fluent_particle.implementation.SimpleParticle;
import jw.spigot_fluent_api.fluent_particle.implementation.ParticleSettings;
import jw.spigot_fluent_api.desing_patterns.builder.FinishBuilder;

public class FinalizeBuild implements FinishBuilder<SimpleParticle> {

    private final ParticleSettings particleSettings;

    public FinalizeBuild(final ParticleSettings particleSettings) {
        this.particleSettings = particleSettings;
    }

    public SimpleParticle build() {
        return new SimpleParticle(particleSettings);
    }

    public SimpleParticle buildAndStart() {
        var result = build();
        result.start();
        return result;
    }
}
