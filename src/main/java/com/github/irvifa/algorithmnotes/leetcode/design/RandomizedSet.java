package com.github.irvifa.algorithmnotes.leetcode.design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Your RandomizedSet object will be instantiated and called as such: RandomizedSet obj = new
 * RandomizedSet(); boolean param_1 = obj.insert(val); boolean param_2 = obj.remove(val); int
 * param_3 = obj.getRandom();
 */
public class RandomizedSet {

  private final Map<Integer, Integer> dictionary;
  private final List<Integer> list;
  private final Random random;

  /**
   * Initialize your data structure here.
   */
  public RandomizedSet() {
    random = new Random();
    dictionary = new HashMap<>();
    list = new ArrayList<>();
  }

  /**
   * Inserts a value to the set. Returns true if the set did not already contain the specified
   * element.
   */
  public boolean insert(int val) {
    if (dictionary.containsKey(val)) {
      return false;
    }

    dictionary.put(val, list.size());
    list.add(list.size(), val);
    return true;
  }

  /**
   * Removes a value from the set. Returns true if the set contained the specified element.
   */
  public boolean remove(int val) {
    if (!dictionary.containsKey(val)) {
      return false;
    }

    // move the last element to the place idx of the element to delete
    int lastElement = list.get(list.size() - 1);
    int index = dictionary.get(val);
    list.set(index, lastElement);
    dictionary.put(lastElement, index);
    // delete the last element
    list.remove(list.size() - 1);
    dictionary.remove(val);
    return true;
  }

  /**
   * Get a random element from the set.
   */
  public int getRandom() {
    return list.get(random.nextInt(list.size()));
  }
}
