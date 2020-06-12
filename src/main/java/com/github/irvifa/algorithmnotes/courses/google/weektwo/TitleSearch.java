package com.github.irvifa.algorithmnotes.courses.google.weektwo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TitleSearch {
    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);

        List<String> titles = new ArrayList<>();
        final int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; ++i) {
            titles.add(sc.nextLine());
        }

        List<String> queries = new ArrayList<>();
        final int q = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < q; ++i) {
            queries.add(sc.nextLine());
        }
        solve(titles, queries);
    }

    static void solve(List<String> titles, List<String> queries) {
        // YOUR CODE HERE
        Map<String, Sentence> words = new HashMap<>();
        for (String title : titles) {
            Sentence sentence = words.getOrDefault(title, new Sentence());
            if (words.containsKey(title)) {
                sentence.frequency = sentence.frequency + 1;
            } else {
                String[] tmp = title.split("[^\\w]+");
                Map<String, Word> currentWord = new HashMap<>();
                for (String t : tmp) {
                    Word w = currentWord.getOrDefault(t, new Word());
                    if (!currentWord.containsKey(t)) {
                        w.frequency = 0;
                        w.word = t;
                    }
                    w.frequency = w.frequency + 1;
                    currentWord.put(t, w);
                }
                sentence.frequency = 1;
                sentence.words = currentWord;
            }
            words.put(title, sentence);
        }


        for (String query : queries) {
            PriorityQueue<Word> q = new PriorityQueue<>(
                    Comparator.comparing((Word s) -> s.word.split("[^\\w]+").length)
                            .thenComparing((s1, s2) -> s1.word.compareTo(s2.word)));
            String[] tmp = query.split("[^\\w]+");
            Map<String, Word> currentWord = new HashMap<>();
            for (String t : tmp) {
                Word w = currentWord.getOrDefault(t, new Word());
                if (!currentWord.containsKey(t)) {
                    w.frequency = 0;
                    w.word = t;
                }
                w.frequency = w.frequency + 1;
                currentWord.put(t, w);
            }

            int tot = 0;
            for (String s : words.keySet()) {
                Sentence st = words.get(s);
                Map<String, Word> cw = st.words;
                Map<String, Word> cww = new HashMap<>(currentWord);
                if (cw.size() < cww.size()) {
                    continue;
                }
                for (String s1 : cw.keySet()) {
                    if (cww.containsKey(s1)) {
                        cww.remove(s1);
                    }
                }
                if (cww.size() == 0) {
                    Word newWord = new Word();
                    newWord.word = s;
                    newWord.frequency = st.frequency;
                    q.add(newWord);
                    tot += st.frequency;
                }
            }
            if (tot <= 10) {
                System.out.println(tot);
            } else {
                System.out.println(10);
            }
            int n = 10;
            int j = 0;
            while (!q.isEmpty()) {
                Word w = q.poll();
                String str = w.word;
                int freq = w.frequency;
                for (int i = 0; i < freq && j < n; i++) {
                    System.out.println(str);
                    j++;
                }
            }
        }
    }
}

class Word {
    String word;
    Integer frequency;
}

class Sentence {
    Integer frequency;
    Map<String, Word> words;
}