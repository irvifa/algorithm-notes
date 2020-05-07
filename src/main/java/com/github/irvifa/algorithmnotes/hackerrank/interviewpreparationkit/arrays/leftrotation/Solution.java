package com.github.irvifa.algorithmnotes.hackerrank.interviewpreparationkit.arrays.leftrotation;

public class Solution {

  private Solution() {
  }

  static int[] rotLeft(int[] a, int k) {
    int n = a.length;
    // CHECKSTYLE.OFF: ParameterAssignment
    k %= n;
    // CHECKSTYLE.ON: ParameterAssignment
    int[] result = new int[n];
    for (int i = 0; i < n; i++) {
      result[(n + i - k) % n] = a[i];
    }

    return result;
  }
}
