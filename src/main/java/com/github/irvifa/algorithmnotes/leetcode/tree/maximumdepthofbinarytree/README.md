# [Maximum Depth of Binary Tree](https://leetcode.com/articles/maximum-depth-of-binary-tree/#)

Given a binary tree, find its maximum depth.

The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.

## Solution

### Recursion

### Intuition

From the definition, an intuitive idea would be to traverse the tree and record the maximum depth during the traversal. 

One could traverse the tree either by Depth-First Search (DFS) strategy or Breadth-First Search (BFS) strategy. For this problem, either way would do. Here we demonstrate a solution that is implemented with the DFS strategy and recursion.

### Complexity

    Time complexity : we visit each node exactly once, thus the time complexity is O(N), where N is the number of nodes.

    Space complexity : in the worst case, the tree is completely unbalanced, e.g. each node has only left child node, the recursion call would occur N times (the height of the tree),
    therefore the storage to keep the call stack would be O(N).
    But in the best case (the tree is completely balanced), the height of the tree would be log⁡(N).
    Therefore, the space complexity in this case would be O(log⁡(N))). 

### Tail Recursion and BFS

One might have noticed that the above recursion solution is probably not the most optimal one in terms of
the space complexity, and in the extreme case the overhead of call stack might even lead to stack overflow.

To address the issue, one can tweak the solution a bit to make it tail recursion,
which is a specific form of recursion where the recursive call is the last action in the function.

The benefit of having tail recursion, is that for certain programming languages (e.g. C++) the compiler 
could optimize the memory allocation of call stack by reusing the same space for every recursive call,
rather than creating the space for each one. 
As a result, one could obtain the constant space complexity O(1) for the overhead of the recursive calls.