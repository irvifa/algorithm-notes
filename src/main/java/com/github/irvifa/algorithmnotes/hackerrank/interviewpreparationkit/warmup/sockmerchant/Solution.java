package com.github.irvifa.algorithmnotes.hackerrank.interviewpreparationkit.warmup.sockmerchant;

import java.util.HashMap;
import java.util.Map;

public class Solution {

  private Solution() {
  }

  static int sockMerchant(int n, int[] ar) {
    Map<Integer, Integer> inventory = new HashMap<>();

    int matchingPairs = 0;
    for (Integer color : ar) {
      if (inventory.containsKey(color) && inventory.get(color).equals(1)) {
        inventory.put(color, 0);
        matchingPairs++;
        continue;
      }
      inventory.put(color, 1);
    }
    return matchingPairs;
  }

}
