package com.example.practice;

import org.junit.jupiter.api.Test;

public class LoopTest2 {
    int size = 9;

    @Test
    void mondai1() {
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= i; j++) {
                if (i == j) {
                    System.out.print("* ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    @Test
    void mondai2() {
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (i % 2 != 0 && j % 2 != 0 || i % 2 == 0 && j % 2 == 0) {
                    System.out.print("* ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    @Test
    void mondai3() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size / 2 || j == size / 2) {
                    System.out.print("* ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}
