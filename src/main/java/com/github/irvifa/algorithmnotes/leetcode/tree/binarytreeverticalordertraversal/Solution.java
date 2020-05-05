package com.github.irvifa.algorithmnotes.leetcode.tree.binarytreeverticalordertraversal;

import com.github.irvifa.algorithmnotes.leetcode.tree.TreeNode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class ColumnOffset {

  TreeNode node;
  Integer offset;

  ColumnOffset(TreeNode node, Integer offset) {
    this.node = node;
    this.offset = offset;
  }
}

public class Solution {

  public List<List<Integer>> verticalOrder(TreeNode root) {
    List<List<Integer>> output = new ArrayList<>();
    if (root == null) {
      return output;
    }

    Map<Integer, List<Integer>> columnTable = new HashMap<>();
    // Pair of node and its column offset
    Queue<ColumnOffset> queue = new ArrayDeque<>();
    int column = 0;
    queue.offer(new ColumnOffset(root, column));

    int minColumn = 0, maxColumn = 0;

    while (!queue.isEmpty()) {
      ColumnOffset p = queue.poll();
      root = p.node;
      column = p.offset;

      if (root != null) {
        if (!columnTable.containsKey(column)) {
          columnTable.put(column, new ArrayList<>());
        }
        columnTable.get(column).add(root.val);
        minColumn = Math.min(minColumn, column);
        maxColumn = Math.max(maxColumn, column);

        queue.offer(new ColumnOffset(root.left, column - 1));
        queue.offer(new ColumnOffset(root.right, column + 1));
      }
    }

    for (int i = minColumn; i < maxColumn + 1; ++i) {
      output.add(columnTable.get(i));
    }

    return output;
  }
}
