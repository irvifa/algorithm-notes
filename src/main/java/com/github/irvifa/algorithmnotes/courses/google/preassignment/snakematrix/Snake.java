package com.github.irvifa.algorithmnotes.courses.google.preassignment.snakematrix;

import java.util.Scanner;

public class Snake {
  public static void main(String[] args) {
    final Scanner sc = new Scanner(System.in);
    final int n = sc.nextInt();
    // ... Replace code below.
    int j = 1;
    for (int i = 1; i <= n;i++) {
      if (i % 2 == 1) {
        for (int k = 1; k <= n; k++) {
          System.out.print(j);
          if (k != n) {
            j++;
            System.out.print(" ");
          }
        }
      } else {
        for (int k = 1; k <= n; k++) {
          System.out.print(j);
          if (k != n) {
            j--;
            System.out.print(" ");
          }
        }
      }
      j += n;
      System.out.println();
    }
  }
}