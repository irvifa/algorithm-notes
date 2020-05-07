# [Same Tree](https://leetcode.com/problems/same-tree/)

## Solution

### Recursive

#### Intuition

Two binary trees are considered the same if they are structurally identical and the nodes have the same value.

#### Complexity Analysis

    Time complexity : O(N), where N is a number of nodes in the tree, since one visits each node exactly once.

    Space complexity : O(log⁡(N)) in the best case of completely balanced tree and O(N) in the worst case of completely unbalanced tree, to keep a recursion stack.
