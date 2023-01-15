/*
 * MIT License
 *
 * Copyright (c)  2023. jwdeveloper
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package jw.fluent.api.temp;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Map;

public class Bullet {

  private static final double RAY_CAST_STEP_DISTANCE = .05;
  private static final double RAY_CAST_RADIUS = .5;

  public static BulletResult shoot(
   Player shooter, Location from, double maxTravelDistance,
    Map<Player, SegmentedHitbox> hitboxes
  ) {
    World world = from.getWorld();

    if (world == null)
      throw new IllegalStateException("Location's world cannot be null");

    Vector path = from.getDirection().normalize().multiply(maxTravelDistance);
    double stepSize = RAY_CAST_STEP_DISTANCE / path.length();

    Location current = from.clone();
    for (double multiplier = 0; multiplier <= 1; multiplier += stepSize) {
      double x = path.getX() * multiplier, y = path.getY() * multiplier, z = path.getZ() * multiplier;
      current.add(x, y, z);

      Collection<Entity> nearbyEntities = world.getNearbyEntities(current, RAY_CAST_RADIUS, RAY_CAST_RADIUS, RAY_CAST_RADIUS);

      for (Entity entity : nearbyEntities) {
        if (!(entity instanceof Player))
          continue;

        Player player = (Player) entity;

        if (player == shooter)
          continue;

        SegmentedHitbox hitbox = hitboxes.get(player);

        if (hitbox == null)
          continue;

        EHitboxSegment segment;
        if ((segment = hitbox.isInside(current.getX(), current.getY(), current.getZ())) == null)
          continue;

        return new BulletResult(player, segment, new SERefVector(from.toVector(), current.toVector()));
      }

      current.subtract(x, y, z);
    }

    return new BulletResult(null, null, new SERefVector(from.toVector(), path));
  }
}
