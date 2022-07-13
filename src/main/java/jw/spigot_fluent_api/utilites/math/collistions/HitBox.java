package jw.spigot_fluent_api.utilites.math.collistions;

import jw.spigot_fluent_api.fluent_particle.implementation.ParticleDisplayMode;
import jw.spigot_fluent_api.fluent_particle.implementation.SimpleParticle;
import jw.spigot_fluent_api.fluent_particle.FluentParticle;

import jw.spigot_fluent_api.utilites.math.MathUtility;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;


public class HitBox {
    private Location origin;
    private final Location min;
    private final Location max;
    private final double[] result = new double[10];
    private SimpleParticle display;

    public HitBox(Location a, Location b) {
        max = MathUtility.max(a, b);
        min = MathUtility.min(a, b);
    }

    public boolean isCollider(Location rayOrigin, float length) {
        return rayBoxIntersect(rayOrigin.toVector(),
                rayOrigin.getDirection(),
                min.toVector(),
                max.toVector()) > 0;
    }

    public void setOrigin(Location location) {
        this.origin = location;
    }

    public void showHitBox() {
        if(display == null)
            display = getHitboxDisplay();
        display.start();
    }

    public void hideHitbox() {

        if(display == null)
           return;
        display.stop();
    }

    private SimpleParticle getHitboxDisplay()
    {
        float size = 0.6F;
        Color color = Color.fromRGB(255, 0, 0);
        Particle.DustOptions options = new Particle.DustOptions(color, size);
       return FluentParticle.create()
                .startAfterTicks(1)
                .triggerEveryTicks(10)
                .nextStep()
                .withColor(Color.RED)
                .withParticleCount(2)
                .withFixedLocation(min)
                .withDisplayMode(ParticleDisplayMode.ALL_AT_ONCE)
                .nextStep()
                .onParticle((particle, particleInvoker) ->
                {
                    particleInvoker.spawnParticle(max,Particle.REDSTONE,1,options);
                    particleInvoker.spawnParticle(min,Particle.REDSTONE,1,options);
                })
                .nextStep()
                .build();
    }


    private double rayBoxIntersect(Vector position, Vector direction, Vector vmin, Vector vmax) {
        result[1] = (vmin.getX() - position.getX()) / direction.getX();
        result[2] = (vmax.getX() - position.getX()) / direction.getX();
        result[3] = (vmin.getY() - position.getY()) / direction.getY();
        result[4] = (vmax.getY() - position.getY()) / direction.getY();
        result[5] = (vmin.getZ() - position.getZ()) / direction.getZ();
        result[6] = (vmax.getZ() - position.getZ()) / direction.getZ();
        result[7] = max(max(min(result[1], result[2]), min(result[3], result[4])), min(result[5], result[6]));
        result[8] = min(min(max(result[1], result[2]), max(result[3], result[4])), max(result[5], result[6]));
        result[9] = (result[8] < 0 || result[7] > result[8]) ? 0 : result[7];
        return result[9];
    }

    private double max(double a, double b) {
        return Math.max(a, b);
    }

    private double min(double a, double b) {
        return Math.min(a, b);
    }
}