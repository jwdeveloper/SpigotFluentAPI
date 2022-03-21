package jw.spigot_fluent_api.fluent_particle.implementation;

import lombok.Getter;
import org.bukkit.*;

import java.time.LocalDate;

@Getter
public class ParticleEvent {
    Color particleColor;
    Location originLocation;
    Particle particle;
    int index;
    int time;
    int amount;


    public void setParticleColor(Color color) {
         this.particleColor = color;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public void setParticle(Particle particle) {
      this.particle = particle;
    }

    public World getWorld() {
        return originLocation.getWorld();
    }

    public Location getLocation() {
        return originLocation;
    }
}
