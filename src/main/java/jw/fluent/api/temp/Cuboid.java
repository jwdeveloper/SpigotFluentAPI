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

public class Cuboid {

  /*
    0 Front BL  4 Back BL
    1 Front BR  5 Back BR
    2 Front TR  6 Back TR
    3 Front TL  7 Back TL
   */
  private final Vector[] corners;
  private final Vector[] cornersRotated;

  /*
    0 Front BL -> BR  4 Back BL -> BR  8  Front BR -> Back BR
    1 Front BR -> TR  5 Back BR -> TR  9  Front TR -> Back TR
    2 Front TR -> TL  6 Back TR -> TL  10 Front BL -> Back BL
    3 Front TL -> BL  7 Back TL -> BL  11 Front TL -> Back TL
   */
  private final SERefVector[] edges;

  private final Vector dimensions, offsets, rotationShift;
  private final Color color;

  public Cuboid(Vector dimensions, Vector offsets, Vector rotationShift, Color color) {
    this.dimensions = dimensions;
    this.offsets = offsets;
    this.rotationShift = rotationShift;
    this.color = color;

    this.corners = new Vector[8];
    this.cornersRotated = new Vector[8];
    this.edges = new SERefVector[12];
    this.setupCornersAndEdges();
  }

  private void setupCornersAndEdges() {
    Vector FBL = this.cornersRotated[0] = new Vector(offsets.getX(), offsets.getY(), offsets.getZ());
    Vector FBR = this.cornersRotated[1] = new Vector(FBL.getX() + dimensions.getX(), FBL.getY() + 0,                 FBL.getZ() + 0);
    Vector FTR = this.cornersRotated[2] = new Vector(FBL.getX() + dimensions.getX(), FBL.getY() + dimensions.getY(), FBL.getZ() + 0);
    Vector FTL = this.cornersRotated[3] = new Vector(FBL.getX() + 0,                 FBL.getY() + dimensions.getY(), FBL.getZ() + 0);
    Vector BBL = this.cornersRotated[4] = new Vector(FBL.getX() + 0,                 FBL.getY() + 0,                 FBL.getZ() + dimensions.getZ());
    Vector BBR = this.cornersRotated[5] = new Vector(FBL.getX() + dimensions.getX(), FBL.getY() + 0,                 FBL.getZ() + dimensions.getZ());
    Vector BTR = this.cornersRotated[6] = new Vector(FBL.getX() + dimensions.getX(), FBL.getY() + dimensions.getY(), FBL.getZ() + dimensions.getZ());
    Vector BTL = this.cornersRotated[7] = new Vector(FBL.getX() + 0,                 FBL.getY() + dimensions.getY(), FBL.getZ() + dimensions.getZ());

    for (int i = 0; i < corners.length; i++)
      corners[i] = new Vector(cornersRotated[i]);

    edges[0]  = new SERefVector(FBL, FBR);
    edges[1]  = new SERefVector(FBR, FTR);
    edges[2]  = new SERefVector(FTR, FTL);
    edges[3]  = new SERefVector(FTL, FBL);
    edges[4]  = new SERefVector(BBL, BBR);
    edges[5]  = new SERefVector(BBR, BTR);
    edges[6]  = new SERefVector(BTR, BTL);
    edges[7]  = new SERefVector(BTL, BBL);
    edges[8]  = new SERefVector(FBR, BBR);
    edges[9]  = new SERefVector(FTR, BTR);
    edges[10] = new SERefVector(FBL, BBL);
    edges[11] = new SERefVector(FTL, BTL);
  }

  public void draw(FVectorVisualizer visualizer) {
    for (SERefVector edge : edges)
      visualizer.visualizeVector(edge, color);
  }

  public void rotateXYZ(double x, double y, double z) {
    for (int i = 0; i < corners.length; i++) {
      cornersRotated[i].rotateXYZInPlace(
        corners[i],
        x, y, z,
        rotationShift == null ? 0 : rotationShift.getX(),
        rotationShift == null ? 0 : rotationShift.getY(),
        rotationShift == null ? 0 : rotationShift.getZ()
      );
    }
  }

  public boolean isInside(Vector point) {
    // https://math.stackexchange.com/questions/1472049/check-if-a-point-is-inside-a-rectangular-shaped-area-3d

    IVector u = edges[10], v = edges[0], w = edges[3];

    IVector p1 = cornersRotated[0];
    IVector p2 = cornersRotated[4];
    IVector p4 = cornersRotated[1];
    IVector p5 = cornersRotated[3];

    double uDot = u.dot(point), vDot = v.dot(point), wDot = w.dot(point);

    return (
      uDot >= u.dot(p1) && uDot <= u.dot(p2) &&
      vDot >= v.dot(p1) && vDot <= v.dot(p4) &&
      // w is pointing in the opposite direction (down instead of up)
      vDot >= w.dot(p5) && wDot <= w.dot(p1)
    );
  }
}
