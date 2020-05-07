package com.github.irvifa.algorithmnotes.leetcode.design;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

// CHECKSTYLE.OFF: AbbreviationAsWordInName
public class LFUCache {

  // CHECKSTYLE.ON: AbbreviationAsWordInName
  private Map<Integer, Integer> values;
  private Map<Integer, Integer> frequency;
  private Map<Integer, LinkedHashSet<Integer>> frequencyCount;
  private int capacity;
  private int minValue;

  public LFUCache(int capacity) {
    values = new HashMap<>();
    frequency = new HashMap<>();
    frequencyCount = new HashMap<>();
    this.capacity = capacity;
    minValue = 0;
  }

  public int get(int key) {
    if (!values.containsKey(key)) {
      return -1;
    }

    // Updating frequency
    int oldFrequency = frequency.get(key);
    frequency.put(key, oldFrequency + 1);
    int newFrequency = frequency.get(key);

    // Removing from old frequency set
    // CHECKSTYLE.OFF: IllegalType
    LinkedHashSet<Integer> set = frequencyCount.get(oldFrequency);
    // CHECKSTYLE.ON: IllegalType
    set.remove(key);
    if (set.isEmpty()) {
      // As this was the only key with min freq so minimum frequency should be updated
      if (minValue == oldFrequency) {
        minValue = oldFrequency + 1;
      }
      frequencyCount.remove(oldFrequency);
    } else {
      frequencyCount.put(oldFrequency, set);
    }

    // Updating new frequency set
    frequencyCount.computeIfAbsent(newFrequency, k -> new LinkedHashSet<>()).add(key);

    return values.get(key);
  }

  public void put(int key, int value) {
    if (capacity == 0) {
      return;
    }

    if (get(key) != -1) {
      values.put(key, value);
      return;
    }

    if (values.size() == capacity) {
      // CHECKSTYLE.OFF: IllegalType
      LinkedHashSet<Integer> set = frequencyCount.get(minValue);
      // CHECKSTYLE.ON: IllegalType
      int keyToBeDeleted = set.iterator().next();
      values.remove(keyToBeDeleted);
      frequency.remove(keyToBeDeleted);
      set.remove(keyToBeDeleted);

      if (set.isEmpty()) {
        frequencyCount.remove(minValue);
      }
    }

    values.put(key, value);
    frequency.put(key, 1);
    frequencyCount.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
    minValue = 1;
  }
}

/**
 * Your LFUCache object will be instantiated and called as such: LFUCache obj = new
 * LFUCache(capacity); int param_1 = obj.get(key); obj.put(key,value);
 */
