package com.github.irvifa.algorithmnotes.leetcode.challenges.may.one.ransomnote;

class Solution {
  public boolean canConstruct(String ransomNote, String magazine) {
    int[] table = new int[26];

    for (int i = 0; i < magazine.length(); i++) {
      table[magazine.charAt(i) - 'a']++;
    }

    for (int i = 0; i < ransomNote.length(); i++) {
      char currentCharacter = ransomNote.charAt(i);
      if (table[currentCharacter - 'a'] <= 0) {
        return false;
      }
      table[currentCharacter - 'a']--;
    }
    return true;
  }
}
