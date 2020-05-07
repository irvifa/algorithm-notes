package com.github.irvifa.algorithmnotes.leetcode.design;

import java.util.HashMap;
import java.util.Map;

public class Logger {

  private final Map<String, Integer> map;

  /**
   * Initialize your data structure here.
   */
  public Logger() {
    this.map = new HashMap<>();
  }

  /**
   * Returns true if the message should be printed in the given timestamp, otherwise returns false.
   * If this method returns false, the message will not be printed. The timestamp is in seconds
   * granularity.
   */
  public boolean shouldPrintMessage(int timestamp, String message) {
    if (this.map.containsKey(message)) {
      int prevTimestamp = this.map.get(message);
      if (prevTimestamp + 10 <= timestamp) {
        this.map.put(message, timestamp);
        return true;
      } else {
        return false;
      }
    }
    this.map.put(message, timestamp);
    return true;
  }
}

/**
 * Your Logger object will be instantiated and called as such: Logger obj = new Logger(); boolean
 * param_1 = obj.shouldPrintMessage(timestamp,message);
 */
