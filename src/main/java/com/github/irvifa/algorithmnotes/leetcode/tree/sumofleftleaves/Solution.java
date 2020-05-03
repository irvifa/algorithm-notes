package com.github.irvifa.algorithmnotes.leetcode.tree.sumofleftleaves;

import com.github.irvifa.algorithmnotes.leetcode.tree.TreeNode;

// https://leetcode.com/problems/sum-of-left-leaves/
public class Solution {

  public int sumOfLeftLeaves(TreeNode root) {
    return sumOfLeftLeaves(root, false);
  }

  private int sumOfLeftLeaves(
      TreeNode root, boolean isLeft) {
    if (root == null) {
      return 0;
    }
    /*This node is a leaf, if it's a left leaf, we return the value
      if it's a right leaf we return 0 since right leaf doesn't count*/
    if (root.left == null && root.right == null) {
      if (isLeft) {
        return root.val;
      }
      return 0;
    }
    return sumOfLeftLeaves(root.left, true) + sumOfLeftLeaves(root.right, false);
  }
}
