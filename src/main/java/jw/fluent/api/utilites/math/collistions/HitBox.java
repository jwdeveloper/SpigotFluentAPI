package jw.fluent.api.utilites.math.collistions;

import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import jw.fluent.api.spigot.particles.api.enums.ParticleDisplayMode;
import jw.fluent.api.spigot.particles.implementation.SimpleParticle;

import jw.fluent.api.utilites.math.MathUtility;
import jw.fluent.plugin.implementation.FluentApi;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;


public class HitBox {
    @Getter
    private Location min;
    @Getter
    private Location max;
    private final double[] result = new double[10];
    private SimpleParticle display;

    public HitBox(Location a, Location b) {
        update(a, b);
    }

    public void update(Location a, Location b) {
        var v1 = Vector.getMaximum(a.toVector(), b.toVector());
        var v2 = Vector.getMinimum(a.toVector(), b.toVector());
        max = new Location(a.getWorld(), v1.getX(), v1.getY(), v1.getZ());
        min = new Location(a.getWorld(), v2.getX(), v2.getY(), v2.getZ());
    }

    public boolean isCollider(Location rayOrigin, float length) {
        return rayBoxIntersect(rayOrigin.toVector(),
                rayOrigin.getDirection(),
                min.toVector(),
                max.toVector()) > 0;
    }

    public void show() {
        if (display == null)
            display = getHitboxDisplay();
        display.start();
    }


    public void show(boolean redraw) {
        if (display != null) {
            display.stop();
        }
        display = getHitboxDisplay();
        display.start();
    }

    @Override
    public String toString() {
        return FluentMessage.message()
                .inBrackets("Min", ChatColor.BLUE).newLine()
                .text("- x ").text(min.getX()).newLine()
                .text("- y").text(min.getY()).newLine()
                .text("- z ").text(min.getZ()).newLine()
                .inBrackets("Max", ChatColor.RED).newLine()
                .text("- x ").text(max.getX()).newLine()
                .text("- y").text(max.getY()).newLine()
                .text("- z ").text(max.getZ()).newLine().toString();

    }

    public void hide() {

        if (display == null)
            return;
        display.stop();
    }

    private SimpleParticle getHitboxDisplay() {
        float size = 0.2F;
        Color color = Color.fromRGB(92, 225, 230);
        Color color2 = Color.fromRGB(255, 0, 0);
        Color color3 = Color.fromRGB(MathUtility.getRandom(0, 255), MathUtility.getRandom(0, 255), MathUtility.getRandom(0, 255));
        Particle.DustOptions optionsMin = new Particle.DustOptions(color, size);
        Particle.DustOptions optionsMax = new Particle.DustOptions(color2, size);
        Particle.DustOptions optionsLine = new Particle.DustOptions(color3, 0.1f);

        List lines = new ArrayList<Location>();
        var direcition = new Vector(
                max.getX() - min.getX(),
                max.getY() - min.getY(),
                max.getZ() - min.getZ());
        lines.add(min);
        for (var t = 0.0f; t < 1.0; t += 0.1f) {

            var x = min.getX() + direcition.getX() * t;
            var y = min.getY() + direcition.getY() * t;
            var z = min.getZ() + direcition.getZ() * t;
            lines.add(new Location(min.getWorld(), x, y, z));
        }
        lines.add(max);


        lines = createBox();


        List<Location> finalLines = lines;
        return FluentApi.particles()
                .startAfterTicks(1)
                .triggerEveryTicks(5)
                .nextStep()
                .withParticleCount(2)
                .withFixedLocation(min)
                .withDisplayMode(ParticleDisplayMode.ALL_AT_ONCE)
                .nextStep()
                .onParticle((particle, particleInvoker) ->
                {
                    particleInvoker.spawnParticle(max, Particle.REDSTONE, 1, optionsMin);
                    for (var line : finalLines) {
                        particleInvoker.spawnParticle(line, Particle.REDSTONE, 1, optionsLine);
                    }
                    particleInvoker.spawnParticle(min, Particle.REDSTONE, 1, optionsMax);
                })
                .nextStep()
                .build();
    }


    public List<Location> createBox() {
        var bottom1 = min;
        var bottom2 = new Location(min.getWorld(), max.getX(), min.getY(), min.getZ());
        var bottom3 = new Location(min.getWorld(), max.getX(), min.getY(), max.getZ());
        var bottom4 = new Location(min.getWorld(), min.getX(), min.getY(), max.getZ());

        var top1 = new Location(min.getWorld(), min.getX(), max.getY(), min.getZ());
        var top2 = new Location(min.getWorld(), max.getX(), max.getY(), min.getZ());
        var top3 = new Location(min.getWorld(), max.getX(), max.getY(), max.getZ());
        var top4 = new Location(min.getWorld(), min.getX(), max.getY(), max.getZ());


        List<Location> result = new ArrayList<>();
        var points = new ArrayList<Location>();
        points.add(bottom1);
        points.add(bottom2);
        points.add(bottom3);
        points.add(bottom4);
        points.add(bottom1);

        points.add(top1);
        points.add(top2);
        points.add(top3);
        points.add(top4);
        points.add(top1);


        result.addAll(getLine(bottom2, top2));
        result.addAll(getLine(bottom3, top3));
        result.addAll(getLine(bottom4, top4));
        for (var i = 1; i < points.size(); i++) {
            result.addAll(getLine(points.get(i - 1), points.get(i)));
        }
        return result;
    }


    public List<Location> getLine(Location a, Location b) {
        var lines = new ArrayList<Location>();
        var direcition = new Vector(
                b.getX() - a.getX(),
                b.getY() - a.getY(),
                b.getZ() - a.getZ());
        for (var t = 0.0f; t < 1.0; t += 0.1f) {

            var x = a.getX() + direcition.getX() * t;
            var y = a.getY() + direcition.getY() * t;
            var z = a.getZ() + direcition.getZ() * t;
            lines.add(new Location(a.getWorld(), x, y, z));
        }
        return lines;
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