package com.github.irvifa.algorithmnotes.leetcode.tree.binarytreelevelordertraversal;

import com.github.irvifa.algorithmnotes.leetcode.tree.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solution {
  public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> nodes = new ArrayList<>();
    if (root == null) {
      return nodes;
    }

    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    int level = 0;
    while (!queue.isEmpty()) {
      nodes.add(new ArrayList<>());

      int levelSize = queue.size();
      for (int i = 0; i < levelSize; ++i) {
        TreeNode node = queue.remove();

        // fulfill the current level
        nodes.get(level).add(node.val);

        // add child nodes of the current level
        // in the queue for the next level
        if (node.left != null) {
          queue.add(node.left);
        }
        if (node.right != null) {
          queue.add(node.right);
        }
      }
      level++;
    }
    return nodes;
  }
}
