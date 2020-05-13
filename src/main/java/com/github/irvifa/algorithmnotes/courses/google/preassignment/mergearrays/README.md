Google use case: merging log files written by the same binary (for example, Google Docs servers) running on multiple computers.

-----

Merge two sorted arrays into one sorted array. Read the input from STDIN and print result to STDOUT as described below.

Input
-----
The first line of the input contains 2 integers N and M - lengths of the arrays. 0 <= N, M <= 10000000.

The following N lines contain elements of the first array A, one integer per line.

The last M lines contain elements of the second array B. 0 <= A[i], B[i] <= 1000000000. A[i] <= A[j], B[i] <= B[j] for i < j.

Output
------

A sorted array constructed from elements of arrays A and B. Each element should be on a separate line.

Example
-------

Input:

2 3
1
3
2
4
5

Output:

1
2
3
4
5

For the example above A = [1, 3], B = [2, 4, 5]; the result is [1, 2, 3, 4, 5].