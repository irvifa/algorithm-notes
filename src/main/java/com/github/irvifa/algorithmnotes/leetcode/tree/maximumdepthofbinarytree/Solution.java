package com.github.irvifa.algorithmnotes.leetcode.tree.maximumdepthofbinarytree;

import com.github.irvifa.algorithmnotes.leetcode.tree.TreeNode;

public class Solution {
  public int maxDepth(TreeNode root) {
    if (root == null) {
      return 0;
    }

    int r = maxDepth(root.right);
    int l = maxDepth(root.left);

    return Math.max(r + 1, l + 1);
  }
}
