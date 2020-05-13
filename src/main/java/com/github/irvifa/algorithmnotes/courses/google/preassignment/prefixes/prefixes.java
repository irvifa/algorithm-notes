package com.github.irvifa.algorithmnotes.courses.google.preassignment.prefixes;

import java.util.*;

class TrieNode {

  char character;
  HashMap<Character, TrieNode> children = new HashMap<>();
  boolean isLeaf;

  public TrieNode() {
  }

  public TrieNode(char character) {
    this.character = character;
  }
}

class Trie {

  private TrieNode root;

  public Trie() {
    root = new TrieNode();
  }

  // Inserts a word into the trie.
  public void insert(String word) {
    HashMap<Character, TrieNode> children = root.children;

    for (int i = 0; i < word.length(); i++) {
      char c = word.charAt(i);

      TrieNode t;
      if (children.containsKey(c)) {
        t = children.get(c);
      } else {
        t = new TrieNode(c);
        children.put(c, t);
      }

      children = t.children;

      //set leaf node
      if (i == word.length() - 1) {
        t.isLeaf = true;
      }
    }
  }

  // Returns if the word is in the trie.
  public boolean search(String word) {
    TrieNode t = searchNode(word);

    if (t != null && t.isLeaf) {
      return true;
    } else {
      return false;
    }
  }

  // Returns if there is any word in the trie
  // that starts with the given prefix.
  public boolean startsWith(String prefix) {
    if (searchNode(prefix) == null) {
      return false;
    } else {
      return true;
    }
  }

  public TrieNode searchNode(String str) {
    Map<Character, TrieNode> children = root.children;
    TrieNode t = null;
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (children.containsKey(c)) {
        t = children.get(c);
        children = t.children;
      } else {
        return null;
      }
    }

    return t;
  }

  public List<String> getWordsWithPrefix(String prefix) {
    List<String> res = new ArrayList<>();
    TrieNode root = searchNode(prefix);
    if (root == null) {
      return res;
    }
    List<LinkedList<Character>> chars = collectChars(root);
    for (LinkedList<Character> charList : chars) {
      String current = combine(prefix.substring(0, prefix.length() - 1), charList);
      res.add(current);
    }
    return res;
  }

  private String combine(String prefix, List<Character> charList) {
    StringBuilder sb = new StringBuilder(prefix);
    for (Character character : charList) {
      sb.append(character);
    }
    return sb.toString();
  }

  private List<LinkedList<Character>> collectChars(TrieNode node) {
    List<LinkedList<Character>> chars = new ArrayList<LinkedList<Character>>();

    if (node.children.size() == 0) {
      chars.add(new LinkedList<Character>(Collections.singletonList(node.character)));
    } else {
      if (node.isLeaf) {
        LinkedList<Character> tmp = new LinkedList<>();
        tmp.add(node.character);
        chars.add(tmp);
      }
      Map<Character, TrieNode> children = node.children;
      for (Character key : children.keySet()) {
        TrieNode child = children.get(key);
        List<LinkedList<Character>> childList = collectChars(child);
        for (LinkedList<Character> characters : childList) {
          characters.push(node.character);
          chars.add(characters);
        }
      }
    }
    return chars;
  }
}

public class prefixes {

  public static void main(String[] args) {
    final Scanner sc = new Scanner(System.in);
    final int n = sc.nextInt();
    Trie root = new Trie();
    for (int i = 0; i < n; ++i) {
      String word = sc.next();
      root.insert(word);
    }
    final int m = sc.nextInt();
    for (int j = 0; j < m; ++j) {
      final String prefix = sc.next();
      List<String> words = root.getWordsWithPrefix(prefix);
      for (String word : words) {
        System.out.print(word + " ");
      }
      System.out.println();
    }
  }
}
