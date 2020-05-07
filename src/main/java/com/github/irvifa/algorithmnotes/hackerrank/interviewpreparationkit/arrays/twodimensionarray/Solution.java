package com.github.irvifa.algorithmnotes.hackerrank.interviewpreparationkit.arrays.twodimensionarray;

public class Solution {

  private Solution() {}

  // Complete the hourglassSum function below.
  static int hourglassSum(int[][] array) {
    int max = Integer.MIN_VALUE;
    int sum = 0;

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        sum = array[i][j] + array[i][j + 1] + array[i][j + 2] + array[i + 1][j + 1] + array[i + 2][j]
                + array[i + 2][j + 1]
                + array[i + 2][j + 2];
        if (max < sum) {
          max = sum;
        }
      }
    }

    return max;
  }
}
