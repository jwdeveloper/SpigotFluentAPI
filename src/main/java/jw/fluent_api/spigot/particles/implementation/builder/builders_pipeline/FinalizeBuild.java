package jw.fluent_api.spigot.particles.implementation.builder.builders_pipeline;

import jw.fluent_api.spigot.particles.implementation.SimpleParticle;
import jw.fluent_api.spigot.particles.implementation.ParticleSettings;
import jw.fluent_api.desing_patterns.builder.FinishBuilder;

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
