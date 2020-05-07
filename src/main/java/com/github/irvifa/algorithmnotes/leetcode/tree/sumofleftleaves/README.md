# [Sum of Left Leaves](https://leetcode.com/problems/sum-of-left-leaves/)

Find the sum of all left leaves in a given binary tree.

## Solution

Recall that a binary tree is made up of linked tree nodes, where each node has a reference to its left child and to its right child.
We access the child nodes by using root.left and root.right. Tree traversal algorithms are used to explore all nodes in the tree.

### Recursive Tree Traversal (Pre-order)

#### Intuition

To implement a recursive function, we need to identify the base cases and recursive cases.

- The base case is that this subtree is a leaf node (i.e. the subtree only contains a single node; its root node). The value we return for it depends on whether this subtree was to the left or the right of its parent. If it was to the left, we return its value otherwise we return zero.
- The recursive case is that this subtree contains a left and/or right subtree (e.g. the subtree has more than just the root node in it). We call the recursive function on the left and right subtrees, add their results together and return the added result.

Like before though, we still have the problem of knowing whether or not the current subtree was to the left of its parent. With recursion though, there is a far more elegant solution than before: 

- We can simply have an additional boolean parameter on our recursive function, specifying whether or not the subtree is a left one! Note that the top subtree is neither a left node, nor a right node, but we pass in false for it, as the purpose of the parameter is to specify whether or not it is a left subtree.

#### Complexity Analysis

Let `N` be the number of nodes.

    Time complexity : O(N).
    The code within the recursive function is all O(1). The function is called exactly once for each of the NNN nodes. Therefore, the total time complexity of the algorithm is O(N).

    Space complexity : O(N).
    In the worst case, the tree consists of nodes that form a single deep strand. In this case, the runtime-stack will have N calls to sumOfLeftLeaves(...) on it at the same time, giving a worst-case space complexity of O(N).

### Morris Tree Traversal (Pre-order)

#### Intuition

All of the algorithms we've looked at so far had a time complexity of O(N),
and a space complexity of O(N).

We know it is impossible to reduce the time complexity any further,
as we need to visit each node to access all the nodes.

The space complexity might initially seem impossible to reduce,
as when a node has two children, we need to explore one immediately,
and keep track of the other for exploration afterward (often, we explore the left subtree key,
and keep track of the right subtree for later). 
Going down the tree, we can end up with many of these child nodes awaiting exploration. 
However, there is a tree traversal algorithm that requires O(N) time and only O(1) space: Morris Tree Traversal.

This traversal algorithm works by temporarily modifying the input tree so that before we explore a left subtree,
we find the subtree root's in-order predecessor (which will never have a right child),
and make its right link point back up to the root. 
Then we explore the left subtree. When we're done exploring the left subtree,
the link back up to the root will then allow us to explore the right subtree.
When we follow the link back up, we also remove it so that the input tree is restored.
In this way, we can no longer need an auxiliary data structure to keep track of the right subtrees.

Given that this algorithm modifies the input tree, will we still be able to identify which nodes are left-leaves?
As it turns out we still can. Whenever we reach a node for the key time, we know we haven't yet looked at its left subtree,
and so have not modified it. Therefore, we can simply check if the left child is a leaf node, in the same way we did before.

```aidl
class Solution {
  public int sumOfLeftLeaves(TreeNode root) {
    int totalSum = 0;
    TreeNode currentNode = root;
    while (currentNode != null) {
      /**
       If there is no left child, we can simply explore the right subtree
       without needing to worry about keeping track of currentNode's other
       child.
      **/
      if (currentNode.left == null) {
        currentNode = currentNode.right;
      } else {
        TreeNode previous = currentNode.left;
        // Check if this left node is a leaf node.
        if (previous.left == null && previous.right == null) {
          totalSum += previous.val;
        }
        // Find the inorder predecessor for currentNode.
        while (previous.right != null && !previous.right.equals(currentNode)) {
          previous = previous.right;
        }
        
        if (previous.right == null) {
         /**
           We've not yet visited the inorder predecessor. This means that we 
           still need to explore currentNode's left subtree. Before doing this,
           we will put a link back so that we can get back to the right subtree
           when we need to.
         **/
          previous.right = currentNode;
          currentNode = currentNode.left;
        } else {
          /**
            We have already visited the inorder predecessor. This means that we
            need to remove the link we added, and then move onto the right
            subtree and explore it.
          **/
          previous.right = null;
          currentNode = currentNode.right;
        }
      }
    }
    return totalSum;
  }
}
```

#### Complexity Analysis

    Time complexity : O(N).

    Each node is visited at least once; with some nodes visited twice to remove the added links and move back up to the subtree root. However, no node is visited more than twice, so our time complexity is O(N)O(N)O(N).

    Space complexity : O(1).

    We are only using constant extra space.
