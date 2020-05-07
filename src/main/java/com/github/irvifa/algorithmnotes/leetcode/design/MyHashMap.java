package com.github.irvifa.algorithmnotes.leetcode.design;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class MyHashMap {

  private static final int SPACE = 2069;
  private int keySpace;
  private List<Bucket> hashTable;

  // CHECKSTYLE.OFF: RedundantModifier
  public MyHashMap() {
    // CHECKSTYLE.ON: RedundantModifier
    this.keySpace = SPACE;
    this.hashTable = new ArrayList<Bucket>();
    for (int i = 0; i < this.keySpace; ++i) {
      this.hashTable.add(new Bucket());
    }
  }

  public void put(int key, int value) {
    int hashKey = key % this.keySpace;
    this.hashTable.get(hashKey).update(key, value);
  }

  public int get(int key) {
    int hashKey = key % this.keySpace;
    return this.hashTable.get(hashKey).get(key);
  }


  public void remove(int key) {
    int hashKey = key % this.keySpace;
    this.hashTable.get(hashKey).remove(key);
  }

  class Entry<K, V> {

    public K key;
    public V value;

    // CHECKSTYLE.OFF: RedundantModifier
    public Entry(K key, V value) {
      // CHECKSTYLE.ON: RedundantModifier
      this.key = key;
      this.value = value;
    }
  }


  class Bucket {

    private final List<Entry<Integer, Integer>> bucket;

    // CHECKSTYLE.OFF: RedundantModifier
    public Bucket() {
      // CHECKSTYLE.ON: RedundantModifier
      this.bucket = new LinkedList<Entry<Integer, Integer>>();
    }

    public Integer get(Integer key) {
      for (Entry<Integer, Integer> entry : this.bucket) {
        if (entry.key.equals(key)) {
          return entry.value;
        }
      }
      return -1;
    }

    public void update(Integer key, Integer value) {
      boolean isFound = false;
      for (Entry<Integer, Integer> entry : this.bucket) {
        if (entry.key.equals(key)) {
          entry.value = value;
          isFound = true;
        }
      }
      if (!isFound) {
        this.bucket.add(new Entry<>(key, value));
      }
    }

    public void remove(Integer key) {
      for (Entry<Integer, Integer> entry : this.bucket) {
        if (entry.key.equals(key)) {
          this.bucket.remove(entry);
          break;
        }
      }
    }
  }
}
