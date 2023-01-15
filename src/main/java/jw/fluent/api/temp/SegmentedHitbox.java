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

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;


/*
  https://minecraft.fandom.com/wiki/Player

  The whole player hitbox is 1.8 blocks high and 0.6 blocks wide
  The body seems to be 0.2 blocks in thickness, except for the head

  https://www.planetminecraft.com/blog/how-to-make-a-minecraft-player-statue-and-statue-dimensions-nice-and-simple/

  Dimensions named relative to front-view
  Segment  W  H  D
  Head     8  8  8
  Torso    8 12  4
  Arm      4 12  4
  Leg      4 12  4

  Outline 16 32  8

  If 32 pixels are 1.8 blocks, then there are 0.05625 blocks per pixel
 */

public class SegmentedHitbox {

  private static final double DEBUG_PARTICLE_DISTANCE;
  private static final float DEBUG_PARTICLE_SIZE;
  private static final long DEBUG_PERIOD_TICKS;

  private static final double BLOCKS_PER_PIXEL;
  private static final double HEAD_WHD, TORSO_W, ARM_LEG_TORSO_D, ARM_LEG_W, ARM_LEG_TORSO_H;
  private static final double PLAYER_SNEAKING_DISTANCE;

  static {
    DEBUG_PARTICLE_DISTANCE = .05;
    DEBUG_PARTICLE_SIZE = .25F;
    DEBUG_PERIOD_TICKS = 2;

    BLOCKS_PER_PIXEL = 0.05625;
    HEAD_WHD = BLOCKS_PER_PIXEL * 8;
    TORSO_W = BLOCKS_PER_PIXEL * 8;
    ARM_LEG_TORSO_D = BLOCKS_PER_PIXEL * 4;
    ARM_LEG_W = BLOCKS_PER_PIXEL * 4;
    ARM_LEG_TORSO_H = BLOCKS_PER_PIXEL * 12;
    PLAYER_SNEAKING_DISTANCE = 0.3;
  }

  private final Player player;
  private final Cuboid head, torso, leftArm, rightArm, leftLeg, rightLeg;

  public SegmentedHitbox(Player player) {
    double yOff = 0;

    this.rightLeg = new Cuboid(
      new Vector(ARM_LEG_W, ARM_LEG_TORSO_H, ARM_LEG_TORSO_D),
      new Vector(-ARM_LEG_W, yOff, -ARM_LEG_TORSO_D / 2),
      null,
      Color.YELLOW
    );

    this.leftLeg = new Cuboid(
      new Vector(ARM_LEG_W, ARM_LEG_TORSO_H, ARM_LEG_TORSO_D),
      new Vector(0, yOff, -ARM_LEG_TORSO_D / 2),
      null,
      Color.PURPLE
    );

    yOff += ARM_LEG_TORSO_H;

    this.rightArm = new Cuboid(
      new Vector(ARM_LEG_W, ARM_LEG_TORSO_H, ARM_LEG_TORSO_D),
      new Vector(-(ARM_LEG_W + TORSO_W / 2), yOff, -ARM_LEG_TORSO_D / 2),
      null,
      Color.AQUA
    );

    this.leftArm = new Cuboid(
      new Vector(ARM_LEG_W, ARM_LEG_TORSO_H, ARM_LEG_TORSO_D),
      new Vector(TORSO_W / 2, yOff, -ARM_LEG_TORSO_D / 2),
      null,
      Color.GREEN
    );

    this.torso = new Cuboid(
      new Vector(TORSO_W, ARM_LEG_TORSO_H, ARM_LEG_TORSO_D),
      new Vector(-TORSO_W / 2, yOff, -ARM_LEG_TORSO_D / 2),
      null,
      Color.ORANGE
    );

    yOff += ARM_LEG_TORSO_H;

    this.head = new Cuboid(
      new Vector(HEAD_WHD, HEAD_WHD, HEAD_WHD),
      new Vector(-HEAD_WHD / 2, yOff, -HEAD_WHD / 2),
      new Vector(0, -yOff -HEAD_WHD / 2, 0),
      Color.RED
    );

    this.player = player;

  }

  public void tick(long time) {
    if (time % DEBUG_PERIOD_TICKS == 0) {
      head.draw(this::visualizeVector);
      torso.draw(this::visualizeVector);
      leftArm.draw(this::visualizeVector);
      rightArm.draw(this::visualizeVector);
      leftLeg.draw(this::visualizeVector);
      rightLeg.draw(this::visualizeVector);
    }

    double yawAngle = -1 * player.getLocation().getYaw() / 180.0 * Math.PI;
    double pitchAngle = player.getLocation().getPitch() / 180.0 * Math.PI;

    head.rotateXYZ(pitchAngle, yawAngle, 0);
    torso.rotateXYZ(0, yawAngle, 0);
    leftArm.rotateXYZ(0, yawAngle, 0);
    rightArm.rotateXYZ(0, yawAngle, 0);
    leftLeg.rotateXYZ(0, yawAngle, 0);
    rightLeg.rotateXYZ(0, yawAngle, 0);
  }

  private void visualizeVector(IDrawableVector vector, Color color) {
    double length = vector.getLength();
    double stepSize = DEBUG_PARTICLE_DISTANCE / length;
    Location origin = getOrigin();

    for (double multiplier = 0; multiplier <= 1; multiplier += stepSize) {
      spawnParticle(
        origin.getX() + vector.getOrigin().getX() + (vector.getX() * multiplier),
        origin.getY() + vector.getOrigin().getY() + (vector.getY() * multiplier),
        origin.getZ() + vector.getOrigin().getZ() + (vector.getZ() * multiplier),
        color
      );
    }
  }

  private Location getOrigin() {
    Location pos = player.getLocation();

    if (player.isSneaking())
      pos.add(0, -PLAYER_SNEAKING_DISTANCE, 0);

    return pos;
  }

  private void spawnParticle(double x, double y, double z, Color color) {
    player.getWorld().spawnParticle(Particle.REDSTONE, x, y, z, 1, new Particle.DustOptions(color, DEBUG_PARTICLE_SIZE));
  }

  public EHitboxSegment isInside(double x, double y, double z) {
    Location origin = getOrigin();

    Vector vector = new Vector(
      x - origin.getX(),
      y - origin.getY(),
      z - origin.getZ()
    );

    if (head.isInside(vector))
      return EHitboxSegment.HEAD;

    if (torso.isInside(vector))
      return EHitboxSegment.TORSO;

    if (leftArm.isInside(vector))
      return EHitboxSegment.LEFT_ARM;

    if (rightArm.isInside(vector))
      return EHitboxSegment.RIGHT_ARM;

    if (leftLeg.isInside(vector))
      return EHitboxSegment.LEFT_LEG;

    if (rightLeg.isInside(vector))
      return EHitboxSegment.RIGHT_LEG;

    return null;
  }
}
