package com.github.irvifa.algorithmnotes.leetcode.design;

public class MinStack {

  class Entry {

    public int value;
    public int min;
    public Entry next;

    // CHECKSTYLE.OFF: RedundantModifier
    public Entry(int value, int min) {
      // CHECKSTYLE.ON: RedundantModifier
      this.value = value;
      this.min = min;
    }
  }

  public Entry top;

  /**
   * initialize your data structure here.
   */
  public MinStack() {
  }

  public void push(int x) {
    if (top == null) {
      top = new Entry(x, x);
    } else {
      Entry entry = new Entry(x, Math.min(x, top.min));
      entry.next = top;
      top = entry;
    }
  }

  public void pop() {
    if (top == null) {
      return;
    }
    Entry temp = top.next;
    top.next = null;
    top = temp;
  }

  public int top() {
    if (top == null) {
      return -1;
    }
    return top.value;
  }

  public int getMin() {
    if (top == null) {
      return -1;
    }
    return top.min;
  }

}
