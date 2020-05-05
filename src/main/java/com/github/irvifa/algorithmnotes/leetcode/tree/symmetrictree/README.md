# [Symmetric Tree](https://leetcode.com/problems/symmetric-tree/)

## Solution

Two trees are a mirror reflection of each other if:

    Their two roots have the same value.
    The right subtree of each tree is a mirror reflection of the left subtree of the other tree.

### Recursive

#### Complexity Analysis

    Time complexity : O(N). Because we traverse the entire input tree once, the total run time is O(N),
    where nnn is the total number of nodes in the tree.

    Space complexity : The number of recursive calls is bound by the height of the tree.
    In the worst case, the tree is linear and the height is in O(N).
    Therefore, space complexity due to recursive calls on the stack is O(N) in the worst case.
