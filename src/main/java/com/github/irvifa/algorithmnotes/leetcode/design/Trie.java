package com.github.irvifa.algorithmnotes.leetcode.design;

public class Trie {

  private TrieNode root;

  public Trie() {
    root = new TrieNode();
  }

  // Inserts a word into the trie.
  public void insert(String word) {
    TrieNode node = root;
    for (int i = 0; i < word.length(); i++) {
      char character = word.charAt(i);
      if (!node.containsKey(character)) {
        node.put(character, new TrieNode());
      }
      node = node.get(character);
    }
    node.setEnd();
  }

  private TrieNode searchPrefix(String word) {
    TrieNode node = root;
    for (int i = 0; i < word.length(); i++) {
      char character = word.charAt(i);
      if (node.containsKey(character)) {
        node = node.get(character);
      } else {
        return null;
      }
    }
    return node;
  }

  public boolean search(String word) {
    TrieNode node = searchPrefix(word);
    return node != null && node.isLeaf();
  }

  public boolean startsWith(String prefix) {
    TrieNode node = searchPrefix(prefix);
    return node != null;
  }

  class TrieNode {

    private static final int LETTERS = 26;
    private TrieNode[] links;
    private boolean isLeaf;

    TrieNode() {
      links = new TrieNode[LETTERS];
    }

    public boolean containsKey(char ch) {
      return links[ch - 'a'] != null;
    }

    public TrieNode get(char ch) {
      return links[ch - 'a'];
    }

    public void put(char ch, TrieNode node) {
      links[ch - 'a'] = node;
    }

    public void setEnd() {
      isLeaf = true;
    }

    public boolean isLeaf() {
      return isLeaf;
    }
  }
}
