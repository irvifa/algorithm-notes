# [Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/)

## Solution

### Iterative

#### Intuition

The zero level contains only one node root.

    Initiate queue with a root and start from the level number 0 : level = 0.

    While queue is not empty :

        Start the current level by adding an empty list into output structure levels.

        Compute how many elements should be on the current level : it's a queue length.

        Pop out all these elements from the queue and add them into the current level.

        Push their child nodes into the queue for the next level.

        Go to the next level level++.

#### Complexity

    Time complexity : O(N) since each node is processed exactly once.

    Space complexity : O(N) to keep the output structure which contains N node values.
