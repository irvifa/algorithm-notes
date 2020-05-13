package com.github.irvifa.algorithmnotes.courses.google.preassignment.singlestopover;

import java.util.*;

public class single {
  public static void main(String[] args) {
    final Scanner sc = new Scanner(System.in);
    Map<String, TreeSet<String>> map = new HashMap<>();
    final int n = sc.nextInt();
    for (int i = 0; i < n; ++i) {
      final String fr = sc.next();
      final String to = sc.next();
      if (!map.containsKey(fr)) {
        TreeSet<String> curr = new TreeSet<>();
        curr.add(to);
        map.put(fr, curr);
      } else {
        TreeSet<String> curr = map.get(fr);
        curr.add(to);
        map.put(fr, curr);
      }
    }
    final int m = sc.nextInt();

    for (int i = 0; i < m; ++i) {
      final String fr = sc.next();
      final String to = sc.next();
      if (!map.containsKey(fr)) {
        System.out.println();
      } else {
        TreeSet<String> curr = map.get(fr);
        List<String> res = new ArrayList<>();
        TreeSet<String> tmp = new TreeSet<>();
        for (String key : curr) {
          if (map.containsKey(key) && !key.equals(to) && !key.equals(fr)) {
            TreeSet<String> arrivals = map.get(key);
            for (String arrival : arrivals) {
              if (arrival.equals(to)) {
                tmp.add(key);
              }
            }
          }
        }
        res.addAll(tmp);
        Collections.sort(res);
        for (int j = 0; j < res.size(); j++) {
          System.out.print(res.get(j));
          if (j != res.size() - 1) {
            System.out.print(" ");
          }
        }
        System.out.println();
      }
    }
  }
}
