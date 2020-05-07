package com.github.irvifa.algorithmnotes.hackerrank.interviewpreparationkit.greedy.luckbalance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {

  private Solution() {
  }

  static int luckBalance(int k, int[][] contests) {
    int n = contests.length;
    int maxLuck = 0;
    List<Integer> importantContests = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      int luck = contests[i][0];
      int important = contests[i][1];

      if (important != 1) {
        maxLuck += luck;
      } else {
        importantContests.add(luck);
      }
    }

    Collections.sort(importantContests, Collections.reverseOrder());
    for (int i = 0; i < importantContests.size(); i++) {
      if (i < k) {
        maxLuck += importantContests.get(i);
      } else {
        maxLuck -= importantContests.get(i);
      }
    }

    return maxLuck;
  }
}
