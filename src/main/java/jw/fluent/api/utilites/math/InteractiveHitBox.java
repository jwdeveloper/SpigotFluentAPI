package jw.fluent.api.utilites.math;

import jw.fluent.api.spigot.particles.api.enums.ParticleDisplayMode;
import jw.fluent.api.spigot.particles.implementation.SimpleParticle;
import jw.fluent.api.utilites.math.MathUtility;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class InteractiveHitBox {
    @Getter
    private Vector min;
    @Getter
    private Vector max;

    @Getter
    private Location origin;
    private Location root;

    private Vector box;

    private final double[] result = new double[10];

    public InteractiveHitBox(Location origin, Vector box) {
        this.origin = origin.clone();
        this.root = origin.clone();
        update(box);
    }

    public void updateOrigin(Location origin)
    {
        this.root = origin.clone();
        this.origin = origin.clone();
        update(box);
    }

    public void update(Vector box) {
        this.box = box;
        min = new Vector(-box.getX(), -box.getY(), -box.getZ());
        max = new Vector(box.getX(), box.getY(), box.getZ());

        origin = root.clone();
        min = origin.clone().toVector().add(min);
        max = origin.clone().toVector().add(max);

        min = Vector.getMinimum(min, max);
        max = Vector.getMaximum(min, max);
    }

    public boolean isCollider(Location location, float length) {
        return rayBoxIntersect(location.toVector(),
                location.getDirection(),
                min,
                max) > 0;
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