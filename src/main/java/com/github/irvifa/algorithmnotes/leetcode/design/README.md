# Design

## [First Unique](https://leetcode.com/problems/first-unique-number/)

### Complexity
    Time complexity :

        constructor: O(K).

        For each of the K numbers passed into the constructor, we're making a call to add().
        As we will determine below, each call to add() has a cost of O(1).
        Therefore, for the K numbers added in the constructor, we get a total cost of K⋅O(1)=O(K).

        add(): O(1).

        Seeing as the queue is implemented as a LinkedHashSet, the cost of this removal is O(1).

        Therefore, we get an O(1) time complexity for add().

        showFirstUnique(): O(1).

        This time around, the implementation for showFirstUnique() is straightforward.
        It simply checks whether or not the queue contains any items, and if it does, it returns the first one (without removing it).
        This has a cost of O(1).

    Space complexity : O(N).

    Each number is stored up to once in the LinkedHashSet, and up to once in the HashMap. Therefore, we get an overall space complexity of O(N).

## [Implement Trie (Prefix Tree)](https://leetcode.com/problems/implement-trie-prefix-tree/)

### Complexity

    Time complexity : O(N)

    Space complexity : O(1)

## [Find Median from Data Stream](https://leetcode.com/problems/find-median-from-data-stream/)

## [Unique Word Abbreviation](https://leetcode.com/problems/unique-word-abbreviation/)
