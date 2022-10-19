package jw.fluent_api.utilites.math;


import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.World;
import java.util.List;

public class MathUtility
{
    public static float lerp(float a, float b, float f)
    {
        return a + f * (b - a);
    }
    public static double getPersent(double max, double current)
    {
        if(current > max)
            current = max;
        if(current<=0)
            current =1 ;
        return current/max;
    }
    public static int getRandom(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static List<Location> getHollowCube(Location min, Location max, double accuracy) {
        List<Location> result = Lists.newArrayList();
        World world = min.getWorld();
        double minX = min.getBlockX();
        double minY = min.getBlockY();
        double minZ = min.getBlockZ();
        double maxX = max.getBlockX();
        double maxY = max.getBlockY();
        double maxZ = max.getBlockZ();

        for (double x = minX; x <= maxX; x += accuracy) {
            for (double y = minY; y <= maxY; y += accuracy) {
                for (double z = minZ; z <= maxZ; z += accuracy) {
                    int components = 0;
                    if (x == minX || x == maxX) components++;
                    if (y == minY || y == maxY) components++;
                    if (z == minZ || z == maxZ) components++;
                    if (components >= 2) {
                        result.add(new Location(world, x, y, z));
                    }
                }
            }
        }
        return result;
    }

    public static Location max(Location a,Location b)
    {
        if(a.getX() > b.getX() &&
                a.getY() > b.getY() &&
                a.getZ() > b.getZ())
        {
            return a;
        }
        return b;
    }
    public static Location min(Location a,Location b)
    {
        if(a.getX() < b.getX() &&
                a.getY() < b.getY() &&
                a.getZ() < b.getZ())
        {
            return a;
        }
        return b;
    }

    public static double yawToRotation(float yaw)
    {
        double rotation = (yaw - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
        return rotation;
    }
}
