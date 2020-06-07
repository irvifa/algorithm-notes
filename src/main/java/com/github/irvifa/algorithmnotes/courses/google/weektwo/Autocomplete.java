package com.github.irvifa.algorithmnotes.courses.google.weektwo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Autocomplete {

    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);

        final int n = Integer.parseInt(sc.nextLine());

        List<String> dict = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            dict.add(sc.nextLine());
        }

        final int q = Integer.parseInt(sc.nextLine());
        List<String> queries = new ArrayList<>(q);
        for (int i = 0; i < q; ++i) {
            queries.add(sc.nextLine());
        }

        solve(dict, queries);
    }


    static private void solve(List<String> dict, List<String> queries) {
        // YOUR CODE HERE
        Set<String> dictionaries = new HashSet<>();
        dictionaries.addAll(dict);
        for (String query: queries) {
            List<String> res = new ArrayList<>();
            for (String s: dictionaries) {
                if (s.length() >= query.length()) {
                    int diff = calculate(query, s.substring(0, query.length()));
                    if (diff <= 1) {
                        res.add(s);
                    }
                }
            }
            Set <String> uniqueResult = new HashSet<>(res);
            PriorityQueue<String> pq = new PriorityQueue<>();
            pq.addAll(uniqueResult);
            res = new ArrayList<>();
            for (int i = 0; i < 10 && !pq.isEmpty(); i++) {
                res.add(pq.poll());
            }
            if (res.size() == 0) {
                System.out.println("<no matches>");
            } else {
                for (int i = 0; i < res.size(); i++) {
                    if (i == res.size() - 1) {
                        System.out.print(res.get(i));
                        break;
                    }
                    System.out.print(res.get(i) + " ");
                }
                System.out.println();
            }
        }
    }

    static int calculate(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
                            Math.min(dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1));
                }
            }
        }

        return dp[x.length()][y.length()];
    }

    static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }
}
