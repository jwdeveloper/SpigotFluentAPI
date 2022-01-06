package jw.spigot_fluent_api.utilites.math.collistions;

import jw.spigot_fluent_api.fluent_tasks.FluentTaskTimer;

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

    public HitBox(Location a, Location b) {
        max = MathUtility.max(a, b);
        min = MathUtility.min(a, b);
    }

    public void setOrigin(Location location) {
        this.origin = location;
    }

    public void showHitBox() {

        float size = 0.1F;
        Color color = Color.fromRGB(0, 0, 255);
        Particle.DustOptions options = new Particle.DustOptions(color, size);
        new FluentTaskTimer(1, (time, taskTimer) ->
        {
            min.getWorld().spawnParticle(Particle.REDSTONE, min, 1,options);
            max.getWorld().spawnParticle(Particle.REDSTONE, max, 1,options);
        }).run();

        //args from command invoke event
        String[] args = new String[10];

        int[] numbers = {1,2,3,4,5,6,7,8,9,10};
        String playerName ="";
        StringBuilder stringBuilder = new StringBuilder();
        if(args.length >1)
        {

            for(int i=0;i<args.length;i++)
            {
                if(i ==0)
                {
                    playerName = args[i];
                }
                else
                {
                    stringBuilder.append(" ").append(args[i]);
                }
            }
        }
        String message = stringBuilder.toString();
    }
    public boolean isCollider(Location rayOrigin, float length) {
        return rayBoxIntersect(rayOrigin.toVector(),
                rayOrigin.getDirection(),
                min.toVector(),
                max.toVector()) > 0;
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