package com.github.irvifa.algorithmnotes.leetcode.challenges.may.one.cousinsinbinarytree;

import com.github.irvifa.algorithmnotes.leetcode.tree.TreeNode;
import java.util.HashMap;
import java.util.Map;

// https://leetcode.com/explore/challenge/card/may-leetcoding-challenge/534/week-1-may-1st-may-7th/3322/
public class Solution {

  Map<Integer, Integer> depth;
  Map<Integer, TreeNode> parent;

  public boolean isCousins(TreeNode root, int x, int y) {
    depth = new HashMap<>();
    parent = new HashMap<>();
    dfs(root, null);
    return (depth.get(x) == depth.get(y) && parent.get(x) != parent.get(y));
  }

  private void dfs(TreeNode node, TreeNode par) {
    if (node != null) {
      depth.put(node.val, par != null ? 1 + depth.get(par.val) : 0);
      parent.put(node.val, par);
      dfs(node.left, node);
      dfs(node.right, node);
    }
  }
}
