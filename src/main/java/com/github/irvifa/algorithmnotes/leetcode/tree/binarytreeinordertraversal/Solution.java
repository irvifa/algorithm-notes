package com.github.irvifa.algorithmnotes.leetcode.tree.binarytreeinordertraversal;

import com.github.irvifa.algorithmnotes.leetcode.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solution {

  public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    TreeNode curr = root;
    while (curr != null || !stack.isEmpty()) {
      while (curr != null) {
        stack.push(curr);
        curr = curr.left;
      }
      curr = stack.pop();
      res.add(curr.val);
      curr = curr.right;
    }
    return res;
  }
}

