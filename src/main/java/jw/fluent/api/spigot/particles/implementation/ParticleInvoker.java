package jw.fluent.api.spigot.particles.implementation;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticleInvoker
{
    public void spawnParticle(ParticleEvent particleEvent)
    {
        particleEvent.getOriginLocation().getWorld().spawnParticle(particleEvent.getParticle(),particleEvent.originLocation,particleEvent.amount);
    }

    public void spawnParticle(Location location, Particle particle,int amount)
    {
        location.getWorld().spawnParticle(particle,location,amount);
    }


    public void spawnParticle(Location location, Particle particle)
    {
        location.getWorld().spawnParticle(particle,location,1);
    }

    public void spawnParticle(Location location, Particle particle, int amount, Particle.DustOptions dustOptions)
    {
        location.getWorld().spawnParticle(particle,location,amount, dustOptions);
    }

    public void spawnDustParticle(ParticleEvent particleEvent)
    {

    }

    public void spawnDustParticle(Location location, Particle particle, Color color)
    {

    }
}
