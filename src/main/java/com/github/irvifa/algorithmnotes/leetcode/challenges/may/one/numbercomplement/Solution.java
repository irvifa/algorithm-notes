package com.github.irvifa.algorithmnotes.leetcode.challenges.may.one.numbercomplement;

class Solution {
  public int findComplement(int num) {
    // CHECKSTYLE.OFF: ParameterAssignment
    int template = (Integer.highestOneBit(num) << 1) - 1;
    num = ~num;
    // CHECKSTYLE.ON: ParameterAssignment
    return num & template;
  }
}
