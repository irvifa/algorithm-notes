package com.github.irvifa.algorithmnotes.leetcode.design;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ValidWordAbbr {

  private final Map<String, Set<String>> lookup;

  public ValidWordAbbr(String[] dictionary) {
    this.lookup = new HashMap<>();
    this.build(dictionary);
  }

  private void build(String[] dictionary) {
    for (String word : dictionary) {
      String key = this.getAbbr(word);
      if (!this.lookup.containsKey(key)) {
        Set<String> values = new HashSet<>();
        values.add(word);
        this.lookup.put(key, values);
      } else {
        Set<String> values = this.lookup.get(key);
        values.add(word);
        this.lookup.put(key, values);
      }
    }
  }

  private String getAbbr(String word) {
    if (word.length() == 0) {
      return word;
    }
    StringBuilder sb = new StringBuilder();
    int size = word.length() - 2;
    sb.append(word.charAt(0));
    if (size > 0) {
      sb.append(size);
    }
    sb.append(word.charAt(word.length() - 1));
    return sb.toString();
  }

  public boolean isUnique(String word) {
    String key = this.getAbbr(word);
    boolean isFoundKey = this.lookup.containsKey(key);
    if (!isFoundKey) {
      return !isFoundKey;
    }
    boolean isSameValue = this.lookup.get(key).contains(word);
    return isFoundKey && isSameValue && (this.lookup.get(key).size() <= 1);
  }
}
