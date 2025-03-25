package com.example.practice;

import org.junit.jupiter.api.Test;

public class LoopTest {
    int rows = 5;
    int cols = 5;

    @Test
    void mondai1() {
        for (int i = 1; i <= rows; i++) { 
            for (int j = 1; j <= i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }

    @Test
    void mondai2() {
        for (int i = 1; i <= rows; i++) { 
            for (int j = rows; j >= i; j--) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }

    @Test
    void mondai3() {
        for (int i = 1; i <= rows; i++) { 
            for (int j = 1; j <= cols; j++) {
                if (i == 1 || i == rows || j == 1 || j == cols) {
                    System.out.print("* ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}
