package jw.spigot_fluent_api.fluent_particle.implementation.builder.sub_builders;

import jw.spigot_fluent_api.fluent_particle.implementation.ParticleEffect;
import jw.spigot_fluent_api.fluent_particle.implementation.ParticleSettings;
import jw.spigot_fluent_api.desing_patterns.builder.FinishBuilder;

public class FinalizeBuild implements FinishBuilder<ParticleEffect> {

    private final ParticleSettings particleSettings;

    public FinalizeBuild(final ParticleSettings particleSettings) {
        this.particleSettings = particleSettings;
    }

    public ParticleEffect build() {
        return new ParticleEffect(particleSettings);
    }

    public ParticleEffect buildAndStart() {
        var result = build();
        result.start();
        return result;
    }
}
