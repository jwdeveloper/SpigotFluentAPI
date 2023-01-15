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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Vector implements IVector {

  private double x, y, z;

  public Vector(Vector other) {
    this.x = other.x;
    this.y = other.y;
    this.z = other.z;
  }

  public Vector(org.bukkit.util.Vector vector) {
    this.x = vector.getX();
    this.y = vector.getY();
    this.z = vector.getZ();
  }

  private double[][] generateZRotation(double angle) {
    return new double[][] {
      { Math.cos(angle), -Math.sin(angle), 0 },
      { Math.sin(angle),  Math.cos(angle), 0 },
      {               0,                0, 1 }
    };
  }

  private double[][] generateYRotation(double angle) {
    return new double[][] {
      { Math.cos(angle),  0, Math.sin(angle) },
      {               0,  1,               0 },
      { -Math.sin(angle), 0, Math.cos(angle) }
    };
  }

  private double[][] generateXRotation(double angle) {
    return new double[][] {
      { 1,               0,                0 },
      { 0, Math.cos(angle), -Math.sin(angle) },
      { 0, Math.sin(angle),  Math.cos(angle) }
    };
  }

  public void rotateXYZInPlace(
    Vector base,
    double angleX, double angleY, double angleZ,
    double shiftX, double shiftY, double shiftZ
  ) {
    double[][] mat = {{ base.getX() + shiftX }, { base.getY() + shiftY }, { base.getZ() + shiftZ }};

    // FIXME: These operations could all be unrolled for quite the speed increase

    mat = multiplyMatrices(generateXRotation(angleX), mat);
    mat = multiplyMatrices(generateYRotation(angleY), mat);
    mat = multiplyMatrices(generateZRotation(angleZ), mat);

    this.x = mat[0][0] - shiftX;
    this.y = mat[1][0] - shiftY;
    this.z = mat[2][0] - shiftZ;
  }

  private double[][] multiplyMatrices(double[][] a, double[][] b) {
    int aRows = a.length;
    int aCols = a[0].length;
    int bCols = b[0].length;

    double[][] res = new double[aRows][bCols];

    // For all rows of A
    for (int r = 0; r < aRows; r += 1) {

      // For all columns of B
      for (int c = 0; c < bCols; c += 1) {

        // For all columns of A
        for (int i = 0; i < aCols; i += 1)
          res[r][c] += a[r][i] * b[i][c];
      }
    }

    return res;
  }
}
