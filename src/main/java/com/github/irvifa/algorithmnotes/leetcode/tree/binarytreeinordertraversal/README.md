# [Binary Tree Inorder Traversal](https://leetcode.com/articles/binary-tree-inorder-traversal/#)

Given a binary tree, return the inorder traversal of its nodes' values.

## Solution

### Iterative using Stack

#### Complexity Analysis

N is the number of node.

    Time complexity : O(N).

    Space complexity : O(N). 

### Morris Traversal
    
In this method, we have to use a new data structure-Threaded Binary Tree, with strategy is as follows:

    Step 1: Initialize current as root
    Step 2: While current is not NULL,
    If current does not have left child
        a. Add current’s value
        b. Go to the right, i.e., current = current.right
    Else
        a. In current's left subtree, make current the right child of the rightmost node
        b. Go to this left child, i.e., current = current.left

```java
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    TreeNode curr = root;
    TreeNode pre;
    while (curr != null) {
        if (curr.left == null) {
            res.add(curr.val);
            curr = curr.right; // move to next right node
        } else { 
            // has a left subtree
            pre = curr.left;
            while (pre.right != null) { // find rightmost
                pre = pre.right;
            }
            pre.right = curr; // put cur after the pre node
            TreeNode temp = curr; 
            curr = curr.left; // move cur to the top of the new tree
            temp.left = null; // set previous left be null, avoid infinite loops
        }
    }
    return res;
}
```

For more details, please check [Threaded binary tree](https://en.wikipedia.org/wiki/Threaded_binary_tree)
and [Explanation of Morris Method](https://stackoverflow.com/questions/5502916/explain-morris-inorder-tree-traversal-without-using-stacks-or-recursion).

#### Complexity Analysis

N is the size of the input.

    Time complexity : O(N). To prove that the time complexity is O(N),
    the biggest problem lies in finding the time complexity of finding the predecessor nodes of all the nodes in the binary tree. 
    Intuitively, the complexity is O(N log⁡ N), because to find the predecessor node for a single node related to the height of the tree.
    However, finding the predecessor nodes for all nodes only needs O(N) time, since a binary Tree with nnn nodes has N-1 edges,
    the whole processing for each edges up to 2 times, one is to locate a node, and the other is to find the predecessor node.
    So the complexity is O(N).

    Space complexity : O(N). 

