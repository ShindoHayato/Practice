package com.example.practice;

import org.junit.jupiter.api.Test;

public class LoopTest3 {
    int size = 10;

    @Test
    void mondai1() {
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (i == j || i + j == size + 1) {
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
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                System.out.print("  ");
            }

            for (int k = 0; k < 2 * i + 1; k++) {
                System.out.print("* ");
            }

            for (int j = 0; j < size - i - 1; j++) {
                System.out.print("  ");
            }
            System.out.println();
        }
    }

    @Test
    void mondai3() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size * 2 - 1; j++) {
                if (j == i || j == (size * 2 - 2 - i)) {
                    System.out.print("* ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}
