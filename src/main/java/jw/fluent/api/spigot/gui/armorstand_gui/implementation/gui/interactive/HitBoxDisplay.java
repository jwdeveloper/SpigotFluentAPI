package jw.fluent.api.spigot.gui.armorstand_gui.implementation.gui.interactive;

import jw.fluent.api.spigot.particles.api.enums.ParticleDisplayMode;
import jw.fluent.api.spigot.particles.implementation.SimpleParticle;
import jw.fluent.api.utilites.math.InteractiveHitBox;
import jw.fluent.api.utilites.math.MathUtility;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class HitBoxDisplay {

    private InteractiveHitBox hitBox;
    private SimpleParticle task;

    private  Particle.DustOptions lineOptions;

    private Color lineColor;

    private List<Location> lines;

    private float particleSize = 0.1f;

    public HitBoxDisplay(InteractiveHitBox hitBox) {
        this.hitBox = hitBox;
        lineColor = Color.fromRGB(MathUtility.getRandom(0, 255), MathUtility.getRandom(0, 255), MathUtility.getRandom(0, 255));
        lineOptions = new Particle.DustOptions(lineColor, particleSize);
        task = getHitboxDisplay();
    }

    public void color(Color color)
    {
        this.lineColor = color;
        lineOptions = new Particle.DustOptions(color, particleSize);
    }

    public void show(float particleSize) {
        lineOptions = new Particle.DustOptions(lineColor, particleSize);
        lines = createBox();
        task.start();
    }

    public void hide() {
        task.stop();
    }


    private SimpleParticle getHitboxDisplay() {
        float size = 2F;
        Color color = Color.fromRGB(92, 225, 230);
        Color color2 = Color.fromRGB(255, 0, 0);
        Particle.DustOptions optionsMin = new Particle.DustOptions(color, size);
        Particle.DustOptions optionsMax = new Particle.DustOptions(color2, size);
        var mini = hitBox.getOrigin().clone().add(hitBox.getMin());
        var maxi = hitBox.getOrigin().clone().add(hitBox.getMin());
        return FluentApi.particles()
                .startAfterTicks(1)
                .triggerEveryTicks(5)
                .nextStep()
                .withParticleCount(2)
                .withFixedLocation(hitBox.getOrigin())
                .withDisplayMode(ParticleDisplayMode.ALL_AT_ONCE)
                .nextStep()
                .onParticle((particle, particleInvoker) ->
                {
                    particleInvoker.spawnParticle(maxi, Particle.REDSTONE, 1, optionsMin);
                    for (var line : lines) {
                        particleInvoker.spawnParticle(line, Particle.REDSTONE, 1, lineOptions);
                    }
                    particleInvoker.spawnParticle(mini, Particle.REDSTONE, 1, optionsMax);
                })
                .nextStep()
                .build();
    }


    public List<Location> createBox() {
        var min = hitBox.getMin();
        var max = hitBox.getMax();
        var wordl = hitBox.getOrigin().getWorld();
        var bottom1 = new Location(wordl, min.getX(), min.getY(), min.getZ());
        var bottom2 = new Location(wordl, max.getX(), min.getY(), min.getZ());
        var bottom3 = new Location(wordl, max.getX(), min.getY(), max.getZ());
        var bottom4 = new Location(wordl, min.getX(), min.getY(), max.getZ());

        var top1 = new Location(wordl, min.getX(), max.getY(), min.getZ());
        var top2 = new Location(wordl, max.getX(), max.getY(), min.getZ());
        var top3 = new Location(wordl, max.getX(), max.getY(), max.getZ());
        var top4 = new Location(wordl, min.getX(), max.getY(), max.getZ());


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
}
