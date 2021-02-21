package com.github.irvifa.algorithmnotes.leetcode.arrays.runningsumofonedimensionarray;

public class Solution {
  public int[] runningSum(int[] nums) {
    int[] result = new int[nums.length];
    int i = 0;
    result[i] = nums[i];
    for (i = 1; i < nums.length; i++) {
      result[i] = result[i - 1] + nums[i];
    }
    return result;
  }
}
