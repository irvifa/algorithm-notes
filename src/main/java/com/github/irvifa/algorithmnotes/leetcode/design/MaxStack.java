package com.github.irvifa.algorithmnotes.leetcode.design;

import java.util.Stack;

/**
 * Your MaxStack object will be instantiated and called as such: MaxStack obj = new MaxStack();
 * obj.push(x); int param_2 = obj.pop(); int param_3 = obj.top(); int param_4 = obj.peekMax(); int
 * param_5 = obj.popMax();
 */
public class MaxStack {

  private final Stack<Integer> stack;
  private final Stack<Integer> maxStack;

  public MaxStack() {
    stack = new Stack<>();
    maxStack = new Stack<>();
  }

  public void push(int value) {
    int max = maxStack.isEmpty() ? value : maxStack.peek();
    maxStack.push(max > value ? max : value);
    stack.push(value);
  }

  public int pop() {
    maxStack.pop();
    return stack.pop();
  }

  public int top() {
    return stack.peek();
  }

  public int peekMax() {
    return maxStack.peek();
  }

  public int popMax() {
    int max = peekMax();
    Stack<Integer> buffer = new Stack();
    while (top() != max) {
      buffer.push(pop());
    }
    pop();
    while (!buffer.isEmpty()) {
      push(buffer.pop());
    }
    return max;
  }
}
