package com.github.irvifa.algorithmnotes.leetcode.design;

/**
 * Your MyLinkedList object will be instantiated and called as such: MyLinkedList obj = new
 * MyLinkedList(); int param_1 = obj.get(index); obj.addAtHead(val); obj.addAtTail(val);
 * obj.addAtIndex(index,val); obj.deleteAtIndex(index);
 */
public class MyLinkedList {

  class Node {

    int val;
    Node next;
    Node prev;

    Node(int x) {
      val = x;
    }
  }

  private int size;
  // sentinel nodes as pseudo-head and pseudo-tail
  private Node head;
  private Node tail;

  public int getSize() {
    return size;
  }

  public Node getHead() {
    return head;
  }

  public Node getTail() {
    return tail;
  }

  public MyLinkedList() {
    size = 0;
    head = new Node(0);
    tail = new Node(0);
    head.next = tail;
    tail.prev = head;
  }

  /**
   * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
   */
  public int get(int index) {
    // if index is invalid
    if (index < 0 || index >= size) {
      return -1;
    }

    // choose the fastest way: to move from the head
    // or to move from the tail
    Node node = head;
    if (index + 1 < size - index) {
      for (int i = 0; i < index + 1; ++i) {
        node = node.next;
      }
    } else {
      node = tail;
      for (int i = 0; i < size - index; ++i) {
        node = node.prev;
      }
    }

    return node.val;
  }

  /**
   * Add a node of value val before the first element of the linked list. After the insertion, the
   * new node will be the first node of the linked list.
   */
  public void addAtHead(int val) {
    Node predecessor = head;
    Node successor = head.next;

    ++size;
    Node toAdd = new Node(val);
    toAdd.prev = predecessor;
    toAdd.next = successor;
    predecessor.next = toAdd;
    successor.prev = toAdd;
  }

  /**
   * Append a node of value val to the last element of the linked list.
   */
  public void addAtTail(int val) {
    Node successor = tail;
    Node predecessor = tail.prev;

    ++size;
    Node toAdd = new Node(val);
    toAdd.prev = predecessor;
    toAdd.next = successor;
    predecessor.next = toAdd;
    successor.prev = toAdd;
  }

  /**
   * Add a node of value val before the index-th node in the linked list. If index equals to the
   * length of linked list, the node will be appended to the end of linked list. If index is greater
   * than the length, the node will not be inserted.
   */
  public void addAtIndex(int index, int val) {
    // If index is greater than the length,
    // the node will not be inserted.
    if (index > size) {
      return;
    }

    // [so weird] If index is negative,
    // the node will be inserted at the head of the list.
    if (index < 0) {
      // CHECKSTYLE.OFF: ParameterAssignment
      index = 0;
      // CHECKSTYLE.ON: ParameterAssignment
    }

    // find predecessor and successor of the node to be added
    Node predecessor;
    Node successor;
    if (index < size - index) {
      predecessor = head;
      for (int i = 0; i < index; ++i) {
        predecessor = predecessor.next;
      }
      successor = predecessor.next;
    } else {
      successor = tail;
      for (int i = 0; i < size - index; ++i) {
        successor = successor.prev;
      }
      predecessor = successor.prev;
    }

    // insertion itself
    ++size;
    Node toAdd = new Node(val);
    toAdd.prev = predecessor;
    toAdd.next = successor;
    predecessor.next = toAdd;
    successor.prev = toAdd;
  }

  /**
   * Delete the index-th node in the linked list, if the index is valid.
   */
  public void deleteAtIndex(int index) {
    // if the index is invalid, do nothing
    if (index < 0 || index >= size) {
      return;
    }

    // find predecessor and successor of the node to be deleted
    Node predecessor;
    Node successor;
    if (index < size - index) {
      predecessor = head;
      for (int i = 0; i < index; ++i) {
        predecessor = predecessor.next;
      }
      successor = predecessor.next.next;
    } else {
      successor = tail;
      for (int i = 0; i < size - index - 1; ++i) {
        successor = successor.prev;
      }
      predecessor = successor.prev.prev;
    }

    --size;
    predecessor.next = successor;
    successor.prev = predecessor;
  }
}
