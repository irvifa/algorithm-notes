# [Path Sum](https://leetcode.com/problems/path-sum/)

Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.

## Solution

### Recursion

#### Intuition

The most intuitive way is to use a recursion here. One is going through the tree by considering at each step the node itself and its children. If node is not a leaf, one calls recursively hasPathSum method for its children with a sum decreased by the current node value. If node is a leaf, one checks if the the current sum is zero, i.e if the initial sum was discovered.

#### Complexity

    Time complexity : we visit each node exactly once, thus the time complexity is O(N), where N is the number of nodes.
    Space complexity : in the worst case, the tree is completely unbalanced, e.g. each node has only one child node, the recursion call would occur N times (the height of the tree), therefore the storage to keep the call stack would be O(N).
    But in the best case (the tree is completely balanced), the height of the tree would be log⁡(N). Therefore, the space complexity in this case would be O(log⁡(N)).
 