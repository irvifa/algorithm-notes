package com.github.irvifa.algorithmnotes.leetcode.design;

import java.util.HashMap;
import java.util.Map;

// CHECKSTYLE.OFF: AbbreviationAsWordInName
public class LRUCache {

  // CHECKSTYLE.ON: AbbreviationAsWordInName
  class Node<K, V> {

    K key;
    V value;
    Node prev;
    Node next;

    Node(K key, V value) {
      this.value = value;
      this.key = key;
    }
  }

  Node<Integer, Integer> head;
  Node<Integer, Integer> tail;
  Map<Integer, Node<Integer, Integer>> map;
  int cap = 0;

  public LRUCache(int capacity) {
    this.cap = capacity;
    this.map = new HashMap<>();
  }

  public int get(int key) {
    if (!map.containsKey(key)) {
      return -1;
    }

    //move to tail
    Node<Integer, Integer> t = map.get(key);

    removeNode(t);
    offerNode(t);

    return t.value;
  }

  public void put(int key, int value) {
    if (map.containsKey(key)) {
      Node t = map.get(key);
      t.value = value;

      //move to tail
      removeNode(t);
      offerNode(t);
    } else {
      if (map.size() >= cap) {
        //delete head
        map.remove(head.key);
        removeNode(head);
      }

      //add to tail
      Node<Integer, Integer> node = new Node<>(key, value);
      offerNode(node);
      map.put(key, node);
    }
  }

  private void removeNode(Node<Integer, Integer> n) {
    if (n.prev != null) {
      n.prev.next = n.next;
    } else {
      head = n.next;
    }

    if (n.next != null) {
      n.next.prev = n.prev;
    } else {
      tail = n.prev;
    }
  }

  private void offerNode(Node<Integer, Integer> n) {
    if (tail != null) {
      tail.next = n;
    }

    n.prev = tail;
    n.next = null;
    tail = n;

    if (head == null) {
      head = tail;
    }
  }
}
/**
 * Your LRUCache object will be instantiated and called as such: LRUCache obj = new
 * LRUCache(capacity); int param_1 = obj.get(key); obj.put(key,value);
 */
