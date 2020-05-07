package com.github.irvifa.algorithmnotes.hackerrank.interviewpreparationkit.linkedlist;

public class SinglyLinkedList {

  public SinglyLinkedListNode head;
  public SinglyLinkedListNode tail;

  public SinglyLinkedList() {
    this.head = null;
    this.tail = null;
  }

  public void insertNode(int nodeData) {
    SinglyLinkedListNode node = new SinglyLinkedListNode(nodeData);

    if (this.head == null) {
      this.head = node;
    } else {
      this.tail.next = node;
    }

    this.tail = node;
  }

  public SinglyLinkedListNode insertNodeAtPosition(SinglyLinkedListNode head, int data,
      int position) {
    SinglyLinkedListNode pointer = new SinglyLinkedListNode(data);

    if (head == null) {
      // CHECKSTYLE.OFF: ParameterAssignment
      head = pointer;
      // CHECKSTYLE.ON: ParameterAssignment
    } else if (position == 0) {
      pointer.next = head;
      // CHECKSTYLE.OFF: ParameterAssignment
      head = pointer;
      // CHECKSTYLE.ON: ParameterAssignment
    } else {
      SinglyLinkedListNode currentNode = head;
      int index = 1;
      while (currentNode != null) {
        if (index == position) {
          pointer.next = currentNode.next;
          currentNode.next = pointer;
          break;
        }
        currentNode = currentNode.next;
        index++;
      }
    }

    return head;
  }

}
