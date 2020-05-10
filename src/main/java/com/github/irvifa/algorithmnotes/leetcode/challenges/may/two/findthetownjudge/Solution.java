package com.github.irvifa.algorithmnotes.leetcode.challenges.may.two.findthetownjudge;

class Solution {

  public int findJudge(int n, int[][] trust) {
    int[] tmp = new int[n + 1];
    for (int[] temp : trust) {
      tmp[temp[0]]--;
      tmp[temp[1]]++;
    }
    for (int i = 1; i <= n; ++i) {
      if (tmp[i] == n - 1) {
        return i;
      }
    }
    return -1;
  }
}
