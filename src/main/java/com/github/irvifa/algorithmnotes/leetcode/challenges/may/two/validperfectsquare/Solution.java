package com.github.irvifa.algorithmnotes.leetcode.challenges.may.two.validperfectsquare;

class Solution {

  public boolean isPerfectSquare(int n) {
    long result = n;
    while (result * result > n) {
      result = (result + n / result) / 2;
    }
    return result * result == n;
  }
}
