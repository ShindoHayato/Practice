package com.example.practice;

import org.junit.jupiter.api.Test;

public class LoopTest5 {
    int rows = 4;
    int cols = 5;
    int num = 10;
    int[][] grid = new int[rows][cols];

    @Test
    void mondai1() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print((num + j + i * 3) + " ");
            }
            System.out.println();
        }
    }

    @Test
    void mondai2() {
        // フィボナッチ数列の初期値
        grid[0][0] = 0;
        grid[0][1] = 1;
        
        // フィボナッチ数列の計算
        for (int i = 0, k = 2; i < rows; i++) {
            for (int j = (i == 0 ? 2 : 0); j < cols; j++, k++) {
                if (i == 0 && j < 2) continue;
                grid[i][j] = grid[(k - 1) / cols][(k - 1) % cols] + grid[(k - 2) / cols][(k - 2) % cols];
            }
        }
        
        // グリッドの表示
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j] + "  ");
            }
            System.out.println();
        }
    }
}
