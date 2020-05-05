package com.github.irvifa.algorithmnotes.leetcode.tree.symmetrictree;

import com.github.irvifa.algorithmnotes.leetcode.tree.TreeNode;

public class Solution {
  public boolean isMirror(TreeNode node1, TreeNode node2)
  {
    if (node1 == null && node2 == null)
      return true;

    if (node1 != null && node2 != null && node1.val == node2.val)
      return (isMirror(node1.left, node2.right)
          && isMirror(node1.right, node2.left));

    return false;
  }

  public boolean isSymmetric(TreeNode root)
  {
    return isMirror(root, root);
  }
}
