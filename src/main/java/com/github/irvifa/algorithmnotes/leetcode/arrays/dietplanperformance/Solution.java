package com.github.irvifa.algorithmnotes.leetcode.arrays.dietplanperformance;

public class Solution {

  public int dietPlanPerformance(int[] calories, int k, int lower, int upper) {
    int size = calories.length;
    int[] tables = new int[size + 1];
    // Create a memoization for calories intake.
    for (int i = 1; i <= size; i++) {
      tables[i] = tables[i - 1] + calories[i - 1];
    }
    int res = 0;
    // For range k, see if during that time the calories is lower or higher.
    for (int i = k; i <= size; i++) {
      int cur = tables[i] - tables[i - k];
      if (cur < lower) {
        res--;
      }
      if (cur > upper) {
        res++;
      }
    }
    return res;
  }
}
