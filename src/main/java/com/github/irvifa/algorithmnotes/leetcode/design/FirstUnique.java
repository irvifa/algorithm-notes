package com.github.irvifa.algorithmnotes.leetcode.design;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class FirstUnique {

  private final Set<Integer> queue;
  private final Map<Integer, Boolean> isUnique;

  public FirstUnique(int[] nums) {
    this.queue = new LinkedHashSet<>();
    this.isUnique = new HashMap<>();
    for (int num : nums) {
      this.add(num);
    }
  }

  public int showFirstUnique() {
    if (!this.queue.isEmpty()) {
      return this.queue.iterator().next();
    }
    return -1;
  }

  public void add(int value) {
    if (!this.isUnique.containsKey(value)) {
      this.isUnique.put(value, true);
      this.queue.add(value);
    } else {
      this.isUnique.put(value, false);
      this.queue.remove(value);
    }
  }
}
