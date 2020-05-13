package com.github.irvifa.algorithmnotes.courses.google.preassignment.mergearrays;

import java.util.Scanner;

public class Merge {
  public static void main(String[] args) {
    final Scanner sc = new Scanner(System.in);
    final int a = sc.nextInt();
    final int[] ta = new int[a];
    final int b = sc.nextInt();
    final int[] tb = new int[b];
    for (int ai = 0; ai < a; ++ai) {
      ta[ai] = sc.nextInt();
    }
    for (int bi = 0; bi < b; ++bi) {
      tb[bi] = sc.nextInt();
    }

    int i = 0;
    int j = 0;
    while (i < a && j < b) {
      if (ta[i] <= tb[j]) {
        System.out.println(ta[i]);
        i++;
      } else {
        System.out.println(tb[j]);
        j++;
      }
    }

    while (i < a) {
      System.out.println(ta[i]);
      i++;
    }

    while (j < b) {
      System.out.println(tb[j]);
      j++;
    }
  }
}
