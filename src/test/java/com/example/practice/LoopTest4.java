package com.example.practice;

import org.junit.jupiter.api.Test;

public class LoopTest4 {
    int size = 5;
    int num = 1;

    @Test
    void mondai1() {
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                System.out.print(num++ + " ");
            }
            System.out.println();
        }
    }

    @Test
    void mondai2() {
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                System.out.printf("%2d ", num++);
            }
            System.out.println();
        }
    }

    @Test
    void mondai3() {
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                System.out.printf("%2d ", i * j);
            }
            System.out.println();
        }
    }
}
