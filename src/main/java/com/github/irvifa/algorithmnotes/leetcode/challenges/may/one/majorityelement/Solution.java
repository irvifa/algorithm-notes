package com.github.irvifa.algorithmnotes.leetcode.challenges.may.one.majorityelement;

import java.util.HashMap;
import java.util.Map;

class Solution {

  public int majorityElement(int[] nums) {
    Map<Integer, Integer> memo = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      if (!memo.containsKey(nums[i])) {
        memo.put(nums[i], 1);
      } else {
        memo.put(nums[i], memo.get(nums[i]) + 1);
      }
    }

    int max = Integer.MIN_VALUE;
    int res = -1;
    for (Integer key : memo.keySet()) {
      if (max < memo.get(key)) {
        max = memo.get(key);
        res = key;
      }
    }
    return res;
  }
}
