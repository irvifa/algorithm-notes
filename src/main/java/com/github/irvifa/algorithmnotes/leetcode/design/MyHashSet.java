package com.github.irvifa.algorithmnotes.leetcode.design;

public class MyHashSet {

  private static final int ITEMS_SIZE = 1001;
  private static final int BUCKETS_SIZE = 1000;

  class Buckets {

    private int itemsNum;
    private int[] items;

    // CHECKSTYLE.OFF: RedundantModifier
    public Buckets() {
      // CHECKSTYLE.ON: RedundantModifier
      items = new int[ITEMS_SIZE];
      init();
    }

    // CHECKSTYLE.OFF: RedundantModifier
    public void init() {
      // CHECKSTYLE.ON: RedundantModifier
      for (int i = 0; i < ITEMS_SIZE; i++) {
        items[i] = -1;
      }
    }

    public void setItem(int key, int value) {
      int index = hashItem(key);
      items[index] = value;
    }

    public int getItem(int key) {
      int index = hashItem(key);
      return items[index];
    }

    public int hashItem(int key) {
      return key / BUCKETS_SIZE;
    }
  }


  private Buckets[] buckets;

  public MyHashSet() {
    buckets = new Buckets[BUCKETS_SIZE];
  }

  public int hashBucket(int key) {
    return key % BUCKETS_SIZE;
  }

  public void add(int key) {
    int bucketId = hashBucket(key);
    if (buckets[bucketId] == null) {
      buckets[bucketId] = new Buckets();
    }
    buckets[bucketId].setItem(key, key);
  }

  public void remove(int key) {
    int bucketId = hashBucket(key);
    if (contains(key)) {
      buckets[bucketId].setItem(key, -1);
    }
  }


  public boolean contains(int key) {
    int bucketId = hashBucket(key);
    return buckets[bucketId] != null && buckets[bucketId].getItem(key) != -1;
  }
}
