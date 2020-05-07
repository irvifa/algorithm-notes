package com.github.irvifa.algorithmnotes.hackerrank.interviewpreparationkit.greedy.minimumabsolutedifferenceinanarray;

import java.util.Arrays;

public class Solution {

  private Solution() {
  }

  static int minimumAbsoluteDifference(int[] arr) {
    Arrays.sort(arr);
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < arr.length - 1; i++) {
      min = Math.min(Math.abs(arr[i] - arr[i + 1]), min);
    }
    return min;
  }
}
