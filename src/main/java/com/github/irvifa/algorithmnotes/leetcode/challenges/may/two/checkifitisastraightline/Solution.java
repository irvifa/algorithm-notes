package com.github.irvifa.algorithmnotes.leetcode.challenges.may.two.checkifitisastraightline;

public class Solution {
  public boolean checkStraightLine(int[][] coordinates) {
    int[] point1 = coordinates[1];
    int[] point2 = coordinates[0];
    float globalSlope = slope(point1, point2);

    for (int i = 1; i < coordinates.length; i++) {
      int[] p1 = coordinates[i];
      int[] p2 = coordinates[0];
      float slop = slope(p1, p2);
      if (globalSlope != slop) {
        return false;
      }
    }
    return true;
  }

  private float slope(int[] p1, int[] p2) {
    if ((p1[0] - p2[0]) == 0) {
      return 0;
    }
    return (float) (p2[1] - p1[1]) / (p2[0] - p1[0]);
  }
}
