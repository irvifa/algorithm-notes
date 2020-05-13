Google use case: Autocomplete feature in Google Search.

-----

There is a dictionary of words. Write a function that for a given prefix returns up to 10 words starting with that prefix. Print the words ordered lexicographically from smallest to largest, choose the first 10 words lexicographically (not as they appear in the dictionary).

Input
-----
The fist line contains N, the number of words in the dictionary. 0 < N < 5000000. Then the subsequent N lines contain one word per line. Words are ASCII only, not empty, and don't contain whitespace. The same word doesn't appear more than once. The words can be in any order (not necessary lexicographical).

The next line contains M, the number of prefixes. 0 < M < 1000000. Then the subsequent M lines contain one prefix per line. Prefixes are ASCII only, not empty, and don't contain whitespace.

Each word and prefix is at most 42 characters long.

Output
------
There is one output line for each prefix. The line contains a space-separated list of up to 10 dictionary words starting with that prefix, in ASCII code lexicographical order. If there are more than 10 matching words, the 10 first in lexicographical order must be printed.

Example lexicographical order: a < ab < abz < ac < az < b

Example
-------
Input:

3
foo
baz
bar
6
f
b
ba
bar
bard
fo

Output:

foo
bar baz
bar baz
bar

foo

 