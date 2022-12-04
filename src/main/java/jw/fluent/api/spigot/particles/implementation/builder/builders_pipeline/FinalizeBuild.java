package jw.fluent.api.spigot.particles.implementation.builder.builders_pipeline;

import jw.fluent.api.spigot.particles.implementation.SimpleParticle;
import jw.fluent.api.spigot.particles.implementation.ParticleSettings;
import jw.fluent.api.desing_patterns.builder.FinishBuilder;

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
