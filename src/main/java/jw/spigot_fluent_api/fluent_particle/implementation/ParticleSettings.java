package jw.spigot_fluent_api.fluent_particle.implementation;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;

@Getter
@Setter
public class ParticleSettings {
    private int startAfterTicks;
    private int stopAfterTicks;
    private int triggerEveryTicks;
    private int particleCount;
    private Entity entityToTrack;
    private Location location;
    private Color color;
    private Particle particle;
    private ParticleDisplayMode particleDisplayMode = ParticleDisplayMode.ALL_AT_THE_TIME;
    private OnParticleEvent onParticleEvent = (a, b) -> {
    };
    private OnParticleEvent onStop = (a, b) -> {
    };
    private OnParticleEvent onStart = (a, b) -> {
    };

    public Location getLocation() {
        if (entityToTrack != null) {
            return entityToTrack.getLocation();
        }
        return location;
    }
}
