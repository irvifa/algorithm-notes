package com.github.irvifa.algorithmnotes.leetcode.challenges.may.one.jewelsandstones;

class Solution {

  // CHECKSTYLE.OFF: CyclomaticComplexity
  public int numJewelsInStones(String jewels, String stones) {
    boolean[] lowerCase = new boolean[26];
    boolean[] upperCase = new boolean[26];
    // CHECKSTYLE.ON: CyclomaticComplexity
    for (int i = 0; i < jewels.length(); i++) {
      char curr = jewels.charAt(i);
      if (curr >= 'a' && curr <= 'z') {
        lowerCase[curr - 'a'] = true;
      } else if (curr >= 'A' && curr <= 'Z') {
        upperCase[curr - 'A'] = true;
      }
    }
    int res = 0;
    for (int i = 0; i < stones.length(); i++) {
      char curr = stones.charAt(i);
      if (curr >= 'a' && curr <= 'z' && lowerCase[curr - 'a']) {
        res++;
      } else if (curr >= 'A' && curr <= 'Z' && upperCase[curr - 'A']) {
        res++;
      }
    }
    return res;
  }
}
